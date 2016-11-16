package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.MCCommonResult;
import com.michen.olaxueyuan.protocol.result.PraiseCirclePostResult;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by tianxiaopeng on 16/7/10.
 */
public interface MCCircleService {
    /**
     * 服务机构列表
     *
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/circle/addOlaCircle")
    void addOlaCircle(@Field("userId") String userId,
                      @Field("title") String title,
                      @Field("content") String content,
                      @Field("imageGids") String imageGids,
                      @Field("location") String location,
                      @Field("type") String type,
                      @Field("phoneInfo") String phoneInfo,
                      Callback<MCCommonResult> cb);

    /**
     * 帖子点赞
     *
     * @param circleId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/circle/praiseCirclePost")
    void praiseCirclePost(
            @Field("circleId") String circleId,
            Callback<PraiseCirclePostResult> cb);
}
