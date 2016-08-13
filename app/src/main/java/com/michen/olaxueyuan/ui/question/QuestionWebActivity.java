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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.model.MCQuestion;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.me.activity.VideoPlayActivity;
import com.michen.olaxueyuan.ui.question.module.QuestionResultNoticeClose;
import com.snail.svprogresshud.SVProgressHUD;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class QuestionWebActivity extends SEBaseActivity implements View.OnClickListener {

    private WebView contentWebView;
    private Button previousBtn;
    private Button nextBtn;
    private TextView indexTV;
    private EditText articleTV;

    private ArrayList<MCQuestion> questionList;
    private int currentIndex;

    private int type; // 1课程 2 题库
    private int showAnswer; // 1 全部解析时显示答案
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

        previousBtn = (Button) findViewById(R.id.previousBtn);
        previousBtn.setVisibility(View.GONE);
        previousBtn.setOnClickListener(this);

        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);

        int courseType = getIntent().getExtras().getInt("courseType");
        if (courseType==2){
            articleTV.setVisibility(View.VISIBLE);
        }

        type = getIntent().getExtras().getInt("type");
        if (type == 1) {
            setTitleText("专项练习");
            MobclickAgent.onEvent(this,"enter_point");
        } else if(type == 2){
            setTitleText("模拟考试");
            MobclickAgent.onEvent(this, "enter_exam");
        }else if(type == 3){
            setTitleText("错题集");
        }
        objectId = getIntent().getExtras().getInt("objectId");

        //支持js
        contentWebView.getSettings().setJavaScriptEnabled(true);
        //设置本地调用对象及其接口
        contentWebView.addJavascriptInterface(new JsInterface(QuestionWebActivity.this), "AndroidWebView");

        String userId="";
        SEAuthManager am = SEAuthManager.getInstance();
        if (am.isAuthenticated()){
            userId = am.getAccessUser().getId();
        }

        contentWebView.loadUrl(SEConfig.getInstance().getAPIBaseURL() + "/question.html?objectId="+objectId+"&type=" + type + "&userId="+userId);
        contentWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (type==3){ //错题集直接显示答案
                    contentWebView.loadUrl("javascript:loadQuestion('1')");
                }else {
                    contentWebView.loadUrl("javascript:loadQuestion('0')");
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                SVProgressHUD.showInViewWithoutIndicator(QuestionWebActivity.this, "加载失败", 2.0f);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK){
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
        }
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
                    if(msg.obj==null||TextUtils.isEmpty(msg.obj.toString())){
                        setRightImageInvisibility();
                    }else {
                        setRightImage(R.drawable.ic_video);
                        final String videoUrl = msg.obj.toString();
                        setRightTextListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(QuestionWebActivity.this, VideoPlayActivity.class);
                                intent.putExtra("videoPath", videoUrl);
                                startActivity(intent);
                            }
                        });
                    }
                    break;
                case 5:
                    Intent intent = new Intent(QuestionWebActivity.this, QuestionResultActivity.class);
                    intent.putExtra("answerArray", msg.obj.toString());
                    intent.putExtra("objectId", objectId);
                    intent.putExtra("type",type);
                    if (type==1){
                        intent.putExtra("outerURL",getIntent().getStringExtra("outerURL"));
                    }
                    startActivityForResult(intent, 1);
                    break;
                case 6:
                    articleTV.setText(msg.obj.toString());
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
