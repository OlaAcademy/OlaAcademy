package com.snail.olaxueyuan.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.RoundRectImageView;
import com.snail.olaxueyuan.common.manager.Logger;
import com.snail.olaxueyuan.common.manager.TitleManager;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.protocol.manager.SEAuthManager;
import com.snail.olaxueyuan.protocol.manager.SECourseManager;
import com.snail.olaxueyuan.protocol.result.GoodsOrderStatusResult;
import com.snail.olaxueyuan.protocol.result.SystemCourseResult;
import com.snail.olaxueyuan.ui.activity.SuperActivity;
import com.snail.svprogresshud.SVProgressHUD;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PayOrderSystemVideoActivity extends SuperActivity {
    TitleManager titleManager;
    @Bind(R.id.left_return)
    TextView leftReturn;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.leanstage)
    TextView leanstage;
    @Bind(R.id.avatar_course)
    RoundRectImageView avatarCourse;
    @Bind(R.id.org)
    TextView org;
    @Bind(R.id.suitto)
    TextView suitto;
    @Bind(R.id.totaltime)
    TextView totaltime;
    @Bind(R.id.detail)
    TextView detail;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.paynum)
    TextView paynum;
    @Bind(R.id.btn_buy)
    Button btnBuy;
    @Bind(R.id.bottom_view)
    RelativeLayout bottomView;
    private String courseId;
    SystemCourseResult.ResultEntity resultEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_system_video);
        performRefresh();
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        titleManager = new TitleManager(this, "支付订单", this, true);
        courseId = getIntent().getStringExtra("courseId");
        resultEntity = (SystemCourseResult.ResultEntity) getIntent().getSerializableExtra("ResultEntity");
    }

    @Override
    public void initData() {
        if (resultEntity == null) {
            return;
        }
        avatarCourse.setRectAdius(200);
        name.setText(resultEntity.getName());
        leanstage.setText(resultEntity.getLeanstage());
        Picasso.with(this).load(resultEntity.getImage()).into(avatarCourse);
        org.setText(resultEntity.getOrg());
        suitto.setText(resultEntity.getSuitto());
        totaltime.setText(String.valueOf(resultEntity.getTotaltime()));
        detail.setText(resultEntity.getDetail());
        price.setText(String.valueOf(resultEntity.getPrice()));
        paynum.setText(getString(R.string.num_buys, resultEntity.getPaynum()));

    }

    SECourseManager courseManager = SECourseManager.getInstance();

    public void performRefresh() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        Logger.e("courseId==" + courseId);
        courseManager.getOrderStatus(courseId, userId, new Callback<GoodsOrderStatusResult>() {
            @Override
            public void success(GoodsOrderStatusResult result, Response response) {
                Logger.json(result);
                if (result.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(PayOrderSystemVideoActivity.this, result.getMessage(), 2.0f);
                } else {

                }
            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtil.showToastShort(PayOrderSystemVideoActivity.this, R.string.data_request_fail);
            }
        });
    }

    @OnClick({R.id.left_return, R.id.right_response, R.id.btn_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_return:
                finish();
                break;
            case R.id.right_response:
                break;
            case R.id.btn_buy:
                Intent intent = new Intent(PayOrderSystemVideoActivity.this, PaySystemVideoActivity.class);
                intent.putExtra("courseId", courseId);
                intent.putExtra("ResultEntity", resultEntity);
                startActivity(intent);
                break;
        }
    }
}
