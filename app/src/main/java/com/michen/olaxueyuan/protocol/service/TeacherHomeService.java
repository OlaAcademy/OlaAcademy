package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.AttendGroupResult;
import com.michen.olaxueyuan.protocol.result.CreateGroupResult;
import com.michen.olaxueyuan.protocol.result.GroupMemberResult;
import com.michen.olaxueyuan.protocol.result.HomeworkListResult;
import com.michen.olaxueyuan.protocol.result.HomeworkStatisticsResult;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
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
     * @param type    1、数学2、英语3、逻辑4、写作
     * @param name
     * @param profile
     * @param avatar
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/homework/createGroup")
    void createGroup(
            @Field("userId") String userId,
            @Field("type") String type,
            @Field("name") String name,
            @Field("profile") String profile,
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
     * @param type   1 数学 2 英语 3 逻辑 4 写作
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/homework/getUserGroupList")
    void getUserGroupList(
            @Field("userId") String userId,
            @Field("type") String type,
            Callback<UserGroupListResult> cb);

    /**
     * 作业列表
     *
     * @param userId
     * @param type       1 学生 2 老师
     * @param homeworkId 当前页最后一条的id(否)
     * @param pageSize   每页条数(否)
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/homework/getHomeworkList")
    void getHomeworkList(
            @Field("userId") String userId,
            @Field("type") String type,
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

    /**
     * 加入／退出群
     *
     * @param userId
     * @param groupId
     * @param type    1 加入 2 退出
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/homework/attendGroup")
    void attendGroup(
            @Field("userId") String userId,
            @Field("groupId") String groupId,
            @Field("type") String type,
            Callback<AttendGroupResult> cb);

    /**
     * 发布作业
     *
     * @param name
     * @param groupIds   群id
     * @param subjectIds 字符串	题目Id串 逗号分隔
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/homework/deployHomework")
    void deployHomework(
            @Field("name") String name,
            @Field("groupIds") String groupIds,
            @Field("subjectIds") String subjectIds,
            Callback<SimpleResult> cb);

    /**
     * （老师版）学生作业完成情况
     *
     * @param groupId    群id
     * @param homeworkId 该作业的id
     * @param pageIndex  起始页 1
     * @param pageSize
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/homework/getHomeworkStatistics")
    void getHomeworkStatistics(
            @Field("groupId") String groupId,
            @Field("homeworkId") String homeworkId,
            @Field("pageIndex") int pageIndex,
            @Field("pageSize") int pageSize,
            Callback<HomeworkStatisticsResult> cb);

    /**
     * 群成员列表
     *
     * @param groupId
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/homework/queryGroupMember")
    void queryGroupMember(
            @Field("groupId") String groupId,
            @Field("pageIndex") int pageIndex,
            @Field("pageSize") int pageSize,
            Callback<GroupMemberResult> cb);
}
