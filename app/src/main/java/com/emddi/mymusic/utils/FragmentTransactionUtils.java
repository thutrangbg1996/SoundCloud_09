package com.emddi.mymusic.utils;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.emddi.mymusic.base.BaseFragment;

public class FragmentTransactionUtils {
    public static FragmentTransaction sTransaction;

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
