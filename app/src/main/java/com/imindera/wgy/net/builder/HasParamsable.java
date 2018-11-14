package com.imindera.wgy.net.builder;

import java.util.Map;

/**
 * Created by zhouyu on 2018/9/4.
 */
public interface HasParamsable
{
    OkHttpRequestBuilder params(Map<String, String> params);
    OkHttpRequestBuilder addParams(String key, String val);
}
