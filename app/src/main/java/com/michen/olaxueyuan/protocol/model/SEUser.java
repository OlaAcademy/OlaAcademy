package com.michen.olaxueyuan.protocol.model;


import java.io.Serializable;

/**
 * Created by tianxiaopeng on 15-1-10.
 */
public class SEUser implements Serializable {

    private int coin;//1元=20欧拉币
    private String vipTime;
    private String realName;
    private String avator;
    private String examtype;
    private String sex;
    private String id;
    private String age;
    private String sign;
    private String signInDays;
    private int isActive;// 1 学生 2 老师
    private String phone;
    private String name;
    private String passwd;
    private String email;
    private String local;

    public int getCoin() {
        return coin;
    }

    public SEUser setCoin(int coin) {
        this.coin = coin;
        return this;
    }

    public SEUser setVipTime(String vipTime) {
        this.vipTime = vipTime;
        return this;
    }

    public String getRealName() {
        return realName;
    }

    public SEUser setRealName(String realName) {
        this.realName = realName;
        return this;
    }

    public String getExamtype() {
        return examtype;
    }

    public SEUser setExamtype(String examtype) {
        this.examtype = examtype;
        return this;
    }

    public String getSignInDays() {
        return signInDays;
    }

    public SEUser setSignInDays(String signInDays) {
        this.signInDays = signInDays;
        return this;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    private boolean _isDataDirty = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getVipTime() {
        return vipTime;
    }

    public boolean is_isDataDirty() {
        return _isDataDirty;
    }

    public void markDataDirty() {
        _isDataDirty = true;
    }

    public boolean checkAndCleanDataDirty() {
        boolean isDataDirty = _isDataDirty;
        _isDataDirty = false;
        return isDataDirty;
    }

}

