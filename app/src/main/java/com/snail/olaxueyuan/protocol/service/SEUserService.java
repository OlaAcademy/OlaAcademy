package com.snail.olaxueyuan.protocol.service;

import com.snail.olaxueyuan.protocol.result.MCCommonResult;
import com.snail.olaxueyuan.protocol.result.MCUploadResult;
import com.snail.olaxueyuan.protocol.result.SEPasswordResult;
import com.snail.olaxueyuan.protocol.result.SEUserInfoResult;
import com.snail.olaxueyuan.protocol.result.SEUserResult;
import com.snail.olaxueyuan.protocol.result.UserCourseCollectResult;
import com.snail.olaxueyuan.protocol.result.UserKnowledgeResult;

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
     * @param phone code
     * @param pass
     */
    @FormUrlEncoded
    @POST("/ola/user/reg")
    public void regUser(@Field("phone") String phone,
                        @Field("code") String code,
                        @Field("passwd") String pass,
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
}
