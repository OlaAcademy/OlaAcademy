package com.michen.olaxueyuan.protocol.event;

import java.io.Serializable;

/**
 * Created by mingge on 2016/9/27.
 */

public class SignInEvent implements Serializable {
    public boolean isSign;//true签到，false不签到

    public SignInEvent(boolean isSign) {
        this.isSign = isSign;
    }
}
