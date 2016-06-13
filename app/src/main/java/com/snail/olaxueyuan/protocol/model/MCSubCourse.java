package com.snail.olaxueyuan.protocol.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15/12/21.
 */
public class MCSubCourse {
    @Override
    public String toString() {
        return "MCSubCourse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pid='" + pid + '\'' +
                ", profile='" + profile + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", totalTime='" + totalTime + '\'' +
                ", playcount='" + playcount + '\'' +
                ", subCourseArrayList=" + subCourseArrayList +
                '}';
    }

    public String id;
    public String name;
    public String pid;
    public String profile;
    public String type;
    public String address;
    public String totalTime;
    public String playcount;
    @SerializedName("child")
    public ArrayList<MCSubCourse> subCourseArrayList;
}
