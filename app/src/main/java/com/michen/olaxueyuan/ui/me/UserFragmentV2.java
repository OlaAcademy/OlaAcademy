package com.michen.olaxueyuan.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.model.SEUser;
import com.michen.olaxueyuan.protocol.result.SEUserResult;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
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

public class UserFragmentV2 extends SuperFragment {
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
    }


    // EventBus 回调
    public void onEventMainThread(UserLoginNoticeModule module) {
        if (!module.isLogin) {
            updateHeadView(null);
        } else {
            fetchUserInfo();
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
                    updateHeadView(result.data);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
