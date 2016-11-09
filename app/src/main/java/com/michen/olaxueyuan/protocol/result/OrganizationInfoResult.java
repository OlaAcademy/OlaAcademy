package com.michen.olaxueyuan.protocol.result;

import java.util.List;

/**
 * Created by mingge on 2016/11/7.
 */

public class OrganizationInfoResult {
    /**
     * message : 成功
     * result : [{"optionName":"机构","optionList":[{"address":"http://organization.ufile.ucloud.com.cn/brochure1.pdf","attendCount":951,"checkinCount":39,"id":1,"logo":"http://commodity.ufile.ucloud.com.cn/logo1.jpg","name":"2017年幂学系统班","org":"幂学","profile":"2018年幂学教育MPAcc招生简章","type":1},{"address":"http://organization.ufile.ucloud.com.cn/brochure2.pdf","attendCount":458,"checkinCount":27,"id":2,"logo":"http://commodity.ufile.ucloud.com.cn/logo1.jpg","name":"2017年幂学私塾班","org":"幂学","profile":"2018年MBA复试与面试辅导招生简章","type":1}]},{"optionName":"学校","optionList":[{"address":"http://organization.ufile.ucloud.com.cn/brochure3.pdf","attendCount":201,"checkinCount":19,"id":3,"logo":"http://banner.ufile.ucloud.com.cn/paris.jpg","name":"巴黎第一大学","org":"法国","profile":"法国巴黎一大招生简章","type":2},{"address":"http://organization.ufile.ucloud.com.cn/brochure4.pdf","attendCount":0,"checkinCount":10,"id":4,"logo":"http://banner.ufile.ucloud.com.cn/neo.jpg","name":"诺欧商学院","org":"法国","profile":"法国诺欧商学院-零售管理硕士招生简章","type":2},{"address":"http://organization.ufile.ucloud.com.cn/brochure5.pdf","attendCount":0,"checkinCount":16,"id":6,"logo":"http://banner.ufile.ucloud.com.cn/xiyatu.jpg","name":"西雅图城市大学","org":"美国","profile":"美国西雅图城市大学MBA招生简章","type":2}]}]
     * apicode : 10000
     */

    private String message;
    private int apicode;
    /**
     * optionName : 机构
     * optionList : [{"address":"http://organization.ufile.ucloud.com.cn/brochure1.pdf","attendCount":951,"checkinCount":39,"id":1,"logo":"http://commodity.ufile.ucloud.com.cn/logo1.jpg","name":"2017年幂学系统班","org":"幂学","profile":"2018年幂学教育MPAcc招生简章","type":1},{"address":"http://organization.ufile.ucloud.com.cn/brochure2.pdf","attendCount":458,"checkinCount":27,"id":2,"logo":"http://commodity.ufile.ucloud.com.cn/logo1.jpg","name":"2017年幂学私塾班","org":"幂学","profile":"2018年MBA复试与面试辅导招生简章","type":1}]
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
        private String optionName;
        /**
         * address : http://organization.ufile.ucloud.com.cn/brochure1.pdf
         * attendCount : 951
         * checkinCount : 39
         * id : 1
         * logo : http://commodity.ufile.ucloud.com.cn/logo1.jpg
         * name : 2017年幂学系统班
         * org : 幂学
         * profile : 2018年幂学教育MPAcc招生简章
         * type : 1
         */

        private List<OptionListBean> optionList;

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public List<OptionListBean> getOptionList() {
            return optionList;
        }

        public void setOptionList(List<OptionListBean> optionList) {
            this.optionList = optionList;
        }

        public static class OptionListBean {
            private String address;
            private int attendCount;
            private int checkinCount;
            private int id;
            private String logo;
            private String name;
            private String org;
            private String profile;
            private int type;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getAttendCount() {
                return attendCount;
            }

            public void setAttendCount(int attendCount) {
                this.attendCount = attendCount;
            }

            public int getCheckinCount() {
                return checkinCount;
            }

            public void setCheckinCount(int checkinCount) {
                this.checkinCount = checkinCount;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLogo() {
                return logo;
            }

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOrg() {
                return org;
            }

            public void setOrg(String org) {
                this.org = org;
            }

            public String getProfile() {
                return profile;
            }

            public void setProfile(String profile) {
                this.profile = profile;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
