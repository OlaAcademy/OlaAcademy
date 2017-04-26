package com.michen.olaxueyuan.ui.question;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.CircleProgressBar;
import com.michen.olaxueyuan.protocol.manager.SEAuthManager;
import com.michen.olaxueyuan.protocol.result.QuestionCourseModule;
import com.michen.olaxueyuan.ui.SuperFragment;
import com.michen.olaxueyuan.ui.me.activity.UserLoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

import static android.R.id.list;

/**
 * Created by mingge on 16/8/9.
 */
public class QuestionHomeWorkFragment extends SuperFragment {
    View view;
    @Bind(R.id.today_homework)
    TextView todayHomework;
    @Bind(R.id.course_more)
    TextView courseMore;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.time_text)
    TextView timeText;
    @Bind(R.id.course_num_text)
    TextView courseNumText;
    @Bind(R.id.group_name_text)
    TextView groupNameText;
    @Bind(R.id.circle_progress)
    CircleProgressBar circleProgress;
    @Bind(R.id.homeworkRL)
    RelativeLayout homeworkRL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = View.inflate(getActivity(), R.layout.fragment_question_homework_layout, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }

    int type = 1;

    private void initView() {
        type = getArguments().getInt("type");
    }

    /**
     * {@link  QuestionFragment#fetchHomeCourseData()}
     *
     * @param questionCourseModule
     */
    public void onEventMainThread(final QuestionCourseModule questionCourseModule) {
        if (questionCourseModule.getResult().getHomework() != null) {
            title.setText(questionCourseModule.getResult().getHomework().getName());
            timeText.setText(questionCourseModule.getResult().getHomework().getTime());
            courseNumText.setText(questionCourseModule.getResult().getHomework().getCount() + "道小题");
            groupNameText.setText(questionCourseModule.getResult().getHomework().getGroupName());
            simulateProgress(questionCourseModule.getResult().getHomework().getFinishedCount() * 100 / questionCourseModule.getResult().getHomework().getCount());
        } else {
            simulateProgress(0);
            title.setText("欧拉练习");
            groupNameText.setText("欧拉作业群");
        }
        homeworkRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SEAuthManager.getInstance().isAuthenticated()) {
                    Intent intent = new Intent(getActivity(), QuestionWebActivity.class);
                    intent.putExtra("objectId", questionCourseModule.getResult().getHomework().getId());
                    intent.putExtra("type", 3);
                    intent.putExtra("courseType", 1); //2 英语阅读
                    startActivity(intent);
                } else {
                    startActivity(new Intent(getActivity(), UserLoginActivity.class));
                }
            }
        });
    }

    private void simulateProgress(int progress) {
        ValueAnimator animator = ValueAnimator.ofInt(0, progress);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                try {
                    int progress = (int) animation.getAnimatedValue();
                    circleProgress.setProgress(progress);
                    if (progress == 0) {
                        circleProgress.setProgressBackgroundColor(Color.parseColor("#FE1600"));
                    } else if (progress > 0 && progress < 50) {
                        circleProgress.setProgressColor(Color.parseColor("#EC950D"));
                        circleProgress.setProgressBackgroundColor(Color.parseColor("#ffd3d3d5"));
                    } else if (progress >= 50 && progress < 100) {
                        circleProgress.setProgressColor(Color.parseColor("#009688"));
                        circleProgress.setProgressBackgroundColor(Color.parseColor("#ffd3d3d5"));
                    } else {
                        circleProgress.setProgressColor(Color.parseColor("#4285F4"));
                        circleProgress.setProgressBackgroundColor(Color.parseColor("#ffd3d3d5"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatCount(0);
        animator.setDuration(1200);
        animator.start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.course_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.course_more:
                startActivity(new Intent(getActivity(), QuestionHomeWorkListActivity.class));
                break;
        }
    }
}
