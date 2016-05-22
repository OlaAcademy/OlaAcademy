package com.snail.olaxueyuan.ui.me;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.protocol.manager.SEUserManager;
import com.snail.olaxueyuan.protocol.result.UserKnowledgeResult;
import com.snail.olaxueyuan.ui.SuperFragment;
import com.snail.olaxueyuan.ui.adapter.UserKnowledgeAdapter;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by mingge on 2016/5/20.
 */
public class UserKnowledgeFragment extends SuperFragment {
    View rootView;
    @Bind(R.id.expandableListView)
    ExpandableListView expandableListView;

    private UserKnowledgeResult module;
    private UserKnowledgeAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_knowledge, container, false);
        ButterKnife.bind(this, rootView);
        fetchData();
        return rootView;
    }

    private void fetchData() {
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        SEUserManager.getInstance().getStatisticsList("1", new Callback<UserKnowledgeResult>() {
            @Override
            public void success(UserKnowledgeResult userKnowledgeResult, Response response) {
                SVProgressHUD.dismiss(getActivity());
//                Logger.json(userKnowledgeResult);
                if (userKnowledgeResult.getApicode() != 10000) {
                    ToastUtil.showToastShort(getActivity(), userKnowledgeResult.getMessage());
                } else {
                    module = userKnowledgeResult;
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (getActivity() != null) {
                    SVProgressHUD.dismiss(getActivity());
                    ToastUtil.showToastShort(getActivity(), R.string.data_request_fail);
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
        expandableListView.setDivider(null);
        expandableListView.setGroupIndicator(null);
        adapter = new UserKnowledgeAdapter(getActivity());
        adapter.updateList(module);
        expandableListView.setAdapter(adapter);
        for (int i = 0; i < module.getResult().size(); i++) {
            expandableListView.expandGroup(i);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
