package com.imindera.wgy.net.builder;

import com.imindera.wgy.net.utils.OkHttpUtils;
import com.imindera.wgy.net.request.OtherRequest;
import com.imindera.wgy.net.request.RequestCall;

/**
 * Created by zhouyu on 2018/9/4.
 */
public class HeadBuilder extends GetBuilder {
    @Override
    public RequestCall build() {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers, id).build();
    }
}
