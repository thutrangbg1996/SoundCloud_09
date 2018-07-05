package com.emddi.mymusic.screen.main;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.SearchView;

import com.emddi.mymusic.R;
import com.emddi.mymusic.base.BaseActivity;
import com.emddi.mymusic.screen.home.Fragment;
import com.emddi.mymusic.utils.FragmentTransactionUtils;

public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View,
        BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView mNavigation;
    private SearchView mSearchView;

    @Override
    public void onPrepareLayout() {
        super.onPrepareLayout();
        mSearchView.setQueryHint(getString(R.string.search_track));
        mNavigation.setOnNavigationItemSelectedListener(this);
        FragmentTransactionUtils.replaceAddToBackStack(getSupportFragmentManager(), new Fragment(),
                R.id.frame_container, false);
    }

    @Override
    protected void initView() {
        mNavigation = findViewById(R.id.navigation);
        mSearchView = findViewById(R.id.search_track);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public MainContract.Presenter onCreatePresenter() {
        return new Presenter(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                FragmentTransactionUtils.replaceAddToBackStack(getSupportFragmentManager(),
                        new Fragment(), R.id.frame_container, false);
                return true;
            case R.id.navigation_dashboard:
                return true;
            case R.id.navigation_notifications:
                return true;
        }
        return false;
    }
}
