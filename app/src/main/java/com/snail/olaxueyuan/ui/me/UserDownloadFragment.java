package com.snail.olaxueyuan.ui.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.ui.SuperFragment;

/**
 * Created by mingge on 2016/5/20.
 */
public class UserDownloadFragment extends SuperFragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_buy_course, container, false);
        initView();
        return rootView;
    }

    private void initView() {
    }

    @Override
    public void onClick(View v) {

    }
}
