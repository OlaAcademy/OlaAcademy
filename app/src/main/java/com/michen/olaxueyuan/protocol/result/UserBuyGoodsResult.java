package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 16/6/13.
 * 我的购买课程
 */
public class UserBuyGoodsResult extends ServiceResult implements Serializable{
    /**
     * message : 成功
     * result : [{"attentionnum":100,"detail":"对考试内容作全面细致的讲解,温故知新，循序渐进，打牢基础，为下一阶段的学习作好充分的准备,期末基本达到联考要求的水平。\r授课名师陈剑，清华大学博士，数学考试大纲唯一指定解析人，标准化辅导体系首创人，曾到日本、澳洲、美国、加拿大等进行国际交流学习。从事数学辅导十二载以来，面对数学纷乱无序的考法，对重点、难点、必考点的把握出神入化，令学员事半功倍;孜孜不倦、高度负责的态度以及对考题的精准预测，令考生受益无穷。多年来学员有\u201c容易的通俗易懂，疑难的分析透彻，零基础的学有所获，数学高手另有启发\u201d的评价，每年超高的命中率使无数零基础考生创造了轻取高分的奇迹，是业界王牌数学老师。","id":1,"image":"http://banner.ufile.ucloud.com.cn/logo_mx.png","leanstage":"5月17日-12月31日","name":"2017年数学周末班基础课程","org":"幂学教育版权所有","paynum":939,"price":0,"suitto":"教材版本：《联考综合能力数学高分指南》","totaltime":2200,"type":"1","url":"http://commodity.ufile.ucloud.com.cn/math.jpg","videonum":27},{"attentionnum":100,"detail":"掌握主要复习要点，确保能够达到考试优良水平。扫荡近年真题，以真题为核心，全程知识点透彻学习，熟悉答题技巧，全面提升考场应试能力。\r授课名师杨武金，中国人民大学哲学院教授，博士研究生导师。逻辑学硕士、博士、博士后。中国逻辑学专业第一个博士后。从事专业学位逻辑辅导近20年，经验丰富，对逻辑教学辅导的输入研究，深得广大学生爱戴、好评。主讲幂学逻辑重要课程。","id":3,"image":"http://banner.ufile.ucloud.com.cn/logo_mx.png","leanstage":"5月17日-12月31日","name":"2016年逻辑真题密训班课程","org":"幂学教育版权所有","paynum":539,"price":0,"suitto":"教材版本：《逻辑历年真题名家详解》","totaltime":710,"type":"1","url":"http://commodity.ufile.ucloud.com.cn/logic.jpg","videonum":8}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * attentionnum : 100
     * detail : 对考试内容作全面细致的讲解,温故知新，循序渐进，打牢基础，为下一阶段的学习作好充分的准备,期末基本达到联考要求的水平。授课名师陈剑，清华大学博士，数学考试大纲唯一指定解析人，标准化辅导体系首创人，曾到日本、澳洲、美国、加拿大等进行国际交流学习。从事数学辅导十二载以来，面对数学纷乱无序的考法，对重点、难点、必考点的把握出神入化，令学员事半功倍;孜孜不倦、高度负责的态度以及对考题的精准预测，令考生受益无穷。多年来学员有“容易的通俗易懂，疑难的分析透彻，零基础的学有所获，数学高手另有启发”的评价，每年超高的命中率使无数零基础考生创造了轻取高分的奇迹，是业界王牌数学老师。
     * id : 1
     * image : http://banner.ufile.ucloud.com.cn/logo_mx.png
     * leanstage : 5月17日-12月31日
     * name : 2017年数学周末班基础课程
     * org : 幂学教育版权所有
     * paynum : 939
     * price : 0
     * suitto : 教材版本：《联考综合能力数学高分指南》
     * totaltime : 2200
     * type : 1
     * url : http://commodity.ufile.ucloud.com.cn/math.jpg
     * videonum : 27
     */

    private List<SystemCourseResultEntity> result;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setApicode(int apicode) {
        this.apicode = apicode;
    }

    public void setResult(List<SystemCourseResultEntity> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public int getApicode() {
        return apicode;
    }

    public List<SystemCourseResultEntity> getResult() {
        return result;
    }

   /* public static class ResultEntity implements Serializable{
        private int attentionnum;
        private String detail;
        private int id;
        private String image;
        private String leanstage;
        private String name;
        private String org;
        private int paynum;
        private int price;
        private String suitto;
        private int totaltime;
        private String type;
        private String url;
        private int videonum;

        public void setAttentionnum(int attentionnum) {
            this.attentionnum = attentionnum;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setLeanstage(String leanstage) {
            this.leanstage = leanstage;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setOrg(String org) {
            this.org = org;
        }

        public void setPaynum(int paynum) {
            this.paynum = paynum;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public void setSuitto(String suitto) {
            this.suitto = suitto;
        }

        public void setTotaltime(int totaltime) {
            this.totaltime = totaltime;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setVideonum(int videonum) {
            this.videonum = videonum;
        }

        public int getAttentionnum() {
            return attentionnum;
        }

        public String getDetail() {
            return detail;
        }

        public int getId() {
            return id;
        }

        public String getImage() {
            return image;
        }

        public String getLeanstage() {
            return leanstage;
        }

        public String getName() {
            return name;
        }

        public String getOrg() {
            return org;
        }

        public int getPaynum() {
            return paynum;
        }

        public int getPrice() {
            return price;
        }

        public String getSuitto() {
            return suitto;
        }

        public int getTotaltime() {
            return totaltime;
        }

        public String getType() {
            return type;
        }

        public String getUrl() {
            return url;
        }

        public int getVideonum() {
            return videonum;
        }
    }*/
}
