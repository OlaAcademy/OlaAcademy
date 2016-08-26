package com.michen.olaxueyuan.ui.circle;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.eventbusmodule.CircleClickEvent;
import com.michen.olaxueyuan.protocol.manager.MCCircleManager;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.OLaCircleModule;
import com.michen.olaxueyuan.protocol.result.PraiseCirclePostResult;
import com.michen.olaxueyuan.sharesdk.ShareModel;
import com.michen.olaxueyuan.sharesdk.SharePopupWindow;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.adapter.CircleAdapter;
import com.michen.olaxueyuan.ui.home.data.ChangeIndexEvent;
import com.michen.olaxueyuan.ui.manager.CirclePopManager;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.utils.UIHandler;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CircleFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener2, PlatformActionListener, Handler.Callback, CirclePopManager.CircleClickListener {
    List<OLaCircleModule.ResultBean> list = new ArrayList<>();
    TitleManager titleManager;
    View rootView;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.listview)
    PullToRefreshListView listview;
    @Bind(R.id.all_search_view)
    LinearLayout allSearchView;
    @Bind(R.id.pop_line)
    View popLine;
    private String type = "";//1 学习记录 2 帖子 "" 全部
    private static final String PAGE_SIZE = "20";//每次加载20条

    CircleAdapter adapter;

    private SharePopupWindow share;

    public CircleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_circle, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        initView();
        fetchData("", PAGE_SIZE);
        return rootView;
    }

    private void initView() {
        titleManager = new TitleManager(R.string.ola_circle, this, rootView, false);
        titleManager.changeImageRes(TitleManager.RIGHT_INDEX_RESPONSE, R.drawable.ic_circle_add);
        Drawable drawable = getResources().getDrawable(R.drawable.title_down_nromal);
        drawable.setBounds(10, 0, drawable.getMinimumWidth() + 10, drawable.getMinimumHeight());
        titleManager.title_tv.setCompoundDrawables(null, null, drawable, null);
        adapter = new CircleAdapter(getActivity());
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(this);
    }

    private void fetchData(final String circleId, String pageSize) {
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        QuestionCourseManager.getInstance().getCircleList(circleId, pageSize, type, new Callback<OLaCircleModule>() {
            @Override
            public void success(OLaCircleModule oLaCircleModule, Response response) {
                SVProgressHUD.dismiss(getActivity());
                listview.onRefreshComplete();
//                Logger.json(oLaCircleModule);
                if (oLaCircleModule.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(getActivity(), oLaCircleModule.getMessage(), 2.0f);
                } else {
//                    Logger.json(oLaCircleModule);
                    if (circleId.equals("")) {
                        list.clear();
                        listview.setAdapter(adapter);
                    }
                    list.addAll(oLaCircleModule.getResult());
                    adapter.updateData(list);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    listview.onRefreshComplete();
                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                }
            }
        });
    }

    @OnClick({R.id.right_response, R.id.title_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_response:
                if (!SEAuthManager.getInstance().isAuthenticated()) {
                    Intent loginIntent = new Intent(getActivity(), UserLoginActivity.class);
                    startActivity(loginIntent);
                    return;
                }
                Intent intent = new Intent(getActivity(), DeployPostActivity.class);
                startActivity(intent);
                break;
            case R.id.title_tv:
                CirclePopManager.getInstance().showMarkPop(getActivity(), popLine, this, allSearchView);
                break;
        }
    }

    // EventBus 回调
    public void onEventMainThread(Boolean addSuccess) {
        if (addSuccess) {
            fetchData("", PAGE_SIZE);
        }
    }

    /**
     * {@link com.michen.olaxueyuan.ui.home.HomeFragment#chageIndex(int)}
     */
    public void onEventMainThread(ChangeIndexEvent changeIndexEvent) {
        if (changeIndexEvent.isChange) {
            this.type = "2";
            titleTv.setText(getActivity().getResources().getStringArray(R.array.circle_select)[2]);
            fetchData("", PAGE_SIZE);
        }
    }

    /**
     * 点赞
     * {@link CircleAdapter.ViewHolder#commentPraise}
     *
     * @param circlePraiseEvent
     */
    public void onEventMainThread(CircleClickEvent circlePraiseEvent) {
        switch (circlePraiseEvent.type) {
            case 1:
                praise(circlePraiseEvent.position);
                break;
            case 2:
                share(circlePraiseEvent.position);
                break;
            default:
                break;
        }
    }

    private void praise(final int position) {
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        MCCircleManager.getInstance().praiseCirclePost(String.valueOf(list.get(position).getCircleId()), new Callback<PraiseCirclePostResult>() {
            @Override
            public void success(PraiseCirclePostResult mcCommonResult, Response response) {
                SVProgressHUD.dismiss(getActivity());
                if (mcCommonResult.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(getActivity(), mcCommonResult.getMessage(), 2.0f);
                } else {
                    list.get(position).setPraiseNumber(list.get(position).getPraiseNumber() + 1);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                SVProgressHUD.dismiss(getActivity());
                ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
            }
        });
    }

    private void share(int position) {
        OLaCircleModule.ResultBean circle = list.get(position);
        share = new SharePopupWindow(getActivity());
        share.setPlatformActionListener(this);
        ShareModel model = new ShareModel();
        if (circle.getUserAvatar().indexOf("jpg") != -1) {
            model.setImageUrl("http://api.olaxueyuan.com/upload/" + circle.getUserAvatar());
        } else {
            model.setImageUrl(SEAPP.PIC_BASE_URL + circle.getUserAvatar());
        }
        model.setText(circle.getContent());
        model.setTitle("欧拉学院");
        model.setUrl(SEConfig.getInstance().getAPIBaseURL() + "/circlepost.html?circleId=" + circle.getCircleId());
        share.initShareParams(model);
        share.showShareWindow();
        // 显示窗口 (设置layout在PopupWindow中显示的位置)
        share.showAtLocation(getActivity().findViewById(R.id.main_circle),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        fetchData("", PAGE_SIZE);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        String circleId = "";
        if (list.size() > 0) {
        }
        circleId = list.get(list.size() - 1).getCircleId() + "";
        fetchData(circleId, PAGE_SIZE);
    }

    /////////////////////// 分享相关  //////////////////////

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
    public void onCancel(Platform platform, int arg1) {
        Message msg = new Message();
        msg.what = 0;
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

    @Override
    public void circlePosition(int type, String text) {
        titleTv.setText(text);
        switch (type) {
            case 0:
                this.type = "";
                break;
            default:
                this.type = String.valueOf(type);
                break;
        }
        fetchData("", PAGE_SIZE);
    }
}
