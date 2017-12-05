package com.michen.olaxueyuan.ui.plan.activity;

import android.os.Bundle;
import android.view.WindowManager;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.me.activity.VideoPlayActivity;

import butterknife.ButterKnife;
import io.vov.vitamio.Vitamio;

/**
 * 完成率视频播放
 */
public class CompleteScheduleVideoPlayActivity extends VideoPlayActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		Vitamio.isInitialized(this);
		setContentView(R.layout.activity_question_video_play);
		ButterKnife.bind(this);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
}
