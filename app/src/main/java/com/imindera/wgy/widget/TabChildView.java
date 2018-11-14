package com.imindera.wgy.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imindera.wgy.R;
import com.imindera.wgy.util.CommonUtils;

/**
 * @function 导航栏子view
 */

public class TabChildView extends LinearLayout {
    private Context context;
    /**
     * 是否选中
     */
    private boolean isChoose;
    /**
     * 默认选中图片
     */
    private int defaultChooseRes;
    /**
     * 默认未选中图片
     */
    private int defaultUnChooseRes;
    /**
     * 文字
     */
    private String text;
    /**
     * 是否有红点
     */
    private boolean isRedRound;

    private ImageView tabIcon;
    private ImageView tabIconRed;
    private TextView tabText;
    private int mPosition;
    private String mActiveIconUrl;     //选中图片url
    private String mInActiveIconUrl;   //未选中图片

    public TabChildView(Context context) {
        super(context);
        this.context = context;
    }

    public TabChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public TabChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TabChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    /**
     * 设置tab初始值
     * @param text                 //tab对应文字
     * @param defaultChooseRes   //默认选中图片资源id
     * @param defaultUnChooseRes  //默认未选中图片资源id
     * @param activeIconUrl       //选中图片url
     * @param inActiveIconUrl     //未选中图片url
     */
    public void initialise(String text, int defaultChooseRes, int defaultUnChooseRes
            , String activeIconUrl, String inActiveIconUrl) {
        this.defaultChooseRes = defaultChooseRes;
        this.defaultUnChooseRes = defaultUnChooseRes;
        this.text = text;
        this.mActiveIconUrl = activeIconUrl;
        this.mInActiveIconUrl = inActiveIconUrl;
        onCreate();
    }

    private void onCreate() {
        View view = View.inflate(context, R.layout.tab_child_view, TabChildView.this);
        tabIcon = (ImageView) view.findViewById(R.id.tab_child_icon);
        tabIconRed = (ImageView) view.findViewById(R.id.tab_child_icon_red);
        tabText = (TextView) view.findViewById(R.id.tab_child_text);
        refreshTab();
        handleRedRound();
    }

    /**
     * 设置是否选中
     *
     * @param isChoose 选中
     */
    public TabChildView choose(boolean isChoose) {
        this.isChoose = isChoose;
        return this;
    }

    /**
     * 设置是否有红点
     *
     * @param isRedRound 红点
     */
    public void redRound(boolean isRedRound) {
        this.isRedRound = isRedRound;
        handleRedRound();
    }

    /**
     * 处理红点
     */
    private void handleRedRound() {
        if (isRedRound) {
            tabIconRed.setVisibility(VISIBLE);
        } else {
            tabIconRed.setVisibility(GONE);
        }
    }

    public String getText() {
        return tabText.getText().toString();
    }

    public void setPosition(int position) {
        this.mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }

    /**
     * tab切换时刷新icon和文字
     */
    public void refreshTab() {
        if (isChoose) {
            CommonUtils.glideLoad(context.getApplicationContext(),
                    mActiveIconUrl, defaultChooseRes, tabIcon);
        } else {
            CommonUtils.glideLoad(context.getApplicationContext(),
                    mInActiveIconUrl, defaultUnChooseRes, tabIcon);
        }
        tabText.setSelected(isChoose);
        tabText.setText(text);
    }
}
