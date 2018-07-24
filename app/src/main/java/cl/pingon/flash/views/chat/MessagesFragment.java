package cl.pingon.flash.views.chat;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cl.pingon.flash.R;
import cl.pingon.flash.adapters.MessageAdapter;
import cl.pingon.flash.adapters.MessagesCallback;
import cl.pingon.flash.models.Chat;
import cl.pingon.flash.views.main.chats.ChatsFragment;


public class MessagesFragment extends Fragment implements MessagesCallback{

    private MessageAdapter adapter;
    private RecyclerView recyclerView;


    public MessagesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_messages, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Chat chat = (Chat) getActivity().getIntent().getSerializableExtra(ChatsFragment.CHAT);

        recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        adapter = new MessageAdapter(chat.getKey(), this);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void update() {

        recyclerView.scrollToPosition(adapter.getItemCount() -1);


    }


}
