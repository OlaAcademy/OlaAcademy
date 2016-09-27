package com.michen.olaxueyuan.protocol.event;

import java.io.Serializable;

/**
 * Created by mingge on 2016/9/27.
 */

public class ShowBottomTabDotEvent implements Serializable {
    public int position;//0考点、1题库、2主页、3欧拉圈、4我的
    public boolean isShowDot;//false不显示，true显示

    public ShowBottomTabDotEvent(int position, boolean isShowDot) {
        this.position = position;
        this.isShowDot = isShowDot;
    }
}
