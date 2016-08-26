package com.michen.olaxueyuan.protocol.manager;

import com.michen.olaxueyuan.protocol.result.HomeModule;
import com.michen.olaxueyuan.protocol.service.HomeListService;

import retrofit.Callback;

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
    public void fetchHomeCourseList(final Callback<HomeModule> callback) {
        getHomeListService().getHomeList(callback);
    }
}
