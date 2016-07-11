package com.michen.olaxueyuan.protocol.result;

import com.michen.olaxueyuan.protocol.model.SEExam;

import java.util.ArrayList;

/**
 * Created by tianxiaopeng on 15-3-22.
 */
public class SEExamResult extends ServiceResult {
    public boolean state;
    public String msg;
    public String cage;
    public ArrayList<SEExam> data;
}
