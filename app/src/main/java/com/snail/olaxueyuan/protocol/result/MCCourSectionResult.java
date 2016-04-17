package com.snail.olaxueyuan.protocol.result;

import com.google.gson.annotations.SerializedName;
import com.snail.olaxueyuan.protocol.model.MCVideo;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15/12/21.
 */
public class MCCourSectionResult extends ServiceResult {

    public String apicode;
    public String message;
    @SerializedName("result")
    public ArrayList<MCVideo> videoArrayList;
}
