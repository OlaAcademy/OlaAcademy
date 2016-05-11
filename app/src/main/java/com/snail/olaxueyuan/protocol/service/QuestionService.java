package com.snail.olaxueyuan.protocol.service;

import com.snail.olaxueyuan.protocol.result.ExamModule;
import com.snail.olaxueyuan.protocol.result.OLaCircleModule;
import com.snail.olaxueyuan.protocol.result.QuestionCourseModule;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

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
    @GET("/ola/cour/getCourList")
    public void fetchHomeCourseList(@Query("pid") String pid,
                                    @Query("type") String type,
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
    public void getExamList(@Field("courseId") String courseId,
                            @Field("type") String type,
                            Callback<ExamModule> cb);

    /**
     * 欧拉圈，获取视频观看历史记录列表
     *
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/cour/getHistotyList")
    public void getHistotyList(
            @Field("courseId") String courseId,//没有这个参数,写上是因为报错
            Callback<OLaCircleModule> cb);

}
