package com.michen.olaxueyuan.protocol.result;

import com.google.gson.annotations.SerializedName;
import com.michen.olaxueyuan.protocol.model.MCCheckInfo;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 16/1/20.
 */
public class MCEnrollListResult extends ServiceResult {
    public String apicode;
    public String message;
    @SerializedName("result")
    public ArrayList<MCCheckInfo> enrollList;
}
