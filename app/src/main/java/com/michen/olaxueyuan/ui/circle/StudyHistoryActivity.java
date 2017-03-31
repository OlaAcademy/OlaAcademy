package com.michen.olaxueyuan.ui.circle;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.activity.SuperActivity;

public class StudyHistoryActivity extends SuperActivity {
    private int type;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getIntent().getIntExtra("type", 1);
        setContentView(R.layout.activity_study_history);
    }

    @Override
    public void initView() {
        transaction = getSupportFragmentManager().beginTransaction();
        switch (type) {
            case 1:
            default:
                setTitleText("学习历史");
                transaction.add(R.id.content, new CircleFragmentOld());
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
