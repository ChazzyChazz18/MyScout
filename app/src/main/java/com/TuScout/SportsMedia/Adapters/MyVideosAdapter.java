package com.TuScout.SportsMedia.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.TuScout.SportsMedia.Models.Videos;
import com.TuScout.SportsMedia.PlayerActivity;
import com.TuScout.SportsMedia.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;

import java.util.ArrayList;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MyVideosAdapter extends RecyclerView.Adapter<MyVideosAdapter.ViewHolder> {

    private ArrayList<Videos> dataset;
    private Context context;

    public MyVideosAdapter(Context context) {
        this.context = context;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item_mine,
                viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Videos video = dataset.get(i);

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

        private FrameLayout videoBtn;

        private TextView addedTime;

        private ImageView videoPreviewImageView;
        //private TextView titleTextView;
        //private TextView descriptionTextView;
        //private LinearLayout videoPreviewImageView;
        //private ImageView videoUserProfileIcon;

        ViewHolder(View itemView) {

            super(itemView);

            videoBtn = itemView.findViewById(R.id.my_video_item_thumbnail);

            addedTime = itemView.findViewById(R.id.my_video_item_time);

            videoPreviewImageView = itemView.findViewById(R.id.video_preview);

            //titleTextView = itemView.findViewById(R.id.video_item_title);
            //descriptionTextView = itemView.findViewById(R.id.video_item_description);
            //videoPreviewImageView = itemView.findViewById(R.id.video_preview);
            //videoUserProfileIcon = itemView.findViewById(R.id.video_profile_img);
        }
    }
}
