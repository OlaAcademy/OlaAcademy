package com.snail.olaxueyuan.ui.question;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.protocol.manager.SECourseManager;
import com.snail.olaxueyuan.protocol.model.MCOption;
import com.snail.olaxueyuan.protocol.model.MCQuestion;
import com.snail.olaxueyuan.protocol.result.MCQuestionListResult;
import com.snail.olaxueyuan.ui.course.pay.CoursePayActivity;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class QuestionWebActivity extends Activity implements View.OnClickListener{

    private WebView contentWebView;
    private Button previousBtn;
    private Button nextBtn;

    private ArrayList<MCQuestion> questionList;
    private ArrayList<MCOption> optionList;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_web);

        contentWebView = (WebView) findViewById(R.id.questionWebView);
        // 启用javascript
        contentWebView.getSettings().setJavaScriptEnabled(true);


        previousBtn = (Button) findViewById(R.id.previousBtn);
        previousBtn.setOnClickListener(btnClickListener);
        //contentWebView.addJavascriptInterface(this, "wst");

        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);
        initQuestionData();
    }

    private void initQuestionData(){
        SECourseManager sm = SECourseManager.getInstance();
        sm.fetchQuestionList("53", new Callback<MCQuestionListResult>() {
            @Override
            public void success(MCQuestionListResult result, Response response) {
                questionList = result.questionList;
                // 从assets目录下面的加载html
                contentWebView.loadUrl("file:///android_asset/question.html");
                contentWebView.loadUrl("javascript:javacalljswithargs('" + questionList.get(0).question + "')");
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    View.OnClickListener btnClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            // 传递参数调用
            contentWebView.loadUrl("javascript:javacalljswithargs('" + questionList.get(0).question + "')");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.nextBtn:
                Intent intent = new Intent(QuestionWebActivity.this,CoursePayActivity.class);
                startActivity(intent);
                break;
        }
    }
}
