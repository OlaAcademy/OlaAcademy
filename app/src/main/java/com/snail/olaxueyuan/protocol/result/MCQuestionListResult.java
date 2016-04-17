package com.snail.olaxueyuan.protocol.result;

import com.google.gson.annotations.SerializedName;
import com.snail.olaxueyuan.protocol.model.MCQuestion;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 16/3/19.
 */
public class MCQuestionListResult extends ServiceResult {

    public String apicode;
    public String message;
    @SerializedName("result")
    public ArrayList<MCQuestion> questionList;
}
