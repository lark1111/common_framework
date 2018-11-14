package com.imindera.wgy.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imindera.wgy.net.utils.OkHttpUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhouyu on 2018/10/19.
 */

public abstract class CustomFragment extends Fragment{
    public final String TAG = getClass().getSimpleName();
    private Unbinder unbinder;
    protected  boolean isPaused;
    protected boolean mDestroyed;
    protected Context mContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        try {
            view = inflater.inflate(getLayoutId(), container, false);
            unbinder = ButterKnife.bind(this, view);
            initParams();
            setTitleBar(view);
            mDestroyed = false;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return view;
    }

    /**
     * 初始化TitleBar
     */
    protected abstract void setTitleBar(View view);

    /**
     * 初始化布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 跳转activity
     *
     * @param activity
     */
    public void startActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(getActivity(), activity);
        startActivity(intent);
    }

    /**
     * 参数初始化
     * 反注册,使其实例可以回收
     */
    protected abstract void initParams();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDestroyed = true;
        OkHttpUtils.getInstance().cancelTag(TAG);
        if(unbinder!=null){
            unbinder.unbind();
        }
    }

    //神策需要重写以下三个方法onResume，setUserVisibleHint，onHiddenChanged
    @Override
    public void onResume() {
        super.onResume();
        isPaused = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isPaused = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    protected boolean isDestroyed() {
        return mDestroyed;
    }
}
