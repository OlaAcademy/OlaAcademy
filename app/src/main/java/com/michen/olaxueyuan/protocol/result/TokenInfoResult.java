package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;

/**
 * <pre>
 *     author : mingge
 *     time   : 2017/03/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TokenInfoResult implements Serializable {

    @Override
    public String toString() {
        return "TokenInfoResult{" +
                "message='" + message + '\'' +
                ", result=" + result +
                ", apicode=" + apicode +
                '}';
    }

    /**
     * message : 成功
     * result : {"id":0,"userId":381,"token":15311450124}
     * apicode : 10000
     */

    private String message;
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

    public static class ResultBean {
        /**
         * id : 0
         * userId : 381
         * token : 15311450124
         */

        private String id;
        private String userId;
        private String token;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
