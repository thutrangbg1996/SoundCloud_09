package com.emddi.mymusic.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by TrangTros on 28/06/2018.
 */

public abstract class BaseActivity<T extends BasePresenter> extends
        AppCompatActivity implements BaseView<T> {
    private T mPresenter;

    protected abstract void initView();
    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //inject view
        initView();
        //Presenter for this view
        mPresenter = (T) onCreatePresenter();
        //Prepare layout
        onPrepareLayout();
    }

    @Override
    public void onPrepareLayout() {
    }

    @Override
    public T getPresenter() {
        return mPresenter;
    }
}
