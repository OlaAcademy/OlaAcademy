package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.CreateGroupResult;
import com.michen.olaxueyuan.protocol.result.HomeworkListResult;
import com.michen.olaxueyuan.protocol.result.SubjectListResult;
import com.michen.olaxueyuan.protocol.result.TeacherGroupListResult;
import com.michen.olaxueyuan.protocol.result.UserGroupListResult;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by mingge on 16/4/28.
 */
public interface TeacherHomeService {
    /**
     * 创建群
     *
     * @param userId
     * @param name
     * @param avatar
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/homework/createGroup")
    void createGroup(
            @Field("userId") String userId,
            @Field("name") String name,
            @Field("avatar") String avatar,
            Callback<CreateGroupResult> cb);

    /**
     * 老师所创建群列表
     *
     * @param userId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/homework/getTeacherGroupList")
    void getTeacherGroupList(
            @Field("userId") String userId,
            Callback<TeacherGroupListResult> cb);

    /**
     * 学生所在群列表
     *
     * @param userId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/homework/getUserGroupList")
    void getUserGroupList(
            @Field("userId") String userId,
            Callback<UserGroupListResult> cb);

    /**
     * 作业列表
     *
     * @param userId
     * @param homeworkId 当前页最后一条的id(否)
     * @param pageSize   每页条数(否)
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/homework/getHomeworkList")
    void getHomeworkList(
            @Field("userId") String userId,
            @Field("homeworkId") String homeworkId,
            @Field("pageSize") String pageSize,
            Callback<HomeworkListResult> cb);

    /**
     * 题目列表
     *
     * @param homeworkId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/homework/getSubjectList")
    void getSubjectList(
            @Field("homeworkId") String homeworkId,
            Callback<SubjectListResult> cb);
}
