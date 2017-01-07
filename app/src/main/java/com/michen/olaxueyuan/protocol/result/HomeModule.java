package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/8/23.
 */
public class HomeModule implements Serializable {

    /**
     * message : 成功
     * result : {"bannerList":[{"id":4,"name":"2017年幂学系统班","objectId":"0,1","pic":"http://cospic.ufile.ucloud.com.cn/94.jpg","type":4,"url":""},{"id":3,"name":"联考逻辑全程体系课程【饶思中】【限时】","objectId":"17","pic":"http://cospic.ufile.ucloud.com.cn/92.jpg","type":3,"url":"","commodity":{"attentionnum":100,"detail":"逻辑名师，讲课生动有趣，善于把握考点，总结规律，把知识点板块化，把考点逻辑化，对考生的逻辑学习提升起到了至关重要的作用。学生评语：方法独特，一招制胜，把复杂的逻辑提干简单化，冗长的逻辑容易理解。","id":17,"image":"http://banner.ufile.ucloud.com.cn/logo_mx.png","leanstage":"8月17日-12月31日","name":"联考逻辑全程体系课程【饶思中】【限时】","org":"幂学教育出版社","paynum":28,"price":98,"status":0,"suitto":"教材版本：管理类联考综合能力历年真题","totaltime":960,"type":"1","url":"http://cospic.ufile.ucloud.com.cn/92.jpg","videonum":12}},{"id":1,"name":"福利来了","objectId":"189","pic":"http://banner.ufile.ucloud.com.cn/banner_book_2.png","type":1,"url":"http://mp.weixin.qq.com/s?__biz=MzIwMzI4MjE0OA==&mid=2247484284&idx=1&sn=fe3426a72614db52666d996f6d9384bd&chksm=96d080f7a1a709e10eaf164c2daac8e0de4983c64b5aa673ae7d048628c0afc3b5bb2924d395#rd"}],"questionList":[{"id":13636,"title":"指定回答","content":"指定回答","number":96,"time":"2016-12-30 16:47:23"},{"id":13628,"title":"测试","content":"测试你的时候才能更加考察你的时候我们就算了。我也好的","number":363,"time":"2016-12-25 12:38:11"}],"goodsList":[{"attentionnum":100,"createTime":{"date":18,"day":4,"hours":20,"minutes":58,"month":7,"seconds":27,"time":1471525107000,"timezoneOffset":-480,"year":116},"detail":"对考试内容作全面细致的讲解,温故知新，循序渐进，打牢基础，为下一阶段的学习作好充分的准备,期末基本达到联考要求的水平。\r授课名师陈剑，清华大学博士，数学考试大纲唯一指定解析人，标准化辅导体系首创人，曾到日本、澳洲、美国、加拿大等进行国际交流学习。从事数学辅导十二载以来，面对数学纷乱无序的考法，对重点、难点、必考点的把握出神入化，令学员事半功倍;孜孜不倦、高度负责的态度以及对考题的精准预测，令考生受益无穷。多年来学员有\u201c容易的通俗易懂，疑难的分析透彻，零基础的学有所获，数学高手另有启发\u201d的评价，每年超高的命中率使无数零基础考生创造了轻取高分的奇迹，是业界王牌数学老师。","id":1,"image":"http://banner.ufile.ucloud.com.cn/logo_mx.png","leanstage":"5月17日-12月31日","name":"2017年数学周末班基础课程","org":"幂学教育版权所有","paynum":970,"price":88,"status":0,"suitto":"教材版本：《联考综合能力数学高分指南》","totaltime":2200,"type":"1","url":"http://cospic.ufile.ucloud.com.cn/89.jpg","videonum":27},{"attentionnum":100,"createTime":{"date":18,"day":4,"hours":19,"minutes":58,"month":7,"seconds":27,"time":1471521507000,"timezoneOffset":-480,"year":116},"detail":"逻辑名师，讲课生动有趣，善于把握考点，总结规律，把知识点板块化，把考点逻辑化，对考生的逻辑学习提升起到了至关重要的作用。学生评语：方法独特，一招制胜，把复杂的逻辑提干简单化，冗长的逻辑容易理解。","id":17,"image":"http://banner.ufile.ucloud.com.cn/logo_mx.png","leanstage":"8月17日-12月31日","name":"联考逻辑全程体系课程【饶思中】【限时】","org":"幂学教育出版社","paynum":28,"price":98,"status":0,"suitto":"教材版本：管理类联考综合能力历年真题","totaltime":960,"type":"1","url":"http://cospic.ufile.ucloud.com.cn/92.jpg","videonum":12}],"courseList":[{"address":"http://cospic.ufile.ucloud.com.cn/31.jpg","bannerPic":"http://banner.ufile.ucloud.com.cn/course_banner7.jpg","id":188,"isBanner":1,"name":"2017年最新数学考试大纲解析","pid":"34","playcount":7460,"profile":"","subAllNum":1,"totalTime":"40分钟","type":"2"},{"address":"http://cospic.ufile.ucloud.com.cn/35.jpg","bannerPic":"","id":243,"isBanner":0,"name":"直言命题考点讲解","pid":"58","playcount":372,"profile":"","subAllNum":1,"totalTime":"42分钟","type":"2"}]}
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

    public static class ResultBean implements Serializable{
        private List<BannerListBean> bannerList;
        private List<QuestionListBean> questionList;
        private List<SystemCourseResultEntity> goodsList;
        private List<CourseListBean> courseList;

        public List<BannerListBean> getBannerList() {
            return bannerList;
        }

        public void setBannerList(List<BannerListBean> bannerList) {
            this.bannerList = bannerList;
        }

        public List<QuestionListBean> getQuestionList() {
            return questionList;
        }

        public void setQuestionList(List<QuestionListBean> questionList) {
            this.questionList = questionList;
        }

        public List<SystemCourseResultEntity> getGoodsList() {
            return goodsList;
        }

        public void setGoodsList(List<SystemCourseResultEntity> goodsList) {
            this.goodsList = goodsList;
        }

        public List<CourseListBean> getCourseList() {
            return courseList;
        }

        public void setCourseList(List<CourseListBean> courseList) {
            this.courseList = courseList;
        }

        public static class BannerListBean implements Serializable{
            /**
             * id : 4
             * name : 2017年幂学系统班
             * objectId : 0,1
             * pic : http://cospic.ufile.ucloud.com.cn/94.jpg
             * type : 4
             * url :
             * commodity : {"attentionnum":100,"detail":"逻辑名师，讲课生动有趣，善于把握考点，总结规律，把知识点板块化，把考点逻辑化，对考生的逻辑学习提升起到了至关重要的作用。学生评语：方法独特，一招制胜，把复杂的逻辑提干简单化，冗长的逻辑容易理解。","id":17,"image":"http://banner.ufile.ucloud.com.cn/logo_mx.png","leanstage":"8月17日-12月31日","name":"联考逻辑全程体系课程【饶思中】【限时】","org":"幂学教育出版社","paynum":28,"price":98,"status":0,"suitto":"教材版本：管理类联考综合能力历年真题","totaltime":960,"type":"1","url":"http://cospic.ufile.ucloud.com.cn/92.jpg","videonum":12}
             */

            private int id;
            private String name;
            private String objectId;
            private String pic;
            private int type;
            private String url;
            private SystemCourseResultEntity commodity;

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

            public String getObjectId() {
                return objectId;
            }

            public void setObjectId(String objectId) {
                this.objectId = objectId;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public SystemCourseResultEntity getCommodity() {
                return commodity;
            }

            public void setCommodity(SystemCourseResultEntity commodity) {
                this.commodity = commodity;
            }

            public static class CommodityBean implements Serializable{
                /**
                 * attentionnum : 100
                 * detail : 逻辑名师，讲课生动有趣，善于把握考点，总结规律，把知识点板块化，把考点逻辑化，对考生的逻辑学习提升起到了至关重要的作用。学生评语：方法独特，一招制胜，把复杂的逻辑提干简单化，冗长的逻辑容易理解。
                 * id : 17
                 * image : http://banner.ufile.ucloud.com.cn/logo_mx.png
                 * leanstage : 8月17日-12月31日
                 * name : 联考逻辑全程体系课程【饶思中】【限时】
                 * org : 幂学教育出版社
                 * paynum : 28
                 * price : 98
                 * status : 0
                 * suitto : 教材版本：管理类联考综合能力历年真题
                 * totaltime : 960
                 * type : 1
                 * url : http://cospic.ufile.ucloud.com.cn/92.jpg
                 * videonum : 12
                 */

                private int attentionnum;
                private String detail;
                private int id;
                private String image;
                private String leanstage;
                private String name;
                private String org;
                private int paynum;
                private int price;
                private int status;
                private String suitto;
                private int totaltime;
                private String type;
                private String url;
                private int videonum;

                public int getAttentionnum() {
                    return attentionnum;
                }

                public void setAttentionnum(int attentionnum) {
                    this.attentionnum = attentionnum;
                }

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getLeanstage() {
                    return leanstage;
                }

                public void setLeanstage(String leanstage) {
                    this.leanstage = leanstage;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getOrg() {
                    return org;
                }

                public void setOrg(String org) {
                    this.org = org;
                }

                public int getPaynum() {
                    return paynum;
                }

                public void setPaynum(int paynum) {
                    this.paynum = paynum;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getSuitto() {
                    return suitto;
                }

                public void setSuitto(String suitto) {
                    this.suitto = suitto;
                }

                public int getTotaltime() {
                    return totaltime;
                }

                public void setTotaltime(int totaltime) {
                    this.totaltime = totaltime;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getVideonum() {
                    return videonum;
                }

                public void setVideonum(int videonum) {
                    this.videonum = videonum;
                }
            }
        }

        public static class QuestionListBean implements Serializable{
            /**
             * id : 13636
             * title : 指定回答
             * content : 指定回答
             * number : 96
             * time : 2016-12-30 16:47:23
             */

            private int id;
            private String title;
            private String content;
            private int number;
            private String time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class GoodsListBean implements Serializable{
            /**
             * attentionnum : 100
             * createTime : {"date":18,"day":4,"hours":20,"minutes":58,"month":7,"seconds":27,"time":1471525107000,"timezoneOffset":-480,"year":116}
             * detail : 对考试内容作全面细致的讲解,温故知新，循序渐进，打牢基础，为下一阶段的学习作好充分的准备,期末基本达到联考要求的水平。授课名师陈剑，清华大学博士，数学考试大纲唯一指定解析人，标准化辅导体系首创人，曾到日本、澳洲、美国、加拿大等进行国际交流学习。从事数学辅导十二载以来，面对数学纷乱无序的考法，对重点、难点、必考点的把握出神入化，令学员事半功倍;孜孜不倦、高度负责的态度以及对考题的精准预测，令考生受益无穷。多年来学员有“容易的通俗易懂，疑难的分析透彻，零基础的学有所获，数学高手另有启发”的评价，每年超高的命中率使无数零基础考生创造了轻取高分的奇迹，是业界王牌数学老师。
             * id : 1
             * image : http://banner.ufile.ucloud.com.cn/logo_mx.png
             * leanstage : 5月17日-12月31日
             * name : 2017年数学周末班基础课程
             * org : 幂学教育版权所有
             * paynum : 970
             * price : 88
             * status : 0
             * suitto : 教材版本：《联考综合能力数学高分指南》
             * totaltime : 2200
             * type : 1
             * url : http://cospic.ufile.ucloud.com.cn/89.jpg
             * videonum : 27
             */

            private int attentionnum;
            private CreateTimeBean createTime;
            private String detail;
            private int id;
            private String image;
            private String leanstage;
            private String name;
            private String org;
            private int paynum;
            private int price;
            private int status;
            private String suitto;
            private int totaltime;
            private String type;
            private String url;
            private int videonum;

            public int getAttentionnum() {
                return attentionnum;
            }

            public void setAttentionnum(int attentionnum) {
                this.attentionnum = attentionnum;
            }

            public CreateTimeBean getCreateTime() {
                return createTime;
            }

            public void setCreateTime(CreateTimeBean createTime) {
                this.createTime = createTime;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLeanstage() {
                return leanstage;
            }

            public void setLeanstage(String leanstage) {
                this.leanstage = leanstage;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOrg() {
                return org;
            }

            public void setOrg(String org) {
                this.org = org;
            }

            public int getPaynum() {
                return paynum;
            }

            public void setPaynum(int paynum) {
                this.paynum = paynum;
            }

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getSuitto() {
                return suitto;
            }

            public void setSuitto(String suitto) {
                this.suitto = suitto;
            }

            public int getTotaltime() {
                return totaltime;
            }

            public void setTotaltime(int totaltime) {
                this.totaltime = totaltime;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public int getVideonum() {
                return videonum;
            }

            public void setVideonum(int videonum) {
                this.videonum = videonum;
            }

            public static class CreateTimeBean implements Serializable{
                /**
                 * date : 18
                 * day : 4
                 * hours : 20
                 * minutes : 58
                 * month : 7
                 * seconds : 27
                 * time : 1471525107000
                 * timezoneOffset : -480
                 * year : 116
                 */

                private int date;
                private int day;
                private int hours;
                private int minutes;
                private int month;
                private int seconds;
                private long time;
                private int timezoneOffset;
                private int year;

                public int getDate() {
                    return date;
                }

                public void setDate(int date) {
                    this.date = date;
                }

                public int getDay() {
                    return day;
                }

                public void setDay(int day) {
                    this.day = day;
                }

                public int getHours() {
                    return hours;
                }

                public void setHours(int hours) {
                    this.hours = hours;
                }

                public int getMinutes() {
                    return minutes;
                }

                public void setMinutes(int minutes) {
                    this.minutes = minutes;
                }

                public int getMonth() {
                    return month;
                }

                public void setMonth(int month) {
                    this.month = month;
                }

                public int getSeconds() {
                    return seconds;
                }

                public void setSeconds(int seconds) {
                    this.seconds = seconds;
                }

                public long getTime() {
                    return time;
                }

                public void setTime(long time) {
                    this.time = time;
                }

                public int getTimezoneOffset() {
                    return timezoneOffset;
                }

                public void setTimezoneOffset(int timezoneOffset) {
                    this.timezoneOffset = timezoneOffset;
                }

                public int getYear() {
                    return year;
                }

                public void setYear(int year) {
                    this.year = year;
                }
            }
        }

        public static class CourseListBean implements Serializable{
            /**
             * address : http://cospic.ufile.ucloud.com.cn/31.jpg
             * bannerPic : http://banner.ufile.ucloud.com.cn/course_banner7.jpg
             * id : 188
             * isBanner : 1
             * name : 2017年最新数学考试大纲解析
             * pid : 34
             * playcount : 7460
             * profile :
             * subAllNum : 1
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
