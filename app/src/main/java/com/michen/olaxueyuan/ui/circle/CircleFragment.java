package com.michen.olaxueyuan.ui.circle;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.eventbusmodule.CirclePraiseEvent;
import com.michen.olaxueyuan.protocol.manager.MCCircleManager;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.OLaCircleModule;
import com.michen.olaxueyuan.protocol.result.PraiseCirclePostResult;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.adapter.CircleAdapter;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CircleFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener2 {
    List<OLaCircleModule.ResultBean> list = new ArrayList<>();
    TitleManager titleManager;
    View rootView;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.listview)
    PullToRefreshListView listview;

    CircleAdapter adapter;

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
        fetchData("", "10");
        return rootView;
    }

    private void initView() {
        titleManager = new TitleManager(R.string.ola_circle, this, rootView, false);
        titleManager.changeImageRes(TitleManager.RIGHT_INDEX_RESPONSE, R.drawable.ic_circle_add);
        adapter = new CircleAdapter(getActivity());
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(this);
    }

    private void fetchData(final String circleId, String pageSize) {
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        QuestionCourseManager.getInstance().getCircleList(circleId, pageSize, new Callback<OLaCircleModule>() {
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

    @OnClick({R.id.right_response})
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
        }
    }

    // EventBus 回调
    public void onEventMainThread(Boolean addSuccess) {
        if (addSuccess) {
            fetchData("", "10");
        }
    }

    /**
     * 点赞
     * {@link com.michen.olaxueyuan.ui.adapter.CircleAdapter.ViewHolder#commentPraise}
     *
     * @param circlePraiseEvent
     */
    public void onEventMainThread(CirclePraiseEvent circlePraiseEvent) {
        switch (circlePraiseEvent.type) {
            case 1:
                praise(circlePraiseEvent.position);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        fetchData("", "20");
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        String circleId = "";
        if (list.size() > 0) {
        }
        circleId = list.get(list.size() - 1).getCircleId() + "";
        fetchData(circleId, "20");
    }

}
