package com.michen.olaxueyuan.ui.me.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.download.DownloadInfo;
import com.michen.olaxueyuan.download.DownloadManager;
import com.michen.olaxueyuan.download.DownloadService;
import com.michen.olaxueyuan.protocol.event.DownloadSuccessEvent;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class DownloadListActivity extends SEBaseActivity {

    @Bind(R.id.downloaded_text)
    TextView downloadedText;
    @Bind(R.id.downloaded_indicator)
    View downloadedIndicator;
    @Bind(R.id.downloaded_layout)
    RelativeLayout downloadedLayout;
    @Bind(R.id.downloading_text)
    TextView downloadingText;
    @Bind(R.id.downloading_indicator)
    View downloadingIndicator;
    @Bind(R.id.downloading_layout)
    RelativeLayout downloadingLayout;
    @ViewInject(R.id.download_list)
    private SwipeMenuListView downloadList;

    private DownloadManager downloadManager;
    private DownloadListAdapter downloadListAdapter;

    private Context mAppContext;
    private boolean currentDowloaded = true;//true显示的为已下载界面， false显示为正在下载界面

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_list);
        ButterKnife.bind(this);
        ViewUtils.inject(this);
        EventBus.getDefault().register(this);
        setTitleText("我的下载");

        mAppContext = this.getApplicationContext();

        downloadManager = DownloadService.getDownloadManager(mAppContext);

        downloadListAdapter = new DownloadListAdapter(mAppContext);
        downloadListAdapter.updateData(downloadManager.getDownloadedList());
        downloadList.setAdapter(downloadListAdapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(160);
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        downloadList.setMenuCreator(creator);
        downloadList.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        downloadList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        try {
                            if (currentDowloaded) {
                                downloadManager.removeDownload(downloadManager.getDownloadedList().get(position));
                                downloadListAdapter.updateData(downloadManager.getDownloadedList());
                            } else {
                                downloadManager.removeDownload(downloadManager.getDownloadingList().get(position));
                                downloadListAdapter.updateData(downloadManager.getDownloadingList());
                            }
                        } catch (DbException e) {
                            LogUtils.e(e.getMessage(), e);
                        }
                        break;
                }
                return false;
            }
        });
        downloadList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DownloadInfo info;
                if (currentDowloaded) {
                    info = downloadManager.getDownloadedList().get(position);
                } else {
                    info = downloadManager.getDownloadingList().get(position);
                }
                if (info.getState() == HttpHandler.State.SUCCESS) {
                    Intent intent = new Intent(DownloadListActivity.this, VideoPlayActivity.class);
                    intent.putExtra("videoPath", info.getFileSavePath());
                    startActivity(intent);
                }
            }
        });
        changeTab(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        downloadListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        try {
            if (downloadListAdapter != null && downloadManager != null) {
                downloadManager.backupDownloadInfoList();
            }
        } catch (DbException e) {
            LogUtils.e(e.getMessage(), e);
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @butterknife.OnClick({R.id.downloaded_layout, R.id.downloading_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.downloaded_layout:
                downloadListAdapter.updateData(downloadManager.getDownloadedList());
                changeTab(true);
                break;
            case R.id.downloading_layout:
                downloadListAdapter.updateData(downloadManager.getDownloadingList());
                changeTab(false);
                break;
        }
    }

    private void changeTab(boolean downloaded) {
        downloadedText.setSelected(downloaded);
        downloadedIndicator.setSelected(downloaded);
        downloadingText.setSelected(!downloaded);
        downloadingIndicator.setSelected(!downloaded);
        currentDowloaded = downloaded;
    }

    public void onEventMainThread(DownloadSuccessEvent downloadSuccess) {
        if (downloadSuccess.isSuccess) {
            if (currentDowloaded) {
                downloadListAdapter.updateData(downloadManager.getDownloadedList());
            } else {
                downloadListAdapter.updateData(downloadManager.getDownloadingList());
            }
        }
    }

    private class DownloadListAdapter extends BaseAdapter {

        private final Context mContext;
        private final LayoutInflater mInflater;
        List<DownloadInfo> downloadedList = new ArrayList<>();

        private DownloadListAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }

        public void updateData(List<DownloadInfo> downloadedList) {
            this.downloadedList = downloadedList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
//            if (downloadManager == null) return 0;
//            return downloadManager.getDownloadInfoListCount();
            return downloadedList.size();
        }

        @Override
        public Object getItem(int i) {
//            return downloadManager.getDownloadInfo(i);
            return downloadedList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @SuppressWarnings("unchecked")
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            DownloadItemViewHolder holder = null;
//            DownloadInfo downloadInfo = downloadManager.getDownloadInfo(i);
            DownloadInfo downloadInfo = downloadedList.get(i);
            if (view == null) {
                view = mInflater.inflate(R.layout.download_item, null);
                holder = new DownloadItemViewHolder(downloadInfo);
                ViewUtils.inject(holder, view);
                view.setTag(holder);
                holder.refresh();
            } else {
                holder = (DownloadItemViewHolder) view.getTag();
                holder.update(downloadInfo);
            }

            HttpHandler<File> handler = downloadInfo.getHandler();
            if (handler != null) {
                RequestCallBack callBack = handler.getRequestCallBack();
                if (callBack instanceof DownloadManager.ManagerCallBack) {
                    DownloadManager.ManagerCallBack managerCallBack = (DownloadManager.ManagerCallBack) callBack;
                    if (managerCallBack.getBaseCallBack() == null) {
                        managerCallBack.setBaseCallBack(new DownloadRequestCallBack());
                    }
                }
                callBack.setUserTag(new WeakReference<DownloadItemViewHolder>(holder));
            }

            return view;
        }
    }

    public class DownloadItemViewHolder {
        @ViewInject(R.id.download_image)
        ImageView image;
        @ViewInject(R.id.download_label)
        TextView label;
        @ViewInject(R.id.download_time)
        TextView timeTV;
        @ViewInject(R.id.download_state)
        TextView state;
        @ViewInject(R.id.download_pb)
        ProgressBar progressBar;
        @ViewInject(R.id.download_stop_btn)
        Button stopBtn;

        private DownloadInfo downloadInfo;

        public DownloadItemViewHolder(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
        }

        @OnClick(R.id.download_stop_btn)
        public void stop(View view) {
            HttpHandler.State state = downloadInfo.getState();
            switch (state) {
                case WAITING:
                case STARTED:
                case LOADING:
                    try {
                        downloadManager.stopDownload(downloadInfo);
                    } catch (DbException e) {
                        LogUtils.e(e.getMessage(), e);
                    }
                    break;
                case CANCELLED:
                case FAILURE:
                    try {
                        downloadManager.resumeDownload(downloadInfo, new DownloadRequestCallBack());
                    } catch (DbException e) {
                        LogUtils.e(e.getMessage(), e);
                    }
                    downloadListAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }

        public void update(DownloadInfo downloadInfo) {
            this.downloadInfo = downloadInfo;
            refresh();
        }

        public void refresh() {
            label.setText(downloadInfo.getFileName());
            if (downloadInfo.getFileImage() != null) {
                try {
                    Picasso.with(mAppContext).load(downloadInfo.getFileImage()).config(Bitmap.Config.RGB_565)
                            .placeholder(R.drawable.ic_default_video).into(image);
                } catch (Exception e) {
                    e.printStackTrace();
                    image.setImageResource(R.drawable.ic_default_video);
                }
            } else {
                image.setBackgroundResource(R.drawable.ic_default_video);
            }

            state.setText(downloadInfo.getState().toString());
            if (downloadInfo.getFileLength() > 0) {
                progressBar.setProgress((int) (downloadInfo.getProgress() * 100 / downloadInfo.getFileLength()));
            } else {
                progressBar.setProgress(0);
            }

            stopBtn.setVisibility(View.VISIBLE);
            stopBtn.setText(mAppContext.getString(R.string.stop));
            HttpHandler.State state = downloadInfo.getState();
            switch (state) {
                case WAITING:
                    stopBtn.setText(mAppContext.getString(R.string.stop));
                    progressBar.setVisibility(View.VISIBLE);
                    timeTV.setVisibility(View.GONE);
                    break;
                case STARTED:
                    stopBtn.setText(mAppContext.getString(R.string.stop));
                    progressBar.setVisibility(View.VISIBLE);
                    timeTV.setVisibility(View.GONE);
                    break;
                case LOADING:
                    stopBtn.setText(mAppContext.getString(R.string.stop));
                    progressBar.setVisibility(View.VISIBLE);
                    timeTV.setVisibility(View.GONE);
                    break;
                case CANCELLED:
                    stopBtn.setText(mAppContext.getString(R.string.resume));
                    progressBar.setVisibility(View.VISIBLE);
                    timeTV.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    stopBtn.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.GONE);
                    timeTV.setVisibility(View.VISIBLE);
                    timeTV.setText(downloadInfo.getFileLength() / 1024 / 1024 + "MB");
                    break;
                case FAILURE:
                    stopBtn.setText(mAppContext.getString(R.string.retry));
                    break;
                default:
                    break;
            }
        }
    }

    private class DownloadRequestCallBack extends RequestCallBack<File> {

        @SuppressWarnings("unchecked")
        private void refreshListItem() {
            if (userTag == null) return;
            WeakReference<DownloadItemViewHolder> tag = (WeakReference<DownloadItemViewHolder>) userTag;
            DownloadItemViewHolder holder = tag.get();
            if (holder != null) {
                holder.refresh();
            }
        }

        @Override
        public void onStart() {
            refreshListItem();
        }

        @Override
        public void onLoading(long total, long current, boolean isUploading) {
            refreshListItem();
        }

        @Override
        public void onSuccess(ResponseInfo<File> responseInfo) {
            refreshListItem();
        }

        @Override
        public void onFailure(HttpException error, String msg) {
            refreshListItem();
        }

        @Override
        public void onCancelled() {
            refreshListItem();
        }
    }

}
