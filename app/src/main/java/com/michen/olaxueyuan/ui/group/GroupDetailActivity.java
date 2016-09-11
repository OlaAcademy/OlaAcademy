package com.michen.olaxueyuan.ui.group;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupDetailActivity extends SEBaseActivity {

    @Bind(R.id.group_avatar)
    RoundRectImageView groupAvatar;
    @Bind(R.id.group_title)
    TextView groupTitle;
    @Bind(R.id.invitation_friend)
    Button invitationFriend;
    @Bind(R.id.group_name_text)
    TextView groupNameText;
    @Bind(R.id.group_name_view)
    RelativeLayout groupNameView;
    @Bind(R.id.intro_view)
    RelativeLayout introView;
    @Bind(R.id.notice_hint_text)
    TextView noticeHintText;
    @Bind(R.id.notice_view)
    RelativeLayout noticeView;
    @Bind(R.id.group_member_text)
    TextView groupMemberText;
    @Bind(R.id.group_member_view)
    RelativeLayout groupMemberView;
    @Bind(R.id.work_history_text)
    TextView workHistoryText;
    @Bind(R.id.work_history_view)
    RelativeLayout workHistoryView;
    @Bind(R.id.dissolution_group)
    Button dissolutionGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
    }

    @OnClick({R.id.invitation_friend, R.id.group_name_view, R.id.intro_view, R.id.notice_view, R.id.group_member_view, R.id.work_history_view, R.id.dissolution_group})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.invitation_friend:
                break;
            case R.id.group_name_view:
                break;
            case R.id.intro_view:
                break;
            case R.id.notice_view:
                break;
            case R.id.group_member_view:
                break;
            case R.id.work_history_view:
                break;
            case R.id.dissolution_group:
                break;
        }
    }
}
