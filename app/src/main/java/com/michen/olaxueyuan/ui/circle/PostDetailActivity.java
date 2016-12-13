package com.michen.olaxueyuan.ui.circle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
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
import com.michen.olaxueyuan.protocol.manager.MCCircleManager;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.model.SEUser;
import com.michen.olaxueyuan.protocol.result.CommentModule;
import com.michen.olaxueyuan.protocol.result.CommentSucessResult;
import com.michen.olaxueyuan.protocol.result.PostDetailModule;
import com.michen.olaxueyuan.protocol.result.PraiseCirclePostResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.PostCommentAdapter;
import com.michen.olaxueyuan.ui.adapter.PostDetailBottomGridAdapter;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.photo.util.NoScrollGridView;
import com.snail.svprogresshud.SVProgressHUD;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

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

    private Context mContext;
    private CommentModule.ResultBean commentResultBean;
    private int circleId;
    private PostDetailModule.ResultBean resultBean;
    private PostDetailBottomGridAdapter bottomGridAdapter;
    Vibrator vibrator;

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
        commentAdapter = new PostCommentAdapter(mContext);
        listView.setAdapter(commentAdapter);
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
                Logger.json(commentModule);
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
            gridview.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.comment_praise, R.id.comment, R.id.share, R.id.bt_send, R.id.avatar, R.id.key_voice
            , R.id.view_more, R.id.voice_record_img, R.id.voice_bg, R.id.voice_again})
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
                addComment();
                break;
            case R.id.avatar:
                if (!TextUtils.isEmpty(resultBean.getUserAvatar())) {
                    PictureUtil.viewPictures(mContext, resultBean.getUserAvatar());
                }
                break;
            case R.id.key_voice:
                bottomViewGrid.setVisibility(View.GONE);
                bottomView.setVisibility(View.VISIBLE);
                voiceRecordView.setVisibility(View.VISIBLE);
                voiceRecordedView.setVisibility(View.GONE);

                break;
            case R.id.view_more:
                bottomViewGrid.setVisibility(View.VISIBLE);
                break;
            case R.id.voice_record_img:
                break;
            case R.id.voice_bg:
                break;
            case R.id.voice_again:
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
                    , content, LocationManager.location, "2", new Callback<CommentSucessResult>() {
                        @Override
                        public void success(CommentSucessResult commentSuccess, Response response) {
                            SEAPP.dismissAllowingStateLoss();
                            etContent.setText("");
                            etContent.clearComposingText();
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

    private void closeIme() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etContent.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        AndroidUtil.stopVibrate(vibrator);
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
    private final int VOICE_BUTTON_ENABLE = 108;// voiceButton设置可点击
    private final int HIDE_TIP_SHOW = 109;// tipShowTimeText 设置为gone
    public static final int STOP_VIBRATE = 110;// 停止震动

    private void initAudio() {
        audioManager = MyAudioManager.getIntance(this, this);
        if (audioManager.isPlaying()) {
            audioManager.stopPlaying();
        }
        audioManager.setRecordingListener(this);
        voiceRecordImg.setOnTouchListener(new RecordListener());
        tipLayout.setVisibility(View.GONE);
    }

    /**
     * @param gone    是否显示覆盖布局
     * @param imageId 设置图片
     * @param text    显示的文字
     */
    private void setScrollTips(boolean gone, int imageId, String text) {
        if (gone) {
            tipLayout.setVisibility(View.GONE);
        } else {
            tipLayout.setVisibility(View.VISIBLE);
        }
        tipShowImg.setBackgroundResource(imageId);
        tipShowTimeText.setVisibility(View.VISIBLE);
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
                case TIME_SHORT_TIPS:
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
                        //Todo 录制成功
                    }
                    break;
                case START_PLAY://开始播放
                    audioManager.startPlayingVoiceByCache(mVoiceFilePath);
                    break;
                case STOP_PLAY://停止播放
                    if (audioManager != null && audioManager.getMediaPlayer() != null
                            && audioManager.getMediaPlayer().isPlaying()) {
                        audioManager.stopPlaying();
                    }
                    break;
                case VOICE_BUTTON_ENABLE:
                    voiceRecordImg.setPressed(false);
                    voiceRecordImg.setEnabled(true);
                    break;
                case HIDE_TIP_SHOW:
                    setScrollTips(true, R.drawable.kd_info_chat_voice_ing, getString(R.string.scroll_up_cancel_send));
                    break;
                case STOP_VIBRATE:
                    AndroidUtil.stopVibrate(vibrator);
                    break;
            }
        }
    };

    @Override
    public void onLoadingUpdate(MediaPlayer mp, int percent, int position, String dataUrl) {

    }

    @Override
    public void onLoadingError(MediaPlayer mp, int what, int extra, int position, String dataUrl) {

    }

    @Override
    public void onLoadingComplete(MediaPlayer mp, int position, String dataUrl) {

    }

    @Override
    public void updateProcess(long pos, int second, int position, String dataUrl) {

    }

    @Override
    public void onPlayingComplete(MediaPlayer mp, int duration, int position, String dataUrl) {

    }

    @Override
    public void onStopPlaying(int position, String dataUrl) {

    }

    @Override
    public void onPlayingFailed(Object e, int position, String dataUrl) {

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

}
