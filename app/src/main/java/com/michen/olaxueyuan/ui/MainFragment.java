package com.michen.olaxueyuan.ui;


import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.SETabBar;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.circle.CircleFragment;
import com.michen.olaxueyuan.ui.course.CourseFragment;
import com.michen.olaxueyuan.ui.examination.ExamFragment;
import com.michen.olaxueyuan.ui.home.HomeFragment;
import com.michen.olaxueyuan.ui.home.data.ChangeIndexEvent;
import com.michen.olaxueyuan.ui.me.UserFragment;
import com.michen.olaxueyuan.ui.question.QuestionFragment;
import com.michen.olaxueyuan.ui.teacher.TeacherHomeFragment;

import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */


public class MainFragment extends Fragment {
    private SETabBar _tabBar;
    private static Activity mActivity;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        setupActionBar();
        register();
        View fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        EventBus.getDefault().register(this);
        _tabBar = (SETabBar) fragmentView.findViewById(R.id.TabBar);

        addChildFragment();

        _tabBar.getItemViewAt(0).setNormalIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_point_normal));
        _tabBar.getItemViewAt(1).setNormalIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_course_normal));
        _tabBar.getItemViewAt(2).setNormalIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_home_selected));
        _tabBar.getItemViewAt(3).setNormalIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_circle_normal));
        _tabBar.getItemViewAt(4).setNormalIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_user_normal));

        _tabBar.getItemViewAt(0).setTitleText("考点");
        _tabBar.getItemViewAt(1).setTitleText("题库");
        _tabBar.getItemViewAt(2).setTitleText("主页");
        _tabBar.getItemViewAt(3).setTitleText("欧拉圈");
        _tabBar.getItemViewAt(4).setTitleText("我的");

        _tabBar.setOnTabSelectionEventListener(new SETabBar.OnTabSelectionEventListener() {
            @Override
            public boolean onWillSelectTab(int tabIndex) {
                return handleOnWillSelectTab(tabIndex);
            }

            @Override
            public void onDidSelectTab(int tabIndex) {
                handleOnDidSelectTab(tabIndex);
            }
        });
        _tabBar.limitTabNum(5);
        return fragmentView;
    }

    private void handleOnDidSelectTab(int tabIndex) {
        switchToPage(tabIndex);
    }

    public void switchToPage(int tabIndex) {
        switch (tabIndex) {
            case 0:
                isTeacher(true);
                break;
            case 1:
                changeFragment(courseFragment, teacherHomeFragment, questionFragment, homeFragment, circleFragment, userFragment);
                break;
            case 2:
                changeFragment(homeFragment, teacherHomeFragment, questionFragment, courseFragment, circleFragment, userFragment);
                break;
            case 3:
                changeFragment(circleFragment, teacherHomeFragment, questionFragment, courseFragment, homeFragment, userFragment);
                break;
            case 4:
                changeFragment(userFragment, teacherHomeFragment, questionFragment, courseFragment, homeFragment, circleFragment);
                break;
            default:
                break;
        }
    }

    /**
     * {@link com.michen.olaxueyuan.ui.home.HomeFragment#chageIndex(int)}
     */
    public void onEventMainThread(ChangeIndexEvent changeIndexEvent) {
        if (changeIndexEvent.isChange) {
            _tabBar.setSelectedTabIndex(changeIndexEvent.position);
        }
    }

    private QuestionFragment questionFragment;
    private ExamFragment examFragment;
    private CourseFragment courseFragment;
    private CircleFragment circleFragment;
    private HomeFragment homeFragment;
    private TeacherHomeFragment teacherHomeFragment;
    private UserFragment userFragment;

    private void addChildFragment() {
        questionFragment = new QuestionFragment();
//        examFragment = new ExamFragment();
        courseFragment = new CourseFragment();
        homeFragment = new HomeFragment();
        circleFragment = new CircleFragment();
//        userFragment = new UserFragment();
        teacherHomeFragment = new TeacherHomeFragment();
        userFragment = new UserFragment();
        changeFragment(homeFragment, teacherHomeFragment, questionFragment, courseFragment, circleFragment, userFragment);
    }

    private void changeFragment(Fragment targetFragment, Fragment... fragments) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction.add(R.id.main_content_view, targetFragment, targetFragment.getClass().getName());
        }
        transaction.show(targetFragment);
        for (int i = 0; i < fragments.length; i++) {
            transaction.hide(fragments[i]);
        }
        transaction.commitAllowingStateLoss();
    }

    private void isTeacher(boolean flag) {
        if (SEAuthManager.getInstance() != null && SEAuthManager.getInstance().getAccessUser() != null
                && SEAuthManager.getInstance().getAccessUser().getIsActive() == 2) {
            if (flag || questionFragment.isVisible()) {
                changeFragment(teacherHomeFragment, questionFragment, courseFragment, homeFragment, circleFragment, userFragment);
            }
        } else {
            if (flag || teacherHomeFragment.isVisible()) {
                changeFragment(questionFragment, teacherHomeFragment, courseFragment, homeFragment, circleFragment, userFragment);
            }
        }
    }

    // EventBus 回调
    public void onEventMainThread(UserLoginNoticeModule module) {
        isTeacher(false);
    }

    private void register() {
        IntentFilter filter1 = new IntentFilter("com.swiftacademy.screen.changed");
        getActivity().registerReceiver(screenReceirver, filter1);

        IntentFilter filter = new IntentFilter("com.swiftacademy.tab.change");
        getActivity().registerReceiver(changeTabReceiver, filter);
    }

    private void unregister() {
        getActivity().unregisterReceiver(changeTabReceiver);
        getActivity().unregisterReceiver(screenReceirver);
    }

    private BroadcastReceiver changeTabReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switchToPage(2);
        }
    };

    // 监听视频横竖屏
    private BroadcastReceiver screenReceirver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean enterFullScreen = intent.getBooleanExtra("enterFullScreen", false);
            if (enterFullScreen) {
                _tabBar.setVisibility(View.GONE);
            } else {
                _tabBar.setVisibility(View.VISIBLE);
            }
        }
    };

    private void setupActionBar() {
        ActionBar actionBar = mActivity.getActionBar();
        try {
            actionBar.getClass().getDeclaredMethod("setShowHideAnimationEnabled", boolean.class).invoke(actionBar, false);
        } catch (Exception exception) {
            // Too bad, the animation will be run ;(
        }
    }

    private void handlePageSelected(int index) {
        _tabBar.setSelectedTabIndex(index);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
//                MWTAuthManager am = MWTAuthManager.getInstance();
//                if (am.isAuthenticated())
//                {
//                    _tabBar.setSelectedTabIndex(4);
//                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void setActionBarVisible(boolean actionBarVisible) {
        ActionBar actionBar = mActivity.getActionBar();
        try {
            actionBar.getClass().getDeclaredMethod("setShowHideAnimationEnabled", boolean.class).invoke(actionBar, false);
        } catch (Exception exception) {
            // Too bad, the animation will be run ;(
        }

        if (actionBarVisible) {
            actionBar.show();
        } else {
            actionBar.hide();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private boolean handleOnWillSelectTab(int tabIndex) {

        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregister();
        EventBus.getDefault().unregister(this);
    }
}

