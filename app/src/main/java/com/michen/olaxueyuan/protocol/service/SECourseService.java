package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.CommentSucessResult;
import com.michen.olaxueyuan.protocol.result.CourseVideoResult;
import com.michen.olaxueyuan.protocol.result.CourseVieoListResult;
import com.michen.olaxueyuan.protocol.result.MCCollectionResult;
import com.michen.olaxueyuan.protocol.result.MCVideoResult;
import com.michen.olaxueyuan.protocol.result.SECourseCateResult;
import com.michen.olaxueyuan.protocol.result.SEWXPayInfoResult;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.protocol.result.SystemVideoResult;
import com.michen.olaxueyuan.protocol.result.CourseCollectResult;
import com.michen.olaxueyuan.protocol.result.GoodsOrderStatusResult;
import com.michen.olaxueyuan.protocol.result.MCBannerResult;
import com.michen.olaxueyuan.protocol.result.MCCommonResult;
import com.michen.olaxueyuan.protocol.result.MCCourseListResult;
import com.michen.olaxueyuan.protocol.result.MCKeywordResult;
import com.michen.olaxueyuan.protocol.result.MCQuestionListResult;
import com.michen.olaxueyuan.protocol.result.MCSearchResult;
import com.michen.olaxueyuan.protocol.result.SEAddCartResult;
import com.michen.olaxueyuan.protocol.result.SECartResult;
import com.michen.olaxueyuan.protocol.result.SECourseDetailResult;
import com.michen.olaxueyuan.protocol.result.SECourseResult;
import com.michen.olaxueyuan.protocol.result.SEOrderResult;
import com.michen.olaxueyuan.protocol.result.VideoCollectionResult;
import com.michen.olaxueyuan.protocol.result.VideoCourseSubResult;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

/**
 * Created by tianxiaopeng on 15-1-1.
 */
public interface SECourseService {

    /**
     * 课程类别列表
     *
     * @param cb
     */
    @GET("/api/classCageList")
    public void fetchCourse(Callback<SECourseCateResult> cb);


    /**
     * 具体课程列表
     *
     * @param cb
     */
    @GET("/api/classList")
    public void fetchCourseList(@Query("free") String free,
                                @Query("tid") int tid,
                                @Query("oid") int oid,
                                @Query("cid") int cid,
                                Callback<SECourseResult> cb);

    /**
     * 我的学习列表
     *
     * @param cb
     */
    @GET("/api/myLearnList")
    public void fetchMyCourseList(@Query("sta") int sta,
                                  @Query("uid") int uid,
                                  @Query("page") int page,
                                  @Query("limit") int limit,
                                  Callback<SECourseResult> cb);

    /**
     * 具体课程信息
     *
     * @param cb
     */
    @GET("/api/classInfo")
    public void getCourseDetail(@Query("id") int id,
                                @Query("uid") int uid,
                                Callback<SECourseDetailResult> cb);

    /**
     * 添加到购物车
     *
     * @param cb
     */
    @GET("/api/classToCart")
    public void addToCart(@Query("id") String id,
                          @Query("uid") String uid,
                          Callback<SEAddCartResult> cb);

    /**
     * 购物车列表
     *
     * @param cb
     */
    @GET("/api/cartList")
    public void fetchCartList(@Query("uid") int uid,
                              Callback<SECartResult> cb);

    /**
     * 购买课程
     *
     * @param cb
     */
    @GET("/api/classBuy")
    public void buyCourse(@Query("id") String id,
                          @Query("uid") String uid,
                          Callback<SEAddCartResult> cb);

    /**
     * 创建订单
     *
     * @param cb
     */
    @Multipart
    @POST("/api/orderCreate")
    public void createOrder(@Part("uid") int uid,
                            Callback<SEOrderResult> cb);


    /**
     * 获取微信支付的相关信息
     *
     * @param cb
     */
    @GET("/api/apppay")
    public void fetchWXPayInfo(@Query("id") String id,
                               Callback<SEWXPayInfoResult> cb);

    /**
     * 首页轮播图
     *
     * @param cb
     */
    @GET("/ola/cour/getBannerList")
    public void fetchHomeBanner(Callback<MCBannerResult> cb);

    /**
     * 知识点对应的视频
     *
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/cour/getVideoByPoi")
    public void fetchVideoInfo(@Field("pointId") String pointId,
                               Callback<MCVideoResult> cb);


    /**
     * 首页课程列表(旧)
     *
     * @param pid  1 数学 2 英语 3 逻辑 4 协作
     * @param type 1 题库课程 2 视频课程
     */
    @FormUrlEncoded
    @POST("/ola/cour/getCourList")
    public void fetchHomeCourseList(
            @Field("userId") String userId,
            @Field("pid") String pid,
            @Field("type") String type,
            Callback<MCCourseListResult> cb);

    /**
     * 首页课程列表
     *
     * @param pid   1 数学 2 英语 3 逻辑 4 协作
     * @param order 1 默认排序 2 热度排序
     */
    @FormUrlEncoded
    @POST("/ola/cour/getVideoCourseList")
    public void getVideoCourseList(
            @Field("userId") String userId,
            @Field("pid") String pid,
            @Field("order") String type,
            Callback<CourseVieoListResult> cb);

    /**
     * 课程子分类
     *
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/cour/getVideoByPoi")
    public void fetchCourseSection(
            @Field("pointId") String courseId,
            @Field("userId") String userId,
            Callback<CourseVideoResult> cb);

    /**
     * 体系课程下面的视频列表
     *
     * @param gid    goodsId
     * @param userId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/goods/getVideoList")
    void getVideoList(
            @Field("gid") String gid,
            @Field("userId") String userId,
            Callback<SystemVideoResult> cb);


    /**
     * 获取搜索关键词
     *
     * @param cb
     */
    @GET("/ola/cour/getKeywordList")
    public void fetchKeywordList(Callback<MCKeywordResult> cb);

    /**
     * 提交答案
     *
     * @param userId userId
     * @param answer answer
     * @param type   type   1考点 2 模考真题 3 作业
     */
    @FormUrlEncoded
    @POST("/ola/cour/checkAnswer")
    public void submitAnswer(
            @Field("userId") String userId,
            @Field("answer") String answer,
            @Field("type") String type,
            Callback<CommentSucessResult> cb);


    /**
     * 视频检索
     *
     * @param cb
     */
    @Multipart
    @POST("/ola/cour/getVideoByKeyWord")
    public void searchVideoList(@Part("keyword") String keyword,
                                Callback<MCSearchResult> cb);

    /**
     * 获取收藏状态
     *
     * @param cb
     */
    @Multipart
    @POST("/ola/collection/getColletionState")
    public void getColletionState(@Part("userId") String userId,
                                  @Part("collectionId") String collectionId,
                                  @Part("type") String type,
                                  Callback<VideoCollectionResult> cb);

    /**
     * 添加到收藏
     *
     * @param state 收藏／取消
     * @param type  文件夹／课程
     * @param cb
     */
    @Multipart
    @POST("/ola/collection/collectionVideo")
    public void collectVideo(@Part("collectionId") String collectionId,
                             @Part("state") String state,
                             @Part("userId") String userId,
                             @Part("type") String type,
                             Callback<MCCommonResult> cb);

    /**
     * 移除所有收藏
     *
     * @param cb
     */
    @Multipart
    @POST("/ola/collection/removeColletionByUserId")
    public void removeAllCollection(@Part("userId") String userId,
                                    Callback<MCCommonResult> cb);

    /**
     * 更新观看数量
     *
     * @param cb
     */
    @Multipart
    @POST("/ola/cour/updateCourseInfo")
    public void updateCourseInfo(@Part("courseId") String courseId,
                                 Callback<MCCommonResult> cb);

    /**
     * 收藏列表
     *
     * @param cb
     */
    @Multipart
    @POST("/ola/collection/getColletionByUserId")
    public void fetchCollectionVideoList(@Part("userId") String userId,
                                         Callback<MCCollectionResult> cb);

    /**
     * 获取视频列表
     *
     * @param cb
     * @param pointId 课程Id
     */
    @Multipart
    @POST("/ola/cour/getPoiSubList")
    public void fetchQuestionList(@Part("pointId") String pointId,
                                  Callback<MCQuestionListResult> cb);

    /**
     * 收藏／取消收藏视频
     *
     * @param userId   用户id
     * @param videoId  视频Id
     * @param courseId 课程id
     * @param state    1 收藏 0 取消
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/collection/collectionVideo")
    void collectionVideo(
            @Field("userId") String userId,
            @Field("videoId") String videoId,
            @Field("courseId") String courseId,
            @Field("state") String state,
            Callback<CourseCollectResult> cb);

    /**
     * 获取整套视频的购买状态
     *
     * @param userId userId
     * @param gid    goodsId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/goods/getOrderStatus")
    void getOrderStatus(
            @Field("userId") String userId,
            @Field("gid") String gid,
            Callback<GoodsOrderStatusResult> cb);

    /**
     * 视频课程列表
     *
     * @param pid
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/cour/getVideoCourseSubList")
    void getVideoCourseSubList(
            @Field("pid") String pid,
            Callback<VideoCourseSubResult> cb);

    /**
     * 保存视频播放时长
     *
     * @param objectId
     * @param type         1 course 2 goods
     * @param currentIndex 课程或精品课中的第几个视频
     * @param duration     时长 秒为单位
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/watchrecord/recordPlayProgress")
    void recordPlayProgress(
            @Field("userId") String userId,
            @Field("objectId") String objectId,
            @Field("type") String type,
            @Field("currentIndex") String currentIndex,
            @Field("duration") String duration,
            Callback<SimpleResult> cb);

}
