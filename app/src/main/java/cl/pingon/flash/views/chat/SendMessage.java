package cl.pingon.flash.views.chat;

import cl.pingon.flash.data.CurrentUser;
import cl.pingon.flash.data.Nodes;
import cl.pingon.flash.models.Chat;
import cl.pingon.flash.models.Message;

public class SendMessage {

    public void validateMessage (String message, Chat chat) {

        if (message.trim().length() > 0) {

            String mail = new CurrentUser().email();
            Message msg = new Message();
            msg.setContent(message);
            msg.setOwner(mail);

            String key = chat.getKey();


            new Nodes().messages(key).push().setValue(msg);
            new Nodes().userChat(chat.getUid()).child(key).child("notification").setValue(true);
        }

    }

}
