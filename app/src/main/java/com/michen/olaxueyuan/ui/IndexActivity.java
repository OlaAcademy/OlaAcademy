package com.michen.olaxueyuan.ui;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.michen.olaxueyuan.R;


public class IndexActivity extends Activity {
    public final static String SP_FILENAME_CONFIG = "sp_fileName_config";
    public final static String SP_PARAMSNAME_ISGUIDE = "sp_paramsName_isGuide";
    public final static String SP_VERSION_CODE = "version_code";
    private boolean isUpdate;//是否升级了版本

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        SharedPreferences mSp = getSharedPreferences(SP_FILENAME_CONFIG, MODE_PRIVATE);
        final boolean isGuide = mSp.getBoolean(SP_PARAMSNAME_ISGUIDE, false);
        try {
            final int versionCode = mSp.getInt(SP_VERSION_CODE, 0);
            int nowVersionCode = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
            if (nowVersionCode > versionCode) {
                isUpdate = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if (isGuide && !isUpdate) {
                    Intent mainIntent = new Intent(IndexActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                } else {
                    Intent guideIntent = new Intent(IndexActivity.this, GuidePageActivity.class);
                    startActivity(guideIntent);
                }
                finish();
            }
        }, 1000);
    }
}
