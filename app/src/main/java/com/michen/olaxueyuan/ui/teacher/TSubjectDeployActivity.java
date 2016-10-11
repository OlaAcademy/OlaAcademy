package com.michen.olaxueyuan.ui.teacher;

import android.os.Bundle;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;

public class TSubjectDeployActivity extends SEBaseActivity {

    private String subjectIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsubject_deploy);

        setTitleText("发布");

        subjectIds = getIntent().getStringExtra("subjectIds");
    }

}
