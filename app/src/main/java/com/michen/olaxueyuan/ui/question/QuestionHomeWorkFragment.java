package com.michen.olaxueyuan.ui.question;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michen.olaxueyuan.R;
import com.michen.olaxueyuan.common.CircleProgressBar;
import com.michen.olaxueyuan.protocol.result.QuestionCourseModule;
import com.michen.olaxueyuan.ui.SuperFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

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
    public void onEventMainThread(QuestionCourseModule questionCourseModule) {
        title.setText(questionCourseModule.getResult().getHomework().getName());
        timeText.setText(questionCourseModule.getResult().getHomework().getTime());
        courseNumText.setText(questionCourseModule.getResult().getHomework().getCount() + "道小题");
        groupNameText.setText(questionCourseModule.getResult().getHomework().getGroupName());
        simulateProgress(questionCourseModule.getResult().getHomework().getFinishedCount() * 100 / questionCourseModule.getResult().getHomework().getCount());
    }

    private void simulateProgress(int progress) {
        ValueAnimator animator = ValueAnimator.ofInt(0, progress);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                try {
                    int progress = (int) animation.getAnimatedValue();
                    circleProgress.setProgress(progress);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatCount(0);
        animator.setDuration(2000);
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
                break;
        }
    }
}
