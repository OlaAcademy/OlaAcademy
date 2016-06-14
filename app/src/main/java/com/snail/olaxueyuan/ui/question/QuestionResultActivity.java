package com.snail.olaxueyuan.ui.question;

import android.os.Bundle;
import android.view.View;

import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.common.manager.Logger;
import com.snail.olaxueyuan.ui.activity.SuperActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionResultActivity extends SuperActivity {
    private int hasAnswer = 0;//已答题目
    private int correct = 0;//正确率
    private int winPercent = 0;//打败多少考生
    private int objectId;//试题的ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_result);
    }

    @Override
    public void initView() {
        setTitleText("答题报告");
        String jsonString = getIntent().getStringExtra("answerArray");
        objectId = getIntent().getExtras().getInt("objectId");
        Logger.json(jsonString);
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                //Todo 解析
                JSONObject object = (JSONObject) array.get(i);
                if (!object.optString("isCorrect").equals("2")) {//没答题
                    hasAnswer++;
                }
                if (object.optString("isCorrect").equals("0")) {//答题正确
                    correct++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }
}
