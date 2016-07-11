package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.MCCommonResult;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by tianxiaopeng on 16/7/10.
 */
public interface MCCircleService {
    /**
     * 服务机构列表
     *
     * @param cb
     */
    @Multipart
    @POST("/ola/circle/addOlaCircle")
    public void addOlaCircle(@Part("userId") String userId,
                             @Part("title") String title,
                             @Part("content") String content,
                             @Part("imageGids") String imageGids,
                             @Part("location") String location,
                             @Part("type") String type,
                             Callback<MCCommonResult> cb);
}
