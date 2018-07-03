package com.emddi.mymusic.base;

public interface BaseView<P extends BasePresenter> {

    void showProgress();

    void hideProgress();

    void onPrepareLayout();

    P getPresenter();

    P onCreatePresenter();

}
