package cl.pingon.flash.views.main.drawer;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import cl.pingon.flash.data.CurrentUser;
import cl.pingon.flash.data.PhotoPreference;

public class UploadPhoto {

    private Context context;

    public UploadPhoto(Context context) {
        this.context = context;
    }

    public void toFirebase(String path) {

        CurrentUser currentUser = new CurrentUser();
        String folder = currentUser.sanitizedEmail(currentUser.email() + "/");
        String photoName = "avatar.jpeg";
        String baseUrl = "gs://flash-a7f9f.appspot.com/avatars/";
        String refUrl = baseUrl + folder + photoName;
        final StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(refUrl);
        storageReference.putFile(Uri.parse(path)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                String[] fullUrl = storageReference.getDownloadUrl().toString().split("&token");
                String url = fullUrl[0];

                Log.d("PHOTO_URL", url);

                new PhotoPreference(context).photoSave(url);


            }
        });
    }
}
