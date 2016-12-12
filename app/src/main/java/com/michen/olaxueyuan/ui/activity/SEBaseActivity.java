package com.michen.olaxueyuan.ui.activity;

import android.app.ActionBar;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.StatusBarCompat;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

public class SEBaseActivity extends FragmentActivity {
    private ImageView leftImage;
    private ImageView rightImage;
    private TextView leftText, titleText, rightText;
    ActionBar actionBar;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        PushAgent.getInstance(this).onAppStart();
        // manifest中配置主题为Translucent，因此需要在这儿通过代码设置
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
//        setTheme(R.style.AppTheme);
//        setTheme(R.style.MyAppTheme);
        setStatusBarColor();
        actionBar = getActionBar();
        if (actionBar != null) {
            // 返回箭头（默认不显示）
            actionBar.setDisplayHomeAsUpEnabled(false);
            // 左侧图标点击事件使能
            actionBar.setHomeButtonEnabled(true);
            // 使左上角图标(系统)是否显示
            actionBar.setDisplayShowHomeEnabled(false);
            // 显示标题
            actionBar.setDisplayShowTitleEnabled(false);
            //显示自定义视图
            actionBar.setDisplayShowCustomEnabled(true);
        }
        View actionbarLayout = LayoutInflater.from(this).inflate(
                R.layout.actionbar_layout, null);
        leftImage = (ImageView) actionbarLayout.findViewById(R.id.left_imbt);
        initLeft();
        rightImage = (ImageView) actionbarLayout.findViewById(R.id.right_imbt);
        titleText = (TextView) actionbarLayout.findViewById(R.id.tv_title);
        leftText = (TextView) actionbarLayout.findViewById(R.id.left_text);
        rightText = (TextView) actionbarLayout.findViewById(R.id.right_text);
        if (actionBar != null) {
            actionBar.setCustomView(actionbarLayout);
        }
    }

    private void initLeft() {
        leftImage.setVisibility(View.VISIBLE);
//        leftImage.setImageResource(R.drawable.ic_back);
        leftImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void setTitleText(String title) {
        titleText.setText(title);
    }


    public void setRightImage(int resid) {
        rightImage.setVisibility(View.VISIBLE);
        rightImage.setImageResource(resid);
    }

    public void setRightImageInvisibility() {
        rightImage.setVisibility(View.GONE);
    }

    public void setLeftImageInvisibility() {
        leftImage.setVisibility(View.GONE);
    }

    public void setLeftImageListener(View.OnClickListener l) {
        leftImage.setOnClickListener(l);
    }

    public void setRightImageListener(View.OnClickListener l) {
        rightImage.setOnClickListener(l);
    }

    public void setRightText(String text) {
        rightText.setText(text);
    }

    public void setLeftText(String text) {
        leftText.setText(text);
    }

    public void setLeftTextListener(View.OnClickListener l) {
        leftText.setOnClickListener(l);
    }

    public void setRightTextListener(View.OnClickListener l) {
        rightText.setOnClickListener(l);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);   // 友盟统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    public void setStatusBarColor() {
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.common_blue));
        }
    }
}

