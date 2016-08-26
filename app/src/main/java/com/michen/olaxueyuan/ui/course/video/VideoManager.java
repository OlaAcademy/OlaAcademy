package com.michen.olaxueyuan.ui.course.video;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.media.AudioManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.ui.course.CourseVideoActivity;

/**
 * Created by mingge on 16/3/16.
 */
public class VideoManager {
    private static final int GESTURE_MODIFY_PROGRESS = 1;
    private static final int GESTURE_MODIFY_VOLUME = 2;
    private static final float STEP_PROGRESS = 0.1f;// 设定进度滑动时的步长，避免每次滑动都改变，导致改变过快
    private long palyerCurrentPosition; // 度播放的当前标志，毫秒
    private long playerDuration;// 播放资源的时长，毫秒
    private Context context;
    private int[] location = new int[2];
    private int windowWidth;
    private int windowHeight;
    private static VideoManager videoManager;

    public static VideoManager getInstance() {
        if (videoManager == null) {
            videoManager = new VideoManager();
        }
        return videoManager;
    }

    CourseVideoActivity activity;

    public void initView(CourseVideoActivity activity) {
        this.activity = activity;
    }

    public void setPortrait() {
        if (activity.isFinishing()){
            return;
        }
        activity.bottomView.setVisibility(View.VISIBLE);
        activity.titleLayout.setVisibility(View.VISIBLE);
        activity.listLayout.setVisibility(View.GONE);
        activity.fullScreenTitleLayout.setVisibility(View.GONE);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        activity.videoParent.setLayoutParams(params);
        FrameLayout.LayoutParams params2 = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(activity, 195));
        activity.setTheme(android.R.style.Theme_Light_NoTitleBar);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//显示掉信息栏
        activity.mVideoView.setLayoutParams(params2);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        activity.controller.show();
    }

    public void setLandScape() {
        if (activity.isFinishing()){
            return;
        }
        activity.bottomView.setVisibility(View.GONE);
        activity.titleLayout.setVisibility(View.GONE);
        activity.listLayout.setVisibility(View.GONE);
        activity.fullScreenTitleLayout.setVisibility(View.VISIBLE);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        activity.videoParent.setLayoutParams(params);
        activity.mVideoView.setLayoutParams(params);
        activity.setTheme(android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        activity.controller.show();
    }

    public boolean setOnScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (activity.isFinishing()){
            return false;
        }
        float mOldX = e1.getX(), mOldY = e1.getY();
        int y = (int) e2.getRawY();
        int x = (int) e1.getRawX();
        windowWidth = Utils.getScreenMetrics(activity).x;
        windowHeight = Utils.getScreenMetrics(activity).y;

        palyerCurrentPosition = activity.mVideoView.getCurrentPosition();
        playerDuration = activity.mVideoView.getDuration();
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            activity.mVideoView.getLocationOnScreen(location);
            Rect anchorRect = new Rect(location[0], location[1], location[0] + activity.mVideoView.getWidth(), location[1] + activity.mVideoView.getHeight());
            if (mOldY > anchorRect.bottom) {
                return false;
            }
        }
        // 横向的距离变化大则调整进度，纵向的变化大则调整音量和亮度
        if (Math.abs(distanceX) >= Math.abs(distanceY)) {
            activity.mVolumeBrightnessLayout.setVisibility(View.GONE);
            activity.gesture_progress_layout.setVisibility(View.VISIBLE);
            activity.mVideoView.mMediaController.show();
            activity.GESTURE_FLAG = GESTURE_MODIFY_PROGRESS;

            if (activity.GESTURE_FLAG == GESTURE_MODIFY_PROGRESS) {
                if (Math.abs(distanceX) > Math.abs(distanceY)) {// 横向移动大于纵向移动
                    if (distanceX >= Utils.dip2px(activity, STEP_PROGRESS)) {// 快退，用步长控制改变速度，可微调
                        activity.gesture_iv_progress.setImageResource(R.drawable.souhu_player_backward);
                        if (palyerCurrentPosition > 2 * 1000) {// 避免为负
                            palyerCurrentPosition -= 2 * 1000;// scroll方法执行一次快退3秒
                        } else {
                            palyerCurrentPosition = 2 * 1000;
                            palyerCurrentPosition = Math.abs((long) (mOldX - x) * 1000);
                        }
                    } else if (distanceX <= -Utils.dip2px(activity, STEP_PROGRESS)) {// 快进
                        activity.gesture_iv_progress.setImageResource(R.drawable.souhu_player_forward);
                        if (palyerCurrentPosition < playerDuration - 2 * 1000) {// 避免超过总时长
                            palyerCurrentPosition += 2 * 1000;// scroll执行一次快进3秒
                        } else {
                            palyerCurrentPosition = playerDuration - Math.abs((long) (mOldX - x) * 1000);
                        }
                    }
                    activity.geture_tv_progress_time.setText(Utils.converLongTimeToStr(palyerCurrentPosition)
                            + "/" + Utils.converLongTimeToStr(playerDuration));
                    activity.mVideoView.seekTo(palyerCurrentPosition);
                }
            }

        } else {
            activity.mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
            activity.gesture_progress_layout.setVisibility(View.GONE);
            activity.mVideoView.mMediaController.hide();
            activity.GESTURE_FLAG = GESTURE_MODIFY_VOLUME;

            if (mOldX > windowWidth * 1.0 / 2)// 右边滑动
                onVolumeSlide((mOldY - y) / activity.mVideoView.getHeight());
            else if (mOldX < windowWidth / 2.0)// 左边滑动
                onBrightnessSlide((mOldY - y) / activity.mVideoView.getHeight());
        }
        return false;
    }

    /**
     * 滑动改变声音大小
     *
     * @param percent
     */
    private void onVolumeSlide(float percent) {
        if (activity.isFinishing()){
            return;
        }
        if (activity.mVolume == -1) {
            activity.mVolume = activity.mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (activity.mVolume < 0)
                activity.mVolume = 0;
            // 显示
            activity.mOperationBg.setImageResource(R.drawable.video_volumn_bg);
            activity.mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }

        int index = (int) (percent * activity.mMaxVolume) + activity.mVolume;
        if (index > activity.mMaxVolume)
            index = activity.mMaxVolume;
        else if (index < 0)
            index = 0;
        // 变更声音
        activity.mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);
        // 变更进度条
        ViewGroup.LayoutParams lp = activity.mOperationPercent.getLayoutParams();
        lp.width = activity.findViewById(R.id.operation_full).getLayoutParams().width
                * index / activity.mMaxVolume;
        activity.mOperationPercent.setLayoutParams(lp);
    }

    /**
     * 滑动改变亮度
     *
     * @param percent
     */
    private void onBrightnessSlide(float percent) {
        if (activity.isFinishing()){
            return;
        }
        if (activity.mBrightness < 0) {
            activity.mBrightness = activity.getWindow().getAttributes().screenBrightness;
            if (activity.mBrightness <= 0.00f)
                activity.mBrightness = 0.50f;
            if (activity.mBrightness < 0.01f)
                activity.mBrightness = 0.01f;
            // 显示
            activity.mOperationBg.setImageResource(R.drawable.video_brightness_bg);
            activity.mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }
        WindowManager.LayoutParams lpa = activity.getWindow().getAttributes();
        lpa.screenBrightness = activity.mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        activity.getWindow().setAttributes(lpa);
        ViewGroup.LayoutParams lp = activity.mOperationPercent.getLayoutParams();
        lp.width = (int) (activity.findViewById(R.id.operation_full).getLayoutParams().width * lpa.screenBrightness);
        activity.mOperationPercent.setLayoutParams(lp);
    }
}
