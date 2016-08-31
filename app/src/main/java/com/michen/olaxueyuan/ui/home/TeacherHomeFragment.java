package com.michen.olaxueyuan.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.SuperFragment;

/**
 * Created by mingge on 2016/8/31.
 */
public class TeacherHomeFragment extends SuperFragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher_home, container, false);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
