package com.michen.olaxueyuan.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;

import com.michen.olaxueyuan.ui.circle.CircleFragment;
import com.michen.olaxueyuan.ui.course.CourseFragment;
import com.michen.olaxueyuan.ui.examination.ExamFragment;
import com.michen.olaxueyuan.ui.home.HomeFragment;
import com.michen.olaxueyuan.ui.me.UserFragment;
import com.michen.olaxueyuan.ui.question.QuestionFragment;

/**
 * Created by tianxiaopeng on 15-1-2.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private QuestionFragment questionFragment;
    private ExamFragment examFragment;
    private CourseFragment courseFragment;
    private CircleFragment circleFragment;
    private HomeFragment homeFragment;
    private UserFragment userFragment;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
        construct();
    }

    private void construct() {
        questionFragment = new QuestionFragment();
        examFragment = new ExamFragment();
        courseFragment = new CourseFragment();
        homeFragment = new HomeFragment();
        circleFragment = new CircleFragment();
        userFragment = new UserFragment();
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return questionFragment;
            case 1:
                return courseFragment;
            case 2:
                return examFragment;
            case 3:
                return circleFragment;
            case 4:
                return userFragment;
            default:
                return null;
        }
    }

}

