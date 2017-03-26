package com.michen.olaxueyuan.ui.umeng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class LoginFromOtherDialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_from_other_dialog);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        int screenWidth = Utils.getScreenMetrics(this).x;
        params.width = screenWidth - 160;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        this.getWindow().setAttributes(params);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);   // 友盟统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.content)
    TextView content;
    @Bind(R.id.no)
    TextView no;
    @Bind(R.id.yes)
    TextView yes;

    private void initView() {
        content.setText("你的账号在另一台手机登录。如非本人操作，则密码可能已经泄露，建议前往修改密码。");
        SEUserManager.getInstance().logout();
        EventBus.getDefault().post(new UserLoginNoticeModule(false));//发送通知登录
        SEAuthManager.getInstance().setTokenInfoResult(null);
        AVUser.logOut();
    }

    @OnClick({R.id.no, R.id.yes})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.yes:
                Intent intent = new Intent(this, UserLoginActivity.class);
                intent.putExtra("isLoginFromOther", true);
                startActivity(intent);
                finish();
            case R.id.no:
                Intent intents = new Intent(this, UserLoginActivity.class);
                startActivity(intents);
                finish();
                break;
        }
    }
}
