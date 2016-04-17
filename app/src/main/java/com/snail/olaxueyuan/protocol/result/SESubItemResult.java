package com.snail.olaxueyuan.protocol.result;

import com.snail.olaxueyuan.protocol.model.SESubItem;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15-3-22.
 */
public class SESubItemResult extends ServiceResult {
    public boolean state;
    public String cage;
    public String msg;
    public ArrayList<SESubItem> data;
}
