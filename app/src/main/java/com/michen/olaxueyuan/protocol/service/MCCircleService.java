package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.AppointTeacherListResult;
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
     * @param userId
     * @param title
     * @param content
     * @param imageGids
     * @param location
     * @param type
     * @param phoneInfo  手机信息
     * @param assignUser 指定回答者Id
     * @param isPublic   是否公开
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
                      @Field("assignUser") String assignUser,
                      @Field("isPublic") String isPublic,
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

    /**
     * 邀请老师回答列表
     *
     * @param callback
     */
    @POST("/ola/user/getTeacherList")
    void getTeacherList(
            Callback<AppointTeacherListResult> callback);
}
