package com.emddi.mymusic.base;

public class BasePresenterImpl<V extends BaseView> implements BasePresenter {
    protected V mView;

    public BasePresenterImpl(V view) {
        this.mView = view;
    }

    @Override
    public V getView() {
        return mView;
    }

}
