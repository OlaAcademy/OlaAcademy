package com.michen.olaxueyuan.protocol.service;

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

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
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
	 *
	 * @param cb
	 */
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
	 * @param type
	 * @param cb
	 */
	@FormUrlEncoded
	@POST("/ola/circle/getCircleList")
	void getCircleList(
			@Field("userId") String userId,
			@Field("circleId") String circleId,
			@Field("pageSize") String pageSize,
			@Field("type") String type,
			Callback<OLaCircleModule> cb);

	/**
	 * 评论列表
	 *
	 * @param postId couserId或circle中的帖子Id
	 * @param type   1 postId为课程 2 postId 为帖子
	 * @param assign 0 全部评论 1 指定回答
	 * @param cb
	 */
	@FormUrlEncoded
	@POST("/ola/comment/getCommentList")
	void getCommentList(@Field("postId") String postId,
						@Field("type") String type,
						@Field("assign") String assign,
						@Field("curUserId") String curUserId,
						Callback<CommentModule> cb);

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
	 * @param cb
	 */
	@FormUrlEncoded
	@POST("/ola/comment/addComment")
	void addComment(@Field("userId") String userId,
					@Field("postId") String postId,
					@Field("toUserId") String toUserId,
					@Field("content") String content,
					@Field("type") String type,
					@Field("imageIds") String imageIds,
					@Field("videoUrls") String videoUrls,
					@Field("videoImgs") String videoImgs,
					@Field("audioUrls") String audioUrls,
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
	 * 未读消息数（旧）
	 *
	 * @param userId
	 * @param cb
	 */
	@FormUrlEncoded
	@POST("/ola/message/getUnreadCount")
	void getUnreadCount(@Field("userId") String userId,
						Callback<MessageUnReadResult> cb);

	@FormUrlEncoded
	@POST("/ola/circle/queryCircleDetail")
	void queryCircleDetail(
			@Field("userId") String userId,
			@Field("circleId") String circleId,
			Callback<PostDetailModule> cb);

	/**
	 * 解锁题目
	 *
	 * @param userId
	 * @param objectId 课程ID／模考Id
	 * @param type     1 课程 2 模考
	 * @param cb
	 */
	@FormUrlEncoded
	@POST("/ola/exchange/unlockSubject")
	void unlockSubject(
			@Field("userId") String userId,
			@Field("objectId") String objectId,
			@Field("type") String type,
			Callback<UnlockSubjectResult> cb);

	/**
	 * 消息列表(新)
	 *
	 * @param userId
	 * @param cb
	 */
	@FormUrlEncoded
	@POST("/ola/message/getUnreadTotalCount")
	void getUnreadTotalCount(@Field("userId") String userId,
							 Callback<MessageUnreadTotalCountResult> cb);

	/**
	 * 评论消息列表
	 *
	 * @param userId    (必填)
	 * @param commentId 最后一条ID，用户翻页
	 * @param pageSize
	 * @param type      1 观看记录 2 发帖 ，固定传2
	 * @param cb
	 */
	@FormUrlEncoded
	@POST("/ola/comment/getCircleMessageList")
	void getCircleMessageList(
			@Field("userId") String userId,
			@Field("commentId") String commentId,
			@Field("pageSize") String pageSize,
			@Field("type") String type,
			Callback<CircleMessageListResult> cb);

	/**
	 * 点赞列表
	 *
	 * @param userId   (必填)
	 * @param praiseId
	 * @param pageSize
	 * @param cb
	 */
	@FormUrlEncoded
	@POST("/ola/circle/getPraiseList")
	void getPraiseList(
			@Field("userId") String userId,
			@Field("praiseId") String praiseId,
			@Field("pageSize") String pageSize,
			Callback<PraiseListResult> cb);

	/**
	 * 个人主页
	 *
	 * @param userId    (必填)
	 * @param curUserId 当前用户userId
	 * @param callback
	 */
	@FormUrlEncoded
	@POST("/ola/circle/getUserPostList")
	void getUserPostList(
			@Field("userId") String userId,
			@Field("curUserId") String curUserId,
			Callback<UserPostListResult> callback);

	/**
	 * 计划首页
	 *
	 * @param userId
	 * @param callback
	 */
	@FormUrlEncoded
	@POST("/ola/plan/queryPlanHomeData")
	void queryPlanHomeData(
			@Field("userId") String userId,
			Callback<PlanHomeData> callback);

	/**
	 * 计划制定前提条件
	 *
	 * @param callback
	 */
	@GET("/ola/plan/queryPlanCondition")
	void queryPlanCondition(
			Callback<PlanConditionResult> callback);

	/**
	 * 制定计划
	 *
	 * @param userId
	 * @param plan
	 * @param callback
	 */
	@FormUrlEncoded
	@POST("/ola/plan/createStudyPlan")
	void createStudyPlan(
			@Field("userId") String userId,
			@Field("plan") String plan,
			Callback<SimpleResult> callback);

	/**
	 * 用户计划详情
	 *
	 * @param userId
	 * @param date
	 * @param callback
	 */
	@FormUrlEncoded
	@POST("/ola/plan/getUserPlanDetail")
	void getUserPlanDetail(
			@Field("userId") String userId,
			@Field("date") String date,
			Callback<UserPlanDetailResult> callback);

	/**
	 * 计划完成每日详情
	 *
	 * @param userId
	 * @param callback
	 */
	@FormUrlEncoded
	@POST("/ola/planrecord/getPlanFinishedDetailList")
	void getPlanFinishedDetailList(
			@Field("userId") String userId,
			Callback<UserPlanFinishedDetailResult> callback);

	/**
	 * 添加／移除备学库（以后再学列表）
	 *
	 * @param userId
	 * @param detailId 知识点Id
	 * @param planDate 所在计划的日期（哪天学习该知识点）
	 * @param type     1 添加 0 删除
	 * @param callback
	 */
	@FormUrlEncoded
	@POST("/ola/plancollect/collectPlanDetail")
	void collectPlanDetail(
			@Field("userId") String userId,
			@Field("detailId") String detailId,
			@Field("planDate") String planDate,
			@Field("type") String type,
			Callback<SimpleResult> callback);
}
