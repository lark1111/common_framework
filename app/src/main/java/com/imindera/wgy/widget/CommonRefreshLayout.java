package com.imindera.wgy.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.imindera.wgy.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.impl.RefreshHeaderWrapper;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;

/**
 *通用下拉刷新
 */

public class CommonRefreshLayout extends SmartRefreshLayout {
    private ImageView commonRefreshAnim;
    private ImageView commonRefreshAnimPull;
    private Context mContext;

    public CommonRefreshLayout(Context context) {
        super(context);
        initRefreshLayout(context);

    }

    public CommonRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initRefreshLayout(context);
    }

    public CommonRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRefreshLayout(context);
    }


    public void initRefreshLayout(final Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.common_refresh_layout,CommonRefreshLayout.this,false);
        commonRefreshAnim = (ImageView) view.findViewById(R.id.common_refresh_anim);
        commonRefreshAnimPull = (ImageView) view.findViewById(R.id.common_refresh_anim_pull);

        setRefreshHeader(new RefreshHeaderWrapper(view));
        //下拉刷新
        AnimationDrawable anim = (AnimationDrawable) commonRefreshAnim.getDrawable();
        anim.start();
        setRefreshFooter(new ClassicsFooter(mContext));
        setOnMultiPurposeListener(new OnMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                if (percent < 1) {
                    finishRefresh(true);
                }
                setOffsetAnim(percent);
            }

            @Override
            public void onHeaderReleased(RefreshHeader header, int headerHeight, int maxDragHeight) {

            }

            @Override
            public void onHeaderStartAnimator(RefreshHeader header, int offset, int maxDragHeight) {
                commonRefreshAnim.setVisibility(View.VISIBLE);
                commonRefreshAnimPull.setVisibility(View.GONE);
            }

            @Override
            public void onHeaderFinish(RefreshHeader header, boolean success) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        commonRefreshAnim.setVisibility(View.GONE);
                        commonRefreshAnimPull.setVisibility(View.VISIBLE);
                    }
                }, 300);
            }

            @Override
            public void onFooterMoving(RefreshFooter footer, boolean isDragging, float percent, int offset, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterReleased(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int footerHeight, int maxDragHeight) {

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

            }
        });
    }

    /**
     * 根据header偏移量设置不同的图片以及位置
     *
     * @param offset 偏移量
     */
    private void setOffsetAnim(float offset) {
        if (offset < cal(0)) {
            setResAndPos(R.mipmap.drop_down_00000, 0);
        }
        if (offset >= cal(0) && offset < cal(1)) {
            setResAndPos(R.mipmap.drop_down_00001, 1);
        }
        if (offset >= cal(1) && offset < cal(2)) {
            setResAndPos(R.mipmap.drop_down_00002, 2);
        }
        if (offset >= cal(2) && offset < cal(3)) {
            setResAndPos(R.mipmap.drop_down_00003, 3);
        }
        if (offset >= cal(3) && offset < cal(4)) {
            setResAndPos(R.mipmap.drop_down_00004, 4);
        }
        if (offset >= cal(4) && offset < cal(5)) {
            setResAndPos(R.mipmap.drop_down_00005, 5);
        }
        if (offset >= cal(5) && offset < cal(6)) {
            setResAndPos(R.mipmap.drop_down_00006, 6);
        }
        if (offset >= cal(6) && offset < cal(7)) {
            setResAndPos(R.mipmap.drop_down_00007, 7);
        }
        if (offset >= cal(7) && offset < cal(8)) {
            setResAndPos(R.mipmap.drop_down_00008, 8);
        }
        if (offset >= cal(9) && offset < cal(10)) {
            setResAndPos(R.mipmap.drop_down_00009, 9);
        }
        if (offset >= cal(10)) {
            setResAndPos(R.mipmap.drop_down_00010, 10);
        }
    }

    /**
     * 计算比例间断
     *
     * @param scale 比重
     * @return 临界值
     */
    private float cal(int scale) {
        return 0.30f + scale * 0.07f;
    }

    /**
     * 设置相应的图片以及位置
     *
     * @param resId 图片资源
     * @param pos   位置信息
     */
    private void setResAndPos(int resId, int pos) {
        commonRefreshAnimPull.setImageDrawable(mContext.getResources().getDrawable(resId));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(commonRefreshAnimPull.getLayoutParams());
        lp.setMargins(0, 120 - 6 * pos, 0, 6 * pos);
        commonRefreshAnimPull.setLayoutParams(lp);
    }
}
