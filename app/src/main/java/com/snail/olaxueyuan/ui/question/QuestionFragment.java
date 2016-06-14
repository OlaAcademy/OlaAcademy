package com.snail.olaxueyuan.ui.question;


import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.TitleManager;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.snail.olaxueyuan.protocol.result.QuestionCourseModule;
import com.snail.olaxueyuan.ui.SuperFragment;
import com.snail.olaxueyuan.ui.adapter.QuestionAdapter;
import com.snail.olaxueyuan.ui.manager.TitlePopManager;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshExpandableListView;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
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
    private ExpandableListView expandableListView;
    QuestionAdapter adapter;
    QuestionCourseModule module;
    TitleManager titleManager;
    private String pid = "1";// 1 数学 2 英语 3 逻辑 4 协作

    public QuestionFragment() {
        // Required empty public constructor
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_question, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        fetchData();
        return rootView;
    }

    private void initView() {
        titleManager = new TitleManager(R.string.math, this, rootView, false);
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

    private void fetchData() {
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        QuestionCourseManager.getInstance().fetchHomeCourseList("126", pid, "1", new Callback<QuestionCourseModule>() {
            @Override
            public void success(QuestionCourseModule questionCourseModule, Response response) {
                SVProgressHUD.dismiss(getActivity());
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv:
                TitlePopManager.getInstance().showPop(getActivity(), titleManager, popLine, this, 1);
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
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        fetchData();
    }
}
