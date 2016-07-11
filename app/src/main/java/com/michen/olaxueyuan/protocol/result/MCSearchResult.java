package com.michen.olaxueyuan.protocol.result;

import com.google.gson.annotations.SerializedName;
import com.michen.olaxueyuan.protocol.model.MCVideo;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15/12/25.
 */
public class MCSearchResult extends ServiceResult {
    public String apicode;
    public String message;
    @SerializedName("result")
    public ArrayList<MCVideo> videoArrayList;
}
