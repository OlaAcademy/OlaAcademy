package com.michen.olaxueyuan.ui.circle;

import android.os.Bundle;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.HomeListManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.AttendListResult;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.FocusListAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FocusedListActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener {

    @Bind(R.id.listview)
    PullToRefreshListView listview;
    private FocusListAdapter adapter;
    private String userId = "";
    private int type = 1;//1关注列表,2粉丝列表
    private List<AttendListResult.ResultBean> attendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_refresh_listview);
        userId = getIntent().getStringExtra("userId");
        type = getIntent().getIntExtra("type", 1);
        ButterKnife.bind(this);
        initView();
        switch (type) {
            case 1:
            default:
                setTitleText("关注");
                queryAttentionList();
                break;
            case 2:
                setTitleText("粉丝");
                queryFollowerList();
                break;
        }
    }

    private void initView() {
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(this);
        adapter = new FocusListAdapter(this, type);
        listview.setAdapter(adapter);
    }

    private void queryAttentionList() {
        SEAPP.showCatDialog(this);
        HomeListManager.getInstance().queryAttentionList(userId, new Callback<AttendListResult>() {
            @Override
            public void success(AttendListResult attendListResult, Response response) {
                if (mContext != null && !FocusedListActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    listview.onRefreshComplete();
                    if (attendListResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(mContext, attendListResult.getMessage());
                    } else {
                        adapter.updateData(attendListResult.getResult());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !FocusedListActivity.this.isFinishing()) {
                    listview.onRefreshComplete();
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    private void queryFollowerList() {
        String curUserId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            curUserId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        SEAPP.showCatDialog(this);
        HomeListManager.getInstance().queryFollowerList(userId, curUserId, new Callback<AttendListResult>() {
            @Override
            public void success(AttendListResult attendListResult, Response response) {
                if (mContext != null && !FocusedListActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    listview.onRefreshComplete();
                    if (attendListResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(mContext, attendListResult.getMessage());
                    } else {
                        attendList = attendListResult.getResult();
                        adapter.updateData(attendList);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !FocusedListActivity.this.isFinishing()) {
                    listview.onRefreshComplete();
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        switch (type) {
            case 1:
            default:
                queryAttentionList();
                break;
            case 2:
                queryFollowerList();
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SEAPP.dismissAllowingStateLoss();
    }

    public void attendUser(String attendId, String attendedId, final int type, final int position) {
        SEAPP.showCatDialog(this);
        HomeListManager.getInstance().attendUser(attendId, attendedId, type, new Callback<SimpleResult>() {
            @Override
            public void success(SimpleResult simpleResult, Response response) {
                if (mContext != null && !FocusedListActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    if (simpleResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(mContext, simpleResult.getMessage());
                    } else {
                        switch (type) {
                            case 1://关注
                                attendList.get(position).setIsAttend(1);
                                break;
                            case 2://取消关注
                                attendList.get(position).setIsAttend(0);
                                break;
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !FocusedListActivity.this.isFinishing()) {
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                }
            }
        });
    }
}
