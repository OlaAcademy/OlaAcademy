package com.snail.olaxueyuan.protocol.manager;

import com.snail.olaxueyuan.protocol.SECallBack;
import com.snail.olaxueyuan.protocol.model.SECart;
import com.snail.olaxueyuan.protocol.model.SECourse;
import com.snail.olaxueyuan.protocol.model.SECourseCate;
import com.snail.olaxueyuan.protocol.model.SECourseDetail;
import com.snail.olaxueyuan.protocol.result.CourseCollectResult;
import com.snail.olaxueyuan.protocol.result.CourseVideoResult;
import com.snail.olaxueyuan.protocol.result.MCBannerResult;
import com.snail.olaxueyuan.protocol.result.MCCollectionResult;
import com.snail.olaxueyuan.protocol.result.MCCommonResult;
import com.snail.olaxueyuan.protocol.result.MCCourseListResult;
import com.snail.olaxueyuan.protocol.result.MCKeywordResult;
import com.snail.olaxueyuan.protocol.result.MCQuestionListResult;
import com.snail.olaxueyuan.protocol.result.MCSearchResult;
import com.snail.olaxueyuan.protocol.result.MCVideoResult;
import com.snail.olaxueyuan.protocol.result.SECartResult;
import com.snail.olaxueyuan.protocol.result.SECourseCateResult;
import com.snail.olaxueyuan.protocol.result.SECourseDetailResult;
import com.snail.olaxueyuan.protocol.result.SECourseResult;
import com.snail.olaxueyuan.protocol.result.ServiceError;
import com.snail.olaxueyuan.protocol.result.VideoCollectionResult;
import com.snail.olaxueyuan.protocol.service.SECourseService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tianxiaopeng on 15-1-10.
 */
public class SECourseManager {


    private static SECourseManager s_instance;
    private ArrayList<SECourseCate> courseCateList = new ArrayList<SECourseCate>();//课程类别列表
    private ArrayList<SECourse> courseList = new ArrayList<SECourse>();  // 具体课程列表
    private ArrayList<SECart> cartList = new ArrayList<SECart>(); //购物车
    private SECourseDetail courseDetail; // 课程详情
    private SECourseService courseService;

    private SECourseManager() {
        courseService = SERestManager.getInstance().create(SECourseService.class);
    }

    public static SECourseManager getInstance() {
        if (s_instance == null) {
            s_instance = new SECourseManager();
        }
        return s_instance;
    }

    public ArrayList<SECourseCate> getCourseCateList() {
        return courseCateList;
    }

    public ArrayList<SECourse> getCourseList() {
        return courseList;
    }

    public ArrayList<SECart> getCartList() {
        return cartList;
    }

    public SECourseDetail getCourseDetail() {
        return courseDetail;
    }

    public SECourseService getCourseService() {
        return courseService;
    }


    /**
     * 课程类别
     *
     * @param callback
     */
    public void refreshCourse(final SECallBack callback) {
        getCourseService().fetchCourse(new Callback<SECourseCateResult>() {
            @Override
            public void success(SECourseCateResult result, Response response) {
                if (result.state) {
                    courseCateList = result.data;
                }
                if (callback != null) {
                    callback.success();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(new ServiceError(error));
                }
            }
        });


    }

    /**
     * 具体课程列表
     *
     * @param callback
     */
    public void refreshCourseList(String free, int tid, int oid, int cid, final SECallBack callback) {
        getCourseService().fetchCourseList(free, tid, oid, cid, new Callback<SECourseResult>() {
            @Override
            public void success(SECourseResult result, Response response) {
                if (result.state) {
                    courseList = result.data;
                }
                if (callback != null) {
                    callback.success();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(new ServiceError(error));
                }
            }
        });
    }

    /**
     * 我的学习进度课程列表
     *
     * @param callback
     */
    public void refreshMyCourseList(int sta, int uid, int page, int limit, final SECallBack callback) {
        getCourseService().fetchMyCourseList(sta, uid, page, limit, new Callback<SECourseResult>() {
            @Override
            public void success(SECourseResult result, Response response) {
                if (result.state) {
                    courseList = result.data;
                }
                if (callback != null) {
                    callback.success();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(new ServiceError(error));
                }
            }
        });
    }

    /**
     * 具体课程信息
     *
     * @param callback
     */
    public void getCourseDetail(int id, int uid, final SECallBack callback) {
        getCourseService().getCourseDetail(id, uid, new Callback<SECourseDetailResult>() {
            @Override
            public void success(SECourseDetailResult result, Response response) {
                if (result.state) {
                    courseDetail = result.data;
                    courseList = result.other;
                }
                if (callback != null) {
                    callback.success();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(new ServiceError(error));
                }
            }
        });


    }

    /**
     * 购物车列表
     *
     * @param callback
     */
    public void fetchCartList(int uid, final SECallBack callback) {
        getCourseService().fetchCartList(uid, new Callback<SECartResult>() {
            @Override
            public void success(SECartResult result, Response response) {
                if (result.state) {
                    cartList = result.data;
                }
                if (callback != null) {
                    callback.success();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(new ServiceError(error));
                }
            }
        });
    }
    //－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 新接口 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－//

    /**
     * 首页轮播图
     *
     * @param callback
     */
    public void fetchHomeBanner(final Callback<MCBannerResult> callback) {
        getCourseService().fetchHomeBanner(new Callback<MCBannerResult>() {
            @Override
            public void success(MCBannerResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });
    }

    /**
     * 课程下的视频列表
     *
     * @param callback
     */
    public void fetchCourseSection(String courseId, String userId, final Callback<CourseVideoResult> callback) {
        getCourseService().fetchCourseSection(courseId, userId, new Callback<CourseVideoResult>() {
            @Override
            public void success(CourseVideoResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });
    }

    /**
     * 获取知识点对应的视频
     *
     * @param callback
     */
    public void fetchVideoInfo(String pointId, final Callback<MCVideoResult> callback) {
        getCourseService().fetchVideoInfo(pointId, new Callback<MCVideoResult>() {
            @Override
            public void success(MCVideoResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });

    }

    /**
     * 首页课程列表
     *
     * @param callback
     */
    public void fetchHomeCourseList(String userId, String pid, String type, final Callback<MCCourseListResult> callback) {
        getCourseService().fetchHomeCourseList(userId, pid, type, new Callback<MCCourseListResult>() {
            @Override
            public void success(MCCourseListResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });
    }

    /**
     * 获取搜索关键词
     *
     * @param callback
     */
    public void fetchKeyWordList(final Callback<MCKeywordResult> callback) {
        getCourseService().fetchKeywordList(new Callback<MCKeywordResult>() {
            @Override
            public void success(MCKeywordResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });
    }

    /**
     * 视频检索
     *
     * @param callback
     */
    public void searchVideoList(String keyword, final Callback<MCSearchResult> callback) {
        getCourseService().searchVideoList(keyword, new Callback<MCSearchResult>() {
            @Override
            public void success(MCSearchResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });

    }

    /**
     * 获取收藏状态
     *
     * @param callback
     */
    public void getCollectionState(String courseId, String type, final Callback<VideoCollectionResult> callback) {
        SEAuthManager am = SEAuthManager.getInstance();
        if (!am.isAuthenticated())
            return;
        getCourseService().getColletionState(am.getAccessUser().getId(), courseId, type, new Callback<VideoCollectionResult>() {
            @Override
            public void success(VideoCollectionResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });

    }

    /**
     * 更新课程观看数量
     *
     * @param callback
     */
    public void updateCourseInfo(String courseId, final Callback<MCCommonResult> callback) {
        getCourseService().updateCourseInfo(courseId, new Callback<MCCommonResult>() {
            @Override
            public void success(MCCommonResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });

    }


    /**
     * 收藏/取消收藏视频
     *
     * @param callback
     */
    public void collectVideo(String collectionId, String state, String userId, String type, final Callback<MCCommonResult> callback) {
        getCourseService().collectVideo(collectionId, state, userId, type, new Callback<MCCommonResult>() {
            @Override
            public void success(MCCommonResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });

    }

    /**
     * 收藏/取消收藏视频
     *
     * @param callback
     */
    public void removeAllCollection(String userId, final Callback<MCCommonResult> callback) {
        getCourseService().removeAllCollection(userId, new Callback<MCCommonResult>() {
            @Override
            public void success(MCCommonResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });

    }

    /**
     * 获取收藏的视频
     *
     * @param callback
     */
    public void fetchCollectionVideoList(String userId, final Callback<MCCollectionResult> callback) {
        getCourseService().fetchCollectionVideoList(userId, new Callback<MCCollectionResult>() {
            @Override
            public void success(MCCollectionResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });

    }

    /**
     * 获取题目列表
     *
     * @param callback
     */
    public void fetchQuestionList(String videoId, final Callback<MCQuestionListResult> callback) {
        getCourseService().fetchQuestionList(videoId, new Callback<MCQuestionListResult>() {
            @Override
            public void success(MCQuestionListResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });

    }

    /**
     * 收藏／取消收藏视频
     *
     * @param userId   用户id
     * @param videoId  视频Id
     * @param courseId 课程id
     * @param state    1 收藏 0 取消
     * @param callback
     */
    public void collectionVideo(String userId, String videoId, String courseId, String state, final Callback<CourseCollectResult> callback) {
        getCourseService().collectionVideo(userId, videoId, courseId, state, new Callback<CourseCollectResult>() {
            @Override
            public void success(CourseCollectResult result, Response response) {
                if (callback != null) {
                    callback.success(result, response);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (callback != null) {
                    callback.failure(error);
                }
            }
        });
    }
}


