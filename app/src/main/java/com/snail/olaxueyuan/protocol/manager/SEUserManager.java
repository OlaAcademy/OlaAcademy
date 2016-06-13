package com.snail.olaxueyuan.protocol.manager;

import android.content.Context;
import android.text.TextUtils;

import com.snail.olaxueyuan.app.SEConfig;
import com.snail.olaxueyuan.protocol.SECallBack;
import com.snail.olaxueyuan.protocol.model.SEUser;
import com.snail.olaxueyuan.protocol.model.SEUserInfo;
import com.snail.olaxueyuan.protocol.result.MCCommonResult;
import com.snail.olaxueyuan.protocol.result.MCUploadResult;
import com.snail.olaxueyuan.protocol.result.SEUserInfoResult;
import com.snail.olaxueyuan.protocol.result.SEUserResult;
import com.snail.olaxueyuan.protocol.result.ServiceError;
import com.snail.olaxueyuan.protocol.result.UserAlipayResult;
import com.snail.olaxueyuan.protocol.result.UserCourseCollectResult;
import com.snail.olaxueyuan.protocol.result.UserKnowledgeResult;
import com.snail.olaxueyuan.protocol.result.UserWXpayResult;
import com.snail.olaxueyuan.protocol.service.SEUserService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by tianxiaopeng on 15-1-10.
 */
public class SEUserManager {


    private final static String USERMANAGER_CONFIG_FILENAME = "usermanager.dat";

    private static SEUserManager s_instance;
    private SEUserService _userService;
    private SEUserInfo userInfo;
    private SEUserResult regResult;
    private MCUploadResult uploadResult;

    private SEUserManager() {
        _userService = SERestManager.getInstance().create(SEUserService.class);
    }

    public static SEUserManager getInstance() {
        if (s_instance == null) {
            s_instance = new SEUserManager();
        }

        return s_instance;
    }

    public SEUserService getUserService() {
        return _userService;
    }

    public SEUserInfo getUserInfo() {
        return userInfo;
    }

    public SEUserResult getRegResult() {
        return regResult;
    }

    public MCUploadResult getUploadResult() {
        return uploadResult;
    }

    public void logout() {
        SEAuthManager.getInstance().clearAccessUser();
        try {
            FileOutputStream fos = SEConfig.getInstance().getContext().openFileOutput(
                    USERMANAGER_CONFIG_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(null);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void regUser(String phone, String code, String pass, final SECallBack callback) {
        _userService.regUser(phone, code, pass, new Callback<SEUserResult>() {
            @Override
            public void success(SEUserResult result, Response response) {
                if (result == null) {
                    if (callback != null) {
                        callback.failure(new ServiceError(-1, "服务器返回数据出错"));
                    }
                    return;
                }

                if (result.error != null) {
                    if (callback != null) {
                        callback.failure(result.error);
                    }
                    return;
                }

                regResult = result;

                if (callback != null) {
                    callback.success();
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                if (callback != null) {
                    callback.failure(new ServiceError(retrofitError));
                }
            }
        });

    }


    public void refreshCurrentUserInfo(final SECallBack callback) {
        SEAuthManager am = SEAuthManager.getInstance();
        if (!am.isAuthenticated()) {
            callback.failure(new ServiceError(-1, "尚未验证身份"));
            return;
        }
        if (SEAuthManager.getInstance().getAccessUser() != null) {
            final int uid = Integer.valueOf(SEAuthManager.getInstance().getAccessUser().getId());
            _userService.fetchInformation(uid, new Callback<SEUserInfoResult>() {
                @Override
                public void success(SEUserInfoResult result, Response response) {
                    if (result == null) {
                        if (callback != null) {
                            callback.failure(new ServiceError(-1, "服务器返回数据出错"));
                        }
                        return;
                    }

                    if (result.error != null) {
                        if (callback != null) {
                            callback.failure(result.error);
                        }
                        return;
                    }

                    userInfo = result.data;

                    if (callback != null) {
                        callback.success();
                    }
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    if (callback != null) {
                        callback.failure(new ServiceError(retrofitError));
                    }
                }
            });

        }
    }

    public void uploadAvatar(final String imagePath, final SECallBack callback) {
        SEAuthManager am = SEAuthManager.getInstance();
        if (!am.isAuthenticated()) {
            callback.failure(new ServiceError(-1, "尚未验证身份"));
            return;
        }

        TypedFile avatarImageTypedFile = null;
        File avatarImageFile = new File(imagePath);
        avatarImageTypedFile = new TypedFile("application/octet-stream", avatarImageFile);
        _userService.updateUserIcon(avatarImageTypedFile, new Callback<MCUploadResult>() {
            @Override
            public void success(MCUploadResult result, Response response) {
                if (result == null) {
                    if (callback != null) {
                        callback.failure(new ServiceError(-1, "服务器返回数据出错"));
                    }
                    return;
                }

                if (result.error != null) {
                    if (callback != null) {
                        callback.failure(result.error);
                    }
                    return;
                }

                uploadResult = result;

                if (callback != null) {
                    callback.success();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void modifyUserMe(String name, String avator, String local, String sex, String descript,
                             final SECallBack callback) {
        SEAuthManager am = SEAuthManager.getInstance();
        if (!am.isAuthenticated()) {
            callback.failure(new ServiceError(-1, "尚未验证身份"));
            return;
        }

        SEUser currentUser = SEAuthManager.getInstance().getAccessUser();

        if (TextUtils.isEmpty(avator)) {
            avator = currentUser.getAvator();
        }

        _userService.updateUser(currentUser.getId(), name, avator, local, sex, descript, new Callback<MCCommonResult>() {
            @Override
            public void success(MCCommonResult result, Response response) {
                if (result == null) {
                    if (callback != null) {
                        callback.failure(new ServiceError(-1, "服务器返回数据出错"));
                    }
                    return;
                }

                if (result.error != null) {
                    if (callback != null) {
                        callback.failure(result.error);
                    }
                    return;
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
     * 知识型谱列表
     *
     * @param type 1 用于考点的课程列表 2 用户视频的课程列表
     * @param cb
     */
    public void getStatisticsList(String type, Callback<UserKnowledgeResult> cb) {
        _userService.getStatisticsList(type, cb);
    }

    /**
     * 需要userid
     *
     * @param cb
     */
    public void getCollectionByUserId(String userid, Callback<UserCourseCollectResult> cb) {
        _userService.getCollectionByUserId(userid, cb);
    }

    /**
     * 支付宝支付
     *
     * @param price
     * @param userId
     * @param type    type : 1月度会员 2 年度会员 3 整套视频
     * @param goodsId
     * @param cb
     */
    public void getAliOrderInfo(String price, String userId, String type, String goodsId, String body, Callback<UserAlipayResult> cb) {
        _userService.getAliOrderInfo(price, userId, type, goodsId, body, cb);
    }

    /**
     * 微信支付
     *
     * @param price
     * @param userId
     * @param type    type : 1月度会员 2 年度会员 3 整套视频
     * @param goodsId
     * @param cb
     */
    public void getWXPayReq(String price, String userId, String type, String goodsId, String body, Callback<UserWXpayResult> cb) {
        _userService.getWXPayReq(price, userId, type, goodsId, body, cb);
    }
}

