package com.emddi.mymusic.screen.home;

import com.emddi.mymusic.base.BasePresenterImpl;
import com.emddi.mymusic.data.model.Genre;
import com.emddi.mymusic.data.repository.GenreRepository;
import com.emddi.mymusic.data.source.GenreDataSource;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter extends BasePresenterImpl<HomeContract.View> implements
        GenreDataSource.OnFetchDataListener<Genre>, HomeContract.Presenter {
    private List<Genre> mGenres = new ArrayList<>();
    private GenreRepository mGenreRepository;

    public HomePresenter(HomeContract.View view, GenreRepository genreRepository) {
        super(view);
        mGenreRepository = genreRepository;
    }

    @Override
    public void getGenre() {
        mView.showProgress();
        mGenreRepository.getTrackRemote(this);
    }

    @Override
    public void onSuccess(Genre data) {
        mView.hideProgress();
        mGenres.add(data);
        mView.getGenreSuccess(mGenres);
    }

    @Override
    public void onFailure(String error) {
        mView.hideProgress();
    }
}
