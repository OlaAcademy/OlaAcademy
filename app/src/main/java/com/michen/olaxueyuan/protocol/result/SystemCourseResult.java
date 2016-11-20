package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 16/5/27.
 */
public class SystemCourseResult extends ServiceResult implements Serializable {
    @Override
    public String toString() {
        return "SystemCourseResult{" +
                "message='" + message + '\'' +
                ", apicode=" + apicode +
                ", result=" + result +
                '}';
    }

    /**
     * message : 成功
     * result : [{"attentionnum":0,"detail":"由幂学教育和欧拉学院版权所有","id":1,"image":"http://math.ufile.ucloud.com.cn/feemath1.jpg","leanstage":"","name":"2016年数学基础课程","org":"","paynum":10,"price":100,"suitto":"","totaltime":0,"type":"1","url":"","videonum":27},{"attentionnum":0,"detail":"由幂学教育和欧拉学院版权所有","id":2,"image":"http://math.ufile.ucloud.com.cn/feemath2.jpg","leanstage":"","name":"2016年数学真题密训课程","org":"","paynum":10,"price":100,"suitto":"","totaltime":0,"type":"1","url":"","videonum":24},{"attentionnum":0,"detail":"由幂学教育和欧拉学院版权所有","id":3,"image":"","leanstage":"","name":"英语","org":"","paynum":10,"price":100,"suitto":"","totaltime":0,"type":"1","url":"","videonum":0},{"attentionnum":0,"detail":"由幂学教育和欧拉学院版权所有","id":4,"image":"","leanstage":"","name":"英语","org":"","paynum":10,"price":100,"suitto":"","totaltime":0,"type":"1","url":"","videonum":0},{"attentionnum":0,"detail":"由幂学教育和欧拉学院版权所有","id":5,"image":"","leanstage":"","name":"英语","org":"","paynum":10,"price":100,"suitto":"","totaltime":0,"type":"1","url":"","videonum":0},{"attentionnum":0,"detail":"由幂学教育和欧拉学院版权所有","id":6,"image":"","leanstage":"","name":"英语","org":"","paynum":10,"price":100,"suitto":"","totaltime":0,"type":"1","url":"","videonum":0},{"attentionnum":0,"detail":"由幂学教育和欧拉学院版权所有","id":7,"image":"","leanstage":"","name":"英语","org":"","paynum":10,"price":100,"suitto":"","totaltime":0,"type":"1","url":"","videonum":0},{"attentionnum":0,"detail":"由幂学教育和欧拉学院版权所有","id":8,"image":"","leanstage":"","name":"英语","org":"","paynum":10,"price":100,"suitto":"","totaltime":0,"type":"1","url":"","videonum":0},{"attentionnum":0,"detail":"由幂学教育和欧拉学院版权所有","id":9,"image":"","leanstage":"","name":"英语","org":"","paynum":10,"price":100,"suitto":"","totaltime":0,"type":"1","url":"","videonum":0}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * attentionnum : 0
     * detail : 由幂学教育和欧拉学院版权所有
     * id : 1
     * image : http://math.ufile.ucloud.com.cn/feemath1.jpg
     * leanstage :
     * name : 2016年数学基础课程
     * org :
     * paynum : 10
     * price : 100
     * suitto :
     * totaltime : 0
     * type : 1
     * url :
     * videonum : 27
     */

    private List<SystemCourseResultEntity> result;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    public void setResult(List<SystemCourseResultEntity> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public int getApicode() {
        return apicode;
    }

    public List<SystemCourseResultEntity> getResult() {
        return result;
    }

   /* public static class ResultEntity implements Serializable {
        private int attentionnum;
        private String detail;
        private int id;
        private String image;
        private String leanstage;
        private String name;
        private String org;
        private int paynum;
        private int price;
        private String suitto;
        private int totaltime;
        private String type;
        private String url;
        private int videonum;
        private CreateTimeBean createTime;
        private int status;

        public CreateTimeBean getCreateTime() {
            return createTime;
        }

        public void setCreateTime(CreateTimeBean createTime) {
            this.createTime = createTime;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "ResultEntity{" +
                    "attentionnum=" + attentionnum +
                    ", detail='" + detail + '\'' +
                    ", id=" + id +
                    ", image='" + image + '\'' +
                    ", leanstage='" + leanstage + '\'' +
                    ", name='" + name + '\'' +
                    ", org='" + org + '\'' +
                    ", paynum=" + paynum +
                    ", price=" + price +
                    ", suitto='" + suitto + '\'' +
                    ", totaltime=" + totaltime +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", videonum=" + videonum +
                    '}';
        }

        public void setAttentionnum(int attentionnum) {
            this.attentionnum = attentionnum;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setLeanstage(String leanstage) {
            this.leanstage = leanstage;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setOrg(String org) {
            this.org = org;
        }

        public void setPaynum(int paynum) {
            this.paynum = paynum;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public void setSuitto(String suitto) {
            this.suitto = suitto;
        }

        public void setTotaltime(int totaltime) {
            this.totaltime = totaltime;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setVideonum(int videonum) {
            this.videonum = videonum;
        }

        public int getAttentionnum() {
            return attentionnum;
        }

        public String getDetail() {
            return detail;
        }

        public int getId() {
            return id;
        }

        public String getImage() {
            return image;
        }

        public String getLeanstage() {
            return leanstage;
        }

        public String getName() {
            return name;
        }

        public String getOrg() {
            return org;
        }

        public int getPaynum() {
            return paynum;
        }

        public int getPrice() {
            return price;
        }

        public String getSuitto() {
            return suitto;
        }

        public int getTotaltime() {
            return totaltime;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        public int getVideonum() {
            return videonum;
        }

        public static class CreateTimeBean implements Serializable {
            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            @Override
            public String toString() {
                return "CreateTimeBean{" +
                        "date=" + date +
                        ", day=" + day +
                        ", hours=" + hours +
                        ", minutes=" + minutes +
                        ", month=" + month +
                        ", seconds=" + seconds +
                        ", time=" + time +
                        ", timezoneOffset=" + timezoneOffset +
                        ", year=" + year +
                        '}';
            }

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
    }*/
}
