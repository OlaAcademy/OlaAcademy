package com.michen.olaxueyuan.ui.me.activity;

import android.os.Bundle;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;

/**
 * 欧拉币规则
 */
public class CoinRuleActivity extends SEBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_rule);
        initView();
    }

    private void initView() {
        setTitleText("欧拉币规则");
    }
}
