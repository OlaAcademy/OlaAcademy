package com.michen.olaxueyuan.common.manager;

import android.content.Context;

import com.michen.olaxueyuan.app.SEAPP;
import com.michen.olaxueyuan.app.SEConfig;
import com.michen.olaxueyuan.sharesdk.ShareModel;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by mingge on 2016/12/13.
 */

public class SimpleSharePlatformManager {
    private static SimpleSharePlatformManager shareManager;
    private Platform.ShareParams shareParams;
    private PlatformActionListener platformActionListener;
    private Context context;

    private SimpleSharePlatformManager() {
    }

    public static SimpleSharePlatformManager getInstance() {
        if (shareManager == null) {
            shareManager = new SimpleSharePlatformManager();
        }
        return shareManager;
    }

    public void share(Context context, String avatar, String id, String content
            , int position, PlatformActionListener platformActionListener) {
        this.context = context;
        this.platformActionListener = platformActionListener;
        ShareModel model = new ShareModel();
        if (avatar.contains("http://")) {
            model.setImageUrl(avatar);
        } else if (avatar.contains(".")) {
            model.setImageUrl("http://api.olaxueyuan.com/upload/" + avatar);
        } else {
            model.setImageUrl(SEAPP.PIC_BASE_URL + avatar);
        }
        model.setText(content);
        model.setTitle("欧拉学院");
        model.setUrl(SEConfig.getInstance().getAPIBaseURL() + "/circlepost.html?circleId=" + id);
        initShareParams(model);
        shares(position, context);
    }


    /**
     * 分享
     *
     * @param position
     */
    private void shares(int position, Context context) {
        Platform plat = null;
        plat = ShareSDK.getPlatform(context, getPlatform(position));
        if (platformActionListener != null) {
            plat.setPlatformActionListener(platformActionListener);
        }
        if (position == 3)
            shareParams.setTitle("欧拉联考——中国最权威的管理类联考学习平台");
        plat.share(shareParams);
    }

    /**
     * 初始化分享参数
     *
     * @param shareModel
     */
    public void initShareParams(ShareModel shareModel) {
        if (shareModel != null) {
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setShareType(Platform.SHARE_TEXT);
            sp.setShareType(Platform.SHARE_WEBPAGE);

            sp.setTitle(shareModel.getTitle());
            sp.setText(shareModel.getText());
            sp.setUrl(shareModel.getUrl());
            sp.setImageUrl(shareModel.getImageUrl());
            shareParams = sp;
        }
    }

    /**
     * 获取平台
     *
     * @param position
     * @return
     */
    private String getPlatform(int position) {
        String platform = "";
        switch (position) {
            case 0:
                platform = "WechatMoments";
                break;
            case 1:
                platform = "Wechat";
                break;
            case 2:
                platform = "QQ";
                break;
            case 3:
                platform = "SinaWeibo";
                break;
            case 4:
                platform = "QZone";
                break;
            case 5:
                platform = "ShortMessage";
                break;
        }
        return platform;
    }

    /**
     * 分享到QQ空间
     */
    private void qzone() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(shareParams.getTitle());
        sp.setTitleUrl(shareParams.getUrl()); // 标题的超链接
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setComment("我对此分享内容的评论");
        sp.setSite(shareParams.getTitle());
        sp.setSiteUrl(shareParams.getUrl());

        Platform qzone = ShareSDK.getPlatform(context, "QZone");

        qzone.setPlatformActionListener(platformActionListener); // 设置分享事件回调 //
        // 执行图文分享
        qzone.share(sp);
    }

    private void qq() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setTitle(shareParams.getTitle());
        sp.setTitleUrl(shareParams.getUrl()); // 标题的超链接
        sp.setText(shareParams.getText());
        sp.setImageUrl(shareParams.getImageUrl());
        sp.setComment("我对此分享内容的评论");
        sp.setSite(shareParams.getTitle());
        sp.setSiteUrl(shareParams.getUrl());
        Platform qq = ShareSDK.getPlatform(context, "QQ");
        qq.setPlatformActionListener(platformActionListener);
        qq.share(sp);
    }

    /**
     * 分享到短信
     */
    private void shortMessage() {
        Platform.ShareParams sp = new Platform.ShareParams();
        sp.setAddress("");
        sp.setText(shareParams.getText() + "这是网址《" + shareParams.getUrl() + "》很给力哦！");

        Platform circle = ShareSDK.getPlatform(context, "ShortMessage");
        circle.setPlatformActionListener(platformActionListener); // 设置分享事件回调
        // 执行图文分享
        circle.share(sp);
    }

}
