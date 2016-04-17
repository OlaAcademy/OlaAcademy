package com.snail.olaxueyuan.protocol.result;

import com.snail.olaxueyuan.protocol.model.SEAdvertisement;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15-1-20.
 */
public class SEAdResult extends ServiceResult {

    public boolean state;
    public String type;
    public String msg;
    public ArrayList<SEAdvertisement> data;
}
