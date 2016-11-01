package com.michen.olaxueyuan.protocol.manager;

import com.michen.olaxueyuan.protocol.result.CommentModule;
import com.michen.olaxueyuan.protocol.result.CommentSucessResult;
import com.michen.olaxueyuan.protocol.result.ExamModule;
import com.michen.olaxueyuan.protocol.result.MCQuestionListResult;
import com.michen.olaxueyuan.protocol.result.MessageListResult;
import com.michen.olaxueyuan.protocol.result.MessageRecordResult;
import com.michen.olaxueyuan.protocol.result.MessageUnReadResult;
import com.michen.olaxueyuan.protocol.result.OLaCircleModule;
import com.michen.olaxueyuan.protocol.result.OLaCircleOldModule;
import com.michen.olaxueyuan.protocol.result.PostDetailModule;
import com.michen.olaxueyuan.protocol.result.QuestionCourseModule;
import com.michen.olaxueyuan.protocol.result.UnlockSubjectResult;
import com.michen.olaxueyuan.protocol.service.QuestionService;

import retrofit.Callback;

/**
 * Created by mingge on 16/4/28.
 */
public class QuestionCourseManager {
    private static QuestionCourseManager q_instance;
    private QuestionService questionService;

    private QuestionCourseManager() {
        questionService = SERestManager.getInstance().create(QuestionService.class);
    }

    public static QuestionCourseManager getInstance() {
        if (q_instance == null) {
            q_instance = new QuestionCourseManager();
        }
        return q_instance;
    }

    public QuestionService getQuestionService() {
        return questionService;
    }

    /**
     * 首页课程列表
     *
     * @param callback
     */
    public void fetchHomeCourseList(String userid, String pid, String type, final Callback<QuestionCourseModule> callback) {
        getQuestionService().fetchHomeCourseList(userid, pid, type, callback);
    }

    /**
     * 题库首页
     *
     * @param courseId
     * @param type
     * @param callback
     */
    public void getExamList(String userId, String courseId, String type, final Callback<ExamModule> callback) {
        getQuestionService().getExamList(userId, courseId, type, callback);
    }

    /**
     * 获取题目列表
     *
     * @param callback
     */
    public void fetchExamQuestionList(String examId, final Callback<MCQuestionListResult> callback) {
        getQuestionService().getExamQuestionList(examId, callback);
    }

    /**
     * 欧拉圈，获取视频观看历史记录列表(old)
     * <p/>
     * {@link #getCircleList(String, String, String, Callback)}
     *
     * @param callback
     */
    @Deprecated
    public void getHistotyList(String videoId, String pageSize, final Callback<OLaCircleOldModule> callback) {
        getQuestionService().getHistotyList(videoId, pageSize, callback);
    }

    /**
     * 欧拉圈，获取视频观看历史记录列表
     *
     * @param circleId
     * @param pageSize
     * @param callback
     */
    public void getCircleList(String circleId, String pageSize, String type, final Callback<OLaCircleModule> callback) {
        getQuestionService().getCircleList(circleId, pageSize, type, callback);
    }

    /**
     * 评论列表
     *
     * @param postId   couserId或circle中的帖子Id
     * @param type     1 postId为课程 2 postId 为帖子
     * @param callback
     */
    public void getCommentList(String postId, String type, final Callback<CommentModule> callback) {
        getQuestionService().getCommentList(postId, type, callback);
    }

    /**
     * 发表评论
     *
     * @param userId
     * @param postId   课程Id 或 帖子Id
     * @param toUserId
     * @param content
     * @param location
     * @param type     1 课程评论 2 帖子评论
     * @param callback
     */
    public void addComment(String userId, String postId, String toUserId, String content, String location, String type, final Callback<CommentSucessResult> callback) {
        getQuestionService().addComment(userId, postId, toUserId, content, location, type, callback);
    }

    /**
     * 消息列表
     *
     * @param userId
     * @param messageId 当前页最后一条的id
     * @param pageSize  每页条数
     * @param callback
     */
    public void getMessageList(String userId, String messageId, String pageSize, Callback<MessageListResult> callback) {
        getQuestionService().getMessageList(userId, messageId, pageSize, callback);
    }

    /**
     * 消息标记为已读
     *
     * @param userId
     * @param messageIds 消息id拼接的字符串
     * @param callback
     */
    public void addMessageRecord(String userId, String messageIds, Callback<MessageRecordResult> callback) {
        getQuestionService().addMessageRecord(userId, messageIds, callback);
    }

    /**
     * 未读消息数
     *
     * @param userId
     * @param callback
     */
    public void getUnreadCount(String userId, Callback<MessageUnReadResult> callback) {
        getQuestionService().getUnreadCount(userId, callback);
    }

    /**
     * 帖子详情
     *
     * @param circleId
     * @param callback
     */
    public void queryCircleDetail(String circleId, Callback<PostDetailModule> callback) {
        getQuestionService().queryCircleDetail(circleId, callback);
    }

    /**
     * 解锁题目
     *
     * @param userId
     * @param objectId 课程ID／模考Id
     * @param type     1 课程 2 模考
     * @param callback
     */
    public void unlockSubject(String userId, String objectId, String type, Callback<UnlockSubjectResult> callback) {
        getQuestionService().unlockSubject(userId, objectId, type, callback);
    }
}
