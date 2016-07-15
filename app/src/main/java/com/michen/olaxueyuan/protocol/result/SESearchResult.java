package com.michen.olaxueyuan.protocol.result;

import com.michen.olaxueyuan.protocol.model.SESearch;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15-3-29.
 */
public class SESearchResult extends ServiceResult {
    public boolean state;
    public String msg;
    public ArrayList<SESearch> data;

}
