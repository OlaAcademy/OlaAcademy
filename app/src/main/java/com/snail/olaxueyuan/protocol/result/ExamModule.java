package com.snail.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 16/5/11.
 */
public class ExamModule implements Serializable{
    /**
     * message : 成功
     * result : [{"id":1,"cid":1,"name":"2016数学真题","target":100,"degree":1,"coverpoint":10,"type":"1"},{"id":2,"cid":1,"name":"2015数学真题","target":88,"degree":1,"coverpoint":23,"type":"1"},{"id":3,"cid":1,"name":"2014数学真题","target":79,"degree":1,"coverpoint":1,"type":"1"},{"id":4,"cid":1,"name":"2013数学真题","target":98,"degree":1,"coverpoint":2,"type":"1"},{"id":5,"cid":1,"name":"2012数学真题","target":95,"degree":1,"coverpoint":3,"type":"1"},{"id":6,"cid":1,"name":"2011数学真题","target":99,"degree":1,"coverpoint":4,"type":"1"},{"id":7,"cid":1,"name":"2010数学真题","target":100,"degree":1,"coverpoint":1,"type":"1"},{"id":8,"cid":1,"name":"2009数学真题","target":100,"degree":1,"coverpoint":1,"type":"1"}]
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
     * id : 1
     * cid : 1
     * name : 2016数学真题
     * target : 100
     * degree : 1
     * coverpoint : 10
     * type : 1
     */

    private List<ResultEntity> result;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public int getApicode() {
        return apicode;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public static class ResultEntity implements Serializable{
        private int id;
        private int cid;
        private String name;
        private int target;
        private int degree;
        private int coverpoint;
        private String type;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public void setDegree(int degree) {
            this.degree = degree;
        }

        public void setCoverpoint(int coverpoint) {
            this.coverpoint = coverpoint;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public int getCid() {
            return cid;
        }

        public String getName() {
            return name;
        }

        public int getTarget() {
            return target;
        }

        public int getDegree() {
            return degree;
        }

        public int getCoverpoint() {
            return coverpoint;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return "ResultEntity{" +
                    "id=" + id +
                    ", cid=" + cid +
                    ", name='" + name + '\'' +
                    ", target=" + target +
                    ", degree=" + degree +
                    ", coverpoint=" + coverpoint +
                    ", type='" + type + '\'' +
                    ", isSelected=" + isSelected +
                    '}';
        }
    }
}
