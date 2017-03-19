package com.michen.olaxueyuan.protocol.result;

import java.util.List;

/**
 * Created by mingge on 17/1/4.
 */

public class UserPostListResult {
    /**
     * message : 成功
     * result : {"id":381,"name":"欧拉技术支持","avator":"78de30c6-2234-4ac0-af2d-0f0b5f792f91","sign":"123","deployList":[{"assignUser":"","commentNumber":0,"content":"测试结果表明的立场上","courseId":0,"id":13613,"imageGids":"4c4deb5e-7059-45e7-afbe-1751ea5e05e9","isPublic":1,"location":"","praiseNumber":1,"readNumber":29,"title":"测试","type":2,"userId":381,"userName":"欧拉技术支持","userAvatar":"78de30c6-2234-4ac0-af2d-0f0b5f792f91","videoId":0,"time":"2016-12-11 10:45:04"}],"replyList":[{"assignUser":"","commentNumber":0,"content":"路过咖啡厅，看见一个人在看视频学习，突然感觉好眼熟，仔细一看，原来是用欧拉联考学习","courseId":0,"id":4233,"imageGids":"","isPublic":1,"location":"北京市朝阳区","praiseNumber":13,"readNumber":17,"title":"路过咖啡厅，看见一个人在看视频学习，突然感觉好眼熟，仔细一看，原来是用欧拉联考学习","type":2,"userId":381,"userName":"欧拉技术支持","userAvatar":"78de30c6-2234-4ac0-af2d-0f0b5f792f91","videoId":0,"time":"2016-09-01 18:35:02"}]}
     * apicode : 10000
     */

    private String message;
    private ResultBean result;
    private int apicode;

    @Override
    public String toString() {
        return "UserPostListResult{" +
                "message='" + message + '\'' +
                ", result=" + result +
                ", apicode=" + apicode +
                '}';
    }

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
         * id : 381
         * name : 欧拉技术支持
         * avator : 78de30c6-2234-4ac0-af2d-0f0b5f792f91
         * sign : 123
         * deployList : [{"assignUser":"","commentNumber":0,"content":"测试结果表明的立场上","courseId":0,"id":13613,"imageGids":"4c4deb5e-7059-45e7-afbe-1751ea5e05e9","isPublic":1,"location":"","praiseNumber":1,"readNumber":29,"title":"测试","type":2,"userId":381,"userName":"欧拉技术支持","userAvatar":"78de30c6-2234-4ac0-af2d-0f0b5f792f91","videoId":0,"time":"2016-12-11 10:45:04"}]
         * replyList : [{"assignUser":"","commentNumber":0,"content":"路过咖啡厅，看见一个人在看视频学习，突然感觉好眼熟，仔细一看，原来是用欧拉联考学习","courseId":0,"id":4233,"imageGids":"","isPublic":1,"location":"北京市朝阳区","praiseNumber":13,"readNumber":17,"title":"路过咖啡厅，看见一个人在看视频学习，突然感觉好眼熟，仔细一看，原来是用欧拉联考学习","type":2,"userId":381,"userName":"欧拉技术支持","userAvatar":"78de30c6-2234-4ac0-af2d-0f0b5f792f91","videoId":0,"time":"2016-09-01 18:35:02"}]
         */

        private int id;
        private String name;
        private String avator;
        private String sign;
        private String phone;
        private String role;
        private int attendStatus;
        private int attendNum;
        private int followerNum;
        private List<DeployListBean> deployList;
        private List<DeployListBean> replyList;
//        private List<ReplyListBean> replyList;

        @Override
        public String toString() {
            return "ResultBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", avator='" + avator + '\'' +
                    ", sign='" + sign + '\'' +
                    ", phone='" + phone + '\'' +
                    ", role='" + role + '\'' +
                    ", attendStatus=" + attendStatus +
                    ", attendNum=" + attendNum +
                    ", followerNum=" + followerNum +
                    ", deployList=" + deployList +
                    ", replyList=" + replyList +
                    '}';
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public int getAttendStatus() {
            return attendStatus;
        }

        public void setAttendStatus(int attendStatus) {
            this.attendStatus = attendStatus;
        }

        public int getAttendNum() {
            return attendNum;
        }

        public void setAttendNum(int attendNum) {
            this.attendNum = attendNum;
        }

        public int getFollowerNum() {
            return followerNum;
        }

        public void setFollowerNum(int followerNum) {
            this.followerNum = followerNum;
        }

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

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public List<DeployListBean> getDeployList() {
            return deployList;
        }

        public void setDeployList(List<DeployListBean> deployList) {
            this.deployList = deployList;
        }

        public List<DeployListBean> getReplyList() {
            return replyList;
        }

        public void setReplyList(List<DeployListBean> replyList) {
            this.replyList = replyList;
        }

        public static class DeployListBean {
            /**
             * assignUser :
             * commentNumber : 0
             * content : 测试结果表明的立场上
             * courseId : 0
             * id : 13613
             * imageGids : 4c4deb5e-7059-45e7-afbe-1751ea5e05e9
             * isPublic : 1
             * location :
             * praiseNumber : 1
             * readNumber : 29
             * title : 测试
             * type : 2
             * userId : 381
             * userName : 欧拉技术支持
             * userAvatar : 78de30c6-2234-4ac0-af2d-0f0b5f792f91
             * videoId : 0
             * time : 2016-12-11 10:45:04
             */

            private String assignUser;
            private int commentNumber;
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
            private String userName;
            private String userAvatar;
            private int videoId;
            private String time;

            @Override
            public String toString() {
                return "DeployListBean{" +
                        "assignUser='" + assignUser + '\'' +
                        ", commentNumber=" + commentNumber +
                        ", content='" + content + '\'' +
                        ", courseId=" + courseId +
                        ", id=" + id +
                        ", imageGids='" + imageGids + '\'' +
                        ", isPublic=" + isPublic +
                        ", location='" + location + '\'' +
                        ", praiseNumber=" + praiseNumber +
                        ", readNumber=" + readNumber +
                        ", title='" + title + '\'' +
                        ", type=" + type +
                        ", userId=" + userId +
                        ", userName='" + userName + '\'' +
                        ", userAvatar='" + userAvatar + '\'' +
                        ", videoId=" + videoId +
                        ", time='" + time + '\'' +
                        '}';
            }

            public String getAssignUser() {
                return assignUser;
            }

            public void setAssignUser(String assignUser) {
                this.assignUser = assignUser;
            }

            public int getCommentNumber() {
                return commentNumber;
            }

            public void setCommentNumber(int commentNumber) {
                this.commentNumber = commentNumber;
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

            public int getVideoId() {
                return videoId;
            }

            public void setVideoId(int videoId) {
                this.videoId = videoId;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class ReplyListBean {
            /**
             * assignUser :
             * commentNumber : 0
             * content : 路过咖啡厅，看见一个人在看视频学习，突然感觉好眼熟，仔细一看，原来是用欧拉联考学习
             * courseId : 0
             * id : 4233
             * imageGids :
             * isPublic : 1
             * location : 北京市朝阳区
             * praiseNumber : 13
             * readNumber : 17
             * title : 路过咖啡厅，看见一个人在看视频学习，突然感觉好眼熟，仔细一看，原来是用欧拉联考学习
             * type : 2
             * userId : 381
             * userName : 欧拉技术支持
             * userAvatar : 78de30c6-2234-4ac0-af2d-0f0b5f792f91
             * videoId : 0
             * time : 2016-09-01 18:35:02
             */

            private String assignUser;
            private int commentNumber;
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
            private String userName;
            private String userAvatar;
            private int videoId;
            private String time;

            public String getAssignUser() {
                return assignUser;
            }

            public void setAssignUser(String assignUser) {
                this.assignUser = assignUser;
            }

            public int getCommentNumber() {
                return commentNumber;
            }

            public void setCommentNumber(int commentNumber) {
                this.commentNumber = commentNumber;
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

            public int getVideoId() {
                return videoId;
            }

            public void setVideoId(int videoId) {
                this.videoId = videoId;
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
