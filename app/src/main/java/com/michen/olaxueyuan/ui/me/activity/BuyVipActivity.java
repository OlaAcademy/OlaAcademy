package com.michen.olaxueyuan.ui.me.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.UserAlipayResult;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.protocol.result.UserWXpayResult;
import com.michen.olaxueyuan.protocol.result.VipPriceResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.course.pay.weixin.MD5;
import com.michen.olaxueyuan.ui.course.pay.weixin.WxPayUtile;
import com.michen.olaxueyuan.ui.course.pay.zhifubao.PayResult;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshScrollView;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BuyVipActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener {
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
    @Bind(R.id.all_year_current_money)
    TextView allYearCurrentMoney;
    @Bind(R.id.all_year_old_money)
    TextView allYearOldMoney;
    @Bind(R.id.all_year_icon)
    ImageView allYearIcon;
    @Bind(R.id.scroll)
    PullToRefreshScrollView scroll;
    @Bind(R.id.season_current_money)
    TextView seasonCurrentMoney;
    @Bind(R.id.season_icon)
    ImageView seasonIcon;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    private static final int PAY_BY_ALIPAY = 101;//使用支付宝支付
    private static final int PAY_BY_WECHAT = 102;//使用微信支付
    private static Context context;
    private int payType = PAY_BY_WECHAT;//最终支付方式,默认支付宝
    public static final String MOTH_VIP = "1";//1月度会员
    public static final String YEAR_VIP = "2";//2 半年会员
    public static final String SUPER_VIP = "3";//3 整套视频
    public static final String ALL_YEAR_VIP = "4";//4年度会员
    public static final String SEASON_VIP = "5";//5季度会员
    private String type = MOTH_VIP;// 1月度会员 2 半年度会员 3 整套视频 4年度会员 5季度会员
    private String userId = "126";//测试的userId
    private String versionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setTitleText("欧拉会员");
        setContentView(R.layout.activity_buy_vip);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        getVipPrice();
    }

    private void initView() {
        monthIcon.setSelected(true);
        wechatView.setSelected(true);
        scroll.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        scroll.setOnRefreshListener(this);
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            versionName = "1.2.9";
        }
       /* SpannableString spannedMonth = new SpannableString(monthOldMoney.getText().toString().trim());
        spannedMonth.setSpan(new StrikethroughSpan(), 0, monthOldMoney.getText().toString().trim().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        monthOldMoney.setText(spannedMonth);
        SpannableString spannedYear = new SpannableString(yearOldMoney.getText().toString().trim());
        spannedYear.setSpan(new StrikethroughSpan(), 0, yearOldMoney.getText().toString().trim().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        yearOldMoney.setText(spannedYear);*/
    }

    @OnClick({R.id.month_vip, R.id.year_vip, R.id.alipay_view, R.id.wechat_view
            , R.id.buy_vip, R.id.all_year_vip, R.id.season_vip})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.month_vip:
                type = MOTH_VIP;
                monthIcon.setSelected(true);
                seasonIcon.setSelected(false);
                yearIcon.setSelected(false);
                allYearIcon.setSelected(false);
                break;
            case R.id.season_vip:
                type = SEASON_VIP;
                monthIcon.setSelected(false);
                seasonIcon.setSelected(true);
                yearIcon.setSelected(false);
                allYearIcon.setSelected(false);
                break;
            case R.id.year_vip:
                type = YEAR_VIP;
                monthIcon.setSelected(false);
                seasonIcon.setSelected(false);
                yearIcon.setSelected(true);
                allYearIcon.setSelected(false);
                break;
            case R.id.all_year_vip:
                type = ALL_YEAR_VIP;
                monthIcon.setSelected(false);
                seasonIcon.setSelected(false);
                yearIcon.setSelected(false);
                allYearIcon.setSelected(true);
                break;
            case R.id.alipay_view:
                payType = PAY_BY_ALIPAY;
                alipayRadio.setSelected(true);
                wechatRadio.setSelected(false);
                break;
            case R.id.wechat_view:
                payType = PAY_BY_WECHAT;
                alipayRadio.setSelected(false);
                wechatRadio.setSelected(true);
                break;
            case R.id.buy_vip:
                switch (payType) {
                    case PAY_BY_WECHAT:
                        payForWXRequest();
                        break;
                    case PAY_BY_ALIPAY:
                    default:
                        payForAlipay();
                        break;
                }
                break;
        }
    }

    public void payForAlipay() {
        userId = SEAuthManager.getInstance().getAccessUser().getId();
        SEUserManager.getInstance().getAliOrderInfo(userId, type, "", "0", versionName, new Callback<UserAlipayResult>() {
            @Override
            public void success(UserAlipayResult userAlipayResult, Response response) {
                if (!BuyVipActivity.this.isFinishing()) {
                    if (userAlipayResult != null && userAlipayResult.getApicode() == 10000) {
                        final UserAlipayResult.ResultBean orderInfoBean = userAlipayResult.getResult();
                        if (orderInfoBean != null) {
                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    // 构造PayTask 对象
                                    PayTask alipay = new PayTask(BuyVipActivity.this);
                                    String result = alipay.pay(orderInfoBean.getOrderInfo(), true);
//                                Logger.e("result==" + result);
                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };
                            // 必须异步调用
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        }
                    } else {
                        ToastUtil.showToastShort(BuyVipActivity.this, userAlipayResult.getMessage());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (!BuyVipActivity.this.isFinishing()) {
                    ToastUtil.showToastShort(BuyVipActivity.this, R.string.data_request_fail);
                }
            }
        });
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(BuyVipActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new UserLoginNoticeModule(true)); //刷新
                        finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(BuyVipActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(BuyVipActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(BuyVipActivity.this, "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                }
                case 100:
                    ToastUtil.showToastShort(BuyVipActivity.this, R.string.alipay_not_install);
                    break;
                default:
                    break;
            }
        }
    };

    public void payForWXRequest() {
        userId = SEAuthManager.getInstance().getAccessUser().getId();
        SEUserManager.getInstance().getWXPayReq(userId, type, "", "0", versionName, new Callback<UserWXpayResult>() {
            @Override
            public void success(UserWXpayResult wxPayModule, Response response) {
                if (!BuyVipActivity.this.isFinishing()) {
                    if (wxPayModule != null && wxPayModule.getApicode() == 10000) {
                        final UserWXpayResult.ResultBean wxpayResult = wxPayModule.getResult();
                        if (wxpayResult != null) {
                            //调用微信支付
                            WxPayUtile.getInstance(BuyVipActivity.this, "100",
                                    "http://121.40.35.3/test", "欧拉会员", wxpayResult,
                                    genOutTradNo()).doPay();
                        }
                    } else {
                        ToastUtil.showToastShort(BuyVipActivity.this, wxPayModule.getMessage());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (!BuyVipActivity.this.isFinishing()) {
                    ToastUtil.showToastShort(BuyVipActivity.this, R.string.data_request_fail);
                }
            }
        });
    }

    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }


    // EventBus 微信支付成功通知
    public void onEventMainThread(Boolean payResult) {
        if (payResult) {
            EventBus.getDefault().post(new UserLoginNoticeModule(true)); //刷新
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        SEAPP.dismissAllowingStateLoss();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        getVipPrice();
    }

    private void getVipPrice() {
        SEAPP.showCatDialog(this);
        SEUserManager.getInstance().getVIPPrice(new Callback<VipPriceResult>() {
            @Override
            public void success(VipPriceResult vipPriceResult, Response response) {
                if (mContext != null && !BuyVipActivity.this.isFinishing()) {
                    scroll.onRefreshComplete();
                    SEAPP.dismissAllowingStateLoss();
                    if (vipPriceResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(mContext, vipPriceResult.getMessage());
                    } else {
                        monthCurrentMoney.setText(getString(R.string.month_vip_price, vipPriceResult.getResult().getMonthPrice()));
                        seasonCurrentMoney.setText(getString(R.string.season_vip_price, vipPriceResult.getResult().getSeasonPrice()));
                        yearCurrentMoney.setText(getString(R.string.half_year_vip_price, vipPriceResult.getResult().getHalfYearPrice()));
                        allYearCurrentMoney.setText(getString(R.string.all_year_vip_price, vipPriceResult.getResult().getYearPrice()));
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !BuyVipActivity.this.isFinishing()) {
                    scroll.onRefreshComplete();
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    @OnClick(R.id.season_vip)
    public void onClick() {
    }
}
