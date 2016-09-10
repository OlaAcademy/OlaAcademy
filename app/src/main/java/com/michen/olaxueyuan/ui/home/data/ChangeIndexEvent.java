package com.michen.olaxueyuan.ui.home.data;

import java.io.Serializable;

/**
 * Created by mingge on 2016/8/26.
 */
public class ChangeIndexEvent implements Serializable {
    public int position;
    public boolean isChange;

    public ChangeIndexEvent(int position, boolean isChange) {
        this.position = position;
        this.isChange = isChange;
    }
}
