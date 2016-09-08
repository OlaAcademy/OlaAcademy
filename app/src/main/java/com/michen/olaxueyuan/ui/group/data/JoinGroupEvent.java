package com.michen.olaxueyuan.ui.group.data;

/**
 * Created by mingge on 16/9/8.
 */
public class JoinGroupEvent {
    public int type;//1、加入群2、退出群
    public boolean isValid;
    public String groupId;

    public JoinGroupEvent(int type, boolean isValid, String groupId) {
        this.type = type;
        this.isValid = isValid;
        this.groupId = groupId;
    }
}
