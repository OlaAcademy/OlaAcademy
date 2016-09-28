package com.michen.olaxueyuan.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.michen.olaxueyuan.common.manager.DialogUtils;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.event.ShowBottomTabDotEvent;
import com.michen.olaxueyuan.protocol.event.SignInEvent;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.model.SEUser;
import com.michen.olaxueyuan.protocol.result.CheckInResult;
import com.michen.olaxueyuan.protocol.result.CheckinStatusResult;
import com.michen.olaxueyuan.protocol.result.SEUserResult;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.sharesdk.ShareManager;
import com.michen.olaxueyuan.sharesdk.ShareModel;
import com.michen.olaxueyuan.ui.MainFragment;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.me.activity.BuyVipActivity;
import com.michen.olaxueyuan.ui.me.activity.DownloadListActivity;
import com.michen.olaxueyuan.ui.me.activity.MyBuyGoodsActivity;
import com.michen.olaxueyuan.ui.me.activity.MyCourseCollectActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.michen.olaxueyuan.ui.me.activity.UserUpdateActivity;
import com.michen.olaxueyuan.ui.me.activity.WrongTopicActivity;
import com.michen.olaxueyuan.ui.setting.SettingActivity;
import com.squareup.picasso.Picasso;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.UIHandler;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mingge on 2016/9/21.
 */

public class UserFragmentV2 extends SuperFragment implements PlatformActionListener, Handler.Callback {
    View rootView;
    @Bind(R.id.avatar)
    RoundRectImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.is_vip)
    TextView isVip;
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

    private final static int EDIT_USER_INFO = 0x1010;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_v2, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        initView();
        return rootView;
    }

    private void initView() {
        avatar.setRectAdius(100);
        getCheckinStatus();
    }


    // EventBus 回调
    public void onEventMainThread(UserLoginNoticeModule module) {
        if (!module.isLogin) {
            updateHeadView(null);
            EventBus.getDefault().post(new ShowBottomTabDotEvent(4, false));
        } else {
            fetchUserInfo();
            getCheckinStatus();
        }
    }

    /**
     * {@link MainFragment#signIn()}
     *
     * @param signInEvent
     */
    public void onEventMainThread(SignInEvent signInEvent) {
        if (signInEvent.isSign) {
            checkIn();
        }
    }

    @OnClick({R.id.right_response, R.id.headLL, R.id.wrong_topic_layout, R.id.buy_vip_layout, R.id.my_buy_layout, R.id.my_collect_layout, R.id.my_download_layout, R.id.service_email_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_response:
                isLogin(SettingActivity.class);
                break;
            case R.id.headLL:
                headViewClick();
                break;
            case R.id.wrong_topic_layout:
                isLogin(WrongTopicActivity.class);
                break;
            case R.id.buy_vip_layout:
                isLogin(BuyVipActivity.class);
                break;
            case R.id.my_buy_layout:
                isLogin(MyBuyGoodsActivity.class);
                break;
            case R.id.my_collect_layout:
                isLogin(MyCourseCollectActivity.class);
                break;
            case R.id.my_download_layout:
                isLogin(DownloadListActivity.class);
                break;
            case R.id.service_email_layout:
                //Todo 测试一下，然后关掉
//                showSignDialog("", "", "");
                break;
            default:
                break;
        }
    }

    private void isLogin(Class<?> cls) {
        if (SEAuthManager.getInstance().isAuthenticated()) {
            startActivity(new Intent(getActivity(), cls));
        } else {
            startActivity(new Intent(getActivity(), UserLoginActivity.class));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchUserInfo();
    }

    private void fetchUserInfo() {
        SEUser user = SEAuthManager.getInstance().getAccessUser();
        if (user != null) {
            SEUserManager um = SEUserManager.getInstance();
            um.queryUserInfo(user.getId(), new Callback<SEUserResult>() {
                @Override
                public void success(SEUserResult result, Response response) {
                    if (getActivity() != null) {
                        updateHeadView(result.data);
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }

            });
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
            name.setText("登录／注册");
            remainDays.setText("还剩0天");
            avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_default_avatar));
        } else {
            try {
                name.setText(userInfo.getName() != null ? userInfo.getName() : "小欧");
                remainDays.setText("还剩" + userInfo.getVipTime() + "天");
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
                name.setText("小欧");
                remainDays.setText("还剩0天");
                avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_default_avatar));
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

    private void getCheckinStatus() {//查看签到状态
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            return;
        }
        SEUserManager.getInstance().getCheckinStatus(userId, new Callback<CheckinStatusResult>() {
            @Override
            public void success(CheckinStatusResult checkinStatusResult, Response response) {
                if (getActivity() != null) {
                    if (checkinStatusResult.getApicode() == 10000) {
                        /**
                         * {@link com.michen.olaxueyuan.ui.MainFragment#onEventMainThread(ShowBottomTabDotEvent)}
                         */
                        if (checkinStatusResult.getResult().getStatus() == 1) {
                            EventBus.getDefault().post(new ShowBottomTabDotEvent(4, false));
                        } else {
                            EventBus.getDefault().post(new ShowBottomTabDotEvent(4, true));
                            isShowSignDialog = true;
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    boolean isShowSignDialog;//是否显示签到的dialog

    private void checkIn() {//签到
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated() && isShowSignDialog) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            return;
        }
        SEUserManager.getInstance().checkin(userId, new Callback<CheckInResult>() {
            @Override
            public void success(CheckInResult checkInResult, Response response) {
                if (getActivity() != null) {
                    if (checkInResult.getApicode() == 10000) {
                        showSignDialog("", "", "");
                        isShowSignDialog = false;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private void showSignDialog(String dayNum, String dayScore, String subjectNum) {
        DialogUtils.showSignDialog(getActivity(), new View.OnClickListener() {
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
        ShareManager.getInstance().initManager(getActivity(), this, model).share(position);
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
            ToastUtil.showToastShort(getActivity(), "分享失败");
//            Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
