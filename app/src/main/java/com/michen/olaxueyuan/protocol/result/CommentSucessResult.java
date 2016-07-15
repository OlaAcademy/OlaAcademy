package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;

/**
 * Created by mingge on 16/7/14.
 */
public class CommentSucessResult implements Serializable {

    /**
     * message : 成功
     * apicode : 10000
     */

    public String message;
    public int apicode;

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
