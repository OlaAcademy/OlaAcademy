package com.michen.olaxueyuan.ui.course.video;

import android.os.Bundle;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CourseVideoDetailActivity extends SEBaseActivity {

	@Bind(R.id.name)
	TextView name;
	@Bind(R.id.avatar_course)
	RoundRectImageView avatarCourse;
	@Bind(R.id.org)
	TextView org;
	@Bind(R.id.detail)
	TextView detail;
	private String teacherName;
	private String teacherAvatar;
	private String teacherProfile;
	private String courseName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_video_detail);
		ButterKnife.bind(this);
		initView();
	}

	private void initView() {
		setTitleText("课程详情");
		avatarCourse.setRectAdius(200);
		teacherName = getIntent().getStringExtra("teacherName");
		teacherAvatar = getIntent().getStringExtra("teacherAvatar");
		teacherProfile = getIntent().getStringExtra("teacherProfile");
		courseName = getIntent().getStringExtra("courseName");
		name.setText(courseName);
		Picasso.with(mContext).load(teacherAvatar).into(avatarCourse);
		org.setText(teacherName);
		detail.setText(teacherProfile);
	}
}
