package com.emddi.mymusic.screen.home;

import com.emddi.mymusic.base.BasePresenter;
import com.emddi.mymusic.base.BaseView;
import com.emddi.mymusic.data.model.Genre;

import java.util.List;

public interface HomeContract {
    interface Presenter extends BasePresenter {
        void getGenre();
    }

    interface View extends BaseView<Presenter> {
        void getGenreSuccess(List<Genre> genres);
    }
}
