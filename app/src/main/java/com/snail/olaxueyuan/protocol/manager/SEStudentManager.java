package com.snail.olaxueyuan.protocol.manager;


import com.snail.olaxueyuan.protocol.SECallBack;
import com.snail.olaxueyuan.protocol.model.SEStudent;
import com.snail.olaxueyuan.protocol.result.SEStudentResult;
import com.snail.olaxueyuan.protocol.result.ServiceError;
import com.snail.olaxueyuan.protocol.service.SEStudentService;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by tianxiaopeng on 15-1-6.
 */
public class SEStudentManager {

    private static SEStudentManager s_instance;
    private ArrayList<SEStudent> studentList = new ArrayList<SEStudent>();
    private SEStudentService seStudentService;

    private SEStudentManager() {
        seStudentService = SERestManager.getInstance().create(SEStudentService.class);
    }

    public static SEStudentManager getInstance() {
        if (s_instance == null) {
            s_instance = new SEStudentManager();
        }
        return s_instance;
    }

    public ArrayList<SEStudent> getStudent() {
        return studentList;
    }

    public SEStudentService getStudentService() {
        return seStudentService;
    }


    public void refreshStudent(int limit, final SECallBack callback) {
        getStudentService().fetchStudent(1, limit, new Callback<SEStudentResult>() {
            @Override
            public void success(SEStudentResult result, Response response) {
                if (result.state) {
                    studentList = result.data;
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

    public void loadMoreStudent(int page, int limit, final SECallBack callback) {
        getStudentService().fetchStudent(page, limit, new Callback<SEStudentResult>() {
            @Override
            public void success(SEStudentResult result, Response response) {
                if (result.state) {
                    studentList = result.data;
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


}


