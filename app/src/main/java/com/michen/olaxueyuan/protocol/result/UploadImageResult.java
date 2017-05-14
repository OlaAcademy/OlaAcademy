package com.michen.olaxueyuan.protocol.result;

/**
 * Created by mingge on 17/5/14.
 */

public class UploadImageResult {
    /**
     * message : 成功
     * result : http://qz-picture.oss-cn-beijing.aliyuncs.com/2017/05/cf052d60-bc02-42d8-a26b-bf810dda3c3f.jpg
     * apicode : 10000
     */

    private String message;
    private String result;
    private int apicode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getApicode() {
        return apicode;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }
}
