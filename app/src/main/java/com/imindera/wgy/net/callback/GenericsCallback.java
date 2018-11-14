package com.imindera.wgy.net.callback;

import com.imindera.wgy.util.IGenericsSerializator;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhouyu on 2018/9/3.
 */
public abstract class GenericsCallback<T> extends Callback<T> {
    IGenericsSerializator mGenericsSerializator;

    public GenericsCallback(IGenericsSerializator serializator) {
        mGenericsSerializator = serializator;
    }

    @Override
    public T parseNetworkResponse(int id, String str) throws Exception {
        return parse(str, null);
    }

    @Override
    public T parseNetworkResponse(Response response, int id) throws IOException {
        String string = response.body().string();
        String url = "";
        try {
            url = response.request().url().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parse(string, url);
    }

    private T parse(String string, String url) {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        if (entityClass == String.class) {
            return (T) string;
        }
        T bean = mGenericsSerializator.transform(string, entityClass);
        if (bean == null) {
            throw new NullPointerException();
        }
        return bean;
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
    }

    static Map<String, String> cache = new HashMap<>();
}