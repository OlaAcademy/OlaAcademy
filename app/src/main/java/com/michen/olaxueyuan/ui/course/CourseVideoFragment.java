package com.michen.olaxueyuan.ui.course;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SECourseManager;
import com.michen.olaxueyuan.protocol.model.MCSubCourse;
import com.michen.olaxueyuan.protocol.result.MCCourseListResult;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.manager.TitlePopManager;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CourseVideoFragment extends SuperFragment implements TitlePopManager.PidClickListener {
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.pop_line)
    View popLine;
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
    @Bind(R.id.interview_text)
    TextView interviewText;
    @Bind(R.id.interview_indicator)
    View interviewIndicator;
    @Bind(R.id.interview_layout)
    RelativeLayout interviewLayout;
    @Bind(R.id.recommend_title)
    TextView recommendTitle;
    @Bind(R.id.recommend_intro)
    TextView recommendIntro;
    @Bind(R.id.recommend_layout)
    RelativeLayout recommendLayout;
    @Bind(R.id.charge_title)
    TextView chargeTitle;
    @Bind(R.id.charge_intro)
    TextView chargeIntro;
    @Bind(R.id.charge_layout)
    RelativeLayout chargeLayout;

    private PullToRefreshListView courseListView;
    private CourseAdapter adapter;
    private ArrayList<MCSubCourse> courseArrayList;
    View mMainView;
    TitleManager titleManager;
    private String pid = "1";// 1 数学 2 英语 3 逻辑 4 协作 5 面试

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_course_video, container, false);
        ButterKnife.bind(this, mMainView);

        setupNavBar();

        courseListView = (PullToRefreshListView) mMainView.findViewById(R.id.infoListView);
        courseListView.getRefreshableView().setDivider(null);
        adapter = new CourseAdapter(getActivity());
        courseListView.setAdapter(adapter);
        mathsText.setSelected(true);
        mathsIndicator.setSelected(true);
        performRefresh();
        courseListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                performRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
            }
        });

        return mMainView;
    }

    private void setupNavBar() {
        titleManager = new TitleManager(R.string.course_database, this, mMainView, false);
    }

    private void performRefresh() {
        SECourseManager courseManager = SECourseManager.getInstance();
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        SEAPP.showCatDialog(this);
        courseManager.getVideoCourseList(userId, pid, "1", new Callback<MCCourseListResult>() {
            @Override
            public void success(MCCourseListResult result, Response response) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    if (!result.apicode.equals("10000")) {
                        SVProgressHUD.showInViewWithoutIndicator(getActivity(), result.message, 2.0f);
                    } else {
                        courseArrayList = result.course.courseArrayList;
                        if (courseArrayList != null) {
                            adapter.updateData(courseArrayList);
                        } else {
                            ToastUtil.showShortToast(getActivity(), "课程在路上，敬请期待...");
                        }
                    }
                    courseListView.onRefreshComplete();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    courseListView.onRefreshComplete();
                    SEAPP.dismissAllowingStateLoss();
                }
            }
        });
    }

    @OnClick({R.id.maths_layout, R.id.english_layout, R.id.logic_layout, R.id.writing_layout
            , R.id.interview_layout, R.id.recommend_layout, R.id.charge_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.maths_layout:
                changeTab(true, false, false, false, false, 0);
                break;
            case R.id.english_layout:
                changeTab(false, true, false, false, false, 1);
                break;
            case R.id.logic_layout:
                changeTab(false, false, true, false, false, 2);
                break;
            case R.id.writing_layout:
                changeTab(false, false, false, true, false, 3);
                break;
            case R.id.interview_layout:
                changeTab(false, false, false, false, true, 4);
                break;
            case R.id.recommend_layout:
                break;
            case R.id.charge_layout:
                break;
        }
    }

    private void changeTab(boolean maths, boolean english, boolean logic, boolean writing, boolean interview, int position) {
        mathsText.setSelected(maths);
        mathsIndicator.setSelected(maths);
        englishText.setSelected(english);
        englishIndicator.setSelected(english);
        logicText.setSelected(logic);
        logicIndicator.setSelected(logic);
        writingText.setSelected(writing);
        writingIndicator.setSelected(writing);
        interviewText.setSelected(interview);
        interviewIndicator.setSelected(interview);
        this.pid = String.valueOf(position + 1);
        performRefresh();
    }

    @Override
    public void pidPosition(int type, String pid) {
        if (type == 3) {
            this.pid = pid;
            performRefresh();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SEAPP.dismissAllowingStateLoss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}

