package com.michen.olaxueyuan.ui.course.video;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.me.activity.BaseFragment;

/**
 * Created by mingge on 2016/9/19.
 */
public class HandOutVideoFragment extends BaseFragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.handout_video_fragment, null);
        return view;
    }
}
