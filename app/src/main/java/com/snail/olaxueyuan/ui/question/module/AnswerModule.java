package com.snail.olaxueyuan.ui.question.module;

/**
 * Created by mingge on 2016/6/14.
 * 答题完成
 */
public class AnswerModule {

    /**
     * questionId : 504
     * optionId :
     * isCorrect : 2
     */

    private int questionId;
    private String optionId;
    private String isCorrect;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(String isCorrect) {
        this.isCorrect = isCorrect;
    }

    @Override
    public String toString() {
        return "AnswerModule{" +
                "questionId=" + questionId +
                ", optionId='" + optionId + '\'' +
                ", isCorrect='" + isCorrect + '\'' +
                '}';
    }
}
