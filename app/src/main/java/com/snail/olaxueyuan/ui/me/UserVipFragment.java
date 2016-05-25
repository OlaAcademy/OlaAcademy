package com.snail.olaxueyuan.ui.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.ui.SuperFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mingge on 2016/5/20.
 */
public class UserVipFragment extends SuperFragment {
    View rootView;
    @Bind(R.id.month_current_money)
    TextView monthCurrentMoney;
    @Bind(R.id.month_old_money)
    TextView monthOldMoney;
    @Bind(R.id.month_icon)
    ImageView monthIcon;
    @Bind(R.id.month_vip)
    RelativeLayout monthVip;
    @Bind(R.id.year_current_money)
    TextView yearCurrentMoney;
    @Bind(R.id.year_old_money)
    TextView yearOldMoney;
    @Bind(R.id.year_icon)
    ImageView yearIcon;
    @Bind(R.id.year_vip)
    RelativeLayout yearVip;
    @Bind(R.id.alipay_radio)
    ImageButton alipayRadio;
    @Bind(R.id.alipay_view)
    RelativeLayout alipayView;
    @Bind(R.id.wechat_radio)
    ImageButton wechatRadio;
    @Bind(R.id.wechat_view)
    RelativeLayout wechatView;
    @Bind(R.id.buy_vip)
    Button buyVip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_vip, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        monthIcon.setSelected(true);
        alipayView.setSelected(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.month_vip, R.id.year_vip, R.id.alipay_view, R.id.wechat_view, R.id.buy_vip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.month_vip:
                break;
            case R.id.year_vip:
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
