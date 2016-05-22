package com.snail.olaxueyuan.protocol.result;

import java.util.List;

/**
 * Created by mingge on 16/5/22.
 */
public class UserKnowledgeResult extends ServiceResult {

    /**
     * message : 成功
     * result : [{"id":1,"name":"数学","child":[{"address":"http://www.mykepu.com:8080/pic/course/math/1.png","id":7,"name":"算数","pid":"1","profile":"算数","subAllNum":6,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/2.png","id":8,"name":"应用题","pid":"1","profile":"应用题","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/3.png","id":36,"name":"整数","pid":"1","profile":"整数","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/4.png","id":37,"name":"函数","pid":"1","profile":"函数","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/5.png","id":38,"name":"方程与不等式","pid":"1","profile":"方程与不等式","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/6.png","id":39,"name":"数列","pid":"1","profile":"数列","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/7.png","id":40,"name":"平面几何","pid":"1","profile":"平面几何","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/8.png","id":41,"name":"立体几何","pid":"1","profile":"立体几何","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/9.png","id":42,"name":"解析几何","pid":"1","profile":"解析几何","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/10.png","id":44,"name":"排列组合","pid":"1","profile":"排列组合","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/11.png","id":45,"name":"概率","pid":"1","profile":"概率","subAllNum":0,"type":"1"}]},{"id":2,"name":"英语","child":[{"address":"http://www.mykepu.com:8080/pic/course/english/1.png","id":10,"name":"核心词汇","pid":"2","profile":"核心词汇","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/english/9.png","id":25,"name":"阅读","pid":"2","profile":"阅读","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/english/2.png","id":28,"name":"长难句分析","pid":"2","profile":"长难句分析","subAllNum":0,"type":"1"}]},{"id":3,"name":"逻辑","child":[{"address":"http://www.mykepu.com:8080/pic/course/logic/2.png","id":29,"name":"分析推理","pid":"3","profile":"分析推理","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/logic/1.png","id":30,"name":"日常推理","pid":"3","profile":"日常推理","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/logic/3.png","id":31,"name":"形式逻辑","pid":"3","profile":"形式逻辑","subAllNum":0,"type":"1"}]},{"id":4,"name":"写作","child":[{"address":"http://www.mykepu.com:8080/pic/course/write/1.png","id":32,"name":"论证有效性分析","pid":"4","profile":"论证有效性分析","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/write/2.png","id":33,"name":"论说文","pid":"4","profile":"论说文","subAllNum":0,"type":"1"}]},{"id":5,"name":"面试","child":[{"address":"http://www.mykepu.com:8080/pic/course/interview/1.png","id":46,"name":"面试","pid":"5","profile":"面试","subAllNum":0,"type":"1"}]}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * id : 1
     * name : 数学
     * child : [{"address":"http://www.mykepu.com:8080/pic/course/math/1.png","id":7,"name":"算数","pid":"1","profile":"算数","subAllNum":6,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/2.png","id":8,"name":"应用题","pid":"1","profile":"应用题","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/3.png","id":36,"name":"整数","pid":"1","profile":"整数","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/4.png","id":37,"name":"函数","pid":"1","profile":"函数","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/5.png","id":38,"name":"方程与不等式","pid":"1","profile":"方程与不等式","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/6.png","id":39,"name":"数列","pid":"1","profile":"数列","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/7.png","id":40,"name":"平面几何","pid":"1","profile":"平面几何","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/8.png","id":41,"name":"立体几何","pid":"1","profile":"立体几何","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/9.png","id":42,"name":"解析几何","pid":"1","profile":"解析几何","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/10.png","id":44,"name":"排列组合","pid":"1","profile":"排列组合","subAllNum":0,"type":"1"},{"address":"http://www.mykepu.com:8080/pic/course/math/11.png","id":45,"name":"概率","pid":"1","profile":"概率","subAllNum":0,"type":"1"}]
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

    public static class ResultEntity {
        private int id;
        private String name;
        /**
         * address : http://www.mykepu.com:8080/pic/course/math/1.png
         * id : 7
         * name : 算数
         * pid : 1
         * profile : 算数
         * subAllNum : 6
         * type : 1
         */

        private List<ChildEntity> child;

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setChild(List<ChildEntity> child) {
            this.child = child;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public List<ChildEntity> getChild() {
            return child;
        }

        public static class ChildEntity {
            private String address;
            private int id;
            private String name;
            private String pid;
            private String profile;
            private int subAllNum;
            private String type;

            public void setAddress(String address) {
                this.address = address;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public void setProfile(String profile) {
                this.profile = profile;
            }

            public void setSubAllNum(int subAllNum) {
                this.subAllNum = subAllNum;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAddress() {
                return address;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getPid() {
                return pid;
            }

            public String getProfile() {
                return profile;
            }

            public int getSubAllNum() {
                return subAllNum;
            }

            public String getType() {
                return type;
            }
        }
    }
}
