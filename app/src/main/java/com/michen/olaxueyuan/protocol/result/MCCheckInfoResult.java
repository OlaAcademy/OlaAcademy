package com.michen.olaxueyuan.protocol.result;

import com.google.gson.annotations.SerializedName;
import com.michen.olaxueyuan.protocol.model.MCCheckInfo;

/**
 * Created by tianxiaopeng on 16/1/20.
 */
public class MCCheckInfoResult extends ServiceResult {
    public String apicode;
    public String message;
    @SerializedName("result")
    public MCCheckInfo checkInfo;
}
