package cl.pingon.flash.views.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.ServerValue;

import cl.pingon.flash.R;
import cl.pingon.flash.data.CurrentUser;
import cl.pingon.flash.data.Nodes;
import cl.pingon.flash.models.Chat;
import cl.pingon.flash.views.main.chats.ChatsFragment;

public class ChatActivity extends AppCompatActivity {

    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Chat chat = (Chat) getIntent().getSerializableExtra(ChatsFragment.CHAT);

        key = chat.getKey();
        new Nodes().userChat(new CurrentUser().uid()).child(key).child("timestamp").setValue(ServerValue.TIMESTAMP);

        getSupportActionBar().setTitle(chat.getReceiver());
    }

    @Override
    protected void onPause() {
        super.onPause();
        new Nodes().userChat(new CurrentUser().uid()).child(key).child("notification").setValue(false);
    }
}
