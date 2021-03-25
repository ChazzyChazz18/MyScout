package com.TuScout.SportsMedia.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.TuScout.SportsMedia.Adapters.MessagesAdapter;
import com.TuScout.SportsMedia.Models.Messages;
import com.TuScout.SportsMedia.Models.Videos;
import com.TuScout.SportsMedia.R;

import java.util.ArrayList;

public class MyMessagesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_messages, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.my_messages_recyclerview);
        MessagesAdapter messagesAdapter = new MessagesAdapter(getContext());
        recyclerView.setAdapter(messagesAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);

        //For Test
        ArrayList<Messages> messagesList = new ArrayList<>();
        messagesList.add(new Messages("User 1", "https://images.pexels.com/photos/207962/pexels-photo-207962.jpeg", "This is just some random text for testing porpurse ja ja ja :v"));
        messagesList.add(new Messages("User 2", "https://pub-static.haozhaopian.net/static/web/site/features/one-tap-enhance/images/1-tap-enhance_comparison_chart0cd39fa2358c48f674db97b65327666e.jpg", "This is just some random text for testing porpurse ja ja ja :v"));
        messagesList.add(new Messages("User 3", "", "This is just some random text for testing porpurse ja ja ja :v"));
        messagesList.add(new Messages("User 4", "", "This is just some random text for testing porpurse ja ja ja :v"));
        messagesList.add(new Messages("User 5", "", "... :D"));
        messagesList.add(new Messages("User 6", "", "... :V"));
        messagesList.add(new Messages("User 7", "", "... :X"));
        messagesList.add(new Messages("User 8", "", "... :S"));

        messagesAdapter.addCommentList(messagesList);

        return view;
    }
}
