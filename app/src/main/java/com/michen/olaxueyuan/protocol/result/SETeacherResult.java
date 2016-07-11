package com.michen.olaxueyuan.protocol.result;

import com.michen.olaxueyuan.protocol.model.SETeacherCate;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15-3-22.
 */
public class SETeacherResult extends ServiceResult {
    public String msg;
    public boolean state;
    public ArrayList<SETeacherCate> data;
}
