package com.michen.olaxueyuan.ui.plan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.UserPlanDetailResult;
import com.michen.olaxueyuan.ui.activity.SuperActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 完成课程定制
 */
public class CompleteScheduleActivity extends SuperActivity {
	@Bind(R.id.today_complete_rate)
	TextView todayCompleteRate;
	@Bind(R.id.complete_rate_progress)
	ProgressBar completeRateProgress;
	@Bind(R.id.examListView)
	ExpandableListView examListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complete_schedule);
		ButterKnife.bind(this);
	}

	@Override
	public void initView() {
		getUserPlanDetail();
	}

	private void getUserPlanDetail() {
		String userId = "";
		if (SEAuthManager.getInstance().isAuthenticated()) {
			userId = SEAuthManager.getInstance().getAccessUser().getId();
		}
		userId = "381";
		SEAPP.showCatDialog(this);
		QuestionCourseManager.getInstance().getUserPlanDetail(userId, "2017-09-13", new Callback<UserPlanDetailResult>() {
			@Override
			public void success(UserPlanDetailResult userPlanDetailResult, Response response) {
				if (mContext != null && !CompleteScheduleActivity.this.isFinishing()) {
					SEAPP.dismissAllowingStateLoss();
					if (userPlanDetailResult.getApicode() != 10000) {
						ToastUtil.showToastShort(mContext, userPlanDetailResult.getMessage());
					} else {
						Logger.e("==" + userPlanDetailResult.getResult().toString());
						setData(userPlanDetailResult.getResult());
					}
				}
			}

			@Override
			public void failure(RetrofitError error) {
				if (mContext != null && !CompleteScheduleActivity.this.isFinishing()) {
					SEAPP.dismissAllowingStateLoss();
					ToastUtil.showToastShort(mContext, R.string.data_request_fail);
				}
			}
		});
	}

	private void setData(UserPlanDetailResult.ResultBean result) {

	}

	@Override
	public void initData() {

	}

	@Override
	public void onClick(View v) {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
	}
}
