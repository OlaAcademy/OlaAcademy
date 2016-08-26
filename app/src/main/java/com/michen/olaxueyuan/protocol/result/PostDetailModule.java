package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/8/26.
 */
public class PostDetailModule implements Serializable {

    /**
     * message : 成功
     * result : {"content":"为满足广大备考者学习需求，欧拉学院将进行一次送课程活动，课程内容包括，陈剑数学基础课程，田然写作有效性分析课程，同学们可回复service@olaxueyuan.com，将您的id和手机号发于邮箱即可，如：小欧0880，13911110880；同时记得给予我们好评哦～","courseId":0,"id":3231,"imageGids":"02f3f5ae-918d-40ac-8ec2-78e573f1e748","location":"北京市朝阳区","praiseNumber":6,"readNumber":1,"title":"欧拉圈","type":2,"userId":517,"videoId":0,"userName":"欧拉客服","userAvatar":"59ba012f-c2d3-4564-889d-76b80c8daef4","time":"2016-08-23 16:49:22","commentList":[{"content":"已发","id":65,"location":"","postId":3231,"praiseNumber":0,"toUserId":"517","type":2,"userId":1017,"userName":"小欧咩咩","userAvatar":"1471425532624.jpg","toUserName":"欧拉客服","time":"2016-08-23 17:27:45"},{"content":"已发","id":64,"location":"北京市,朝阳区","postId":3231,"praiseNumber":0,"toUserId":"517","type":2,"userId":422,"userName":"小欧8460-豆豆","userAvatar":"","toUserName":"欧拉客服","time":"2016-08-23 17:27:15"},{"content":"已发","id":63,"location":"","postId":3231,"praiseNumber":0,"toUserId":"517","type":2,"userId":990,"userName":"小欧6900","userAvatar":"1471324624797.jpg","toUserName":"欧拉客服","time":"2016-08-23 17:26:51"},{"content":"活动名额20人","id":62,"location":"北京市,海淀区","postId":3231,"praiseNumber":0,"toUserId":"","type":2,"userId":517,"userName":"欧拉客服","userAvatar":"59ba012f-c2d3-4564-889d-76b80c8daef4","time":"2016-08-23 16:52:57"}]}
     * apicode : 10000
     */

    private String message;
    /**
     * content : 为满足广大备考者学习需求，欧拉学院将进行一次送课程活动，课程内容包括，陈剑数学基础课程，田然写作有效性分析课程，同学们可回复service@olaxueyuan.com，将您的id和手机号发于邮箱即可，如：小欧0880，13911110880；同时记得给予我们好评哦～
     * courseId : 0
     * id : 3231
     * imageGids : 02f3f5ae-918d-40ac-8ec2-78e573f1e748
     * location : 北京市朝阳区
     * praiseNumber : 6
     * readNumber : 1
     * title : 欧拉圈
     * type : 2
     * userId : 517
     * videoId : 0
     * userName : 欧拉客服
     * userAvatar : 59ba012f-c2d3-4564-889d-76b80c8daef4
     * time : 2016-08-23 16:49:22
     * commentList : [{"content":"已发","id":65,"location":"","postId":3231,"praiseNumber":0,"toUserId":"517","type":2,"userId":1017,"userName":"小欧咩咩","userAvatar":"1471425532624.jpg","toUserName":"欧拉客服","time":"2016-08-23 17:27:45"},{"content":"已发","id":64,"location":"北京市,朝阳区","postId":3231,"praiseNumber":0,"toUserId":"517","type":2,"userId":422,"userName":"小欧8460-豆豆","userAvatar":"","toUserName":"欧拉客服","time":"2016-08-23 17:27:15"},{"content":"已发","id":63,"location":"","postId":3231,"praiseNumber":0,"toUserId":"517","type":2,"userId":990,"userName":"小欧6900","userAvatar":"1471324624797.jpg","toUserName":"欧拉客服","time":"2016-08-23 17:26:51"},{"content":"活动名额20人","id":62,"location":"北京市,海淀区","postId":3231,"praiseNumber":0,"toUserId":"","type":2,"userId":517,"userName":"欧拉客服","userAvatar":"59ba012f-c2d3-4564-889d-76b80c8daef4","time":"2016-08-23 16:52:57"}]
     */

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
        private String content;
        private int courseId;
        private int id;
        private String imageGids;
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
        /**
         * content : 已发
         * id : 65
         * location :
         * postId : 3231
         * praiseNumber : 0
         * toUserId : 517
         * type : 2
         * userId : 1017
         * userName : 小欧咩咩
         * userAvatar : 1471425532624.jpg
         * toUserName : 欧拉客服
         * time : 2016-08-23 17:27:45
         */

        private List<CommentListBean> commentList;

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
            private String content;
            private int id;
            private String location;
            private int postId;
            private int praiseNumber;
            private String toUserId;
            private int type;
            private int userId;
            private String userName;
            private String userAvatar;
            private String toUserName;
            private String time;

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

            public String getToUserName() {
                return toUserName;
            }

            public void setToUserName(String toUserName) {
                this.toUserName = toUserName;
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
