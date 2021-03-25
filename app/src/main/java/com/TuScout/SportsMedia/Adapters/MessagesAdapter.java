package com.TuScout.SportsMedia.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TuScout.SportsMedia.Models.Comments;
import com.TuScout.SportsMedia.Models.Messages;
import com.TuScout.SportsMedia.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private ArrayList<Messages> dataset;
    private Context context;

    public MessagesAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_item,
                viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Messages message = dataset.get(i);

        if(!message.getTransmitterUserProfileImage().equals("")){
            Glide.with(context)
                    .load(message.getTransmitterUserProfileImage())
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.transmitterProfileImg);
        }else {
            Glide.with(context)
                    .load(R.drawable.profile_test)
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.transmitterProfileImg);
        }

        viewHolder.transmitterName.setText(message.getTransmitterUserName());

        viewHolder.transmitterMessage.setText(message.getTransmitterUserMessage());

        viewHolder.messageBtn.setOnClickListener(v -> {
            Toast.makeText(context, "El mensaje seleccionado es el de: " + message.getTransmitterUserName(), Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addCommentList (ArrayList<Messages> messagesList) {
        dataset.addAll(messagesList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View messageBtn;

        private ImageView transmitterProfileImg;

        private TextView transmitterName;

        private TextView transmitterMessage;

        ViewHolder(View itemView) {

            super(itemView);

            messageBtn = itemView;

            transmitterProfileImg = itemView.findViewById(R.id.transmitter_profile_image);

            transmitterName = itemView.findViewById(R.id.transmitter_name_text);

            transmitterMessage = itemView.findViewById(R.id.transmitter_message_text);

        }
    }
}
