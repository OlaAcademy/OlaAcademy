package com.michen.olaxueyuan.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.michen.olaxueyuan.ui.course.commodity.DataLibraryFragment;

/**
 * Created by mingge on 2016/11/3.
 */

public class DataLibraryViewPagerAdapter  extends FragmentStatePagerAdapter {
    private DataLibraryFragment fragmentMaths;
    private DataLibraryFragment fragmentEnglish;
    private DataLibraryFragment fragmentLogic;
    private DataLibraryFragment fragmentWriting;

    public DataLibraryViewPagerAdapter(FragmentManager fm) {
        super(fm);
        constrct();
    }

    private void constrct() {
        fragmentMaths = new DataLibraryFragment();
        fragmentEnglish = new DataLibraryFragment();
        fragmentLogic = new DataLibraryFragment();
        fragmentWriting = new DataLibraryFragment();
        Bundle bundleMaths = new Bundle();
        bundleMaths.putInt("type", 1);
        fragmentMaths.setArguments(bundleMaths);
        Bundle bundleEnglish = new Bundle();
        bundleEnglish.putInt("type", 2);
        fragmentEnglish.setArguments(bundleEnglish);
        Bundle bundleLogic = new Bundle();
        bundleLogic.putInt("type", 3);
        fragmentLogic.setArguments(bundleLogic);
        Bundle bundleWrting = new Bundle();
        bundleWrting.putInt("type", 4);
        fragmentWriting.setArguments(bundleWrting);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return fragmentMaths;
            case 1:
                return fragmentEnglish;
            case 2:
                return fragmentLogic;
            case 3:
                return fragmentWriting;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

}
