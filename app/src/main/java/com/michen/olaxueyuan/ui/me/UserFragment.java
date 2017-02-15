package com.michen.olaxueyuan.ui.me;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.event.ShowBottomTabDotEvent;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.model.SEUser;
import com.michen.olaxueyuan.protocol.result.CheckinStatusResult;
import com.michen.olaxueyuan.protocol.result.SEUserResult;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.protocol.result.VipPriceResult;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.me.activity.BuyVipActivity;
import com.michen.olaxueyuan.ui.me.activity.CoinHomePageActivity;
import com.michen.olaxueyuan.ui.me.activity.DownloadListActivity;
import com.michen.olaxueyuan.ui.me.activity.MyBuyGoodsActivity;
import com.michen.olaxueyuan.ui.me.activity.MyCourseCollectActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.michen.olaxueyuan.ui.me.activity.UserUpdateActivity;
import com.michen.olaxueyuan.ui.me.activity.WrongTopicSetActivity;
import com.michen.olaxueyuan.ui.setting.SettingActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshScrollView;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mingge on 2016/9/21.
 */

public class UserFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener {
    View rootView;
    @Bind(R.id.avatar)
    RoundRectImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.remain_days)
    TextView remainDays;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.headLL)
    RelativeLayout headLL;
    @Bind(R.id.wrong_topic_layout)
    RelativeLayout wrongTopicLayout;
    @Bind(R.id.buy_vip_layout)
    RelativeLayout buyVipLayout;
    @Bind(R.id.my_buy_layout)
    RelativeLayout myBuyLayout;
    @Bind(R.id.my_collect_layout)
    RelativeLayout myCollectLayout;
    @Bind(R.id.my_download_layout)
    RelativeLayout myDownloadLayout;
    @Bind(R.id.service_email_layout)
    RelativeLayout serviceEmailLayout;
    @Bind(R.id.ola_coin)
    TextView olaCoin;
    @Bind(R.id.my_coin_text)
    TextView myCoinText;
    @Bind(R.id.sign_dot)
    ImageView signDot;
    @Bind(R.id.root_scroll)
    PullToRefreshScrollView rootScroll;
    @Bind(R.id.buy_vip_text)
    TextView buyVipText;
    @Bind(R.id.qq_group_layout)
    RelativeLayout qqGroupLayout;

    private final static int EDIT_USER_INFO = 0x1010;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        initView();
        return rootView;
    }

    private void initView() {
        avatar.setRectAdius(100);
        rootScroll.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        rootScroll.setOnRefreshListener(this);
        qqGroupLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                copyQQGroupNum();
                return false;
            }
        });
    }


    // EventBus 回调
    public void onEventMainThread(UserLoginNoticeModule module) {
        if (avatar == null) {
            return;
        }
        if (!module.isLogin) {
            updateHeadView(null);
            EventBus.getDefault().post(new ShowBottomTabDotEvent(4, false));
        } else {
            fetchUserInfo();
            getSignStatus();
        }
    }

    @OnClick({R.id.right_response, R.id.headLL, R.id.wrong_topic_layout, R.id.buy_vip_layout
            , R.id.my_buy_layout, R.id.my_collect_layout, R.id.my_download_layout
            , R.id.service_email_layout, R.id.my_coin_layout, R.id.avatar, R.id.qq_group_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_response:
                Utils.jumpLoginOrNot(getActivity(), SettingActivity.class);
                break;
            case R.id.headLL:
                headViewClick();
                break;
            case R.id.wrong_topic_layout:
//                Utils.jumpLoginOrNot(getActivity(), WrongTopicActivity.class);
                Utils.jumpLoginOrNot(getActivity(), WrongTopicSetActivity.class);
                break;
            case R.id.buy_vip_layout:
                Utils.jumpLoginOrNot(getActivity(), BuyVipActivity.class);
                break;
            case R.id.my_buy_layout:
                Utils.jumpLoginOrNot(getActivity(), MyBuyGoodsActivity.class);
                break;
            case R.id.my_collect_layout:
                Utils.jumpLoginOrNot(getActivity(), MyCourseCollectActivity.class);
                break;
            case R.id.my_download_layout:
                Utils.jumpLoginOrNot(getActivity(), DownloadListActivity.class);
                break;
            case R.id.my_coin_layout:
                Utils.jumpLoginOrNot(getActivity(), CoinHomePageActivity.class);
                break;
            case R.id.service_email_layout:
                sendEmail();
                break;
            case R.id.avatar:
                if (SEAuthManager.getInstance().getAccessUser() != null && !TextUtils.isEmpty(SEAuthManager.getInstance().getAccessUser().getAvator())) {
                    PictureUtils.viewPictures(getActivity(), SEAuthManager.getInstance().getAccessUser().getAvator());
                }
                break;
            case R.id.qq_group_layout:
                copyQQGroupNum();
                break;
            default:
                break;
        }
    }

    private void copyQQGroupNum() {
        ClipboardManager mClipboardManager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("QQGroup", getActivity().getString(R.string.qq_group_num));
        mClipboardManager.setPrimaryClip(mClipData);
        ToastUtil.showToastShort(getActivity(), "QQ群号已复制到剪切板");
    }

    private void sendEmail() {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse("mailto:" + getActivity().getString(R.string.service_olaxueyuan_com)));
        if (SEAuthManager.getInstance().isAuthenticated()) {
            data.putExtra(Intent.EXTRA_SUBJECT, SEAuthManager.getInstance().getAccessUser().getName());
            data.putExtra(Intent.EXTRA_TEXT, "手机号码：" + SEAuthManager.getInstance().getAccessUser().getPhone());
        }
        startActivity(data);
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchUserInfo();
        getSignStatus();
        getVipPrice();
    }

    private void fetchUserInfo() {
        SEUser user = SEAuthManager.getInstance().getAccessUser();
        if (user != null) {
            SEUserManager um = SEUserManager.getInstance();
            um.queryUserInfo(user.getId(), new Callback<SEUserResult>() {
                @Override
                public void success(SEUserResult result, Response response) {
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        rootScroll.onRefreshComplete();
                        updateHeadView(result.data);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        rootScroll.onRefreshComplete();
                    }
                }
            });
        } else {
            rootScroll.onRefreshComplete();
        }
    }

    private void headViewClick() {
        SEUser user = SEAuthManager.getInstance().getAccessUser();
        if (user == null) {
            Intent intent = new Intent(getActivity(), UserLoginActivity.class);
            intent.putExtra("isVisitor", 1);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getActivity(), UserUpdateActivity.class);
            startActivityForResult(intent, EDIT_USER_INFO);
        }
    }

    private void updateHeadView(SEUser userInfo) {
        if (userInfo == null) {
            setEmptyHeadView();
        } else {
            try {
                name.setText(userInfo.getName() != null ? userInfo.getName() : "小欧");
                remainDays.setText("会员" + userInfo.getVipTime() + "天");
                if (userInfo.getAvator() != null) {
                    String avatarUrl = "";
                    //                if (userInfo.getAvator().contains("jpg")||userInfo.getAvator().contains("gif")) {
                    if (userInfo.getAvator().contains(".")) {
                        avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + userInfo.getAvator();
                    } else {
                        avatarUrl = SEAPP.PIC_BASE_URL + userInfo.getAvator();
                    }
                    Picasso.with(getActivity())
                            .load(avatarUrl)
                            .placeholder(R.drawable.ic_default_avatar)
                            .error(R.drawable.ic_default_avatar)
                            .into(avatar);
                } else {
                    avatar.setBackgroundResource(R.drawable.ic_default_avatar);
                }
            } catch (Exception e) {
                e.printStackTrace();
                setEmptyHeadView();
            }

            try {
                FileOutputStream fos = SEConfig.getInstance().getContext().openFileOutput(SEAuthManager.AUTH_CONFIG_FILENAME, Context.MODE_PRIVATE);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(userInfo);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setEmptyHeadView() {
        name.setText("登录／注册");
        myCoinText.setText("");
        olaCoin.setText("0欧拉币");
        remainDays.setText("会员0天");
        signDot.setVisibility(View.INVISIBLE);
        avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_default_avatar));
    }

    private void getSignStatus() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            rootScroll.onRefreshComplete();
            return;
        }
        SEUserManager.getInstance().getCheckinStatus(userId, new Callback<CheckinStatusResult>() {
            @Override
            public void success(CheckinStatusResult checkinStatusResult, Response response) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    rootScroll.onRefreshComplete();
                    if (checkinStatusResult.getApicode() == 10000) {
                        olaCoin.setText(checkinStatusResult.getResult().getCoin() + "欧拉币");
                        myCoinText.setText(String.valueOf(checkinStatusResult.getResult().getCoin()));
                        if (checkinStatusResult.getResult().getStatus() == 1) {
                            signDot.setVisibility(View.INVISIBLE);
                        } else {
                            signDot.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    rootScroll.onRefreshComplete();
                }
            }
        });
    }

    private void getVipPrice() {
        SEUserManager.getInstance().getVIPPrice(new Callback<VipPriceResult>() {
            @Override
            public void success(VipPriceResult vipPriceResult, Response response) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    if (vipPriceResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(getActivity(), vipPriceResult.getMessage());
                    } else {
                        buyVipText.setText(vipPriceResult.getResult().getMonthPrice() + "元/月");
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        SEAPP.dismissAllowingStateLoss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        getVipPrice();
        fetchUserInfo();
        getSignStatus();
    }
}
