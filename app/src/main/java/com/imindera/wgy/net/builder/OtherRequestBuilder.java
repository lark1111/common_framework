package com.imindera.wgy.net.builder;

import com.imindera.wgy.net.request.RequestCall;
import com.imindera.wgy.net.request.OtherRequest;

import okhttp3.RequestBody;

/**
 * Created by zhouyu on 2018/9/4.
 * DELETE、PUT、PATCH等其他方法
 */
public class OtherRequestBuilder extends OkHttpRequestBuilder<OtherRequestBuilder> {
    private RequestBody requestBody;
    private String method;
    private String content;

    public OtherRequestBuilder(String method) {
        this.method = method;
    }

    @Override
    public RequestCall build() {
        return new OtherRequest(requestBody, content, method, url, tag, params, headers, id).build();
    }

    public OtherRequestBuilder requestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public OtherRequestBuilder requestBody(String content) {
        this.content = content;
        return this;
    }


}
