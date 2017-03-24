package com.michen.olaxueyuan.common.manager;

import android.os.Environment;

/**
 * Created by mingge on 2016/12/12.
 */

public class CommonConstant {
    public static final int DEPLOY_POST_APPOINT_TEACHER_FOR_RESULT = 0x1001;//发布帖子跳转到指定回答界面的forResult

    public static String StoragePath = Environment.getExternalStorageDirectory().getPath() + "/.th/image";
    public static String ROOTPATH = Environment.getExternalStorageDirectory().getPath() + "/.th";
    public static String UPLOADTMP = ROOTPATH + "/tmp";
    public static String RECORDPATH = Environment.getExternalStorageDirectory().getPath() + "/.ttmp";
    public static String APPNAME = "file.apk";
    public static final int DISK_CACHE = 512 * 1024 * 1024;//Bytes
    public static final int MEME_CACHE = 100 * 1024 * 1024;//bytes
    //    public static final int NIULANG = -1;
    public static final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();

    public static String RECORD_PATH = ROOTPATH + "/.audio";
    public static final String EXTRA_INDEX_REVIEW = "extra_index_review";

    public static final int CODE_REQUEST_VIDEO_RECORD = 10001;
    public static final int TAKE_PICTURE = 10002;
    public static int DAY_STUDY_TIME = 0;//首页每日学习的时长
    public static final String DAY_STUDY_PREFERENCE = "day_study_time_preference";//首页每日学习的时长sharePreference存储名称
    public static final String DAY_STUDY_TIME_LENGTH = "day_study_time";//首页每日学习的时长sharePreference
    public static final String DAY_STUDY_DATE = "day_study_date_preference";//首页每日学习最后的时间sharePreference
    public static final String QQ_GROUP_KEY = "u5Whiq-Ik7zFDroIeRRPolENgFYi63PF";//qq群的key
    public static final String QQ_GROUP_URI = "mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D";//qq群的uri

    // appId、appKey 可以在「LeanCloud  控制台 > 设置 > 应用 Key」获取
    public static final String LeanCloud_APP_ID = "HSXwUCr9cAVKFARwO1hWLeka-gzGzoHsz";
    public static final String LeanCloud_APP_KEY = "g2oB3U1D1hh2ilVMiT42TU0M";
    public static final String LEAN_CHAT_UNREAD = "lenchat_unread";
    public static final String LEAN_CHAT_UNREAD_KEY = "lenchat_unread_key";
}
