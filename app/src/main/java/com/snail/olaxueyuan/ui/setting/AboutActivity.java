package com.snail.olaxueyuan.ui.setting;

import android.os.Bundle;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.app.SEConfig;
import com.snail.olaxueyuan.ui.activity.SEBaseActivity;

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
