package com.imindera.wgy.bean;

import org.litepal.crud.DataSupport;

public class BottomLevelIconTextsBean extends DataSupport {

    private int position;
    private String iconNormal;
    private String iconClick;
    private String text;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getIconNormal() {
        return iconNormal;
    }

    public void setIconNormal(String iconNormal) {
        this.iconNormal = iconNormal;
    }

    public String getIconClick() {
        return iconClick;
    }

    public void setIconClick(String iconClick) {
        this.iconClick = iconClick;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
