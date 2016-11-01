package com.michen.olaxueyuan.protocol.result;

/**
 * Created by mingge on 2016/9/27.
 */

public class CheckinStatusResult extends ServiceResult {
    /**
     * message : 成功
     * result : {"status":1,"lastSignIn":"2016-10-18","signInDays":"6","coin":"263","todayCoin":0,"profileTask":0,"vipTask":1,"courseTask":1}
     * apicode : 10000
     */

    private String message;
    /**
     * status : 1
     * lastSignIn : 2016-10-18
     * signInDays : 6
     * coin : 263
     * todayCoin : 0
     * profileTask : 0
     * vipTask : 1
     * courseTask : 1
     */

    private ResultEntity result;
    private int apicode;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    public String getMessage() {
        return message;
    }

    public ResultEntity getResult() {
        return result;
    }

    public int getApicode() {
        return apicode;
    }

    public static class ResultEntity {
        private int status;
        private String lastSignIn;
        private String signInDays;
        private int coin;
        private int todayCoin;
        private int profileTask;
        private int vipTask;
        private int courseTask;

        public void setStatus(int status) {
            this.status = status;
        }

        public void setLastSignIn(String lastSignIn) {
            this.lastSignIn = lastSignIn;
        }

        public void setSignInDays(String signInDays) {
            this.signInDays = signInDays;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }

        public void setTodayCoin(int todayCoin) {
            this.todayCoin = todayCoin;
        }

        public void setProfileTask(int profileTask) {
            this.profileTask = profileTask;
        }

        public void setVipTask(int vipTask) {
            this.vipTask = vipTask;
        }

        public void setCourseTask(int courseTask) {
            this.courseTask = courseTask;
        }

        public int getStatus() {
            return status;
        }

        public String getLastSignIn() {
            return lastSignIn;
        }

        public String getSignInDays() {
            return signInDays;
        }

        public int getCoin() {
            return coin;
        }

        public int getTodayCoin() {
            return todayCoin;
        }

        public int getProfileTask() {
            return profileTask;
        }

        public int getVipTask() {
            return vipTask;
        }

        public int getCourseTask() {
            return courseTask;
        }
    }
}
