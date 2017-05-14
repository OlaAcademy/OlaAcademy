package com.michen.olaxueyuan.protocol.result;

/**
 * Created by mingge on 17/5/14.
 */

public class UploadMediaResult {
    /**
     * message : 成功
     * mediaUrl : http://qz-media.oss-cn-beijing.aliyuncs.com/2017/05/59dea92d-fe48-4221-b08b-8782c921688c.amr
     * thumbUrl : http://qz-media.oss-cn-beijing.aliyuncs.com/2017/05/59dea92d-fe48-4221-b08b-8782c921688c.jpg
     * apicode : 10000
     */

    private String message;
    private String mediaUrl;
    private String thumbUrl;
    private int apicode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public int getApicode() {
        return apicode;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }
}
