package com.emddi.mymusic.screen.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.emddi.mymusic.R;
import com.emddi.mymusic.base.BaseFragment;
import com.emddi.mymusic.data.model.Genre;
import com.emddi.mymusic.data.repository.GenreRepository;
import com.emddi.mymusic.screen.home.adapter.GenreAdapter;
import com.emddi.mymusic.utils.APIServiceUtils;

import java.util.List;

public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {
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
}
