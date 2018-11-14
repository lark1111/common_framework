package com.imindera.wgy.net;

import com.imindera.wgy.net.builder.GetBuilder;
import com.imindera.wgy.net.builder.PostFormBuilder;
import com.imindera.wgy.net.request.RequestCall;
import com.imindera.wgy.util.CommonUtils;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zhouyu on 2018/9/4.
 */
public class HttpUtil {
    /**
     * 增加tag用来批量取消网络请求
     *
     * @param url
     * @param tag
     * @return
     */
    public static PostFormBuilder post(String url, String tag) {
        return new MyPostFormBuilder()
                .url(url)
                .tag(tag)
                .headers(CommonUtils.getUpNetworkHashMap());
    }

    /**
     * 创建一个OkHttpClient对象
     */
    private static OkHttpClient okHttpClient = new OkHttpClient();

    /**
     * post请求，发送json数据
     *
     * @param url      地址
     * @param jsonData json数据
     * @param callback 回调接口
     * @return
     */
    public static Request postJsonData(String url, String jsonData, Callback callback) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        //申明给服务端传递一个json串
        //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
        RequestBody requestBody = RequestBody.create(JSON, jsonData);
        //创建一个请求对象
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
        return request;
    }

    public static GetBuilder get(String url, String tag) {
        return new MyGetBuilder()
                .url(url)
                .tag(tag)
                .headers(CommonUtils.getUpNetworkHashMap());
    }

    private static class MyPostFormBuilder extends PostFormBuilder {

        @Override
        public PostFormBuilder headers(Map<String, String> headers) {
            if (this.headers == null) {
                return super.headers(headers);
            } else {
                this.headers.putAll(headers);
            }
            return this;
        }

        @Override
        public RequestCall build() {
            return super.build();
        }
    }

    private static class MyGetBuilder extends GetBuilder {

        @Override
        public GetBuilder headers(Map<String, String> headers) {
            if (this.headers == null) {
                return super.headers(headers);
            } else {
                this.headers.putAll(headers);
            }
            return this;
        }
    }
}