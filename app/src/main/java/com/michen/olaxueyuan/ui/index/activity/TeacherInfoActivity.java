package com.michen.olaxueyuan.ui.index.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.model.MCTeacher;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;

public class TeacherInfoActivity extends SEBaseActivity {

    private TextView infoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_info);

        infoTV = (TextView) findViewById(R.id.tv_teacherInfo);

        MCTeacher teacherInfo = (MCTeacher) getIntent().getSerializableExtra("teacherInfo");
        if (teacherInfo != null) {
            setTitleText(teacherInfo.name);
            infoTV.setText("\u3000\u3000" + teacherInfo.profile);
        }
    }
}
