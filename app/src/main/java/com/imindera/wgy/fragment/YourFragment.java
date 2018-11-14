package com.imindera.wgy.fragment;

import android.view.View;

import com.imindera.wgy.R;
import com.imindera.wgy.base.CustomFragment;

/**
 * Created by zhouyu on 2018/10/19.
 */

public class YourFragment extends CustomFragment {
    @Override
    protected void setTitleBar(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_your;
    }

    @Override
    protected void initParams() {

    }

    @Override
    public synchronized void onDestroy() {
        super.onDestroy();
    }
}
