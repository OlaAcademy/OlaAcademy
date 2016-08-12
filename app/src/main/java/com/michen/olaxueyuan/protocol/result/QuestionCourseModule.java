package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 16/4/28.
 */
public class QuestionCourseModule implements Serializable{
    /**
     * message : 成功
     * result : {"address":"http://mp.weixin.qq.com/s?__biz=MzIwMzI4MjE0OA==&mid=100000167&idx=1&sn=2739a8d7771b27d5addb8559d8e84cdf#rd","bannerPic":"http://banner.ufile.ucloud.com.cn/banner8.png","id":4,"isBanner":0,"name":"写作","pid":"0","profile":"【陈君华】写作名家详解（幂学内部教材）","subAllNum":0,"totalTime":"","type":"1","child":[{"address":"","bannerPic":"","id":30,"isBanner":0,"name":"论证有效性","pid":"4","profile":"","subAllNum":12,"totalTime":"","type":"1","child":[{"address":"https://www.amazon.cn/2016MBA-MPA-MPAcc%E8%81%94%E8%80%83%E7%BB%BC%E5%90%88%E8%83%BD%E5%8A%9B%E5%86%99%E4%BD%9C%E5%8E%86%E5%B9%B4%E7%9C%9F%E9%A2%98%E5%90%8D%E5%AE%B6%E8%AF%A6%E8%A7%A3-%E9%99%88%E5%90%9B%E5%8D%8E/dp/B014EPU19Q/ref=sr_1_3?ie=UTF8&qid=1464153107&sr=8-3&keywords=%E5%86%99%E4%BD%9C+%E9%9","bannerPic":"","id":32,"isBanner":0,"name":"论证有效性分析","pid":"30","profile":"","subAllNum":12,"totalTime":"","type":"1","subNum":0,"playcount":31}],"subNum":0,"playcount":30},{"address":"","bannerPic":"","id":31,"isBanner":0,"name":"论说文","pid":"4","profile":"","subAllNum":17,"totalTime":"","type":"1","child":[{"address":"https://www.amazon.cn/2016MBA-MPA-MPAcc%E8%81%94%E8%80%83%E7%BB%BC%E5%90%88%E8%83%BD%E5%8A%9B%E5%86%99%E4%BD%9C%E5%8E%86%E5%B9%B4%E7%9C%9F%E9%A2%98%E5%90%8D%E5%AE%B6%E8%AF%A6%E8%A7%A3-%E9%99%88%E5%90%9B%E5%8D%8E/dp/B014EPU19Q/ref=sr_1_3?ie=UTF8&qid=1464153107&sr=8-3&keywords=%E5%86%99%E4%BD%9C+%E9%9","bannerPic":"","id":33,"isBanner":0,"name":"论说文","pid":"31","profile":"","subAllNum":17,"totalTime":"","type":"1","subNum":0,"playcount":35}],"subNum":0,"playcount":35}],"subNum":0,"playcount":0,"homework":{"avatar":"1468744004262.jpg","groupId":"1","groupName":"欧拉学习群","id":1,"name":"每周一练","userName":"小欧4743","count":7,"finishedCount":0,"time":"2016-08-02 17:50"}}
     * apicode : 10000
     */

    private String message;
    /**
     * address : http://mp.weixin.qq.com/s?__biz=MzIwMzI4MjE0OA==&mid=100000167&idx=1&sn=2739a8d7771b27d5addb8559d8e84cdf#rd
     * bannerPic : http://banner.ufile.ucloud.com.cn/banner8.png
     * id : 4
     * isBanner : 0
     * name : 写作
     * pid : 0
     * profile : 【陈君华】写作名家详解（幂学内部教材）
     * subAllNum : 0
     * totalTime :
     * type : 1
     * child : [{"address":"","bannerPic":"","id":30,"isBanner":0,"name":"论证有效性","pid":"4","profile":"","subAllNum":12,"totalTime":"","type":"1","child":[{"address":"https://www.amazon.cn/2016MBA-MPA-MPAcc%E8%81%94%E8%80%83%E7%BB%BC%E5%90%88%E8%83%BD%E5%8A%9B%E5%86%99%E4%BD%9C%E5%8E%86%E5%B9%B4%E7%9C%9F%E9%A2%98%E5%90%8D%E5%AE%B6%E8%AF%A6%E8%A7%A3-%E9%99%88%E5%90%9B%E5%8D%8E/dp/B014EPU19Q/ref=sr_1_3?ie=UTF8&qid=1464153107&sr=8-3&keywords=%E5%86%99%E4%BD%9C+%E9%9","bannerPic":"","id":32,"isBanner":0,"name":"论证有效性分析","pid":"30","profile":"","subAllNum":12,"totalTime":"","type":"1","subNum":0,"playcount":31}],"subNum":0,"playcount":30},{"address":"","bannerPic":"","id":31,"isBanner":0,"name":"论说文","pid":"4","profile":"","subAllNum":17,"totalTime":"","type":"1","child":[{"address":"https://www.amazon.cn/2016MBA-MPA-MPAcc%E8%81%94%E8%80%83%E7%BB%BC%E5%90%88%E8%83%BD%E5%8A%9B%E5%86%99%E4%BD%9C%E5%8E%86%E5%B9%B4%E7%9C%9F%E9%A2%98%E5%90%8D%E5%AE%B6%E8%AF%A6%E8%A7%A3-%E9%99%88%E5%90%9B%E5%8D%8E/dp/B014EPU19Q/ref=sr_1_3?ie=UTF8&qid=1464153107&sr=8-3&keywords=%E5%86%99%E4%BD%9C+%E9%9","bannerPic":"","id":33,"isBanner":0,"name":"论说文","pid":"31","profile":"","subAllNum":17,"totalTime":"","type":"1","subNum":0,"playcount":35}],"subNum":0,"playcount":35}]
     * subNum : 0
     * playcount : 0
     * homework : {"avatar":"1468744004262.jpg","groupId":"1","groupName":"欧拉学习群","id":1,"name":"每周一练","userName":"小欧4743","count":7,"finishedCount":0,"time":"2016-08-02 17:50"}
     */

    private ResultEntity result;
    private int apicode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public int getApicode() {
        return apicode;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    @Override
    public String toString() {
        return "QuestionCourseModule{" +
                "message='" + message + '\'' +
                ", result=" + result +
                ", apicode=" + apicode +
                '}';
    }

    public static class ResultEntity implements Serializable{
        private String address;
        private String bannerPic;
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
        /**
         * avatar : 1468744004262.jpg
         * groupId : 1
         * groupName : 欧拉学习群
         * id : 1
         * name : 每周一练
         * userName : 小欧4743
         * count : 7
         * finishedCount : 0
         * time : 2016-08-02 17:50
         */

        private HomeworkBean homework;
        /**
         * address :
         * bannerPic :
         * id : 30
         * isBanner : 0
         * name : 论证有效性
         * pid : 4
         * profile :
         * subAllNum : 12
         * totalTime :
         * type : 1
         * child : [{"address":"https://www.amazon.cn/2016MBA-MPA-MPAcc%E8%81%94%E8%80%83%E7%BB%BC%E5%90%88%E8%83%BD%E5%8A%9B%E5%86%99%E4%BD%9C%E5%8E%86%E5%B9%B4%E7%9C%9F%E9%A2%98%E5%90%8D%E5%AE%B6%E8%AF%A6%E8%A7%A3-%E9%99%88%E5%90%9B%E5%8D%8E/dp/B014EPU19Q/ref=sr_1_3?ie=UTF8&qid=1464153107&sr=8-3&keywords=%E5%86%99%E4%BD%9C+%E9%9","bannerPic":"","id":32,"isBanner":0,"name":"论证有效性分析","pid":"30","profile":"","subAllNum":12,"totalTime":"","type":"1","subNum":0,"playcount":31}]
         * subNum : 0
         * playcount : 30
         */

        private List<ChildEntity> child;

        @Override
        public String toString() {
            return "ResultEntity{" +
                    "address='" + address + '\'' +
                    ", bannerPic='" + bannerPic + '\'' +
                    ", id=" + id +
                    ", isBanner=" + isBanner +
                    ", name='" + name + '\'' +
                    ", pid='" + pid + '\'' +
                    ", profile='" + profile + '\'' +
                    ", subAllNum=" + subAllNum +
                    ", totalTime='" + totalTime + '\'' +
                    ", type='" + type + '\'' +
                    ", subNum=" + subNum +
                    ", playcount=" + playcount +
                    ", homework=" + homework +
                    ", child=" + child +
                    '}';
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBannerPic() {
            return bannerPic;
        }

        public void setBannerPic(String bannerPic) {
            this.bannerPic = bannerPic;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIsBanner() {
            return isBanner;
        }

        public void setIsBanner(int isBanner) {
            this.isBanner = isBanner;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public int getSubAllNum() {
            return subAllNum;
        }

        public void setSubAllNum(int subAllNum) {
            this.subAllNum = subAllNum;
        }

        public String getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSubNum() {
            return subNum;
        }

        public void setSubNum(int subNum) {
            this.subNum = subNum;
        }

        public int getPlaycount() {
            return playcount;
        }

        public void setPlaycount(int playcount) {
            this.playcount = playcount;
        }

        public HomeworkBean getHomework() {
            return homework;
        }

        public void setHomework(HomeworkBean homework) {
            this.homework = homework;
        }

        public List<ChildEntity> getChild() {
            return child;
        }

        public void setChild(List<ChildEntity> child) {
            this.child = child;
        }

        public static class HomeworkBean {
            private String avatar;
            private String groupId;
            private String groupName;
            private int id;
            private String name;
            private String userName;
            private int count;
            private int finishedCount;
            private String time;

            @Override
            public String toString() {
                return "HomeworkBean{" +
                        "avatar='" + avatar + '\'' +
                        ", groupId='" + groupId + '\'' +
                        ", groupName='" + groupName + '\'' +
                        ", id=" + id +
                        ", name='" + name + '\'' +
                        ", userName='" + userName + '\'' +
                        ", count=" + count +
                        ", finishedCount=" + finishedCount +
                        ", time='" + time + '\'' +
                        '}';
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getGroupId() {
                return groupId;
            }

            public void setGroupId(String groupId) {
                this.groupId = groupId;
            }

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
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

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getFinishedCount() {
                return finishedCount;
            }

            public void setFinishedCount(int finishedCount) {
                this.finishedCount = finishedCount;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class ChildEntity {
            private String address;
            private String bannerPic;
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
            private boolean isExpanded;//是否展开

            @Override
            public String toString() {
                return "ChildEntity{" +
                        "address='" + address + '\'' +
                        ", bannerPic='" + bannerPic + '\'' +
                        ", id=" + id +
                        ", isBanner=" + isBanner +
                        ", name='" + name + '\'' +
                        ", pid='" + pid + '\'' +
                        ", profile='" + profile + '\'' +
                        ", subAllNum=" + subAllNum +
                        ", totalTime='" + totalTime + '\'' +
                        ", type='" + type + '\'' +
                        ", subNum=" + subNum +
                        ", playcount=" + playcount +
                        ", isExpanded=" + isExpanded +
                        ", child=" + child +
                        '}';
            }

            public boolean isExpanded() {
                return isExpanded;
            }

            public void setIsExpanded(boolean expanded) {
                isExpanded = expanded;
            }

            /**
             * address : https://www.amazon.cn/2016MBA-MPA-MPAcc%E8%81%94%E8%80%83%E7%BB%BC%E5%90%88%E8%83%BD%E5%8A%9B%E5%86%99%E4%BD%9C%E5%8E%86%E5%B9%B4%E7%9C%9F%E9%A2%98%E5%90%8D%E5%AE%B6%E8%AF%A6%E8%A7%A3-%E9%99%88%E5%90%9B%E5%8D%8E/dp/B014EPU19Q/ref=sr_1_3?ie=UTF8&qid=1464153107&sr=8-3&keywords=%E5%86%99%E4%BD%9C+%E9%9
             * bannerPic :
             * id : 32
             * isBanner : 0
             * name : 论证有效性分析
             * pid : 30
             * profile :
             * subAllNum : 12
             * totalTime :
             * type : 1
             * subNum : 0
             * playcount : 31
             */

            private List<ChildEntityChild> child;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getBannerPic() {
                return bannerPic;
            }

            public void setBannerPic(String bannerPic) {
                this.bannerPic = bannerPic;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getIsBanner() {
                return isBanner;
            }

            public void setIsBanner(int isBanner) {
                this.isBanner = isBanner;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getProfile() {
                return profile;
            }

            public void setProfile(String profile) {
                this.profile = profile;
            }

            public int getSubAllNum() {
                return subAllNum;
            }

            public void setSubAllNum(int subAllNum) {
                this.subAllNum = subAllNum;
            }

            public String getTotalTime() {
                return totalTime;
            }

            public void setTotalTime(String totalTime) {
                this.totalTime = totalTime;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getSubNum() {
                return subNum;
            }

            public void setSubNum(int subNum) {
                this.subNum = subNum;
            }

            public int getPlaycount() {
                return playcount;
            }

            public void setPlaycount(int playcount) {
                this.playcount = playcount;
            }

            public List<ChildEntityChild> getChild() {
                return child;
            }

            public void setChild(List<ChildEntityChild> child) {
                this.child = child;
            }

            public static class ChildEntityChild {
                private String address;
                private String bannerPic;
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

                @Override
                public String toString() {
                    return "ChildEntityChild{" +
                            "address='" + address + '\'' +
                            ", bannerPic='" + bannerPic + '\'' +
                            ", id=" + id +
                            ", isBanner=" + isBanner +
                            ", name='" + name + '\'' +
                            ", pid='" + pid + '\'' +
                            ", profile='" + profile + '\'' +
                            ", subAllNum=" + subAllNum +
                            ", totalTime='" + totalTime + '\'' +
                            ", type='" + type + '\'' +
                            ", subNum=" + subNum +
                            ", playcount=" + playcount +
                            '}';
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public String getBannerPic() {
                    return bannerPic;
                }

                public void setBannerPic(String bannerPic) {
                    this.bannerPic = bannerPic;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getIsBanner() {
                    return isBanner;
                }

                public void setIsBanner(int isBanner) {
                    this.isBanner = isBanner;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPid() {
                    return pid;
                }

                public void setPid(String pid) {
                    this.pid = pid;
                }

                public String getProfile() {
                    return profile;
                }

                public void setProfile(String profile) {
                    this.profile = profile;
                }

                public int getSubAllNum() {
                    return subAllNum;
                }

                public void setSubAllNum(int subAllNum) {
                    this.subAllNum = subAllNum;
                }

                public String getTotalTime() {
                    return totalTime;
                }

                public void setTotalTime(String totalTime) {
                    this.totalTime = totalTime;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public int getSubNum() {
                    return subNum;
                }

                public void setSubNum(int subNum) {
                    this.subNum = subNum;
                }

                public int getPlaycount() {
                    return playcount;
                }

                public void setPlaycount(int playcount) {
                    this.playcount = playcount;
                }
            }
        }
    }
}
