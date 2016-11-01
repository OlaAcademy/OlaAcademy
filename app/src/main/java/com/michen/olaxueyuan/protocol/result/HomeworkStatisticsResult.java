package com.michen.olaxueyuan.protocol.result;

import java.util.List;

/**
 * Created by mingge on 2016/10/31.
 */

public class HomeworkStatisticsResult {
    /**
     * message : 成功
     * result : {"unfinishedCount":902,"finishedCount":10,"correctness":62,"statisticsList":[{"userId":381,"userName":"小欧4738","userAvatar":"1471223596772.jpg","finished":58},{"userId":382,"userName":"小欧","userAvatar":"default.jpg","finished":58},{"userId":383,"userName":"小欧2034","userAvatar":"default.jpg","finished":0},{"userId":384,"userName":"小欧0270","userAvatar":"default.jpg","finished":66}]}
     * apicode : 10000
     */

    private String message;
    /**
     * unfinishedCount : 902
     * finishedCount : 10
     * correctness : 62
     * statisticsList : [{"userId":381,"userName":"小欧4738","userAvatar":"1471223596772.jpg","finished":58},{"userId":382,"userName":"小欧","userAvatar":"default.jpg","finished":58},{"userId":383,"userName":"小欧2034","userAvatar":"default.jpg","finished":0},{"userId":384,"userName":"小欧0270","userAvatar":"default.jpg","finished":66}]
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
        private int unfinishedCount;
        private int finishedCount;
        private int correctness;
        /**
         * userId : 381
         * userName : 小欧4738
         * userAvatar : 1471223596772.jpg
         * finished : 58
         */

        private List<StatisticsListBean> statisticsList;

        public int getUnfinishedCount() {
            return unfinishedCount;
        }

        public void setUnfinishedCount(int unfinishedCount) {
            this.unfinishedCount = unfinishedCount;
        }

        public int getFinishedCount() {
            return finishedCount;
        }

        public void setFinishedCount(int finishedCount) {
            this.finishedCount = finishedCount;
        }

        public int getCorrectness() {
            return correctness;
        }

        public void setCorrectness(int correctness) {
            this.correctness = correctness;
        }

        public List<StatisticsListBean> getStatisticsList() {
            return statisticsList;
        }

        public void setStatisticsList(List<StatisticsListBean> statisticsList) {
            this.statisticsList = statisticsList;
        }

        public static class StatisticsListBean {
            private int userId;
            private String userName;
            private String userAvatar;
            private int finished;

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

            public int getFinished() {
                return finished;
            }

            public void setFinished(int finished) {
                this.finished = finished;
            }
        }
    }
}
