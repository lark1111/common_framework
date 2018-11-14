package com.imindera.wgy.widget;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.imindera.wgy.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.InternalClassics;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * 经典上拉底部组件
 */

@SuppressWarnings({"unused", "UnusedReturnValue", "deprecation"})
public class ClassicsFooter extends InternalClassics<ClassicsFooter> implements RefreshFooter {

    protected boolean mNoMoreData = false;

    //<editor-fold desc="LinearLayout">
    public ClassicsFooter(Context context) {
        this(context, null);
    }

    public ClassicsFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicsFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final View thisView = this;
        final View arrowView = mArrowView;
        final View progressView = mProgressView;
        final DensityUtil density = new DensityUtil();

        mTitleText.setTextColor(getResources().getColor(R.color.gray_middle_color));
        mTitleText.setTextSize(12);

        LayoutParams lpArrow = (LayoutParams) arrowView.getLayoutParams();
        LayoutParams lpProgress = (LayoutParams) progressView.getLayoutParams();
        lpProgress.rightMargin = density.dip2px(20);
        lpArrow.rightMargin = lpProgress.rightMargin;

        mSpinnerStyle = SpinnerStyle.Translate;

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
        if (!mNoMoreData) {
            super.onStartAnimator(refreshLayout, height, maxDragHeight);
        }
    }

    @Override
    public int onFinish(@NonNull RefreshLayout layout, boolean success) {
        if (!mNoMoreData) {
            return super.onFinish(layout, success);
        }
        return 0;
    }

    /**
     * ClassicsFooter 在(SpinnerStyle.FixedBehind)时才有主题色
     */
    @Override
    @Deprecated
    public void setPrimaryColors(@ColorInt int... colors) {
        if (mSpinnerStyle == SpinnerStyle.FixedBehind) {
            super.setPrimaryColors(colors);
        }
    }

    /**
     * 设置数据全部加载完成，将不能再次触发加载功能
     */
    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData;
            final View arrowView = mArrowView;
            if (noMoreData) {
                mTitleText.setText("没有更多数据了");
                arrowView.setVisibility(GONE);
            } else {
                arrowView.setVisibility(VISIBLE);
            }
        }
        return true;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        final View arrowView = mArrowView;
        if (!mNoMoreData) {
            switch (newState) {
                case None:
                    arrowView.setVisibility(VISIBLE);
                case PullUpToLoad:
                    arrowView.animate().rotation(180);
                    break;
                case Loading:
                case LoadReleased:
                    arrowView.setVisibility(GONE);
                    break;
                case ReleaseToLoad:
                    arrowView.animate().rotation(0);
                    break;
                case Refreshing:
                    arrowView.setVisibility(GONE);
                    break;
            }
        }
    }
}
