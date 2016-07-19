package com.michen.olaxueyuan.ui.me;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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
import com.michen.olaxueyuan.ui.me.activity.DownloadListActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.michen.olaxueyuan.ui.me.activity.UserUpdateActivity;
import com.michen.olaxueyuan.ui.me.adapter.UserPageAdapter;
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
 * Created by mingge on 2016/5/20.
 */
public class UserFragment extends SuperFragment {
    View rootView;
    @Bind(R.id.left_icon)
    ImageView leftIcon;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.avatar)
    RoundRectImageView avatar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.is_vip)
    TextView isVip;
    @Bind(R.id.remain_days)
    TextView remainDays;
    @Bind(R.id.knowledge_bottom_indicator)
    ImageView knowledgeBottomIndicator;
    @Bind(R.id.knowledge_layout)
    RelativeLayout knowledgeLayout;
    @Bind(R.id.course_collect_bottom_indicator)
    ImageView courseCollectBottomIndicator;
    @Bind(R.id.course_collect_layout)
    RelativeLayout courseCollectLayout;
    @Bind(R.id.vip_bottom_indicator)
    ImageView vipBottomIndicator;
    @Bind(R.id.vip_layout)
    RelativeLayout vipLayout;
    @Bind(R.id.download_bottom_indicator)
    ImageView downloadBottomIndicator;
    @Bind(R.id.download_layout)
    RelativeLayout downloadLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private UserPageAdapter userPageAdapter;

    private final static int EDIT_USER_INFO = 0x1010;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        initView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchUserInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        avatar.setRectAdius(300);
        titleTv.setText(R.string.me);
        leftIcon.setVisibility(View.VISIBLE);
        leftIcon.setImageDrawable(getResources().getDrawable(R.drawable.icon_download));
        rightResponse.setVisibility(View.VISIBLE);

        userPageAdapter = new UserPageAdapter(getActivity().getFragmentManager());
        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(userPageAdapter);
        viewPager.setOnPageChangeListener(new ViewPagerListener());
        viewPager.setCurrentItem(0);
    }

    @OnClick({R.id.left_icon, R.id.right_response, R.id.headLL, R.id.knowledge_layout, R.id.course_collect_layout, R.id.vip_layout, R.id.download_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_icon:
                Intent downloadIntent = new Intent(getActivity(), DownloadListActivity.class);
                startActivity(downloadIntent);
                break;
            case R.id.right_response:
                Intent settingIntent = new Intent(getActivity(), SettingActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.headLL:
                headViewClick();
                break;
            case R.id.knowledge_layout:
                viewPager.setCurrentItem(0);
                changeTitleTab(0);
                break;
            case R.id.vip_layout:
                viewPager.setCurrentItem(1);
                changeTitleTab(1);
                break;
            case R.id.course_collect_layout:
                viewPager.setCurrentItem(2);
                changeTitleTab(2);
                break;
            case R.id.download_layout:
                viewPager.setCurrentItem(3);
                changeTitleTab(3);
                break;
        }
    }

    private void headViewClick(){
        SEUser user = SEAuthManager.getInstance().getAccessUser();
        if (user == null) {
            Intent intent = new Intent(getActivity(), UserLoginActivity.class);
            intent.putExtra("isVisitor", 1);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getActivity(), UserUpdateActivity.class);
            startActivityForResult(intent, EDIT_USER_INFO);
        }
    }

    // EventBus 回调
    public void onEventMainThread(UserLoginNoticeModule module) {
        if (!module.isLogin){
            updateHeadView(null);
        }else{
            fetchUserInfo();
        }
    }

    private void fetchUserInfo(){
        SEUser user = SEAuthManager.getInstance().getAccessUser();
        if (user!=null){
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

    private void updateHeadView(SEUser userInfo){
        if (userInfo==null){
            name.setText("登录／注册");
            remainDays.setText("还剩0天");
            avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_default_avatar));
        }else{
            name.setText(userInfo.getName());
            remainDays.setText("还剩"+ userInfo.getVipTime() + "天");
            String avatarUrl = "";
            if (userInfo.getAvator().indexOf("jpg")!=-1){
                avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/"+userInfo.getAvator();
            }else{
                avatarUrl = SEAPP.PIC_BASE_URL+userInfo.getAvator();
            }
            Picasso.with(getActivity())
                    .load(avatarUrl)
                    .placeholder(R.drawable.ic_default_avatar)
                    .error(R.drawable.ic_default_avatar)
                    .into(avatar);
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

    class ViewPagerListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            changeTitleTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

    private void changeTitleTab(int position) {
        knowledgeBottomIndicator.setVisibility(View.GONE);
        courseCollectBottomIndicator.setVisibility(View.GONE);
        vipBottomIndicator.setVisibility(View.GONE);
        downloadBottomIndicator.setVisibility(View.GONE);
        switch (position) {
            case 0:
                knowledgeBottomIndicator.setVisibility(View.VISIBLE);
                break;
            case 1:
                vipBottomIndicator.setVisibility(View.VISIBLE);
                break;
            case 2:
                courseCollectBottomIndicator.setVisibility(View.VISIBLE);
                break;
            case 3:
                downloadBottomIndicator.setVisibility(View.VISIBLE);
                break;
            default:
                knowledgeBottomIndicator.setVisibility(View.VISIBLE);
                break;
        }
    }

}
