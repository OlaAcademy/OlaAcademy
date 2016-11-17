package com.michen.olaxueyuan.ui.course.video;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.exception.DbException;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.manager.DialogUtils;
import com.michen.olaxueyuan.common.manager.Logger;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.download.DownloadManager;
import com.michen.olaxueyuan.download.DownloadService;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.SECourseManager;
import com.michen.olaxueyuan.protocol.result.GoodsOrderStatusResult;
import com.michen.olaxueyuan.protocol.result.SystemCourseResult;
import com.michen.olaxueyuan.protocol.result.SystemVideoResult;
import com.michen.olaxueyuan.sharesdk.ShareModel;
import com.michen.olaxueyuan.sharesdk.SharePopupWindow;
import com.michen.olaxueyuan.ui.adapter.SystemVideoListAdapter;
import com.michen.olaxueyuan.ui.course.PaySystemVideoActivity;
import com.michen.olaxueyuan.ui.course.SystemVideoActivity;
import com.michen.olaxueyuan.ui.me.activity.BaseFragment;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.UIHandler;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mingge on 2016/11/11.
 */

public class SystemCatalogFragment extends BaseFragment implements PlatformActionListener, Handler.Callback {
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.rmb)
    TextView rmb;
    @Bind(R.id.price)
    TextView price;
    @Bind(R.id.paynum)
    TextView paynum;
    @Bind(R.id.btn_buy)
    Button btnBuy;
    @Bind(R.id.bottom_view)
    RelativeLayout bottomView;
    @Bind(R.id.video_download_btn)
    ImageView videoDownloadBtn;
    @Bind(R.id.video_share_btn)
    ImageView videoShareBtn;
    @Bind(R.id.bottom_download_view)
    RelativeLayout bottomDownloadView;
    private SystemVideoListAdapter adapter;
    private String courseId;
    private String userId;
    private List<SystemVideoResult.ResultBean> videoArrayList;
    SystemCourseResult.ResultEntity resultEntity;
    public boolean hasBuyGoods = false;//true购买过改套视频，false没有购买过该套视频
    private DownloadManager downloadManager;
    private SharePopupWindow share;
    private int playPosition;//当前播放位置

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.catalog_system_video_fragment, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        courseId = getArguments().getString("courseId");
        resultEntity = (SystemCourseResult.ResultEntity) getArguments().getSerializable("resultEntity");
        adapter = new SystemVideoListAdapter(mContext);
        listview.setAdapter(adapter);
        initData();
        getOrderStatus();
        performRefresh();
    }

    private void initData() {
        price.setText(String.valueOf(resultEntity.getPrice()));
        paynum.setText(getString(R.string.num_buys, String.valueOf(resultEntity.getPaynum())));
        downloadManager = DownloadService.getDownloadManager(getActivity());
    }

    public void getOrderStatus() {
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        Logger.e("courseId==" + courseId);
        SECourseManager.getInstance().getOrderStatus(userId, courseId, new Callback<GoodsOrderStatusResult>() {
            @Override
            public void success(GoodsOrderStatusResult result, Response response) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    Logger.json(result);
                    if (result.getApicode() != 10000) {
//                    SVProgressHUD.showInViewWithoutIndicator(SystemVideoActivity.this, result.getMessage(), 2.0f);
                    } else {
                        if (result.getResult() == 1) {
                            hasBuyGoods = true;
                            bottomView.setVisibility(View.GONE);
                            bottomDownloadView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                }
            }
        });
    }

    public void performRefresh() {
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            startActivity(new Intent(mContext, UserLoginActivity.class));
            return;
        }
        Logger.e("courseId==" + courseId);
        SECourseManager.getInstance().getVideoList(courseId, userId, new Callback<SystemVideoResult>() {
            @Override
            public void success(SystemVideoResult result, Response response) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    if (result.getApicode() != 10000) {
                        SVProgressHUD.showInViewWithoutIndicator(getActivity(), result.getMessage(), 2.0f);
                    } else {
                        videoArrayList = result.getResult();
                        ((SystemVideoActivity) getActivity()).setVieoArrayList(videoArrayList);
                        if (videoArrayList != null && videoArrayList.size() > 0) {
                            videoArrayList.get(0).setSelected(true);
                            adapter = new SystemVideoListAdapter(getActivity());
                            listview.setAdapter(adapter);
                            adapter.updateData(videoArrayList);
                            initListViewItemClick();
                            ((SystemVideoActivity) getActivity()).playVideo(videoArrayList.get(0).getAddress());
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null && !getActivity().isFinishing()) {
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                }
            }
        });
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
                    playPosition = position;
                    if (!SEAuthManager.getInstance().isAuthenticated()) {
                        loginDialog();
                    } else {
                        if (hasBuyGoods) {
                            bottomView.setVisibility(View.GONE);
                            bottomDownloadView.setVisibility(View.VISIBLE);
                            ((SystemVideoActivity) getActivity()).playVideo(videoArrayList.get(position).getAddress());
                            ((SystemVideoActivity) getActivity()).pdfPosition = position;
                            adapter.updateData(videoArrayList);
                        } else {
                            bottomView.setVisibility(View.VISIBLE);
//                            bottomView.setVisibility(View.GONE);
                            bottomDownloadView.setVisibility(View.GONE);
                            jumpToPayOrder();
                        }
                    }
                }
            }
        });
    }

    private void loginDialog() {
        DialogUtils.showDialog(getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.yes:
                        startActivity(new Intent(getActivity(), UserLoginActivity.class));
                        break;
                }
            }
        }, "", getString(R.string.to_login), "", "");
    }

    private void jumpToPayOrder() {
        if (!SEAuthManager.getInstance().isAuthenticated()) {
            loginDialog();
        } else {
            if (resultEntity != null) {
                Intent intent = new Intent(getActivity(), PaySystemVideoActivity.class);
                intent.putExtra("courseId", courseId);
                intent.putExtra("ResultEntity", resultEntity);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.btn_buy, R.id.video_download_btn, R.id.video_share_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_buy:
                jumpToPayOrder();
                break;
            case R.id.video_download_btn:
                downloadCourse(videoArrayList.get(playPosition));
                break;
            case R.id.video_share_btn:
                shareCourse(videoArrayList.get(playPosition));
                break;
        }

    }

    private void downloadCourse(SystemVideoResult.ResultBean videoInfo) {
        String target = "/sdcard/OlaAcademy/" + videoInfo.getId() + ".mp4";
        try {
            downloadManager.addNewDownload(
                    videoInfo.getAddress(),
                    videoInfo.getName(),
                    videoInfo.getPic(),
                    target,
                    true, // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
                    false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
                    null);
        } catch (DbException e) {
            SVProgressHUD.showInViewWithoutIndicator(getActivity(), "添加下载失败", 2.0f);
        }
    }

    private void shareCourse(SystemVideoResult.ResultBean videoInfo) {
        share = new SharePopupWindow(getActivity());
        share.setPlatformActionListener(this);
        ShareModel model = new ShareModel();
        model.setImageUrl(SEConfig.getInstance().getAPIBaseURL() + "/ola/images/icon.png");
        model.setText(videoInfo.getName());
        model.setTitle("欧拉学院");
        model.setUrl("http://api.olaxueyuan.com/course.html?courseId=" + courseId);
        share.initShareParams(model);
        share.showShareWindow();
        // 显示窗口 (设置layout在PopupWindow中显示的位置)
        share.showAtLocation(view.findViewById(R.id.root),
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
            Toast.makeText(getActivity(), "分享失败", Toast.LENGTH_SHORT).show();
        }
        if (share != null) {
            share.dismiss();
        }
        return false;
    }

}
