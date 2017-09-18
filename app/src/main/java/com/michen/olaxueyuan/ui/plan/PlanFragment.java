package com.michen.olaxueyuan.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.SubListView;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.PlanHomeData;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.plan.activity.CompleteScheduleActivity;
import com.michen.olaxueyuan.ui.plan.activity.CorrectRateActivity;
import com.michen.olaxueyuan.ui.plan.activity.SchedulePlanActivity;
import com.michen.olaxueyuan.ui.plan.data.PlanMyCourseAdapter;
import com.michen.olaxueyuan.ui.plan.data.PlanRecommendAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by wangfangming on 2017/9/2.
 */

public class PlanFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener {
	@Bind(R.id.today_study_time_tips)
	TextView todayStudyTimeTips;
	@Bind(R.id.today_study_time)
	TextView todayStudyTime;
	@Bind(R.id.view_study_complete_rate)
	ImageView viewStudyCompleteRate;
	@Bind(R.id.chart_progress)
	BarChart chartProgress;
	@Bind(R.id.study_line)
	View studyLine;
	@Bind(R.id.friend_rank_tips)
	TextView friendRankTips;
	@Bind(R.id.friend_rank)
	TextView friendRank;
	@Bind(R.id.user_avatar)
	RoundRectImageView userAvatar;
	@Bind(R.id.line_customized_top)
	View lineCustomizedTop;
	@Bind(R.id.customized_course_img)
	ImageView customizedCourseImg;
	@Bind(R.id.line_customized_bottom)
	View lineCustomizedBottom;
	@Bind(R.id.my_course)
	TextView myCourse;
	@Bind(R.id.add_course)
	Button addCourse;
	@Bind(R.id.my_course_line)
	View myCourseLine;
	@Bind(R.id.line_my_course_bottom)
	View lineMyCourseBottom;
	@Bind(R.id.daily_recommend)
	TextView dailyRecommend;
	@Bind(R.id.scroll)
	PullToRefreshScrollView scroll;
	@Bind(R.id.my_course_listview)
	SubListView myCourseListview;
	@Bind(R.id.daily_recommend_listview)
	SubListView dailyRecommendListview;
	private View rootView;
	private PlanMyCourseAdapter planMyCourseAdapter;//我的课程
	private PlanRecommendAdapter planRecommendAdapter;//每日推荐

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = View.inflate(getActivity(), R.layout.fragment_plan, null);
		ButterKnife.bind(this, rootView);
		initView();
		queryPlanHomeData();
		return rootView;
	}

	private void initView() {
		scroll.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
		scroll.setOnRefreshListener(this);
		scroll.setRefreshing();
		userAvatar.setRectAdius(100);
		planRecommendAdapter = new PlanRecommendAdapter(getActivity());
		dailyRecommendListview.setAdapter(planRecommendAdapter);
		planMyCourseAdapter = new PlanMyCourseAdapter(getActivity());
		myCourseListview.setAdapter(planMyCourseAdapter);
	}

	private void queryPlanHomeData() {
		String userId = "";
		if (SEAuthManager.getInstance().isAuthenticated()) {
			userId = SEAuthManager.getInstance().getAccessUser().getId();
		}
		userId = "381";
		SEAPP.showCatDialog(this);
		Logger.e("queryPlanHomeData=");
		Logger.e("userId=" + userId);
		QuestionCourseManager.getInstance().queryPlanHomeData(userId, new Callback<PlanHomeData>() {
			@Override
			public void success(PlanHomeData planHomeData, Response response) {
				if (getActivity() != null && !getActivity().isFinishing()) {
					scroll.onRefreshComplete();
					SEAPP.dismissAllowingStateLoss();
					if (planHomeData.getApicode() != 10000) {
						ToastUtil.showToastShort(getActivity(), planHomeData.getMessage());
					} else {
						Logger.e("==" + planHomeData.getResult().toString());
						setData(planHomeData.getResult());
					}
				}
			}

			@Override
			public void failure(RetrofitError error) {
				if (getActivity() != null && !getActivity().isFinishing()) {
					scroll.onRefreshComplete();
					SEAPP.dismissAllowingStateLoss();
					ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
				}
			}
		});
	}

	private void setData(PlanHomeData.ResultBean result) {
		try {
			if (result.getUserList() != null && result.getUserList().size() > 0) {
				PictureUtils.loadAvatar(getActivity(), userAvatar, result.getUserList().get(0).getAvator(), 30);
			}
			planMyCourseAdapter.updateData(result.getCollectionList());
			Logger.e("result.getRecommendList()=" + result.getRecommendList().toString());
			planRecommendAdapter.updateData(result.getRecommendList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	@OnClick({R.id.view_study_complete_rate, R.id.customized_course_img, R.id.add_course})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.view_study_complete_rate:
//				getActivity().startActivity(new Intent(getActivity(), CorrectRateActivity.class));
				getActivity().startActivity(new Intent(getActivity(), CompleteScheduleActivity.class));
				break;
			case R.id.customized_course_img:
				getActivity().startActivity(new Intent(getActivity(), SchedulePlanActivity.class));
				break;
			case R.id.add_course:
				break;
		}
	}

	@Override
	public void onRefresh(PullToRefreshBase refreshView) {
		Logger.e("onRefresh=");
		queryPlanHomeData();
	}
}
