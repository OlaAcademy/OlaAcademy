package com.michen.olaxueyuan.protocol.result;

import java.util.List;

/**
 * Created by mingge on 17/1/4.
 */

public class HomePageDeployPostBean {
    private String time;
    List<UserPostListResult.ResultBean.DeployListBean> child;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<UserPostListResult.ResultBean.DeployListBean> getChild() {
        return child;
    }

    public void setChild(List<UserPostListResult.ResultBean.DeployListBean> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "HomePageDeployPostBean{" +
                "time='" + time + '\'' +
                ", child=" + child +
                '}';
    }
}
