package com.michen.olaxueyuan.protocol.event;

/**
 * <pre>
 *     author : mingge
 *     time   : 2017/03/24
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LenChatUnReadEvent {
    private boolean isHasNewInfo;

    public LenChatUnReadEvent(boolean isHasNewInfo) {
        this.isHasNewInfo = isHasNewInfo;
    }

    public boolean isHasNewInfo() {
        return isHasNewInfo;
    }

    public void setHasNewInfo(boolean hasNewInfo) {
        isHasNewInfo = hasNewInfo;
    }
}
