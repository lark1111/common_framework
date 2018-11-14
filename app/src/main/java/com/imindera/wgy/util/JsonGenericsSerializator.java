package com.imindera.wgy.util;

import com.google.gson.Gson;

/**
 * Created by zhouyu on 2018/9/3.
 */
public class JsonGenericsSerializator implements IGenericsSerializator {
    Gson mGson = new Gson();
    @Override
    public <T> T transform(String response, Class<T> classOfT) {
//        return mGson.fromJson(response, classOfT);
        return GsonUtils.toBean(response,classOfT);
    }
}
