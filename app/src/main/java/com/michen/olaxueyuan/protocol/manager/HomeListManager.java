package com.michen.olaxueyuan.protocol.manager;

import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.michen.olaxueyuan.protocol.result.MaterialListResult;
import com.michen.olaxueyuan.protocol.result.OrganizationInfoResult;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.protocol.service.HomeListService;

import retrofit.Callback;

import static com.michen.olaxueyuan.ui.circle.CircleFragment.type;

/**
 * Created by mingge on 16/4/28.
 */
public class HomeListManager {
    private static HomeListManager q_instance;
    private HomeListService homeListService;

    private HomeListManager() {
        homeListService = SERestManager.getInstance().create(HomeListService.class);
    }

    public static HomeListManager getInstance() {
        if (q_instance == null) {
            q_instance = new HomeListManager();
        }
        return q_instance;
    }

    public HomeListService getHomeListService() {
        return homeListService;
    }

    /**
     * 首页列表
     *
     * @param callback
     */
    public void fetchHomeCourseList(String userId,final Callback<HomeModule> callback) {
        getHomeListService().getHomeList(userId,callback);
    }

    /**
     * 资料列表
     *
     * @param userId     字符串
     * @param materailId 字符串	当前页最后一条的id
     * @param pageSize   字符串	每页条数
     * @param type       字符串	1 数学 2 英语 3 逻辑 4 写作
     * @param cb
     */
    public void getMaterailList(String userId, String materailId, String pageSize,
                                String type, final Callback<MaterialListResult> callback) {
        getHomeListService().getMaterailList(userId, materailId, pageSize, type, callback);
    }

    /**
     * 解锁资料
     *
     * @param userId
     * @param materialId
     * @param cb
     */
    public void unlockMaterial(String userId, String materailId, final Callback<SimpleResult> callback) {
        getHomeListService().unlockMaterial(userId, materailId, callback);
    }

    /**
     * 更新资料的浏览量
     *
     * @param materailId
     * @param callback
     */
    public void updateBrowseCount(String materailId, final Callback<SimpleResult> callback) {
        getHomeListService().updateBrowseCount(materailId, callback);
    }

    /**
     * 我要报名 获取机构及学校信息
     *
     * @param callback
     */
    public void getOrganizationInfo(final Callback<OrganizationInfoResult> callback) {
        getHomeListService().getOrganizationInfo(callback);
    }
}
