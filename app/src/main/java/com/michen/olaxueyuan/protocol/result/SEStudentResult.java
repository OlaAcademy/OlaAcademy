package com.michen.olaxueyuan.protocol.result;

import com.michen.olaxueyuan.protocol.model.SEStudent;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15-2-2.
 */
public class SEStudentResult extends ServiceResult {
    public String msg;
    public String page;
    public String limit;
    public boolean state;
    public ArrayList<SEStudent> data;
}
