package com.imindera.wgy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.BarUtils;
import com.imindera.wgy.base.CustomFragment;
import com.imindera.wgy.bean.BottomLevelIconTextsBean;
import com.imindera.wgy.fragment.FirstFragment;
import com.imindera.wgy.fragment.MineFragment;
import com.imindera.wgy.fragment.YourFragment;
import com.imindera.wgy.util.CommonUtils;
import com.imindera.wgy.widget.TabContainerView;
import com.imindera.wgy.widget.TabItem;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhouyu on 2018/9/3.
 */
public class MainActivity extends AppCompatActivity implements TabContainerView.OnTabSelectedListener{
    @BindView(R.id.tabContainer)
    TabContainerView tabContainerView;
    @BindView(R.id.mian_tab)
    LinearLayout mainTab;

    public final String TAG = getClass().getSimpleName();

    private static final int TAB_INDEX = 0;
    private static final int TAB_OTHER = 1;
    private static final int TAB_MINE = 2;

    private FirstFragment indexFragment;
    private YourFragment yourFragment;
    private MineFragment mineFragment;
    private Fragment currentFragment;
    private int clickFragmen = TAB_INDEX;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BarUtils.setStatusBarAlpha(this, 50);
        ButterKnife.bind(this);
        initTabBar();
        int tabIndex = getIntent().getIntExtra("tabIndex", TAB_INDEX);
        initFragment(tabIndex);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handlerStatus(clickFragmen);
    }

    private void initFragment(int tabIndex) {
        fragmentManager = getSupportFragmentManager();
        clickFragmen = tabIndex;
        indexFragment = new FirstFragment();
        yourFragment = new YourFragment();
        mineFragment = new MineFragment();
        currentFragment = getFragment(clickFragmen);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.index_frag, currentFragment).commit();
    }

    /*初始化底部导航栏*/
    private void initTabBar() {
        List<BottomLevelIconTextsBean> beans = getBottomData();
        tabContainerView.setInActiveColor(R.color.text_color);//未选中时的颜色
        tabContainerView.setActiveColor(R.color.forget_pwd);//选中时的颜色
        BottomLevelIconTextsBean bean = beans.get(0);
        TabItem indexItem = new TabItem(R.mipmap.home_icon_select,
                R.mipmap.home_icon_unselect, bean.getIconClick(), bean.getIconNormal()
                , bean.getText());
        bean = beans.get(1);
        TabItem shopItem = new TabItem(R.mipmap.commercial_icon_select,
                R.mipmap.commercial_icon_unselect, bean.getIconClick(), bean.getIconNormal()
                , bean.getText());
        bean = beans.get(2);
        TabItem personalItem = new TabItem(R.mipmap.mine_icon_select,
                R.mipmap.mine_icon_unselect, bean.getIconClick(), bean.getIconNormal()
                , bean.getText());
        tabContainerView.addTab(indexItem).addTab(shopItem)
                .addTab(personalItem);
        tabContainerView.initialise();
        tabContainerView.setTabSelectedListener(this);
    }

    /**
     * 处理首页的相应选中状态并作出相应的UI处理
     *
     * @param select 选中TAB位置
     */
    private void handlerStatus(int select) {
        tabContainerView.setSelectedPosition(select);
    }

    private List<BottomLevelIconTextsBean> getBottomData() {
        List<BottomLevelIconTextsBean> currentBottomList = DataSupport.findAll(BottomLevelIconTextsBean.class);
        String[] bottomText = new String[]{"首页", "你的", "我的"};
        List<BottomLevelIconTextsBean> bottomList = new ArrayList<>();
        BottomLevelIconTextsBean bottomData;
        for (int i = 1; i < 4; i++) {
            bottomData = null;
            for (int j = 0; j < currentBottomList.size(); j++) {
                if (currentBottomList.get(j).getPosition() == i) {
                    bottomData = new BottomLevelIconTextsBean();
                    bottomData.setPosition(i);
                    if (TextUtils.isEmpty(currentBottomList.get(j).getText())) {
                        bottomData.setText(bottomText[i - 1]);
                    } else {
                        bottomData.setText(currentBottomList.get(j).getText());
                    }
                    bottomData.setIconClick(currentBottomList.get(j).getIconClick());
                    bottomData.setIconNormal(currentBottomList.get(j).getIconNormal());
                    break;
                }
            }
            if (bottomData == null) {
                bottomData = new BottomLevelIconTextsBean();
                bottomData.setPosition(i);
                bottomData.setText(bottomText[i - 1]);
                bottomData.setIconClick("");
                bottomData.setIconNormal("");
            }
            bottomList.add(bottomData);
        }
        return bottomList;
    }

    private CustomFragment getFragment(int tabIndex) {
        CustomFragment fragment;
        switch (tabIndex) {
            case TAB_INDEX:
                fragment = indexFragment;
                break;
            case TAB_OTHER:
                fragment = yourFragment;
                break;
            case TAB_MINE:
                fragment = mineFragment;
                break;
            default:
                fragment = indexFragment;
        }
        return fragment;
    }

    @Override
    public void onTabSelected(int position) {
        switch (position) {
            case 0: {
                clickFragmen = TAB_INDEX;
                mainTab.setVisibility(View.VISIBLE);
                mainTab.setEnabled(true);
                currentFragment = CommonUtils.switchFragmentContent(currentFragment,
                        currentFragment, indexFragment, R.id.index_frag, MainActivity.this);

                break;
            }
            case 1: {
                clickFragmen = TAB_OTHER;
                currentFragment = CommonUtils.switchFragmentContent(currentFragment,
                        currentFragment, yourFragment, R.id.index_frag, MainActivity.this);
                break;
            }
            case 2: {
                clickFragmen = TAB_MINE;
                currentFragment = CommonUtils.switchFragmentContent(currentFragment,
                        currentFragment, mineFragment, R.id.index_frag, MainActivity.this);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

}
