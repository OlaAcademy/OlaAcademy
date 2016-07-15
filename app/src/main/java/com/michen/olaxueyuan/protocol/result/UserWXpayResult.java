package com.michen.olaxueyuan.protocol.result;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mingge on 2016/5/26.
 */
public class UserWXpayResult extends ServiceResult {
    /**
     * apicode : 10000
     * message : 成功
     * result : {"appid":"wxbb88b0b8e23e130f","noncestr":"fq65RyFPMljlmroV","package":"Sign=WXPay","partnerid":"1315757101","prepayid":"wx201604211423210acdd0c7290282579590","sign":"500C92EA164521C1C8171A924B11480D","timestamp":"1461219800"}
     */

    private int apicode;
    private String message;

    @Override
    public String toString() {
        return "UserWXpayResult{" +
                "apicode=" + apicode +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    /**
     * appid : wxbb88b0b8e23e130f
     * noncestr : fq65RyFPMljlmroV
     * package : Sign=WXPay
     * partnerid : 1315757101
     * prepayid : wx201604211423210acdd0c7290282579590
     * sign : 500C92EA164521C1C8171A924B11480D
     * timestamp : 1461219800
     */

    private ResultBean result;

    public int getApicode() {
        return apicode;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

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

    public static class ResultBean {
        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        @Override
        public String toString() {
            return "ResultBean{" +
                    "appid='" + appid + '\'' +
                    ", noncestr='" + noncestr + '\'' +
                    ", packageX='" + packageX + '\'' +
                    ", partnerid='" + partnerid + '\'' +
                    ", prepayid='" + prepayid + '\'' +
                    ", sign='" + sign + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    '}';
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
