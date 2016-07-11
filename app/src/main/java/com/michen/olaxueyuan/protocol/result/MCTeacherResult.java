package com.michen.olaxueyuan.protocol.result;

import com.google.gson.annotations.SerializedName;
import com.michen.olaxueyuan.protocol.model.MCTeacher;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15/12/29.
 */
public class MCTeacherResult extends ServiceResult {
    public String apicode;
    public String message;
    @SerializedName("result")
    public ArrayList<MCTeacher> teacherArrayList;
}
