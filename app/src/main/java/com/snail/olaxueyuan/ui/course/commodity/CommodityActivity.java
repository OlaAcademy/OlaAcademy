package com.snail.olaxueyuan.ui.course.commodity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.TitleManager;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.protocol.manager.MCOrgManager;
import com.snail.olaxueyuan.protocol.result.SystemCourseResult;
import com.snail.olaxueyuan.ui.activity.SuperActivity;
import com.snail.olaxueyuan.ui.manager.TitlePopManager;
import com.snail.olaxueyuan.ui.me.adapter.SystemCourseAdapter;
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
    private String pid = "1";
    private SystemCourseAdapter adapter;
    private SystemCourseResult module;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);
        ButterKnife.bind(this);
    }

    @Override
    public void initView() {
        titleManager = new TitleManager(this, R.string.course, this, true);
        Drawable drawable = getResources().getDrawable(R.drawable.title_down_nromal);
        drawable.setBounds(10, 0, drawable.getMinimumWidth() + 10, drawable.getMinimumHeight());
        titleManager.title_tv.setCompoundDrawables(null, null, drawable, null);

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
        MCOrgManager.getInstance().getGoodsList(pid, new Callback<SystemCourseResult>() {
            @Override
            public void success(SystemCourseResult systemCourseResult, Response response) {
                listview.onRefreshComplete();
//                Logger.json(systemCourseResult);
                if (systemCourseResult.getApicode() != 10000) {
                    ToastUtil.showToastShort(CommodityActivity.this, systemCourseResult.getMessage());
                } else {
                    module = systemCourseResult;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (!CommodityActivity.this.isFinishing()) {
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
                TitlePopManager.getInstance().showPop(this, titleManager, popLine, this, 4);
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
