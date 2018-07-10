package cl.pingon.flash.views.main.drawer;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import cl.pingon.flash.data.CurrentUser;
import cl.pingon.flash.data.EmailProcessor;
import cl.pingon.flash.data.Nodes;
import cl.pingon.flash.data.PhotoPreference;
import cl.pingon.flash.models.LocalUser;

public class UploadPhoto {

    private Context context;

    public UploadPhoto(Context context) {
        this.context = context;
    }

    public void toFirebase(String path) {

        final CurrentUser currentUser = new CurrentUser();
        String folder = new EmailProcessor().sanitizedEmail(currentUser.email() + "/");
        String photoName = "avatar.jpeg";
        String baseUrl = "gs://flash-a7f9f.appspot.com/avatars/";
        String refUrl = baseUrl + folder + photoName;
        final StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(refUrl);
        UploadTask uploadTask = storageReference.putFile(Uri.parse(path));
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    String[] parts = task.getResult().toString().split("&token");
                    String url = parts[0];

                    Log.d("PHOTO_URL", url);

                    LocalUser user = new LocalUser();
                    user.setEmail(currentUser.email());
                    user.setName(currentUser.getCurrentUser().getDisplayName());
                    user.setPhoto(url);
                    user.setUid(currentUser.uid());
                    String key = new EmailProcessor().sanitizedEmail(currentUser.email());
                    new Nodes().user(key).setValue(user);

                    new PhotoPreference(context).photoSave(url);

                }
            }

        });
    }
}
