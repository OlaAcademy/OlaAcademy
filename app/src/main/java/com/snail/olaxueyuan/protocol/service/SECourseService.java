package com.snail.olaxueyuan.protocol.service;

import com.snail.olaxueyuan.protocol.result.CourseVideoResult;
import com.snail.olaxueyuan.protocol.result.MCBannerResult;
import com.snail.olaxueyuan.protocol.result.MCCollectionResult;
import com.snail.olaxueyuan.protocol.result.MCCommonResult;
import com.snail.olaxueyuan.protocol.result.MCCourSectionResult;
import com.snail.olaxueyuan.protocol.result.MCCourseListResult;
import com.snail.olaxueyuan.protocol.result.MCKeywordResult;
import com.snail.olaxueyuan.protocol.result.MCQuestionListResult;
import com.snail.olaxueyuan.protocol.result.MCSearchResult;
import com.snail.olaxueyuan.protocol.result.MCVideoResult;
import com.snail.olaxueyuan.protocol.result.SEAddCartResult;
import com.snail.olaxueyuan.protocol.result.SECartResult;
import com.snail.olaxueyuan.protocol.result.SECourseCateResult;
import com.snail.olaxueyuan.protocol.result.SECourseDetailResult;
import com.snail.olaxueyuan.protocol.result.SECourseResult;
import com.snail.olaxueyuan.protocol.result.SEOrderResult;
import com.snail.olaxueyuan.protocol.result.SEWXPayInfoResult;
import com.snail.olaxueyuan.protocol.result.VideoCollectionResult;

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
     * 首页课程列表
     *
     * @param pid  1 数学 2 英语 3 逻辑 4 协作
     * @param type 1 题库课程 2 视频课程
     */
    @FormUrlEncoded
    @POST("/ola/cour/getCourList")
    public void fetchHomeCourseList(@Field("pid") String pid,
                                    @Field("type") String type,
                                    Callback<MCCourseListResult> cb);

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
     * 获取搜索关键词
     *
     * @param cb
     */
    @GET("/ola/cour/getKeywordList")
    public void fetchKeywordList(Callback<MCKeywordResult> cb);


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
}
