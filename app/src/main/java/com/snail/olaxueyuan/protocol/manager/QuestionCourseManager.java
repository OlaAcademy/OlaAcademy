package com.snail.olaxueyuan.protocol.manager;

import com.snail.olaxueyuan.protocol.result.QuestionCourseModule;
import com.snail.olaxueyuan.protocol.service.QuestionService;

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
    public void fetchHomeCourseList(String pid, String type, final Callback<QuestionCourseModule> callback) {
        getQuestionService().fetchHomeCourseList(pid, type, callback);
    }
}
