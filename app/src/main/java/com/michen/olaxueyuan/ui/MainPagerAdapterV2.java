package com.michen.olaxueyuan.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.michen.olaxueyuan.ui.circle.CircleFragment;
import com.michen.olaxueyuan.ui.course.CourseFragment;
import com.michen.olaxueyuan.ui.examination.ExamFragment;
import com.michen.olaxueyuan.ui.home.HomeFragment;
import com.michen.olaxueyuan.ui.teacher.TeacherHomeFragment;
import com.michen.olaxueyuan.ui.me.UserFragment;
import com.michen.olaxueyuan.ui.me.UserFragmentV2;
import com.michen.olaxueyuan.ui.question.QuestionFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tianxiaopeng on 15-1-2.
 */
public class MainPagerAdapterV2 extends FragmentStatePagerAdapter {

    private QuestionFragment questionFragment;
    private ExamFragment examFragment;
    private CourseFragment courseFragment;
    private CircleFragment circleFragment;
    private HomeFragment homeFragment;
    private UserFragment userFragment;
    private TeacherHomeFragment teacherHomeFragment;
    private UserFragmentV2 userFragmentV2;
    private List<Fragment> list = new ArrayList<>();

    public MainPagerAdapterV2(FragmentManager fm) {
        super(fm);
        construct();
    }

    public void upDateMainFragment() {
        notifyDataSetChanged();
    }

    private void construct() {
        questionFragment = new QuestionFragment();
//        examFragment = new ExamFragment();
        courseFragment = new CourseFragment();
        homeFragment = new HomeFragment();
        circleFragment = new CircleFragment();
//        userFragment = new UserFragment();
        teacherHomeFragment = new TeacherHomeFragment();
        userFragmentV2 = new UserFragmentV2();
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
//                if (SEAuthManager.getInstance() != null && SEAuthManager.getInstance().getAccessUser() != null
//                        && SEAuthManager.getInstance().getAccessUser().getIsActive() == 2) {
//                return teacherHomeFragment;
//                } else {
                return questionFragment;
//                }
            case 1:
                return courseFragment;
            case 2:
                return homeFragment;
            case 3:
                return circleFragment;
            case 4:
//                return userFragment;
                return userFragmentV2;
            default:
                return null;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getItemPosition(Object object) {
//        return super.getItemPosition(object);
        return PagerAdapter.POSITION_NONE;
    }
}

