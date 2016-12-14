package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/7/14.
 */
public class CommentModule implements Serializable {
    /**
     * message : 成功
     * result : {"assignUser":"","content":"请输入内容\u2026","courseId":0,"id":13621,"imageGids":"ff07e0f3-3a72-49ea-ba17-a104b568b41a,4db3903e-bdd9-48a1-8ddc-7342d8964df2","isPublic":1,"location":"","praiseNumber":1,"readNumber":46,"title":"请输入标题","type":2,"userId":1017,"videoId":0,"userName":"欧啦啦","userAvatar":"1471425532624.jpg","time":"2016-12-13 16:34:07","commentList":[{"audioUrls":"movie/2016/12/14/8b08a808-df8a-41a4-8eb6-8c6ff70e7d5c.amr","content":"测试","id":254,"imageIds":"","location":"","postId":13621,"praiseNumber":0,"toUserId":"","type":2,"userId":1292,"videoImgs":"","videoUrls":"","userName":"明哥","userAvatar":"1474529387314.gif","time":"2016-12-14 16:49:30"}]}
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
         * assignUser :
         * content : 请输入内容…
         * courseId : 0
         * id : 13621
         * imageGids : ff07e0f3-3a72-49ea-ba17-a104b568b41a,4db3903e-bdd9-48a1-8ddc-7342d8964df2
         * isPublic : 1
         * location :
         * praiseNumber : 1
         * readNumber : 46
         * title : 请输入标题
         * type : 2
         * userId : 1017
         * videoId : 0
         * userName : 欧啦啦
         * userAvatar : 1471425532624.jpg
         * time : 2016-12-13 16:34:07
         * commentList : [{"audioUrls":"movie/2016/12/14/8b08a808-df8a-41a4-8eb6-8c6ff70e7d5c.amr","content":"测试","id":254,"imageIds":"","location":"","postId":13621,"praiseNumber":0,"toUserId":"","type":2,"userId":1292,"videoImgs":"","videoUrls":"","userName":"明哥","userAvatar":"1474529387314.gif","time":"2016-12-14 16:49:30"}]
         */

        private String assignUser;
        private String content;
        private int courseId;
        private int id;
        private String imageGids;
        private int isPublic;
        private String location;
        private int praiseNumber;
        private int readNumber;
        private String title;
        private int type;
        private int userId;
        private int videoId;
        private String userName;
        private String userAvatar;
        private String time;
        private List<CommentListBean> commentList;

        public String getAssignUser() {
            return assignUser;
        }

        public void setAssignUser(String assignUser) {
            this.assignUser = assignUser;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getCourseId() {
            return courseId;
        }

        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageGids() {
            return imageGids;
        }

        public void setImageGids(String imageGids) {
            this.imageGids = imageGids;
        }

        public int getIsPublic() {
            return isPublic;
        }

        public void setIsPublic(int isPublic) {
            this.isPublic = isPublic;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public int getPraiseNumber() {
            return praiseNumber;
        }

        public void setPraiseNumber(int praiseNumber) {
            this.praiseNumber = praiseNumber;
        }

        public int getReadNumber() {
            return readNumber;
        }

        public void setReadNumber(int readNumber) {
            this.readNumber = readNumber;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getVideoId() {
            return videoId;
        }

        public void setVideoId(int videoId) {
            this.videoId = videoId;
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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<CommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentList) {
            this.commentList = commentList;
        }

        public static class CommentListBean {
            /**
             * audioUrls : movie/2016/12/14/8b08a808-df8a-41a4-8eb6-8c6ff70e7d5c.amr
             * content : 测试
             * id : 254
             * imageIds :
             * location :
             * postId : 13621
             * praiseNumber : 0
             * toUserId :
             * type : 2
             * userId : 1292
             * videoImgs :
             * videoUrls :
             * userName : 明哥
             * userAvatar : 1474529387314.gif
             * time : 2016-12-14 16:49:30
             */

            private String audioUrls;
            private String content;
            private int id;
            private String imageIds;
            private String location;
            private int postId;
            private int praiseNumber;
            private String toUserId;
            private String toUserName;
            private int type;
            private int userId;
            private String videoImgs;
            private String videoUrls;
            private String userName;
            private String userAvatar;
            private String time;
            private int voiceState = 0;

            public String getToUserName() {
                return toUserName;
            }

            public void setToUserName(String toUserName) {
                this.toUserName = toUserName;
            }

            public int getVoiceState() {
                return voiceState;
            }

            public void setVoiceState(int voiceState) {
                this.voiceState = voiceState;
            }

            public String getAudioUrls() {
                return audioUrls;
            }

            public void setAudioUrls(String audioUrls) {
                this.audioUrls = audioUrls;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImageIds() {
                return imageIds;
            }

            public void setImageIds(String imageIds) {
                this.imageIds = imageIds;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public int getPostId() {
                return postId;
            }

            public void setPostId(int postId) {
                this.postId = postId;
            }

            public int getPraiseNumber() {
                return praiseNumber;
            }

            public void setPraiseNumber(int praiseNumber) {
                this.praiseNumber = praiseNumber;
            }

            public String getToUserId() {
                return toUserId;
            }

            public void setToUserId(String toUserId) {
                this.toUserId = toUserId;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getVideoImgs() {
                return videoImgs;
            }

            public void setVideoImgs(String videoImgs) {
                this.videoImgs = videoImgs;
            }

            public String getVideoUrls() {
                return videoUrls;
            }

            public void setVideoUrls(String videoUrls) {
                this.videoUrls = videoUrls;
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

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
