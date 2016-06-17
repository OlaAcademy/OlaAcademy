package com.snail.olaxueyuan.protocol.service;

import com.snail.olaxueyuan.protocol.result.ExamModule;
import com.snail.olaxueyuan.protocol.result.MCQuestionListResult;
import com.snail.olaxueyuan.protocol.result.OLaCircleModule;
import com.snail.olaxueyuan.protocol.result.QuestionCourseModule;

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
     * 欧拉圈，获取视频观看历史记录列表
     *
     * @param cb
     */
    @POST("/ola/cour/getHistoryList")
    void getHistotyList(Callback<OLaCircleModule> cb);

}
