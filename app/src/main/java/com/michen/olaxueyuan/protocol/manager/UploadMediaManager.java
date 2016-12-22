package com.michen.olaxueyuan.protocol.manager;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.protocol.model.AccessToken;
import com.michen.olaxueyuan.protocol.result.VideoUploadResult;
import com.michen.olaxueyuan.protocol.service.UploadService;
import com.snail.photo.upload.UploadResult;

import java.io.IOException;
import java.util.Date;

import retrofit.Callback;
import retrofit.ErrorHandler;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedFile;

/**
 * Created by mingge on 2016/12/14.
 */

public class UploadMediaManager {
    private static UploadMediaManager q_instance;
    private UploadService uploadService;

    private UploadMediaManager() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .registerTypeAdapter(Date.class, new DateTypeAdapter())
                .create();
        ErrorHandler errorHandler = new ErrorHandler() {
            @Override
            public Throwable handleError(RetrofitError retrofitError) {
                if (retrofitError != null && retrofitError.getMessage() != null) {
                    Log.e("retrofit", retrofitError.getMessage());
                }
                if (retrofitError.isNetworkError()) {
                    return RetrofitError.networkError(null, new IOException("网络异常，请检查网络后重试"));
                }
                return retrofitError;
            }
        };
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2141.0 Safari/537.36");
                request.addHeader("Accept", "application/json");
                AccessToken accessToken = SEAuthManager.getInstance().getAccessToken();
                if (accessToken != null) {
                    String tokenValue = accessToken.getTokenValue();
                    if (tokenValue != null) {
                        request.addHeader("Authorization", "Bearer " + tokenValue);
                    }
                }
            }
        };
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SEAPP.MEDIA_BASE_URL)
                .setErrorHandler(errorHandler)
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        uploadService = restAdapter.create(UploadService.class);
    }

    public static UploadMediaManager getInstance() {
        if (q_instance == null) {
            q_instance = new UploadMediaManager();
        }
        return q_instance;
    }

    public UploadService getUploadService() {
        return uploadService;
    }

    /**
     * 上传音频和视频
     *
     * @param movData
     * @param movType mp3、mp4
     * @param cb
     */
    public void movieUpload(TypedFile movData, String movType, Callback<VideoUploadResult> cb) {
        getUploadService().movieUpload(movData, movType, cb);
    }

    /**
     * 上传图片
     *
     * @param imgData
     * @param imgAngle
     * @param width
     * @param height
     * @param picType
     * @param callback
     */
    public void uploadImage(TypedFile imgData, int imgAngle, int width, int height, String picType, Callback<UploadResult> callback) {
        getUploadService().uploadImage(imgData, imgAngle, width, height, picType, callback);
    }
}
