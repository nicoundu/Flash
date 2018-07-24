package cl.pingon.flash.adapters;

import android.app.DownloadManager;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Node;

import cl.pingon.flash.R;
import cl.pingon.flash.data.CurrentUser;
import cl.pingon.flash.data.Nodes;
import cl.pingon.flash.models.Message;

public class MessageAdapter extends FirebaseRecyclerAdapter<Message, MessageAdapter.MessageHolder> {

    private static final String CURRENT_EMAIL = new CurrentUser().email();
    private MessagesCallback callback;

    public MessageAdapter(String key, MessagesCallback callback) {
        super(new FirebaseRecyclerOptions.Builder<Message>()
                .setQuery(new Nodes().messages(key), Message.class)
                .setLifecycleOwner((LifecycleOwner) callback)
                .build());
        this.callback = callback;

    }

    @Override
    protected void onBindViewHolder(@NonNull MessageHolder holder, int position, @NonNull Message model) {
        TextView textView = (TextView) holder.itemView;
        if(CURRENT_EMAIL.equals(model.getOwner())){
            textView.setGravity(Gravity.RIGHT);
        } else {
            textView.setGravity(Gravity.LEFT);
        }
        textView.setText(model.getContent());

    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_message, parent, false);
        return new MessageHolder(view);

    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        callback.update();
    }

    public static class MessageHolder extends RecyclerView.ViewHolder{
        public MessageHolder(View itemView) {
            super(itemView);
        }
    }

}
