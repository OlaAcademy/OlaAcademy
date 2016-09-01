package com.michen.olaxueyuan.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.ui.circle.CircleFragment;
import com.michen.olaxueyuan.ui.course.CourseFragment;
import com.michen.olaxueyuan.ui.examination.ExamFragment;
import com.michen.olaxueyuan.ui.home.HomeFragment;
import com.michen.olaxueyuan.ui.home.TeacherHomeFragment;
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
    private TeacherHomeFragment teacherHomeFragment;

    public MainPagerAdapter(FragmentManager fm) {
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
        userFragment = new UserFragment();
        teacherHomeFragment = new TeacherHomeFragment();
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                if (SEAuthManager.getInstance() != null && SEAuthManager.getInstance().getAccessUser() != null
                        && SEAuthManager.getInstance().getAccessUser().getIsActive() == 2) {
                    return teacherHomeFragment;
                } else {
                    return questionFragment;
                }
            case 1:
                return courseFragment;
            case 2:
                return homeFragment;
            case 3:
                return circleFragment;
            case 4:
                return userFragment;
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
        return POSITION_NONE;
    }
}

