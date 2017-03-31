package com.michen.olaxueyuan.ui.question;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.TitleManager;

import butterknife.OnClick;
import cn.leancloud.chatkit.activity.LCIMConversationListFragment;

public class LenCloudMessageActivity extends FragmentActivity implements View.OnClickListener {
    FragmentTransaction transaction;
    TitleManager titleManager;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = View.inflate(this, R.layout.activity_len_cloud_message, null);
        setContentView(view);
        initView();
    }

    public void initView() {
        transaction = getSupportFragmentManager().beginTransaction();
        titleManager = new TitleManager(this, "聊天历史", this, true);
        transaction.add(R.id.content, new LCIMConversationListFragment());
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.left_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_return:
                finish();
                break;
        }
    }
}
