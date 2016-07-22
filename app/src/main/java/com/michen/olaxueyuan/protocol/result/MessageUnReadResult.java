package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;

/**
 * Created by mingge on 16/7/19.
 */
public class MessageUnReadResult implements Serializable {
    /**
     * message : 成功
     * result : 1
     * apicode : 10000
     */

    private String message;
    private int result;
    private int apicode;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    public String getMessage() {
        return message;
    }

    public int getResult() {
        return result;
    }

    public int getApicode() {
        return apicode;
    }
}
