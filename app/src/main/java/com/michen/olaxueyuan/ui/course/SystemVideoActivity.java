package com.michen.olaxueyuan.ui.course;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.event.VideoPdfEvent;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SECourseManager;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.protocol.result.SystemCourseResultEntity;
import com.michen.olaxueyuan.protocol.result.SystemVideoResult;
import com.michen.olaxueyuan.ui.adapter.SystemVideoListAdapter;
import com.michen.olaxueyuan.ui.course.video.SystemVideoFragmentManger;
import com.michen.olaxueyuan.ui.course.video.VideoManager;
import com.michen.olaxueyuan.ui.course.video.VideoSystemManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaControllerView;
import io.vov.vitamio.widget.VideoView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SystemVideoActivity extends FragmentActivity implements View.OnClickListener
        , VideoView.OnVideoPlayFailListener, MediaControllerView.Authentication, VideoView.OnVideoSizeChangedListener2 {

    @Bind(R.id.left_return)
    public TextView leftReturn;
    @Bind(R.id.title_tv)
    public TextView titleTv;
    @Bind(R.id.surface_view)
    public VideoView mVideoView;
    @Bind(R.id.mediacontroller_play_pause)
    public ImageButton mediacontrollerPlayPause;
    @Bind(R.id.set_full_screen)
    public ImageView set_full_screen;
    @Bind(R.id.mediacontroller_time_total)
    public TextView mediacontrollerTimeTotal;
    @Bind(R.id.mediacontroller_time_current)
    public TextView mediacontrollerTimeCurrent;
    @Bind(R.id.mediacontroller_seekbar)
    public SeekBar mediacontrollerSeekbar;
    @Bind(R.id.mediacontroller_file_name)
    public TextView mediacontrollerFileName;
    @Bind(R.id.mediacontroller_speed_text)
    public TextView speedText;
    @Bind(R.id.root_view)
    public LinearLayout rootView;
    @Bind(R.id.loading_text)
    public TextView loading_text;
    @Bind(R.id.video_view_return)
    public ImageView videoViewReturn;
    @Bind(R.id.course_title_name)
    public TextView courseTitleName;
    @Bind(R.id.course_btn)
    public ImageButton courseBtn;
    @Bind(R.id.full_screen_title_layout)
    public RelativeLayout fullScreenTitleLayout;
    @Bind(R.id.course_list)
    public ExpandableListView courseList;
    @Bind(R.id.list_layout)
    public LinearLayout listLayout;
    @Bind(R.id.video_parent)
    public FrameLayout videoParent;
    @Bind(R.id.operation_bg)
    public ImageView mOperationBg;
    @Bind(R.id.operation_full)
    public ImageView operationFull;
    @Bind(R.id.operation_percent)
    public ImageView mOperationPercent;
    @Bind(R.id.operation_volume_brightness)
    public FrameLayout mVolumeBrightnessLayout;
    @Bind(R.id.gesture_iv_progress)
    public ImageView gesture_iv_progress;
    @Bind(R.id.geture_tv_progress_time)
    public TextView geture_tv_progress_time;
    @Bind(R.id.gesture_progress_layout)
    public RelativeLayout gesture_progress_layout;
    @Bind(R.id.title_layout)
    public LinearLayout titleLayout;
    @Bind(R.id.root)
    public LinearLayout root;

    @Bind(R.id.catalig_text)
    public TextView cataligText;
    @Bind(R.id.catalog_indicator)
    public View catalogIndicator;
    @Bind(R.id.catalog_layout)
    public RelativeLayout catalogLayout;
    @Bind(R.id.detail_text)
    public TextView detailText;
    @Bind(R.id.detail_indicator)
    public View detailIndicator;
    @Bind(R.id.detail_layout)
    public RelativeLayout detailLayout;
    @Bind(R.id.handout_text)
    public TextView handoutText;
    @Bind(R.id.handout_indicator)
    public View handoutIndicator;
    @Bind(R.id.handout_layout)
    public RelativeLayout handoutLayout;
    @Bind(R.id.view_pager)
    public ViewPager mViewPager;

    private String courseId;
    private List<SystemVideoResult.ResultBean> videoArrayList;
    private SystemVideoListAdapter adapter;

    public AudioManager mAudioManager;
    public int mMaxVolume;   // 最大声音
    public int mVolume = -1;  //当前声音
    public float mBrightness = -1f; // 当前亮度
    public GestureDetector mGestureDetector;
    public int GESTURE_FLAG = 0;// 1,调节进度，2，调节音量和亮度
    public Context context;
    public MediaControllerView controller;
    public long msec = 0;//是否播放过
    private String userId = "";
    SystemCourseResultEntity resultEntity;
    public int pdfPosition = 0;
    private SystemVideoResult.ResultBean resultBean;
    private int videoListPosition = 0;
    private int videoWidth = 16;
    private int videoHeight = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(getApplicationContext());
        context = this;
        setContentView(R.layout.activity_system_video);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setVideoViewHeight(videoWidth, videoHeight);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDismissHandler.sendEmptyMessage(1);
        mDismissHandler.sendEmptyMessageDelayed(1, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();
        msec = mVideoView.getCurrentPosition();
        mVideoView.pause();
        recordPlayProgress();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void initView() {
        courseId = getIntent().getExtras().getString("pid");
        resultEntity = (SystemCourseResultEntity) getIntent().getSerializableExtra("ResultEntity");
        new TitleManager(this, getString(R.string.video_detail), this, true);
        mVideoView.setOnClickListener(this);
        SystemVideoFragmentManger.getInstance().initView(this, courseId, resultEntity);
    }

    public void initData() {
        playFunction();
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        VideoSystemManager.getInstance().initView(SystemVideoActivity.this);
        mGestureDetector = new GestureDetector(this, new MyGestureListener());
        mVideoView.setOnInfoListener(infoListener);
        mVideoView.setOnVideoPlayFailListener(this);
        mVideoView.setOnVideoSizeChangedListener2(this);
//        mVideoView.setVideoPath("http://mooc.ufile.ucloud.com.cn/0110010_360p_w141.mp4");
//        performRefresh();
    }

    public void playVideo(SystemVideoResult.ResultBean resultBean, int position, long playProgress) {
        this.resultBean = resultBean;
        this.videoListPosition = position;
        mVideoView.setVideoPath(resultBean.getAddress());
        mVideoView.seekTo(playProgress * 1000);
        msec = playProgress * 1000;
        mDismissHandler.sendEmptyMessage(1);
    }

    SECourseManager courseManager = SECourseManager.getInstance();

    void playFunction() {
        controller = new MediaControllerView(SystemVideoActivity.this, SystemVideoActivity.this, rootView, root, false, this);
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

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
//        Logger.e("width==" + width + ";height==" + height);
        videoWidth = width;
        videoHeight = height;
        setVideoViewHeight(width, height);
    }

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (!SystemVideoActivity.this.isFinishing()) {
                VideoManager.getInstance().setOnScroll(e1, e2, distanceX, distanceY);
            }
            return false;
        }
    }

    public void setVideoViewHeight(int videoWidth, int videoHeight) {
        try {
            int width = Utils.getScreenWidth(context);
//        int height = width * 9 / 16;
            int height = width * videoHeight / videoWidth;
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mVideoView.getLayoutParams();
            layoutParams.height = height;
            mVideoView.setLayoutParams(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.left_return, R.id.title_tv, R.id.set_full_screen, R.id.video_view_return
            , R.id.catalog_layout, R.id.detail_layout, R.id.handout_layout, R.id.mediacontroller_speed_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_return:
                finish();
                break;
            case R.id.title_tv:
                break;
            case R.id.video_view_return:
            case R.id.set_full_screen:
                // 设置横竖屏
                if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                    VideoSystemManager.getInstance().setPortrait();
                    setVideoViewHeight(videoWidth, videoHeight);
                } else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    VideoSystemManager.getInstance().setLandScape();
                }
                break;
            case R.id.catalog_layout:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.detail_layout:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.handout_layout:
                mViewPager.setCurrentItem(2);
                setDownloadPdfPosition(pdfPosition);
                break;
            case R.id.mediacontroller_speed_text:
                switch (speedCount) {
                    case 0:
                        speedCount = 1;
                        mVideoView.setPlaybackSpeed(1.25f);
                        speedText.setText("1.25x");
                        break;
                    case 1:
                        speedCount = 2;
                        mVideoView.setPlaybackSpeed(1.5f);
                        speedText.setText("1.5x");
                        break;
                    case 2:
                        speedCount = 3;
                        mVideoView.setPlaybackSpeed(1.75f);
                        speedText.setText("1.75x");
                        break;
                    case 3:
                        speedCount = 4;
                        mVideoView.setPlaybackSpeed(2.0f);
                        speedText.setText("2.0x");
                        break;
                    case 4:
                        speedCount = 0;
                        mVideoView.setPlaybackSpeed(1.0f);
                        speedText.setText("1.0x");
                        break;
                }
                break;
            default:
                break;
        }
    }

    int speedCount = 0;

    MediaPlayer.OnInfoListener infoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    //开始缓存，暂停播放
                    loading_text.setVisibility(View.VISIBLE);
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                   /* long position = mVideoView.getCurrentPosition();
                    int totalSeconds = (int) (position / 1000);
                    int seconds = totalSeconds / 60;
                    Logger.e("position==" + position);
                    Logger.e("seconds==" + seconds);
                    if (seconds >= 5) {
                        ToastUtil.showShortToast(CourseVideoActivity.this, "不是会员只能看五分钟");
                        Logger.e("不是会员只能看五分钟==");
//                        mVideoView.suspend();
                        mVideoView.pause();
                        loading_text.setVisibility(View.GONE);
                    } else {*/
                    //缓存完成，继续播放
                    mVideoView.start();
                    loading_text.setVisibility(View.GONE);
//                    }
                    break;
                case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                    //显示 下载速度
//                    Logger.e("download rate:" + extra);
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
                if (listLayout.getVisibility() == View.VISIBLE) {
                    listLayout.setVisibility(View.GONE);
                    controller.show();
                }
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
        mDismissHandler.removeMessages(2);
        mDismissHandler.sendEmptyMessageDelayed(2, 500);
    }

    /**
     * 定时隐藏
     */
    private Handler mDismissHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2:
                    if (isFinishing()) {
                        return;
                    }
                    mVolumeBrightnessLayout.setVisibility(View.GONE);
                    GESTURE_FLAG = 0;// 手指离开屏幕后，重置调节音量或进度的标志
                    gesture_progress_layout.setVisibility(View.GONE);
                    break;
                case 0:
//                    Utils.dismissDialog(mDialog);
                    break;
                case 1:
                    if (msec > 0) {
                        mVideoView.resume();
                        mVideoView.seekTo(msec);
                    }
            }
        }
    };

    @Override
    public void videoPlayFaile(boolean isFaile) {
        if (isFaile) {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                VideoManager.getInstance().setPortrait();
            }
            ToastUtil.showToastShort(context, R.string.play_fail_open_other_video);
        }
    }

    /**
     * @param isAuth 是否是认证用户
     * @param flag   是否播放到了五分钟
     */
    @Override
    public void isFiveMinute(boolean isAuth, boolean flag) {
        /*if (!isAuth) {
            if (flag) {
                mVideoView.pause();
                loading_text.setVisibility(View.GONE);
                ToastUtil.showShortToast(CourseVideoActivity.this, "不是会员只能看五分钟");
                Logger.e("不是会员只能看五分钟==");
            }
        }*/
    }

    @Override
    public void onBackPressed() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            VideoManager.getInstance().setPortrait();
        } else {
            finish();
        }
    }

    public void setVieoArrayList(List<SystemVideoResult.ResultBean> videoArrayList) {
        this.videoArrayList = videoArrayList;
    }

    public void setDownloadPdfPosition(int position) {
        /**
         * {@link com.michen.olaxueyuan.ui.course.video.HandOutVideoFragment#onEventMainThread(VideoPdfEvent)}
         */
        try {
            VideoPdfEvent videoPdfEvent = new VideoPdfEvent(
                    null, videoArrayList.get(position).getId(), 1, position, videoArrayList.get(position).getName());
            EventBus.getDefault().post(videoPdfEvent);
            Logger.e("videoPdfEvent==" + videoPdfEvent.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recordPlayProgress() {
        if (!SEAuthManager.getInstance().isAuthenticated() || resultBean == null) {
            return;
        }
        SECourseManager.getInstance().recordPlayProgress(SEAuthManager.getInstance().getAccessUser().getId()
                , String.valueOf(resultBean.getId()), "1", String.valueOf(videoListPosition)
                , String.valueOf(msec / 1000), new Callback<SimpleResult>() {
                    @Override
                    public void success(SimpleResult simpleResult, Response response) {
                    }

                    @Override
                    public void failure(RetrofitError error) {
                    }
                });
    }
}
