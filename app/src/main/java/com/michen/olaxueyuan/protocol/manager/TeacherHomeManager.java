package com.michen.olaxueyuan.protocol.manager;

import com.michen.olaxueyuan.protocol.result.CreateGroupResult;
import com.michen.olaxueyuan.protocol.result.HomeworkListResult;
import com.michen.olaxueyuan.protocol.result.SubjectListResult;
import com.michen.olaxueyuan.protocol.result.TeacherGroupListResult;
import com.michen.olaxueyuan.protocol.result.UserGroupListResult;
import com.michen.olaxueyuan.protocol.service.TeacherHomeService;

import retrofit.Callback;

/**
 * Created by mingge on 16/8/31.
 */
public class TeacherHomeManager {
    private static TeacherHomeManager q_instance;
    private TeacherHomeService teacherHomeService;

    private TeacherHomeManager() {
        teacherHomeService = SERestManager.getInstance().create(TeacherHomeService.class);
    }

    public static TeacherHomeManager getInstance() {
        if (q_instance == null) {
            q_instance = new TeacherHomeManager();
        }
        return q_instance;
    }

    public TeacherHomeService getTeacherHomeService() {
        return teacherHomeService;
    }

    /**
     * 创建群
     *
     * @param callback
     */
    public void createGroup(String userId, String name, String avatar, final Callback<CreateGroupResult> callback) {
        getTeacherHomeService().createGroup(userId, name, avatar, callback);
    }

    /**
     * 老师所创建群列表
     *
     * @param userId
     * @param cb
     */
    public void getTeacherGroupList(String userId, final Callback<TeacherGroupListResult> callback) {
        getTeacherHomeService().getTeacherGroupList(userId, callback);
    }

    /**
     * 学生所在群列表
     *
     * @param userId
     * @param cb
     */
    public void getUserGroupList(String userId, final Callback<UserGroupListResult> callback) {
        getTeacherHomeService().getUserGroupList(userId, callback);
    }

    /**
     * 作业列表
     *
     * @param userId
     * @param homeworkId 当前页最后一条的id(否)
     * @param pageSize   每页条数(否)
     * @param cb
     */
    public void getHomeworkList(String userId, String homeworkId, String pageSize, final Callback<HomeworkListResult> callback) {
        getTeacherHomeService().getHomeworkList(userId, homeworkId, pageSize, callback);
    }

    /**
     * 题目列表
     *
     * @param homeworkId
     * @param cb
     */
    public void getSubjectList(String homeworkId, final Callback<SubjectListResult> callback) {
        getTeacherHomeService().getSubjectList(homeworkId, callback);
    }
}
