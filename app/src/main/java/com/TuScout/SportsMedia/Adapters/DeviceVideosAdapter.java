package com.TuScout.SportsMedia.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.TuScout.SportsMedia.Fragments.AddVideoFragment;
import com.TuScout.SportsMedia.Fragments.LibraryFragment;
import com.TuScout.SportsMedia.MainActivity;
import com.TuScout.SportsMedia.Models.Videos;
import com.TuScout.SportsMedia.PlayerActivity;
import com.TuScout.SportsMedia.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class DeviceVideosAdapter extends RecyclerView.Adapter<DeviceVideosAdapter.ViewHolder> {

    private ArrayList<Videos> dataset;
    private Context context;
    private LibraryFragment libraryFragment;

    public DeviceVideosAdapter(Context context, LibraryFragment fragment) {
        this.context = context;
        libraryFragment = fragment;
        dataset = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_item_device,
                viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Videos video = dataset.get(i);

        Glide.with(context)
                .load(libraryFragment.getDeviceVideoList().get(i).getVideoURL()) // or URI/path
                .into(viewHolder.videoThumbnail); //imageview to set thumbnail to

        viewHolder.videoBtn.setOnClickListener(v -> {
            libraryFragment.setActualVideoInArrayPos(i);
            FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();
            AddVideoFragment fragm;
            if(fm != null) {
                fragm = (AddVideoFragment) fm.findFragmentByTag("6");
                if(fragm != null)
                    fragm.showSelectedFragment(fragm.getUploadVideoFragment());
            }
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

        private ImageView videoThumbnail;

        //private TextView addedTime;
        //private TextView titleTextView;
        //private TextView descriptionTextView;
        //private LinearLayout videoPreviewImageView;
        //private ImageView videoUserProfileIcon;

        ViewHolder(View itemView) {

            super(itemView);

            videoBtn = itemView.findViewById(R.id.device_video_item_thumbnail);

            videoThumbnail = itemView.findViewById(R.id.device_video_cover);

            //addedTime = itemView.findViewById(R.id.my_video_item_time);

            //titleTextView = itemView.findViewById(R.id.video_item_title);
            //descriptionTextView = itemView.findViewById(R.id.video_item_description);
            //videoPreviewImageView = itemView.findViewById(R.id.video_preview);
            //videoUserProfileIcon = itemView.findViewById(R.id.video_profile_img);
        }
    }
}
