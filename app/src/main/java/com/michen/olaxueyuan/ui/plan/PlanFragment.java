package com.michen.olaxueyuan.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
import com.michen.olaxueyuan.ui.MainFragment;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.circle.CircleFragment;
import com.michen.olaxueyuan.ui.home.data.ChangeIndexEvent;
import com.michen.olaxueyuan.ui.plan.activity.CompleteScheduleActivity;
import com.michen.olaxueyuan.ui.plan.activity.DailyPlanDetailActivity;
import com.michen.olaxueyuan.ui.plan.activity.SchedulePlanActivity;
import com.michen.olaxueyuan.ui.plan.data.PlanMyCourseAdapter;
import com.michen.olaxueyuan.ui.plan.data.PlanRecommendAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
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
	@Bind(R.id.my_course_more)
	ImageView myCourseMore;
	@Bind(R.id.knowledge_count_text)
	TextView knowledgeCountText;
	@Bind(R.id.knowledge_complete_rate)
	TextView knowledgeCompleteRate;
	@Bind(R.id.complete_rate_progress)
	ProgressBar completeRateProgress;
	@Bind(R.id.course_detail_layout)
	LinearLayout courseDetailLayout;
	private View rootView;
	private PlanMyCourseAdapter planMyCourseAdapter;//我的课程
	private PlanRecommendAdapter planRecommendAdapter;//每日推荐
	private List<PlanHomeData.ResultBean.CollectionListBean> courseListThree = new ArrayList<>();
	private List<PlanHomeData.ResultBean.CollectionListBean> courseListAll = new ArrayList<>();
	private PlanHomeData.ResultBean result;

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
		dailyRecommendListview.setFocusable(false);
		myCourseListview.setFocusable(false);
	}

	private void queryPlanHomeData() {
		String userId = "";
		if (SEAuthManager.getInstance().isAuthenticated()) {
			userId = SEAuthManager.getInstance().getAccessUser().getId();
		}
//		userId = "754";
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
						result = planHomeData.getResult();
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
			if (result.getPlan() != null) {
				courseDetailLayout.setVisibility(View.VISIBLE);
				customizedCourseImg.setVisibility(View.GONE);
				knowledgeCountText.setText(result.getPlan().getDay() + "/" + result.getPlan().getTotal() + " | " + result.getPlan().getPointCount() + "个知识点");
				knowledgeCompleteRate.setText("完成" + result.getPlan().getProgress() + "%");
				completeRateProgress.setProgress((int) result.getPlan().getProgress());
			} else {
				courseDetailLayout.setVisibility(View.GONE);
				customizedCourseImg.setVisibility(View.VISIBLE);
			}
			setChart();
			if (result.getUserList() != null && result.getUserList().size() > 0) {
				PictureUtils.loadAvatar(getActivity(), userAvatar, result.getUserList().get(0).getAvator(), 30);
			}
			courseListAll = result.getCollectionList();
			courseListThree.clear();
			if (result.getCollectionList().size() > 3) {
				myCourseMore.setVisibility(View.VISIBLE);
				for (int i = 0; i < result.getCollectionList().size(); i++) {
					if (i < 3) {
						courseListThree.add(result.getCollectionList().get(i));
					}
				}
				planMyCourseAdapter.updateData(courseListThree);
			} else {
				myCourseMore.setVisibility(View.GONE);
				planMyCourseAdapter.updateData(result.getCollectionList());
			}
			Logger.e("result.getRecommendList()=" + result.getRecommendList().toString());
			planRecommendAdapter.updateData(result.getRecommendList());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setChart() {
		chartProgress.getDescription().setEnabled(false);

		// scaling can now only be done on x- and y-axis separately
		chartProgress.setPinchZoom(false);

		chartProgress.setDrawBarShadow(false);
		chartProgress.setDrawGridBackground(false);
		chartProgress.setMaxVisibleValueCount(100);
		XAxis xAxis = chartProgress.getXAxis();
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
		xAxis.setDrawGridLines(false);

		chartProgress.getAxisLeft().setDrawGridLines(false);

		chartProgress.getLegend().setEnabled(false);

		setChartData(7);
		chartProgress.setFitBars(true);
	}

	private void setChartData(int count) {
		ArrayList<BarEntry> yVals = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			float val = (float) (Math.random() * count) + 50;
			yVals.add(new BarEntry(i, (int) val));
		}
		BarDataSet set = new BarDataSet(yVals, "Data Set");
		set.setColors(R.color.color_4A4F5E);
		set.setDrawValues(false);
		BarData data = new BarData(set);
		chartProgress.setData(data);
		chartProgress.invalidate();
		chartProgress.animateY(800);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	private boolean isShowMoreCourse = false;

	@OnClick({R.id.view_study_complete_rate, R.id.customized_course_img, R.id.add_course
			, R.id.my_course_more, R.id.course_detail_layout})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.view_study_complete_rate:
//				getActivity().startActivity(new Intent(getActivity(), CorrectRateActivity.class));
				getActivity().startActivity(new Intent(getActivity(), CompleteScheduleActivity.class));
				break;
			case R.id.course_detail_layout:
				if (result.getPlan() != null) {
					getActivity().startActivity(new Intent(getActivity(), DailyPlanDetailActivity.class));
				}
				break;
			case R.id.customized_course_img:
				getActivity().startActivity(new Intent(getActivity(), SchedulePlanActivity.class));
				break;
			case R.id.add_course:
				getActivity().startActivity(new Intent(getActivity(), SchedulePlanActivity.class));
				chageIndex(1);
				break;
			case R.id.my_course_more:
				if (isShowMoreCourse) {
					isShowMoreCourse = false;
					planMyCourseAdapter.updateData(courseListAll);
					myCourseMore.setImageResource(R.drawable.top_arrow_plan);
				} else {
					isShowMoreCourse = true;
					planMyCourseAdapter.updateData(courseListThree);
					myCourseMore.setImageResource(R.drawable.bottom_arrow_plan);
				}
				break;
		}
	}

	@Override
	public void onRefresh(PullToRefreshBase refreshView) {
		Logger.e("onRefresh=");
		queryPlanHomeData();
	}

	private void chageIndex(int position) {
		/**
		 *{@link MainFragment#onEventMainThread(ChangeIndexEvent)}
		 * {@link CircleFragment#onEventMainThread(ChangeIndexEvent)}
		 */
		EventBus.getDefault().post(new ChangeIndexEvent(position, true));
	}

	@OnClick(R.id.course_detail_layout)
	public void onViewClicked() {
	}
}
