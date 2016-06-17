package com.snail.olaxueyuan.protocol.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mingge on 2016/6/17.
 */
public class SystemVideoResult extends ServiceResult implements Serializable {
    /**
     * message : 成功
     * result : [{"id":110,"name":"课时1：算数部分【1】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/01_s_01.mp4","playCount":24,"timeSpan":"0.041585","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":111,"name":"课时2：算数部分【2】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/02_s_02.mp4","playCount":12,"timeSpan":"0.059537","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":112,"name":"课时3：算数部分【3】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/03_ss_03.mp4","playCount":10,"timeSpan":"0.057615","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":113,"name":"课时4：算数部分【4】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/04_ss_04.mp4","playCount":14,"timeSpan":"0.063981","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":114,"name":"课时5：应用题部分【1】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/05_yy_01.mp4","playCount":20,"timeSpan":"0.054039","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":115,"name":"课时6：应用题部分【2】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/06_yy_02.mp4","playCount":11,"timeSpan":"0.075138","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":116,"name":"课时7：应用题部分【3】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/07_yy_03.mp4","playCount":7,"timeSpan":"0.044525","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":117,"name":"课时8：应用题部分【4】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/08_yy_04.mp4","playCount":6,"timeSpan":"0.057789","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":118,"name":"课时9：代数部分【1】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/09_ds_01.mp4","playCount":7,"timeSpan":"0.059618","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":119,"name":"课时10：代数部分【2】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/10_ds_02.mp4","playCount":15,"timeSpan":"0.061759","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":120,"name":"课时11：代数部分【3】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/11_ds_03.mp4","playCount":7,"timeSpan":"0.050567","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":121,"name":"课时12：代数部分【4】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/12_ds_04.mp4","playCount":10,"timeSpan":"0.056122","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":122,"name":"课时13：代数部分【5】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/13_ds_05.mp4","playCount":9,"timeSpan":"0.055613","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":123,"name":"课时14：数列部分【1】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/14_sl_01.mp4","playCount":5,"timeSpan":"0.058078","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":124,"name":"课时15：数列部分【2】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/15_sl_02.mp4","playCount":5,"timeSpan":"0.069409","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":125,"name":"课时16：几何部分【1】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/16_jh_01.mp4","playCount":5,"timeSpan":"0.067916","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":126,"name":"课时17：几何部分【2】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/17_jh_02.mp4","playCount":3,"timeSpan":"0.038923","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":127,"name":"课时18：几何部分【3】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/18_jh_03.mp4","playCount":3,"timeSpan":"0.070972","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":128,"name":"课时19：几何部分【4】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/19_jh_04.mp4","playCount":14,"timeSpan":"0.058553","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":129,"name":"课时20：几何部分【5】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/20_jh_05.mp4","playCount":6,"timeSpan":"0.067106","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":130,"name":"课时21：几何部分【6】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/21_jh_06.mp4","playCount":4,"timeSpan":"0.047233","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":131,"name":"课时22：排列组合部分【1】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/22_pl_01.mp4","playCount":8,"timeSpan":"0.046516","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":132,"name":"课时23：排列组合部分【2】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/23_pl_02.mp4","playCount":4,"timeSpan":"0.044988","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":133,"name":"课时24：排列组合部分【3】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/24_pl_03.mp4","playCount":8,"timeSpan":"0.060185","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":134,"name":"课时25：概率部分【1】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/25_gl_01.mp4","playCount":6,"timeSpan":"0.072754","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":135,"name":"课时26：概率部分【2】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/26_gl_02.mp4","playCount":7,"timeSpan":"0.058888","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null},{"id":136,"name":"课时27：概率部分【3】","content":null,"address":"http://olamath.ufile.ucloud.com.cn/2016jc/27_gl_03.mp4","playCount":8,"timeSpan":"AM 12:41","weight":null,"orgname":null,"tname":null,"isBanner":0,"pic":null}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * id : 110
     * name : 课时1：算数部分【1】
     * content : null
     * address : http://olamath.ufile.ucloud.com.cn/2016jc/01_s_01.mp4
     * playCount : 24
     * timeSpan : 0.041585
     * weight : null
     * orgname : null
     * tname : null
     * isBanner : 0
     * pic : null
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

    public static class ResultBean implements Serializable {
        private int id;
        private String name;
        private Object content;
        private String address;
        private int playCount;
        private String timeSpan;
        private Object weight;
        private Object orgname;
        private Object tname;
        private int isBanner;
        private Object pic;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

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

        public Object getContent() {
            return content;
        }

        public void setContent(Object content) {
            this.content = content;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public Object getWeight() {
            return weight;
        }

        public void setWeight(Object weight) {
            this.weight = weight;
        }

        public Object getOrgname() {
            return orgname;
        }

        public void setOrgname(Object orgname) {
            this.orgname = orgname;
        }

        public Object getTname() {
            return tname;
        }

        public void setTname(Object tname) {
            this.tname = tname;
        }

        public int getIsBanner() {
            return isBanner;
        }

        public void setIsBanner(int isBanner) {
            this.isBanner = isBanner;
        }

        public Object getPic() {
            return pic;
        }

        public void setPic(Object pic) {
            this.pic = pic;
        }
    }
}
