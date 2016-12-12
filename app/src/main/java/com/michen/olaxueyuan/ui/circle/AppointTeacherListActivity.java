package com.michen.olaxueyuan.ui.circle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.MCCircleManager;
import com.michen.olaxueyuan.protocol.result.AppointTeacherListResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.AppointTeacherListAdapter;
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

/**
 * 邀请老师回答列表
 */
public class AppointTeacherListActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener {

    @Bind(R.id.listview)
    PullToRefreshListView listview;
    AppointTeacherListAdapter adapter;
    List<AppointTeacherListResult.ResultBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appoint_teacher_list);
        ButterKnife.bind(this);
        initView();
        getTeacherList();
        initData();
    }

    private void initView() {
        setTitleText("老师列表");
        adapter = new AppointTeacherListAdapter(this);
        listview.getRefreshableView().setDivider(null);
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(this);
        listview.setAdapter(adapter);
    }

    private void getTeacherList() {
        SEAPP.showCatDialog(this);
        MCCircleManager.getInstance().getTeacherList(new Callback<AppointTeacherListResult>() {
            @Override
            public void success(AppointTeacherListResult result, Response response) {
                if (mContext != null && !AppointTeacherListActivity.this.isFinishing()) {
                    listview.onRefreshComplete();
                    SEAPP.dismissAllowingStateLoss();
                    if (result.getApicode() != 10000) {
                        ToastUtil.showToastShort(mContext, result.getMessage());
                    } else {
                        if (result.getResult().size() == 0) {
                            ToastUtil.showToastShort(mContext, "没找到老师");
                            return;
                        }
                        list.clear();
                        list.addAll(result.getResult());
                        adapter.updateData(list);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (mContext != null && !AppointTeacherListActivity.this.isFinishing()) {
                    listview.onRefreshComplete();
                    ToastUtil.showToastShort(mContext, R.string.data_request_fail);
                    SEAPP.dismissAllowingStateLoss();
                }
            }
        });
    }

    private void initData() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    Intent intent = getIntent();
                    intent.putExtra("id", list.get(position - 1).getId());
                    intent.putExtra("name", list.get(position - 1).getName());
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        getTeacherList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mContext != null && !AppointTeacherListActivity.this.isFinishing()) {
            listview.onRefreshComplete();
            SVProgressHUD.dismiss(mContext);
            SEAPP.dismissAllowingStateLoss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
