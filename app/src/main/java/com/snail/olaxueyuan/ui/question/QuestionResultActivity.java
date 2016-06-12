package com.snail.olaxueyuan.ui.question;

import android.os.Bundle;
import android.app.Activity;

import com.google.gson.Gson;
import com.snail.olaxueyuan.R;
import com.snail.olaxueyuan.ui.activity.SEBaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuestionResultActivity extends SEBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_result);
        setTitleText("答题报告");
        String jsonString = getIntent().getStringExtra("answerArray");
        try {
            JSONArray array=new JSONArray(jsonString);
            for (int i=0;i<array.length();i++){
                //Todo 解析
                Object o = array.get(i);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
