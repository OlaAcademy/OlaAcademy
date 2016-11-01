package com.michen.olaxueyuan.ui.course;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.manager.WebViewManger;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebViewActivity extends SEBaseActivity {

    /*private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        setTitleText("欧拉精选");

        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl(getIntent().getStringExtra("textUrl"));
    }*/
    
    @Bind(R.id.webview)
    WebView mWebView;
    private String mUrl = "";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        mContext = this;
        mUrl = getIntent().getStringExtra("textUrl");
        initView();
        loadUrl(mUrl);
    }

    private void initView() {
        setTitleText("欧拉精选");
        WebViewManger.getInstance().initView(WebViewActivity.this, mWebView, mUrl);
        mWebView.setWebViewClient(new WebViewActivity.WebViewClientExtend());
    }

    public void loadUrl(String url) {
        if (mWebView != null) {
            Logger.e("url==" + url);
            SVProgressHUD.showInView(mContext, mContext.getResources().getString(R.string.request_running), true);
            mWebView.loadUrl(url);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SVProgressHUD.dismiss(mContext);
    }

    class WebViewClientExtend extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
//            mSwipeRefreshLayout.setRefreshing(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
//            mSwipeRefreshLayout.setRefreshing(false);
            SVProgressHUD.dismiss(mContext);
            view.loadUrl("javascript:(function() { var videos = document.getElementsByTagName('video'); for(var i=0;i<videos.length;i++){videos[i].play();}})()");
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            SVProgressHUD.dismiss(mContext);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            SVProgressHUD.dismiss(mContext);
            super.onReceivedSslError(view, handler, error);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
