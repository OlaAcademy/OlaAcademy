package com.michen.olaxueyuan.ui.question.module;

import java.io.Serializable;

/**
 * Created by mingge on 16/8/10.
 */
public class QuestionChangeTabEvent implements Serializable {
   public int position;

    public QuestionChangeTabEvent(int position) {
        this.position = position;
    }
}
