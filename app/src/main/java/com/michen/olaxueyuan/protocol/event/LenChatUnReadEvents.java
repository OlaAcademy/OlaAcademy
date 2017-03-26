package com.michen.olaxueyuan.protocol.event;

/**
 * Created by mingge on 17/3/24.
 */

public class LenChatUnReadEvents {
    private boolean isHasNewInfo;

    public LenChatUnReadEvents(boolean isHasNewInfo) {
        this.isHasNewInfo = isHasNewInfo;
    }

    public boolean isHasNewInfo() {
        return isHasNewInfo;
    }

    public void setHasNewInfo(boolean hasNewInfo) {
        isHasNewInfo = hasNewInfo;
    }

}
