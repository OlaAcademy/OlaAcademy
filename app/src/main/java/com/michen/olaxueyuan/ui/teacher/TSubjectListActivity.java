package com.michen.olaxueyuan.ui.teacher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.protocol.event.PublishHomeWorkSuccessEvent;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.group.CreateGroupActivity;
import com.snail.svprogresshud.SVProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class TSubjectListActivity extends SEBaseActivity implements View.OnClickListener {

    @Bind(R.id.questionWebView)
    WebView contentWebView;
    @Bind(R.id.operationRL)
    RelativeLayout operationRL;
    @Bind(R.id.deployRL)
    RelativeLayout deployRL;
    @Bind(R.id.nextBtn)
    Button nextBtn;
    @Bind(R.id.chooseBtn)
    Button chooseBtn;
    @Bind(R.id.deployBtn)
    Button deployBtn;

    private int type; // 1课程 2 题库
    private int objectId;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsubject_list);

        setTitleText("题目列表");

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        nextBtn.setOnClickListener(this);
        chooseBtn.setOnClickListener(this);
        deployBtn.setOnClickListener(this);

        type = getIntent().getExtras().getInt("type");
        objectId = getIntent().getExtras().getInt("objectId");

        //支持js
        contentWebView.getSettings().setJavaScriptEnabled(true);
        //设置本地调用对象及其接口
        contentWebView.addJavascriptInterface(new JsInterface(TSubjectListActivity.this), "AndroidWebView");
        contentWebView.setWebChromeClient(new WebChromeClient()); // 使js的alert可用

        contentWebView.loadUrl(SEConfig.getInstance().getAPIBaseURL() + "/homework.html?objectId=" + objectId + "&type=" + type);
        contentWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                SVProgressHUD.showInViewWithoutIndicator(TSubjectListActivity.this, "加载失败", 2.0f);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nextBtn:
                contentWebView.loadUrl("javascript:clickNext()");
                deployRL.setVisibility(View.VISIBLE);
                operationRL.setVisibility(View.GONE);
                break;
            case R.id.chooseBtn:
                contentWebView.loadUrl("javascript:rechoose()");
                operationRL.setVisibility(View.VISIBLE);
                deployRL.setVisibility(View.GONE);
                break;
            case R.id.deployBtn:
                contentWebView.loadUrl("javascript:deploy()");
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    String subjectArray = msg.obj.toString();
                    if (!TextUtils.isEmpty(subjectArray) && !subjectArray.equals("[]")) {
                        Intent intent = new Intent(TSubjectListActivity.this, TSubjectDeployActivity.class);
                        try {
                            JSONArray jsonArray = new JSONArray(subjectArray);
                            String subjectIds = "";
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject question = jsonArray.getJSONObject(i);
                                subjectIds += question.get("id") + ",";
                            }
                            intent.putExtra("subjectIds", subjectIds);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        SVProgressHUD.showInViewWithoutIndicator(TSubjectListActivity.this, "请选择题目", 2.0f);
                    }

                    break;
            }
        }
    };

    private class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        //在js中调用window.AndroidWebView.chooseSubject()，便会触发此方法。
        @JavascriptInterface
        public void deploySubject(String subjectArray) {
            Message msg = Message.obtain();
            msg.what = 1;
            msg.obj = subjectArray;
            handler.sendMessage(msg);
        }

    }

    public void onResume() {
        super.onResume();
        if (nextBtn != null) {
            nextBtn.setEnabled(true);
        }
    }

    /**
     * {@link TSubjectDeployActivity#publishHomeWork()}
     * {@link CreateGroupActivity#saveGroupInfo(String, String)}
     *
     * @param successEvent
     */
    public void onEventMainThread(PublishHomeWorkSuccessEvent successEvent) {
        if (successEvent.isSuccess) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}

