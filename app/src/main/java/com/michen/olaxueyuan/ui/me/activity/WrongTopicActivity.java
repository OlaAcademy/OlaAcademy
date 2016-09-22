package com.michen.olaxueyuan.ui.me.activity;

import android.os.Bundle;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;

/**
 * 错题本
 */
public class WrongTopicActivity extends SEBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrong_topic);
        initView();
    }

    private void initView() {
        setTitleText(getString(R.string.wrong_topic_text));
    }
}
