package com.michen.olaxueyuan.ui.question;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.DialogUtils;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.event.MessageReadEvent;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.CheckinStatusResult;
import com.michen.olaxueyuan.protocol.result.ExamModule;
import com.michen.olaxueyuan.protocol.result.MessageUnReadResult;
import com.michen.olaxueyuan.protocol.result.QuestionCourseModule;
import com.michen.olaxueyuan.protocol.result.UnlockSubjectResult;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.adapter.QuestionAdapter;
import com.michen.olaxueyuan.ui.adapter.QuestionListViewAdapter;
import com.michen.olaxueyuan.ui.adapter.QuestionViewPagerAdapter;
import com.michen.olaxueyuan.ui.me.activity.BuyVipActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.michen.olaxueyuan.ui.question.module.BottomPopWindowManager;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshExpandableListView;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener, QuestionListViewAdapter.SelectBuyCourse {
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.expandableListView)
    PullToRefreshExpandableListView expandableListViews;
    @Bind(R.id.pop_line)
    View popLine;
    @Bind(R.id.red_dot)
    TextView redDot;
    @Bind(R.id.maths_text)
    TextView mathsText;
    @Bind(R.id.maths_indicator)
    View mathsIndicator;
    @Bind(R.id.maths_layout)
    RelativeLayout mathsLayout;
    @Bind(R.id.english_text)
    TextView englishText;
    @Bind(R.id.english_indicator)
    View englishIndicator;
    @Bind(R.id.english_layout)
    RelativeLayout englishLayout;
    @Bind(R.id.logic_text)
    TextView logicText;
    @Bind(R.id.logic_indicator)
    View logicIndicator;
    @Bind(R.id.logic_layout)
    RelativeLayout logicLayout;
    @Bind(R.id.writing_text)
    TextView writingText;
    @Bind(R.id.writing_indicator)
    View writingIndicator;
    @Bind(R.id.writing_layout)
    RelativeLayout writingLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.subject_name)
    TextView subjectName;
    @Bind(R.id.subject_layout)
    RelativeLayout subjectLayout;
    @Bind(R.id.listview_question)
    PullToRefreshListView listview;
    private ExpandableListView expandableListView;
    QuestionAdapter adapter;
    QuestionCourseModule module;
    TitleManager titleManager;
    private String pid = "1";// 1 数学 2 英语 3 逻辑 4 协作
    private int unReadMessageCount = 0;
    private QuestionViewPagerAdapter viewPagerAdapter;
    private int selectType = 0;//三个条件0,1,2
    private String[] selectArray = {};//
    private QuestionListViewAdapter questionListViewAdapter;

    public QuestionFragment() {
        // Required empty public constructor
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        rootView = inflater.inflate(R.layout.fragment_question, container, false);
        if (container == null) {
            return null;
        } else {
            rootView = View.inflate(getActivity(), R.layout.fragment_question, null);
            ButterKnife.bind(this, rootView);
            EventBus.getDefault().register(this);
            initView();
            fetchHomeCourseData();
            getUnReadMessageCount();
            return rootView;
        }
    }

    private void initView() {
        selectArray = getActivity().getResources().getStringArray(R.array.question_select_maths);
        titleManager = new TitleManager("考点", this, rootView, false);
        titleManager.changeImageRes(TitleManager.RIGHT_INDEX_RESPONSE, R.drawable.message_tip_icon);
        expandableListViews.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        expandableListViews.setOnRefreshListener(this);
        expandableListView = expandableListViews.getRefreshableView();
        expandableListView.setDivider(null);

        expandableListView.setGroupIndicator(null);
        adapter = new QuestionAdapter(getActivity());
        expandableListView.setAdapter(adapter);
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                if (module != null) {
                    module.getResult().getChild().get(groupPosition).setIsExpanded(false);
                    adapter.updateList(module);
                }
            }
        });
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (module != null) {
                    module.getResult().getChild().get(groupPosition).setIsExpanded(true);
                    adapter.updateList(module);
                }
            }
        });
        listview.setOnRefreshListener(this);
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.getRefreshableView().setDivider(null);
        questionListViewAdapter = new QuestionListViewAdapter(getActivity(), this);
        listview.setAdapter(questionListViewAdapter);

        FragmentManager fragmentManager;
        if (Build.VERSION.SDK_INT >= 17) {
            fragmentManager = getChildFragmentManager();
        } else {
            fragmentManager = getFragmentManager();
        }
        viewPagerAdapter = new QuestionViewPagerAdapter(fragmentManager);
        viewPager.setAdapter(viewPagerAdapter);
        mathsText.setSelected(true);
        mathsIndicator.setSelected(true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        changeTab(true, false, false, false, false, 0);
                        break;
                    case 1:
                        changeTab(false, true, false, false, false, 1);
                        break;
                    case 2:
                        changeTab(false, false, true, false, false, 2);
                        break;
                    case 3:
                        changeTab(false, false, false, true, false, 3);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    // EventBus 回调
    public void onEventMainThread(UserLoginNoticeModule module) {
        if (redDot == null) {
            return;
        }
        if (selectType == 0) {
            fetchHomeCourseData();
        } else {
            fetchExamListData();
        }
        getUnReadMessageCount();
    }

    /**
     * {@link MessageActivity#onEventMainThread(MessageReadEvent)}*
     */
    public void onEventMainThread(MessageReadEvent event) {
        if (event.isRefresh) {
            getUnReadMessageCount();
        }
    }


    @OnClick({R.id.right_response, R.id.red_dot, R.id.maths_layout, R.id.english_layout
            , R.id.logic_layout, R.id.writing_layout, R.id.subject_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_response:
            case R.id.red_dot:
                if (!SEAuthManager.getInstance().isAuthenticated()) {
                    Intent loginIntent = new Intent(getActivity(), UserLoginActivity.class);
                    startActivity(loginIntent);
                    return;
                }
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
            case R.id.maths_layout:
                changeTab(true, false, false, false, true, 0);
                break;
            case R.id.english_layout:
                changeTab(false, true, false, false, true, 1);
                break;
            case R.id.logic_layout:
                changeTab(false, false, true, false, true, 2);
                break;
            case R.id.writing_layout:
                changeTab(false, false, false, true, true, 3);
                break;
            case R.id.subject_layout:
                DialogUtils.showSelectQuestionDialog(getActivity(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.select_one:
                                selectType = 0;
                                fetchHomeCourseData();
                                break;
                            case R.id.select_two:
                                selectType = 1;
                                fetchExamListData();
                                break;
                            case R.id.select_three:
                                selectType = 2;
                                fetchExamListData();
                                break;
                        }
                        subjectName.setText(selectArray[selectType]);
                    }
                }, selectType, selectArray);
                break;
        }
    }

    private void changeTab(boolean maths, boolean english, boolean logic, boolean writing, boolean setCurrentItem, int position) {
        mathsText.setSelected(maths);
        mathsIndicator.setSelected(maths);
        englishText.setSelected(english);
        englishIndicator.setSelected(english);
        logicText.setSelected(logic);
        logicIndicator.setSelected(logic);
        writingText.setSelected(writing);
        writingIndicator.setSelected(writing);
        if (setCurrentItem) {
            viewPager.setCurrentItem(position);
        }
        switch (position) {
            case 1:
                selectArray = getActivity().getResources().getStringArray(R.array.question_select_english);
                break;
            case 2:
                selectArray = getActivity().getResources().getStringArray(R.array.question_select_logic);
                break;
            case 3:
                selectArray = getActivity().getResources().getStringArray(R.array.question_select_writing);
                break;
            case 0:
            default:
                selectArray = getActivity().getResources().getStringArray(R.array.question_select_maths);
                break;
        }
        subjectName.setText(selectArray[0]);
        this.pid = String.valueOf(position + 1);
        selectType = 0;
        fetchHomeCourseData();
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        if (refreshView == listview) {
            fetchExamListData();
        }
        if (refreshView == expandableListViews) {
            fetchHomeCourseData();
        }
    }


    private void fetchHomeCourseData() {
        expandableListViews.setVisibility(View.VISIBLE);
        listview.setVisibility(View.GONE);
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        QuestionCourseManager.getInstance().fetchHomeCourseList(userId, pid, "1", new Callback<QuestionCourseModule>() {
            @Override
            public void success(QuestionCourseModule questionCourseModule, Response response) {
                if (getActivity() != null) {
                    SVProgressHUD.dismiss(getActivity());
                    expandableListViews.onRefreshComplete();
//                Logger.json(questionCourseModule);
                    if (questionCourseModule.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(getActivity(), questionCourseModule.getMessage(), 2.0f);
                    } else {
                        module = questionCourseModule;
                        adapter.updateList(module);
                        expandableListView.setFocusable(false);
                        /**
                         * {@link QuestionHomeWorkFragment#onEventMainThread(QuestionCourseModule)}
                         */
                        EventBus.getDefault().post(questionCourseModule);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    expandableListViews.onRefreshComplete();
                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                }
            }
        });
    }

    private void fetchExamListData() {
        expandableListViews.setVisibility(View.GONE);
        listview.setVisibility(View.VISIBLE);
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        String userId = "";
        SEAuthManager am = SEAuthManager.getInstance();
        if (am.isAuthenticated()) {
            userId = am.getAccessUser().getId();
        }
        QuestionCourseManager.getInstance().getExamList(userId, pid, String.valueOf(selectType), new Callback<ExamModule>() {
            @Override
            public void success(ExamModule examModule, Response response) {
                if (getActivity() != null) {
                    SVProgressHUD.dismiss(getActivity());
                    listview.onRefreshComplete();
//                Logger.json(examModule);
                    if (examModule.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(getActivity(), examModule.getMessage(), 2.0f);
                    } else {
                        questionListViewAdapter.updateData(examModule.getResult(), Integer.parseInt(pid));
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    listview.onRefreshComplete();
                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                }
            }
        });
    }

    private void getUnReadMessageCount() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            return;
        }
        QuestionCourseManager.getInstance().getUnreadCount(userId, new Callback<MessageUnReadResult>() {
            @Override
            public void success(MessageUnReadResult messageUnReadResult, Response response) {
                if (messageUnReadResult.getApicode() == 10000) {
                    if (getActivity() != null) {
                        unReadMessageCount = messageUnReadResult.getResult();
                        redDot.setText(String.valueOf(unReadMessageCount));
                        if (unReadMessageCount > 0) {
                            redDot.setVisibility(View.VISIBLE);
                        } else {
                            redDot.setVisibility(View.GONE);
                        }
                        Logger.e("unReadMessageCount==" + unReadMessageCount);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    @Override
    public void buyCourse(boolean isBuy, final int objectId, final int type) {
        if (isBuy) {
            BottomPopWindowManager.getInstance().showBottomPop(getActivity(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.item_popupwindows_camera:
                            buyVip();
                            break;
                        case R.id.item_popupwindows_Photo:
                            showExchangeDialog(objectId, type);
                            break;
                        case R.id.item_popupwindows_cancel:
                            break;
                    }
                }
            }, "购买会员", "积分兑换", "").showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
        }
    }

    private void buyVip() {
        if (getActivity() == null) {
            return;
        }
        if (SEAuthManager.getInstance().isAuthenticated()) {
            getActivity().startActivity(new Intent(getActivity(), BuyVipActivity.class));
        } else {
            getActivity().startActivity(new Intent(getActivity(), UserLoginActivity.class));
        }
    }

    private void showExchangeDialog(final int objectId, final int type) {
        if (getActivity() == null) {
            return;
        }
        if (!SEAuthManager.getInstance().isAuthenticated()) {
            getActivity().startActivity(new Intent(getActivity(), UserLoginActivity.class));
        } else {
            DialogUtils.showDialog(getActivity(), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.yes:
                            getCheckinStatus(objectId, type);
                            break;
                    }
                }
            },"", getActivity().getString(R.string.pay_twenty_coin), getActivity().getString(R.string.exchange), "");
        }
    }

    String userId = "";

    private void getCheckinStatus(final int objectId, final int type) {
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            getActivity().startActivity(new Intent(getActivity(), UserLoginActivity.class));
            return;
        }
        SVProgressHUD.showInView(getActivity(), getActivity().getString(R.string.get_coin_ing), true);
        SEUserManager.getInstance().getCheckinStatus(userId, new Callback<CheckinStatusResult>() {
            @Override
            public void success(CheckinStatusResult checkinStatusResult, Response response) {
                if (getActivity() != null) {
                    SVProgressHUD.dismiss(getActivity());
                    if (checkinStatusResult.getApicode() == 10000) {
                        if (checkinStatusResult.getResult().getCoin() >= 20) {
                            unlockSubject(userId, objectId, type);
                        } else {
                            DialogUtils.showDialog(getActivity(), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    switch (v.getId()) {
                                        case R.id.yes:
                                            buyVip();
                                            break;
                                    }
                                }
                            },"", "您的欧拉币余额不足，使用其它方式？", "购买会员", "");
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), "获取积分失败,请重试");
                }
            }
        });
    }

    private void unlockSubject(String userId, int objectId, final int type) {
        SVProgressHUD.showInView(getActivity(), "正在兑换，请稍后", true);
        QuestionCourseManager.getInstance().unlockSubject(userId, String.valueOf(objectId), String.valueOf(type), new Callback<UnlockSubjectResult>() {
            @Override
            public void success(UnlockSubjectResult unlockSubjectResult, Response response) {
                if (getActivity() != null) {
                    SVProgressHUD.dismiss(getActivity());
                    if (unlockSubjectResult.getApicode() == 10000) {
                        ToastUtil.showToastShort(getActivity(), "兑换成功");
                        if (type == 1) {
                            onRefresh(expandableListViews);
                        } else {
                            onRefresh(listview);
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), "兑换失败,请重试");
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
