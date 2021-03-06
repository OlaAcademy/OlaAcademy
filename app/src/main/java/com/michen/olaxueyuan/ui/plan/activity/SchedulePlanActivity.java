package com.michen.olaxueyuan.ui.plan.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.DateUtils;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.PlanConditionResult;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.ui.activity.SuperActivity;
import com.michen.olaxueyuan.ui.plan.data.SetScheduleListAdapter;

import android.provider.CalendarContract.Calendars;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 制定计划
 */
public class SchedulePlanActivity extends SuperActivity {
	TitleManager titleManager;
	@Bind(R.id.list_view)
	ListView listView;
	@Bind(R.id.time)
	TextView time;
	@Bind(R.id.set_time_layout)
	LinearLayout setTimeLayout;
	@Bind(R.id.next)
	Button next;
	@Bind(R.id.set_schedule_layout)
	LinearLayout setScheduleLayout;
	@Bind(R.id.time_layout)
	LinearLayout timeLayout;
	private SetScheduleListAdapter scheduleListAdapter;
	private boolean isShowTime = true;//下一步，false为显示提醒时间，true为创建计划
	private PlanConditionResult conditionResult;
	public int mathLength;
	public int englishLength;
	public int logicLength;
	public int writeLength;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule_plan);
		ButterKnife.bind(this);
		queryPlanCondition();
	}

	@Override
	public void initView() {
		titleManager = new TitleManager(this, "", this, true);
		scheduleListAdapter = new SetScheduleListAdapter(mContext, this);
		listView.setAdapter(scheduleListAdapter);
	}

	@Override
	public void initData() {

	}

	private void queryPlanCondition() {
		SEAPP.showCatDialog(this);
		QuestionCourseManager.getInstance().queryPlanCondition(new Callback<PlanConditionResult>() {
			@Override
			public void success(PlanConditionResult planConditionResult, Response response) {
				if (mContext != null && !SchedulePlanActivity.this.isFinishing()) {
					SEAPP.dismissAllowingStateLoss();
					if (planConditionResult.getApicode() == 10000) {
						scheduleListAdapter.updateData(planConditionResult.getResult());
						conditionResult = planConditionResult;
						List<PlanConditionResult.ResultBean> list = conditionResult.getResult();
						for (int i = 0; i < list.size(); i++) {
							switch (list.get(i).getType()) {
								case 1://数学
									mathLength = list.get(i).getLeast();
									break;
								case 2://英语
									englishLength = list.get(i).getLeast();
									break;
								case 3://逻辑
									logicLength = list.get(i).getLeast();
									break;
								case 4://写作
									writeLength = list.get(i).getLeast();
									break;
							}
						}
					} else {
						ToastUtil.showToastShort(mContext, planConditionResult.getMessage());
					}
				}
			}

			@Override
			public void failure(RetrofitError error) {
				if (mContext != null && !SchedulePlanActivity.this.isFinishing()) {
					SEAPP.dismissAllowingStateLoss();
					ToastUtil.showToastShort(mContext, R.string.data_request_fail);
				}
			}
		});

	}

	private void createStudyPlan() {
		SEAPP.showCatDialog(this);
		String userId = "";
		SEAuthManager am = SEAuthManager.getInstance();
		if (am.isAuthenticated()) {
			userId = am.getAccessUser().getId();
		}
//		userId = "381";
		String plan = "";
		List<PlanConditionResult.ResultBean> result = conditionResult.getResult();
		JSONObject jsonObject = new JSONObject();
		try {
			for (int i = 0; i < result.size(); i++) {
				switch (result.get(i).getType()) {
					case 1://数学
						jsonObject.put("mathbegin", DateUtils.plusDay(0));
						jsonObject.put("mathend", DateUtils.plusDay(mathLength));
						break;
					case 2://英语
						jsonObject.put("englishbegin", DateUtils.plusDay(0));
						jsonObject.put("englishend", DateUtils.plusDay(englishLength));
						break;
					case 3://逻辑
						jsonObject.put("logicbegin", DateUtils.plusDay(0));
						jsonObject.put("logicend", DateUtils.plusDay(logicLength));
						break;
					case 4://写作
						jsonObject.put("writebegin", DateUtils.plusDay(0));
						jsonObject.put("writeend", DateUtils.plusDay(writeLength));
						break;
				}
			}
			plan = jsonObject.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Logger.e("plan=" + plan);
		QuestionCourseManager.getInstance().createStudyPlan(userId, plan, new Callback<SimpleResult>() {
			@Override
			public void success(SimpleResult simpleResult, Response response) {
				if (mContext != null && !SchedulePlanActivity.this.isFinishing()) {
					SEAPP.dismissAllowingStateLoss();
					if (simpleResult.getApicode() == 10000) {
						ToastUtil.showToastShort(mContext, "制定计划成功");
						startActivity(new Intent(mContext, CompleteScheduleActivity.class));
					} else {
						ToastUtil.showToastShort(mContext, simpleResult.getMessage());
					}
				}
			}

			@Override
			public void failure(RetrofitError error) {
				if (mContext != null && !SchedulePlanActivity.this.isFinishing()) {
					SEAPP.dismissAllowingStateLoss();
					ToastUtil.showToastShort(mContext, R.string.data_request_fail);
				}
			}
		});
	}

	@OnClick({R.id.set_time_layout, R.id.next, R.id.left_return})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.left_return:
				addCalendar();
//				finish();
				break;
			case R.id.set_time_layout:
				addCalendar();
				break;
			case R.id.next:
				if (isShowTime) {
					isShowTime = false;
					setScheduleLayout.setVisibility(View.GONE);
					timeLayout.setVisibility(View.VISIBLE);
				} else {
					createStudyPlan();
				}
				break;
		}
	}

	private String calanderURL = "content://com.android.calendar/calendars";
	private String calanderEventURL = "content://com.android.calendar/events";
	private String calanderRemiderURL = "content://com.android.calendar/reminders";

	public void addCalendar() {
		// 获取要出入的gmail账户的id
		String calId = "";
		Cursor userCursor = getContentResolver().query(Uri.parse(calanderURL), null, null, null, null);
		if (userCursor.getCount() > 0) {
//			userCursor.moveToLast();  //注意：是向最后一个账户添加，开发者可以根据需要改变添加事件 的账户
			userCursor.moveToFirst();
			calId = userCursor.getString(userCursor.getColumnIndex("_id"));
		} else {
			Toast.makeText(this, "没有账户，请先添加账户", Toast.LENGTH_SHORT).show();
			return;
		}

		ContentValues event = new ContentValues();
		event.put("title", "【欧拉MBA】 计划课程日·坚持=成功");//标题
		event.put("description", "欧拉MBA学习提醒");//描述
		// 插入账户
		event.put("calendar_id", calId);
		System.out.println("calId: " + calId);
		event.put("eventLocation", "");//地址，可导航

		Calendar mCalendar = Calendar.getInstance();
		mCalendar.set(Calendar.HOUR_OF_DAY, 11);
		mCalendar.set(Calendar.MINUTE, 45);
		long start = mCalendar.getTime().getTime();
		mCalendar.set(Calendar.HOUR_OF_DAY, 12);
		long end = mCalendar.getTime().getTime();

		event.put("dtstart", start);
		event.put("dtend", end);
		event.put("hasAlarm", 1);

		event.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai");  //这个是时区，必须有，
		//添加事件
		Uri newEvent = getContentResolver().insert(Uri.parse(calanderEventURL), event);
		//事件提醒的设定
		long id = Long.parseLong(newEvent.getLastPathSegment());
		ContentValues values = new ContentValues();
		values.put("event_id", id);
		// 提前10分钟有提醒
		values.put("minutes", 10);
		getContentResolver().insert(Uri.parse(calanderRemiderURL), values);

//		Toast.makeText(mContext, "插入事件成功", Toast.LENGTH_LONG).show();
	}
}
