package com.michen.olaxueyuan.protocol.result;

import java.util.List;

/**
 * Created by mingge on 2016/11/3.
 */

public class MaterialListResult {
    /**
     * message : 成功
     * result : [{"count":10,"id":10,"pic":"http://cospic.ufile.ucloud.com.cn/icon.jpg","price":"20","provider":"欧拉学院","size":"813K","title":"平面几何精选20题","type":1,"url":"http://datapdf.ufile.ucloud.com.cn/math/s_10.pdf","time":"2016-09-22 10:01:20","status":0},{"count":10,"id":9,"pic":"http://cospic.ufile.ucloud.com.cn/icon.jpg","price":"20","provider":"欧拉学院","size":"607K","title":"蒙猜大法－数学条件充分性判断终极解题技巧","type":1,"url":"http://datapdf.ufile.ucloud.com.cn/math/s_9.pdf","time":"2016-09-21 10:01:20","status":0},{"count":10,"id":8,"pic":"http://cospic.ufile.ucloud.com.cn/icon.jpg","price":"0","provider":"欧拉学院","size":"772K","title":"管理类联考数学必备公式","type":1,"url":"http://datapdf.ufile.ucloud.com.cn/math/s_8.pdf","time":"2016-09-20 10:01:20","status":1}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * count : 10
     * id : 10
     * pic : http://cospic.ufile.ucloud.com.cn/icon.jpg
     * price : 20
     * provider : 欧拉学院
     * size : 813K
     * title : 平面几何精选20题
     * type : 1
     * url : http://datapdf.ufile.ucloud.com.cn/math/s_10.pdf
     * time : 2016-09-22 10:01:20
     * status : 0
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
        private int count;
        private int id;
        private String pic;
        private String price;
        private String provider;
        private String size;
        private String title;
        private int type;
        private String url;
        private String time;
        private int status;// 0 ;1 已兑换

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
