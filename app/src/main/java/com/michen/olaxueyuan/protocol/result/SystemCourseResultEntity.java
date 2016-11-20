package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;

/**
 * Created by mingge on 16/11/20.
 */

public class SystemCourseResultEntity implements Serializable {
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
    private SystemCourseResultEntity.CreateTimeBean createTime;
    private int status;

    public SystemCourseResultEntity.CreateTimeBean getCreateTime() {
        return createTime;
    }

    public void setCreateTime(SystemCourseResultEntity.CreateTimeBean createTime) {
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

}
