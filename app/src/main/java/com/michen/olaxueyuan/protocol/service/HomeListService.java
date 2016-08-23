package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.HomeModule;

import retrofit.Callback;
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
    @POST("/ola/home/getHomeList")
    void getHomeList(Callback<HomeModule> cb);
}
