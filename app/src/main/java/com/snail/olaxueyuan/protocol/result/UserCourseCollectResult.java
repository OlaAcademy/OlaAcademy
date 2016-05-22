package com.snail.olaxueyuan.protocol.result;

import java.util.List;

/**
 * Created by mingge on 16/5/22.
 */
public class UserCourseCollectResult extends ServiceResult {

    /**
     * message : 成功
     * result : [{"videoId":1,"videoName":"课时1：实数的概念","videoUrl":"http://olamath.ufile.ucloud.com.cn/2016suanshu/01_gainian_01.mp4","courseId":54,"coursePic":"http://math.ufile.ucloud.com.cn/basicmath1.jpg","totalTime":"2:16:05","subAllNum":0}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * videoId : 1
     * videoName : 课时1：实数的概念
     * videoUrl : http://olamath.ufile.ucloud.com.cn/2016suanshu/01_gainian_01.mp4
     * courseId : 54
     * coursePic : http://math.ufile.ucloud.com.cn/basicmath1.jpg
     * totalTime : 2:16:05
     * subAllNum : 0
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
        private int videoId;
        private String videoName;
        private String videoUrl;
        private int courseId;
        private String coursePic;
        private String totalTime;
        private int subAllNum;

        public void setVideoId(int videoId) {
            this.videoId = videoId;
        }

        public void setVideoName(String videoName) {
            this.videoName = videoName;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public void setCoursePic(String coursePic) {
            this.coursePic = coursePic;
        }

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
        }

        public void setSubAllNum(int subAllNum) {
            this.subAllNum = subAllNum;
        }

        public int getVideoId() {
            return videoId;
        }

        public String getVideoName() {
            return videoName;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public int getCourseId() {
            return courseId;
        }

        public String getCoursePic() {
            return coursePic;
        }

        public String getTotalTime() {
            return totalTime;
        }

        public int getSubAllNum() {
            return subAllNum;
        }
    }
}
