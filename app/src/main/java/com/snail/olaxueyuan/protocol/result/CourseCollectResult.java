package com.snail.olaxueyuan.protocol.result;

/**
 * Created by mingge on 16/6/15.
 * 视频收藏
 */
public class CourseCollectResult extends ServiceResult {

    /**
     * message : 成功
     * apicode : 10000
     */

    private String message;
    private int apicode;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    public String getMessage() {
        return message;
    }

    public int getApicode() {
        return apicode;
    }
}
