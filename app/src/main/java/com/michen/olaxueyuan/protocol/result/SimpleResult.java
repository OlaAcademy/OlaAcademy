package com.michen.olaxueyuan.protocol.result;

/**
 * Created by mingge on 2016/10/27.
 */

public class SimpleResult  {


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
