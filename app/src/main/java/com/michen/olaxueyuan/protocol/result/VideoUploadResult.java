package com.michen.olaxueyuan.protocol.result;

/**
 * Created by mingge on 2016/12/14.
 */

public class VideoUploadResult {
    /**
     * code : 1
     * message : 上传成功
     * url : movie/2016/12/14/de8224e1-2ff0-4248-8a43-0f003f870e17.mp3
     * gid : de8224e1-2ff0-4248-8a43-0f003f870e17
     */

    private int code;
    private String message;
    private String url;
    private String gid;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }
}
