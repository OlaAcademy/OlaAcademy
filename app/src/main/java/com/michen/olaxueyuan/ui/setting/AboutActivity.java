package com.michen.olaxueyuan.ui.setting;

import android.os.Bundle;
import android.widget.TextView;

import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEConfig;

public class AboutActivity extends SEBaseActivity {

    private TextView versionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitleText("关于");

        versionText = (TextView) findViewById(R.id.versionText);
        versionText.setText(SEConfig.getInstance().getVersionText());
    }

}
