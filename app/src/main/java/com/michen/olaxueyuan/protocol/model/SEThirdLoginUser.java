package com.michen.olaxueyuan.protocol.model;

/**
 * <pre>
 *     author : mingge
 *     time   : 2017/04/26
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class SEThirdLoginUser {
    /**
     * message : 成功
     * data : {"id":0,"name":null,"sex":null,"age":0,"phone":"15311450124","passwd":"123123","email":null,"birthday":null,"qq":null,"regtime":null,"logintime":1445257362174,"avator":null,"sign":null,"level":null,"honor":null,"learntime":null,"status":null,"examtype":null,"yzm":null,"isActive":1,"realName":null,"infoPerfectLev":null}
     * apicode : 10000
     */

    private String message;
    private DataBean data;
    private int apicode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getApicode() {
        return apicode;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    public static class DataBean {
        /**
         * id : 0
         * name : null
         * sex : null
         * age : 0
         * phone : 15311450124
         * passwd : 123123
         * email : null
         * birthday : null
         * qq : null
         * regtime : null
         * logintime : 1445257362174
         * avator : null
         * sign : null
         * level : null
         * honor : null
         * learntime : null
         * status : null
         * examtype : null
         * yzm : null
         * isActive : 1
         * realName : null
         * infoPerfectLev : null
         */

        private String id;
        private String name;
        private String sex;
        private String age;
        private String phone;
        private String passwd;
        private String email;
        private String birthday;
        private String qq;
        private String regtime;
        private long logintime;
        private String avator;
        private String sign;
        private String level;
        private String honor;
        private String learntime;
        private String status;
        private String examtype;
        private String yzm;
        private int isActive;
        private String realName;
        private String infoPerfectLev;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPasswd() {
            return passwd;
        }

        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getRegtime() {
            return regtime;
        }

        public void setRegtime(String regtime) {
            this.regtime = regtime;
        }

        public long getLogintime() {
            return logintime;
        }

        public void setLogintime(long logintime) {
            this.logintime = logintime;
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

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getHonor() {
            return honor;
        }

        public void setHonor(String honor) {
            this.honor = honor;
        }

        public String getLearntime() {
            return learntime;
        }

        public void setLearntime(String learntime) {
            this.learntime = learntime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getExamtype() {
            return examtype;
        }

        public void setExamtype(String examtype) {
            this.examtype = examtype;
        }

        public String getYzm() {
            return yzm;
        }

        public void setYzm(String yzm) {
            this.yzm = yzm;
        }

        public int getIsActive() {
            return isActive;
        }

        public void setIsActive(int isActive) {
            this.isActive = isActive;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getInfoPerfectLev() {
            return infoPerfectLev;
        }

        public void setInfoPerfectLev(String infoPerfectLev) {
            this.infoPerfectLev = infoPerfectLev;
        }
    }
}
