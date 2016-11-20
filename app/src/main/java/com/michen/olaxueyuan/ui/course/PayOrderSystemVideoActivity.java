package com.michen.olaxueyuan.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.protocol.result.SystemCourseResult;
import com.michen.olaxueyuan.protocol.result.SystemCourseResultEntity;
import com.michen.olaxueyuan.ui.activity.SuperActivity;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

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
    SystemCourseResultEntity resultEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_system_video);
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        titleManager = new TitleManager(this, "支付订单", this, true);
        courseId = getIntent().getStringExtra("courseId");
        resultEntity = (SystemCourseResultEntity) getIntent().getSerializableExtra("ResultEntity");
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

    // EventBus 回调
    public void onEventMainThread(Boolean payResult) {
        if (payResult){
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
