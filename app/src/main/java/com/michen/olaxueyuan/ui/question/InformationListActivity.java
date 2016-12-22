package com.michen.olaxueyuan.ui.question;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
    @Bind(R.id.answer_content)
    TextView answerContent;
    @Bind(R.id.praise_content)
    TextView praiseContent;
    @Bind(R.id.system_content)
    TextView systemContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation_list);
        ButterKnife.bind(this);
        setTitleText("消息");
        fetchData();
    }

    private void fetchData() {
        SEAPP.showCatDialog(this);
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            ToastUtil.showToastShort(this, "您还没有登录，请先登录");
            return;
        }
        QuestionCourseManager.getInstance().getUnreadTotalCount(userId, new Callback<MessageUnreadTotalCountResult>() {
            @Override
            public void success(MessageUnreadTotalCountResult messageUnreadTotalCountResult, Response response) {
                if (!InformationListActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    if (messageUnreadTotalCountResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(mContext, messageUnreadTotalCountResult.getMessage(), 2.0f);
                    } else {
                        refreshData(messageUnreadTotalCountResult.getResult());

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

    private void refreshData(MessageUnreadTotalCountResult.ResultBean result) {
        if (!TextUtils.isEmpty(result.getCircleCount()) && Integer.parseInt(result.getCircleCount()) > 0) {
            answerUnreadDot.setVisibility(View.VISIBLE);
            answerUnreadDot.setText(result.getCircleCount());
        }
        answerContent.setText(getString(R.string.pending_deal_comment, result.getCircleCount()));
        if (!TextUtils.isEmpty(result.getPraiseCount()) && Integer.parseInt(result.getPraiseCount()) > 0) {
            praiseUnreadDot.setVisibility(View.VISIBLE);
            praiseUnreadDot.setText(result.getPraiseCount());
        }
        praiseContent.setText(getString(R.string.num_praise, result.getPraiseCount()));
        if (!TextUtils.isEmpty(result.getSystemCount()) && Integer.parseInt(result.getSystemCount()) > 0) {
            systemUnreadDot.setVisibility(View.VISIBLE);
            systemUnreadDot.setText(result.getSystemCount());
        }
        systemContent.setText(getString(R.string.pending_deal_system_info, result.getSystemCount()));
    }


    @OnClick({R.id.answer_layout, R.id.praise_layout, R.id.system_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.answer_layout:
                startActivity(new Intent(this, CommentListActivity.class));
                break;
            case R.id.praise_layout:
                break;
            case R.id.system_layout:
                startActivity(new Intent(this, MessageActivity.class));
                break;
        }
    }
}
