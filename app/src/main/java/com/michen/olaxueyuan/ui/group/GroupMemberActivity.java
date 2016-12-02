package com.michen.olaxueyuan.ui.group;

import android.os.Bundle;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.TeacherHomeManager;
import com.michen.olaxueyuan.protocol.result.GroupMemberResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.GroupMemberListAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GroupMemberActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener2 {
    @Bind(R.id.listview)
    PullToRefreshListView listview;
    private int PAGE_INDEX = 1;
    private final int PAGE_SIZE = 20;
    private String groupId;
    private GroupMemberListAdapter adapter;
    private List<GroupMemberResult.ResultBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_member);
        ButterKnife.bind(this);
        initView();
        getGroupMember();
    }

    private void initView() {
        setTitleText("群成员");
        groupId = getIntent().getStringExtra("groupId");
        adapter = new GroupMemberListAdapter(mContext);
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        listview.setOnRefreshListener(this);
        listview.setAdapter(adapter);
    }

    private void getGroupMember() {
//        SVProgressHUD.showInView(mContext, getString(R.string.request_running), true);
        SEAPP.showCatDialog(this);
        TeacherHomeManager.getInstance().queryGroupMember(groupId, PAGE_INDEX, PAGE_SIZE, new Callback<GroupMemberResult>() {
            @Override
            public void success(GroupMemberResult groupMemberResult, Response response) {
                if (mContext != null && !GroupMemberActivity.this.isFinishing()) {
                    listview.onRefreshComplete();
//                    SVProgressHUD.dismiss(mContext);
                    SEAPP.dismissAllowingStateLoss();
                    if (groupMemberResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(mContext, groupMemberResult.getMessage());
                    } else {
                        if (groupMemberResult.getResult().size() == 0) {
                            ToastUtil.showToastShort(mContext, R.string.to_end);
                            return;
                        }
                        list.addAll(groupMemberResult.getResult());
                        adapter.updateData(list);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !GroupMemberActivity.this.isFinishing()) {
                    listview.onRefreshComplete();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
//                    SVProgressHUD.dismiss(mContext);
                    SEAPP.dismissAllowingStateLoss();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mContext != null && !GroupMemberActivity.this.isFinishing()) {
            listview.onRefreshComplete();
            SVProgressHUD.dismiss(mContext);
            SEAPP.dismissAllowingStateLoss();
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        PAGE_INDEX = 1;
        list.clear();
        getGroupMember();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        PAGE_INDEX++;
        getGroupMember();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
