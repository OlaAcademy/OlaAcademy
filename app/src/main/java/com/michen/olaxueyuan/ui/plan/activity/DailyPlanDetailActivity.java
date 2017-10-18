package com.michen.olaxueyuan.ui.plan.activity;

import android.os.Bundle;
import android.view.View;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.UserPlanFinishedDetailResult;
import com.michen.olaxueyuan.ui.activity.SuperActivity;
import com.michen.olaxueyuan.ui.plan.data.DailyPlanDetailAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 计划完成每日详情
 */
public class DailyPlanDetailActivity extends SuperActivity implements PullToRefreshBase.OnRefreshListener {
	@Bind(R.id.listView)
	PullToRefreshListView listView;
	private DailyPlanDetailAdapter adapter;
	TitleManager titleManager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daily_plan_detail);
		ButterKnife.bind(this);
	}

	@Override
	public void initView() {
		titleManager = new TitleManager(this, "学习历史", this, true);
		listView.getRefreshableView().setDivider(null);
		listView.setOnRefreshListener(this);
		listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
		adapter = new DailyPlanDetailAdapter(mContext);
		listView.setAdapter(adapter);
		listView.setRefreshing();
		getPlanFinishedDetailList();
	}

	@Override
	public void initData() {

	}

	@OnClick({R.id.left_return})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.left_return:
				finish();
				break;
		}
	}

	private void getPlanFinishedDetailList() {
		String userId = "";
		if (SEAuthManager.getInstance().isAuthenticated()) {
			userId = SEAuthManager.getInstance().getAccessUser().getId();
		}
//		userId = "754";
		SEAPP.showCatDialog(this);
		QuestionCourseManager.getInstance().getPlanFinishedDetailList(userId, new Callback<UserPlanFinishedDetailResult>() {
			@Override
			public void success(UserPlanFinishedDetailResult userPlanFinishedDetailResult, Response response) {
				if (mContext != null && !DailyPlanDetailActivity.this.isFinishing()) {
					SEAPP.dismissAllowingStateLoss();
					if (userPlanFinishedDetailResult.getApicode() != 10000) {
						ToastUtil.showToastShort(mContext, userPlanFinishedDetailResult.getMessage());
					} else {
						adapter.updateData(userPlanFinishedDetailResult.getResult().getDailyList());
					}
				}
			}

			@Override
			public void failure(RetrofitError error) {
				if (mContext != null && !DailyPlanDetailActivity.this.isFinishing()) {
					SEAPP.dismissAllowingStateLoss();
					ToastUtil.showToastShort(mContext, R.string.data_request_fail);
				}
			}
		});
	}

	@Override
	public void onRefresh(PullToRefreshBase refreshView) {
		getPlanFinishedDetailList();
	}
}
