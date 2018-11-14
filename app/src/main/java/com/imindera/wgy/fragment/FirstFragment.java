package com.imindera.wgy.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.imindera.wgy.R;
import com.imindera.wgy.activity.LocalImageHolderView;
import com.imindera.wgy.base.CustomFragment;
import com.imindera.wgy.bean.PublicBean;
import com.imindera.wgy.bean.WGYBean;
import com.imindera.wgy.net.HttpUtil;
import com.imindera.wgy.net.callback.GenericsCallback;
import com.imindera.wgy.net.request.PostFormRequest;
import com.imindera.wgy.net.url.ApiUrls;
import com.imindera.wgy.util.JsonGenericsSerializator;
import com.imindera.wgy.widget.CommonRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by zhouyu on 2018/10/19.
 */

public class FirstFragment extends CustomFragment implements OnItemClickListener {
    @BindView(R.id.tv_test)
    TextView tv_test;
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindString(R.string.app_name)
    String app_name;
    @BindView(R.id.img_photo)
    ImageView img_photo;
    @BindView(R.id.common_refresh)
    CommonRefreshLayout commonRefresh;

    private List<Integer> data=new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //下拉刷新
        commonRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                commonRefresh.finishRefresh(true);
            }
        });
        commonRefresh.setEnableLoadMore(false);
    }

    @Override
    protected void setTitleBar(View view) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void initParams() {
        initview();  //添加数据
    }

    private void initview() {
        tv_test.setText(app_name);
        String url = "http://upload-images.jianshu.io/upload_images/2822163-70ac87aa2d2199d1.jpg";
        Glide.with(this)
                .load(url)
                .into(img_photo);

        data.add(R.drawable.timg);
        data.add(R.drawable.timg2);
        data.add(R.drawable.timg4);

        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new LocalImageHolderView();
            }
        },data).startTurning(2000)
                .setPageIndicator(new int[]{R.drawable.bg_indicator_gray,R.drawable.bg_indicator_red})  //小圆点
                .setOnItemClickListener(this);  //点击监听
    }

    @Override
    public void onItemClick(int position) {
        switch (position) {
            case 0:
                Toast.makeText(getActivity(), "111", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(getActivity(), "222", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(getActivity(), "333", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @OnClick(R.id.tv_test)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_test:
//                CrashReport.testJavaCrash();
                Toast.makeText(getActivity(),"test",Toast.LENGTH_LONG).show();
//                getList();
                break;
            default:
                break;
        }
    }

    private void getList() {
        HttpUtil.get(ApiUrls.TEST_API,TAG)
                .contentType(PostFormRequest.FORMAT_JSON)
                .build()
                .execute(new GenericsCallback<PublicBean>(new JsonGenericsSerializator()) {
                             @Override
                             public void onBefore(Request request, int id) {
                                 super.onBefore(request, id);

                             }

                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Toast.makeText(getActivity(), "网络异常，请检查网络后重试", Toast.LENGTH_SHORT).show();
                             }

                             @Override
                             public void onResponse(PublicBean response, int id) {
                                 if (response == null) {
                                     return;
                                 }

                             }

                             @Override
                             public void onAfter(int id) {
                                 super.onAfter(id);

                             }
                         }
                );
    }

    private void postList() {
        HashMap<String, String> params = new HashMap<>();
        HttpUtil.post(ApiUrls.TEST_API,TAG)
                .params(params)
                .contentType(PostFormRequest.FORMAT_JSON)
                .build()
                .execute(new GenericsCallback<WGYBean>(new JsonGenericsSerializator()) {
                             @Override
                             public void onBefore(Request request, int id) {
                                 super.onBefore(request, id);
                             }

                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 Toast.makeText(getActivity(), "网络异常，请检查网络后重试", Toast.LENGTH_SHORT).show();
                             }

                             @Override
                             public void onResponse(WGYBean response, int id) {
                                 if (response == null || response.getData() == null) {
                                     return;
                                 }
                                 if (!"0".equals(response.getCode())){
                                     Toast.makeText(getActivity(), response.getMsg(), Toast.LENGTH_SHORT).show();
                                     return;
                                 }
                             }

                             @Override
                             public void onAfter(int id) {
                                 super.onAfter(id);
                             }
                         }
                );
    }

    @Override
    public synchronized void onDestroy() {
        super.onDestroy();
    }
}
