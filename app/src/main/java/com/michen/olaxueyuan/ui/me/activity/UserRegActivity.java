package com.michen.olaxueyuan.ui.me.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.ui.MainActivity;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.SECallBack;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.MCCommonResult;
import com.michen.olaxueyuan.protocol.result.SEUserResult;
import com.michen.olaxueyuan.protocol.result.ServiceError;
import com.snail.svprogresshud.SVProgressHUD;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class UserRegActivity extends SEBaseActivity implements View.OnClickListener {

    private EditText phoneET, codeET, passET;
    private TextView protocolTV;
    private Button codeBtn, regBtn;

    private String user, pass, repass;

    private CountDownTimer mDownTimer = new CountDownTimer(60000, 1000) {

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            codeBtn.setText(millisUntilFinished / 1000 + "秒");
        }

        @Override
        public void onFinish() {
            codeBtn.setText("验证");
            codeBtn.setClickable(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reg);
        setTitleText("注册");

        phoneET = (EditText) findViewById(R.id.et_name);
        codeET = (EditText) findViewById(R.id.et_code);
        passET = (EditText) findViewById(R.id.et_password);

        LinearLayout regLL = (LinearLayout) findViewById(R.id.regLL);
        regLL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(phoneET.getWindowToken(), 0);
                phoneET.clearFocus();
                return false;
            }
        });


        protocolTV = (TextView) findViewById(R.id.tv_protocol);
        SpannableString msp = new SpannableString("请验证完成后点击『提交』完成注册。");
        //msp.setSpan(new ForegroundColorSpan(Color.rgb(0, 187, 230)), 8, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        protocolTV.setText(msp);

        codeBtn = (Button) findViewById(R.id.btn_code);
        regBtn = (Button) findViewById(R.id.btn_reg);

        codeBtn.setOnClickListener(this);
        regBtn.setOnClickListener(this);

        phoneET.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                user = phoneET.getText().toString().trim();
                if (user != null && (!user.equals(""))) {

                } else {
                    codeET.setText("");
                    passET.setText("");
                }
                changeColor();
            }
        });

        codeET.addTextChangedListener(textWatcher);
        passET.addTextChangedListener(textWatcher);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1,
                                      int arg2, int arg3) {
        }

        @Override
        public void afterTextChanged(Editable arg0) {
            changeColor();
        }
    };

    // 改变登陆按钮文字颜色
    public void changeColor() {
        user = phoneET.getText().toString().trim();
        pass = codeET.getText().toString().trim();
        repass = passET.getText().toString().trim();
        if ((user != null) && (!user.equals(""))
                && (pass != null) && (!pass.equals("")) && (repass != null) && (!repass.equals(""))) {
            regBtn.setClickable(true);
            regBtn.setTextColor(Color.WHITE);
        } else {
            regBtn.setClickable(false);
            regBtn.setTextColor(Color.LTGRAY);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_code:
                fecthCode();
                break;
            case R.id.btn_reg:
                regUser();
                break;
        }

    }

    private void fecthCode() {
        String phone = phoneET.getText().toString();
        if (phone.length() != 11) {
            SVProgressHUD.showInViewWithoutIndicator(UserRegActivity.this, "请输入手机号", 2.0f);
            return;
        }
        codeBtn.setClickable(false);
        mDownTimer.start();
        SEAuthManager am = SEAuthManager.getInstance();
        am.requestSMSAuthCode(phone, new Callback<MCCommonResult>() {
            @Override
            public void success(MCCommonResult result, Response response) {
                if (!result.apicode.equals("10000")) {
                    SVProgressHUD.showInViewWithoutIndicator(UserRegActivity.this, result.message, 2.0f);
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });

    }

    private void regUser() {
        String phone = phoneET.getText().toString();
        if (phone.length() != 11) {
            SVProgressHUD.showInViewWithoutIndicator(UserRegActivity.this, "请输入手机号", 2.f);
            return;
        }
        String code = codeET.getText().toString();
        if (code.length() < 4) {
            SVProgressHUD.showInViewWithoutIndicator(UserRegActivity.this, "请输入验证码", 2.f);
            return;
        }
        String pass = passET.getText().toString();
        if (pass.length() < 3) {
            SVProgressHUD.showInViewWithoutIndicator(UserRegActivity.this, "密码不能小于三位", 2.f);
            return;
        }
        SEUserManager.getInstance().regUser(phone, code, pass, new SECallBack() {
            @Override
            public void success() {
                SEUserResult result = SEUserManager.getInstance().getRegResult();
                if (!result.apicode.equals("10000")) {
                    SVProgressHUD.showInViewWithoutIndicator(UserRegActivity.this, result.message, 2.0f);
                    return;
                }
                Intent intent = getIntent();
                intent.putExtra("phone", phoneET.getText().toString());
                intent.putExtra("password", passET.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
                //login();
            }

            @Override
            public void failure(ServiceError error) {
                Toast.makeText(UserRegActivity.this, "请求失败 " + error.message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void login() {
        SEAuthManager am = SEAuthManager.getInstance();
        am.authWithUsernamePassword(phoneET.getText().toString(), passET.getText().toString(), new Callback<SEUserResult>() {
            @Override
            public void success(SEUserResult seUserResult, Response response) {
                Intent mainIntent = new Intent(UserRegActivity.this,
                        MainActivity.class);
                startActivity(mainIntent);
                finish();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
