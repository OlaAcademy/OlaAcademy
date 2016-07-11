package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 16/5/11.
 */
public class OLaCircleOldModule implements Serializable{
    /**
     * message : 成功
     * result : [{"userName":"晋成公","userAvatar":"1452319877991.jpg","videoId":1,"videoName":"古典概率","courseId":"1","time":"2016-04-28 08:58:45"},{"userName":"赵建菲","userAvatar":"","videoId":9,"videoName":"大纲词汇分析02","courseId":"1","time":"2016-04-28 08:59:48"}]
     * apicode : 10000
     */

    private String message;
    private int apicode;

    @Override
    public String toString() {
        return "OLaCircleModule{" +
                "message='" + message + '\'' +
                ", apicode=" + apicode +
                ", result=" + result +
                '}';
    }

    /**
     * userName : 晋成公
     * userAvatar : 1452319877991.jpg
     * videoId : 1
     * videoName : 古典概率
     * courseId : 1
     * time : 2016-04-28 08:58:45
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
        private String logId;
        private String userName;
        private String userAvatar;
        private int videoId;
        private String videoName;
        private String courseId;
        private String time;

        public String getLogId() {
            return logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public void setVideoId(int videoId) {
            this.videoId = videoId;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getUserName() {
            return userName;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public int getVideoId() {
            return videoId;
        }

        public String getVideoName() {
            return videoName;
        }

        public String getCourseId() {
            return courseId;
        }

        public String getTime() {
            return time;
        }

        @Override
        public String toString() {
            return "ResultEntity{" +
                    "userName='" + userName + '\'' +
                    ", userAvatar='" + userAvatar + '\'' +
                    ", videoId=" + videoId +
                    ", videoName='" + videoName + '\'' +
                    ", courseId='" + courseId + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }
}
