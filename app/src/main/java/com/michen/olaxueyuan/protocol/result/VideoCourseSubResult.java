package com.michen.olaxueyuan.protocol.result;

import java.util.List;

/**
 * Created by mingge on 2017/2/13.
 */

public class VideoCourseSubResult {
    /**
     * message : 成功
     * result : {"address":"http://cospic.ufile.ucloud.com.cn/1.jpg","bannerPic":"","id":34,"isBanner":0,"name":"导学课程","pid":"1","playcount":114,"profile":"","subAllNum":2,"teacherId":"1","totalTime":"","type":"2","teacherName":"陈剑","teacherAvator":"http://upload.swiftacademy.cn:8080/swift/teacher/avatar1.jpg","subList":[{"address":"http://cospic.ufile.ucloud.com.cn/31.jpg","bannerPic":"http://banner.ufile.ucloud.com.cn/course_banner4.jpg","id":188,"isBanner":1,"name":"2017年最新数学考试大纲解析","pid":"34","playcount":4099,"profile":"","subAllNum":1,"teacherId":"1","totalTime":"40分钟","type":"2"},{"address":"http://cospic.ufile.ucloud.com.cn/30.jpg","bannerPic":"http://banner.ufile.ucloud.com.cn/banner6.jpg","id":189,"isBanner":0,"name":"2017年最新逻辑考试大纲解析","pid":"34","playcount":2178,"profile":"","subAllNum":1,"teacherId":"1","totalTime":"44分钟","type":"2"},{"address":"http://cospic.ufile.ucloud.com.cn/28.jpg","bannerPic":"","id":191,"isBanner":0,"name":"考研英语(二)备考总攻略","pid":"34","playcount":812,"profile":"","subAllNum":4,"teacherId":"1","totalTime":"33分钟","type":"2"},{"address":"http://cospic.ufile.ucloud.com.cn/3.jpg","bannerPic":"","id":214,"isBanner":0,"name":"2017年最新写作考试大纲解析","pid":"34","playcount":264,"profile":"","subAllNum":1,"teacherId":"1","totalTime":"34分钟","type":"2"}]}
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
         * address : http://cospic.ufile.ucloud.com.cn/1.jpg
         * bannerPic :
         * id : 34
         * isBanner : 0
         * name : 导学课程
         * pid : 1
         * playcount : 114
         * profile :
         * subAllNum : 2
         * teacherId : 1
         * totalTime :
         * type : 2
         * teacherName : 陈剑
         * teacherAvator : http://upload.swiftacademy.cn:8080/swift/teacher/avatar1.jpg
         * subList : [{"address":"http://cospic.ufile.ucloud.com.cn/31.jpg","bannerPic":"http://banner.ufile.ucloud.com.cn/course_banner4.jpg","id":188,"isBanner":1,"name":"2017年最新数学考试大纲解析","pid":"34","playcount":4099,"profile":"","subAllNum":1,"teacherId":"1","totalTime":"40分钟","type":"2"},{"address":"http://cospic.ufile.ucloud.com.cn/30.jpg","bannerPic":"http://banner.ufile.ucloud.com.cn/banner6.jpg","id":189,"isBanner":0,"name":"2017年最新逻辑考试大纲解析","pid":"34","playcount":2178,"profile":"","subAllNum":1,"teacherId":"1","totalTime":"44分钟","type":"2"},{"address":"http://cospic.ufile.ucloud.com.cn/28.jpg","bannerPic":"","id":191,"isBanner":0,"name":"考研英语(二)备考总攻略","pid":"34","playcount":812,"profile":"","subAllNum":4,"teacherId":"1","totalTime":"33分钟","type":"2"},{"address":"http://cospic.ufile.ucloud.com.cn/3.jpg","bannerPic":"","id":214,"isBanner":0,"name":"2017年最新写作考试大纲解析","pid":"34","playcount":264,"profile":"","subAllNum":1,"teacherId":"1","totalTime":"34分钟","type":"2"}]
         */

        private String address;
        private String bannerPic;
        private int id;
        private int isBanner;
        private String name;
        private String pid;
        private int playcount;
        private String profile;
        private int subAllNum;
        private String teacherId;
        private String totalTime;
        private String type;
        private String teacherName;
        private String teacherAvator;
        private List<SubListBean> subList;

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

        public int getPlaycount() {
            return playcount;
        }

        public void setPlaycount(int playcount) {
            this.playcount = playcount;
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

        public String getTeacherId() {
            return teacherId;
        }

        public void setTeacherId(String teacherId) {
            this.teacherId = teacherId;
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

        public String getTeacherName() {
            return teacherName;
        }

        public void setTeacherName(String teacherName) {
            this.teacherName = teacherName;
        }

        public String getTeacherAvator() {
            return teacherAvator;
        }

        public void setTeacherAvator(String teacherAvator) {
            this.teacherAvator = teacherAvator;
        }

        public List<SubListBean> getSubList() {
            return subList;
        }

        public void setSubList(List<SubListBean> subList) {
            this.subList = subList;
        }

        public static class SubListBean {
            /**
             * address : http://cospic.ufile.ucloud.com.cn/31.jpg
             * bannerPic : http://banner.ufile.ucloud.com.cn/course_banner4.jpg
             * id : 188
             * isBanner : 1
             * name : 2017年最新数学考试大纲解析
             * pid : 34
             * playcount : 4099
             * profile :
             * subAllNum : 1
             * teacherId : 1
             * totalTime : 40分钟
             * type : 2
             */

            private String address;
            private String bannerPic;
            private int id;
            private int isBanner;
            private String name;
            private String pid;
            private int playcount;
            private String profile;
            private int subAllNum;
            private String teacherId;
            private String totalTime;
            private String type;

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

            public int getPlaycount() {
                return playcount;
            }

            public void setPlaycount(int playcount) {
                this.playcount = playcount;
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

            public String getTeacherId() {
                return teacherId;
            }

            public void setTeacherId(String teacherId) {
                this.teacherId = teacherId;
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
        }
    }
}
