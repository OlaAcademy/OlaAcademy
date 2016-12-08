package com.michen.olaxueyuan.ui.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.DialogUtils;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.event.PublishHomeWorkSuccessEvent;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.manager.TeacherHomeManager;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.protocol.result.TeacherGroupListResult;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.adapter.TGetGroupListViewAdapter;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TSubjectDeployActivity extends SEBaseActivity implements PullToRefreshBase.OnRefreshListener, TGetGroupListViewAdapter.GetSelectedGroupId {

    @Bind(R.id.work_name)
    EditText workName;
    @Bind(R.id.listview_group)
    PullToRefreshListView listviewGroup;
    @Bind(R.id.publish_homework)
    Button publishHomework;
    private String subjectIds;//	题目Id串 逗号分隔
    private String groupIds;//群id	逗号分隔
    private Context context;
    private TGetGroupListViewAdapter adapter;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsubject_deploy);
        ButterKnife.bind(this);
        context = this;
        initView();
        subjectIds = getIntent().getStringExtra("subjectIds");
        getTeacherGroupList();
    }

    private void initView() {
        setTitleText("发布作业");
        adapter = new TGetGroupListViewAdapter(context, this);
        listviewGroup.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listviewGroup.setOnRefreshListener(this);
        listviewGroup.setAdapter(adapter);
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
                if (context != null && TSubjectDeployActivity.this.isFinishing()) {
                    listviewGroup.onRefreshComplete();
//                    SVProgressHUD.dismiss(context);
                    SEAPP.dismissAllowingStateLoss();
                    if (teacherGroupListResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(context, teacherGroupListResult.getMessage());
                    } else {
                        adapter.updateData(teacherGroupListResult.getResult());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (context != null) {
                    listviewGroup.onRefreshComplete();
//                    SVProgressHUD.dismiss(context);
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(context, R.string.data_request_fail);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (context != null) {
//            SVProgressHUD.dismiss(context);
            SEAPP.dismissAllowingStateLoss();
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        getTeacherGroupList();
    }

    List<String> groupIdList = new ArrayList<>();//选中群id

    @Override
    public void getGroupId(String id, boolean add) {
        if (add) {
            groupIdList.add(id);
        } else {
            if (groupIdList.contains(id)) {
                groupIdList.remove(id);
            }
        }
    }

    @OnClick({R.id.publish_homework})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.publish_homework:
                checkPublishHomeWork();
                break;
        }
    }

    private void checkPublishHomeWork() {
        title = workName.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            ToastUtil.showToastShort(context, "请填写作业名称");
            return;
        }
        if (groupIdList.size() == 0) {
            ToastUtil.showToastShort(context, "请选择要发布的群");
            return;
        }
        groupIds = "";
        for (int i = 0; i < groupIdList.size(); i++) {
            if (i == groupIdList.size() - 1) {
                groupIds += groupIdList.get(i);
            } else {
                groupIds += groupIdList.get(i) + ",";
            }
        }
        DialogUtils.showDialog(context, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.yes:
                        publishHomeWork();
                        break;
                }

            }
        }, "立即发布", "作业将立即发布给学生", "", "");
    }

    private void publishHomeWork() {
//        SVProgressHUD.showInView(context, getString(R.string.request_running), true);
        SEAPP.showCatDialog(this);
        TeacherHomeManager.getInstance().deployHomework(title, groupIds, subjectIds, new Callback<SimpleResult>() {
            @Override
            public void success(SimpleResult simpleResult, Response response) {
                if (context != null && !TSubjectDeployActivity.this.isFinishing()) {
//                    SVProgressHUD.dismiss(context);
                    SEAPP.dismissAllowingStateLoss();
                    if (simpleResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(context, simpleResult.getMessage());
                    } else {
                        ToastUtil.showToastShort(context, "发布成功");
                        EventBus.getDefault().post(new PublishHomeWorkSuccessEvent(true));
                        finish();
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (context != null && !TSubjectDeployActivity.this.isFinishing()) {
//                    SVProgressHUD.dismiss(context);
                    SEAPP.dismissAllowingStateLoss();
                    ToastUtil.showToastShort(context, R.string.data_request_fail);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
