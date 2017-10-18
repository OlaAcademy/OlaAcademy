package com.michen.olaxueyuan.ui.plan.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loonggg.weekcalendar.view.WeekCalendar;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.AndUtil;
import com.michen.olaxueyuan.common.manager.DateUtils;
import com.michen.olaxueyuan.common.manager.SystemBarTintManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.protocol.result.UserPlanDetailResult;
import com.michen.olaxueyuan.ui.activity.SuperActivity;
import com.michen.olaxueyuan.ui.adapter.CompleteScheduleAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
	@Bind(R.id.expandableListView)
	ExpandableListView expandableListView;
	@Bind(R.id.left_close)
	ImageView leftClose;
	@Bind(R.id.day_title)
	TextView dayTitle;
	@Bind(R.id.right_share)
	ImageView rightShare;
	@Bind(R.id.system_status_inflate_view)
	View systemStatusInflateView;
	@Bind(R.id.week_calendar)
	WeekCalendar weekCalendar;
	private CompleteScheduleAdapter adapter;
	private String currentTime;
	List<String> list = new ArrayList<>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complete_schedule);
		setInflateView();
		setStatusStyle(); //设置状态栏透明度
		addFlags();
		ButterKnife.bind(this);
	}

	private void setInflateView() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			systemStatusInflateView.setVisibility(View.GONE);
		} else {
			//snackBar显示在系统状态栏的下方，用view填充系统状态栏的高度
			systemStatusInflateView.setVisibility(View.VISIBLE);
			ViewGroup.LayoutParams layoutParams = systemStatusInflateView.getLayoutParams();
			layoutParams.height = AndUtil.getStatusBarHeight();
			systemStatusInflateView.setLayoutParams(layoutParams);
		}
	}

	@Override
	public void initView() {
		expandableListView.setDivider(null);
		expandableListView.setGroupIndicator(null);
		expandableListView.setItemsCanFocus(false);
		currentTime = DateUtils.getCurrentDate();
		getUserPlanDetail(DateUtils.getCurrentDate());
		adapter = new CompleteScheduleAdapter(mContext, this);
		expandableListView.setAdapter(adapter);

		list.add("2017-10-11");
		list.add("2017-10-12");
		list.add("2017-10-13");
		list.add("2017-10-14");
		list.add("2017-10-16");
//		weekCalendar.setSelectDates(list);

		weekCalendar.setCompletedDates(list);
//		weekCalendar.setNoCompletedDates(list);
		//设置日历点击事件
		weekCalendar.setOnDateClickListener(new WeekCalendar.OnDateClickListener() {
			@Override
			public void onDateClick(String time) {
				currentTime = time;
				getUserPlanDetail(time);
				Toast.makeText(CompleteScheduleActivity.this, time, Toast.LENGTH_SHORT).show();
			}
		});

		weekCalendar.setOnCurrentMonthDateListener(new WeekCalendar.OnCurrentMonthDateListener() {
			@Override
			public void onCallbackMonthDate(String year, String month) {
				Toast.makeText(CompleteScheduleActivity.this, year + "-" + month, Toast.LENGTH_SHORT).show();
			}
		});

		weekCalendar.setOnCurrentWeekChangeListener(new WeekCalendar.OnCurrentWeekChangeListener() {
			@Override
			public void OnCurrentWeekChange(String time) {
				Toast.makeText(CompleteScheduleActivity.this, time, Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void collectPlanDetail(String detailId, String type) {
		String userId = "";
		if (SEAuthManager.getInstance().isAuthenticated()) {
			userId = SEAuthManager.getInstance().getAccessUser().getId();
		}
		userId = "754";
		SEAPP.showCatDialog(this);
		QuestionCourseManager.getInstance().collectPlanDetail(userId, detailId, currentTime, type, new Callback<SimpleResult>() {
			@Override
			public void success(SimpleResult simpleResult, Response response) {
				if (mContext != null && !CompleteScheduleActivity.this.isFinishing()) {
					if (simpleResult.getApicode() != 10000) {
						SEAPP.dismissAllowingStateLoss();
						ToastUtil.showToastShort(mContext, simpleResult.getMessage());
					} else {
						getUserPlanDetail(currentTime);
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

	private void getUserPlanDetail(String time) {
		String userId = "";
		if (SEAuthManager.getInstance().isAuthenticated()) {
			userId = SEAuthManager.getInstance().getAccessUser().getId();
		}
		userId = "754";
		SEAPP.showCatDialog(this);
		QuestionCourseManager.getInstance().getUserPlanDetail(userId, time, new Callback<UserPlanDetailResult>() {
			@Override
			public void success(UserPlanDetailResult userPlanDetailResult, Response response) {
				if (mContext != null && !CompleteScheduleActivity.this.isFinishing()) {
					if (userPlanDetailResult.getApicode() != 10000) {
						SEAPP.dismissAllowingStateLoss();
						ToastUtil.showToastShort(mContext, userPlanDetailResult.getMessage());
					} else {
						if (userPlanDetailResult.getResult() != null && userPlanDetailResult.getResult().toString().length() > 2) {
							try {
//								Logger.e("==" + userPlanDetailResult.getResult().toString());
								setData(userPlanDetailResult.getResult());
								adapter.updateList(userPlanDetailResult);
								for (int i = 0; i < userPlanDetailResult.getResult().getPlanList().size(); i++) {
									expandableListView.expandGroup(i);
								}
								SEAPP.dismissAllowingStateLoss();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
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


	@Override
	public void initData() {

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
	}

	@OnClick({R.id.left_close, R.id.right_share})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.left_close:
				finish();
				break;
			case R.id.right_share:
				break;
		}
	}

	public void addFlags() {
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明实现
			Window window = getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);//calculateStatusColor(Color.WHITE, (int) alphaValue)
		}
	}

	/**
	 */
	@SuppressLint("NewApi")
	protected void setStatusStyle() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setNavigationBarTintEnabled(true);
			tintManager.setTintColor(Color.parseColor("#ff5353"));
			tintManager.setStatusBarTintResource(R.color.black);
			tintManager.setStatusBarAlpha(0.3f);
			tintManager.setNavigationBarTintResource(R.color.red_f01c06);
		}
	}
}
