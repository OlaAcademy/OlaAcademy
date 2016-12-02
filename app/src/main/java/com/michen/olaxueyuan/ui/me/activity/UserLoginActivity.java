package com.michen.olaxueyuan.ui.me.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.SEUserResult;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.R;
import com.snail.svprogresshud.SVProgressHUD;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserLoginActivity extends SEBaseActivity {

    private EditText phoneET;
    private EditText passET;
    private Button loginBtn;
    private TextView forgetTV;

    public static int PASS_FOREGT = 0x0101;
    public static int USER_REG = 0x0202;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

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
                        SVProgressHUD.showInViewWithoutIndicator(UserLoginActivity.this, result.message, 2.0f);
                        return;
                    }
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
    }
}
