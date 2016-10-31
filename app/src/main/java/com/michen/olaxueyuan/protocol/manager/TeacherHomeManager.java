package com.michen.olaxueyuan.protocol.manager;

import com.michen.olaxueyuan.protocol.result.AttendGroupResult;
import com.michen.olaxueyuan.protocol.result.CreateGroupResult;
import com.michen.olaxueyuan.protocol.result.HomeworkListResult;
import com.michen.olaxueyuan.protocol.result.HomeworkStatisticsResult;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
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
     * @param type     1、数学2、英语3、逻辑4、写作
     * @param callback
     */
    public void createGroup(String userId, String type, String name, String avatar, final Callback<CreateGroupResult> callback) {
        getTeacherHomeService().createGroup(userId, type, name, avatar, callback);
    }

    /**
     * 老师所创建群列表
     *
     * @param userId
     * @param callback
     */
    public void getTeacherGroupList(String userId, final Callback<TeacherGroupListResult> callback) {
        getTeacherHomeService().getTeacherGroupList(userId, callback);
    }

    /**
     * 学生所在群列表
     *
     * @param userId
     * @param type     1 数学 2 英语 3 逻辑 4 写作
     * @param callback
     */
    public void getUserGroupList(String userId, String type, final Callback<UserGroupListResult> callback) {
        getTeacherHomeService().getUserGroupList(userId, type, callback);
    }

    /**
     * 作业列表
     *
     * @param userId
     * @param type       1 学生 2 老师
     * @param homeworkId 当前页最后一条的id(否)
     * @param pageSize   每页条数(否)
     * @param callback
     */
    public void getHomeworkList(String userId, String type, String homeworkId, String pageSize, final Callback<HomeworkListResult> callback) {
        getTeacherHomeService().getHomeworkList(userId, type, homeworkId, pageSize, callback);
    }

    /**
     * 题目列表
     *
     * @param homeworkId
     * @param callback
     */
    public void getSubjectList(String homeworkId, final Callback<SubjectListResult> callback) {
        getTeacherHomeService().getSubjectList(homeworkId, callback);
    }

    /**
     * 加入／退出群
     *
     * @param userId
     * @param groupId
     * @param type     1 加入 2 退出
     * @param callback
     */
    public void attendGroup(String userId, String groupId, String type, final Callback<AttendGroupResult> callback) {
        getTeacherHomeService().attendGroup(userId, groupId, type, callback);
    }

    /**
     * 发布作业
     *
     * @param name
     * @param groupIds   群id
     * @param subjectIds 字符串	题目Id串 逗号分隔
     * @param cb
     */
    public void deployHomework(String name, String groupIds, String subjectIds, final Callback<SimpleResult> callback) {
        getTeacherHomeService().deployHomework(name, groupIds, subjectIds, callback);
    }

    /**
     * （老师版）学生作业完成情况
     *
     * @param groupId    群id
     * @param homeworkId 该作业的id
     * @param pageIndex  起始页 1
     * @param pageSize
     * @param cb
     */
    public void getHomeworkStatistics(String groupId, String homeworkId, int pageIndex, int pageSize, final Callback<HomeworkStatisticsResult> callback) {
        getTeacherHomeService().getHomeworkStatistics(groupId, homeworkId, pageIndex, pageSize, callback);
    }
}
