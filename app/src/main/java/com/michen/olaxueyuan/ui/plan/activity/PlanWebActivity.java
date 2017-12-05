package com.michen.olaxueyuan.ui.plan.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.manager.DialogUtils;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.ui.activity.SuperActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.michen.olaxueyuan.ui.me.activity.VideoPlayActivity;
import com.michen.olaxueyuan.ui.question.QuestionResultActivity;
import com.michen.olaxueyuan.ui.question.module.QuestionResultNoticeClose;
import com.snail.svprogresshud.SVProgressHUD;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.analytics.MobclickAgent;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 计划答题webview
 */

public class PlanWebActivity extends SuperActivity implements View.OnClickListener {

	private WebView contentWebView;
	private Button previousBtn;
	private Button nextBtn;
	private TextView indexTV;
	private EditText articleTV;
	private TextView leftReturn;
	private ImageView addWrongTopicIcon;
	private ImageView openVideoIcon;
	private TextView tvTitle;
	private TextView rightText;

	private int type;
	private String currentSubjectId;
	private boolean add_delete;
	private int objectId;
	private String title;
	private String subjectIds;
	private String planId;
	private String planCurrentTime;


	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plan_web);
		EventBus.getDefault().register(this);

		contentWebView = (WebView) findViewById(R.id.questionWebView);
		contentWebView.setWebChromeClient(new WebChromeClient());
		// 启用javascript
		contentWebView.getSettings().setJavaScriptEnabled(true);
		contentWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

		indexTV = (TextView) findViewById(R.id.tv_index);
		articleTV = (EditText) findViewById(R.id.tv_article);

		leftReturn = (TextView) findViewById(R.id.left_return);
		addWrongTopicIcon = (ImageView) findViewById(R.id.add_wrong_topic_icon);
		openVideoIcon = (ImageView) findViewById(R.id.open_video_icon);
		tvTitle = (TextView) findViewById(R.id.tv_title);
		rightText = (TextView) findViewById(R.id.right_text);
		leftReturn.setOnClickListener(this);
		addWrongTopicIcon.setOnClickListener(this);
		rightText.setOnClickListener(this);

		previousBtn = (Button) findViewById(R.id.previousBtn);
		previousBtn.setVisibility(View.GONE);
		previousBtn.setOnClickListener(this);

		nextBtn = (Button) findViewById(R.id.nextBtn);
		nextBtn.setOnClickListener(this);

		type = getIntent().getExtras().getInt("type");
		title = getIntent().getExtras().getString("name");
		subjectIds = getIntent().getExtras().getString("subjectIds");
		planId = getIntent().getExtras().getString("planId");
		planCurrentTime = getIntent().getExtras().getString("planCurrentTime");
		tvTitle.setText(title);
		objectId = getIntent().getExtras().getInt("objectId");

		//支持js
		contentWebView.getSettings().setJavaScriptEnabled(true);
		//设置本地调用对象及其接口
		contentWebView.addJavascriptInterface(new JsInterface(PlanWebActivity.this), "AndroidWebView");

		String userId = "";
		SEAuthManager am = SEAuthManager.getInstance();
		if (am.isAuthenticated()) {
			userId = am.getAccessUser().getId();
		} else {
			startActivity(new Intent(PlanWebActivity.this, UserLoginActivity.class));
			return;
		}

		contentWebView.loadUrl(SEConfig.getInstance().getAPIBaseURL() + "/plan_question.html?subjectIds=" + subjectIds + "&userId=" + userId);
		Logger.e(SEConfig.getInstance().getAPIBaseURL() + "/plan_question.html?subjectIds=" + subjectIds + "&userId=" + userId);
		contentWebView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView webView, String s) {
				super.onPageFinished(webView, s);
				contentWebView.loadUrl("javascript:loadQuestion('0')");
			}

			@Override
			public void onReceivedError(WebView webView, int i, String s, String s1) {
				super.onReceivedError(webView, i, s, s1);
				ToastUtil.showToastShort(mContext, "加载失败");
			}
		});
	}

	@Override
	public void initView() {

	}

	@Override
	public void initData() {

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			nextBtn.setText("下一题");
			contentWebView.loadUrl("javascript:loadQuestion('1')"); //全部解析
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.previousBtn:
				contentWebView.loadUrl("javascript:clickPrevious()");
				break;
			case R.id.nextBtn:
				contentWebView.loadUrl("javascript:clickNext()");
				break;
			case R.id.left_return:
				finish();
				break;
			case R.id.add_wrong_topic_icon:
				if (type == 4 || type == 5) {
					showSetWrongTopic(false, "您是否要把该题从错题集中移除?");
				} else {
					showSetWrongTopic(true, "您是否要把该题加入错题集中?");
				}
				break;
			case R.id.right_text:
				break;
		}
	}

	private void showSetWrongTopic(final boolean addOrDelete, String content) {
		DialogUtils.showDialog(mContext, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
					case R.id.yes:
						add_delete = addOrDelete;
						contentWebView.loadUrl("javascript:getCurrentSubject()");
						break;
				}
			}
		}, "", content, "", "");
	}

	private void updateWrongSet() {
		String userId = "";
		if (SEAuthManager.getInstance().isAuthenticated()) {
			userId = SEAuthManager.getInstance().getAccessUser().getId();
		}
		if (TextUtils.isEmpty(currentSubjectId)) {
			ToastUtil.showToastShort(mContext, "请选择题目");
			return;
		}
		String add = "1";//1增加错题，2删除错题
		if (add_delete) {
			add = "1";
		} else {
			add = "2";
		}
		int answerType = type;
		if (type == 4 || type == 5) {
			answerType = type - 3;
		}
		SEUserManager.getInstance().updateWrongSet(userId, String.valueOf(answerType), add, currentSubjectId, new Callback<SimpleResult>() {
			@Override
			public void success(SimpleResult simpleResult, Response response) {
				if (mContext != null && !PlanWebActivity.this.isFinishing()) {
					if (simpleResult.getApicode() != 10000) {
						SVProgressHUD.showInViewWithoutIndicator(mContext, simpleResult.getMessage(), 2.0f);
					} else {
						if (add_delete) {
							ToastUtil.showToastShort(mContext, "增加错题集成功");
						} else {
							ToastUtil.showToastShort(mContext, "删除错题集成功");
							contentWebView.reload();
						}
					}
				}
			}

			@Override
			public void failure(RetrofitError error) {
				if (mContext != null && !PlanWebActivity.this.isFinishing()) {
					if (add_delete) {
						ToastUtil.showToastShort(mContext, "增加错题集失败");
					} else {
						ToastUtil.showToastShort(mContext, "删除错题集失败");
					}
				}
			}
		});
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					indexTV.setText(msg.obj.toString());
					break;
				case 1:
					previousBtn.setVisibility(View.VISIBLE);
					break;
				case 2:
					previousBtn.setVisibility(View.GONE);
					break;
				case 3:
					nextBtn.setText(msg.obj.toString());
					break;
				case 4:
					if (msg.obj == null || TextUtils.isEmpty(msg.obj.toString())) {
//                        setRightImageInvisibility();
						openVideoIcon.setVisibility(View.INVISIBLE);
					} else {
//                        setRightImage(R.drawable.ic_video_blue);
						openVideoIcon.setVisibility(View.VISIBLE);
						final String videoUrl = msg.obj.toString();
						openVideoIcon.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								Intent intent = new Intent(PlanWebActivity.this, VideoPlayActivity.class);
								intent.putExtra("videoPath", videoUrl);
								startActivity(intent);
							}
						});
					}
					break;
				case 5:
					nextBtn.setEnabled(false);
					Intent intent = new Intent(PlanWebActivity.this, CompleteScheduleQuestionResultActivity.class);
					intent.putExtra("answerArray", msg.obj.toString());
					intent.putExtra("objectId", objectId);
					intent.putExtra("planId", planId);
					intent.putExtra("planCurrentTime", planCurrentTime);
					intent.putExtra("type", type);
					if (type == 1) {
						intent.putExtra("outerURL", getIntent().getStringExtra("outerURL"));
					}
					startActivityForResult(intent, 1);
					break;
				case 6:
					articleTV.setText(msg.obj.toString());
					break;
				case 7:
					currentSubjectId = msg.obj.toString();
					updateWrongSet();
					break;
			}
		}
	};

	private class JsInterface {
		private Context mContext;

		public JsInterface(Context context) {
			this.mContext = context;
		}

		//在js中调用window.AndroidWebView.showPreviousButton()，便会触发此方法。
		@JavascriptInterface
		public void updateQuestionIndex(String questionIndex) {
			Message msg = Message.obtain();
			msg.obj = questionIndex;
			msg.what = 0;
			handler.sendMessage(msg);
		}

		@JavascriptInterface
		public void showPreviousButton() {
			handler.sendEmptyMessage(1);
		}

		@JavascriptInterface
		public void hidePreviousButton() {
			handler.sendEmptyMessage(2);
		}

		@JavascriptInterface
		public void updateNextButton(String text) {
			Message msg = Message.obtain();
			msg.what = 3;
			msg.obj = text;
			handler.sendMessage(msg);
		}

		@JavascriptInterface
		public void showVideo(String videoUrl) {
			Message msg = Message.obtain();
			msg.what = 4;
			msg.obj = videoUrl;
			handler.sendMessage(msg);
		}

		@JavascriptInterface
		public void submitAnswer(String answerArray) {
			Message msg = Message.obtain();
			msg.obj = answerArray;
			msg.what = 5;
			handler.sendMessage(msg);
		}

		@JavascriptInterface
		public void showArticle(String article) {
			Message msg = Message.obtain();
			msg.obj = article;
			msg.what = 6;
			handler.sendMessage(msg);
		}

		@JavascriptInterface
		public void updateSubjectId(String subjectId) {
			Message msg = Message.obtain();
			msg.obj = subjectId;
			msg.what = 7;
			handler.sendMessage(msg);
		}
	}

	public void onEventMainThread(QuestionResultNoticeClose module) {//答题完成界面点击答题完成，finish本页面
		if (module.type == 0 && module.isClose) {
			finish();
		}
	}

	// 友盟session统计
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
		if (nextBtn != null) {
			nextBtn.setEnabled(true);
		}
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);

	}
}
