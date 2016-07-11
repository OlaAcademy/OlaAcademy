package com.michen.olaxueyuan.ui.question.module;

import java.io.Serializable;

/**
 * Created by mingge on 2016/5/10.
 */
public class CourseNameModule implements Serializable {
    private String name;
    private boolean isSelected;

    public CourseNameModule(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
