package com.snail.olaxueyuan.ui.me.subfragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.protocol.manager.SEAuthManager;
import com.snail.olaxueyuan.protocol.manager.SEUserManager;
import com.snail.olaxueyuan.protocol.result.UserAlipayResult;
import com.snail.olaxueyuan.protocol.result.UserWXpayResult;
import com.snail.olaxueyuan.ui.SuperFragment;
import com.snail.olaxueyuan.ui.course.pay.weixin.MD5;
import com.snail.olaxueyuan.ui.course.pay.weixin.WxPayUtile;
import com.snail.olaxueyuan.ui.course.pay.zhifubao.PayResult;

import java.text.DecimalFormat;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;
    private static final int PAY_BY_ALIPAY = 101;//使用支付宝支付
    private static final int PAY_BY_WECHAT = 102;//使用微信支付
    private static Context context;
    private int payType = PAY_BY_ALIPAY;//最终支付方式,默认支付宝
    private String price;
    public static final String MOTH_VIP = "1";//1月度会员
    public static final String YEAR_VIP = "2";//2 年度会员
    public static final String SUPER_VIP = "3";//3 整套视频
    private String type = MOTH_VIP;// 1月度会员 2 年度会员 3 整套视频
    private String userId = "126";//测试的userId

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        rootView = inflater.inflate(R.layout.fragment_user_vip, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        monthIcon.setSelected(true);
        alipayView.setSelected(true);
        SpannableString spannedMonth = new SpannableString(monthOldMoney.getText().toString().trim());
        spannedMonth.setSpan(new StrikethroughSpan(), 0, monthOldMoney.getText().toString().trim().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        monthOldMoney.setText(spannedMonth);
        SpannableString spannedYear = new SpannableString(yearOldMoney.getText().toString().trim());
        spannedYear.setSpan(new StrikethroughSpan(), 0, yearOldMoney.getText().toString().trim().length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        yearOldMoney.setText(spannedYear);
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
                type = MOTH_VIP;
                monthIcon.setSelected(true);
                yearIcon.setSelected(false);
                break;
            case R.id.year_vip:
                type = YEAR_VIP;
                monthIcon.setSelected(false);
                yearIcon.setSelected(true);
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
        price = "0.01";
        userId = SEAuthManager.getInstance().getAccessUser().getId();
        SEUserManager.getInstance().getAliOrderInfo(price, userId, type, "", "123", new Callback<UserAlipayResult>() {
            @Override
            public void success(UserAlipayResult userAlipayResult, Response response) {
                if (userAlipayResult != null && userAlipayResult.getApicode() == 10000) {
                    final UserAlipayResult.ResultBean orderInfoBean = userAlipayResult.getResult();
                    if (orderInfoBean != null) {
                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                // 构造PayTask 对象
                                PayTask alipay = new PayTask(getActivity());
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
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
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
                        Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(getActivity(), "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(getActivity(), "检查结果为：" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                }
                case 100:
                    ToastUtil.showToastShort(getActivity(), R.string.alipay_not_install);
                    break;
                default:
                    break;
            }
        }

        ;
    };

    public void payForWXRequest() {
        price = "0.01";
        userId = SEAuthManager.getInstance().getAccessUser().getId();
        double pricess = Double.parseDouble(price) * 100;
        final String prices = new DecimalFormat("###").format(pricess);
        SEUserManager.getInstance().getWXPayReq(prices, userId, type, "", "123", new Callback<UserWXpayResult>() {
            @Override
            public void success(UserWXpayResult wxPayModule, Response response) {
                if (wxPayModule != null && wxPayModule.getApicode() == 10000) {
                    final UserWXpayResult.ResultBean wxpayResult = wxPayModule.getResult();
                    if (wxpayResult != null) {
                        //调用微信支付
                        WxPayUtile.getInstance(getActivity(), prices,
                                "http://121.40.35.3/test", "测试商品", wxpayResult,
                                genOutTradNo()).doPay();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
            }
        });
    }

    private String genOutTradNo() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    //微信支付回掉
    public static Handler handler = new Handler(new Handler.Callback() {
        //	msg.what== 0 ：表示支付成功
        //		msg.what== -1 ：表示支付失败
        //		msg.what== -2 ：表示取消支付
        @Override
        public boolean handleMessage(Message msg) {
            // TODO Auto-generated method stub

            switch (msg.what) {
                case 800://商户订单号重复或生成错误
                    ToastUtil.showToastShort(context, "商户订单号重复或生成错误");
                    break;
                case 0://支付成功
                    ToastUtil.showToastShort(context, "支付成功");
                    break;
                case -1://支付失败
                    ToastUtil.showToastShort(context, "支付失败");
                    break;
                case -2://取消支付
                    ToastUtil.showToastShort(context, "取消支付");
                    break;
                default:
                    ToastUtil.showToastShort(context, "支付失败");
                    break;
            }
            return false;
        }
    });

}
