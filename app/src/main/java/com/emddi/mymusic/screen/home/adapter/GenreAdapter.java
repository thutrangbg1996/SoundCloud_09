package com.emddi.mymusic.screen.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.emddi.mymusic.R;
import com.emddi.mymusic.data.model.Genre;

import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ItemViewHolder> {
    private List<String> mGenreNames;
    private List<Genre> mGenres;
    private LayoutInflater mInflater;
    private TrackAdapter.OnTrackClickListener mListener;

    public GenreAdapter(Context context, List<String> genreNames, List<Genre> genres) {
        mGenreNames = genreNames;
        mGenres = genres;
        mInflater = LayoutInflater.from(context);
    }

    public void setListener(TrackAdapter.OnTrackClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(mInflater.inflate(R.layout.item_genre, parent,
                false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Genre genre = mGenres.get(position);
        String genreName = mGenreNames.get(position);
        holder.bindView(genre, genreName);
    }

    @Override
    public int getItemCount() {
        return mGenres == null ? 0 : mGenres.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView mName;
        private TrackAdapter mTrackAdapter;
        private RecyclerView mRecyclerView;
        private TrackAdapter.OnTrackClickListener mListener;

        private ItemViewHolder(View itemView, TrackAdapter.OnTrackClickListener listener) {
            super(itemView);
            mName = itemView.findViewById(R.id.text_name_genre);
            mRecyclerView = itemView.findViewById(R.id.recycler_track);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            mListener = listener;
            mTrackAdapter = new TrackAdapter(itemView.getContext());
            mRecyclerView.setAdapter(mTrackAdapter);
        }

        private void bindView(Genre genre, String genreName) {
            if (genre == null) return;
            mName.setText(genreName);
            mTrackAdapter.setListener(mListener);
            mTrackAdapter.setTracks(genre.getListTrack());
        }
    }
}
