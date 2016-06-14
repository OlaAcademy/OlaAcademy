package com.snail.olaxueyuan.ui.question;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.Logger;
import com.snail.olaxueyuan.common.manager.ToastUtil;
import com.snail.olaxueyuan.ui.activity.SEBaseActivity;
import com.snail.olaxueyuan.ui.adapter.QuestionResultAdapter;
import com.snail.photo.util.NoScrollGridView;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionResultActivity extends SEBaseActivity {
    @Bind(R.id.correct_num_tv)
    TextView correctNumTv;
    @Bind(R.id.answer_all_number_tv)
    TextView answerAllNumberTv;
    @Bind(R.id.has_answer_num_tv)
    TextView hasAnswerNumTv;
    @Bind(R.id.correct_precent_tv)
    TextView correctPercent;
    @Bind(R.id.win_student_tv)
    TextView winStudentTv;
    @Bind(R.id.grid_answer)
    GridView gridAnswer;
    //    NoScrollGridView gridAnswer;
    @Bind(R.id.grid_knowledge)
    NoScrollGridView gridKnowledge;
    @Bind(R.id.start_exam)
    Button startExam;
    @Bind(R.id.finish_answer)
    Button finishAnswer;
    private int hasAnswer = 0;//已答题目
    private int correct = 0;//正确率
    private int winPercent = 30;//打败多少考生
    private int objectId;//试题的ID
    private JSONArray array;
    private QuestionResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_result);
        ButterKnife.bind(this);
        setTitleText("答题报告");
        initView();
    }

    public void initView() {
        String jsonString = getIntent().getStringExtra("answerArray");
        objectId = getIntent().getExtras().getInt("objectId");
        Logger.json(jsonString);
        try {
            array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                //Todo 解析
                JSONObject object = (JSONObject) array.get(i);
                if (!object.optString("isCorrect").equals("2")) {//没答题
                    hasAnswer++;
                }
                if (object.optString("isCorrect").equals("1")) {//答题正确
                    correct++;
                }
            }
            correctNumTv.setText(String.valueOf(correct));
            answerAllNumberTv.setText(getString(R.string.all_number_subject, array.length()));
            hasAnswerNumTv.setText(String.valueOf(hasAnswer));
            correctPercent.setText(correct * 100 / array.length() + "%");
            winStudentTv.setText(String.valueOf(winPercent) + "%");
            resultAdapter = new QuestionResultAdapter(array, QuestionResultActivity.this);
            gridAnswer.setAdapter(resultAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.start_exam, R.id.finish_answer})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_exam:
                ToastUtil.showToastShort(QuestionResultActivity.this, "我是开始解析");
                break;
            case R.id.finish_answer:
                ToastUtil.showToastShort(QuestionResultActivity.this, "我是答题完成");
                break;
        }
    }
}
