package com.michen.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/5/24.
 */
public class CourseVideoResult extends ServiceResult implements Serializable {
    /**
     * message : 成功
     * result : {"pointId":"54","isCollect":"1","videoList":[{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/01_gainian_01.mp4","content":"古典概率","id":1,"isfree":1,"name":"课时1：实数的概念","orgname":"幂学","pic":"","playCount":40,"timeSpan":"0:02:02","tname":"陈剑","weight":"10"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/02_feifu_01.mp4","content":"事件的独立性","id":2,"isfree":1,"name":"课时2：非负性的应用Ⅰ","orgname":"幂学","pic":"","playCount":6,"timeSpan":"0:02:39","tname":"陈剑","weight":"20"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/03_feifu_02.mp4","content":"事件的独立性真题","id":3,"isfree":1,"name":"课时3：非负性的应用Ⅱ","orgname":"幂学","pic":"","playCount":8,"timeSpan":"0:06:09","tname":"陈剑","weight":"10"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/04_zhishu_01.mp4","content":"平均值、方差、标准差概念","id":4,"isfree":1,"name":"课时4：质数与合数","orgname":"幂学","pic":"","playCount":8,"timeSpan":"0:10:09","tname":"陈剑","weight":"10"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/05_zhishu_02.mp4","content":"","id":5,"isfree":1,"name":"课时5：质数的性质","orgname":"","pic":"http://www.mykepu.com:8080/pic/videopic/logo_chenxing.png","playCount":5,"timeSpan":"0:02:45","tname":"","weight":"10"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/06_jishu_01.mp4","content":"幂学教育","id":6,"isfree":1,"name":"课时6：奇数和偶数","orgname":"幂学","pic":"http://www.mykepu.com:8080/pic/videopic/logo_mixue.png","playCount":973,"timeSpan":"0:07:38","tname":"陈剑","weight":"10"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/07_fenshu_01.mp4","content":"晨星成长计划","id":7,"isfree":1,"name":"课时7：分数与小数","orgname":"","pic":"http://www.mykepu.com:8080/pic/videopic/logo_chenxing.png","playCount":62,"timeSpan":"0:06:15","tname":"","weight":"10"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/08_yueshu_01.mp4","content":"大纲词汇分析01","id":8,"isfree":1,"name":"课时8：约数与公倍数","orgname":"幂学","pic":"","playCount":695,"timeSpan":"0:08:09","tname":"何敬","weight":"10"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/09_yueshu_02.mp4","content":"大纲词汇分析02","id":9,"isfree":1,"name":"课时9：约数与倍数的应用","orgname":"幂学","pic":"","playCount":198,"timeSpan":"0:07:15","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/10_yueshu_03.mp4","content":"大纲词汇分析03","id":10,"isfree":1,"name":"课时10：公约数的应用方法","orgname":"幂学","pic":"","playCount":105,"timeSpan":"0:01:32","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/11_juedui_1.mp4","content":"大纲词汇分析04","id":11,"isfree":1,"name":"课时11：绝对值","orgname":"幂学","pic":"","playCount":117,"timeSpan":"0:15:48","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/12_juedui_2.mp4","content":"大纲词汇分析05","id":12,"isfree":1,"name":"课时12：绝对值的基本应用","orgname":"幂学","pic":"","playCount":92,"timeSpan":"0:03:35","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/13_juedui_3.mp4","content":"大纲词汇分析06","id":13,"isfree":1,"name":"课时13：绝对值的非负性","orgname":"幂学","pic":"","playCount":116,"timeSpan":"0:08:36","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/14_bili_01.mp4","content":"大纲词汇分析07","id":14,"isfree":1,"name":"课时14：比和比例的概念","orgname":"幂学","pic":"","playCount":145,"timeSpan":"0:05:39","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/15_bili_02.mp4","content":"大纲词汇分析08","id":15,"isfree":1,"name":"课时15：正比和反比的应用","orgname":"幂学","pic":"","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/18_pingjun_01.mp4","content":"大纲词汇分析11","id":18,"isfree":1,"name":"课时18：平均值的概念","orgname":"幂学","pic":"","playCount":140,"timeSpan":"0:07:31","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/19_pingjun_02.mp4","content":"大纲词汇分析12","id":19,"isfree":1,"name":"课时19：平均值的定理","orgname":"幂学","pic":"","playCount":290,"timeSpan":"0:08:36","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/20_chfen_1.mp4","content":"大纲词汇分析13","id":20,"isfree":1,"name":"课时20：条件充分性判定","orgname":"幂学","pic":"","playCount":103,"timeSpan":"0:16:51","tname":"何敬","weight":""}]}
     * apicode : 10000
     */

    private String message;

    @Override
    public String toString() {
        return "CourseVideoResult{" +
                "message='" + message + '\'' +
                ", result=" + result +
                ", apicode=" + apicode +
                ", playIndex=" + playIndex +
                ", isCollect=" + isCollect +
                ", orderStatus=" + orderStatus +
                ", playProgress=" + playProgress +
                '}';
    }

    /**
     * pointId : 54
     * isCollect : 1
     * videoList : [{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/01_gainian_01.mp4","content":"古典概率","id":1,"isfree":1,"name":"课时1：实数的概念","orgname":"幂学","pic":"","playCount":40,"timeSpan":"0:02:02","tname":"陈剑","weight":"10"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/02_feifu_01.mp4","content":"事件的独立性","id":2,"isfree":1,"name":"课时2：非负性的应用Ⅰ","orgname":"幂学","pic":"","playCount":6,"timeSpan":"0:02:39","tname":"陈剑","weight":"20"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/03_feifu_02.mp4","content":"事件的独立性真题","id":3,"isfree":1,"name":"课时3：非负性的应用Ⅱ","orgname":"幂学","pic":"","playCount":8,"timeSpan":"0:06:09","tname":"陈剑","weight":"10"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/04_zhishu_01.mp4","content":"平均值、方差、标准差概念","id":4,"isfree":1,"name":"课时4：质数与合数","orgname":"幂学","pic":"","playCount":8,"timeSpan":"0:10:09","tname":"陈剑","weight":"10"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/05_zhishu_02.mp4","content":"","id":5,"isfree":1,"name":"课时5：质数的性质","orgname":"","pic":"http://www.mykepu.com:8080/pic/videopic/logo_chenxing.png","playCount":5,"timeSpan":"0:02:45","tname":"","weight":"10"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/06_jishu_01.mp4","content":"幂学教育","id":6,"isfree":1,"name":"课时6：奇数和偶数","orgname":"幂学","pic":"http://www.mykepu.com:8080/pic/videopic/logo_mixue.png","playCount":973,"timeSpan":"0:07:38","tname":"陈剑","weight":"10"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/07_fenshu_01.mp4","content":"晨星成长计划","id":7,"isfree":1,"name":"课时7：分数与小数","orgname":"","pic":"http://www.mykepu.com:8080/pic/videopic/logo_chenxing.png","playCount":62,"timeSpan":"0:06:15","tname":"","weight":"10"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/08_yueshu_01.mp4","content":"大纲词汇分析01","id":8,"isfree":1,"name":"课时8：约数与公倍数","orgname":"幂学","pic":"","playCount":695,"timeSpan":"0:08:09","tname":"何敬","weight":"10"},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/09_yueshu_02.mp4","content":"大纲词汇分析02","id":9,"isfree":1,"name":"课时9：约数与倍数的应用","orgname":"幂学","pic":"","playCount":198,"timeSpan":"0:07:15","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/10_yueshu_03.mp4","content":"大纲词汇分析03","id":10,"isfree":1,"name":"课时10：公约数的应用方法","orgname":"幂学","pic":"","playCount":105,"timeSpan":"0:01:32","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/11_juedui_1.mp4","content":"大纲词汇分析04","id":11,"isfree":1,"name":"课时11：绝对值","orgname":"幂学","pic":"","playCount":117,"timeSpan":"0:15:48","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/12_juedui_2.mp4","content":"大纲词汇分析05","id":12,"isfree":1,"name":"课时12：绝对值的基本应用","orgname":"幂学","pic":"","playCount":92,"timeSpan":"0:03:35","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/13_juedui_3.mp4","content":"大纲词汇分析06","id":13,"isfree":1,"name":"课时13：绝对值的非负性","orgname":"幂学","pic":"","playCount":116,"timeSpan":"0:08:36","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/14_bili_01.mp4","content":"大纲词汇分析07","id":14,"isfree":1,"name":"课时14：比和比例的概念","orgname":"幂学","pic":"","playCount":145,"timeSpan":"0:05:39","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/15_bili_02.mp4","content":"大纲词汇分析08","id":15,"isfree":1,"name":"课时15：正比和反比的应用","orgname":"幂学","pic":"","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/18_pingjun_01.mp4","content":"大纲词汇分析11","id":18,"isfree":1,"name":"课时18：平均值的概念","orgname":"幂学","pic":"","playCount":140,"timeSpan":"0:07:31","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/19_pingjun_02.mp4","content":"大纲词汇分析12","id":19,"isfree":1,"name":"课时19：平均值的定理","orgname":"幂学","pic":"","playCount":290,"timeSpan":"0:08:36","tname":"何敬","weight":""},{"address":"http://olamath.ufile.ucloud.com.cn/2016suanshu/20_chfen_1.mp4","content":"大纲词汇分析13","id":20,"isfree":1,"name":"课时20：条件充分性判定","orgname":"幂学","pic":"","playCount":103,"timeSpan":"0:16:51","tname":"何敬","weight":""}]
     */

    private ResultBean result;
    private int apicode;
    private int playIndex;
    private int isCollect;
    private int orderStatus;
    private long playProgress;

    public int getPlayIndex() {
        return playIndex;
    }

    public void setPlayIndex(int playIndex) {
        this.playIndex = playIndex;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getPlayProgress() {
        return playProgress;
    }

    public void setPlayProgress(long playProgress) {
        this.playProgress = playProgress;
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

    public static class ResultBean implements Serializable {
        private String pointId;
        private String isCollect;

        @Override
        public String toString() {
            return "ResultBean{" +
                    "pointId='" + pointId + '\'' +
                    ", isCollect='" + isCollect + '\'' +
                    ", videoList=" + videoList +
                    '}';
        }

        /**
         * address : http://olamath.ufile.ucloud.com.cn/2016suanshu/01_gainian_01.mp4
         * content : 古典概率
         * id : 1
         * isfree : 1
         * name : 课时1：实数的概念
         * orgname : 幂学
         * pic :
         * playCount : 40
         * timeSpan : 0:02:02
         * tname : 陈剑
         * weight : 10
         */

        private List<VideoListBean> videoList;

        public String getPointId() {
            return pointId;
        }

        public void setPointId(String pointId) {
            this.pointId = pointId;
        }

        public String getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(String isCollect) {
            this.isCollect = isCollect;
        }

        public List<VideoListBean> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<VideoListBean> videoList) {
            this.videoList = videoList;
        }

        public static class VideoListBean implements Serializable {
            private String address;
            private String content;
            private long id;
            private int isfree;
            private String name;
            private String orgname;
            private String pic;
            private int playCount;
            private String timeSpan;
            private String tname;
            private String weight;
            private String url;
            private String size;
            private boolean isSelected;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getSize() {
                return size;
            }

            public void setSize(String size) {
                this.size = size;
            }

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
            }

            @Override
            public String toString() {
                return "VideoListBean{" +
                        "address='" + address + '\'' +
                        ", content='" + content + '\'' +
                        ", id=" + id +
                        ", isfree=" + isfree +
                        ", name='" + name + '\'' +
                        ", orgname='" + orgname + '\'' +
                        ", pic='" + pic + '\'' +
                        ", playCount=" + playCount +
                        ", timeSpan='" + timeSpan + '\'' +
                        ", tname='" + tname + '\'' +
                        ", weight='" + weight + '\'' +
                        ", url='" + url + '\'' +
                        ", size='" + size + '\'' +
                        ", isSelected=" + isSelected +
                        '}';
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getIsfree() {
                return isfree;
            }

            public void setIsfree(int isfree) {
                this.isfree = isfree;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOrgname() {
                return orgname;
            }

            public void setOrgname(String orgname) {
                this.orgname = orgname;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public int getPlayCount() {
                return playCount;
            }

            public void setPlayCount(int playCount) {
                this.playCount = playCount;
            }

            public String getTimeSpan() {
                return timeSpan;
            }

            public void setTimeSpan(String timeSpan) {
                this.timeSpan = timeSpan;
            }

            public String getTname() {
                return tname;
            }

            public void setTname(String tname) {
                this.tname = tname;
            }

            public String getWeight() {
                return weight;
            }

            public void setWeight(String weight) {
                this.weight = weight;
            }
        }
    }
}
