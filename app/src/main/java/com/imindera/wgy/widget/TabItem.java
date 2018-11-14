package com.imindera.wgy.widget;

public class TabItem {
    private int mActiveIconResource; //默认的选中图片资源id
    private int mInActiveIconResource; //默认的未选中图片资源id
    private String mActiveIconUrl;     //选中图片url
    private String mInActiveIconUrl;   //未选中图片
    private String mTitle;              //tab对应文字

    public TabItem(int activeIconResource, int inActiveIconResource,
                   String activeIconUrl, String inActiveIconUrl, String title) {
        this.mActiveIconResource = activeIconResource;
        this.mInActiveIconResource = inActiveIconResource;
        this.mActiveIconUrl = activeIconUrl;
        this.mInActiveIconUrl = inActiveIconUrl;
        this.mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getActiveIconResource() {
        return mActiveIconResource;
    }

    public int getInactiveIconResource() {
        return mInActiveIconResource;
    }

    public String getActiveIconUrl() {
        return mActiveIconUrl;
    }

    public String getInActiveIconUrl() {
        return mInActiveIconUrl;
    }
}
