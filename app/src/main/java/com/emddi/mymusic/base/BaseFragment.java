package com.emddi.mymusic.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView<P>{
    private P mPresenter;
    private View mRootView;
    public static FragmentTransaction sTransaction;

    protected abstract int getLayoutId();
    protected abstract void initView();

    public BaseFragment() {
        mPresenter = onCreatePresenter();
    }

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mPresenter == null) {
            mPresenter = onCreatePresenter();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        initView();
        onPrepareLayout();
        return mRootView;
    }

    public static void replaceAddToBackStack(FragmentManager manager, BaseFragment baseFragment, int container,
                                             boolean addTobackStack) {
        sTransaction = manager.beginTransaction();
        sTransaction.replace(container, baseFragment);
        if (addTobackStack) {
            sTransaction.addToBackStack(null);
        }
        sTransaction.commit();
    }
}
