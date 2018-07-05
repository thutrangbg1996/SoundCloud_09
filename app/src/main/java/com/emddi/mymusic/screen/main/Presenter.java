package com.emddi.mymusic.screen.main;

import com.emddi.mymusic.base.BasePresenterImpl;

public class Presenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter {
    public Presenter(MainContract.View view) {
        super(view);
    }
}
