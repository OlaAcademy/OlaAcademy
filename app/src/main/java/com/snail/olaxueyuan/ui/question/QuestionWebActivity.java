package com.snail.olaxueyuan.ui.question;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.app.SEConfig;
import com.snail.olaxueyuan.protocol.model.MCQuestion;
import com.snail.olaxueyuan.ui.activity.SEBaseActivity;
import com.snail.svprogresshud.SVProgressHUD;
import java.util.ArrayList;


public class QuestionWebActivity extends SEBaseActivity implements View.OnClickListener {

    private WebView contentWebView;
    private Button previousBtn;
    private Button nextBtn;

    private ArrayList<MCQuestion> questionList;
    private int currentIndex;

    private int type; // 1课程 2 题库
    private int showAnswer; // 1 全部解析时显示答案
    private int hasArticle; // 1 英语类阅读理解

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_web);

        contentWebView = (WebView) findViewById(R.id.questionWebView);
        // 启用javascript
        contentWebView.getSettings().setJavaScriptEnabled(true);


        previousBtn = (Button) findViewById(R.id.previousBtn);
        previousBtn.setVisibility(View.GONE);
        previousBtn.setOnClickListener(this);

        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);

        type = getIntent().getExtras().getInt("type");
        if (type==1){
            setTitleText("专项练习");
        }else {
            setTitleText("模拟考试");
        }
        final int objectId = getIntent().getExtras().getInt("objectId");

        //支持js
        contentWebView.getSettings().setJavaScriptEnabled(true);
        //设置本地调用对象及其接口
        contentWebView.addJavascriptInterface(new JsInterface(QuestionWebActivity.this), "AndroidWebView");

        contentWebView.loadUrl(SEConfig.getInstance().getAPIBaseURL() + "/ola/jsp/question.jsp?&type=" + type);
        contentWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                contentWebView.loadUrl("javascript:loadQuestion('"+objectId+"')");
            }
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                SVProgressHUD.showInViewWithoutIndicator(QuestionWebActivity.this,"加载失败",2.0f);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.previousBtn:
                contentWebView.loadUrl("javascript:clickPrevious()");
                break;
            case R.id.nextBtn:
                contentWebView.loadUrl("javascript:clickNext()");
                break;
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    previousBtn.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    previousBtn.setVisibility(View.GONE);
                    break;
                case 2:
                    nextBtn.setText(msg.obj.toString());
                    break;
                case 3:
                    Intent intent = new Intent(QuestionWebActivity.this,QuestionResultActivity.class);
                    intent.putExtra("answerArray",msg.obj.toString());
                    startActivity(intent);
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
        public void showPreviousButton() {
            handler.sendEmptyMessage(0);
        }
        @JavascriptInterface
        public void hidePreviousButton() {
            handler.sendEmptyMessage(1);
        }
        @JavascriptInterface
        public void updateNextButton(String text) {
            Message msg = Message.obtain();
            msg.what = 2;
            msg.obj = text;
            handler.sendMessage(msg);
        }

        @JavascriptInterface
        public void submitAnswer(String answerArray){
            Message msg = Message.obtain();
            msg.obj = answerArray;
            msg.what = 3;
            handler.sendMessage(msg);
        }
    }
}
