package com.michen.olaxueyuan.ui.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.ui.SuperFragment;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by mingge on 2016/9/21.
 */

public class UserFragmentV2 extends SuperFragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        initView();
        return rootView;
    }

    private void initView() {
    }

    @Override
    public void onClick(View v) {

    }

    // EventBus 回调
    public void onEventMainThread(UserLoginNoticeModule module) {
        if (!module.isLogin) {
//            updateHeadView(null);
        } else {
//            fetchUserInfo();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
