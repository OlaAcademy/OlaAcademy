package com.michen.olaxueyuan.ui.question;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.ui.SuperFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by mingge on 16/8/9.
 */
public class QuestionHomeWorkFragment extends SuperFragment {
    View view;
    @Bind(R.id.text)
    TextView text;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_question_homework_layout, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    int type = 1;

    private void initView() {
        type = getArguments().getInt("type");
        text.setText(String.valueOf(type));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {

    }
}
