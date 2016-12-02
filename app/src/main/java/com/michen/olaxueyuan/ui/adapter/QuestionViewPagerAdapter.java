package com.michen.olaxueyuan.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.michen.olaxueyuan.ui.question.QuestionHomeWorkFragment;

/**
 * Created by mingge on 16/8/9.
 */
public class QuestionViewPagerAdapter extends FragmentStatePagerAdapter {
    private QuestionHomeWorkFragment fragmentMaths;
    private QuestionHomeWorkFragment fragmentEnglish;
    private QuestionHomeWorkFragment fragmentLogic;
    private QuestionHomeWorkFragment fragmentWriting;

    public QuestionViewPagerAdapter(FragmentManager fm) {
        super(fm);
        constrct();
    }

    private void constrct() {
        fragmentMaths = new QuestionHomeWorkFragment();
        fragmentEnglish = new QuestionHomeWorkFragment();
        fragmentLogic = new QuestionHomeWorkFragment();
        fragmentWriting = new QuestionHomeWorkFragment();
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
