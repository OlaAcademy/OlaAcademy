package com.michen.olaxueyuan.protocol.result;

/**
 * Created by mingge on 2016/9/27.
 */

public class CheckInResult extends ServiceResult {
    /**
     * message : 成功
     * apicode : 10000
     */

    private String message;
    private int apicode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getApicode() {
        return apicode;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }
}
