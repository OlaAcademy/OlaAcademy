package com.michen.olaxueyuan.ui.course;

import android.os.Bundle;
import android.webkit.WebView;

import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.R;

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
