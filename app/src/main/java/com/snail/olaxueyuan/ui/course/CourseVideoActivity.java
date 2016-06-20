package com.snail.olaxueyuan.ui.course;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.DialogUtils;
import com.snail.olaxueyuan.common.manager.Logger;
import com.snail.olaxueyuan.common.manager.TitleManager;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.common.manager.Utils;
import com.snail.olaxueyuan.database.CourseDB;
import com.snail.olaxueyuan.protocol.manager.SEAuthManager;
import com.snail.olaxueyuan.protocol.manager.SECourseManager;
import com.snail.olaxueyuan.protocol.result.CourseCollectResult;
import com.snail.olaxueyuan.protocol.result.CourseVideoResult;
import com.snail.olaxueyuan.ui.adapter.CourseVideoListAdapter;
import com.snail.olaxueyuan.ui.course.video.VideoManager;
import com.snail.olaxueyuan.ui.me.UserCourseCollectFragment;
import com.snail.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaControllerView;
import io.vov.vitamio.widget.VideoView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CourseVideoActivity extends Activity implements View.OnClickListener, VideoView.OnVideoPlayFailListener, MediaControllerView.Authentication {

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
    @Bind(R.id.listview)
    public ListView listview;
    @Bind(R.id.title_layout)
    public LinearLayout titleLayout;
    @Bind(R.id.root)
    public LinearLayout root;
    @Bind(R.id.video_download_btn)
    ImageView videoDownloadBtn;
    @Bind(R.id.video_collect_btn)
    ImageView videoCollectBtn;
    @Bind(R.id.bottom_view)
    public RelativeLayout bottomView;

    private String courseId;
    private List<CourseVideoResult.ResultBean.VideoListBean> videoArrayList;
    private CourseVideoListAdapter adapter;

    public AudioManager mAudioManager;
    public int mMaxVolume;   // 最大声音
    public int mVolume = -1;  //当前声音
    public float mBrightness = -1f; // 当前亮度
    public GestureDetector mGestureDetector;
    public int GESTURE_FLAG = 0;// 1,调节进度，2，调节音量和亮度
    public Context context;
    public MediaControllerView controller;
    public long msec = 0;//是否播放过
    private boolean isFromNet = true;//false：不从网络请求数据，true：从网络请求接口
    private CourseVideoResult courseVideoResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Vitamio.isInitialized(getApplicationContext());
        context = this;
        setContentView(R.layout.activity_course_video);
        ButterKnife.bind(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setVideoViewHeight();
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public void initView() {
        courseId = getIntent().getExtras().getString("pid");
        new TitleManager(this, getString(R.string.video_detail), this, true);
        mVideoView.setOnClickListener(this);
    }

    public void initData() {
        playFunction();
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        VideoManager.getInstance().initView(CourseVideoActivity.this);
        mGestureDetector = new GestureDetector(this, new MyGestureListener());
        mVideoView.setOnInfoListener(infoListener);
        mVideoView.setOnVideoPlayFailListener(this);
//        mVideoView.setVideoPath("http://mooc.ufile.ucloud.com.cn/0110010_360p_w141.mp4");
        isFromNet = getIntent().getBooleanExtra("isFromNet", true);
        CourseVideoResult result = (CourseVideoResult) getIntent().getSerializableExtra("result");
        if (isFromNet) {
            performRefresh();
        } else {
            fromIntentData(result);
        }
    }

    public void fromIntentData(CourseVideoResult result) {
        courseVideoResult = result;
        videoArrayList = result.getResult().getVideoList();
        if (videoArrayList != null && videoArrayList.size() > 0) {
            videoArrayList.get(0).setSelected(true);
            adapter = new CourseVideoListAdapter(CourseVideoActivity.this);
            listview.setAdapter(adapter);
            initListViewItemClick();
            adapter.updateData(videoArrayList);
            mVideoView.setVideoPath(videoArrayList.get(0).getAddress());
        }
    }

    private void initListViewItemClick() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (videoArrayList != null && videoArrayList.size() > 0) {
                    for (int i = 0; i < videoArrayList.size(); i++) {
                        videoArrayList.get(i).setSelected(false);
                    }
                    videoArrayList.get(position).setSelected(true);
                    mVideoView.setVideoPath(videoArrayList.get(position).getAddress());
                    adapter.updateData(videoArrayList);
                }
            }
        });
    }

    SECourseManager courseManager = SECourseManager.getInstance();

    public void performRefresh() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        courseManager.fetchCourseSection(courseId, userId, new Callback<CourseVideoResult>() {
            @Override
            public void success(CourseVideoResult result, Response response) {
                if (result.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(CourseVideoActivity.this, result.getMessage(), 2.0f);
                } else {
                    courseVideoResult = result;
                    videoArrayList = result.getResult().getVideoList();
                    if (videoArrayList != null && videoArrayList.size() > 0) {
                        videoArrayList.get(0).setSelected(true);
                        adapter = new CourseVideoListAdapter(CourseVideoActivity.this);
                        listview.setAdapter(adapter);
                        initListViewItemClick();
                        adapter.updateData(videoArrayList);
                        mVideoView.setVideoPath(videoArrayList.get(0).getAddress());
//                        mVideoView.setVideoPath("http://mooc.ufile.ucloud.com.cn/0110010_360p_w141.mp4");
                        if (result.getResult().getIsCollect().equals("1")) {
                            videoCollectBtn.setImageResource(R.drawable.video_collect_icon_selected);
                        } else {
                            videoCollectBtn.setImageResource(R.drawable.video_collect_icon);
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtil.showToastShort(CourseVideoActivity.this, R.string.data_request_fail);
            }
        });
    }

    void playFunction() {
        controller = new MediaControllerView(CourseVideoActivity.this, CourseVideoActivity.this, rootView, root, false, this);
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

    public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            VideoManager.getInstance().setOnScroll(e1, e2, distanceX, distanceY);
            return false;
        }
    }

    public void setVideoViewHeight() {
        int width = Utils.getScreenWidth(context);
        int height = width * 9 / 16;
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mVideoView.getLayoutParams();
        layoutParams.height = height;
        mVideoView.setLayoutParams(layoutParams);
    }

    @OnClick({R.id.left_return, R.id.title_tv, R.id.set_full_screen, R.id.video_view_return
            , R.id.video_download_btn, R.id.video_collect_btn})
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
                    VideoManager.getInstance().setPortrait();
                } else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    VideoManager.getInstance().setLandScape();
                }
                break;
            case R.id.video_download_btn:
                if (courseVideoResult != null && courseVideoResult.getResult().getVideoList().size() > 0) {
                    CourseVideoResult.ResultBean.VideoListBean videoInfo =  courseVideoResult.getResult().getVideoList().get(0);
                    downloadCourse(videoInfo);
                }
                break;
            case R.id.video_collect_btn:
                if (SEAuthManager.getInstance().getAccessUser() == null) {
                    loginDialog();
                } else {
                    if (courseVideoResult != null && courseVideoResult.getResult().getVideoList().size() > 0) {
                        final String videoId = String.valueOf(courseVideoResult.getResult().getVideoList().get(0).getId());
                        if (courseVideoResult.getResult().getIsCollect().equals("1")) {
                            DialogUtils.showDialog(CourseVideoActivity.this, new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            switch (v.getId()) {
                                                case R.id.yes:
                                                    collectVideo(videoId, "0");
                                                    break;
                                            }
                                        }
                                    }, getString(R.string.sure_uncollect), getString(R.string.confirm_message)
                                    , getString(R.string.cancel_message));
                        } else {
                            collectVideo(videoId, "1");
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private void loginDialog() {
        DialogUtils.showDialog(CourseVideoActivity.this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.yes:
                        startActivity(new Intent(CourseVideoActivity.this, UserLoginActivity.class));
                        break;
                }
            }
        }, getString(R.string.to_login), "", "");
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

    /**
     * 收藏视频
     *
     * @param videoId 课程id
     * @param state   1 收藏 0 取消
     */
    public void collectVideo(String videoId, final String state) {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        courseManager.collectionVideo(userId, videoId, courseVideoResult.getResult().getPointId(), state, new Callback<CourseCollectResult>() {
            @Override
            public void success(CourseCollectResult result, Response response) {
                if (result.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(CourseVideoActivity.this, result.getMessage(), 2.0f);
                } else {
                    UserCourseCollectFragment.isRefreshCourseCollectList = true;//通知我的收藏列表刷新
                    Logger.e("UserCourseCollectFragment.isRefreshCourseCollectList==" + UserCourseCollectFragment.isRefreshCourseCollectList);
                    ToastUtil.showToastShort(CourseVideoActivity.this, result.getMessage());
                    if (state.equals("1")) {
                        courseVideoResult.getResult().setIsCollect("1");
                        videoCollectBtn.setImageResource(R.drawable.video_collect_icon_selected);
                    } else {
                        courseVideoResult.getResult().setIsCollect("0");
                        videoCollectBtn.setImageResource(R.drawable.video_collect_icon);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                ToastUtil.showToastShort(CourseVideoActivity.this, R.string.data_request_fail);
            }
        });
    }

    private void downloadCourse(CourseVideoResult.ResultBean.VideoListBean videoInfo) {
        DbUtils db = DbUtils.create(CourseVideoActivity.this);
        CourseDB course = new CourseDB();
        course.setId(videoInfo.getId());
        course.setName(videoInfo.getName());
        course.setThumb(videoInfo.getPic());
        course.setVideo(videoInfo.getAddress());
        try {
            if (db.findById(CourseDB.class, videoInfo.getId()) != null) {
                SVProgressHUD.showInViewWithoutIndicator(this, "缓存列表已存在", 2.0f);
                return;
            }
            db.save(course);
            SVProgressHUD.showInViewWithoutIndicator(this, "成功添加至缓存列表", 2.0f);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
