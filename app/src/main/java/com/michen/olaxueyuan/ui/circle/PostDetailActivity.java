package com.michen.olaxueyuan.ui.circle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.NoScrollGridAdapter;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.SubListView;
import com.michen.olaxueyuan.common.manager.AndUtil;
import com.michen.olaxueyuan.common.manager.AndroidUtil;
import com.michen.olaxueyuan.common.manager.LocationManager;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.MyAudioManager;
import com.michen.olaxueyuan.common.manager.PictureUtil;
import com.michen.olaxueyuan.common.manager.SharePlatformManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.event.VideoRefreshEvent;
import com.michen.olaxueyuan.protocol.manager.MCCircleManager;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.UploadMediaManager;
import com.michen.olaxueyuan.protocol.model.SEUser;
import com.michen.olaxueyuan.protocol.result.CommentModule;
import com.michen.olaxueyuan.protocol.result.CommentSucessResult;
import com.michen.olaxueyuan.protocol.result.PostDetailModule;
import com.michen.olaxueyuan.protocol.result.PraiseCirclePostResult;
import com.michen.olaxueyuan.protocol.result.VideoUploadResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.PostCommentAdapter;
import com.michen.olaxueyuan.ui.adapter.PostDetailBottomGridAdapter;
import com.michen.olaxueyuan.ui.adapter.PostDetailVideoGridAdapter;
import com.michen.olaxueyuan.ui.circle.upload.Video;
import com.michen.olaxueyuan.ui.circle.upload.VideoThumbnailUtil;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.photo.util.ImageItem;
import com.snail.photo.util.NoScrollGridView;
import com.snail.photo.util.PicInfo;
import com.snail.svprogresshud.SVProgressHUD;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.snail.photo.util.Bimp.tempSelectBitmap;

public class PostDetailActivity extends SEBaseActivity implements MyAudioManager.RecordingListener, MyAudioManager.PlayingListener {
    @Bind(R.id.avatar)
    RoundRectImageView avatar;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.study_name)
    TextView studyName;
    @Bind(R.id.gridview)
    NoScrollGridView gridview;
    @Bind(R.id.comment_comment)
    TextView commentComment;
    @Bind(R.id.comment_empty)
    TextView commentEmpty;
    @Bind(R.id.comment_list)
    SubListView listView;
    @Bind(R.id.comment_praise)
    TextView commentPraise;
    @Bind(R.id.comment)
    ImageView comment;
    @Bind(R.id.share)
    ImageView share;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.bt_send)
    Button btSend;
    @Bind(R.id.comment_layout)
    RelativeLayout commentLayout;
    @Bind(R.id.key_voice)
    ImageView keyVoice;
    @Bind(R.id.view_more)
    ImageView viewMore;
    @Bind(R.id.bottom_view_grid)
    GridView bottomViewGrid;

    PostCommentAdapter commentAdapter;
    @Bind(R.id.voice_record_img)
    ImageView voiceRecordImg;
    @Bind(R.id.voice_record_view)
    LinearLayout voiceRecordView;
    @Bind(R.id.voice_state)
    ImageView voiceState;
    @Bind(R.id.voice_bg)
    LinearLayout voiceBg;
    @Bind(R.id.voice_time)
    TextView voiceTime;
    @Bind(R.id.voice_again)
    Button voiceAgain;
    @Bind(R.id.voice_recorded_view)
    LinearLayout voiceRecordedView;
    @Bind(R.id.tip_show_img)
    ImageView tipShowImg;
    @Bind(R.id.tip_show_time_text)
    TextView tipShowTimeText;
    @Bind(R.id.tip_show_text)
    TextView tipShowText;
    @Bind(R.id.tip_layout)
    LinearLayout tipLayout;
    @Bind(R.id.bottom_view)
    FrameLayout bottomView;
    @Bind(R.id.bottom_video_img_grid)
    NoScrollGridView bottomVideoImgGrid;

    private Context mContext;
    private CommentModule.ResultBean commentResultBean;
    private PostDetailModule.ResultBean resultBean;
    private PostDetailBottomGridAdapter bottomGridAdapter;
    private PostDetailVideoGridAdapter videoGridAdapter;
    Vibrator vibrator;
    private int circleId;
    private String imageIds;//上传之后的图片id
    private String videoUrls;//上传视频之后视频的url
    private String videoImgs;//上传视频之后获取到的img
    private String audioUrls;//上传音频

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail_v2);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        mContext = this;
        etContent.clearFocus();
        setTitleText("欧拉分享");
        initView();
        initAudio();//初始化播放录音的操作
        LocationManager.getInstance().locations(this);
        getCommentListData();
    }

    private void initView() {
        circleId = getIntent().getIntExtra("circleId", 0);
        queryCircleDetail(String.valueOf(circleId));
        commentAdapter = new PostCommentAdapter(this);
        listView.setAdapter(commentAdapter);
        videoGridAdapter = new PostDetailVideoGridAdapter(this);
        bottomVideoImgGrid.setAdapter(videoGridAdapter);
        bottomGridAdapter = new PostDetailBottomGridAdapter(this);
        bottomViewGrid.setAdapter(bottomGridAdapter);
        bottomViewGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消GridView中Item选中时默认的背景色
        bottomViewGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (bottomGridAdapter.listBeans.get(position).getPosition()) {
                    case PostDetailBottomGridAdapter.CAMERA://拍照
                        break;
                    case PostDetailBottomGridAdapter.PICTURE://相册
                        break;
                    case PostDetailBottomGridAdapter.VIDEO://视频
                        break;
                    case PostDetailBottomGridAdapter.VIDEO_RECORD://录屏
                        startActivity(new Intent(PostDetailActivity.this, MultiSelectVideoListActivity.class));
                        break;
                }
            }
        });
    }

    private void getCommentListData() {
        SEAPP.showCatDialog(this);
        QuestionCourseManager.getInstance().getCommentList(String.valueOf(circleId), "2", new Callback<CommentModule>() {
            @Override
            public void success(CommentModule commentModule, Response response) {
//                Logger.json(commentModule);
                SEAPP.dismissAllowingStateLoss();
                if (commentModule.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(mContext, commentModule.getMessage(), 2.0f);
                } else {
                    commentAdapter.upDateData(commentModule.getResult());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                SEAPP.dismissAllowingStateLoss();
            }
        });
    }

    private void queryCircleDetail(String circleId) {
        SEAPP.showCatDialog(this);
        QuestionCourseManager.getInstance().queryCircleDetail(circleId, new Callback<PostDetailModule>() {
            @Override
            public void success(PostDetailModule postDetailModule, Response response) {
                SEAPP.dismissAllowingStateLoss();
                if (postDetailModule.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(mContext, postDetailModule.getMessage(), 2.0f);
                } else {
                    resultBean = postDetailModule.getResult();
                    updateDetail(postDetailModule.getResult());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                SEAPP.dismissAllowingStateLoss();
            }
        });
    }

    private void updateDetail(PostDetailModule.ResultBean resultBean) {
        avatar.setRectAdius(100);
        title.setText(resultBean.getUserName());
        if (!TextUtils.isEmpty(resultBean.getUserAvatar())) {
            String avatarUrl = "";
//            if (resultBean.getUserAvatar().indexOf("jpg") != -1) {
            if (resultBean.getUserAvatar().contains(".")) {
                avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + resultBean.getUserAvatar();
            } else {
                avatarUrl = SEAPP.PIC_BASE_URL + resultBean.getUserAvatar();
            }
            Picasso.with(mContext).load(avatarUrl).placeholder(R.drawable.ic_default_avatar)
                    .error(R.drawable.ic_default_avatar).resize(Utils.dip2px(mContext, 50), Utils.dip2px(mContext, 50)).into(avatar);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                avatar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_default_avatar, null));
            } else {
                avatar.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_default_avatar));
            }
        }
        time.setText(resultBean.getTime());
        studyName.setText(resultBean.getContent());
        commentPraise.setText(String.valueOf(resultBean.getPraiseNumber()));
        if (!TextUtils.isEmpty(resultBean.getLocation())) {
            address.setText("@" + resultBean.getLocation());
        } else {
            address.setText("");
        }
        if (!TextUtils.isEmpty(resultBean.getImageGids())) {
            ArrayList<String> imageUrls = PictureUtil.getListFromString(resultBean.getImageGids());
            final ArrayList<String> imageList = imageUrls;
            if (imageUrls.size() == 1) {
                gridview.setNumColumns(1);
            } else {
                gridview.setNumColumns(3);
            }
            gridview.setAdapter(new NoScrollGridAdapter(mContext, imageUrls));
            // 点击回帖九宫格，查看大图
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PictureUtil.viewPictures(mContext, position, imageList);
                }
            });
        } else {
            gridview.setVisibility(GONE);
        }
    }

    @OnClick({R.id.comment_praise, R.id.comment, R.id.share, R.id.bt_send, R.id.avatar, R.id.key_voice
            , R.id.view_more, R.id.voice_bg, R.id.voice_again})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.comment_praise:
                praise();
                break;
            case R.id.comment:
                Utils.showInputMethod(PostDetailActivity.this);
                this.commentResultBean = null;
                break;
            case R.id.share:
                SharePlatformManager.getInstance().share(mContext, findViewById(R.id.circle_detail)
                        , resultBean.getUserAvatar(), String.valueOf(resultBean.getId()), resultBean.getContent());
                break;
            case R.id.bt_send:
//                addComment();
                sendAudio();
                break;
            case R.id.avatar:
                if (!TextUtils.isEmpty(resultBean.getUserAvatar())) {
                    PictureUtil.viewPictures(mContext, resultBean.getUserAvatar());
                }
                break;
            case R.id.key_voice:
                if (bottomView.getVisibility() == VISIBLE) {
                    showView(GONE, GONE, VISIBLE, GONE, GONE, true);
                } else {
                    showView(GONE, VISIBLE, VISIBLE, GONE, GONE, true);
                }
                break;
            case R.id.view_more:
                if (bottomViewGrid.getVisibility() == VISIBLE) {
                    showView(GONE, GONE, VISIBLE, GONE, GONE, true);
                } else {
                    showView(VISIBLE, GONE, VISIBLE, GONE, GONE, true);
                }
                break;
            case R.id.voice_bg:
                handler.sendEmptyMessage(START_PLAY);
                break;
            case R.id.voice_again:
                showView(GONE, VISIBLE, VISIBLE, GONE, GONE, true);
                break;
            default:
                break;
        }
    }

    private void praise() {
        SEAPP.showCatDialog(this);
        MCCircleManager.getInstance().praiseCirclePost(String.valueOf(circleId), new Callback<PraiseCirclePostResult>() {
            @Override
            public void success(PraiseCirclePostResult mcCommonResult, Response response) {
                SEAPP.dismissAllowingStateLoss();
                if (mcCommonResult.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(mContext, mcCommonResult.getMessage(), 2.0f);
                } else {
                    resultBean.setPraiseNumber(resultBean.getPraiseNumber() + 1);
                    commentPraise.setText(String.valueOf(resultBean.getPraiseNumber()));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                SEAPP.dismissAllowingStateLoss();
                ToastUtil.showToastShort(mContext, R.string.data_request_fail);
            }
        });
    }

    private void sendAudio() {
        uploadAudioVideo(new TypedFile("application/octet-stream", new File(mVoiceFilePath)), "amr");
    }

    private void uploadAudioVideo(TypedFile typeFile, final String type) {
        SEAPP.showCatDialog(this, "正在上传文件，请稍后...");
        UploadMediaManager.getInstance().movieUpload(typeFile, type, new Callback<VideoUploadResult>() {
            @Override
            public void success(VideoUploadResult uploadResult, Response response) {
                SEAPP.dismissAllowingStateLoss();
                if (uploadResult.getCode() != 1) {
                    SVProgressHUD.showInViewWithoutIndicator(mContext, uploadResult.getMessage(), 2.0f);
                } else {
                    Logger.e("type==" + type);
                    if (type.equals("amr")) {
                        audioUrls = uploadResult.getUrl();
                        addComment();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                SEAPP.dismissAllowingStateLoss();
                ToastUtil.showToastShort(mContext, R.string.data_request_fail);
            }
        });
    }


    /**
     * {@link PostCommentAdapter#getView(int, View, ViewGroup)}
     */
    public void onEventMainThread(CommentModule.ResultBean comments) {
        Utils.showInputMethod(PostDetailActivity.this);
        this.commentResultBean = comments;
        etContent.setFocusable(true);
    }

    private void addComment() {
        SEUser user = SEAuthManager.getInstance().getAccessUser();
        if (user != null) {
            String postId = String.valueOf(circleId);
            String toUserId;
            String content = etContent.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                ToastUtil.showToastShort(mContext, R.string.fill_comment_content);
                return;
            }
            if (commentResultBean != null) {
                Logger.json(commentResultBean);
                toUserId = String.valueOf(commentResultBean.getUserId());
            } else {
                toUserId = "";
            }
            SEAPP.showCatDialog(this, "发送中请稍后...");
            QuestionCourseManager.getInstance().addComment(user.getId(), postId, toUserId
                    , content, LocationManager.location, "2", imageIds, videoUrls, videoImgs, audioUrls, new Callback<CommentSucessResult>() {
                        @Override
                        public void success(CommentSucessResult commentSuccess, Response response) {
                            SEAPP.dismissAllowingStateLoss();
                            etContent.setText("");
                            etContent.clearComposingText();
                            showView(GONE, GONE, GONE, GONE, GONE, true);
                            getCommentListData();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            SEAPP.dismissAllowingStateLoss();
                        }
                    });
            this.commentResultBean = null;
        } else {
            startActivity(new Intent(mContext, UserLoginActivity.class));
        }
    }

    /**
     * 音频操作
     */
    private final int RELEASE_CANCEL = 102;//松开取消
    private final int START_RECORD = 103;//开始录音
    private final int STOP_RECORD = 104;//停止录音
    private final int START_PLAY = 105;//开始播放
    private final int STOP_PLAY = 106;//停止播放
    private final int START_RECORD_TIPS = 114;//开始录音提示
    private final int TIME_SHORT_TIPS = 115;//时间太短提示
    private String mVoiceFilePath = "";//录音保存路径
    private String url = "";
    private int second = 0;
    private MyAudioManager audioManager;
    boolean isRecording = false;
    private final int VOICE_BUTTON_ENABLE = 108;// voiceRecordImg设置可点击
    private final int HIDE_TIP_SHOW = 109;// tipShowTimeText 设置为gone
    public static final int STOP_VIBRATE = 110;// 停止震动
    private AnimationDrawable animationDrawable;
    private boolean isPlaying = false;//是否正在播放

    private void initAudio() {
        audioManager = MyAudioManager.getIntance(this, this);
        if (audioManager.isPlaying()) {
            audioManager.stopPlaying();
        }
        audioManager.setRecordingListener(this);
        voiceRecordImg.setOnTouchListener(new RecordListener());
        tipLayout.setVisibility(GONE);
    }

    /**
     * @param gone    是否显示覆盖布局
     * @param imageId 设置图片
     * @param text    显示的文字
     */
    private void setScrollTips(boolean gone, int imageId, String text) {
        if (gone) {
            tipLayout.setVisibility(GONE);
        } else {
            tipLayout.setVisibility(VISIBLE);
        }
        tipShowImg.setBackgroundResource(imageId);
        tipShowTimeText.setVisibility(VISIBLE);
        tipShowText.setText(text);
    }

    private float startY;
    private float endY;
    private boolean cancelRecord = false;//是否取消了录音
    private boolean is_sixty_second = false;//是否超过60秒
    private long startPressTime, deltaTime;
    private boolean isTimeShort = false;// 时间太短

    private class RecordListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (v.getId() == R.id.voice_record_img) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        voiceRecordImg.setPressed(true);
                        startY = event.getRawY();
                        handler.sendEmptyMessage(START_RECORD);
                        handler.sendEmptyMessageDelayed(START_RECORD_TIPS, 200);
                        startPressTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (!is_sixty_second) {
                            voiceRecordImg.setPressed(true);
                            endY = event.getRawY();
                            if (startY - endY > 120) {
                                cancelRecord = true;
                                handler.sendEmptyMessage(RELEASE_CANCEL);
                            } else {
                                deltaTime = System.currentTimeMillis() - startPressTime;
                                if (deltaTime > 1000) {
                                    cancelRecord = false;
                                    setScrollTips(false, R.drawable.kd_info_chat_voice_ing, getString(R.string.scroll_up_cancel_send));
                                }
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        deltaTime = System.currentTimeMillis() - startPressTime;
                        if (deltaTime < 990 && deltaTime > 200) {
                            isTimeShort = true;
                            handler.sendEmptyMessage(STOP_RECORD);
                            handler.sendEmptyMessage(TIME_SHORT_TIPS);
                            handler.sendEmptyMessage(VOICE_BUTTON_ENABLE);
                            break;
                        }
                        if (deltaTime >= 1000) {
                            isTimeShort = false;
                        } else {
                            isTimeShort = true;
                        }
                        if (!is_sixty_second) {
                            voiceRecordImg.setPressed(false);
                            handler.sendEmptyMessage(STOP_RECORD);
                        }
                        is_sixty_second = false;
                        handler.sendEmptyMessage(VOICE_BUTTON_ENABLE);
                        handler.sendEmptyMessage(HIDE_TIP_SHOW);
                        break;
                }
            }
            return true;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RELEASE_CANCEL://松开取消
                    setScrollTips(false, R.drawable.kd_info_chat_voice_delete, getString(R.string.release_cancel_send));
                    break;
                case START_RECORD://开始录音
                    audioManager.startRecording();
                    break;
                case START_RECORD_TIPS:
                    if (!isTimeShort || !voiceRecordImg.isPressed()) {
                        if (audioManager != null && audioManager.isRecording()) {
                            setScrollTips(false, R.drawable.kd_info_chat_voice_ing, getString(R.string.scroll_up_cancel_send));
                        }
                    }
                    break;
                case TIME_SHORT_TIPS://录音时间太短提示
                    setScrollTips(false, R.drawable.kd_info_chat_voice_exclamation, getString(R.string.time_too_short));
                    tipShowTimeText.setVisibility(View.INVISIBLE);
                    handler.sendEmptyMessageDelayed(STOP_RECORD, 300);
                    break;
                case STOP_RECORD://停止录音
                    audioManager.stopRecording();
                    setScrollTips(true, R.drawable.kd_info_chat_voice_ing, getString(R.string.scroll_up_cancel_send));
                    if (second == 0) {
                        if (audioManager != null && audioManager.getMediaPlayer() != null
                                && audioManager.getMediaPlayer().isPlaying()) {
                            audioManager.stopPlaying();
                            return;
                        }
                    }

                    if (!cancelRecord && !TextUtils.isEmpty(mVoiceFilePath) && second != 0) {//如果没有取消录音,并且路径存在
                        showView(GONE, VISIBLE, GONE, VISIBLE, GONE, true);
                        voiceTime.setText(second + "'");
                    }
                    break;
                case START_PLAY://开始播放音频
                    if (isPlaying) {
                        handler.sendEmptyMessage(STOP_PLAY);
                        return;
                    }
                    isPlaying = true;
                    audioManager.startPlayingVoiceByCache(mVoiceFilePath);
                    if (animationDrawable != null) {
                        if (animationDrawable.isRunning()) {
                            animationDrawable.stop();
                        }
                        animationDrawable = null;
                    }
                    animationDrawable = (AnimationDrawable) voiceState.getDrawable();
                    animationDrawable.start();
                    break;
                case STOP_PLAY://停止播放音频
                    isPlaying = false;
                    stopPlayAudio();
                    if (animationDrawable != null) {
                        if (animationDrawable.isRunning()) {
                            animationDrawable.stop();
                        }
                        animationDrawable = null;
                    }
                    voiceState.setImageResource(R.drawable.left_voice_play);
                    break;
                case VOICE_BUTTON_ENABLE:// voiceRecordImg设置可点击
                    voiceRecordImg.setPressed(false);
                    voiceRecordImg.setEnabled(true);
                    break;
                case HIDE_TIP_SHOW:// tipShowTimeText 设置为gone
                    setScrollTips(true, R.drawable.kd_info_chat_voice_ing, getString(R.string.scroll_up_cancel_send));
                    break;
                case STOP_VIBRATE:// 停止震动
                    AndroidUtil.stopVibrate(vibrator);
                    break;
                case GENERATE_ING_THUMBNAIL:// 正在生成缩略图
                    ToastUtil.showToastShort(mContext, "正在生成缩略图请稍后");
                    break;
                case GENERATED_THUMBNAIL:// 生成一张视频的缩略图，通知刷新
                    showView(GONE, VISIBLE, GONE, GONE, VISIBLE, true);
                    videoGridAdapter.update();
                    break;
            }
        }
    };

    private void stopPlayAudio() {
        if (audioManager != null && audioManager.getMediaPlayer() != null
                && audioManager.getMediaPlayer().isPlaying()) {
            audioManager.stopPlaying();
        }
    }

    @Override
    public void onLoadingUpdate(MediaPlayer mp, int percent, int position, String dataUrl) {

    }

    @Override
    public void onLoadingError(MediaPlayer mp, int what, int extra, int position, String dataUrl) {
        handler.sendEmptyMessage(STOP_PLAY);
    }

    @Override
    public void onLoadingComplete(MediaPlayer mp, int position, String dataUrl) {

    }

    @Override
    public void updateProcess(long pos, int second, int position, String dataUrl) {

    }

    @Override
    public void onPlayingComplete(MediaPlayer mp, int duration, int position, String dataUrl) {
        handler.sendEmptyMessage(STOP_PLAY);
    }

    @Override
    public void onStopPlaying(int position, String dataUrl) {
        handler.sendEmptyMessage(STOP_PLAY);
    }

    @Override
    public void onPlayingFailed(Object e, int position, String dataUrl) {
        handler.sendEmptyMessage(STOP_PLAY);
    }

    @Override
    public void updateRecordingTime(int current, long currentTimeMillis) {
        tipShowTimeText.setText(AndUtil.voiceCostTime(current));
        second = current;
        if (current >= 50 && current < 60) {
            tipShowTimeText.setText(getString(R.string.say_limit, String.valueOf(60 - current)));
            AndroidUtil.startVibrate(this, vibrator, handler);
        } else if (current >= 60) {
            tipShowTimeText.setText(getString(R.string.say_limit, String.valueOf(60 - current)));
            is_sixty_second = true;
            handler.sendEmptyMessage(STOP_RECORD);
            handler.sendEmptyMessage(HIDE_TIP_SHOW);
        }
    }

    @Override
    public void onRecordingStart(long startTimeMillis) {
        isRecording = true;
    }

    @Override
    public void onAmplitudesUpdate(int level) {

    }

    @Override
    public void onRecordingFailed(Object reason) {
        isRecording = false;
    }

    @Override
    public void onRecordingComplete(String filePath, long duration) {
        isRecording = false;
        mVoiceFilePath = audioManager.getRecordingFilePath();
    }

    /**
     * 视频操作
     */
    public static final int GENERATE_ING_THUMBNAIL = 116;// 正在生成缩略图
    public static final int GENERATED_THUMBNAIL = 117;// 生成一张视频的缩略图，通知刷新
    public static List<Video> selectedVideoList = new ArrayList<>();
    private ExecutorService mFixedExecutor = Executors.newFixedThreadPool(6);

    public void onEventMainThread(VideoRefreshEvent videoRefreshEvent) {
        if (videoRefreshEvent.type != 1) {
            return;
        }
        ArrayList<ImageItem> localTempSelectBitmap = new ArrayList<>();
        for (int i = 0; i < tempSelectBitmap.size(); i++) {
            if (tempSelectBitmap.get(i).tag.type.equals("3")) {
                localTempSelectBitmap.add(tempSelectBitmap.get(i));
            }
        }
        for (int i = 0; i < localTempSelectBitmap.size(); i++) {
            tempSelectBitmap.remove(localTempSelectBitmap.get(i));
        }
        if (selectedVideoList.size() > 0) {
            handler.sendEmptyMessage(GENERATE_ING_THUMBNAIL);
            showView(GONE, VISIBLE, GONE, GONE, VISIBLE, true);
        }
        for (final Video video : selectedVideoList) {
            mFixedExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = VideoThumbnailUtil.getVideoThumbnail(video.getPath(), 200, 200, MediaStore.Images.Thumbnails.MICRO_KIND);
                    video.setThumbnailBitmap(bitmap);
                    PicInfo pi = new PicInfo();
                    pi.type = "3";
                    pi.isNew = true;
                    pi.path = video.getPath();
                    ImageItem imageItem = new ImageItem();
                    imageItem.setBitmap(video.getThumbnailBitmap());
                    imageItem.tag = pi;
                    tempSelectBitmap.add(imageItem);
                    handler.sendEmptyMessage(GENERATED_THUMBNAIL);
                }
            });
        }
    }

    /**
     * 显示底部视图
     *
     * @param bottomViewGridV    底部拍照、相册、视频、录屏
     * @param bottomViewV        语音显示，语音录制、视频显示、录屏显示界面
     * @param voiceRecordViewV   点击录音界面
     * @param voiceRecordedViewV 录制完成可点击播放界面
     * @param closeIme           是否关闭或者开启键盘
     */
    private void showView(int bottomViewGridV, int bottomViewV, int voiceRecordViewV, int voiceRecordedViewV, int bottomVideoImgGridV, boolean closeIme) {
        bottomViewGrid.setVisibility(bottomViewGridV);
        bottomView.setVisibility(bottomViewV);
        voiceRecordView.setVisibility(voiceRecordViewV);
        voiceRecordedView.setVisibility(voiceRecordedViewV);
        bottomVideoImgGrid.setVisibility(bottomVideoImgGridV);
        if (closeIme) {
            etContent.clearFocus();
            closeIme();
        }
    }

    private void closeIme() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        commentAdapter.stopVoice();
        commentAdapter.stopDownload();
        AndroidUtil.stopVibrate(vibrator);
        selectedVideoList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharePlatformManager.getInstance().dismissShareView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SEAPP.dismissAllowingStateLoss();
    }
}
