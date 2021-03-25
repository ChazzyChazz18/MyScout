package com.TuScout.SportsMedia.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.TuScout.SportsMedia.Models.Videos;
import com.TuScout.SportsMedia.PlayerActivity;
import com.TuScout.SportsMedia.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.ViewHolder> {

    private ArrayList<Videos> dataset;
    private Context context;

    public VideosAdapter (Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item,
                viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Videos video = dataset.get(i);

        viewHolder.titleTextView.setText(video.getNombre());
        viewHolder.descriptionTextView.setText(video.getDescripcion());

        //For CrossFade Effect
        /*DrawableCrossFadeFactory factory =
                new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();*/

        /*Glide.with(context)
                .load(R.drawable.profile_test)
                .transition(withCrossFade(factory))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.circleCropTransform())
                .into(viewHolder.videoUserProfileIcon);*/

        RequestOptions previewOpts = new RequestOptions()
                .centerCrop();

        RequestOptions profileOpts = new RequestOptions()
                .centerCrop()
                .override(128, 128);

        Glide.with(context)
                .asBitmap()
                .apply(profileOpts)
                .load(video.getVideoURL())
                .placeholder(R.drawable.profile_test)
                //.transition(withCrossFade(factory))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.circleCropTransform())
                .into(viewHolder.videoUserProfileIcon);

        Glide.with(context)
                .asBitmap()
                //.apply(previewOpts)
                .load(video.getVideoURL())
                //.transition(withCrossFade(factory))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.videoPreviewImageView);

        viewHolder.videoBtn.setOnClickListener(v -> {
            //Toast.makeText(context, video.getNombre(), Toast.LENGTH_SHORT).show();
            //((MainActivity)context).showSelectedFragment(((MainActivity)context).PlayerFragmentIns ());
            Intent intent = new Intent(context, PlayerActivity.class);
            intent.putExtra("videoURL", video.getVideoURL());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addVideoList (ArrayList<Videos> videoList) {
        dataset.addAll(videoList);
        notifyDataSetChanged();
    }

    public void clearVideoList () {
        if(dataset.size() > 0) {
            dataset.clear();
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout videoBtn;

        private TextView titleTextView;
        private TextView descriptionTextView;
        private ImageView videoPreviewImageView;
        private ImageView videoUserProfileIcon;

        ViewHolder(View itemView) {

            super(itemView);

            videoBtn = itemView.findViewById(R.id.video_item_btn);

            titleTextView = itemView.findViewById(R.id.video_item_title);
            descriptionTextView = itemView.findViewById(R.id.video_item_description);
            videoPreviewImageView = itemView.findViewById(R.id.video_preview);
            videoUserProfileIcon = itemView.findViewById(R.id.video_profile_img);
        }
    }
}
