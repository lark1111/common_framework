package com.imindera.wgy.fragment;

import android.view.View;

import com.imindera.wgy.R;
import com.imindera.wgy.base.CustomFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhouyu on 2018/10/19.
 */

public class MineFragment extends CustomFragment {
    @Override
    protected void setTitleBar(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initParams() {

    }

    @Override
    public synchronized void onDestroy() {
        super.onDestroy();
    }
}
