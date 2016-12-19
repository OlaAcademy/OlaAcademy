package com.michen.olaxueyuan.ui.question;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.MessageUnreadTotalCountResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 消息列表
 */
public class InformationListActivity extends SEBaseActivity {
    @Bind(R.id.answer_layout)
    RelativeLayout answerLayout;
    @Bind(R.id.praise_layout)
    RelativeLayout praiseLayout;
    @Bind(R.id.system_layout)
    RelativeLayout systemLayout;
    @Bind(R.id.answer_unread_dot)
    TextView answerUnreadDot;
    @Bind(R.id.praise_unread_dot)
    TextView praiseUnreadDot;
    @Bind(R.id.system_unread_dot)
    TextView systemUnreadDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation_list);
        ButterKnife.bind(this);
        setRightText("消息");
        fetchData();
    }

    private void fetchData() {
        SEAPP.showCatDialog(this);
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        QuestionCourseManager.getInstance().getUnreadTotalCount(userId, new Callback<MessageUnreadTotalCountResult>() {
            @Override
            public void success(MessageUnreadTotalCountResult messageUnreadTotalCountResult, Response response) {
                if (!InformationListActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    if (messageUnreadTotalCountResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(mContext, messageUnreadTotalCountResult.getMessage(), 2.0f);
                    } else {
                        answerUnreadDot.setText(messageUnreadTotalCountResult.getResult().getCircleCount());
                        praiseUnreadDot.setText(messageUnreadTotalCountResult.getResult().getPraiseCount());
                        systemUnreadDot.setText(messageUnreadTotalCountResult.getResult().getSystemCount());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (!InformationListActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    @OnClick({R.id.answer_layout, R.id.praise_layout, R.id.system_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.answer_layout:
                break;
            case R.id.praise_layout:
                break;
            case R.id.system_layout:
                startActivity(new Intent(this, MessageActivity.class));
                break;
        }
    }
}
