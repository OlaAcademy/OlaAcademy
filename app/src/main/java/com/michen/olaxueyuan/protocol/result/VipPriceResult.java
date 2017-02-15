package com.michen.olaxueyuan.protocol.result;

/**
 * Created by mingge on 2017/2/15.
 */

public class VipPriceResult {
    /**
     * message : 成功
     * result : {"monthPrice":38,"halfYearPrice":198,"yearPrice":298}
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
         * monthPrice : 38
         * halfYearPrice : 198
         * yearPrice : 298
         */

        private String  monthPrice;
        private String halfYearPrice;
        private String yearPrice;

        public String getMonthPrice() {
            return monthPrice;
        }

        public void setMonthPrice(String monthPrice) {
            this.monthPrice = monthPrice;
        }

        public String getHalfYearPrice() {
            return halfYearPrice;
        }

        public void setHalfYearPrice(String halfYearPrice) {
            this.halfYearPrice = halfYearPrice;
        }

        public String getYearPrice() {
            return yearPrice;
        }

        public void setYearPrice(String yearPrice) {
            this.yearPrice = yearPrice;
        }
    }
}
