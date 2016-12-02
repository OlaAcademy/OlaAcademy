package com.michen.olaxueyuan.ui.course.commodity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.MCOrgManager;
import com.michen.olaxueyuan.protocol.result.SystemCourseResult;
import com.michen.olaxueyuan.ui.activity.SuperActivity;
import com.michen.olaxueyuan.ui.manager.TitlePopManager;
import com.michen.olaxueyuan.ui.me.adapter.SystemCourseAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CommodityActivity extends SuperActivity implements TitlePopManager.PidClickListener, PullToRefreshBase.OnRefreshListener {

    @Bind(R.id.left_return)
    TextView leftReturn;
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.pop_line)
    View popLine;
    TitleManager titleManager;
    @Bind(R.id.listview)
    PullToRefreshListView listview;
    private String pid = "1";// 1 视频 2 题库
    private SystemCourseAdapter adapter;
    private SystemCourseResult module;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        title = getIntent().getStringExtra("title");
        pid = getIntent().getStringExtra("type");
        if (TextUtils.isEmpty(pid)) {
            pid = "1";
        }
        if (!TextUtils.isEmpty(title)) {
            titleManager = new TitleManager(this, title, this, true);
        } else {
            titleManager = new TitleManager(this, R.string.data_base, this, true);
        }
//        Drawable drawable = getResources().getDrawable(R.drawable.title_down_nromal);
//        drawable.setBounds(10, 0, drawable.getMinimumWidth() + 10, drawable.getMinimumHeight());
//        titleManager.title_tv.setCompoundDrawables(null, null, drawable, null);

        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(this);
        adapter = new SystemCourseAdapter(this);
        listview.setAdapter(adapter);
    }

    @Override
    public void initData() {
        fectData();
    }

    private void fectData() {
//        SVProgressHUD.showInView(CommodityActivity.this, getString(R.string.request_running), true);
        SEAPP.showCatDialog(this);
        MCOrgManager.getInstance().getGoodsList(pid, new Callback<SystemCourseResult>() {
            @Override
            public void success(SystemCourseResult systemCourseResult, Response response) {
                if (!CommodityActivity.this.isFinishing()) {
                    listview.onRefreshComplete();
//                    SVProgressHUD.dismiss(CommodityActivity.this);
                    SEAPP.dismissAllowingStateLoss();
//                Logger.json(systemCourseResult);
                    if (systemCourseResult.getApicode() != 10000) {
                        ToastUtil.showToastShort(CommodityActivity.this, systemCourseResult.getMessage());
                    } else {
                        module = systemCourseResult;
                        handler.sendEmptyMessage(0);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (!CommodityActivity.this.isFinishing()) {
//                    SVProgressHUD.dismiss(CommodityActivity.this);
                    SEAPP.dismissAllowingStateLoss();
                    listview.onRefreshComplete();
                    ToastUtil.showToastShort(CommodityActivity.this, R.string.data_request_fail);
                }
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    initAdapter();
                    break;
            }
        }
    };

    private void initAdapter() {
        adapter.updateData(module);
    }

    @OnClick({R.id.left_return, R.id.title_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_return:
                finish();
                break;
            case R.id.title_tv:
//                TitlePopManager.getInstance().showPop(this, titleManager, popLine, this, 4);
                break;
        }
    }

    @Override
    public void pidPosition(int type, String pid) {
        if (type == 4) {
            this.pid = pid;
            fectData();
        }
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        fectData();
    }
}
