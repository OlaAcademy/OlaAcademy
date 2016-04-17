package com.snail.olaxueyuan.protocol.result;

import com.snail.olaxueyuan.protocol.model.SEExam;

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
