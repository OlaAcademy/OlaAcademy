package com.michen.olaxueyuan.protocol.model;

/**
 * Created by mingge on 15/11/24.
 */
public class PostDetailBottomGridBean {
    private int img;
    private String text;
    private int position;

    public int getImg() {
        return img;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setImg(int img) {
        this.img = img;

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
