package com.snail.olaxueyuan.protocol.result;

import com.google.gson.annotations.SerializedName;
import com.snail.olaxueyuan.protocol.model.MCCourse;

/**
 * Created by tianxiaopeng on 15/12/21.
 */
public class MCCourseListResult extends ServiceResult {
    public String apicode;
    public String message;
    @SerializedName("result")
    public MCCourse course;
}
