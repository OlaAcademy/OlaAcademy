package com.snail.olaxueyuan.ui.course;

import android.os.Bundle;
import android.app.Activity;
import android.webkit.WebView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.ui.activity.SEBaseActivity;

public class WebViewActivity extends SEBaseActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        setTitleText("欧拉精选");

        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl(getIntent().getStringExtra("textUrl"));
    }

}
