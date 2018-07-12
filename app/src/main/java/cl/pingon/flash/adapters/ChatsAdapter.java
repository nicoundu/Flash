package cl.pingon.flash.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.siyamed.shapeimageview.BubbleImageView;
import com.squareup.picasso.Picasso;

import cl.pingon.flash.R;
import cl.pingon.flash.data.CurrentUser;
import cl.pingon.flash.data.Nodes;
import cl.pingon.flash.models.Chat;


public class ChatsAdapter extends FirebaseRecyclerAdapter<Chat, ChatsAdapter.ViewHolder> {

    private ChatListener listener;


    public ChatsAdapter(@NonNull FirebaseRecyclerOptions<Chat> options, ChatListener listener) {
        super(options);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Chat chat) {
        Picasso.get().load(chat.getPhoto()).centerCrop().fit().into(holder.photoBiv);
        holder.emailTv.setText(chat.getReceiver());
        if (chat.isNotification()) {
            holder.notificationV.setVisibility(View.VISIBLE);
        } else {
            holder.notificationV.setVisibility(View.GONE);
        }

    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        private BubbleImageView photoBiv;
        private TextView emailTv;
        private View notificationV;


        public ViewHolder(View itemView) {
            super(itemView);

            photoBiv = itemView.findViewById(R.id.photoBiv);
            emailTv = itemView.findViewById(R.id.emailTv);
            notificationV = itemView.findViewById(R.id.notificationV);


        }
    }





}
