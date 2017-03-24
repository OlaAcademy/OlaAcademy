package com.michen.olaxueyuan.ui.circle;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.SubListView;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.result.UserPostListResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.PersonalHomePageListAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshScrollView;
import com.snail.svprogresshud.SVProgressHUD;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PersonalHomePageActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener {

    @Bind(R.id.avatar)
    RoundRectImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.sign_text)
    TextView signText;
    @Bind(R.id.reply_text)
    TextView replyText;
    @Bind(R.id.reply_indicator)
    View replyIndicator;
    @Bind(R.id.reply_layout)
    RelativeLayout replyLayout;
    @Bind(R.id.deploy_text)
    TextView deployText;
    @Bind(R.id.deploy_indicator)
    View deployIndicator;
    @Bind(R.id.deploy_layout)
    RelativeLayout deployLayout;
    @Bind(R.id.listview)
    SubListView listview;
    @Bind(R.id.scroll_view)
    PullToRefreshScrollView scrollView;

    private PersonalHomePageListAdapter adapter;
    private UserPostListResult postListResult;
    private int userId;
    private String avatarUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_home_page);
        ButterKnife.bind(this);
        initView();
        fetchData();
    }

    private void initView() {
        setTitleText("个人主页");
        userId = getIntent().getIntExtra("userId", 0);
        avatar.setRectAdius(200);
        scrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        scrollView.setOnRefreshListener(this);
        adapter = new PersonalHomePageListAdapter(this);
        listview.setDivider(null);
        listview.setAdapter(adapter);
        changeTab(true, false);
    }

    private void changeTab(boolean deploy, boolean reply) {
        deployText.setSelected(deploy);
        deployIndicator.setSelected(deploy);
        replyText.setSelected(reply);
        replyIndicator.setSelected(reply);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        fetchData();
    }

    private void fetchData() {
        SEAPP.showCatDialog(this);
        QuestionCourseManager.getInstance().getUserPostList(String.valueOf(userId), new Callback<UserPostListResult>() {
            @Override
            public void success(UserPostListResult userPostListResult, Response response) {
                if (mContext != null && !PersonalHomePageActivity.this.isFinishing()) {
                    scrollView.onRefreshComplete();
                    SEAPP.dismissAllowingStateLoss();
                    if (userPostListResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(mContext, userPostListResult.getMessage(), 2.0f);
                    } else {
                        scrollView.scrollTo(0, 0);
                        postListResult = userPostListResult;
                        updateUI(userPostListResult.getResult());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !PersonalHomePageActivity.this.isFinishing()) {
                    scrollView.onRefreshComplete();
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    private void updateUI(UserPostListResult.ResultBean result) {
        if (result.getAvator().contains("http://")) {
            avatarUrl = result.getAvator();
        } else if (result.getAvator().contains(".")) {
            avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + result.getAvator();
        } else {
            avatarUrl = SEAPP.PIC_BASE_URL + result.getAvator();
        }
        Picasso.with(mContext).load(avatarUrl)
                .placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar)
                .resize(Utils.dip2px(mContext, 50), Utils.dip2px(mContext, 50)).into(avatar);
        name.setText(result.getName());
        signText.setText(result.getSign());
        adapter.updateData(result.getDeployList(), 1);
        scrollView.scrollTo(0, 0);
    }


    @OnClick({R.id.avatar, R.id.reply_layout, R.id.deploy_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.avatar:
                if (postListResult != null && !TextUtils.isEmpty(postListResult.getResult().getAvator())) {
                    PictureUtils.viewPictures(mContext, postListResult.getResult().getAvator());
                }
                break;
            case R.id.reply_layout:
                if (postListResult != null) {
                    changeTab(false, true);
                    adapter.updateData(postListResult.getResult().getReplyList(), 2);
                }
                break;
            case R.id.deploy_layout:
                if (postListResult != null) {
                    changeTab(true, false);
                    adapter.updateData(postListResult.getResult().getDeployList(), 1);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
