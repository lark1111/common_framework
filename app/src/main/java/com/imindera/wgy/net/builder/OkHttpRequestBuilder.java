package com.imindera.wgy.net.builder;

import com.imindera.wgy.net.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by zhouyu on 2018/9/4.
 */
public abstract class OkHttpRequestBuilder<T extends OkHttpRequestBuilder> {
    protected String url;
    protected Object tag;
    protected Map<String, String> headers;
    protected Map<String, String> params;
    protected int id;
    protected int contentType;
    protected String system;

    public T id(int id) {
        this.id = id;
        return (T) this;
    }

    public T url(String url) {
        this.url = url;
        return (T) this;
    }


    public T tag(Object tag) {
        this.tag = tag;
        return (T) this;
    }

    public T contentType(int contentType) {
        this.contentType = contentType;
        return (T) this;
    }

    public T system(String system) {
        this.system = system;
        return (T) this;
    }

    public T headers(Map<String, String> headers) {
        this.headers = headers;
        return (T) this;
    }

    public T addHeader(String key, String val) {
        if (this.headers == null) {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return (T) this;
    }

    public abstract RequestCall build();
}
