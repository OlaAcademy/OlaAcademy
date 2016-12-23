package com.michen.olaxueyuan.ui.question;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;

public class CommonMessageActivity extends SEBaseActivity {
    private int type;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_message_view);
        type = getIntent().getIntExtra("type", 1);
        initView();
    }

    private void initView() {
        transaction = getSupportFragmentManager().beginTransaction();
        switch (type) {
            case 1:
                setTitleText("评论列表");
                transaction.add(R.id.content, new CommentListFragment());
                break;
            case 2:
                setTitleText("赞了");
                transaction.add(R.id.content, new PraiseListFragment());
                break;
            case 3:
            default:
                setTitleText("系统消息");
                transaction.add(R.id.content, new SystemMessageFragment());
                break;
        }
        transaction.commitAllowingStateLoss();
    }
}
