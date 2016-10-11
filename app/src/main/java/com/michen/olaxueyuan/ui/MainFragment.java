package com.michen.olaxueyuan.ui;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.SETabBar;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.CheckInResult;
import com.michen.olaxueyuan.protocol.result.CheckinStatusResult;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.circle.CircleFragment;
import com.michen.olaxueyuan.ui.course.CourseFragment;
import com.michen.olaxueyuan.ui.examination.ExamFragment;
import com.michen.olaxueyuan.ui.home.HomeFragment;
import com.michen.olaxueyuan.ui.teacher.TeacherHomeFragment;
import com.michen.olaxueyuan.ui.home.data.ChangeIndexEvent;
import com.michen.olaxueyuan.ui.me.UserFragment;
import com.michen.olaxueyuan.ui.me.UserFragmentV2;
import com.michen.olaxueyuan.ui.question.QuestionFragment;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
        getCheckinStatus(false);
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
                changeFragment(courseFragment, teacherHomeFragment, questionFragment, homeFragment, circleFragment, userFragmentV2);
                break;
            case 2:
                changeFragment(homeFragment, teacherHomeFragment, questionFragment, courseFragment, circleFragment, userFragmentV2);
                break;
            case 3:
                changeFragment(circleFragment, teacherHomeFragment, questionFragment, courseFragment, homeFragment, userFragmentV2);
                break;
            case 4:
                changeFragment(userFragmentV2, teacherHomeFragment, questionFragment, courseFragment, homeFragment, circleFragment);
                getCheckinStatus(true);
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
    private UserFragment userFragment;
    private TeacherHomeFragment teacherHomeFragment;
    private UserFragmentV2 userFragmentV2;

    private void addChildFragment() {
        questionFragment = new QuestionFragment();
//        examFragment = new ExamFragment();
        courseFragment = new CourseFragment();
        homeFragment = new HomeFragment();
        circleFragment = new CircleFragment();
//        userFragment = new UserFragment();
        teacherHomeFragment = new TeacherHomeFragment();
        userFragmentV2 = new UserFragmentV2();
        changeFragment(homeFragment, teacherHomeFragment, questionFragment, courseFragment, circleFragment, userFragmentV2);
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
                changeFragment(teacherHomeFragment, questionFragment, courseFragment, homeFragment, circleFragment, userFragmentV2);
            }
        } else {
            if (flag || teacherHomeFragment.isVisible()) {
                changeFragment(questionFragment, teacherHomeFragment, courseFragment, homeFragment, circleFragment, userFragmentV2);
            }
        }
        if (userFragmentV2.isVisible()) {
            getCheckinStatus(true);
        } else {
            getCheckinStatus(false);
        }
    }

    // EventBus 回调
    public void onEventMainThread(UserLoginNoticeModule module) {
        isTeacher(false);
    }

    /**
     * @param sign 是否要调用签到的接口
     */
    private void getCheckinStatus(final boolean sign) {
        String userId = "";
        final SharedPreferences preference = getActivity().getSharedPreferences("dot", Context.MODE_PRIVATE);
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            _tabBar.getItemViewAt(4).hideRedDot();
            preference.edit().putLong("time", 0).apply();
            return;
        }
        long time = preference.getLong("time", 0);
        String pUserId = preference.getString("userId", "");
        boolean isSigned = preference.getBoolean("isSigned", false);
        //距离上次访问签到接口8小时或者保存的userid和当前不符,或者没有签到
        if (System.currentTimeMillis() - time > 8 * 60 * 60 * 1000 || !userId.equals(pUserId) || !isSigned) {
            preference.edit().putLong("time", System.currentTimeMillis()).apply();
            preference.edit().putString("userId", userId).apply();
            SEUserManager.getInstance().getCheckinStatus(userId, new Callback<CheckinStatusResult>() {
                @Override
                public void success(CheckinStatusResult checkinStatusResult, Response response) {
                    if (getActivity() != null) {
                        if (checkinStatusResult.getApicode() == 10000) {
                            if (checkinStatusResult.getResult().getStatus() == 1) {
                                preference.edit().putBoolean("isSigned", true).apply();
                                _tabBar.getItemViewAt(4).hideRedDot();
                                if (isShowSignDialog) {
                                    /**
                                     * {@link UserFragmentV2#onEventMainThread(CheckinStatusResult)}
                                     */
                                    EventBus.getDefault().post(checkinStatusResult);
                                    isShowSignDialog = false;
                                }
                            } else {
                                preference.edit().putBoolean("isSigned", false).apply();
                                _tabBar.getItemViewAt(4).showRedDot();
                                if (sign) {//调用签到接口
                                    signIn();
                                }
                            }
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        }
    }

    boolean isShowSignDialog;//是否显示签到的dialog

    private void signIn() {//签到
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            return;
        }
        SEUserManager.getInstance().checkin(userId, new Callback<CheckInResult>() {
            @Override
            public void success(CheckInResult checkInResult, Response response) {
                if (getActivity() != null) {
                    if (checkInResult.getApicode() == 10000) {
                        isShowSignDialog = true;
                        getCheckinStatus(false);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
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

