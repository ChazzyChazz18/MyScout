package com.TuScout.SportsMedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TuScout.SportsMedia.Models.Comments;
import com.TuScout.SportsMedia.Models.Videos;
import com.TuScout.SportsMedia.PlayerActivity;
import com.TuScout.SportsMedia.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private ArrayList<Comments> dataset;
    private Context context;

    public CommentsAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment_item,
                viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Comments comment = dataset.get(i);

        if(!comment.getUserProfileImg().equals("")) {
            Glide.with(context)
                    .load(comment.getUserProfileImg())
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.userProfileImg);
        }else {
            Glide.with(context)
                    .load(R.drawable.profile_test)
                    .apply(RequestOptions.circleCropTransform())
                    .into(viewHolder.userProfileImg);
        }

        viewHolder.userName.setText(comment.getUserName());

        viewHolder.userComment.setText(comment.getUserComment());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addCommentList (ArrayList<Comments> commentList) {
        dataset.addAll(commentList);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView userProfileImg;

        private TextView userName;

        private TextView userComment;

        ViewHolder(View itemView) {

            super(itemView);

            userProfileImg = itemView.findViewById(R.id.user_profile_image_imageview);

            userName = itemView.findViewById(R.id.user_name_textview);

            userComment = itemView.findViewById(R.id.comment_textview);

        }
    }
}
