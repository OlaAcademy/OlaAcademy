package com.michen.olaxueyuan.protocol.result;

/**
 * Created by mingge on 16/6/19.
 */
public class GoodsOrderStatusResult extends ServiceResult {
    /**
     * message : 成功
     * result : 0
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
