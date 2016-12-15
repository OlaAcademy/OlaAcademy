package com.michen.olaxueyuan.protocol.manager;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.protocol.result.VideoUploadResult;
import com.michen.olaxueyuan.protocol.service.UploadService;

import java.util.Date;

import retrofit.Callback;
import retrofit.ErrorHandler;
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
                return retrofitError;
            }
        };
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(SEAPP.MEDIA_BASE_URL)
                .setErrorHandler(errorHandler)
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

}
