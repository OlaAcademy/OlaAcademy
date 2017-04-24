package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.AttendListResult;
import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.michen.olaxueyuan.protocol.result.MaterialListResult;
import com.michen.olaxueyuan.protocol.result.OrganizationInfoResult;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.protocol.result.TokenInfoResult;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by mingge on 16/4/28.
 */
public interface HomeListService {
    /**
     * 首页列表
     *
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/home/getHomeList")
    void getHomeList(
            @Field("userId") String userId,
            Callback<HomeModule> cb);

    /**
     * 资料列表
     *
     * @param userId     字符串
     * @param materailId 字符串	当前页最后一条的id
     * @param pageSize   字符串	每页条数
     * @param type       字符串	1 数学 2 英语 3 逻辑 4 写作
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/material/getMaterialList")
    void getMaterailList(
            @Field("userId") String userId,
            @Field("materialId") String materialId,
            @Field("pageSize") String pageSize,
            @Field("type") String type,
            Callback<MaterialListResult> cb);

    /**
     * 解锁资料
     *
     * @param userId
     * @param materialId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/exchange/unlockMaterial")
    void unlockMaterial(
            @Field("userId") String userId,
            @Field("materialId") String materialId,
            Callback<SimpleResult> cb);

    /**
     * 更新资料的浏览量
     *
     * @param materialId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/material/updateBrowseCount")
    void updateBrowseCount(
            @Field("materialId") String materialId,
            Callback<SimpleResult> cb);

    /**
     * 我要报名 获取机构及学校信息
     *
     * @param cb
     */
    @POST("/ola/organization/getOrganizationInfo")
    void getOrganizationInfo(
            Callback<OrganizationInfoResult> cb);

    /**
     * 用户登录token信息
     *
     * @param userId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/token/getTokenInfo")
    void getTokenInfo(
            @Field("userId") String userId,
            Callback<TokenInfoResult> cb);

    /**
     * 关注／取消关注
     *
     * @param attendId   关注人Id
     * @param attendedId 被关注人Id
     * @param type       1 关注 2 取消
     * @param callback
     */
    @FormUrlEncoded
    @POST("/ola/attention/attendUser")
    void attendUser(
            @Field("attendId") String attendId,
            @Field("attendedId") String attendedId,
            @Field("type") int type,
            Callback<SimpleResult> callback);

    /**
     * 关注／取消关注
     *
     * @param attendId   关注人Id
     * @param attendedId 被关注人Id
     * @param type       1 关注 2 取消
     * @param curUserId  当前用户userID
     * @param callback
     */
    @FormUrlEncoded
    @POST("/ola/attention/attendUser")
    void attendUser(
            @Field("attendId") String attendId,
            @Field("attendedId") String attendedId,
            @Field("curUserId") String curUserId,
            @Field("type") int type,
            Callback<SimpleResult> callback);

    /**
     * 关注列表
     *
     * @param userId
     * @param callback
     */
    @FormUrlEncoded
    @POST("/ola/attention/queryAttentionList")
    void queryAttentionList(
            @Field("userId") String userId,
            Callback<AttendListResult> callback);

    /**
     * 粉丝列表
     *
     * @param userId
     * @param callback
     */
    @FormUrlEncoded
    @POST("/ola/attention/queryFollowerList")
    void queryFollowerList(
            @Field("userId") String userId,
            Callback<AttendListResult> callback);

}
