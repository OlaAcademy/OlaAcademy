package com.michen.olaxueyuan.ui.course;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.Gravity;
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
import android.widget.Toast;

import com.lidroid.xutils.exception.DbException;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.DialogUtils;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.download.DownloadManager;
import com.michen.olaxueyuan.download.DownloadService;
import com.michen.olaxueyuan.protocol.event.VideoPdfEvent;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SECourseManager;
import com.michen.olaxueyuan.protocol.result.CourseCollectResult;
import com.michen.olaxueyuan.protocol.result.CourseVideoResult;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.sharesdk.ShareModel;
import com.michen.olaxueyuan.sharesdk.SharePopupWindow;
import com.michen.olaxueyuan.ui.circle.chat.CustomUserProvider;
import com.michen.olaxueyuan.ui.course.video.CatalogVideoFragment;
import com.michen.olaxueyuan.ui.course.video.CourseVideoDetailActivity;
import com.michen.olaxueyuan.ui.course.video.CourseVideoFragmentManger;
import com.michen.olaxueyuan.ui.course.video.CourseVideoPopupWindowManager;
import com.michen.olaxueyuan.ui.course.video.HandOutVideoFragment;
import com.michen.olaxueyuan.ui.course.video.VideoManager;
import com.michen.olaxueyuan.ui.me.activity.BuyVipActivity;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.svprogresshud.SVProgressHUD;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.UIHandler;
import de.greenrobot.event.EventBus;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaControllerView;
import io.vov.vitamio.widget.VideoView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CourseVideoActivity extends FragmentActivity implements View.OnClickListener, VideoView.OnVideoPlayFailListener
		, MediaControllerView.Authentication, PlatformActionListener, Handler.Callback, VideoView.OnVideoSizeChangedListener2 {

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
	@Bind(R.id.video_collect_btn)
	TextView videoCollectBtn;
	@Bind(R.id.bottom_view)
	public RelativeLayout bottomView;
	@Bind(R.id.catalig_text)
	public TextView cataligText;
	@Bind(R.id.catalog_indicator)
	public View catalogIndicator;
	@Bind(R.id.catalog_layout)
	RelativeLayout catalogLayout;
	@Bind(R.id.handout_text)
	public TextView handoutText;
	@Bind(R.id.handout_indicator)
	public View handoutIndicator;
	@Bind(R.id.handout_layout)
	RelativeLayout handoutLayout;
	@Bind(R.id.view_pager)
	public ViewPager mViewPager;
	@Bind(R.id.update_item_count)
	TextView updateItemCount;
	@Bind(R.id.batch_download)
	TextView batchDownload;
	@Bind(R.id.lecture_avatar)
	RoundRectImageView lectureAvatar;
	@Bind(R.id.lecture)
	TextView lecture;
	@Bind(R.id.popDownLine)
	public View popDownLine;
	@Bind(R.id.collect_icon)
	ImageView collectIcon;
	@Bind(R.id.previous_video)
	ImageView previousVideo;
	@Bind(R.id.next_video)
	ImageView nextVideo;

	private String courseId;
	private List<CourseVideoResult.ResultBean.VideoListBean> videoArrayList;

	public AudioManager mAudioManager;
	public int mMaxVolume;   // 最大声音
	public int mVolume = -1;  //当前声音
	public float mBrightness = -1f; // 当前亮度
	public GestureDetector mGestureDetector;
	public int GESTURE_FLAG = 0;// 1,调节进度，2，调节音量和亮度
	public Context context;
	public MediaControllerView controller;
	public long msec = 0;//是否播放过
	private CourseVideoResult courseVideoResult;

	private DownloadManager downloadManager;

	private SharePopupWindow share;
	public int pdfPosition;
	private int videoWidth = 16;
	private int videoHeight = 9;
	private LCChatKitUser lcChatKitUser;
	private TitleManager titleManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Vitamio.isInitialized(getApplicationContext());
		context = this;
		setContentView(R.layout.activity_course_video);
		ButterKnife.bind(this);
		EventBus.getDefault().register(this);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		downloadManager = DownloadService.getDownloadManager(context);
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
//        mDismissHandler.sendEmptyMessage(1);
		mDismissHandler.sendEmptyMessageDelayed(1, 500);
		MobclickAgent.onResume(this);          //统计时长

		//分享
		if (share != null) {
			share.dismiss();
		}
	}

	// EventBus 回调
	public void onEventMainThread(UserLoginNoticeModule module) {
		initData();
	}

	public void onEventMainThread(CourseVideoResult result) {
		courseVideoResult = result;
		this.videoArrayList = result.getResult().getVideoList();
		if (videoArrayList != null && videoArrayList.size() > 0) {
			updateItemCountText(videoArrayList.size());
			if (!TextUtils.isEmpty(videoArrayList.get(result.getResult().getPlayIndex()).getFileSavePath())) {
				mVideoView.setVideoPath(videoArrayList.get(result.getResult().getPlayIndex()).getFileSavePath());
			} else {
				mVideoView.setVideoPath(videoArrayList.get(result.getResult().getPlayIndex()).getAddress());
			}
			mVideoView.resume();
			mVideoView.seekTo(result.getResult().getPlayProgress() * 1000);
			Logger.e("msec===" + msec);
			Logger.e("result.getPlayProgress() * 1000===" + result.getResult().getPlayProgress() * 1000);
			mDismissHandler.sendEmptyMessage(1);
			if (result.getResult().getIsCollect().equals("1")) {
				setCollectDrawable(true);
//								videoCollectBtn.setImageResource(R.drawable.video_collect_icon_selected);
			} else {
				setCollectDrawable(false);
//								videoCollectBtn.setImageResource(R.drawable.video_collect_icon);
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		msec = mVideoView.getCurrentPosition();
		mVideoView.pause();
		MobclickAgent.onPause(this);
		recordPlayProgress();
	}


	public void initView() {
//        courseId = getIntent().getExtras().getString("pid");
		courseId = getIntent().getStringExtra("pid");
		Logger.e("courseId==" + courseId);
		titleManager = new TitleManager(this, getString(R.string.video_detail), this, true);
		titleManager.changeImageRes(TitleManager.RIGHT_INDEX_RESPONSE, R.drawable.video_share_icon);
		mVideoView.setOnClickListener(this);
		lectureAvatar.setRectAdius(100);
		CourseVideoFragmentManger.getInstance().initView(this);
		CourseVideoPopupWindowManager.getInstance().initView(this);
	}

	public void initData() {
		playFunction();
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		VideoManager.getInstance().initView(CourseVideoActivity.this);
		mGestureDetector = new GestureDetector(this, new MyGestureListener());
		mVideoView.setOnInfoListener(infoListener);
		mVideoView.setOnVideoPlayFailListener(this);
		mVideoView.setOnVideoSizeChangedListener2(this);
		performRefresh();
	}

	int position = 0;

	public void playVideo(int position) {
		if (videoArrayList == null || videoArrayList.size() == 0) {
			return;
		}
		if (position < 0) {
			position = videoArrayList.size() - 1;
		} else if (position > videoArrayList.size() - 1) {
			position = 0;
		}
		if (videoArrayList.get(position).getIsfree() == 0) {
			if (!SEAuthManager.getInstance().isAuthenticated()) {
				startActivity(new Intent(context, UserLoginActivity.class));
			} else {
				new AlertDialog.Builder(context)
						.setTitle("友情提示")
						.setMessage("购买会员后即可拥有")
						.setPositiveButton("去购买", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								startActivity(new Intent(context, BuyVipActivity.class));
							}
						})
						.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// do nothing
							}
						})
						.show();
			}
			return;
		}
		((CatalogVideoFragment) (CourseVideoFragmentManger.getInstance().adapter.getItem(0))).listview.smoothScrollToPosition(position);
		if (!TextUtils.isEmpty(videoArrayList.get(position).getFileSavePath())) {
			mVideoView.setVideoPath(videoArrayList.get(position).getFileSavePath());
		} else {
			mVideoView.setVideoPath(videoArrayList.get(position).getAddress());
		}
		pdfPosition = position;

		for (int i = 0; i < videoArrayList.size(); i++) {
			videoArrayList.get(i).setSelected(false);
		}
		videoArrayList.get(position).setSelected(true);
		((CatalogVideoFragment) (CourseVideoFragmentManger.getInstance().adapter.getItem(0))).adapter.updateData(videoArrayList);
		this.position = position;
	}

	public void setDownloadPdfPosition(int position) {
		/**
		 * {@link HandOutVideoFragment#onEventMainThread(VideoPdfEvent)}
		 */
		try {
			EventBus.getDefault().post(new VideoPdfEvent(videoArrayList.get(position).getUrl()
					, videoArrayList.get(position).getId(), 1, position, videoArrayList.get(position).getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
				if (!CourseVideoActivity.this.isFinishing()) {
					if (result.getApicode() != 10000) {
						ToastUtil.showToastShort(context,result.getMessage());
					} else {
						EventBus.getDefault().post(result);
						courseVideoResult = result;
						lcChatKitUser = new LCChatKitUser(result.getResult().getUserPhone(), result.getResult().getTeacherName(),
								PictureUtils.getAvatarPath(result.getResult().getTeacherAvatar()));
						lecture.setText(result.getResult().getTeacherName());
						PictureUtils.loadAvatar(context, lectureAvatar, result.getResult().getTeacherAvatar(), 30);
						msec = result.getResult().getPlayProgress() * 1000;
						videoArrayList = result.getResult().getVideoList();
						downloadManager.getVideoArrayList(CourseVideoActivity.this, result);
					}
				}
			}

			@Override
			public void failure(RetrofitError error) {
				if (!CourseVideoActivity.this.isFinishing()) {
					ToastUtil.showToastShort(CourseVideoActivity.this, R.string.data_request_fail);
				}
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
			try {
				VideoManager.getInstance().setOnScroll(e1, e2, distanceX, distanceY);
			} catch (Exception e) {
				e.printStackTrace();
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

	@OnClick({R.id.left_return, R.id.title_tv, R.id.set_full_screen, R.id.video_view_return, R.id.mediacontroller_speed_text
			, R.id.video_collect_btn, R.id.catalog_layout, R.id.handout_layout,
			R.id.batch_download, R.id.course_detail, R.id.communicate_with_teacher, R.id.collect_icon
			, R.id.previous_video, R.id.next_video, R.id.right_response})
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
					setVideoViewHeight(videoWidth, videoHeight);
				} else if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
					VideoManager.getInstance().setLandScape();
				}
				break;
			case R.id.video_collect_btn:
			case R.id.collect_icon:
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
									}, "", getString(R.string.sure_uncollect), getString(R.string.confirm_message)
									, getString(R.string.cancel_message));
						} else {
							collectVideo(videoId, "1");
						}
					}
				}
				break;
			case R.id.right_response:
				if (courseVideoResult != null && courseVideoResult.getResult().getVideoList().size() > 0) {
					for (CourseVideoResult.ResultBean.VideoListBean videoInfo : courseVideoResult.getResult().getVideoList()) {
						if (videoInfo.isSelected()) {
							shareCourse(videoInfo);
						}
					}
				}
				break;
			case R.id.catalog_layout:
				mViewPager.setCurrentItem(0);
				break;
			case R.id.handout_layout:
				mViewPager.setCurrentItem(1);
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
			case R.id.batch_download:
				if (courseVideoResult != null) {
					CourseVideoPopupWindowManager.getInstance().downLoadPopupWindow(courseVideoResult.getResult().getVideoList(), courseVideoResult.getResult().getCourseName());
				}
				break;
			case R.id.course_detail:
				if (courseVideoResult != null) {
					startActivity(new Intent(this, CourseVideoDetailActivity.class)
							.putExtra("courseName", courseVideoResult.getResult().getCourseName())
							.putExtra("teacherProfile", courseVideoResult.getResult().getTeacherProfile())
							.putExtra("teacherAvatar", courseVideoResult.getResult().getTeacherAvatar())
							.putExtra("teacherName", courseVideoResult.getResult().getTeacherName())
					);
				}
				break;
			case R.id.communicate_with_teacher:
				if (!SEAuthManager.getInstance().isAuthenticated()) {
					Intent loginIntent = new Intent(context, UserLoginActivity.class);
					startActivity(loginIntent);
					return;
				}
				if (lcChatKitUser == null) {
					return;
				}
				CustomUserProvider.getInstance().setpartUsers(lcChatKitUser);
				Intent intent = new Intent(context, LCIMConversationActivity.class);
				intent.putExtra(LCIMConstants.PEER_ID, lcChatKitUser.getUserId());
				startActivity(intent);
				break;
			case R.id.previous_video:
				playVideo(position - 1);
				break;
			case R.id.next_video:
				playVideo(position + 1);
				break;
			default:
				break;
		}
	}

	private void updateItemCountText(int count) {
		updateItemCount.setText("已更新" + count + "条音频");
	}

	int speedCount = 0;

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
		}, "", getString(R.string.to_login), "", "");
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
					Logger.e("msec=" + msec);
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
				if (!CourseVideoActivity.this.isFinishing()) {
					if (result.getApicode() != 10000) {
						ToastUtil.showToastShort(context,result.getMessage());
					} else {
						ToastUtil.showToastShort(CourseVideoActivity.this, result.getMessage());
						if (state.equals("1")) {
							courseVideoResult.getResult().setIsCollect("1");
//							videoCollectBtn.setImageResource(R.drawable.video_collect_icon_selected);
							setCollectDrawable(true);
						} else {
							courseVideoResult.getResult().setIsCollect("0");
							setCollectDrawable(false);
//							videoCollectBtn.setImageResource(R.drawable.video_collect_icon);
						}
					}
				}
			}

			@Override
			public void failure(RetrofitError error) {
				if (!CourseVideoActivity.this.isFinishing()) {
					ToastUtil.showToastShort(CourseVideoActivity.this, R.string.data_request_fail);
				}
			}
		});
	}

	private void setCollectDrawable(boolean isSetCollected) {
		Drawable drawable;
		if (isSetCollected) {
			drawable = context.getResources().getDrawable(R.drawable.video_collect_icon_selected);
		} else {
			drawable = context.getResources().getDrawable(R.drawable.video_collect_icon);
		}
//		videoCollectBtn.setCompoundDrawables(drawable, null, null, null);
		collectIcon.setImageDrawable(drawable);
	}

	public void downloadCourse(CourseVideoResult.ResultBean.VideoListBean videoInfo) {
		String target = "/sdcard/OlaAcademy/" + videoInfo.getId() + ".mp4";
		try {
			downloadManager.addNewDownload(videoInfo.getAddress(),
					videoInfo.getName(),
					videoInfo.getPic(),
					target,
					true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
					false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
					null);
		} catch (DbException e) {
			SVProgressHUD.showInViewWithoutIndicator(this, "添加下载失败", 2.0f);
		}
	}

	private void recordPlayProgress() {
		if (!SEAuthManager.getInstance().isAuthenticated() || courseVideoResult == null) {
			return;
		}
		SECourseManager.getInstance().recordPlayProgress(SEAuthManager.getInstance().getAccessUser().getId()
				, courseVideoResult.getResult().getPointId(), "1", String.valueOf(pdfPosition)
				, String.valueOf(msec / 1000), new Callback<SimpleResult>() {
					@Override
					public void success(SimpleResult simpleResult, Response response) {
					}

					@Override
					public void failure(RetrofitError error) {
					}
				});
	}

	private void shareCourse(CourseVideoResult.ResultBean.VideoListBean videoInfo) {
		mVideoView.pause();
		share = new SharePopupWindow(this);
		share.setPlatformActionListener(this);
		ShareModel model = new ShareModel();
		model.setImageUrl(SEConfig.getInstance().getAPIBaseURL() + "/ola/images/icon.png");
		model.setText(videoInfo.getName());
		model.setTitle("欧拉学院");
		model.setUrl("http://api.olaxueyuan.com/course.html?courseId=" + courseId);
		share.initShareParams(model);
		share.showShareWindow();
		// 显示窗口 (设置layout在PopupWindow中显示的位置)
		share.showAtLocation(findViewById(R.id.root),
				Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
	}

	/////////////////////// 分享相关  ///////////////////////
	@Override
	public void onCancel(Platform arg0, int arg1) {
		Message msg = new Message();
		msg.what = 0;
		UIHandler.sendMessage(msg, this);
	}

	@Override
	public void onComplete(Platform plat, int action,
						   HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	@Override
	public void onError(Platform arg0, int arg1, Throwable arg2) {
		Message msg = new Message();
		msg.what = 1;
		UIHandler.sendMessage(msg, this);
	}

	@Override
	public boolean handleMessage(Message msg) {
		int what = msg.what;
		if (what == 1) {
			Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
		}
		if (share != null) {
			share.dismiss();
		}
		return false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
		EventBus.getDefault().unregister(this);
	}
}
