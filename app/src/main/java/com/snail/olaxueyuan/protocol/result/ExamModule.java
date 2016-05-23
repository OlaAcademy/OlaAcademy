package com.snail.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 16/5/11.
 */
public class ExamModule implements Serializable{
    /**
     * message : 成功
     * result : [{"cid":3,"coverpoint":10,"degree":5,"id":17,"name":"逻辑模考一","target":100,"type":"1","isfree":1,"rank":60}]
     * apicode : 10000
     */

    private String message;
    private int apicode;

    @Override
    public String toString() {
        return "ExamModule{" +
                "message='" + message + '\'' +
                ", apicode=" + apicode +
                ", result=" + result +
                '}';
    }

    /**
     * cid : 3
     * coverpoint : 10
     * degree : 5
     * id : 17
     * name : 逻辑模考一
     * target : 100
     * type : 1
     * isfree : 1
     * rank : 60
     */

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
        private int cid;
        private int coverpoint;
        private int degree;
        private int id;
        private String name;
        private int target;
        private String type;
        private int isfree;
        private int rank;

        @Override
        public String toString() {
            return "ResultBean{" +
                    "cid=" + cid +
                    ", coverpoint=" + coverpoint +
                    ", degree=" + degree +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", target=" + target +
                    ", type='" + type + '\'' +
                    ", isfree=" + isfree +
                    ", rank=" + rank +
                    '}';
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public int getCoverpoint() {
            return coverpoint;
        }

        public void setCoverpoint(int coverpoint) {
            this.coverpoint = coverpoint;
        }

        public int getDegree() {
            return degree;
        }

        public void setDegree(int degree) {
            this.degree = degree;
        }

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

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getIsfree() {
            return isfree;
        }

        public void setIsfree(int isfree) {
            this.isfree = isfree;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }
}
