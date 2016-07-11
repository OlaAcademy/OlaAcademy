package com.michen.olaxueyuan.protocol.result;

import com.google.gson.annotations.SerializedName;
import com.michen.olaxueyuan.protocol.model.MCVideo;

/**
 * Created by tianxiaopeng on 15/12/25.
 */
public class MCVideoResult extends ServiceResult {

    public String apicode;
    public String message;
    @SerializedName("result")
    public MCVideo videoInfo;
}
