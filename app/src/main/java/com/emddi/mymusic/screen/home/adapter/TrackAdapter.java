package com.emddi.mymusic.screen.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.emddi.mymusic.R;
import com.emddi.mymusic.data.model.Track;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ItemViewHolder> {
    private Context mContext;
    private List<Track> mTracks;
    private LayoutInflater mInflater;

    public TrackAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_track,
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Track track = mTracks.get(position);
        holder.bindView(mContext, track);
    }

    @Override
    public int getItemCount() {
        return mTracks == null ? 0 : mTracks.size();
    }

    public void setTracks(List<Track> data) {
        mTracks = data;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageTrack;
        private TextView mTitle;

        private ItemViewHolder(View itemView) {
            super(itemView);
            mImageTrack = itemView.findViewById(R.id.image_track);
            mTitle = itemView.findViewById(R.id.text_title_track);
        }

        private void bindView(Context context, Track track) {
            if (track == null) return;
            String url = track.getAvatarUrl();
            if (track.getAvatarUrl().equals("")) {
                url = track.getArtworkUrl();
            }
            Glide.with(context).load(url).into(mImageTrack);
            mTitle.setText(track.getTitle());
        }
    }
}
