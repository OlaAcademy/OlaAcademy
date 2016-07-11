package com.michen.olaxueyuan.ui.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.sharesdk.ShareModel;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.me.activity.UserPasswordActivity;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.sharesdk.SharePopupWindow;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.UIHandler;
import de.greenrobot.event.EventBus;

public class SettingActivity extends SEBaseActivity implements PlatformActionListener, Handler.Callback {

    private SharePopupWindow share;
    private RelativeLayout shareRL;
    private TextView versionText;
    private RelativeLayout passRL;
    private RelativeLayout downloadRL;
    private TextView downloadTV;
    private RelativeLayout aboutRL;
    private SharedPreferences sp;
    private Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitleText("设置");

        versionText = (TextView) findViewById(R.id.versionText);
        versionText.setText("V 1.1");

        //获取SharedPreferences对象
        sp = getSharedPreferences("SP", MODE_PRIVATE);

        passRL = (RelativeLayout) findViewById(R.id.passRL);
        passRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, UserPasswordActivity.class);
                startActivity(intent);
            }
        });

        aboutRL = (RelativeLayout) findViewById(R.id.aboutRL);
        aboutRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        downloadRL = (RelativeLayout) findViewById(R.id.downloadRL);
        downloadTV = (TextView) findViewById(R.id.downloadTV);
        if (sp.getInt("DOWNLOAD_TYPE", 0) == 0) {
            downloadTV.setText("仅WIFI");
        } else {
            downloadTV.setText("移动数据和WIFI");
        }
        downloadRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomPopWindow();
            }
        });

        shareRL = (RelativeLayout) findViewById(R.id.shareRL);
        shareRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                share = new SharePopupWindow(SettingActivity.this);
                share.setPlatformActionListener(SettingActivity.this);
                ShareModel model = new ShareModel();
                model.setImageUrl(SEConfig.getInstance().getAPIBaseURL()+"/ola/images/icon.png");
                model.setText("欧拉联考——中国最权威的管理类联考学习平台");
                model.setTitle("欧拉学院");
                model.setUrl("http://app.olaxueyuan.com");
                share.initShareParams(model);
                share.showShareWindow();
                // 显示窗口 (设置layout在PopupWindow中显示的位置)
                share.showAtLocation(SettingActivity.this.findViewById(R.id.main_setting),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

            }
        });

        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    private void logout() {
        new AlertDialog.Builder(SettingActivity.this)
                .setTitle("请确认")
                .setMessage("确认退出当前用户吗？")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SEUserManager.getInstance().logout();
                        EventBus.getDefault().post(new UserLoginNoticeModule(false));//发送通知登录
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }


    private void showBottomPopWindow() {
        final View popView = LayoutInflater.from(this).inflate(R.layout.layout_popwindow_download, null);
        final PopupWindow popWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        TextView tv_only = (TextView) popView.findViewById(R.id.pop_first);
        TextView tv_both = (TextView) popView.findViewById(R.id.pop_second);
        TextView tv_cancel = (TextView) popView.findViewById(R.id.pop_cancel);
        View rootView = popView.findViewById(R.id.pop_root);

        tv_only.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadTV.setText("仅WIFI");
                //存入数据
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("DOWNLOAD_TYPE", 0);
                editor.commit();
                popWindow.dismiss();
            }
        });
        tv_both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadTV.setText("移动数据和WIFI");
                //存入数据
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt("DOWNLOAD_TYPE", 1);
                editor.commit();
                popWindow.dismiss();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        popWindow.setTouchable(true);
        popWindow.setOutsideTouchable(true);
        //影响返回键所需
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /////////////////////// 分享相关  ///////////////////////
    @Override
    protected void onResume() {
        super.onResume();
        if (share != null) {
            share.dismiss();
        }
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
            Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
        }
        if (share != null) {
            share.dismiss();
        }
        return false;
    }


}
