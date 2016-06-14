package com.snail.olaxueyuan.ui.me;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.protocol.manager.SEUserManager;
import com.snail.olaxueyuan.protocol.result.UserBuyGoodsResult;
import com.snail.olaxueyuan.ui.SuperFragment;
import com.snail.olaxueyuan.ui.me.adapter.UserBuyGoodsAdapter;
import com.snail.pulltorefresh.PullToRefreshBase;
import com.snail.pulltorefresh.PullToRefreshListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by mingge on 2016/5/20.
 */
public class UserBuyGoodsFragment extends SuperFragment implements PullToRefreshBase.OnRefreshListener {
    View rootView;
    @Bind(R.id.listview)
    PullToRefreshListView listview;
    UserBuyGoodsAdapter adapter;
    UserBuyGoodsResult module;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_buy_course, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        fetchData();
        return rootView;
    }

    private void fetchData() {
        SEUserManager.getInstance().getBuyGoodsList("126", new Callback<UserBuyGoodsResult>() {
            @Override
            public void success(UserBuyGoodsResult userBuyGoodsResult, Response response) {
                listview.onRefreshComplete();
                if (userBuyGoodsResult.getApicode() != 10000) {
                    ToastUtil.showToastShort(getActivity(), userBuyGoodsResult.getMessage());
                } else {
                    module = userBuyGoodsResult;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    listview.onRefreshComplete();
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
                }
            }
        });
    }

    private void initView() {
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview.setOnRefreshListener(this);
        adapter = new UserBuyGoodsAdapter(getActivity());
        listview.setAdapter(adapter);
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

    @Override
    public void onClick(View v) {

    }


    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        fetchData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
