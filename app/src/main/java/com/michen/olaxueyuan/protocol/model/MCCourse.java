package com.michen.olaxueyuan.protocol.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15/12/21.
 */
public class MCCourse implements Serializable{

    @SerializedName("id")
    public String courId;
    public String name;
    public String profile;
    public String totalTime;
    @SerializedName("child")
    public ArrayList<MCSubCourse> courseArrayList;
}
