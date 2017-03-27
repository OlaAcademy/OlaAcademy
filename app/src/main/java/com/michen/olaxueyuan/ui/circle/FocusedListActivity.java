package com.michen.olaxueyuan.ui.circle;

import android.os.Bundle;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.HomeListManager;
import com.michen.olaxueyuan.protocol.manager.SEUserManager;
import com.michen.olaxueyuan.protocol.result.AttendListResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.FocusListAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;

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
        adapter = new FocusListAdapter(this);
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
        SEAPP.showCatDialog(this);
        HomeListManager.getInstance().queryFollowerList(userId, new Callback<AttendListResult>() {
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
}
