package com.michen.olaxueyuan.protocol.manager;

import com.michen.olaxueyuan.protocol.result.ExamModule;
import com.michen.olaxueyuan.protocol.result.OLaCircleModule;
import com.michen.olaxueyuan.protocol.result.QuestionCourseModule;
import com.michen.olaxueyuan.protocol.service.QuestionService;
import com.michen.olaxueyuan.protocol.result.MCQuestionListResult;

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
    public void fetchHomeCourseList(String userid,String pid, String type, final Callback<QuestionCourseModule> callback) {
        getQuestionService().fetchHomeCourseList(userid,pid, type, callback);
    }

    /**
     * 题库首页
     *
     * @param courseId
     * @param type
     * @param callback
     */
    public void getExamList(String userId,String courseId, String type, final Callback<ExamModule> callback) {
        getQuestionService().getExamList(userId,courseId, type, callback);
    }

    /**
     * 获取题目列表
     *
     * @param callback
     */
    public void fetchExamQuestionList(String examId, final Callback<MCQuestionListResult> callback) {
        getQuestionService().getExamQuestionList(examId,callback);
    }

    /**
     * 欧拉圈，获取视频观看历史记录列表
     *
     * @param callback
     */
    public void getHistotyList(String videoId,String pageSize, final Callback<OLaCircleModule> callback) {
        getQuestionService().getHistotyList(videoId,pageSize,callback);
    }

}
