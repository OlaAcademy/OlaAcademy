package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.CheckInResult;
import com.michen.olaxueyuan.protocol.result.CheckinStatusResult;
import com.michen.olaxueyuan.protocol.result.CoinHistoryResult;
import com.michen.olaxueyuan.protocol.result.MCCommonResult;
import com.michen.olaxueyuan.protocol.result.MCUploadResult;
import com.michen.olaxueyuan.protocol.result.SEPasswordResult;
import com.michen.olaxueyuan.protocol.result.SEUserInfoResult;
import com.michen.olaxueyuan.protocol.result.SEUserResult;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.protocol.result.UserAlipayResult;
import com.michen.olaxueyuan.protocol.result.UserBuyGoodsResult;
import com.michen.olaxueyuan.protocol.result.UserCourseCollectResult;
import com.michen.olaxueyuan.protocol.result.UserKnowledgeResult;
import com.michen.olaxueyuan.protocol.result.UserWXpayResult;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by tianxiaopeng on 15-1-10.
 */
public interface SEUserService {


    /**
     * 注册
     *
     * @param phone  code
     * @param pass
     * @param stauts 1 iphone 2 android
     */
    @FormUrlEncoded
    @POST("/ola/user/reg")
    public void regUser(@Field("phone") String phone,
                        @Field("code") String code,
                        @Field("passwd") String pass,
                        @Field("status") String stauts,
                        Callback<SEUserResult> cb);

    /**
     * 查询用户信息
     *
     * @param id
     */
    @FormUrlEncoded
    @POST("/ola/user/queryUser")
    public void queryUserInfo(@Field("id") String id,
                              Callback<SEUserResult> cb);

    /**
     * 找回密码--获取验证码
     *
     * @param user
     * @param cb
     */
    @GET("/api/user/findPass")
    public void findPass(@Query("user") String user,
                         Callback<SEPasswordResult> cb);

    /**
     * 找回密码--密码重置
     *
     * @param phone
     * @param cb
     */
    @GET("/ola/user/modifypasswd")
    public void reMakePass(@Query("phone") String phone,
                           @Query("code") String code,
                           @Query("passwd") String passwd,
                           Callback<SEPasswordResult> cb);


    /**
     * 更新
     *
     * @param uid name say
     * @param cb
     */
    @Multipart
    @POST("/ola/user/updateInfo")
    public void updateUser(@Part("id") String uid,
                           @Part("name") String name,
                           @Part("realName") String realName,
                           @Part("avator") String avator,
                           @Part("local") String local,
                           @Part("sex") String sex,
                           @Part("descript") String descript,
                           Callback<MCCommonResult> cb);

    /**
     * 上传头像
     *
     * @param file
     * @param cb
     */
    @Multipart
    @POST("/ola/user/fileUpload")
    public void updateUserIcon(@Part("file") TypedFile file,
                               Callback<MCUploadResult> cb);


    /**
     * 查询用户的 学习进度 订单 收藏等信息
     *
     * @param uid
     * @param cb
     */
    @GET("/api/user/userHouseCount")
    public void fetchInformation(@Query("uid") int uid,
                                 Callback<SEUserInfoResult> cb);

    /**
     * 蜗牛评测
     *
     * @param cb
     */
    @GET("/app/evaluation")
    public void evaluation(Callback<Response> cb);

    /**
     * 知识型谱列表(需要userid)
     *
     * @param type 1 用于考点的课程列表 2 用户视频的课程列表
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/cour/getStatisticsList")
    void getStatisticsList(@Field("type") String type,
                           @Field("userid") String userid,
                           Callback<UserKnowledgeResult> cb);

    /**
     * 需要userid
     *
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/collection/getCollectionByUserId")
    void getCollectionByUserId(
            @Field("userId") String userid,
            Callback<UserCourseCollectResult> cb);

    /**
     * 支付宝支付
     *
     * @param userId
     * @param type    type : 1月度会员 2 年度会员 3 整套视频
     * @param goodsId
     * @param coin
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/pay/getAliOrderInfo")
    void getAliOrderInfo(
//            @Field("price") String price,
            @Field("userId") String userId,
            @Field("type") String type,
            @Field("goodsId") String goodsId,
            @Field("coin") String coin,
            Callback<UserAlipayResult> cb);

    /**
     * 微信支付
     *
     * @param userId
     * @param type    type : 1月度会员 2 年度会员 3 整套视频
     * @param goodsId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/pay/getWXPayReq")
    void getWXPayReq(
//            @Field("price") String price,
            @Field("userId") String userId,
            @Field("type") String type,
            @Field("goodsId") String goodsId,
            @Field("coin") String coin,
//            @Field("body") String body,
            Callback<UserWXpayResult> cb);

    /**
     * 获取已购买视频列表
     * 需要userid
     *
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/goods/getBuyGoodsList")
    void getBuyGoodsList(
            @Field("userId") String userId,
            Callback<UserBuyGoodsResult> cb);

    /**
     * 获取今日签到状态
     *
     * @param userId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/dailyact/getCheckinStatus")
    void getCheckinStatus(
            @Field("userId") String userId,
            Callback<CheckinStatusResult> cb);

    /**
     * 签到
     *
     * @param userId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/dailyact/checkin")
    void checkin(
            @Field("userId") String userId,
            Callback<CheckInResult> cb);

    /**
     * 欧拉币获取及消耗明细
     *
     * @param userId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/coin/getHistoryList")
    void coinGetHistoryList(
            @Field("userId") String userId,
            Callback<CoinHistoryResult> cb);

    /**
     * 分享
     *
     * @param userId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/dailyact/dailyShare")
    void share(
            @Field("userId") String userId,
            Callback<SimpleResult> cb);

}
