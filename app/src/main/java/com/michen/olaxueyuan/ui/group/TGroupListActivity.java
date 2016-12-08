package com.michen.olaxueyuan.ui.group;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.common.manager.Utils;
import com.michen.olaxueyuan.protocol.event.PublishHomeWorkSuccessEvent;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.TeacherHomeManager;
import com.michen.olaxueyuan.protocol.result.TeacherGroupListResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.group.data.TGroupListAdapter;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.michen.olaxueyuan.ui.teacher.TSubjectDeployActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TGroupListActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener {

    @Bind(R.id.listview)
    PullToRefreshListView listview;
    @Bind(R.id.create_group)
    Button createGroup;
    @Bind(R.id.empty_layout)
    LinearLayout emptyLayout;
    private Context context;
    private TGroupListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tgroup_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        context = this;
        initView();
    }

    private void initView() {
        setTitleText("群列表");
        setRightImage(R.drawable.create_group_icon);
        setRightImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.jumpLoginOrNot(context, CreateGroupActivity.class);
            }
        });
        listview.setOnRefreshListener(this);
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        adapter = new TGroupListAdapter(context);
        listview.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTeacherGroupList();
    }

    private void getTeacherGroupList() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        } else {
            startActivity(new Intent(context, UserLoginActivity.class));
            return;
        }
//        SVProgressHUD.showInView(context, getString(R.string.request_running), true);
        SEAPP.showCatDialog(this);
        TeacherHomeManager.getInstance().getTeacherGroupList(userId, new Callback<TeacherGroupListResult>() {
            @Override
            public void success(TeacherGroupListResult teacherGroupListResult, Response response) {
                if (context != null&&!TGroupListActivity.this.isFinishing()) {
                    listview.onRefreshComplete();
//                    SVProgressHUD.dismiss(context);
                    SEAPP.dismissAllowingStateLoss();
                    if (teacherGroupListResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(context, teacherGroupListResult.getMessage());
                        emptyLayout.setVisibility(View.VISIBLE);
                    } else {
                        if (teacherGroupListResult.getResult().size() > 0) {
                            emptyLayout.setVisibility(View.GONE);
                            adapter.updateData(teacherGroupListResult);
                        } else {
                            emptyLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (context != null&&!TGroupListActivity.this.isFinishing()) {
                    emptyLayout.setVisibility(View.VISIBLE);
//                    SVProgressHUD.dismiss(context);
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(context, R.string.request_group_fail);
                    listview.onRefreshComplete();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (context != null&&!TGroupListActivity.this.isFinishing()) {
            SVProgressHUD.dismiss(context);
            SEAPP.dismissAllowingStateLoss();
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        getTeacherGroupList();
    }

    @OnClick({R.id.create_group})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.create_group:
                Utils.jumpLoginOrNot(context, CreateGroupActivity.class);
                break;
        }
    }

    /**
     * {@link TSubjectDeployActivity#publishHomeWork()}
     * {@link CreateGroupActivity#saveGroupInfo(String, String,String)}
     *
     * @param successEvent
     */
    public void onEventMainThread(PublishHomeWorkSuccessEvent successEvent) {
        if (successEvent.isSuccess) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
