package com.michen.olaxueyuan.ui.me.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.manager.DialogUtils;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.CheckInResult;
import com.michen.olaxueyuan.protocol.result.CheckinStatusResult;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.sharesdk.ShareManager;
import com.michen.olaxueyuan.sharesdk.ShareModel;
import com.michen.olaxueyuan.sharesdk.SharePopupWindow;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.course.commodity.CommodityActivity;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.UIHandler;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 积分首页
 */
public class CoinHomePageActivity extends SEBaseActivity implements PlatformActionListener, Handler.Callback {
    @Bind(R.id.coin_text)
    TextView coinText;
    @Bind(R.id.today_get_coin)
    TextView todayGetCoin;
    @Bind(R.id.ola_coin_detail_Text)
    TextView olaCoinDetailText;
    @Bind(R.id.ola_coin_rule_Text)
    TextView olaCoinRuleText;
    @Bind(R.id.to_sign)
    TextView toSign;
    @Bind(R.id.to_share)
    TextView toShare;
    @Bind(R.id.to_complete)
    TextView toComplete;
    @Bind(R.id.to_set)
    TextView toSet;
    @Bind(R.id.to_buy)
    TextView toBuy;
    private Context context;
    private boolean isShowSignDialog;//是否显示签到对话框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_home_page);
        ButterKnife.bind(this);
        context = this;
        initView();
    }

    private void initView() {
        setTitleText(getString(R.string.coin));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSignStatus();
    }

    private void getSignStatus() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            return;
        }
        SVProgressHUD.showInView(context, getString(R.string.request_running), true);
        SEUserManager.getInstance().getCheckinStatus(userId, new Callback<CheckinStatusResult>() {
            @Override
            public void success(CheckinStatusResult checkinStatusResult, Response response) {
                if (context != null) {
                    SVProgressHUD.dismiss(context);
                    if (checkinStatusResult.getApicode() == 10000) {
                        if (isShowSignDialog) {
                            showSignDialog(checkinStatusResult.getResult().getSignInDays() + "天", "", checkinStatusResult.getResult().getCoin() + "欧");
                            isShowSignDialog = false;
                        }
                        updateUI(checkinStatusResult);
                    } else {
                        ToastUtil.showToastShort(context, checkinStatusResult.getMessage());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (context != null) {
                    SVProgressHUD.dismiss(context);
                    ToastUtil.showToastShort(context, R.string.request_failed);
                }
            }
        });
    }

    private void updateUI(CheckinStatusResult checkinStatusResult) {
        coinText.setText(String.valueOf(checkinStatusResult.getResult().getCoin()));
        todayGetCoin.setText(getString(R.string.today_get_coin, String.valueOf(checkinStatusResult.getResult().getTodayCoin())));
        if (checkinStatusResult.getResult().getStatus() == 1) {
            toSign.setEnabled(false);
            toSign.setText(R.string.has_sign);
        } else {
            toSign.setEnabled(true);
            toSign.setText(R.string.sign);
        }
        if (checkinStatusResult.getResult().getProfileTask() == 1) {
            toComplete.setEnabled(false);
            toComplete.setText(R.string.has_complete);
        } else {
            toComplete.setEnabled(true);
            toComplete.setText(R.string.to_perfect);
        }
        if (checkinStatusResult.getResult().getVipTask() == 1) {
            toSet.setEnabled(false);
            toSet.setText(R.string.has_complete);
        } else {
            toSet.setEnabled(true);
            toSet.setText(R.string.to_set);
        }
        if (checkinStatusResult.getResult().getCourseTask() == 1) {
            toBuy.setEnabled(false);
            toBuy.setText(R.string.has_complete);
        } else {
            toBuy.setEnabled(true);
            toBuy.setText(R.string.to_buy);
        }
    }

    @OnClick({R.id.ola_coin_detail_Text, R.id.ola_coin_rule_Text, R.id.to_sign
            , R.id.to_share, R.id.to_complete, R.id.to_set, R.id.to_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ola_coin_detail_Text:
                startActivity(new Intent(this, CoinDetailActivity.class));
                break;
            case R.id.ola_coin_rule_Text:
                startActivity(new Intent(this, CoinRuleActivity.class));
                break;
            case R.id.to_sign:
                signIn();
                break;
            case R.id.to_share:
                shareFriend();
                break;
            case R.id.to_complete:
                startActivity(new Intent(this, UserUpdateActivity.class));
                break;
            case R.id.to_set:
                startActivity(new Intent(this, BuyVipActivity.class));
                break;
            case R.id.to_buy:
                startActivity(new Intent(this, CommodityActivity.class).putExtra("title", "精品课程").putExtra("type", "1"));
                break;
        }
    }

    private void signIn() {//签到
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            return;
        }
        SEUserManager.getInstance().checkin(userId, new Callback<CheckInResult>() {
            @Override
            public void success(CheckInResult checkInResult, Response response) {
                if (context != null) {
                    if (checkInResult.getApicode() == 10000) {
                        isShowSignDialog = true;
                        getSignStatus();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private void showSignDialog(String dayNum, String dayScore, String subjectNum) {
        DialogUtils.showSignDialog(context, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.close_icon:
                        break;
                    case R.id.wechat_sign:
                        shareFriends(0);
                        break;
                    case R.id.wechat_circle_sign:
                        shareFriends(1);
                        break;
                    case R.id.qq_friend_sign:
                        shareFriends(2);
                        break;
                    case R.id.qq_friend_space_sign:
                        shareFriends(3);
                        break;
                    case R.id.sina_sign:
                        shareFriends(4);
                        break;
                }
            }
        }, dayNum, dayScore, subjectNum);
    }

    private void shareFriends(int position) {
        ShareModel model = new ShareModel();
        model.setImageUrl(SEConfig.getInstance().getAPIBaseURL() + "/ola/images/icon.png");
        model.setText("欧拉联考——中国最权威的管理类联考学习平台");
        model.setTitle("欧拉学院");
        model.setUrl("http://app.olaxueyuan.com");
        ShareManager.getInstance().initManager(context, this, model).share(position);
    }

    @Override
    public void onCancel(Platform arg0, int arg1) {
        Message msg = new Message();
        msg.what = 0;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onComplete(Platform plat, int action,
                           HashMap<String, Object> res) {
        Message msg = new Message();
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
        shareComplete();
    }

    @Override
    public void onError(Platform arg0, int arg1, Throwable arg2) {
        Message msg = new Message();
        msg.what = 1;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        int what = msg.what;
        if (what == 1) {
            ToastUtil.showToastShort(context, "分享失败");
        }
        return false;
    }

    private void shareComplete() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            return;
        }
        SEUserManager.getInstance().share(userId, new Callback<SimpleResult>() {
            @Override
            public void success(SimpleResult simpleResult, Response response) {
                if (simpleResult.getApicode() == 10000) {
                    getSignStatus();
                } else {
                    ToastUtil.showToastShort(context, simpleResult.getMessage());
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private SharePopupWindow share;

    private void shareFriend() {
        share = new SharePopupWindow(context);
        share.setPlatformActionListener(CoinHomePageActivity.this);
        ShareModel model = new ShareModel();
        model.setImageUrl(SEConfig.getInstance().getAPIBaseURL() + "/ola/images/icon.png");
        model.setText("欧拉联考——中国最权威的管理类联考学习平台");
        model.setTitle("欧拉学院");
        model.setUrl("http://app.olaxueyuan.com");
        share.initShareParams(model);
        share.showShareWindow();
        // 显示窗口 (设置layout在PopupWindow中显示的位置)
        share.showAtLocation(CoinHomePageActivity.this.findViewById(R.id.activity_coin_home_page),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
