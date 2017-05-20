package com.michen.olaxueyuan.protocol.service;

import com.michen.olaxueyuan.protocol.result.AccessTokenResult;
import com.michen.olaxueyuan.protocol.result.MCCommonResult;
import com.michen.olaxueyuan.protocol.result.SEUserResult;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by tianxiaopeng on 15-1-6.
 */
public interface AuthService {


    @FormUrlEncoded
    @POST("/ola/user/getYzmByPhone")
    public void requestSMSAuthCode(@Field("mobile") String mobile,
                                   Callback<MCCommonResult> cb);

    /**
     * 手机加验证码登录  （咱未使用）
     *
     * @param cellphone
     * @param clientID
     * @param verificationCode
     * @param cb
     */
    @FormUrlEncoded
    @POST("/auth/sms")
    public void verifySMSAuthCode(@Field("cellphone") String cellphone,
                                  @Field("client_id") String clientID,
                                  @Field("verification_code") String verificationCode,
                                  Callback<AccessTokenResult> cb);

    /**
     * 用户名密码登录
     *
     * @param phone
     * @param passwd
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/user/login")
    public void authWithUsernamePassword(@Field("phone") String phone,
                                         @Field("passwd") String passwd,
                                         Callback<SEUserResult> cb);

    /**
     * 第三方登录
     *
     * @param source
     * @param sourceId
     * @param unionId
     * @param cb
     */
    @FormUrlEncoded
    @POST("/ola/user/loginBySDK")
    public void thirdLogin(
            @Field("source") String source,//QQ  wechat  sinaMicroblog
            @Field("sourceId") String sourceId,
            @Field("unionId") String unionId,
            Callback<SEUserResult> cb);
}

