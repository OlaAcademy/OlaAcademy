package com.michen.olaxueyuan.protocol.manager;

import android.text.TextUtils;

import com.michen.olaxueyuan.protocol.result.CircleMessageListResult;
import com.michen.olaxueyuan.protocol.result.CommentModule;
import com.michen.olaxueyuan.protocol.result.CommentSucessResult;
import com.michen.olaxueyuan.protocol.result.ExamModule;
import com.michen.olaxueyuan.protocol.result.MCQuestionListResult;
import com.michen.olaxueyuan.protocol.result.MessageListResult;
import com.michen.olaxueyuan.protocol.result.MessageRecordResult;
import com.michen.olaxueyuan.protocol.result.MessageUnReadResult;
import com.michen.olaxueyuan.protocol.result.MessageUnreadTotalCountResult;
import com.michen.olaxueyuan.protocol.result.OLaCircleModule;
import com.michen.olaxueyuan.protocol.result.OLaCircleOldModule;
import com.michen.olaxueyuan.protocol.result.PlanConditionResult;
import com.michen.olaxueyuan.protocol.result.PlanHomeData;
import com.michen.olaxueyuan.protocol.result.PostDetailModule;
import com.michen.olaxueyuan.protocol.result.PraiseListResult;
import com.michen.olaxueyuan.protocol.result.QuestionCourseModule;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.protocol.result.UnlockSubjectResult;
import com.michen.olaxueyuan.protocol.result.UserPlanDetailResult;
import com.michen.olaxueyuan.protocol.result.UserPlanFinishedDetailResult;
import com.michen.olaxueyuan.protocol.result.UserPostListResult;
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
	 *
	 * @param callback
	 */
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
	public void getCircleList(String userId, String circleId, String pageSize, String type, final Callback<OLaCircleModule> callback) {
		getQuestionService().getCircleList(userId, circleId, pageSize, type, callback);
	}

	/**
	 * 评论列表
	 *
	 * @param postId   couserId或circle中的帖子Id
	 * @param type     1 postId为课程 2 postId 为帖子
	 * @param assign   0 全部评论 1 指定回答
	 * @param callback
	 */
	public void getCommentList(String postId, String type, String assign, String curUserId, final Callback<CommentModule> callback) {
		getQuestionService().getCommentList(postId, type, assign, curUserId, callback);
	}

	/**
	 * 发表评论
	 *
	 * @param userId
	 * @param postId    课程Id 或 帖子Id
	 * @param toUserId
	 * @param content
	 * @param location
	 * @param type      1 课程评论 2 帖子评论
	 * @param imageIds
	 * @param videoUrls
	 * @param videoImgs
	 * @param audioUrls
	 * @param callback
	 */
	public void addComment(String userId, String postId, String toUserId, String content, String type
			, String imageIds, String videoUrls, String videoImgs, String audioUrls
			, final Callback<CommentSucessResult> callback) {
		getQuestionService().addComment(userId, postId, toUserId, content, type, imageIds, videoUrls, videoImgs, audioUrls, callback);
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
	 * 未读消息数(旧)
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
	public void queryCircleDetail(String userId, String circleId, Callback<PostDetailModule> callback) {
		getQuestionService().queryCircleDetail(userId, circleId, callback);
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

	/**
	 * 消息列表(新)
	 *
	 * @param userId
	 * @param cb
	 */
	public void getUnreadTotalCount(String userId, Callback<MessageUnreadTotalCountResult> callback) {
		getQuestionService().getUnreadTotalCount(userId, callback);
	}

	/**
	 * 评论消息列表
	 *
	 * @param userId    (必填)
	 * @param commentId 最后一条ID，用户翻页
	 * @param pageSize
	 * @param type      1 观看记录 2 发帖 ，固定传2
	 * @param callback
	 */
	public void getCircleMessageList(String userId, String commentId, String pageSize, String type, Callback<CircleMessageListResult> callback) {
		getQuestionService().getCircleMessageList(userId, commentId, pageSize, type, callback);
	}

	/**
	 * 点赞列表
	 *
	 * @param userId   (必填)
	 * @param praiseId
	 * @param pageSize
	 * @param cb
	 */
	public void getPraiseList(String userId, String praiseId, String pageSize, Callback<PraiseListResult> callback) {
		getQuestionService().getPraiseList(userId, praiseId, pageSize, callback);
	}

	/**
	 * 个人主页
	 *
	 * @param userId    (必填)
	 * @param curUserId 当前用户userId
	 * @param callback
	 */
	public void getUserPostList(String userId, String curUserId, Callback<UserPostListResult> callback) {
		getQuestionService().getUserPostList(userId, curUserId, callback);
	}

	/**
	 * 计划首页
	 *
	 * @param userId
	 * @param callback
	 */
	public void queryPlanHomeData(String userId, Callback<PlanHomeData> callback) {
		getQuestionService().queryPlanHomeData(userId, callback);
	}

	/**
	 * 计划制定前提条件
	 *
	 * @param callback
	 */
	public void queryPlanCondition(Callback<PlanConditionResult> callback) {
		getQuestionService().queryPlanCondition(callback);
	}

	/**
	 * 制定计划
	 *
	 * @param userId
	 * @param plan
	 * @param callback
	 */
	public void createStudyPlan(String userId, String plan, Callback<SimpleResult> callback) {
		getQuestionService().createStudyPlan(userId, plan, callback);
	}

	/**
	 * 用户计划详情
	 *
	 * @param userId
	 * @param date
	 * @param callback
	 */
	public void getUserPlanDetail(String userId, String date, Callback<UserPlanDetailResult> callback) {
		getQuestionService().getUserPlanDetail(userId, date, callback);
	}

	/**
	 * 计划完成每日详情
	 *
	 * @param userId
	 * @param callback
	 */
	public void getPlanFinishedDetailList(String userId, Callback<UserPlanFinishedDetailResult> callback) {
		getQuestionService().getPlanFinishedDetailList(userId, callback);
	}

	/**
	 * 添加／移除备学库（以后再学列表）
	 *
	 * @param userId
	 * @param detailId 知识点Id
	 * @param planDate 所在计划的日期（哪天学习该知识点）
	 * @param type     1 添加 0 删除
	 * @param callback
	 */
	public void collectPlanDetail(String userId, String detailId, String planDate, String type
			, Callback<SimpleResult> callback) {
		getQuestionService().collectPlanDetail(userId, detailId, planDate, type, callback);
	}
}
