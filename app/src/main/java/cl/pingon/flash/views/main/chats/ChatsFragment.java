package cl.pingon.flash.views.main.chats;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import cl.pingon.flash.R;
import cl.pingon.flash.adapters.ChatListener;
import cl.pingon.flash.adapters.ChatsAdapter;
import cl.pingon.flash.data.CurrentUser;
import cl.pingon.flash.data.Nodes;
import cl.pingon.flash.models.Chat;

public class ChatsFragment extends Fragment implements ChatListener {




    public ChatsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_chats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.chatsRv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        FirebaseRecyclerOptions<Chat> options = new FirebaseRecyclerOptions.Builder<Chat>()
                .setQuery(new Nodes().userChat(new CurrentUser().uid()), Chat.class)
                .setLifecycleOwner(getActivity())
                .build();

        ChatsAdapter adapter = new ChatsAdapter(options, this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void clicked(String key, String mail) {

    }
}
