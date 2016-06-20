package com.snail.olaxueyuan.ui.course;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.Logger;
import com.snail.olaxueyuan.common.manager.TitleManager;
import com.snail.olaxueyuan.protocol.result.SystemCourseResult;
import com.snail.olaxueyuan.ui.activity.SuperActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaySystemVideoActivity extends SuperActivity {
    TitleManager titleManager;
    @Bind(R.id.left_return)
    TextView leftReturn;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.leanstage)
    TextView leanstage;
    @Bind(R.id.org)
    TextView org;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.alipay_icon)
    ImageView alipayIcon;
    @Bind(R.id.alipay_radio)
    ImageButton alipayRadio;
    @Bind(R.id.alipay_view)
    RelativeLayout alipayView;
    @Bind(R.id.wechat_icon)
    ImageView wechatIcon;
    @Bind(R.id.wechat_radio)
    ImageButton wechatRadio;
    @Bind(R.id.wechat_view)
    RelativeLayout wechatView;
    @Bind(R.id.buy_vip)
    Button buyVip;
    SystemCourseResult.ResultEntity resultEntity;
    private String courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_system_video_view);
        courseId = getIntent().getStringExtra("courseId");
        resultEntity = (SystemCourseResult.ResultEntity) getIntent().getSerializableExtra("ResultEntity");
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        titleManager = new TitleManager(this, "支付订单", this, true);

    }

    @Override
    public void initData() {
        if (resultEntity != null) {
            name.setText(resultEntity.getName());
            leanstage.setText("有效期:" + resultEntity.getLeanstage() + "(" + resultEntity.getTotaltime() + ")");
            org.setText(resultEntity.getOrg());
            Logger.e("price=="+resultEntity.getPrice());
            price.setText(String.valueOf(resultEntity.getPrice()));

        }
    }

    @OnClick({R.id.left_return, R.id.right_response, R.id.alipay_view, R.id.wechat_view, R.id.buy_vip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_return:
                finish();
                break;
            case R.id.right_response:
                break;
            case R.id.alipay_view:
                break;
            case R.id.wechat_view:
                break;
            case R.id.buy_vip:
                break;
        }
    }
}
