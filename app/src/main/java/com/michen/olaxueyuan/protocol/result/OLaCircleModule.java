package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 16/5/11.
 */
public class OLaCircleModule implements Serializable {
    @Override
    public String toString() {
        return "OLaCircleModule{" +
                "apicode=" + apicode +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    /**
     * apicode : 10000
     * message : 成功
     * result : [{"circleId":13636,"userId":1292,"userName":"明哥","userAvatar":"60464f71-3b08-4ba1-b4c7-4ba2af6f0597","type":2,"time":"2016-12-30 16:47:23","title":"指定回答","content":"指定回答","imageGids":"5bbf52d3-b3e8-4a6f-838f-032262984e4b,","location":"","praiseNumber":1,"readNumber":74,"commentNumber":4},{"circleId":13628,"userId":381,"userName":"欧拉技术支持","userAvatar":"78de30c6-2234-4ac0-af2d-0f0b5f792f91","type":2,"time":"2016-12-25 12:38:11","title":"测试","content":"测试你的时候才能更加考察你的时候我们就算了。我也好的","imageGids":"80f41589-9ccf-45a6-a0f4-84e5689ea7ec,f839fe3d-b361-46e3-aaeb-ad385ead6aab,24096950-71ed-4cd4-9f79-3e4b55678c7c,17b1e477-90dc-4e0e-b6c2-a374e46a18ef,95dd60b6-e66d-4046-a9bf-620b3a3b26f8,87713ef3-07be-42cd-ba61-b462c21eb2de","location":"","praiseNumber":3,"readNumber":345,"commentNumber":8},{"circleId":13621,"userId":1017,"userName":"欧啦啦","userAvatar":"1471425532624.jpg","type":2,"time":"2016-12-13 16:34:07","title":"请输入标题","content":"请输入内容\u2026","imageGids":"ff07e0f3-3a72-49ea-ba17-a104b568b41a,4db3903e-bdd9-48a1-8ddc-7342d8964df2","location":"","praiseNumber":1,"readNumber":275,"commentNumber":0},{"circleId":13620,"userId":1292,"userName":"明哥","userAvatar":"60464f71-3b08-4ba1-b4c7-4ba2af6f0597","type":2,"time":"2016-12-12 17:24:50","title":"测试哦","content":"昨天客场惨败给灰熊之后，勇士今天又奔赴明尼苏达，进行与森林狼的背靠背较量。本场比赛帕楚利亚继续休战，之前轮休的伊戈达拉和受伤的鲁尼复","imageGids":"","location":"","praiseNumber":0,"readNumber":198,"commentNumber":16},{"circleId":13619,"userId":1292,"userName":"明哥","userAvatar":"60464f71-3b08-4ba1-b4c7-4ba2af6f0597","type":2,"time":"2016-12-12 17:20:37","title":"精明","content":"凝视着人陪有食欲下午","imageGids":"","location":"","praiseNumber":0,"readNumber":3,"commentNumber":0},{"circleId":13618,"userId":1292,"userName":"明哥","userAvatar":"60464f71-3b08-4ba1-b4c7-4ba2af6f0597","type":2,"time":"2016-12-12 17:19:30","title":"标题","content":"昨天客场惨败给灰熊之后，勇士今天又奔赴明尼苏达，进行与森林狼的背靠背较量。本场比赛帕楚利亚继续休战，之前轮休的伊戈达拉和受伤的鲁尼复出。森林狼这边，别利察连续第二场缺席。最终森林狼延续了本赛季崩盘的传统，在末节被勇士打出了一波25-4的超级攻击波，勇士逆转取胜避免连败，最终比分为116-108。\n","imageGids":"","location":"","praiseNumber":0,"readNumber":8,"commentNumber":0},{"circleId":13617,"userId":1292,"userName":"明哥","userAvatar":"60464f71-3b08-4ba1-b4c7-4ba2af6f0597","type":2,"time":"2016-12-12 17:18:34","title":"测试","content":"昨天客场惨败给灰熊之后，勇士今天又奔赴明尼苏达，进行与森林狼的背靠背较量。本场比赛帕楚利亚继续休战，之前轮休的伊戈达拉和受伤的鲁尼复出。森林狼这边，别利察连续第二场缺席。最终森林狼延续了本赛季崩盘的传统，在末节被勇士打出了一波25-4的超级攻击波，勇士逆转取胜避免连败，最终比分为116-108。\n\n在上半场比赛中，虽然杜兰特手感冰凉，但汤普森一扫昨天狂铁的颓势，成为了球队进攻端的箭头，带领疲惫的球队与森林狼紧咬比分。反观森林狼这边，唐斯和维金斯的二人转让本身就舟车劳顿的勇士在防守端更加疲于奔命，这场已经是勇士本周的第5场比赛了，半场结束实力更强的勇士只领先2分，60-58。\n\n易边再战，勇士开始全队哑火，森林狼在一波强势反扑之后开始拉开分差，好在库里连续几球止血，继续维持着比赛的悬念，三节结束森林狼88-78领先，前三节结束后杜","imageGids":"","location":"","praiseNumber":0,"readNumber":3,"commentNumber":0}]
     */

    private int apicode;
    private String message;
    private List<ResultBean> result;

    public int getApicode() {
        return apicode;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
                    "circleId=" + circleId +
                    ", userId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", userAvatar='" + userAvatar + '\'' +
                    ", type=" + type +
                    ", time='" + time + '\'' +
                    ", title='" + title + '\'' +
                    ", content='" + content + '\'' +
                    ", imageGids='" + imageGids + '\'' +
                    ", location='" + location + '\'' +
                    ", praiseNumber=" + praiseNumber +
                    ", readNumber=" + readNumber +
                    ", commentNumber=" + commentNumber +
                    '}';
        }

        /**
         * circleId : 13636
         * userId : 1292
         * userName : 明哥
         * userAvatar : 60464f71-3b08-4ba1-b4c7-4ba2af6f0597
         * type : 2
         * time : 2016-12-30 16:47:23
         * title : 指定回答
         * content : 指定回答
         * imageGids : 5bbf52d3-b3e8-4a6f-838f-032262984e4b,
         * location :
         * praiseNumber : 1
         * readNumber : 74
         * commentNumber : 4
         */

        private int circleId;
        private int userId;
        private String userName;
        private String userAvatar;
        private int type;
        private String time;
        private String title;
        private String content;
        private String imageGids;
        private String location;
        private int praiseNumber;
        private int readNumber;
        private int commentNumber;

        public int getCircleId() {
            return circleId;
        }

        public void setCircleId(int circleId) {
            this.circleId = circleId;
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

        public int getCommentNumber() {
            return commentNumber;
        }

        public void setCommentNumber(int commentNumber) {
            this.commentNumber = commentNumber;
        }
    }
}
