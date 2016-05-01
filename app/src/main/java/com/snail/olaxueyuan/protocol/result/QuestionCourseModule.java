package com.snail.olaxueyuan.protocol.result;

import java.util.List;

/**
 * Created by mingge on 16/4/28.
 */
public class QuestionCourseModule {
    /**
     * message : 成功
     * result : {"address":"","id":1,"name":"数学","pid":"0","profile":"数学","subAllNum":6,"type":"1","child":[{"address":"http://www.mykepu.com:8080/pic/course/math/1.png","id":7,"name":"算数","pid":"1","profile":"算数","subAllNum":6,"type":"1","child":[{"address":"","id":53,"name":"算数1-1","pid":"7","profile":"算数1-1","subAllNum":6,"type":"1","subNum":0}],"subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/2.png","id":8,"name":"应用题","pid":"1","profile":"应用题","subAllNum":0,"type":"1","child":[{"address":"","id":58,"name":"应用题1-1","pid":"8","profile":"","subAllNum":4,"type":"1","subNum":0}],"subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/3.png","id":36,"name":"整数","pid":"1","profile":"整数","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/4.png","id":37,"name":"函数","pid":"1","profile":"函数","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/5.png","id":38,"name":"方程与不等式","pid":"1","profile":"方程与不等式","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/6.png","id":39,"name":"数列","pid":"1","profile":"数列","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/7.png","id":40,"name":"平面几何","pid":"1","profile":"平面几何","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/8.png","id":41,"name":"立体几何","pid":"1","profile":"立体几何","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/9.png","id":42,"name":"解析几何","pid":"1","profile":"解析几何","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/10.png","id":44,"name":"排列组合","pid":"1","profile":"排列组合","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/11.png","id":45,"name":"概率","pid":"1","profile":"概率","subAllNum":0,"type":"1","subNum":0}],"subNum":0}
     * apicode : 10000
     */

    private String message;
    /**
     * address :
     * id : 1
     * name : 数学
     * pid : 0
     * profile : 数学
     * subAllNum : 6
     * type : 1
     * child : [{"address":"http://www.mykepu.com:8080/pic/course/math/1.png","id":7,"name":"算数","pid":"1","profile":"算数","subAllNum":6,"type":"1","child":[{"address":"","id":53,"name":"算数1-1","pid":"7","profile":"算数1-1","subAllNum":6,"type":"1","subNum":0}],"subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/2.png","id":8,"name":"应用题","pid":"1","profile":"应用题","subAllNum":0,"type":"1","child":[{"address":"","id":58,"name":"应用题1-1","pid":"8","profile":"","subAllNum":4,"type":"1","subNum":0}],"subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/3.png","id":36,"name":"整数","pid":"1","profile":"整数","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/4.png","id":37,"name":"函数","pid":"1","profile":"函数","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/5.png","id":38,"name":"方程与不等式","pid":"1","profile":"方程与不等式","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/6.png","id":39,"name":"数列","pid":"1","profile":"数列","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/7.png","id":40,"name":"平面几何","pid":"1","profile":"平面几何","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/8.png","id":41,"name":"立体几何","pid":"1","profile":"立体几何","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/9.png","id":42,"name":"解析几何","pid":"1","profile":"解析几何","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/10.png","id":44,"name":"排列组合","pid":"1","profile":"排列组合","subAllNum":0,"type":"1","subNum":0},{"address":"http://www.mykepu.com:8080/pic/course/math/11.png","id":45,"name":"概率","pid":"1","profile":"概率","subAllNum":0,"type":"1","subNum":0}]
     * subNum : 0
     */

    private ResultEntity result;
    private int apicode;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    public String getMessage() {
        return message;
    }

    public ResultEntity getResult() {
        return result;
    }

    public int getApicode() {
        return apicode;
    }

    public static class ResultEntity {
        private String address;
        private int id;
        private String name;
        private String pid;
        private String profile;
        private int subAllNum;
        private String type;
        private int subNum;
        /**
         * address : http://www.mykepu.com:8080/pic/course/math/1.png
         * id : 7
         * name : 算数
         * pid : 1
         * profile : 算数
         * subAllNum : 6
         * type : 1
         * child : [{"address":"","id":53,"name":"算数1-1","pid":"7","profile":"算数1-1","subAllNum":6,"type":"1","subNum":0}]
         * subNum : 0
         */

        private List<ChildEntity> child;

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

        public void setSubNum(int subNum) {
            this.subNum = subNum;
        }

        public void setChild(List<ChildEntity> child) {
            this.child = child;
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

        public int getSubNum() {
            return subNum;
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
            private int subNum;
            /**
             * address :
             * id : 53
             * name : 算数1-1
             * pid : 7
             * profile : 算数1-1
             * subAllNum : 6
             * type : 1
             * subNum : 0
             */

            private List<ChildEntityChild> child;

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

            public void setSubNum(int subNum) {
                this.subNum = subNum;
            }

            public void setChild(List<ChildEntityChild> child) {
                this.child = child;
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

            public int getSubNum() {
                return subNum;
            }

            public List<ChildEntityChild> getChild() {
                return child;
            }

            public static class ChildEntityChild {
                private String address;
                private int id;
                private String name;
                private String pid;
                private String profile;
                private int subAllNum;
                private String type;
                private int subNum;

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

                public void setSubNum(int subNum) {
                    this.subNum = subNum;
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

                public int getSubNum() {
                    return subNum;
                }
            }
        }
    }
}
