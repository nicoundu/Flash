package cl.pingon.flash.views.main.finder;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import cl.pingon.flash.data.CurrentUser;
import cl.pingon.flash.data.EmailProcessor;
import cl.pingon.flash.data.Nodes;
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
            if (email.contains("œ")) {
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
                    callback.succes();
                }else {
                    callback.notFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
