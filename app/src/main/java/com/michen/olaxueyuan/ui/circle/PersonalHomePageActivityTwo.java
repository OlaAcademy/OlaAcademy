package com.michen.olaxueyuan.ui.circle;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.result.UserPostListResult;
import com.michen.olaxueyuan.ui.activity.SuperActivity;
import com.snail.svprogresshud.SVProgressHUD;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PersonalHomePageActivityTwo extends SuperActivity {

    @Bind(R.id.head_bg)
    RoundRectImageView headBg;
    @Bind(R.id.head_image)
    RoundRectImageView headImage;
    @Bind(R.id.left_return)
    TextView leftReturn;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.role)
    TextView role;
    @Bind(R.id.vip_icon)
    ImageView vipIcon;
    @Bind(R.id.sign)
    TextView sign;
    @Bind(R.id.num_focus)
    TextView numFocus;
    @Bind(R.id.num_fans)
    TextView numFans;
    private int userId;
    private UserPostListResult postListResult;
    private String avatarUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getIntent().getIntExtra("userId", 0);
        setContentView(R.layout.activity_personal_home_page_two);
        ButterKnife.bind(this);
        fetchData();
    }

    @Override
    public void initView() {
        headBg.setRectAdius(200);
        headImage.setRectAdius(200);
    }

    @Override
    public void initData() {

    }

    private void fetchData() {
        SEAPP.showCatDialog(this);
        QuestionCourseManager.getInstance().getUserPostList(String.valueOf(userId), new Callback<UserPostListResult>() {
            @Override
            public void success(UserPostListResult userPostListResult, Response response) {
                if (mContext != null && !PersonalHomePageActivityTwo.this.isFinishing()) {
//                    scrollView.onRefreshComplete();
                    SEAPP.dismissAllowingStateLoss();
                    if (userPostListResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(mContext, userPostListResult.getMessage(), 2.0f);
                    } else {
//                        scrollView.scrollTo(0, 0);
                        postListResult = userPostListResult;
                        updateUI(userPostListResult.getResult());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !PersonalHomePageActivityTwo.this.isFinishing()) {
//                    scrollView.onRefreshComplete();
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    private void updateUI(UserPostListResult.ResultBean result) {
        if (result.getAvator().contains(".")) {
            avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + result.getAvator();
        } else {
            avatarUrl = SEAPP.PIC_BASE_URL + result.getAvator();
        }
        Picasso.with(mContext).load(avatarUrl)
                .placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar)
                .resize(Utils.dip2px(mContext, 50), Utils.dip2px(mContext, 50)).into(headImage);
        title.setText(result.getName());
        role.setText(result.getRole());
        sign.setText(result.getSign());
        numFocus.setText(String.valueOf(result.getAttendNum()));
        numFans.setText(String.valueOf(result.getFollowerNum()));
//        adapter.updateData(result.getDeployList(), 1);
//        scrollView.scrollTo(0, 0);
    }

    @OnClick({R.id.left_return, R.id.right_response, R.id.head_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_image:
                if (postListResult != null && !TextUtils.isEmpty(postListResult.getResult().getAvator())) {
                    PictureUtils.viewPictures(mContext, postListResult.getResult().getAvator());
                }
                break;
            case R.id.left_return:
                finish();
                break;
            case R.id.right_response:
                break;
        }
    }
}
