package com.michen.olaxueyuan.protocol.result;

import java.util.List;

/**
 * Created by mingge on 2016/11/14.
 */

public class WrongListResult {
    /**
     * message : 成功
     * result : [{"id":9,"name":"2017年联考数学终极预测卷一","subAllNum":25,"wrongNum":4},{"id":10,"name":"2017年联考数学终极预测卷二","subAllNum":25,"wrongNum":4},{"id":11,"name":"2017年联考数学终极预测卷三","subAllNum":25,"wrongNum":0},{"id":12,"name":"2017年联考数学终极预测卷四","subAllNum":25,"wrongNum":0},{"id":13,"name":"2017年联考数学终极预测卷五","subAllNum":25,"wrongNum":0},{"id":14,"name":"2017年联考数学终极预测卷六","subAllNum":25,"wrongNum":0},{"id":15,"name":"2017年联考数学终极预测卷七","subAllNum":25,"wrongNum":0},{"id":16,"name":"2017年联考数学终极预测卷八","subAllNum":25,"wrongNum":0}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * id : 9
         * name : 2017年联考数学终极预测卷一
         * subAllNum : 25
         * wrongNum : 4
         */

        private int id;
        private String name;
        private int subAllNum;
        private int wrongNum;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSubAllNum() {
            return subAllNum;
        }

        public void setSubAllNum(int subAllNum) {
            this.subAllNum = subAllNum;
        }

        public int getWrongNum() {
            return wrongNum;
        }

        public void setWrongNum(int wrongNum) {
            this.wrongNum = wrongNum;
        }
    }
}
