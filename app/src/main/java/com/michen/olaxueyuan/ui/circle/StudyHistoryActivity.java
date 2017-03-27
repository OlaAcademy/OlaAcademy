package com.michen.olaxueyuan.ui.circle;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.activity.SuperActivity;
import com.michen.olaxueyuan.ui.question.CommentListFragment;
import com.michen.olaxueyuan.ui.question.PraiseListFragment;
import com.michen.olaxueyuan.ui.question.SystemMessageFragment;

import cn.leancloud.chatkit.activity.LCIMConversationListFragment;

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
