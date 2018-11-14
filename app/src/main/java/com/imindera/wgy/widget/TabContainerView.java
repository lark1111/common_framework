package com.imindera.wgy.widget;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.imindera.wgy.R;

import java.util.ArrayList;

 /*
 * 底部菜单容器
 */

public class TabContainerView extends LinearLayout {
    private static final int DEFAULT_SELECTED_POSITION = -1;
    private int mSelectedPosition = DEFAULT_SELECTED_POSITION;
    private int mFirstSelectedPosition = 0;
    private OnTabSelectedListener mTabSelectedListener;
    ArrayList<TabItem> mTabItems = new ArrayList<>();
    ArrayList<TabChildView> mTabChildViews = new ArrayList<>();
    private int mActiveColor;
    private int mInActiveColor;

    public TabContainerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 添加Tab item
     *
     * @param item
     * @return
     */
    public TabContainerView addTab(TabItem item) {
        mTabItems.add(item);
        return this;
    }

    /**
     * 返回和positon对应的TabTab
     *
     * @param position
     * @return
     */
    public TabItem getTab(int position) {
        return mTabItems.get(position);
    }

    /**
     * 初始化tab控件，必须在添加所有的tab后调用
     */
    public void initialise() {
        DisplayMetrics ds = getResources().getDisplayMetrics();
        int tabWidth = ds.widthPixels / mTabItems.size();
        for (TabItem item : mTabItems) {
            setUpTab(item, tabWidth);
        }
        selectTabInternal(mFirstSelectedPosition, false);
    }

    /**
     * 设置选中的tab
     *
     * @param selectedPosition
     */
    public void setSelectedPosition(int selectedPosition) {
        selectTabInternal(selectedPosition, false);
    }

    /**
     * tab切换回调，callListener为ttrue时点击tab导致的切换
     *
     * @param newPosition
     * @param callListener
     */
    private void selectTabInternal(int newPosition, boolean callListener) {
        int oldPosition = mSelectedPosition;
        if (mSelectedPosition != newPosition) {
            mSelectedPosition = newPosition;
            mTabChildViews.get(newPosition).choose(true).refreshTab();
            if (oldPosition != -1) {
                mTabChildViews.get(oldPosition).choose(false).refreshTab();
            }
        }
        if (callListener) {
            if (mTabSelectedListener != null) {
                mTabSelectedListener.onTabSelected(newPosition);
                if (oldPosition != -1) {
                    mTabSelectedListener.onTabUnselected(oldPosition);
                }
            }
        }
    }

    /**
     * 初始化TabChildView
     *
     * @param item
     */
    private void setUpTab(TabItem item, int itemWidth) {
        TabChildView tabChildView = (TabChildView) LayoutInflater.from(getContext())
                .inflate(R.layout.item_tab, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(itemWidth,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        tabChildView.setLayoutParams(params);
        tabChildView.initialise(item.getTitle(), item.getActiveIconResource(), item.getInactiveIconResource()
                , item.getActiveIconUrl(), item.getInActiveIconUrl());
        tabChildView.setPosition(mTabItems.indexOf(item));
        tabChildView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TabChildView tabView = (TabChildView) v;
                selectTabInternal(tabView.getPosition(), true);
            }
        });
        mTabChildViews.add(tabChildView);
        addView(tabChildView);
    }

    /**
     * 设置未选中的tab文字色值
     */
    public TabContainerView setInActiveColor(@ColorRes int inActiveColor) {
        this.mInActiveColor = ContextCompat.getColor(getContext(), inActiveColor);
        return this;
    }

    /**
     * 设置选中的tab文字色值
     */
    public TabContainerView setActiveColor(@ColorRes int activeColor) {
        this.mActiveColor = ContextCompat.getColor(getContext(), activeColor);
        return this;
    }

    /**
     * 设置tab切换listener
     *
     * @param tabSelectedListener callback listener for tabs
     * @return this, to allow builder pattern
     */
    public TabContainerView setTabSelectedListener(OnTabSelectedListener tabSelectedListener) {
        this.mTabSelectedListener = tabSelectedListener;
        return this;
    }

    public void setRedRound(int position, boolean flag) {
        TabChildView tabView = mTabChildViews.get(position);
        tabView.redRound(flag);
    }

    /**
     * 切换Tab回调
     */
    public interface OnTabSelectedListener {
        /**
         * @param position The position of the tab that was selected
         */
        void onTabSelected(int position);

        /**
         * @param position The position of the tab that was unselected
         */
        void onTabUnselected(int position);
    }
}
