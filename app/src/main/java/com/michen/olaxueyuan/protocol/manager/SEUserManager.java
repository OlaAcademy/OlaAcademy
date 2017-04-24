package com.michen.olaxueyuan.protocol.manager;

import android.content.Context;
import android.text.TextUtils;

import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.protocol.SECallBack;
import com.michen.olaxueyuan.protocol.model.SEUser;
import com.michen.olaxueyuan.protocol.model.SEUserInfo;
import com.michen.olaxueyuan.protocol.result.CheckInResult;
import com.michen.olaxueyuan.protocol.result.CheckinStatusResult;
import com.michen.olaxueyuan.protocol.result.CoinHistoryResult;
import com.michen.olaxueyuan.protocol.result.MCCommonResult;
import com.michen.olaxueyuan.protocol.result.MCUploadResult;
import com.michen.olaxueyuan.protocol.result.SEUserByPhoneResult;
import com.michen.olaxueyuan.protocol.result.SEUserInfoResult;
import com.michen.olaxueyuan.protocol.result.SEUserResult;
import com.michen.olaxueyuan.protocol.result.ServiceError;
import com.michen.olaxueyuan.protocol.result.SimpleResult;
import com.michen.olaxueyuan.protocol.result.UserAlipayResult;
import com.michen.olaxueyuan.protocol.result.UserBuyGoodsResult;
import com.michen.olaxueyuan.protocol.result.UserCourseCollectResult;
import com.michen.olaxueyuan.protocol.result.UserKnowledgeResult;
import com.michen.olaxueyuan.protocol.result.UserWXpayResult;
import com.michen.olaxueyuan.protocol.result.VipPriceResult;
import com.michen.olaxueyuan.protocol.result.WrongListResult;
import com.michen.olaxueyuan.protocol.service.SEUserService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import static com.michen.olaxueyuan.ui.circle.CircleFragment.type;

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

    public String getUserId() {
        String userId = "";
        if (SEAuthManager.getInstance().isAuthenticated()) {
            userId = SEAuthManager.getInstance().getAccessUser().getId();
        }
        return userId;
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
        _userService.regUser(phone, code, pass, "2", new Callback<SEUserResult>() {
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

    public void modifyUserMe(String name, String realName, String avator, String local, String sex, String descript, String examtype,
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

        _userService.updateUser(currentUser.getId(), name, realName, avator, local, sex, descript, examtype, new Callback<MCCommonResult>() {
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
     * 查询用户信息
     *
     * @param userId
     * @param cb
     */
    public void queryUserInfo(String userId, Callback<SEUserResult> cb) {
        _userService.queryUserInfo(userId, cb);
    }

    /**
     * 知识型谱列表
     *
     * @param type 1 用于考点的课程列表 2 用户视频的课程列表
     * @param cb
     */
    public void getStatisticsList(String type, String userid, Callback<UserKnowledgeResult> cb) {
        _userService.getStatisticsList(type, userid, cb);
    }

    /**
     * 错题集列表
     *
     * @param type        1 考点 2 真题 3 模考
     * @param subjectType 1 数学 2 英语 3 逻辑 4 写作
     * @param userId
     * @param cb
     */
    public void getWrongList(String type, String subjectType, String userId, Callback<WrongListResult> cb) {
        _userService.getWrongList(type, subjectType, userId, cb);
    }

    /**
     * 更新错题集
     *
     * @param userId
     * @param questionType 1 考点 2 模考或真题
     * @param type         1 增加 2 删除
     * @param subjectId
     * @param cb
     */
    public void updateWrongSet(String userId, String questionType, String type, String subjectId, Callback<SimpleResult> cb) {
        _userService.updateWrongSet(userId, questionType, type, subjectId, cb);
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
    public void getAliOrderInfo(String userId, String type, String goodsId, String coin, String version, Callback<UserAlipayResult> cb) {
        _userService.getAliOrderInfo(userId, type, goodsId, coin, version, cb);
    }

    /**
     * 微信支付
     *
     * @param price
     * @param userId
     * @param type    type : 1月度会员 2 年度会员 3 整套视频
     * @param goodsId
     * @param coin
     * @param cb
     */
    public void getWXPayReq(String userId, String type, String goodsId, String coin, String version, Callback<UserWXpayResult> cb) {
        _userService.getWXPayReq(userId, type, goodsId, coin, version, cb);
    }

    /**
     * 后台获取VIP价格
     *
     * @param cb
     */
    public void getVIPPrice(Callback<VipPriceResult> cb) {
        _userService.getVIPPrice(cb);
    }

    /**
     * 获取已购买视频列表
     * 需要userId
     *
     * @param cb
     */
    public void getBuyGoodsList(String userId, Callback<UserBuyGoodsResult> cb) {
        _userService.getBuyGoodsList(userId, cb);
    }

    /**
     * 获取今日签到状态
     *
     * @param userId
     * @param cb
     */
    public void getCheckinStatus(String userId, Callback<CheckinStatusResult> cb) {
        _userService.getCheckinStatus(userId, cb);
    }

    /**
     * 签到
     *
     * @param userId
     * @param cb
     */
    public void checkin(String userId, Callback<CheckInResult> cb) {
        _userService.checkin(userId, cb);
    }

    /**
     * 欧拉币获取及消耗明细
     *
     * @param userId
     * @param cb
     */
    public void coinGetHistoryList(String userId, Callback<CoinHistoryResult> cb) {
        _userService.coinGetHistoryList(userId, cb);
    }

    /**
     * 分享
     *
     * @param userId
     * @param cb
     */
    public void share(String userId, Callback<SimpleResult> cb) {
        _userService.share(userId, cb);
    }

    /**
     * 根据手机号查询用户信息
     *
     * @param phones
     * @param callback
     */
    public void queryUserByPhoneNumbers(String phones, Callback<SEUserByPhoneResult> callback) {
        _userService.queryUserByPhoneNumbers(phones, callback);
    }

    /**
     * 入群送50欧拉币
     *
     * @param userId
     * @param type   type传10
     * @param cb
     */
    public void presentOlaCoin(String userId, String type, Callback<SimpleResult> callback) {
        _userService.presentOlaCoin(userId, type, callback);
    }
}

