package com.snail.olaxueyuan.protocol.result;

import com.google.gson.annotations.SerializedName;
import com.snail.olaxueyuan.protocol.model.MCOrgInfo;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15/12/20.
 */
public class MCOrgListResult extends ServiceResult{
    public String apicode;
    public String message;
    @SerializedName("result")
    public ArrayList<MCOrgInfo> orgList;
}
