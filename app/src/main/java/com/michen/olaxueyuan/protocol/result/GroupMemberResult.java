package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/11/9.
 */

public class GroupMemberResult implements Serializable {
    /**
     * message : 成功
     * result : [{"id":3250,"name":"小欧8680","sex":null,"age":0,"phone":null,"passwd":null,"email":null,"birthday":null,"qq":null,"local":null,"regtime":null,"logintime":null,"avator":"default.jpg","sign":null,"level":null,"honor":null,"learntime":null,"status":null,"examtype":null,"yzm":null,"isActive":0,"realName":null,"coin":null,"vipTime":null},{"id":3242,"name":"小欧2951","sex":null,"age":0,"phone":null,"passwd":null,"email":null,"birthday":null,"qq":null,"local":null,"regtime":null,"logintime":null,"avator":"default.jpg","sign":null,"level":null,"honor":null,"learntime":null,"status":null,"examtype":null,"yzm":null,"isActive":0,"realName":null,"coin":null,"vipTime":null}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * id : 3250
     * name : 小欧8680
     * sex : null
     * age : 0
     * phone : null
     * passwd : null
     * email : null
     * birthday : null
     * qq : null
     * local : null
     * regtime : null
     * logintime : null
     * avator : default.jpg
     * sign : null
     * level : null
     * honor : null
     * learntime : null
     * status : null
     * examtype : null
     * yzm : null
     * isActive : 0
     * realName : null
     * coin : null
     * vipTime : null
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

    public static class ResultBean {
        private int id;
        private String name;
        private Object sex;
        private int age;
        private Object phone;
        private Object passwd;
        private Object email;
        private Object birthday;
        private Object qq;
        private Object local;
        private Object regtime;
        private Object logintime;
        private String avator;
        private Object sign;
        private Object level;
        private Object honor;
        private Object learntime;
        private Object status;
        private Object examtype;
        private Object yzm;
        private int isActive;
        private Object realName;
        private Object coin;
        private Object vipTime;

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

        public Object getSex() {
            return sex;
        }

        public void setSex(Object sex) {
            this.sex = sex;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getPasswd() {
            return passwd;
        }

        public void setPasswd(Object passwd) {
            this.passwd = passwd;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public Object getQq() {
            return qq;
        }

        public void setQq(Object qq) {
            this.qq = qq;
        }

        public Object getLocal() {
            return local;
        }

        public void setLocal(Object local) {
            this.local = local;
        }

        public Object getRegtime() {
            return regtime;
        }

        public void setRegtime(Object regtime) {
            this.regtime = regtime;
        }

        public Object getLogintime() {
            return logintime;
        }

        public void setLogintime(Object logintime) {
            this.logintime = logintime;
        }

        public String getAvator() {
            return avator;
        }

        public void setAvator(String avator) {
            this.avator = avator;
        }

        public Object getSign() {
            return sign;
        }

        public void setSign(Object sign) {
            this.sign = sign;
        }

        public Object getLevel() {
            return level;
        }

        public void setLevel(Object level) {
            this.level = level;
        }

        public Object getHonor() {
            return honor;
        }

        public void setHonor(Object honor) {
            this.honor = honor;
        }

        public Object getLearntime() {
            return learntime;
        }

        public void setLearntime(Object learntime) {
            this.learntime = learntime;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getExamtype() {
            return examtype;
        }

        public void setExamtype(Object examtype) {
            this.examtype = examtype;
        }

        public Object getYzm() {
            return yzm;
        }

        public void setYzm(Object yzm) {
            this.yzm = yzm;
        }

        public int getIsActive() {
            return isActive;
        }

        public void setIsActive(int isActive) {
            this.isActive = isActive;
        }

        public Object getRealName() {
            return realName;
        }

        public void setRealName(Object realName) {
            this.realName = realName;
        }

        public Object getCoin() {
            return coin;
        }

        public void setCoin(Object coin) {
            this.coin = coin;
        }

        public Object getVipTime() {
            return vipTime;
        }

        public void setVipTime(Object vipTime) {
            this.vipTime = vipTime;
        }
    }
}
