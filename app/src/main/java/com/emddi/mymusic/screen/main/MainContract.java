package com.emddi.mymusic.screen.main;

import com.emddi.mymusic.base.BasePresenter;
import com.emddi.mymusic.base.BaseView;

public interface MainContract {
    interface Presenter extends BasePresenter {
    }

    interface View extends BaseView<Presenter> {
    }
}
