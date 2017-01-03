package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/8/26.
 */
public class PostDetailModule implements Serializable {

    /**
     * message : 成功
     * result : {"assignUser":"381","commentNumber":4,"content":"指定回答","courseId":0,"id":13636,"imageGids":"5bbf52d3-b3e8-4a6f-838f-032262984e4b,","isPublic":1,"location":"","praiseNumber":1,"readNumber":60,"title":"指定回答","type":2,"userId":1292,"videoId":0,"userName":"明哥","userAvatar":"60464f71-3b08-4ba1-b4c7-4ba2af6f0597","isPraised":0,"time":"2016-12-30 16:47:23","commentList":[{"audioUrls":"","content":"图片","id":280,"imageIds":"915d19d3-419d-43e8-9bb4-a3631bcef283,832831c6-e158-4ecd-afa3-de5fbe26e932,","isRead":1,"location":"","postId":13636,"praiseNumber":0,"toUserId":"","type":2,"userId":1292,"videoImgs":"","videoUrls":"","userName":"明哥","userAvatar":"60464f71-3b08-4ba1-b4c7-4ba2af6f0597","time":"2016-12-31 11:39:02"},{"audioUrls":"","content":"上面图片的清晰度再提高一下","id":279,"imageIds":"","isRead":1,"location":"北京市,海淀区","postId":13636,"praiseNumber":0,"toUserId":"","type":2,"userId":1017,"videoImgs":"","videoUrls":"","userName":"欧啦啦","userAvatar":"1471425532624.jpg","time":"2016-12-30 21:19:56"},{"audioUrls":"","content":"我是4738","id":278,"imageIds":"","isRead":1,"location":"","postId":13636,"praiseNumber":0,"toUserId":"","type":2,"userId":378,"videoImgs":"","videoUrls":"","userName":"小欧4743","userAvatar":"1468744004262.jpg","time":"2016-12-30 16:50:34"},{"audioUrls":"","content":"我回答了","id":277,"imageIds":"","isRead":1,"location":"北京市,海淀区","postId":13636,"praiseNumber":0,"toUserId":"","type":2,"userId":381,"videoImgs":"","videoUrls":"","userName":"欧拉技术支持","userAvatar":"78de30c6-2234-4ac0-af2d-0f0b5f792f91","time":"2016-12-30 16:48:23"}]}
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
         * assignUser : 381
         * commentNumber : 4
         * content : 指定回答
         * courseId : 0
         * id : 13636
         * imageGids : 5bbf52d3-b3e8-4a6f-838f-032262984e4b,
         * isPublic : 1
         * location :
         * praiseNumber : 1
         * readNumber : 60
         * title : 指定回答
         * type : 2
         * userId : 1292
         * videoId : 0
         * userName : 明哥
         * userAvatar : 60464f71-3b08-4ba1-b4c7-4ba2af6f0597
         * isPraised : 0
         * time : 2016-12-30 16:47:23
         * commentList : [{"audioUrls":"","content":"图片","id":280,"imageIds":"915d19d3-419d-43e8-9bb4-a3631bcef283,832831c6-e158-4ecd-afa3-de5fbe26e932,","isRead":1,"location":"","postId":13636,"praiseNumber":0,"toUserId":"","type":2,"userId":1292,"videoImgs":"","videoUrls":"","userName":"明哥","userAvatar":"60464f71-3b08-4ba1-b4c7-4ba2af6f0597","time":"2016-12-31 11:39:02"},{"audioUrls":"","content":"上面图片的清晰度再提高一下","id":279,"imageIds":"","isRead":1,"location":"北京市,海淀区","postId":13636,"praiseNumber":0,"toUserId":"","type":2,"userId":1017,"videoImgs":"","videoUrls":"","userName":"欧啦啦","userAvatar":"1471425532624.jpg","time":"2016-12-30 21:19:56"},{"audioUrls":"","content":"我是4738","id":278,"imageIds":"","isRead":1,"location":"","postId":13636,"praiseNumber":0,"toUserId":"","type":2,"userId":378,"videoImgs":"","videoUrls":"","userName":"小欧4743","userAvatar":"1468744004262.jpg","time":"2016-12-30 16:50:34"},{"audioUrls":"","content":"我回答了","id":277,"imageIds":"","isRead":1,"location":"北京市,海淀区","postId":13636,"praiseNumber":0,"toUserId":"","type":2,"userId":381,"videoImgs":"","videoUrls":"","userName":"欧拉技术支持","userAvatar":"78de30c6-2234-4ac0-af2d-0f0b5f792f91","time":"2016-12-30 16:48:23"}]
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
        private int videoId;
        private String userName;
        private String userAvatar;
        private int isPraised;
        private String time;
        private List<CommentListBean> commentList;

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

        public int getIsPraised() {
            return isPraised;
        }

        public void setIsPraised(int isPraised) {
            this.isPraised = isPraised;
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
             * audioUrls :
             * content : 图片
             * id : 280
             * imageIds : 915d19d3-419d-43e8-9bb4-a3631bcef283,832831c6-e158-4ecd-afa3-de5fbe26e932,
             * isRead : 1
             * location :
             * postId : 13636
             * praiseNumber : 0
             * toUserId :
             * type : 2
             * userId : 1292
             * videoImgs :
             * videoUrls :
             * userName : 明哥
             * userAvatar : 60464f71-3b08-4ba1-b4c7-4ba2af6f0597
             * time : 2016-12-31 11:39:02
             */

            private String audioUrls;
            private String content;
            private int id;
            private String imageIds;
            private int isRead;
            private String location;
            private int postId;
            private int praiseNumber;
            private String toUserId;
            private int type;
            private int userId;
            private String videoImgs;
            private String videoUrls;
            private String userName;
            private String userAvatar;
            private String time;

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

            public int getIsRead() {
                return isRead;
            }

            public void setIsRead(int isRead) {
                this.isRead = isRead;
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
