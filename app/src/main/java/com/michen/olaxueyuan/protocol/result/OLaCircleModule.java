package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 16/5/11.
 */
public class OLaCircleModule implements Serializable {
    /**
     * message : 成功
     * result : [{"circleId":230,"userName":"小欧5425","type":1,"time":"2016-07-11 14:50:07","courseId":70,"videoId":107,"title":"学习记录","content":"学习了：几何考点预测及拿分策略","imageGids":"http://math.ufile.ucloud.com.cn/basicmath7.jpg"},{"circleId":228,"userName":"小欧5425","type":1,"time":"2016-07-11 14:49:49","courseId":56,"videoId":644,"title":"学习记录","content":"学习了：实数、绝对值、比和比例","imageGids":"http://math.ufile.ucloud.com.cn/basicmath1.jpg"},{"circleId":178,"userName":"小欧5425","type":1,"time":"2016-07-11 14:49:25","courseId":69,"videoId":95,"title":"学习记录","content":"学习了：概率","imageGids":"http://math.ufile.ucloud.com.cn/basicmath4.jpg"},{"circleId":229,"userName":"小欧5425","type":1,"time":"2016-07-11 14:42:42","courseId":66,"videoId":88,"title":"学习记录","content":"学习了：排列组成","imageGids":"http://math.ufile.ucloud.com.cn/basicmath10.jpg"},{"circleId":227,"userName":"小欧6534","type":1,"time":"2016-07-11 14:27:27","courseId":110,"videoId":539,"title":"学习记录","content":"学习了：比和比例","imageGids":"https://www.amazon.cn/MBA-MPA-MPAcc%E8%81%94%E8%80%83%E7%BB%BC%E5%90%88%E8%83%BD%E5%8A%9B%E6%95%B0%E5%AD%A6%E9%AB%98%E5%88%86%E6%8C%87%E5%8D%97/dp/B01ARJDYFQ/ref=sr_1_1?ie=UTF8&qid=1464152741&sr=8-1&keywords=%E9%99%88%E5%89%91"},{"circleId":226,"userName":"小欧5228","type":1,"time":"2016-07-11 13:37:25","courseId":71,"videoId":109,"title":"学习记录","content":"学习了：数据分析考点预测及拿分策略","imageGids":"http://math.ufile.ucloud.com.cn/basicmath4.jpg"},{"circleId":225,"userName":"小欧5228","type":1,"time":"2016-07-11 13:37:07","courseId":184,"videoId":652,"title":"学习记录","content":"学习了：应用题","imageGids":"http://math.ufile.ucloud.com.cn/basic11.jpg"},{"circleId":224,"userName":"小欧5228","type":1,"time":"2016-07-11 13:36:57","courseId":56,"videoId":644,"title":"学习记录","content":"学习了：实数、绝对值、比和比例","imageGids":"http://math.ufile.ucloud.com.cn/basicmath1.jpg"},{"circleId":223,"userName":"小欧5228","type":1,"time":"2016-07-11 13:35:50","courseId":188,"videoId":678,"title":"学习记录","content":"学习了：2016年联考大纲名家解读","imageGids":"http://math.ufile.ucloud.com.cn/guide1.jpg"},{"circleId":99,"userName":"小欧4738","userAvatar":"664c3a2d-8ddd-4acc-a0c3-5382f625cbb5","type":1,"time":"2016-07-11 12:55:50","courseId":188,"videoId":678,"title":"学习记录","content":"学习了：2016年联考大纲名家解读","imageGids":"http://math.ufile.ucloud.com.cn/guide1.jpg","location":"北京市,海淀区"}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * circleId : 230
     * userName : 小欧5425
     * type : 1
     * time : 2016-07-11 14:50:07
     * courseId : 70
     * videoId : 107
     * title : 学习记录
     * content : 学习了：几何考点预测及拿分策略
     * imageGids : http://math.ufile.ucloud.com.cn/basicmath7.jpg
     */

    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getApicode() {
        return apicode;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "OLaCircleModule{" +
                "message='" + message + '\'' +
                ", apicode=" + apicode +
                ", result=" + result +
                '}';
    }

    public static class ResultBean {
        private int circleId;
        private String userName;
        private int type;
        private String time;
        private int courseId;
        private int videoId;
        private String title;
        private String content;
        private String imageGids;

        public int getCircleId() {
            return circleId;
        }

        public void setCircleId(int circleId) {
            this.circleId = circleId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public int getVideoId() {
            return videoId;
        }

        public void setVideoId(int videoId) {
            this.videoId = videoId;
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

        public String getImageGids() {
            return imageGids;
        }

        public void setImageGids(String imageGids) {
            this.imageGids = imageGids;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "circleId=" + circleId +
                    ", userName='" + userName + '\'' +
                    ", type=" + type +
                    ", time='" + time + '\'' +
                    ", courseId=" + courseId +
                    ", videoId=" + videoId +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", imageGids='" + imageGids + '\'' +
                    '}';
        }
    }
}
