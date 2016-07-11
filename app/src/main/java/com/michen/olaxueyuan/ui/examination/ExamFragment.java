package com.michen.olaxueyuan.ui.examination;


import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.protocol.result.UserLoginNoticeModule;
import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.HorizontalScrollViewAdapter;
import com.michen.olaxueyuan.common.MyHorizontalScrollView;
import com.michen.olaxueyuan.common.manager.TitleManager;
import com.michen.olaxueyuan.common.manager.ToastUtil;
import com.michen.olaxueyuan.protocol.manager.QuestionCourseManager;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.ExamModule;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.manager.TitleExamPopManager;
import com.snail.svprogresshud.SVProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFragment extends SuperFragment implements TitleExamPopManager.ExamPidClickListener {
    @Bind(R.id.title_tv)
    TextView titleTv;
    @Bind(R.id.right_response)
    ImageView rightResponse;
    @Bind(R.id.pop_line)
    View popLine;
    TitleManager titleManager;
    View view;
    @Bind(R.id.target_score)
    TextView targetScore;
    @Bind(R.id.all_rank)
    TextView allRank;
    @Bind(R.id.coverage_exam)
    TextView coverageExam;
    @Bind(R.id.id_gallery)
    LinearLayout idGallery;
    @Bind(R.id.id_horizontalScrollView)
    MyHorizontalScrollView mHorizontalScrollView;
    private String courseId = "1";// 1 数学 2 英语 3 逻辑 4 协作
    private String courseType = "1";//1 模考 2 真题

    ExamModule module;
    private HorizontalScrollViewAdapter mAdapter;

    public ExamFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_exam, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }

    private void initView() {
        titleManager = new TitleManager("数学", this, view, false);
        Drawable drawable = getResources().getDrawable(R.drawable.title_down_nromal);
        drawable.setBounds(10, 0, drawable.getMinimumWidth() + 10, drawable.getMinimumHeight());
        titleManager.title_tv.setCompoundDrawables(null, null, drawable, null);
        fetchData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_tv:
                TitleExamPopManager.getInstance().showPop(getActivity(), titleManager, popLine, this, 2);
                break;
        }
    }

    // EventBus 回调
    public void onEventMainThread(UserLoginNoticeModule module) {
        fetchData();
    }

    private void fetchData() {
        SVProgressHUD.showInView(getActivity(), getString(R.string.request_running), true);
        String userId = "";
        SEAuthManager am = SEAuthManager.getInstance();
        if (am.isAuthenticated()) {
            userId = am.getAccessUser().getId();
        }
        QuestionCourseManager.getInstance().getExamList(userId, courseId, courseType, new Callback<ExamModule>() {
            @Override
            public void success(ExamModule examModule, Response response) {
                SVProgressHUD.dismiss(getActivity());
//                Logger.json(examModule);
                if (examModule.getApicode() != 10000) {
                    SVProgressHUD.showInViewWithoutIndicator(getActivity(), examModule.getMessage(), 2.0f);
                } else {
                    module = examModule;
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
        if (module != null && module.getResult() != null && module.getResult().size() > 0) {
            mAdapter = new HorizontalScrollViewAdapter(getActivity(), Integer.parseInt(courseId),module.getResult());
            //添加滚动回调
            mHorizontalScrollView
                    .setCurrentImageChangeListener(new MyHorizontalScrollView.CurrentImageChangeListener() {
                        @Override
                        public void onCurrentImgChanged(int position, View viewIndicator) {
                            setView(viewIndicator, position);
                        }
                    });
            //添加点击回调
            mHorizontalScrollView.setOnItemClickListener(new MyHorizontalScrollView.OnItemClickListener() {
                @Override
                public void onClick(View view, int position) {
                    setView(view, position);
                }
            });
            //设置适配器
            mHorizontalScrollView.initDatas(mAdapter);
        } else {
            ToastUtil.showToastShort(getActivity(), "没有试题");
        }
    }

    private void setView(View view, int position) {
        view.setBackgroundResource(R.drawable.shape_white_retangle_selected);
        try {
            targetScore.setText(module.getResult().get(position).getTarget() + "分");
            allRank.setText(module.getResult().get(position).getRank() + "名");
            coverageExam.setText(module.getResult().get(position).getCoverpoint() + "个");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void examPidPosition(int type, String courseType, String courseId) {
        if (type == 2) {
            this.courseId = courseId;
            this.courseType = courseType;
            fetchData();
        }
    }
}
