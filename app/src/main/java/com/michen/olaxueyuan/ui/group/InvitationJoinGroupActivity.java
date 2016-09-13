package com.michen.olaxueyuan.ui.group;

import android.os.Bundle;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;

/**
 * 邀请加入作业群
 */
public class InvitationJoinGroupActivity extends SEBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_join_group);
        initView();
    }

    private void initView() {
        setTitleText("邀请加入作业群");
    }
}
