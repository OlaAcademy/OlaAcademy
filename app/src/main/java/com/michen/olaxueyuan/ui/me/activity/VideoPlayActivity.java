package com.michen.olaxueyuan.ui.me.activity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.ui.course.video.QuestionVideoPlayManager;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaControllerView;
import io.vov.vitamio.widget.VideoView;

public class VideoPlayActivity extends FragmentActivity implements View.OnClickListener, VideoView.OnVideoPlayFailListener
        , MediaPlayer.OnCompletionListener, MediaControllerView.Authentication {
    @Bind(R.id.root_view)
    public LinearLayout root_view;
    @Bind(R.id.surface_view)
    public VideoView mVideoView;
    @Bind(R.id.set_full_screen)
    public ImageView set_full_screen;
    @Bind(R.id.loading_text)
    public TextView loading_text;
    @Bind(R.id.video_parent)
    public FrameLayout video_parent;
    @Bind(R.id.operation_bg)
    public ImageView mOperationBg;
    @Bind(R.id.operation_percent)
    public ImageView mOperationPercent;
    @Bind(R.id.operation_volume_brightness)
    public FrameLayout mVolumeBrightnessLayout;
    @Bind(R.id.gesture_iv_progress)
    public ImageView gesture_iv_progress;// 快进或快退标志
    @Bind(R.id.geture_tv_progress_time)
    public TextView geture_tv_progress_time;// 播放时间进度
    @Bind(R.id.gesture_progress_layout)
    public RelativeLayout gesture_progress_layout;// 进度图标布局
    @Bind(R.id.root)
    public FrameLayout root;
    @Bind(R.id.video_view_return)
    public ImageView videoViewReturn;
    @Bind(R.id.course_btn)
    public ImageButton courseBtn;
    @Bind(R.id.full_screen_title_layout)
    public RelativeLayout fullScreenTitleLayout;
    @Bind(R.id.course_list)
    public ListView courseList;
    @Bind(R.id.list_layout)
    public LinearLayout listLayout;

    public AudioManager mAudioManager;
    public int mMaxVolume;   // 最大声音
    public int mVolume = -1;  //当前声音
    public float mBrightness = -1f; // 当前亮度
    public GestureDetector mGestureDetector;
    public int GESTURE_FLAG = 0;// 1,调节进度，2，调节音量和亮度
    public Context context;
    public MediaControllerView controller;
    public long msec = 0;//是否播放过
    public String gid;
    public String videoTitle;
    public int type;
    public String location;
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        Vitamio.isInitialized(this);
        setContentView(R.layout.activity_question_video_play);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initView();

    }

    private void initView() {
        set_full_screen.setOnClickListener(this);
        videoViewReturn.setOnClickListener(this);
        mVideoView.setOnClickListener(this);
        courseBtn.setOnClickListener(this);
        set_full_screen.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        path = getIntent().getExtras().getString("videoPath");
        playfunction(path);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        QuestionVideoPlayManager.getInstance().initView(VideoPlayActivity.this);
        mGestureDetector = new GestureDetector(this, new MyGestureListener());
        mVideoView.setOnInfoListener(infoListener);
        mVideoView.setOnVideoPlayFailListener(this);
    }

    void playfunction(String path) {
        Logger.e("path=" + path);
        if (TextUtils.isEmpty(path)) {
            ToastUtil.showToastShort(context, R.string.play_fail_no_found_path);
            return;
        } else {
            mVideoView.setVideoPath(path);
            controller = new MediaControllerView(VideoPlayActivity.this, VideoPlayActivity.this, root_view, root, false, this);
            mVideoView.setMediaController(controller);
            mVideoView.requestFocus();
            mVideoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setPlaybackSpeed(1.0f);
                    controller.show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_view_return:
                finish();
                break;
        }
    }

    MediaPlayer.OnInfoListener infoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    //开始缓存，暂停播放
                    loading_text.setVisibility(View.VISIBLE);
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    //缓存完成，继续播放
                    mVideoView.start();
                    loading_text.setVisibility(View.GONE);
                    break;
                case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                    //显示 下载速度
//                    Logger.e("download rate:" + extra);
                    if (mVideoView.isPlaying()) {
                        controller.setProgress();
                    }
                    break;
            }
            return true;
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event))
            return true;
        // 处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                endGesture();
                break;
            case MotionEvent.ACTION_DOWN:
//                if (listLayout.getVisibility() == View.VISIBLE) {
//                    listLayout.setVisibility(View.GONE);
//                    controller.show();
//                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 手势结束
     */
    private void endGesture() {
        mVolume = -1;
        mBrightness = -1f;
        // 隐藏
        mDismissHandler.removeMessages(0);
        mDismissHandler.sendEmptyMessageDelayed(0, 500);
    }

    /**
     * 定时隐藏
     */
    private Handler mDismissHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (!VideoPlayActivity.this.isFinishing()) {
                        mVolumeBrightnessLayout.setVisibility(View.GONE);
                        GESTURE_FLAG = 0;// 手指离开屏幕后，重置调节音量或进度的标志
                        gesture_progress_layout.setVisibility(View.GONE);
                    }
                    break;
                case 101:
                    break;
            }
        }
    };

    @Override
    public void videoPlayFaile(boolean isFaile) {
        if (isFaile) {
            ToastUtil.showToastShort(context, R.string.play_fail_open_other_video);
            finish();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void isFiveMinute(boolean isAuth, boolean flag) {
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            QuestionVideoPlayManager.getInstance().setOnScroll(e1, e2, distanceX, distanceY);
            return false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        msec = mVideoView.getCurrentPosition();
        mVideoView.pause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (msec > 0) {
            mVideoView.resume();
            mVideoView.seekTo(msec);
        }
        MobclickAgent.onResume(this);   // 友盟统计
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
