package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.CommentModule;
import com.michen.olaxueyuan.protocol.result.CommentSucessResult;
import com.michen.olaxueyuan.protocol.result.ExamModule;
import com.michen.olaxueyuan.protocol.result.MCQuestionListResult;
import com.michen.olaxueyuan.protocol.result.MessageListResult;
import com.michen.olaxueyuan.protocol.result.MessageRecordResult;
import com.michen.olaxueyuan.protocol.result.MessageUnReadResult;
import com.michen.olaxueyuan.protocol.result.OLaCircleModule;
import com.michen.olaxueyuan.protocol.result.OLaCircleOldModule;
import com.michen.olaxueyuan.protocol.result.QuestionCourseModule;

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

    /**
     * 评论列表
     *
     * @param postId couserId或circle中的帖子Id
     * @param type   1 postId为课程 2 postId 为帖子
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/comment/getCommentList")
    void getCommentList(@Field("postId") String postId,
                        @Field("type") String type,
                        Callback<CommentModule> cb);

    /**
     * 发表评论
     *
     * @param userId
     * @param postId   课程Id 或 帖子Id
     * @param toUserId
     * @param content
     * @param location
     * @param type     1 课程评论 2 帖子评论
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/comment/addComment")
    void addComment(@Field("userId") String userId,
                    @Field("postId") String postId,
                    @Field("toUserId") String toUserId,
                    @Field("content") String content,
                    @Field("location") String location,
                    @Field("type") String type,
                    Callback<CommentSucessResult> cb);

    /**
     * 消息列表
     *
     * @param userId
     * @param messageId 当前页最后一条的id
     * @param pageSize  每页条数
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/message/getMessageList")
    void getMessageList(@Field("userId") String userId,
                        @Field("messageId") String messageId,
                        @Field("pageSize") String pageSize,
                        Callback<MessageListResult> cb);

    /**
     * 消息标记为已读
     *
     * @param userId
     * @param messageIds 消息id拼接的字符串
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/message/addMessageRecord")
    void addMessageRecord(@Field("userId") String userId,
                          @Field("messageIds") String messageIds,
                          Callback<MessageRecordResult> cb);

    /**
     * 未读消息数
     *
     * @param userId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/message/getUnreadCount")
    void getUnreadCount(@Field("userId") String userId,
                        Callback<MessageUnReadResult> cb);


}
