package com.michen.olaxueyuan.ui.me.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.MCCommonResult;
import com.michen.olaxueyuan.protocol.result.SEUserResult;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BindMobileActivity extends SEBaseActivity {
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.btn_code)
    Button btnCode;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.btn_bind)
    Button btnBind;

    private String source;
    private String sourceId;
    private String unionId;
    private String headUrl;
    private String gender;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_mobile);
        ButterKnife.bind(this);
        setTitleText("绑定手机号");
        initView();
    }

    private void initView() {
        source = getIntent().getStringExtra("source");
        sourceId = getIntent().getStringExtra("sourceId");
        unionId = getIntent().getStringExtra("unionId");
        headUrl = getIntent().getStringExtra("headUrl");
        gender = getIntent().getStringExtra("gender");
        nickname = getIntent().getStringExtra("nickname");
        if (gender.equals("男")) {
            gender = "1";
        } else {
            gender = "2";
        }
        Logger.e(
                "source=" + source
                        + "\nsourceId=" + sourceId
                        + "\nunionId=" + unionId
                        + "\nheadUrl=" + headUrl
                        + "\ngender=" + gender
                        + "\nnickname=" + nickname
        );
    }

    private CountDownTimer mDownTimer = new CountDownTimer(60000, 1000) {

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btnCode.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            btnCode.setText("验证");
            btnCode.setClickable(true);
        }
    };

    private void fecthCode() {
        String phone = etName.getText().toString();
        if (phone.length() != 11) {
            ToastUtil.showToastLong(mContext, "请输入手机号");
            return;
        }

        btnCode.setClickable(false);
        mDownTimer.start();
        SEAuthManager am = SEAuthManager.getInstance();
        am.requestSMSAuthCode(phone, new Callback<MCCommonResult>() {
            @Override
            public void success(MCCommonResult result, Response response) {
                if (!result.apicode.equals("10000")) {
                    ToastUtil.showToastShort(mContext, result.message);
                } else {
                    ToastUtil.showToastLong(mContext, "发送验证码成功，请注意短信");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !BindMobileActivity.this.isFinishing()) {
                    ToastUtil.showToastLong(mContext, "发送验证码失败");
                }
            }
        });

    }

    private void bindPhoneWithSDK() {
        String phone = etName.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            ToastUtil.showToastShort(mContext, "请输入正确手机号");
        }
        String code = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showToastShort(mContext, "请输入验证码");
        }
        SEUserManager.getInstance().bindPhoneWithSDK(source, unionId, nickname, phone, code
                , headUrl, gender, new Callback<SEUserResult>() {
                    @Override
                    public void success(SEUserResult thirdLoginUser, Response response) {
                        if (mContext != null && !BindMobileActivity.this.isFinishing()) {
                            if (!thirdLoginUser.apicode.equals("10000")) {
                                ToastUtil.showToastLong(mContext, thirdLoginUser.message);
                                return;
                            }
                            SEAuthManager.getInstance().updateUserInfo(thirdLoginUser.data);
                            SEAuthManager.getInstance().setTokenInfoResult(null);
                            setResult(UserLoginActivity.BIND_PHONE);
                            finish();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if (mContext != null && !BindMobileActivity.this.isFinishing()) {
                            ToastUtil.showToastShort(mContext, error.getMessage());
                        }
                    }
                });
    }

    @OnClick({R.id.btn_code, R.id.btn_bind})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_code:
                fecthCode();
                break;
            case R.id.btn_bind:
                bindPhoneWithSDK();
                break;
        }
    }
}
