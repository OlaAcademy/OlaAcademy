package com.michen.olaxueyuan.ui.index.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.SECallBack;
import com.michen.olaxueyuan.protocol.SEDataRetriever;
import com.michen.olaxueyuan.protocol.model.SEMsg;
import com.michen.olaxueyuan.protocol.result.ServiceError;
import com.michen.olaxueyuan.ui.activity.SEBaseActivity;
import com.michen.olaxueyuan.ui.index.adapter.MsgAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;

public class MsgActivity extends SEBaseActivity {

    private PullToRefreshListView msgListView;
    private MsgAdapter adapter;
    private SEDataRetriever dataRetriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_refresh_listview);
        setTitleText("消息中心");

        this.setDataRetriver(new SEDataRetriever() {
            @Override
            public void refresh(SECallBack callback) {
                if (adapter != null) {
                    adapter.refresh(callback);
                } else {
                    if (callback != null) {
                        callback.success();
                    }
                }
            }

            @Override
            public void loadMore(SECallBack callback) {
                if (callback != null) {
                    callback.success();
                }
            }
        });

        msgListView = (PullToRefreshListView) findViewById(R.id.listview);
        msgListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                performRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                performLoadMore();
            }
        });
        adapter = new MsgAdapter(this);
        msgListView.setAdapter(adapter);
        adapter.refresh(null);

        msgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SEMsg msg = (SEMsg) adapter.getItem(position - 1);
            }
        });
    }

    private void performRefresh() {
        if (dataRetriver != null) {
            dataRetriver.refresh(new SECallBack() {
                @Override
                public void success() {
                    stopRefreshAnimation();
                }

                @Override
                public void failure(ServiceError error) {
                    Toast.makeText(MsgActivity.this, error.getMessageWithPrompt("刷新失败"), Toast.LENGTH_SHORT).show();
                    stopRefreshAnimation();
                }
            });
        }
    }

    private void performLoadMore() {
        if (dataRetriver != null) {
            dataRetriver.loadMore(new SECallBack() {
                @Override
                public void success() {
                    stopRefreshAnimation();
                }

                @Override
                public void failure(ServiceError error) {
                    Toast.makeText(MsgActivity.this, error.getMessageWithPrompt("无法加载更多"), Toast.LENGTH_SHORT).show();
                    stopRefreshAnimation();
                }
            });
        }
    }

    public void setDataRetriver(SEDataRetriever dataRetriver) {
        this.dataRetriver = dataRetriver;
    }

    private void startRefreshAnimation() {
        if (msgListView != null) {
            msgListView.setRefreshing(true);
        }
    }

    private void stopRefreshAnimation() {
        if (msgListView != null) {
            msgListView.onRefreshComplete();
        }
    }

}