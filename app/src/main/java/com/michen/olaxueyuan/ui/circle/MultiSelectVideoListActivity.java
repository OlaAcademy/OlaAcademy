package com.michen.olaxueyuan.ui.circle;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.DateUtils;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.protocol.event.VideoRefreshEvent;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.circle.upload.Video;
import com.michen.olaxueyuan.ui.circle.upload.VideoProvider;
import com.michen.olaxueyuan.ui.circle.upload.VideoThumbnailUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.greenrobot.event.EventBus;

import static com.michen.olaxueyuan.ui.circle.PostDetailActivity.selectedVideoList;

public class MultiSelectVideoListActivity extends SEBaseActivity {

    private ListView mListView;
    private VideoListAdapter mVideoAdapter;
    private List<Video> mVideoList = new ArrayList<>();

    private Context mContext;
    private static int COUNT_THREAD_FIXED = 6;
    private ExecutorService mFixedExecutor = Executors.newFixedThreadPool(COUNT_THREAD_FIXED);

    private int mVideoCount;
    private boolean isRefreshVideo;//false不回头刷新视频缩略图，true刷新

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_select_video_list);
        mContext = this;
        mListView = (ListView) findViewById(R.id.lv_video);
        setTitleText("选择视频");
        init();
    }

    private void init() {
//        SVProgressHUD.showInView(mContext, "资源获取中...", true);
        mVideoList = (List<Video>) new VideoProvider(mContext).getList();
        mVideoAdapter = new VideoListAdapter(this);
        mListView.setAdapter(mVideoAdapter);
        setRightImage(R.drawable.bg_complete);
        setRightImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRefreshVideo) {
                    EventBus.getDefault().post(new VideoRefreshEvent(1));
                }
                finish();
            }
        });
        //获取视频缩略图并加入到videoList中
        for (final Video video : mVideoList) {
            if (MultiSelectVideoListActivity.this.isFinishing()) {
                mFixedExecutor.shutdownNow();
                return;
            }
            mFixedExecutor.submit(new Runnable() {
                @Override
                public void run() {
                    if (MultiSelectVideoListActivity.this.isFinishing()) {
                        return;
                    }
                    Bitmap bitmap = VideoThumbnailUtil.getVideoThumbnail(video.getPath(), 100, 100, MediaStore.Images.Thumbnails.MICRO_KIND);
                    video.setThumbnailBitmap(bitmap);
                    updateListView();
                }
            });
        }
    }

    private void updateListView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mVideoAdapter.notifyDataSetChanged();
                mVideoCount++;
                Logger.e("mVideoCount: " + mVideoCount + "/" + mVideoList.size());
            }
        });
    }

    public class VideoListAdapter extends BaseAdapter {
        private Context myContext;

        public VideoListAdapter(Context myContext) {
            this.myContext = myContext;
        }


        @Override
        public int getCount() {

            return mVideoList.size();
        }

        @Override
        public Object getItem(int position) {

            return mVideoList.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(myContext, R.layout.item_video_listview, null);
                holder = new ViewHolder();
                holder.riv_video_pic = (ImageView) convertView.findViewById(R.id.riv_video_pic);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
                holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
                holder.cb_check = (CheckBox) convertView.findViewById(R.id.cb_check);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final Video video = mVideoList.get(position);
            Bitmap bitmap = video.getThumbnailBitmap();
            if (bitmap != null) {
                holder.riv_video_pic.setImageBitmap(bitmap);
            }
//            CheckBox cb = (CheckBox) convertView.findViewById(R.id.cb_check);
            holder.cb_check.setVisibility(View.VISIBLE);
            for (Video video1 : selectedVideoList) {
                if (video.getPath().equals(video1.getPath())) {
                    holder.cb_check.setChecked(true);
                    break;
                } else {
                    holder.cb_check.setChecked(false);
                }
            }
            holder.tv_title.setText(video.getDisplayName());
            holder.tv_size.setText(video.getSize() / 1024 / 1024 + "MB");
            String date = DateUtils.getFormatDate("yyyy-MM-dd hh:mm", video.getDate() * 1000);
            holder.tv_time.setText(date);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedVideoList.size() == 0 || holder.cb_check.isChecked()) {
                        isRefreshVideo = true;
                        if (holder.cb_check.isChecked()) {
                            holder.cb_check.setChecked(false);
                            int remove = -1;
                            for (int i = 0; i < selectedVideoList.size(); i++) {
                                if (selectedVideoList.get(i).getPath().equals(video.getPath())) {
                                    remove = i;
                                }
                            }
                            if (remove != -1) {
                                selectedVideoList.remove(remove);
                            }
                        } else {
                            holder.cb_check.setChecked(true);
                            if (!selectedVideoList.contains(video)) {
                                selectedVideoList.add(video);
                            }
                        }
                    } else {
                        Toast.makeText(mContext, "已达到最大可选数量", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        private ImageView riv_video_pic;
        private TextView tv_title;
        private TextView tv_size;
        private TextView tv_time;
        private CheckBox cb_check;
    }

}
