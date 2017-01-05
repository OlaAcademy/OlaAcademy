package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/7/14.
 */
public class CommentModule implements Serializable {

    @Override
    public String toString() {
        return "CommentModule{" +
                "message='" + message + '\'' +
                ", apicode=" + apicode +
                ", result=" + result +
                '}';
    }

    /**
     * message : 成功
     * result : [{"commentId":255,"userId":1292,"userName":"明哥","userAvatar":"1474529387314.gif","location":"","toUserId":"754","toUserName":"微何","content":"测试","audioUrls":"movie/2016/12/14/7cd34d07-f34a-4a20-b54a-f4a091faaa6b.amr","praiseNumber":0,"time":"2016-12-14 17:56:45"},{"commentId":247,"userId":754,"userName":"微何","userAvatar":"1c84834c-1d69-47fb-a009-f426c6ec6d83","location":"北京市,海淀区","content":"测试结果","imageIds":"","videoUrls":"movie/2016/12/13/932c92b4-45ee-46b9-a77f-860a0a3767fc.mp4","videoImgs":"movie/2016/12/13/932c92b4-45ee-46b9-a77f-860a0a3767fc.jpg","audioUrls":"","praiseNumber":0,"time":"2016-12-13 13:29:09"},{"commentId":246,"userId":754,"userName":"微何","userAvatar":"1c84834c-1d69-47fb-a009-f426c6ec6d83","location":"北京市,海淀区","content":"测试","imageIds":"","videoUrls":"movie/2016/12/13/039e26bf-445b-41c6-8b66-749af19ffbfc.mp4","videoImgs":"movie/2016/12/13/039e26bf-445b-41c6-8b66-749af19ffbfc.jpg","audioUrls":"","praiseNumber":0,"time":"2016-12-13 13:19:28"},{"commentId":244,"userId":1292,"userName":"明哥","userAvatar":"1474529387314.gif","location":"","toUserId":"754","toUserName":"微何","content":"5757554","praiseNumber":0,"time":"2016-12-13 10:05:38"},{"commentId":243,"userId":1292,"userName":"明哥","userAvatar":"1474529387314.gif","location":"","content":"写作","praiseNumber":0,"time":"2016-12-13 10:05:18"},{"commentId":242,"userId":1292,"userName":"明哥","userAvatar":"1474529387314.gif","location":"","content":"测试","praiseNumber":0,"time":"2016-12-13 10:04:37"},{"commentId":241,"userId":1292,"userName":"明哥","userAvatar":"1474529387314.gif","location":"","toUserId":"754","toUserName":"微何","content":"测试","praiseNumber":0,"time":"2016-12-13 10:00:09"},{"commentId":240,"userId":754,"userName":"微何","userAvatar":"1c84834c-1d69-47fb-a009-f426c6ec6d83","location":"北京市,海淀区","content":"测试","imageIds":"","videoUrls":"","videoImgs":"","audioUrls":"movie/2016/12/12/3388013d-0932-4067-b162-332cfc8eeea8.mp3","praiseNumber":0,"time":"2016-12-12 17:35:16"},{"commentId":239,"userId":754,"userName":"微何","userAvatar":"1c84834c-1d69-47fb-a009-f426c6ec6d83","location":"北京市,海淀区","content":"测试","imageIds":"","videoUrls":"","videoImgs":"","audioUrls":"","praiseNumber":0,"time":"2016-12-12 17:31:20"},{"commentId":238,"userId":754,"userName":"微何","userAvatar":"1c84834c-1d69-47fb-a009-f426c6ec6d83","location":"北京市,海淀区","content":"测试","imageIds":"","videoUrls":"","videoImgs":"","audioUrls":"","praiseNumber":0,"time":"2016-12-12 17:30:00"}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
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

    public static class ResultBean {
        @Override
        public String toString() {
            return "ResultBean{" +
                    "commentId=" + commentId +
                    ", userId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", userAvatar='" + userAvatar + '\'' +
                    ", location='" + location + '\'' +
                    ", toUserId='" + toUserId + '\'' +
                    ", toUserName='" + toUserName + '\'' +
                    ", content='" + content + '\'' +
                    ", audioUrls='" + audioUrls + '\'' +
                    ", praiseNumber=" + praiseNumber +
                    ", time='" + time + '\'' +
                    ", imageIds='" + imageIds + '\'' +
                    ", videoUrls='" + videoUrls + '\'' +
                    ", videoImgs='" + videoImgs + '\'' +
                    ", voiceState=" + voiceState +
                    ", subCount='" + subCount + '\'' +
                    '}';
        }

        /**
         * commentId : 255
         * userId : 1292
         * userName : 明哥
         * userAvatar : 1474529387314.gif
         * location :
         * toUserId : 754
         * toUserName : 微何
         * content : 测试
         * audioUrls : movie/2016/12/14/7cd34d07-f34a-4a20-b54a-f4a091faaa6b.amr
         * praiseNumber : 0
         * time : 2016-12-14 17:56:45
         * imageIds :
         * videoUrls : movie/2016/12/13/932c92b4-45ee-46b9-a77f-860a0a3767fc.mp4
         * videoImgs : movie/2016/12/13/932c92b4-45ee-46b9-a77f-860a0a3767fc.jpg
         */

        private int commentId;
        private int userId;
        private String userName;
        private String userAvatar;
        private String location;
        private String toUserId;
        private String toUserName;
        private String content;
        private String audioUrls;
        private int praiseNumber;
        private String time;
        private String imageIds;
        private String videoUrls;
        private String videoImgs;
        private int voiceState = 0;
        private String subCount;
        private String voiceTime;

        public String getVoiceTime() {
            return voiceTime;
        }

        public void setVoiceTime(String voiceTime) {
            this.voiceTime = voiceTime;
        }

        public String getSubCount() {
            return subCount;
        }

        public void setSubCount(String subCount) {
            this.subCount = subCount;
        }

        public int getVoiceState() {
            return voiceState;
        }

        public void setVoiceState(int voiceState) {
            this.voiceState = voiceState;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserAvatar() {
            return userAvatar;
        }

        public void setUserAvatar(String userAvatar) {
            this.userAvatar = userAvatar;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getToUserId() {
            return toUserId;
        }

        public void setToUserId(String toUserId) {
            this.toUserId = toUserId;
        }

        public String getToUserName() {
            return toUserName;
        }

        public void setToUserName(String toUserName) {
            this.toUserName = toUserName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAudioUrls() {
            return audioUrls;
        }

        public void setAudioUrls(String audioUrls) {
            this.audioUrls = audioUrls;
        }

        public int getPraiseNumber() {
            return praiseNumber;
        }

        public void setPraiseNumber(int praiseNumber) {
            this.praiseNumber = praiseNumber;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getImageIds() {
            return imageIds;
        }

        public void setImageIds(String imageIds) {
            this.imageIds = imageIds;
        }

        public String getVideoUrls() {
            return videoUrls;
        }

        public void setVideoUrls(String videoUrls) {
            this.videoUrls = videoUrls;
        }

        public String getVideoImgs() {
            return videoImgs;
        }

        public void setVideoImgs(String videoImgs) {
            this.videoImgs = videoImgs;
        }
    }
}
