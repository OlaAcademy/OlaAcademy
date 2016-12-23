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
}
