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
import com.emddi.mymusic.utils.TrackUtils;

import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ItemViewHolder> {
    private Context mContext;
    private List<Track> mTracks;
    private LayoutInflater mInflater;
    private OnTrackClickListener mListener;

    public TrackAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setListener(OnTrackClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_track,
                parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bindView(mContext, mTracks, position);
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
        private OnTrackClickListener mListener;

        private ItemViewHolder(View itemView, OnTrackClickListener listener) {
            super(itemView);
            mImageTrack = itemView.findViewById(R.id.image_track);
            mTitle = itemView.findViewById(R.id.text_title_track);
            mListener = listener;
        }

        private void bindView(Context context,final List<Track> tracks, final int position) {
            Track track = tracks.get(position);
            if (track== null) return;
            String url = track.getAvatarUrl();
            if (track.getAvatarUrl().equals("")) {
                url = track.getArtworkUrl();
            }
            Glide.with(context).load(TrackUtils.getBetterArtwork(url)).into(mImageTrack);
            mTitle.setText(track.getTitle());
            mImageTrack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemClick(tracks, position);
                }
            });
        }
    }

    public interface OnTrackClickListener {
        void onItemClick(List<Track> tracks, int position);
    }
}
