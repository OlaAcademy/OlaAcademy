package com.michen.olaxueyuan.ui.plan.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.NoScrollGridView;
import com.michen.olaxueyuan.common.SubListView;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SECourseManager;
import com.michen.olaxueyuan.protocol.result.CommentSucessResult;
import com.michen.olaxueyuan.protocol.result.CourseVideoResult;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.QuestionResultAdapter;
import com.michen.olaxueyuan.ui.adapter.QuestionResultListAdapter;
import com.michen.olaxueyuan.ui.question.module.QuestionResultNoticeClose;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CompleteScheduleQuestionResultActivity extends SEBaseActivity {
	@Bind(R.id.correct_num_tv)
	TextView correctNumTv;
	@Bind(R.id.answer_all_number_tv)
	TextView answerAllNumberTv;
	@Bind(R.id.has_answer_num_tv)
	TextView hasAnswerNumTv;
	@Bind(R.id.correct_precent_tv)
	TextView correctPercent;
	@Bind(R.id.win_student_tv)
	TextView winStudentTv;
	@Bind(R.id.grid_answer)
	NoScrollGridView gridAnswer;
	//    NoScrollGridView gridAnswer;
	@Bind(R.id.list_knowledge)
	SubListView listKnowledge;
	@Bind(R.id.start_exam)
	Button startExam;
	@Bind(R.id.finish_answer)
	Button finishAnswer;
	@Bind(R.id.up_down_icon)
	ImageView upDownIcon;
	private int type; // 1课程 2 题库
	private int hasAnswer = 0;//已答题目
	private int correct = 0;//正确率
	private int winPercent = 30;//打败多少考生
	private int objectId;//试题的ID
	private String outerURL; //亚马逊购买链接
	private JSONArray array;
	private QuestionResultAdapter resultAdapter;
	private QuestionResultListAdapter listAdapter;
	private List<CourseVideoResult.ResultBean.VideoListBean> videoArrayList;
	private JSONArray limitArray = new JSONArray();//最大显示10个题目
	private String planId;
	private String planCurrentTime;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complete_schedule_question_result);
		ButterKnife.bind(this);
		setTitleText("答题报告");
		initView();
//		performRefresh();
	}

	public void initView() {
		String jsonString = getIntent().getStringExtra("answerArray");
		type = getIntent().getExtras().getInt("type");
		planId = getIntent().getExtras().getString("planId");
		planCurrentTime = getIntent().getExtras().getString("planCurrentTime");
		if (type == 1) {
			startExam.setText("购买教材");
			outerURL = getIntent().getStringExtra("outerURL");
		} else if (type == 2 || type == 3) {
			startExam.setText("全部解析");
		} else if (type == 4) {
			startExam.setVisibility(View.INVISIBLE);
			finishAnswer.setVisibility(View.INVISIBLE);
		}
		objectId = getIntent().getExtras().getInt("objectId");
		Logger.json(jsonString);
		try {
			array = new JSONArray(jsonString);
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = (JSONObject) array.get(i);
				if (!object.optString("isCorrect").equals("2")) {//没答题
					hasAnswer++;
				}
				if (object.optString("isCorrect").equals("1")) {//答题正确
					correct++;
				}
			}
			correctNumTv.setText(String.valueOf(correct));
			answerAllNumberTv.setText(getString(R.string.all_number_subject, array.length()));
			hasAnswerNumTv.setText(String.valueOf(hasAnswer));
			correctPercent.setText(correct * 100 / array.length() + "%");
			//全都答对100%，全错0%，其他65%--95%
			if (correct == array.length()) {
				winPercent = 100;
			} else if (correct == 0) {
				winPercent = 0;
			} else {
				winPercent = 65 + (int) (Math.random() * 30);
			}
			winStudentTv.setText(String.valueOf(winPercent) + "%");
			//最多显示10个，其它的默认隐藏掉
			if (array.length() > 10) {
				for (int i = 0; i < 10; i++) {
					limitArray.put(i, array.get(i));
				}
				resultAdapter = new QuestionResultAdapter(limitArray, CompleteScheduleQuestionResultActivity.this);
				upDownIcon.setVisibility(View.VISIBLE);
				upDownIcon.setImageResource(R.drawable.down_arrow_icon);
			} else {
				resultAdapter = new QuestionResultAdapter(array, CompleteScheduleQuestionResultActivity.this);
			}
			gridAnswer.setSelector(new ColorDrawable(Color.TRANSPARENT));
			gridAnswer.setAdapter(resultAdapter);

			//提交答案
			submitAnswer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	boolean upOrDown = false;//false:朝上, true:朝下

	@OnClick({R.id.start_exam, R.id.finish_answer, R.id.up_down_icon})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.start_exam:
				if (type == 1) {
					Uri uri = Uri.parse(outerURL);
					Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(intent);
				} else if (type == 2) {
					setResult(RESULT_OK);
					finish();
				}
				break;
			case R.id.finish_answer:
				EventBus.getDefault().post(new QuestionResultNoticeClose(0, true));
				finish();
				break;
			case R.id.up_down_icon:
				if (upOrDown) {
					upOrDown = false;
					upDownIcon.setImageResource(R.drawable.down_arrow_icon);
					resultAdapter.upDateData(limitArray);
				} else {
					upOrDown = true;
					upDownIcon.setImageResource(R.drawable.up_arrow_icon);
					resultAdapter.upDateData(array);
				}
				break;
		}
	}

	private void submitAnswer() {
		JSONArray answerArray = new JSONArray();
		try {
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = new JSONObject();
				JSONObject object = (JSONObject) array.get(i);
				if (!TextUtils.isEmpty(object.optString("optionId"))) {
					jsonObject.put("no", object.optString("questionId"));
					jsonObject.put("optId", object.optString("optionId"));
					answerArray.put(jsonObject);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		final SEAuthManager am = SEAuthManager.getInstance();
		SECourseManager courseManager = SECourseManager.getInstance();
		String userId = "";
		if (am.isAuthenticated()) {
			userId = SEAuthManager.getInstance().getAccessUser().getId();
		}
//        courseManager.submitAnswer(userId, answerArray.toString(), type+"", new Callback<CommentSucessResult>() {
//            @Override
//            public void success(CommentSucessResult result, Response response) {
//                // 刷新首页数据
//                if (am.isAuthenticated()){
//                    EventBus.getDefault().post(new UserLoginNoticeModule(true));
//                }
//
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//
//            }
//        });
		courseManager.createPlanRecord(userId, planId, String.valueOf(objectId), planCurrentTime, answerArray.toString(), new Callback<CommentSucessResult>() {
			@Override
			public void success(CommentSucessResult result, Response response) {
				// 刷新首页数据
				if (am.isAuthenticated()) {
					EventBus.getDefault().post(new UserLoginNoticeModule(true));
				}

			}

			@Override
			public void failure(RetrofitError error) {

			}
		});

	}

	public void performRefresh() {
		SECourseManager courseManager = SECourseManager.getInstance();
		String userId = "";
		if (SEAuthManager.getInstance().isAuthenticated()) {
			userId = SEAuthManager.getInstance().getAccessUser().getId();
		}
		courseManager.fetchCourseSection(String.valueOf(objectId), userId, new Callback<CourseVideoResult>() {
			@Override
			public void success(CourseVideoResult result, Response response) {
				if (result.getApicode() != 10000) {
					ToastUtil.showToastShort(mContext, result.getMessage());
				} else {
					videoArrayList = result.getResult().getVideoList();
					if (videoArrayList != null && videoArrayList.size() > 0) {
						listAdapter = new QuestionResultListAdapter(CompleteScheduleQuestionResultActivity.this);
						listKnowledge.setAdapter(listAdapter);
						listAdapter.updateData(videoArrayList, result, String.valueOf(objectId));
					}
				}
			}

			@Override
			public void failure(RetrofitError error) {
				ToastUtil.showToastShort(mContext, R.string.get_knowledge_fail);
			}
		});
	}

}
