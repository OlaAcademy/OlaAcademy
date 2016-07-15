package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.ExamModule;
import com.michen.olaxueyuan.protocol.result.OLaCircleModule;
import com.michen.olaxueyuan.protocol.result.OLaCircleOldModule;
import com.michen.olaxueyuan.protocol.result.QuestionCourseModule;
import com.michen.olaxueyuan.protocol.result.MCQuestionListResult;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by mingge on 16/4/28.
 */
public interface QuestionService {
    /**
     * 首页课程列表
     *
     * @param pid  1 数学 2 英语 3 逻辑 4 协作
     * @param type 1 题库课程 2 视频课程
     */
    @FormUrlEncoded
    @POST("/ola/cour/getCourList")
    public void fetchHomeCourseList(
            @Field("userid") String userid,
            @Field("pid") String pid,
            @Field("type") String type,
            Callback<QuestionCourseModule> cb);

    /**
     * 模考／真题列表
     *
     * @param courseId 课程id 1 数学 2 英语 3 逻辑 4 协作
     * @param type     1 模考 2 真题
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/exam/getExamList")
    public void getExamList(
            @Field("userId") String userid,
            @Field("courseId") String courseId,
            @Field("type") String type,
            Callback<ExamModule> cb);

    /**
     * 模考／真题 题目列表
     *
     * @param examId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/exam/getExamSubList")
    public void getExamQuestionList(@Field("examId") String examId,
                                    Callback<MCQuestionListResult> cb);

    /**
     * 欧拉圈，获取视频观看历史记录列表(老接口)
     * {@link #getCircleList(String, String, Callback)}
     *
     * @param cb
     */
    @Deprecated
    @FormUrlEncoded
    @POST("/ola/cour/getHistoryList")
    void getHistotyList(@Field("videoId") String videoId,
                        @Field("pageSize") String pageSize,
                        Callback<OLaCircleOldModule> cb);

    /**
     * 欧拉圈，获取视频观看历史记录列表
     *
     * @param circleId
     * @param pageSize
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/circle/getCircleList")
    void getCircleList(@Field("circleId") String circleId,
                       @Field("pageSize") String pageSize,
                       Callback<OLaCircleModule> cb);

}
