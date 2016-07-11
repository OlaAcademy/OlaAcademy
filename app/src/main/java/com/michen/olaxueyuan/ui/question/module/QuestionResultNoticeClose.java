package com.michen.olaxueyuan.ui.question.module;

/**
 * Created by mingge on 2016/6/15.
 */
public class QuestionResultNoticeClose {
    public int type = 0;//0:通知做题界面关闭
    public boolean isClose = false;//true关闭界面，false，不关闭界面

    public QuestionResultNoticeClose(int type, boolean isClose) {
        this.type = type;
        this.isClose = isClose;
    }
}
