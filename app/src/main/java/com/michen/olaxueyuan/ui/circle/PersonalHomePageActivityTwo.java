package com.michen.olaxueyuan.ui.circle;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.DateUtils;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.manager.HomeListManager;
import com.michen.olaxueyuan.protocol.manager.MCCircleManager;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.HomePageDeployPostBean;
import com.michen.olaxueyuan.protocol.result.PraiseCirclePostResult;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.protocol.result.UserPostListResult;
import com.michen.olaxueyuan.sharesdk.ShareModel;
import com.michen.olaxueyuan.sharesdk.SharePopupWindow;
import com.michen.olaxueyuan.ui.activity.SuperActivity;
import com.michen.olaxueyuan.ui.adapter.PersonalHomePageAdapter;
import com.michen.olaxueyuan.ui.circle.chat.CustomUserProvider;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.michen.olaxueyuan.ui.me.activity.UserUpdateActivity;
import com.snail.svprogresshud.SVProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.UIHandler;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PersonalHomePageActivityTwo extends SuperActivity implements PlatformActionListener, Handler.Callback {

    @Bind(R.id.head_bg)
    RoundRectImageView headBg;
    @Bind(R.id.head_image)
    RoundRectImageView headImage;
    @Bind(R.id.left_return)
    TextView leftReturn;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.role)
    TextView role;
    @Bind(R.id.vip_icon)
    ImageView vipIcon;
    @Bind(R.id.sign)
    TextView sign;
    @Bind(R.id.num_focus)
    TextView numFocus;
    @Bind(R.id.num_fans)
    TextView numFans;
    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;
    @Bind(R.id.focus)
    TextView focus;
    @Bind(R.id.chat)
    TextView chat;
    private int userId;
    private UserPostListResult postListResult;
    private String avatarUrl = "";
    private PersonalHomePageAdapter adapter;
    private List<HomePageDeployPostBean> list;
    private SharePopupWindow share;
    private LCChatKitUser lcChatKitUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getIntent().getIntExtra("userId", 0);
        setContentView(R.layout.activity_personal_home_page_two);
        ButterKnife.bind(this);
        fetchData();
    }

    @Override
    public void initView() {
        headBg.setRectAdius(200);
        headImage.setRectAdius(200);
        expandableListView.setDivider(null);
        expandableListView.setGroupIndicator(null);
        adapter = new PersonalHomePageAdapter(this);
        expandableListView.setAdapter(adapter);
        if (SEAuthManager.getInstance().isAuthenticated()) {
            String myUserId = SEAuthManager.getInstance().getAccessUser().getId();
            if (myUserId.equals(String.valueOf(userId))) {
                rightResponse.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
    }

    private void fetchData() {
        SEAPP.showCatDialog(this);
        QuestionCourseManager.getInstance().getUserPostList(String.valueOf(userId), new Callback<UserPostListResult>() {
            @Override
            public void success(UserPostListResult userPostListResult, Response response) {
                if (mContext != null && !PersonalHomePageActivityTwo.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    if (userPostListResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(mContext, userPostListResult.getMessage(), 2.0f);
                    } else {
                        postListResult = userPostListResult;
                        list.clear();
                        List<UserPostListResult.ResultBean.DeployListBean> deployList = postListResult.getResult().getDeployList();
                        HomePageDeployPostBean bean = new HomePageDeployPostBean();
                        List<UserPostListResult.ResultBean.DeployListBean> child = new ArrayList<>();
                        for (int i = 0; i < deployList.size(); i++) {
                            if (i == 0 || (i > 0 && DateUtils.getMonth(deployList.get(i).getTime(), 1) == DateUtils.getMonth(deployList.get(i - 1).getTime(), 1))) {
                                bean.setTime(deployList.get(i).getTime());
                                child.add(deployList.get(i));
                                bean.setChild(child);
                            } else {
                                list.add(bean);
                                child = new ArrayList<>();
                                bean = new HomePageDeployPostBean();
                                bean.setTime(deployList.get(i).getTime());
                                child.add(deployList.get(i));
                                bean.setChild(child);
                            }
                            if (i == deployList.size() - 1) {
                                list.add(bean);
                            }
                        }
                        adapter.updateList(list);
                        for (int i = 0; i < list.size(); i++) {
                            expandableListView.expandGroup(i);
                        }
                        updateUI(userPostListResult.getResult());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !PersonalHomePageActivityTwo.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    private void updateUI(UserPostListResult.ResultBean result) {
        if (result.getAvator().contains("http://")) {
            avatarUrl = result.getAvator();
        } else if (result.getAvator().contains(".")) {
            avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + result.getAvator();
        } else {
            avatarUrl = SEAPP.PIC_BASE_URL + result.getAvator();
        }
        Picasso.with(mContext).load(avatarUrl)
                .placeholder(R.drawable.ic_default_avatar).error(R.drawable.ic_default_avatar)
                .resize(Utils.dip2px(mContext, 50), Utils.dip2px(mContext, 50)).into(headImage);
        title.setText(result.getName());
        role.setText(result.getRole());
        if (!TextUtils.isEmpty(result.getSign())) {
            sign.setText(result.getSign());
        } else {
            sign.setText(R.string.come_on_2018);
        }
        numFocus.setText(String.valueOf(result.getAttendNum()));
        numFans.setText(String.valueOf(result.getFollowerNum()));
        lcChatKitUser = new LCChatKitUser(result.getPhone(), result.getName(), avatarUrl);
        if (result.getAttendStatus() == 0) {
            focus.setText("关注");
        } else {
            focus.setText("取消关注");
        }
    }

    @OnClick({R.id.left_return, R.id.right_response, R.id.head_image, R.id.focus, R.id.chat, R.id.num_focus, R.id.num_fans})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head_image:
                if (postListResult != null && !TextUtils.isEmpty(postListResult.getResult().getAvator())) {
                    PictureUtils.viewPictures(mContext, postListResult.getResult().getAvator());
                }
                break;
            case R.id.left_return:
                finish();
                break;
            case R.id.right_response:
                startActivity(new Intent(mContext, UserUpdateActivity.class));
                break;
            case R.id.focus:
                if (!SEAuthManager.getInstance().isAuthenticated()) {
                    Intent loginIntent = new Intent(mContext, UserLoginActivity.class);
                    startActivity(loginIntent);
                    return;
                }
                if (postListResult != null) {
                    if (postListResult.getResult().getAttendStatus() == 0) {
                        attendUser(SEUserManager.getInstance().getUserId(), String.valueOf(userId), 1);
                    } else {
                        attendUser(SEUserManager.getInstance().getUserId(), String.valueOf(userId), 2);
                    }
                }
                break;
            case R.id.chat:
                if (!SEAuthManager.getInstance().isAuthenticated()) {
                    Intent loginIntent = new Intent(mContext, UserLoginActivity.class);
                    startActivity(loginIntent);
                    return;
                }
                CustomUserProvider.getInstance().setpartUsers(lcChatKitUser);
                Intent intent = new Intent(mContext, LCIMConversationActivity.class);
//                intent.putExtra(LCIMConstants.PEER_ID, "378");
//                intent.putExtra(LCIMConstants.PEER_ID, "1292");
                intent.putExtra(LCIMConstants.PEER_ID, lcChatKitUser.getUserId());
                startActivity(intent);
                break;
            case R.id.num_focus:
                getFocusList(1);//1关注列表
                break;
            case R.id.num_fans:
                getFocusList(2);//2粉丝列表
                break;
        }
    }

    private void getFocusList(int type) {
        if (!SEAuthManager.getInstance().isAuthenticated()) {
            Intent loginIntent = new Intent(mContext, UserLoginActivity.class);
            startActivity(loginIntent);
            return;
        }
        Intent intentFocus = new Intent(mContext, FocusedListActivity.class);
        intentFocus.putExtra("userId", String.valueOf(userId));
        intentFocus.putExtra("type", type);
        startActivity(intentFocus);
    }

    public void praise(final int groupPosition, final int childPosition) {
//        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        SEAPP.showCatDialog(this);
        final UserPostListResult.ResultBean.DeployListBean deployListBean = list.get(groupPosition).getChild().get(childPosition);
        MCCircleManager.getInstance().praiseCirclePost(SEUserManager.getInstance().getUserId(), String.valueOf(deployListBean.getId()), new Callback<PraiseCirclePostResult>() {
            @Override
            public void success(PraiseCirclePostResult mcCommonResult, Response response) {
                if (mContext != null && !PersonalHomePageActivityTwo.this.isFinishing()) {
//                    SVProgressHUD.dismiss(getActivity());
                    SEAPP.dismissAllowingStateLoss();
                    if (mcCommonResult.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(mContext, mcCommonResult.getMessage(), 2.0f);
                    } else {
                        if (deployListBean.getIsPraised() == 0) {
                            deployListBean.setIsPraised(1);
                            deployListBean.setPraiseNumber(deployListBean.getPraiseNumber() + 1);
                        } else {
                            deployListBean.setIsPraised(0);
                            deployListBean.setPraiseNumber(deployListBean.getPraiseNumber() - 1);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !PersonalHomePageActivityTwo.this.isFinishing()) {
//                    SVProgressHUD.dismiss(getActivity());
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    private void attendUser(String attendId, String attendedId, final int type) {
        SEAPP.showCatDialog(this);
        HomeListManager.getInstance().attendUser(attendId, attendedId, type, new Callback<SimpleResult>() {
            @Override
            public void success(SimpleResult simpleResult, Response response) {
                if (mContext != null && !PersonalHomePageActivityTwo.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    if (simpleResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(mContext, simpleResult.getMessage());
                    } else {
                        switch (type) {
                            case 1://关注
                                focus.setText("取消关注");
                                postListResult.getResult().setAttendStatus(1);
                                break;
                            case 2://取消关注
                                focus.setText("关注");
                                postListResult.getResult().setAttendStatus(0);
                                break;
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !PersonalHomePageActivityTwo.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    public void share(final int groupPosition, final int childPosition) {
        UserPostListResult.ResultBean.DeployListBean deployListBean = list.get(groupPosition).getChild().get(childPosition);
        share = new SharePopupWindow(mContext);
        share.setPlatformActionListener(this);
        ShareModel model = new ShareModel();
//        if (circle.getUserAvatar().indexOf("jpg") != -1) {
        if (deployListBean.getUserAvatar().contains("http://")) {
            model.setImageUrl(deployListBean.getUserAvatar());
        } else if (deployListBean.getUserAvatar().contains(".")) {
            model.setImageUrl("http://api.olaxueyuan.com/upload/" + deployListBean);
        } else {
            model.setImageUrl(SEAPP.PIC_BASE_URL + deployListBean.getUserAvatar());
        }
        model.setText(deployListBean.getContent());
        model.setTitle("欧拉学院");
        model.setUrl(SEConfig.getInstance().getAPIBaseURL() + "/circlepost.html?circleId=" + deployListBean.getId());
        share.initShareParams(model);
        share.showShareWindow();
        // 显示窗口 (设置layout在PopupWindow中显示的位置)
        share.showAtLocation(findViewById(R.id.main_personal_home),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /////////////////////// 分享相关  //////////////////////
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
    public void onCancel(Platform platform, int arg1) {
        Message msg = new Message();
        msg.what = 0;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        int what = msg.what;
        if (what == 1) {
            Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
        }
        if (share != null) {
            share.dismiss();
        }
        return false;
    }
}
