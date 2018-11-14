package com.imindera.wgy.net.utils;

import com.imindera.wgy.net.builder.GetBuilder;
import com.imindera.wgy.net.builder.HeadBuilder;
import com.imindera.wgy.net.builder.OtherRequestBuilder;
import com.imindera.wgy.net.builder.PostFileBuilder;
import com.imindera.wgy.net.builder.PostFormBuilder;
import com.imindera.wgy.net.builder.PostStringBuilder;
import com.imindera.wgy.net.callback.Callback;
import com.imindera.wgy.net.request.RequestCall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by zhouyu on 2018/9/4.
 */
public class OkHttpUtils {
    public static final long DEFAULT_MILLISECONDS = 10_000L;
    private volatile static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Platform mPlatform;
    private List<Call> runningAsyncCalls;

    public OkHttpUtils(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        } else {
            mOkHttpClient = okHttpClient;
        }
        runningAsyncCalls = Collections.synchronizedList(new ArrayList<Call>());
        mPlatform = Platform.get();
    }


    public static OkHttpUtils initClient(OkHttpClient okHttpClient) {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils(okHttpClient);
                }
            }
        }
        return mInstance;
    }

    public static OkHttpUtils getInstance() {
        return initClient(null);
    }


    public Executor getDelivery() {
        return mPlatform.defaultCallbackExecutor();
    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }

    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post() {
        return new PostFormBuilder();
    }

    public static OtherRequestBuilder put() {
        return new OtherRequestBuilder(METHOD.PUT);
    }

    public static HeadBuilder head() {
        return new HeadBuilder();
    }

    public static OtherRequestBuilder delete() {
        return new OtherRequestBuilder(METHOD.DELETE);
    }

    public static OtherRequestBuilder patch() {
        return new OtherRequestBuilder(METHOD.PATCH);
    }

    public void execute(final RequestCall requestCall, Callback callback, boolean success, boolean test, String str) {
        if (test) {
            if (callback == null)
                callback = Callback.CALLBACK_DEFAULT;
            final Callback finalCallback = callback;
            final int id = requestCall.getOkHttpRequest().getId();
            Call call = requestCall.getCall();
            if (success) {
                Object o = null;
                try {
                    o = finalCallback.parseNetworkResponse(id, str);
                    sendSuccessResultCallback(call, o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                }
            } else {
                sendFailResultCallback(call, new Exception("Just for test"), finalCallback, id);
            }
            return;
        }
        execute(requestCall, callback);
    }

    public void execute(final RequestCall requestCall, Callback callback) {
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        final int id = requestCall.getOkHttpRequest().getId();

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                sendFailResultCallback(call, e, finalCallback, id);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                try {
                    if (call.isCanceled()) {
                        sendFailResultCallback(call, new IOException("Canceled!"), finalCallback, id);
                        return;
                    }

                    if (!finalCallback.validateReponse(response, id)) {
                        sendFailResultCallback(call, new IOException("request failed , reponse's code is : " + response.code()), finalCallback, id);
                        return;
                    }

                    Object o = finalCallback.parseNetworkResponse(response, id);
                    sendSuccessResultCallback(call, o, finalCallback, id);
                } catch (Exception e) {
                    sendFailResultCallback(call, e, finalCallback, id);
                } finally {
                    if (response.body() != null)
                        response.body().close();
                }

            }
        });
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback, final int id) {
        if (callback == null) return;
        runningAsyncCalls.add(call);
        mPlatform.execute(new Runnable() {
            @Override
            public void run() {
                runningAsyncCalls.remove(call);
                if (call.isCanceled()) {
                    callback.onCancel(id);
                    return;
                }
                callback.onError(call, e, id);
                callback.onAfter(id);
            }
        });
    }

    public void sendSuccessResultCallback(final Call call, final Object object, final Callback callback, final int id) {
        synchronized (this) {
            if (callback == null) return;
            runningAsyncCalls.add(call);
            mPlatform.execute(new Runnable() {
                @Override
                public void run() {
                    runningAsyncCalls.remove(call);
                    if (call.isCanceled()) {
                        callback.onCancel(id);
                        return;
                    }
                    callback.onResponse(object, id);
                    callback.onAfter(id);
                }
            });
        }
    }

    public void cancelTag(Object tag) {
        synchronized (this) {
            for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
            for (Call call : runningAsyncCalls) {
                if (tag.equals(call.request().tag())) {
                    call.cancel();
                }
            }
        }
    }

    public static class METHOD {
        public static final String HEAD = "HEAD";
        public static final String DELETE = "DELETE";
        public static final String PUT = "PUT";
        public static final String PATCH = "PATCH";
    }
}

