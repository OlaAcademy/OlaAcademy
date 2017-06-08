package com.michen.olaxueyuan.ui.course.video;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;

/**
 * Created by mingge on 2017/6/8.
 * 视频的popupWindow
 */

public class CourseVideoPopupWindowManager {
	private static CourseVideoPopupWindowManager manager;

	public static CourseVideoPopupWindowManager getInstance() {
		if (manager == null) {
			manager = new CourseVideoPopupWindowManager();
		}
		return manager;
	}

	public void initView(CourseVideoActivity activity) {
		this.activity = activity;
	}

	private PopupWindow handOutPopup;
	private int[] location = new int[2];
	public CourseVideoActivity activity;

	public void showHandOutPop(Context context) {
		View popView = LayoutInflater.from(context).inflate(R.layout.handout_video_fragment, null);
		popView.setFocusable(true);
		popView.setFocusableInTouchMode(true);
//		Activity.popDownLine.getLocationOnScreen(location);
	}
}
