package com.emddi.mymusic.screen.home;

import com.emddi.mymusic.base.BasePresenterImpl;
import com.emddi.mymusic.callback.ResponseListenner;
import com.emddi.mymusic.data.model.Genre;
import com.emddi.mymusic.data.source.remote.network.ServiceBuilder;
import com.emddi.mymusic.utils.APIServiceUtils;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter extends BasePresenterImpl<HomeContract.View> implements
        ResponseListenner<Genre>, HomeContract.Presenter {
    private List<Genre> mGenres = new ArrayList<>();

    public HomePresenter(HomeContract.View view) {
        super(view);
    }

    @Override
    public void getGenre() {
        mView.showProgress();
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
