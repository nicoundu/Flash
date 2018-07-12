package cl.pingon.flash.views.main.finder;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import cl.pingon.flash.data.CurrentUser;
import cl.pingon.flash.data.EmailProcessor;
import cl.pingon.flash.data.Nodes;
import cl.pingon.flash.data.PhotoPreference;
import cl.pingon.flash.models.Chat;
import cl.pingon.flash.models.LocalUser;

public class UserValidation {

    private FinderCallback callback;
    private Context context;

    public UserValidation(FinderCallback callback, Context context) {
        this.callback = callback;
        this.context = context;
    }

    public void init(String email) {

        if (email.trim().length() > 0 ) {
            if (email.contains("@")) {
                String currentEmail = new CurrentUser().email();
                if (!email.equals(currentEmail)) {
                    findUser(email);
                }else {
                    callback.error("¿No tienes con quien chatear?");
                }
            }else {
                callback.error("El email debe contener @");
            }
        } else {
            callback.error("Se necesita email");
        }
    }

    private void findUser(String email) {
        new Nodes().user(new EmailProcessor().sanitizedEmail(email)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LocalUser otherUser = dataSnapshot.getValue(LocalUser.class);

                if (otherUser != null) {
                    createChats(otherUser);
                }else {
                    callback.notFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void createChats(LocalUser otherUser) {
        FirebaseUser currentUser = new CurrentUser().getCurrentUser();
        String photo = new PhotoPreference(context).getPhoto();

        String key = new EmailProcessor().keyEmails(otherUser.getEmail());

        Chat currentChat = new Chat();
        currentChat.setKey(key);
        currentChat.setPhoto(otherUser.getPhoto());
        currentChat.setNotification(true);
        currentChat.setReceiver(otherUser.getEmail());

        Chat otherChat = new Chat();
        otherChat.setKey(key);
        otherChat.setPhoto(photo);
        otherChat.setNotification(true);
        otherChat.setReceiver(currentUser.getEmail());

        new Nodes().userChat(currentUser.getUid()).child(key).setValue(currentChat);
        new Nodes().userChat(otherUser.getUid()).child(key).setValue(otherChat);

        callback.succes();


    }


}
