package com.michen.olaxueyuan.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.NoScrollGridAdapter;
import com.michen.olaxueyuan.common.RoundRectImageView;
import com.michen.olaxueyuan.common.manager.CommonConstant;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.MyAudioManager;
import com.michen.olaxueyuan.common.manager.PictureUtils;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.model.SEUser;
import com.michen.olaxueyuan.protocol.result.CommentModule;
import com.michen.olaxueyuan.ui.circle.PostDetailActivity;
import com.michen.olaxueyuan.ui.circle.upload.VideoThumbnailUtil;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.photo.util.NoScrollGridView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by mingge on 2016/7/14.
 */
public class PostCommentAdapterV3 extends BaseAdapter implements MyAudioManager.PlayingListener {
    private Context mContext;
    private List<CommentModule.ResultBean> list = new ArrayList<>();
    private MyAudioManager audioManager;
    private MyAudioManager.PlayingListener listener;
    private AnimationDrawable animationDrawable;
    HttpUtils http = new HttpUtils();
    HttpHandler handler;

    public PostCommentAdapterV3(Activity context) {
        mContext = context;
        audioManager = MyAudioManager.getIntance(this, context);
        listener = this;
    }

    public void upDateData(List<CommentModule.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_comment_v3, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.itemCommentAvatar.setRectAdius(100);
        holder.itemCommentName.setText(list.get(position).getUserName());
        if (!TextUtils.isEmpty(list.get(position).getToUserName())) {
            String comment = "@" + list.get(position).getToUserName() + "  " + list.get(position).getContent();
            SpannableStringBuilder builder = new SpannableStringBuilder(comment);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.rgb(0, 144, 255));
            builder.setSpan(redSpan, 0, list.get(position).getToUserName().length() + 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.itemCommentOriginalContent.setText(builder);
        } else {
            holder.itemCommentOriginalContent.setText(list.get(position).getContent());
        }
        holder.commentTime.setText(list.get(position).getTime());
        holder.numRead.setText(list.get(position).getSubCount());
        if (!TextUtils.isEmpty(list.get(position).getUserAvatar())) {
            String avatarUrl = "";
            if (list.get(position).getUserAvatar().contains("http://")) {
                avatarUrl = list.get(position).getUserAvatar();
            } else if (list.get(position).getUserAvatar().contains(".")) {
                avatarUrl = SEConfig.getInstance().getAPIBaseURL() + "/upload/" + list.get(position).getUserAvatar();
            } else {
                avatarUrl = SEAPP.PIC_BASE_URL + list.get(position).getUserAvatar();
            }
            Picasso.with(mContext).load(avatarUrl).placeholder(R.drawable.ic_default_avatar)
                    .error(R.drawable.ic_default_avatar).resize(Utils.dip2px(mContext, 50), Utils.dip2px(mContext, 50)).into(holder.itemCommentAvatar);
        }
        if (!TextUtils.isEmpty(list.get(position).getAudioUrls())) {
            holder.voiceBg.setVisibility(View.VISIBLE);
        } else {
            holder.voiceBg.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(list.get(position).getVideoUrls())) {
            holder.videoView.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(SEAPP.MEDIA_BASE_URL + "/" + list.get(position).getVideoImgs()).placeholder(R.drawable.system_wu)
                    .error(R.drawable.system_wu).resize(Utils.dip2px(mContext, 241), Utils.dip2px(mContext, 132)).into(holder.videoImage);
        } else {
            holder.videoView.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(list.get(position).getImageIds())) {
            holder.imageGridview.setVisibility(View.VISIBLE);
            ArrayList<String> imageUrls = PictureUtils.getListFromString(list.get(position).getImageIds());
            final ArrayList<String> imageList = imageUrls;
            if (imageUrls.size() == 1) {
                holder.imageGridview.setNumColumns(1);
            } else {
                holder.imageGridview.setNumColumns(3);
            }
            holder.imageGridview.setAdapter(new NoScrollGridAdapter(mContext, imageUrls, 2));
            // 点击回帖九宫格，查看大图
            holder.imageGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PictureUtils.viewPictures(mContext, position, imageList);
                }
            });
        } else {
            holder.imageGridview.setVisibility(View.GONE);
        }
        switch (list.get(position).getVoiceState()) {
            case 0:
                holder.voiceState.setImageResource(R.drawable.kd_info_chat_left_voice3);
                break;
            case 1:
                holder.voiceState.setImageResource(R.drawable.kd_found_voice_loading);
                break;
            case 2:
                holder.voiceState.setImageResource(R.drawable.left_voice_play);
                animationDrawable = (AnimationDrawable) holder.voiceState.getDrawable();
                animationDrawable.start();
                break;
        }
        holder.numPraised.setText(String.valueOf(list.get(position).getPraiseNumber()));
        holder.numPraised.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((PostDetailActivity) (mContext)).praise();
            }
        });
        holder.itemCommentAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureUtils.viewPictures(mContext, list.get(position).getUserAvatar());
            }
        });
        holder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String videoUrl = list.get(position).getVideoUrls();
                // 调用系统自带的播放器来播放流媒体视频
                Intent playIntent = new Intent(Intent.ACTION_VIEW);
                if (!TextUtils.isEmpty(videoUrl)) {
                    String extension = MimeTypeMap.getFileExtensionFromUrl(videoUrl);
                    String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                    playIntent.setDataAndType(Uri.parse(SEAPP.MEDIA_BASE_URL + "/" + videoUrl), mimeType);

                } else {
                    ToastUtil.showToastShort(mContext, "播放地址不存在");
                    return;
                }
                Intent finalIntent = VideoThumbnailUtil.filterIntentByType(mContext, playIntent);
                mContext.startActivity(finalIntent);
            }
        });
        holder.voiceBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getVoiceState() == 2) {//在播放中的点击一次停止播放
                    if (audioManager != null && audioManager.isPlaying()) {
                        audioManager.stopPlaying();
                        return;
                    }
                }
                downloadAudio(list.get(position).getAudioUrls(), position);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SEUser user = SEAuthManager.getInstance().getAccessUser();
                if (user != null) {
                    if (user.getId().equals(String.valueOf(list.get(position).getUserId()))) {
                        ToastUtil.showToastShort(mContext, R.string.no_comment_self);
                    } else {
                        /**
                         * {@link PostDetailActivity#onEventMainThread(CommentModule.ResultBean)}
                         */
                        EventBus.getDefault().post(list.get(position));
                    }
                } else {
                    mContext.startActivity(new Intent(mContext, UserLoginActivity.class));
                }
            }
        });
        return convertView;
    }

    @Override
    public void onLoadingUpdate(MediaPlayer mp, int percent, int position, String dataUrl) {

    }

    @Override
    public void onLoadingError(MediaPlayer mp, int what, int extra, int position, String dataUrl) {
        if (position < 0 || position >= list.size()) {
            return;
        }
        list.get(position).setVoiceState(0);
        PostCommentAdapterV3.this.notifyDataSetChanged();
    }

    @Override
    public void onLoadingComplete(MediaPlayer mp, int position, String dataUrl) {
        if (position < 0 || position >= list.size()) {
            return;
        }
        list.get(position).setVoiceState(2);
        PostCommentAdapterV3.this.notifyDataSetChanged();
    }

    @Override
    public void updateProcess(long pos, int second, int position, String dataUrl) {
        if (position < 0 || position >= list.size()) {
            return;
        }
        if (list.get(position).getVoiceState() == 2) {
            return;
        }
        list.get(position).setVoiceState(2);
        PostCommentAdapterV3.this.notifyDataSetChanged();
    }

    @Override
    public void onPlayingComplete(MediaPlayer mp, int duration, int position, String dataUrl) {
        if (position < 0 || position >= list.size()) {
            return;
        }
        list.get(position).setVoiceState(0);
        PostCommentAdapterV3.this.notifyDataSetChanged();
    }

    @Override
    public void onStopPlaying(int position, String dataUrl) {
        if (position < 0 || position >= list.size()) {
            return;
        }
        list.get(position).setVoiceState(0);
        this.notifyDataSetChanged();
    }

    @Override
    public void onPlayingFailed(Object e, int position, String dataUrl) {
        if (position < 0 || position >= list.size()) {
            return;
        }
        list.get(position).setVoiceState(0);
        this.notifyDataSetChanged();
    }

    private void downloadAudio(String url, final int position) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastUtil.showToastShort(mContext, R.string.need_sd);
            return;
        }
        String outFileDir = CommonConstant.RECORD_PATH;
        File fileDir = new File(outFileDir);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        String[] split = url.split("/");
        String name = split[split.length - 1];
        final String mFilePath = outFileDir + "/" + name;
        Logger.e("mFilePath==" + mFilePath);
        final File file = new File(mFilePath);
        if (file.exists()) {
            stopVoice();
            list.get(position).setVoiceState(2);
            audioManager.setPlayingListener(listener);
            audioManager.startPlayingVoiceByCache(mFilePath, position);
            notifyDataSetChanged();
            return;
        }
        handler = http.download(SEAPP.MEDIA_BASE_URL + "/" + url, mFilePath,
                true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                true, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                new RequestCallBack<File>() {
                    @Override
                    public void onStart() {
                        list.get(position).setVoiceState(1);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        Logger.e("current==" + current + ";(int) (current / total * 100)==" + (int) (current / total * 100));
                        list.get(position).setVoiceState(1);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        list.get(position).setVoiceState(2);
                        stopVoice();
                        audioManager.setPlayingListener(listener);
                        audioManager.startPlayingVoiceByCache(mFilePath, position);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        list.get(position).setVoiceState(0);
                        notifyDataSetChanged();
                    }
                });
    }

    public void stopDownload() {
        if (handler != null) {
            handler.cancel();
        }
    }

    public void stopVoice() {
        if (audioManager != null && audioManager.isPlaying()) {
            audioManager.stopPlaying();
        }
    }

    class ViewHolder {
        @Bind(R.id.item_comment_avatar)
        RoundRectImageView itemCommentAvatar;
        @Bind(R.id.item_comment_name)
        TextView itemCommentName;
        @Bind(R.id.item_comment_originalContent)
        TextView itemCommentOriginalContent;
        @Bind(R.id.video_image)
        ImageView videoImage;
        @Bind(R.id.video_view)
        FrameLayout videoView;
        @Bind(R.id.voice_icon)
        ImageView voiceIcon;
        @Bind(R.id.voice_state)
        ImageView voiceState;
        @Bind(R.id.voice_time)
        TextView voiceTime;
        @Bind(R.id.voice_bg)
        RelativeLayout voiceBg;
        @Bind(R.id.image_gridview)
        NoScrollGridView imageGridview;
        @Bind(R.id.media_view)
        FrameLayout mediaView;
        @Bind(R.id.child_content)
        TextView childContent;
        @Bind(R.id.num_read)
        TextView numRead;
        @Bind(R.id.comment_time)
        TextView commentTime;
        @Bind(R.id.comment_layout)
        RelativeLayout commentLayout;
        @Bind(R.id.num_praised)
        Button numPraised;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
