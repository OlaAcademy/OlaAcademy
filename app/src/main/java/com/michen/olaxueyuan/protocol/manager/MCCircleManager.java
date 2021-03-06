package com.michen.olaxueyuan.protocol.manager;

import com.michen.olaxueyuan.protocol.result.AppointTeacherListResult;
import com.michen.olaxueyuan.protocol.result.MCCommonResult;
import com.michen.olaxueyuan.protocol.result.PraiseCirclePostResult;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.protocol.service.MCCircleService;
import com.snail.photo.upload.UploadResult;

import retrofit.Callback;
import retrofit.mime.TypedFile;

/**
 * Created by tianxiaopeng on 16/7/10.
 */
public class MCCircleManager {

    private static MCCircleManager s_instance;
    private MCCircleService circleService;


    private MCCircleManager() {
        circleService = SERestManager.getInstance().create(MCCircleService.class);
    }

    public static MCCircleManager getInstance() {
        if (s_instance == null) {
            s_instance = new MCCircleManager();
        }
        return s_instance;
    }

    public MCCircleService getCircleService() {
        return circleService;
    }

    /**
     * 欧拉圈发帖
     *
     * @param type
     * @param callback
     */
    public void deployPost(String userId, String title, String content, String imageGids, String location
            , String type, String phoneInfo, String assignUser, String isPublic
            , final Callback<MCCommonResult> callback) {
        getCircleService().addOlaCircle(userId, title, content, imageGids, location, type
                , phoneInfo, assignUser, isPublic, callback);
    }

    /**
     * 帖子点赞
     *
     * @param circleId
     * @param callback
     */
    public void praiseCirclePost(String userId, String circleId, Callback<PraiseCirclePostResult> callback) {
        getCircleService().praiseCirclePost(userId, circleId, callback);
    }

    /**
     * 帖子评论点赞
     *
     * @param userId
     * @param commentId
     * @param callback
     */
    public void praiseComment(String userId, String commentId, Callback<SimpleResult> callback) {
        getCircleService().praiseComment(userId, commentId, callback);
    }

    /**
     * 邀请老师回答列表
     *
     * @param callback
     */
    public void getTeacherList(Callback<AppointTeacherListResult> callback) {
        getCircleService().getTeacherList(callback);
    }
}
