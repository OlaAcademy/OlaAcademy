package com.michen.olaxueyuan.ui.question;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.eventbusmodule.MessageReadEvent;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.MessageUnReadResult;
import com.michen.olaxueyuan.protocol.result.QuestionCourseModule;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.adapter.QuestionAdapter;
import com.michen.olaxueyuan.ui.manager.TitlePopManager;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshExpandableListView;
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
public class QuestionFragment extends SuperFragment implements TitlePopManager.PidClickListener, PullToRefreshBase.OnRefreshListener {
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.question_name)
    TextView questionName;
    @Bind(R.id.expandableListView)
    PullToRefreshExpandableListView expandableListViews;
    @Bind(R.id.pop_line)
    View popLine;
    @Bind(R.id.red_dot)
    TextView redDot;
    private ExpandableListView expandableListView;
    QuestionAdapter adapter;
    QuestionCourseModule module;
    TitleManager titleManager;
    private String pid = "1";// 1 数学 2 英语 3 逻辑 4 协作
    private int unReadMessageCount = 0;

    public QuestionFragment() {
        // Required empty public constructor
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        rootView = inflater.inflate(R.layout.fragment_question, container, false);
        rootView = View.inflate(getActivity(),R.layout.fragment_question, null);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        initView();
        fetchData();
        getUnReadMessageCount();
        return rootView;
    }

    private void initView() {
        titleManager = new TitleManager(R.string.math, this, rootView, false);
        titleManager.changeImageRes(TitleManager.RIGHT_INDEX_RESPONSE, R.drawable.message_tip_icon);
        Drawable drawable = getResources().getDrawable(R.drawable.title_down_nromal);
        drawable.setBounds(10, 0, drawable.getMinimumWidth() + 10, drawable.getMinimumHeight());
        titleManager.title_tv.setCompoundDrawables(null, null, drawable, null);
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
    }

    // EventBus 回调
    public void onEventMainThread(UserLoginNoticeModule module) {
        fetchData();
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

    private void fetchData() {
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        QuestionCourseManager.getInstance().fetchHomeCourseList(userId, pid, "1", new Callback<QuestionCourseModule>() {
            @Override
            public void success(QuestionCourseModule questionCourseModule, Response response) {
                SVProgressHUD.dismiss(getActivity());
                questionName.setText(questionCourseModule.getResult().getProfile());
                expandableListViews.onRefreshComplete();
//                Logger.json(questionCourseModule);
                if (questionCourseModule.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(getActivity(), questionCourseModule.getMessage(), 2.0f);
                } else {
                    module = questionCourseModule;
                    adapter.updateList(module);
                    expandableListView.setFocusable(false);
//                    for (int i = 0; i < module.getResult().getChild().size(); i++) {
//                        expandableListView.expandGroup(i);
//                    }
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

    @OnClick({R.id.title_tv, R.id.right_response, R.id.red_dot})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_tv:
                TitlePopManager.getInstance().showPop(getActivity(), titleManager, popLine, this, 1);
                break;
            case R.id.right_response:
            case R.id.red_dot:
                if (!SEAuthManager.getInstance().isAuthenticated()) {
                    Intent loginIntent = new Intent(getActivity(), UserLoginActivity.class);
                    startActivity(loginIntent);
                    return;
                }
                startActivity(new Intent(getActivity(), MessageActivity.class));
                break;
        }
    }

    @Override
    public void pidPosition(int type, String pid) {
        if (type == 1) {
            this.pid = pid;
            fetchData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        fetchData();
    }

    private void getUnReadMessageCount() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        QuestionCourseManager.getInstance().getUnreadCount(userId, new Callback<MessageUnReadResult>() {
            @Override
            public void success(MessageUnReadResult messageUnReadResult, Response response) {
                if (messageUnReadResult.getApicode() == 10000) {
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

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

}
