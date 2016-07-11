package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.MCOrgListResult;
import com.michen.olaxueyuan.protocol.result.MCTeacherResult;
import com.michen.olaxueyuan.protocol.result.MCCheckInfoResult;
import com.michen.olaxueyuan.protocol.result.MCCommonResult;
import com.michen.olaxueyuan.protocol.result.MCEnrollListResult;
import com.michen.olaxueyuan.protocol.result.SystemCourseResult;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;

/**
 * Created by tianxiaopeng on 15/12/20.
 */
public interface MCOrgService {
    /**
     * 服务机构列表
     *
     * @param cb
     */
    @Multipart
    @POST("/ola/organization/getAllOrganization")
    public void fetchOrganizationList(@Part("userId") String userId,
                                      Callback<MCOrgListResult> cb);

    /**
     * 更新机构关注数量
     *
     * @param cb
     */
    @Multipart
    @POST("/ola/organization/updateAttendCount")
    public void updateAttendCount(@Part("orgId") String orgId,
                                  Callback<MCCommonResult> cb);

    /**
     * 获取报名状态
     *
     * @param cb
     */
    @Multipart
    @POST("/ola/organization/getInfoByUserPhoneAndOrg")
    public void getEnrollInfo(@Part("userPhone") String userPhone,
                              @Part("orgId") String orgId,
                              Callback<MCCheckInfoResult> cb);

    /**
     * 获取报名列表
     *
     * @param cb
     */
    @Multipart
    @POST("/ola/organization/getListByPhone")
    public void fetchEnrollList(@Part("userPhone") String userPhone,
                                Callback<MCEnrollListResult> cb);

    /**
     * 删除报名信息
     *
     * @param cb
     */
    @Multipart
    @POST("/ola/organization/cancelCheckIn")
    public void removeEnroll(@Part("checkId") String checkId,
                             Callback<MCCommonResult> cb);

    /**
     * 更新机构报名数量
     *
     * @param cb
     */
    @Multipart
    @POST("/ola/organization/updateCheckInCount")
    public void updateEnrollCount(@Part("orgId") String orgId,
                                  @Part("type") String type,
                                  Callback<MCCommonResult> cb);

    /**
     * 机构教师列表
     *
     * @param cb
     */
    @Multipart
    @POST("/ola/organization/getTeacherListByOrgId")
    public void fetchTeacherList(@Part("orgId") String orgId,
                                 Callback<MCTeacherResult> cb);

    /**
     * 报名
     *
     * @param cb
     */
    @Multipart
    @POST("/ola/organization/checkin")
    public void enroll(@Part("orgId") String orgId,
                       @Part("userPhone") String userPhone,
                       @Part("userLocal") String userLocal,
                       @Part("type") String type,
                       @Part("checkinTime") String checkinTime,
                       Callback<MCCommonResult> cb);

    /**
     * 获取整套视频或题库列表
     * @param type  1 视频 2 题库
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/goods/getGoodsList")
    void getGoodsList(@Field("type") String type,
                      Callback<SystemCourseResult> cb);
}
