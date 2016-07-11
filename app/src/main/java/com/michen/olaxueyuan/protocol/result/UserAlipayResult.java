package com.michen.olaxueyuan.protocol.result;

/**
 * Created by mingge on 2016/5/26.
 */
public class UserAlipayResult extends ServiceResult{
    @Override
    public String toString() {
        return "UserAlipayResult{" +
                "message='" + message + '\'' +
                ", result=" + result +
                ", apicode=" + apicode +
                '}';
    }

    /**
     * message : 成功
     * result : {"orderInfo":"partner=\"2088221471342703\"&seller_id=\"developer@olaxueyuan.com\"&out_trade_no=\"0955264704\"&subject=\"123\"&body=\"订单号：0955264704\"&total_fee=\"0.01\"&notify_url=\"http://api.olaxueyuan.com/ola/pay/aliPayCallBack\"&service=\"mobile.securitypay.pay\"&payment_type=\"1\"&_input_charset=\"utf-8\"&it_b_pay=\"1d\"&return_url=\"http://www.tianjiandao.com\"&sign=\"YIBsreiTYyEieuTg7i0LOPwROQG%2BR22E88Yi4GR4LIQYFb8kCr63Yqo769JY%2F0kw8Yo%2FMFKEYEKkfYZtCuSb141ZXDcT74sK%2F01SgmfI7ijoDBsT7q98XnqTqYsyenPN0zmwAygLBVEHtkKXQ1vDXqd8A35q9VilO%2BKZsV5vjcU%3D\"&sign_type=\"RSA\"","orderNo":"0955264704"}
     * apicode : 10000
     */

    private String message;
    /**
     * orderInfo : partner="2088221471342703"&seller_id="developer@olaxueyuan.com"&out_trade_no="0955264704"&subject="123"&body="订单号：0955264704"&total_fee="0.01"&notify_url="http://api.olaxueyuan.com/ola/pay/aliPayCallBack"&service="mobile.securitypay.pay"&payment_type="1"&_input_charset="utf-8"&it_b_pay="1d"&return_url="http://www.tianjiandao.com"&sign="YIBsreiTYyEieuTg7i0LOPwROQG%2BR22E88Yi4GR4LIQYFb8kCr63Yqo769JY%2F0kw8Yo%2FMFKEYEKkfYZtCuSb141ZXDcT74sK%2F01SgmfI7ijoDBsT7q98XnqTqYsyenPN0zmwAygLBVEHtkKXQ1vDXqd8A35q9VilO%2BKZsV5vjcU%3D"&sign_type="RSA"
     * orderNo : 0955264704
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

    public static class ResultBean {
        private String orderInfo;
        private String orderNo;

        @Override
        public String toString() {
            return "ResultBean{" +
                    "orderInfo='" + orderInfo + '\'' +
                    ", orderNo='" + orderNo + '\'' +
                    '}';
        }

        public String getOrderInfo() {
            return orderInfo;
        }

        public void setOrderInfo(String orderInfo) {
            this.orderInfo = orderInfo;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }
    }
}
