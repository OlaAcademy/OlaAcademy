package com.michen.olaxueyuan.ui.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.HomeListManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.SEUserResult;
import com.michen.olaxueyuan.protocol.result.TokenInfoResult;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.circle.chat.CustomUserProvider;
import com.michen.olaxueyuan.ui.course.pay.weixin.Constants;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.LCChatKitUser;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserLoginActivity extends SEBaseActivity {

    @Bind(R.id.login_weixin)
    ImageView loginWeixin;
    private EditText phoneET;
    private EditText passET;
    private Button loginBtn;
    private TextView forgetTV;

    public static int PASS_FOREGT = 0x0101;
    public static int USER_REG = 0x0202;
    private UMSocialService mController;
    private String source;
    private String sourceId;
    private String unionId = "";
    private String headUrl;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        ButterKnife.bind(this);
        mController = UMServiceFactory.getUMSocialService("com.umeng.login");
        setTitleText("登录");
        setLeftImageInvisibility();
        setRightImageInvisibility();

        setLeftText("取消");
        setLeftTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setRightText("注册");
        setRightTextListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLoginActivity.this, UserRegActivity.class);
                startActivityForResult(intent, USER_REG);
            }
        });

        phoneET = (EditText) findViewById(R.id.et_phone);
        passET = (EditText) findViewById(R.id.et_pass);

        forgetTV = (TextView) findViewById(R.id.tv_forget);
        forgetTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, UserPasswordActivity.class);
                startActivityForResult(intent, PASS_FOREGT);
            }
        });

        loginBtn = (Button) findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {
//        SVProgressHUD.showInView(UserLoginActivity.this, "登录中,请稍后...", true);
        SEAPP.showCatDialog(this);
        SEAuthManager am = SEAuthManager.getInstance();
        am.authWithUsernamePassword(phoneET.getText().toString(), passET.getText().toString(), new Callback<SEUserResult>() {
            @Override
            public void success(SEUserResult result, Response response) {
                if (!UserLoginActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    if (!result.apicode.equals("10000")) {
//                        SVProgressHUD.showInViewWithoutIndicator(UserLoginActivity.this, result.message, 2.0f);
                        ToastUtil.showToastLong(mContext, result.message);
                        return;
                    }
                    SEAuthManager.getInstance().setTokenInfoResult(null);
                    getTokenInfo();
                    loginChat(result);
                    EventBus.getDefault().post(new UserLoginNoticeModule(true));//发送通知登录
//                    SVProgressHUD.dismiss(UserLoginActivity.this);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (!UserLoginActivity.this.isFinishing()) {
//                    SVProgressHUD.dismiss(UserLoginActivity.this);
                    SEAPP.dismissAllowingStateLoss();
                }
            }
        });
    }

    private LCChatKitUser lcChatKitUser;

    private void loginChat(SEUserResult result) {
        if (SEAuthManager.getInstance().isAuthenticated()) {
            LCChatKit.getInstance().open(SEAuthManager.getInstance().getAccessUser().getPhone(), new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (null == e) {
//                        ToastUtil.showToastShort(UserLoginActivity.this, "登录成功");
                    } else {
                        Toast.makeText(UserLoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        String avatarUrl = "";
        if (result.data.getAvator() != null) {
            if (result.data.getAvator().contains("http://")) {
                avatarUrl = result.data.getAvator();
            } else if (result.data.getAvator().contains(".")) {
                avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + result.data.getAvator();
            } else {
                avatarUrl = SEAPP.PIC_BASE_URL + result.data.getAvator();
            }
        }
        lcChatKitUser = new LCChatKitUser(result.data.getPhone(), result.data.getName(), avatarUrl);
        CustomUserProvider.getInstance().setpartUsers(lcChatKitUser);
    }

    private void getTokenInfo() {
        if (!SEAuthManager.getInstance().isAuthenticated()) {
            return;
        }
        String userId = SEAuthManager.getInstance().getAccessUser().getId();
        HomeListManager.getInstance().getTokenInfo(userId, new Callback<TokenInfoResult>() {
            @Override
            public void success(TokenInfoResult tokenInfoResult, Response response) {
                if (!UserLoginActivity.this.isFinishing()) {
                    if (tokenInfoResult != null && tokenInfoResult.getApicode() == 10000) {
                        if (SEAuthManager.getInstance().getTokenInfoResult() != null) {
                            String nativeToken = SEAuthManager.getInstance().getTokenInfoResult().getResult().getToken();
                            if (nativeToken != null && !nativeToken.equals(tokenInfoResult.getResult().getToken())) {
                                SEAPP.showLoginFromOtherDialog();
                            } else {
                                SEAuthManager.getInstance().setTokenInfoResult(tokenInfoResult);
                            }
                        } else {
                            SEAuthManager.getInstance().setTokenInfoResult(tokenInfoResult);
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PASS_FOREGT && resultCode == RESULT_OK) {
            phoneET.setText(data.getStringExtra("phone"));
            passET.setText(data.getStringExtra("password"));
        } else if (requestCode == USER_REG && resultCode == RESULT_OK) {
            phoneET.setText(data.getStringExtra("phone"));
            passET.setText(data.getStringExtra("password"));
            login();
        }
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    private void thirdLoginWeChat() {
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(UserLoginActivity.this, Constants.APP_ID, Constants.API_KEY);
        wxHandler.addToSocialSDK();
        mController.doOauthVerify(UserLoginActivity.this, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                Toast.makeText(UserLoginActivity.this, "授权开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA platform) {
                Toast.makeText(UserLoginActivity.this, "授权错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(final Bundle value, SHARE_MEDIA platform) {
                Toast.makeText(UserLoginActivity.this, "授权完成", Toast.LENGTH_SHORT).show();
                //获取相关授权信息
                mController.getPlatformInfo(UserLoginActivity.this, SHARE_MEDIA.WEIXIN, new SocializeListeners.UMDataListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(UserLoginActivity.this, "获取平台数据开始...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(int status, Map<String, Object> info) {
                        if (status == 200 && info != null) {
                            StringBuilder sb = new StringBuilder();
                            Set<String> keys = info.keySet();
                            for (String key : keys) {
                                sb.append(key + "=" + info.get(key).toString() + "\r\n");
                                if ("headimgurl".equals(key)) {
                                    headUrl = info.get(key).toString();
                                }
                                if ("sex".equals(key)) {
                                    if ("1".equals(info.get(key).toString())) {
                                        gender = "男";
                                    } else if ("2".equals(info.get(key).toString())) {
                                        gender = "女";
                                    } else {
                                        gender = "";
                                    }
                                }
                                if ("unionid".equals(key)) {
                                    unionId = info.get(key).toString();
                                }
                            }
                            source = "wechat";
                            sourceId = value.getString("uid");
                            Logger.e("uid======================================" + value.getString("uid"));
//                            bindingPhoneOrNot(source, unionId, sourceId);
                            Logger.e("==========" + sb.toString());
                        } else {
                            Logger.e("==========发生错误：" + status);
                        }
                    }
                });
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                Toast.makeText(UserLoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.login_weixin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_weixin:
                thirdLoginWeChat();
                break;
        }
    }
}
