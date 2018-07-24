package cl.pingon.flash;

import com.google.firebase.database.FirebaseDatabase;

import cl.pingon.flash.data.Nodes;

public class Offline {

    public void setPersistance() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        new Nodes().chats().keepSynced(true);
    }
}
