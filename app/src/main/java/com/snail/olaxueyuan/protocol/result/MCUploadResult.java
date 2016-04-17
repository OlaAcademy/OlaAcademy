package com.snail.olaxueyuan.protocol.result;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tianxiaopeng on 16/1/21.
 */
public class MCUploadResult extends ServiceResult {
    public String apicode;
    public String message;
    @SerializedName("result")
    public String imageName;
}
