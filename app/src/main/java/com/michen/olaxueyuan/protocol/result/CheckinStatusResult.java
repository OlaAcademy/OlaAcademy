package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;

/**
 * Created by mingge on 2016/9/27.
 */

public class CheckinStatusResult extends ServiceResult {
    /**
     * message : 成功
     * result : {"status":1,"lastSignIn":"2016-09-26","signInDays":"1"}
     * apicode : 10000
     */

    private String message;
    /**
     * status : 1
     * lastSignIn : 2016-09-26
     * signInDays : 1
     */

    private ResultBean result;
    private int apicode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getApicode() {
        return apicode;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    public static class ResultBean implements Serializable{
        private int status;
        private String lastSignIn;
        private String signInDays;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getLastSignIn() {
            return lastSignIn;
        }

        public void setLastSignIn(String lastSignIn) {
            this.lastSignIn = lastSignIn;
        }

        public String getSignInDays() {
            return signInDays;
        }

        public void setSignInDays(String signInDays) {
            this.signInDays = signInDays;
        }
    }
}
