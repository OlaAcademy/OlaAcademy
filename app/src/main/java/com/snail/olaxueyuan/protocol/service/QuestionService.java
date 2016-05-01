package com.snail.olaxueyuan.protocol.service;

import com.snail.olaxueyuan.protocol.result.QuestionCourseModule;

import retrofit.Callback;
import retrofit.http.GET;
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
}
