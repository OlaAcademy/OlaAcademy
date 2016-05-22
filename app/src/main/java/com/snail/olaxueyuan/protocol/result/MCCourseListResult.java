package com.snail.olaxueyuan.protocol.result;

import com.google.gson.annotations.SerializedName;
import com.snail.olaxueyuan.protocol.model.MCCourse;

/**
 * Created by tianxiaopeng on 15/12/21.
 */
public class MCCourseListResult extends ServiceResult {
/*
    */
/**
 * message : 成功
 * result : {"address":"","id":1,"isBanner":0,"name":"数学","pid":"0","profile":"数学","subAllNum":6,"totalTime":"","type":"1","child":[{"address":"","id":47,"isBanner":0,"name":"基础课程","pid":"1","profile":"","subAllNum":0,"totalTime":"","type":"2","child":[{"address":"http://math.ufile.ucloud.com.cn/basicmath1.jpg","id":54,"isBanner":0,"name":"算数","pid":"47","profile":"算数","subAllNum":20,"totalTime":"136分钟","type":"2","subNum":0,"playcount":1023},{"address":"http://math.ufile.ucloud.com.cn/basicmath2.jpg","id":55,"isBanner":0,"name":"应用题","pid":"47","profile":"","subAllNum":15,"totalTime":"108分钟","type":"2","subNum":0,"playcount":1005},{"address":"http://math.ufile.ucloud.com.cn/basicmath3.jpg","id":59,"isBanner":0,"name":"代数","pid":"47","profile":"","subAllNum":16,"totalTime":"77分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath5.jpg","id":61,"isBanner":1,"name":"方程与不等式","pid":"47","profile":"","subAllNum":8,"totalTime":"63分钟","type":"2","subNum":0,"playcount":1004},{"address":"http://math.ufile.ucloud.com.cn/basicmath6.jpg","id":62,"isBanner":0,"name":"数列","pid":"47","profile":"","subAllNum":9,"totalTime":"68分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath7.jpg","id":63,"isBanner":0,"name":"平面几何","pid":"47","profile":"","subAllNum":6,"totalTime":"56分钟","type":"2","subNum":0,"playcount":1001},{"address":"http://math.ufile.ucloud.com.cn/basicmath8.jpg","id":64,"isBanner":0,"name":"立体几何","pid":"47","profile":"","subAllNum":3,"totalTime":"24分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath9.jpg","id":65,"isBanner":0,"name":"解析几何","pid":"47","profile":"","subAllNum":10,"totalTime":"81分钟","type":"2","subNum":0,"playcount":1004},{"address":"http://math.ufile.ucloud.com.cn/basicmath10.jpg","id":66,"isBanner":0,"name":"排列组成","pid":"47","profile":"","subAllNum":7,"totalTime":"52分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath4.jpg","id":69,"isBanner":0,"name":"概率","pid":"47","profile":"","subAllNum":6,"totalTime":"39分钟","type":"2","subNum":0,"playcount":1000}],"subNum":0},{"address":"","id":51,"isBanner":0,"name":"系统课程","pid":"1","profile":"","subAllNum":0,"totalTime":"","type":"2","child":[{"address":"http://math.ufile.ucloud.com.cn/systemmath1.jpg","id":56,"isBanner":0,"name":"敬请期待","pid":"51","profile":"","subAllNum":0,"totalTime":"","type":"2","subNum":0,"playcount":1000}],"subNum":0},{"address":"","id":52,"isBanner":0,"name":"串讲课程","pid":"1","profile":"","subAllNum":0,"totalTime":"","type":"2","child":[{"address":"http://math.ufile.ucloud.com.cn/basicmath1.jpg","id":60,"isBanner":0,"name":"算数考点预测及拿分策略","pid":"52","profile":"","subAllNum":2,"totalTime":"48分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath2.jpg","id":67,"isBanner":0,"name":"应用题考点预测及拿分策略","pid":"52","profile":"","subAllNum":2,"totalTime":"42分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath3.jpg","id":68,"isBanner":0,"name":"代数与数列考点预测及拿分策略","pid":"52","profile":"","subAllNum":2,"totalTime":"54分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath7.jpg","id":70,"isBanner":0,"name":"几何考点预测及拿分策略","pid":"52","profile":"","subAllNum":2,"totalTime":"84分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath4.jpg","id":71,"isBanner":0,"name":"数据分析考点预测及拿分策略","pid":"52","profile":"","subAllNum":1,"totalTime":"38分钟","type":"2","subNum":0,"playcount":1000}],"subNum":0}],"subNum":0}
 * apicode : 10000
 *//*


    private String message;
    */
/**
 * address :
 * id : 1
 * isBanner : 0
 * name : 数学
 * pid : 0
 * profile : 数学
 * subAllNum : 6
 * totalTime :
 * type : 1
 * child : [{"address":"","id":47,"isBanner":0,"name":"基础课程","pid":"1","profile":"","subAllNum":0,"totalTime":"","type":"2","child":[{"address":"http://math.ufile.ucloud.com.cn/basicmath1.jpg","id":54,"isBanner":0,"name":"算数","pid":"47","profile":"算数","subAllNum":20,"totalTime":"136分钟","type":"2","subNum":0,"playcount":1023},{"address":"http://math.ufile.ucloud.com.cn/basicmath2.jpg","id":55,"isBanner":0,"name":"应用题","pid":"47","profile":"","subAllNum":15,"totalTime":"108分钟","type":"2","subNum":0,"playcount":1005},{"address":"http://math.ufile.ucloud.com.cn/basicmath3.jpg","id":59,"isBanner":0,"name":"代数","pid":"47","profile":"","subAllNum":16,"totalTime":"77分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath5.jpg","id":61,"isBanner":1,"name":"方程与不等式","pid":"47","profile":"","subAllNum":8,"totalTime":"63分钟","type":"2","subNum":0,"playcount":1004},{"address":"http://math.ufile.ucloud.com.cn/basicmath6.jpg","id":62,"isBanner":0,"name":"数列","pid":"47","profile":"","subAllNum":9,"totalTime":"68分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath7.jpg","id":63,"isBanner":0,"name":"平面几何","pid":"47","profile":"","subAllNum":6,"totalTime":"56分钟","type":"2","subNum":0,"playcount":1001},{"address":"http://math.ufile.ucloud.com.cn/basicmath8.jpg","id":64,"isBanner":0,"name":"立体几何","pid":"47","profile":"","subAllNum":3,"totalTime":"24分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath9.jpg","id":65,"isBanner":0,"name":"解析几何","pid":"47","profile":"","subAllNum":10,"totalTime":"81分钟","type":"2","subNum":0,"playcount":1004},{"address":"http://math.ufile.ucloud.com.cn/basicmath10.jpg","id":66,"isBanner":0,"name":"排列组成","pid":"47","profile":"","subAllNum":7,"totalTime":"52分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath4.jpg","id":69,"isBanner":0,"name":"概率","pid":"47","profile":"","subAllNum":6,"totalTime":"39分钟","type":"2","subNum":0,"playcount":1000}],"subNum":0},{"address":"","id":51,"isBanner":0,"name":"系统课程","pid":"1","profile":"","subAllNum":0,"totalTime":"","type":"2","child":[{"address":"http://math.ufile.ucloud.com.cn/systemmath1.jpg","id":56,"isBanner":0,"name":"敬请期待","pid":"51","profile":"","subAllNum":0,"totalTime":"","type":"2","subNum":0,"playcount":1000}],"subNum":0},{"address":"","id":52,"isBanner":0,"name":"串讲课程","pid":"1","profile":"","subAllNum":0,"totalTime":"","type":"2","child":[{"address":"http://math.ufile.ucloud.com.cn/basicmath1.jpg","id":60,"isBanner":0,"name":"算数考点预测及拿分策略","pid":"52","profile":"","subAllNum":2,"totalTime":"48分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath2.jpg","id":67,"isBanner":0,"name":"应用题考点预测及拿分策略","pid":"52","profile":"","subAllNum":2,"totalTime":"42分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath3.jpg","id":68,"isBanner":0,"name":"代数与数列考点预测及拿分策略","pid":"52","profile":"","subAllNum":2,"totalTime":"54分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath7.jpg","id":70,"isBanner":0,"name":"几何考点预测及拿分策略","pid":"52","profile":"","subAllNum":2,"totalTime":"84分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath4.jpg","id":71,"isBanner":0,"name":"数据分析考点预测及拿分策略","pid":"52","profile":"","subAllNum":1,"totalTime":"38分钟","type":"2","subNum":0,"playcount":1000}],"subNum":0}]
 * subNum : 0
 *//*


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
        private int isBanner;
        private String name;
        private String pid;
        private String profile;
        private int subAllNum;
        private String totalTime;
        private String type;
        private int subNum;
        */
/**
 * address :
 * id : 47
 * isBanner : 0
 * name : 基础课程
 * pid : 1
 * profile :
 * subAllNum : 0
 * totalTime :
 * type : 2
 * child : [{"address":"http://math.ufile.ucloud.com.cn/basicmath1.jpg","id":54,"isBanner":0,"name":"算数","pid":"47","profile":"算数","subAllNum":20,"totalTime":"136分钟","type":"2","subNum":0,"playcount":1023},{"address":"http://math.ufile.ucloud.com.cn/basicmath2.jpg","id":55,"isBanner":0,"name":"应用题","pid":"47","profile":"","subAllNum":15,"totalTime":"108分钟","type":"2","subNum":0,"playcount":1005},{"address":"http://math.ufile.ucloud.com.cn/basicmath3.jpg","id":59,"isBanner":0,"name":"代数","pid":"47","profile":"","subAllNum":16,"totalTime":"77分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath5.jpg","id":61,"isBanner":1,"name":"方程与不等式","pid":"47","profile":"","subAllNum":8,"totalTime":"63分钟","type":"2","subNum":0,"playcount":1004},{"address":"http://math.ufile.ucloud.com.cn/basicmath6.jpg","id":62,"isBanner":0,"name":"数列","pid":"47","profile":"","subAllNum":9,"totalTime":"68分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath7.jpg","id":63,"isBanner":0,"name":"平面几何","pid":"47","profile":"","subAllNum":6,"totalTime":"56分钟","type":"2","subNum":0,"playcount":1001},{"address":"http://math.ufile.ucloud.com.cn/basicmath8.jpg","id":64,"isBanner":0,"name":"立体几何","pid":"47","profile":"","subAllNum":3,"totalTime":"24分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath9.jpg","id":65,"isBanner":0,"name":"解析几何","pid":"47","profile":"","subAllNum":10,"totalTime":"81分钟","type":"2","subNum":0,"playcount":1004},{"address":"http://math.ufile.ucloud.com.cn/basicmath10.jpg","id":66,"isBanner":0,"name":"排列组成","pid":"47","profile":"","subAllNum":7,"totalTime":"52分钟","type":"2","subNum":0,"playcount":1000},{"address":"http://math.ufile.ucloud.com.cn/basicmath4.jpg","id":69,"isBanner":0,"name":"概率","pid":"47","profile":"","subAllNum":6,"totalTime":"39分钟","type":"2","subNum":0,"playcount":1000}]
 * subNum : 0
 *//*


        private List<ChildEntity> child;

        public void setAddress(String address) {
            this.address = address;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setIsBanner(int isBanner) {
            this.isBanner = isBanner;
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

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
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

        public int getIsBanner() {
            return isBanner;
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

        public String getTotalTime() {
            return totalTime;
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
            private int isBanner;
            private String name;
            private String pid;
            private String profile;
            private int subAllNum;
            private String totalTime;
            private String type;
            private int subNum;
            */
    /**
     * address : http://math.ufile.ucloud.com.cn/basicmath1.jpg
     * id : 54
     * isBanner : 0
     * name : 算数
     * pid : 47
     * profile : 算数
     * subAllNum : 20
     * totalTime : 136分钟
     * type : 2
     * subNum : 0
     * playcount : 1023
     *//*


            @SerializedName("child")
            private List<ChildChildEntity> childChild;

            public void setAddress(String address) {
                this.address = address;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setIsBanner(int isBanner) {
                this.isBanner = isBanner;
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

            public void setTotalTime(String totalTime) {
                this.totalTime = totalTime;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setSubNum(int subNum) {
                this.subNum = subNum;
            }

            public void setChildChild(List<ChildChildEntity> childChild) {
                this.childChild = childChild;
            }

            public String getAddress() {
                return address;
            }

            public int getId() {
                return id;
            }

            public int getIsBanner() {
                return isBanner;
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

            public String getTotalTime() {
                return totalTime;
            }

            public String getType() {
                return type;
            }

            public int getSubNum() {
                return subNum;
            }

            public List<ChildChildEntity> getChildChild() {
                return childChild;
            }

            public static class ChildChildEntity {
                private String address;
                private int id;
                private int isBanner;
                private String name;
                private String pid;
                private String profile;
                private int subAllNum;
                private String totalTime;
                private String type;
                private int subNum;
                private int playcount;

                public void setAddress(String address) {
                    this.address = address;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public void setIsBanner(int isBanner) {
                    this.isBanner = isBanner;
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

                public void setTotalTime(String totalTime) {
                    this.totalTime = totalTime;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public void setSubNum(int subNum) {
                    this.subNum = subNum;
                }

                public void setPlaycount(int playcount) {
                    this.playcount = playcount;
                }

                public String getAddress() {
                    return address;
                }

                public int getId() {
                    return id;
                }

                public int getIsBanner() {
                    return isBanner;
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

                public String getTotalTime() {
                    return totalTime;
                }

                public String getType() {
                    return type;
                }

                public int getSubNum() {
                    return subNum;
                }

                public int getPlaycount() {
                    return playcount;
                }
            }
        }
    }
*/
    public String apicode;
    public String message;
    @SerializedName("result")
    public MCCourse course;
}
