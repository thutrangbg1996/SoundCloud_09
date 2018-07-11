package com.emddi.mymusic.screen.player;

import com.emddi.mymusic.base.BasePresenter;
import com.emddi.mymusic.base.BaseView;

public interface PlayerContract {
    interface View extends BaseView<PlayerContract.Presenter> {

    }

    interface Presenter extends BasePresenter {

    }
}
