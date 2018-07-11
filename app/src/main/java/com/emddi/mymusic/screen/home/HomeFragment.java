package com.emddi.mymusic.screen.home;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.emddi.mymusic.R;
import com.emddi.mymusic.base.BaseFragment;
import com.emddi.mymusic.data.model.Genre;
import com.emddi.mymusic.data.model.Track;
import com.emddi.mymusic.data.repository.GenreRepository;
import com.emddi.mymusic.screen.home.adapter.GenreAdapter;
import com.emddi.mymusic.screen.home.adapter.TrackAdapter;
import com.emddi.mymusic.screen.player.PlayerActivity;
import com.emddi.mymusic.service.MusicService;
import com.emddi.mymusic.utils.APIServiceUtils;
import com.emddi.mymusic.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View, TrackAdapter.OnTrackClickListener {
    private RecyclerView mRecyclerGenre;
    private ProgressBar mProgressBar;

    @Override
    public void onPrepareLayout() {
        getPresenter().getGenre();
    }

    @Override
    public void getGenreSuccess(List<Genre> genres) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerGenre.setLayoutManager(linearLayoutManager);
        GenreAdapter genreAdapter = new GenreAdapter(getContext(), APIServiceUtils.LIST_TRACK_GENRES, genres);
        genreAdapter.setListener(this);
        mRecyclerGenre.setAdapter(genreAdapter);
    }

    @Override
    protected void initView(android.view.View view) {
        mRecyclerGenre = view.findViewById(R.id.recycler_genre);
        mProgressBar = view.findViewById(R.id.progress_loadding);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public HomeContract.Presenter onCreatePresenter() {
        return new HomePresenter(this, GenreRepository.getInstance());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onItemClick(List<Track> tracks, int position) {
        Intent intentService = new Intent(getActivity(), MusicService.class);
        intentService.setAction(MusicService.ACTION_PLAY);
        intentService.putParcelableArrayListExtra(Track.class.getName(),
                (ArrayList<? extends Parcelable>) tracks);
        intentService.putExtra(Constants.POSITION, position);
        getActivity().startService(intentService);
        Intent intent = new Intent(getContext(), PlayerActivity.class);
        intent.putExtra(Track.class.getName(), tracks.get(position));
        startActivity(intent);
    }
}
