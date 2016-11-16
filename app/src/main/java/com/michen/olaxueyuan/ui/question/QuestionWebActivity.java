package com.michen.olaxueyuan.ui.question;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.model.MCQuestion;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.ui.activity.SuperActivity;
import com.michen.olaxueyuan.ui.me.activity.VideoPlayActivity;
import com.michen.olaxueyuan.ui.question.module.QuestionResultNoticeClose;
import com.snail.svprogresshud.SVProgressHUD;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import io.vov.vitamio.utils.StringUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class QuestionWebActivity extends SuperActivity implements View.OnClickListener {

    private WebView contentWebView;
    private Button previousBtn;
    private Button nextBtn;
    private TextView indexTV;
    private EditText articleTV;
    private TextView leftReturn;
    private ImageView addWrongTopicIcon;
    private ImageView openVideoIcon;
    private TextView tvTitle;

    private int type; // 1课程 2 题库
    private String currentSubjectId;
    private int hasArticle; // 1 英语类阅读理解
    private int objectId;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_web);
        EventBus.getDefault().register(this);

        contentWebView = (WebView) findViewById(R.id.questionWebView);
        // 启用javascript
        contentWebView.getSettings().setJavaScriptEnabled(true);

        indexTV = (TextView) findViewById(R.id.tv_index);
        articleTV = (EditText) findViewById(R.id.tv_article);

        leftReturn = (TextView) findViewById(R.id.left_return);
        addWrongTopicIcon = (ImageView) findViewById(R.id.add_wrong_topic_icon);
        openVideoIcon = (ImageView) findViewById(R.id.open_video_icon);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        leftReturn.setOnClickListener(this);
        addWrongTopicIcon.setOnClickListener(this);

        previousBtn = (Button) findViewById(R.id.previousBtn);
        previousBtn.setVisibility(View.GONE);
        previousBtn.setOnClickListener(this);

        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);

        int courseType = getIntent().getExtras().getInt("courseType");
        if (courseType == 2) {
            articleTV.setVisibility(View.VISIBLE);
        }

        type = getIntent().getExtras().getInt("type");
        if (type == 1) {
            tvTitle.setText("专项练习");
            MobclickAgent.onEvent(this, "enter_point");
        } else if (type == 2) {
            tvTitle.setText("模拟考试");
            MobclickAgent.onEvent(this, "enter_exam");
        } else if (type == 3) {
            tvTitle.setText("欧拉作业");
            addWrongTopicIcon.setVisibility(View.GONE);
        } else if (type == 4 || type == 5) {
            tvTitle.setText("错题集");
            addWrongTopicIcon.setImageResource(R.drawable.video_collect_icon_selected);
        }
        objectId = getIntent().getExtras().getInt("objectId");

        //支持js
        contentWebView.getSettings().setJavaScriptEnabled(true);
        //设置本地调用对象及其接口
        contentWebView.addJavascriptInterface(new JsInterface(QuestionWebActivity.this), "AndroidWebView");

        String userId = "";
        SEAuthManager am = SEAuthManager.getInstance();
        if (am.isAuthenticated()) {
            userId = am.getAccessUser().getId();
        }

        contentWebView.loadUrl(SEConfig.getInstance().getAPIBaseURL() + "/question.html?objectId=" + objectId + "&type=" + type + "&userId=" + userId);

        contentWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                if (type == 4 || type == 5) { //错题集直接显示答案
                    contentWebView.loadUrl("javascript:loadQuestion('1')");
                } else {
                    contentWebView.loadUrl("javascript:loadQuestion('0')");
                }
            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
                SVProgressHUD.showInViewWithoutIndicator(QuestionWebActivity.this, "加载失败", 2.0f);
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
                if (type == 4 || type == 5) {
                    addWrongTopicIcon.setImageResource(R.drawable.video_collect_icon_selected);
                } else {
                    addWrongTopicIcon.setImageResource(R.drawable.video_collect_icon);
                }
                contentWebView.loadUrl("javascript:clickPrevious()");
                break;
            case R.id.nextBtn:
                if (type == 4 || type == 5) {
                    addWrongTopicIcon.setImageResource(R.drawable.video_collect_icon_selected);
                } else {
                    addWrongTopicIcon.setImageResource(R.drawable.video_collect_icon);
                }
                contentWebView.loadUrl("javascript:clickNext()");
                break;
            case R.id.left_return:
                finish();
                break;
            case R.id.add_wrong_topic_icon:
                if (type == 4 || type == 5) {
                    updateWrongSet(false);
                } else {
                    updateWrongSet(true);
                }
                break;
        }
    }

    private void updateWrongSet(final boolean addOrDelete) {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        if (TextUtils.isEmpty(currentSubjectId)) {
            ToastUtil.showToastShort(mContext, "请选择题目");
            return;
        }
        String add = "1";//1增加错题，2删除错题
        if (addOrDelete) {
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
                if (mContext != null && !QuestionWebActivity.this.isFinishing()) {
                    if (simpleResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(mContext, simpleResult.getMessage(), 2.0f);
                    } else {
                        if (addOrDelete) {
                            ToastUtil.showToastShort(mContext, "增加错题集成功");
                            addWrongTopicIcon.setImageResource(R.drawable.video_collect_icon_selected);
                        } else {
                            ToastUtil.showToastShort(mContext, "删除错题集成功");
                            addWrongTopicIcon.setImageResource(R.drawable.video_collect_icon);
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !QuestionWebActivity.this.isFinishing()) {
                    if (addOrDelete) {
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
                                Intent intent = new Intent(QuestionWebActivity.this, VideoPlayActivity.class);
                                intent.putExtra("videoPath", videoUrl);
                                startActivity(intent);
                            }
                        });
                    }
                    break;
                case 5:
                    nextBtn.setEnabled(false);
                    Intent intent = new Intent(QuestionWebActivity.this, QuestionResultActivity.class);
                    intent.putExtra("answerArray", msg.obj.toString());
                    intent.putExtra("objectId", objectId);
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
