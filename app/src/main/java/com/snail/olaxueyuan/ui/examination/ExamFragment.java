package com.snail.olaxueyuan.ui.examination;


import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.Logger;
import com.snail.olaxueyuan.common.manager.TitleManager;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.snail.olaxueyuan.protocol.result.ExamModule;
import com.snail.olaxueyuan.ui.SuperFragment;
import com.snail.olaxueyuan.ui.manager.TitlePopManager;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFragment extends SuperFragment implements TitlePopManager.PidClickListener {
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.pop_line)
    View popLine;
    TitleManager titleManager;
    View view;
    private String courseId = "1";// 1 数学 2 英语 3 逻辑 4 协作
    ExamModule module;

    public ExamFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_exam, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        titleManager = new TitleManager("数学", this, view, false);
        Drawable drawable = getResources().getDrawable(R.drawable.title_down_nromal);
        drawable.setBounds(10, 0, drawable.getMinimumWidth() + 10, drawable.getMinimumHeight());
        titleManager.title_tv.setCompoundDrawables(null, null, drawable, null);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv:
                TitlePopManager.getInstance().showPop(getActivity(), titleManager, popLine, this, 2);
                break;
        }
    }

    private void fetchData() {
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        QuestionCourseManager.getInstance().getExamList(courseId, "1", new Callback<ExamModule>() {
            @Override
            public void success(ExamModule examModule, Response response) {
                SVProgressHUD.dismiss(getActivity());
                Logger.json(examModule);
                if (examModule.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(getActivity(), examModule.getMessage(), 2.0f);
                } else {
                    module = examModule;
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

    @Override
    public void pidPosition(int type, String pid) {
        if (type == 2) {
            this.courseId = pid;
            fetchData();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
