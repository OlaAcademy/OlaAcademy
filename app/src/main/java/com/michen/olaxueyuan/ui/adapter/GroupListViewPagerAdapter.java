package com.michen.olaxueyuan.ui.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.michen.olaxueyuan.ui.group.GroupListFragment;

/**
 * Created by mingge on 16/8/9.
 */
public class GroupListViewPagerAdapter extends FragmentStatePagerAdapter {
    private GroupListFragment fragmentMaths;
    private GroupListFragment fragmentEnglish;
    private GroupListFragment fragmentLogic;
    private GroupListFragment fragmentWriting;

    public GroupListViewPagerAdapter(FragmentManager fm) {
        super(fm);
        constrct();
    }

    private void constrct() {
        fragmentMaths = new GroupListFragment();
        fragmentEnglish = new GroupListFragment();
        fragmentLogic = new GroupListFragment();
        fragmentWriting = new GroupListFragment();
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
