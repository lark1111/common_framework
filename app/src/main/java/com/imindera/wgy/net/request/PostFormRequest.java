package com.imindera.wgy.net.request;

import com.google.gson.Gson;
import com.imindera.wgy.net.utils.OkHttpUtils;
import com.imindera.wgy.net.builder.PostFormBuilder;
import com.imindera.wgy.net.callback.Callback;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zhouyu on 2018/9/4.
 */
public class PostFormRequest extends OkHttpRequest {
    public final static int FORMAT_JSON = 1;
    public final static int FORMAT_PLAIN = 2;
    private MediaType mediaTypePlain = MediaType.parse("text/plain;charset=utf-8");
    private MediaType mediaTypeJson = MediaType.parse("application/json; charset=utf-8");

    private List<PostFormBuilder.FileInput> files;
    private int contentType;
    private String system;

    public PostFormRequest(String url, Object tag, int contentType, String system, Map<String, String> params, Map<String, String> headers, List<PostFormBuilder.FileInput> files, int id) {
        super(url, tag, params, headers, id);
        this.files = files;
        this.contentType = contentType;
        this.system = system;
    }

    @Override
    protected RequestBody buildRequestBody() {
        if (contentType == FORMAT_PLAIN) {
            if (files == null || files.isEmpty()) {
                FormBody.Builder builder = new FormBody.Builder();
                addParams(builder);
                FormBody formBody = builder.build();
                return formBody;
            } else {
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);
                addParams(builder);
                for (int i = 0; i < files.size(); i++) {
                    PostFormBuilder.FileInput fileInput = files.get(i);
                    RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileInput.filename)), fileInput.file);
                    builder.addFormDataPart(fileInput.key, fileInput.filename, fileBody);
                }
                return builder.build();
            }
        } else if (contentType == FORMAT_JSON) {
            if ("member".equals(system)) {
                HashMap<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
                map.put("data", params);
                return RequestBody.create(mediaTypeJson, new Gson().toJson(map));
            } else {
                return RequestBody.create(mediaTypeJson, new Gson().toJson(params));
            }
        } else {
            if (files == null || files.isEmpty()) {
                FormBody.Builder builder = new FormBody.Builder();
                addParams(builder);
                FormBody formBody = builder.build();
                return formBody;
            } else {
                MultipartBody.Builder builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM);
                addParams(builder);
                for (int i = 0; i < files.size(); i++) {
                    PostFormBuilder.FileInput fileInput = files.get(i);
                    RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileInput.filename)), fileInput.file);
                    builder.addFormDataPart(fileInput.key, fileInput.filename, fileBody);
                }
                return builder.build();
            }
        }
    }

    @Override
    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        if (callback == null) {
            return requestBody;
        }
        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody, new CountingRequestBody.Listener() {
            @Override
            public void onRequestProgress(final long bytesWritten, final long contentLength) {

                OkHttpUtils.getInstance().getDelivery().execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.inProgress(bytesWritten * 1.0f / contentLength, contentLength, id);
                    }
                });

            }
        });
        return countingRequestBody;
    }

    @Override
    protected Request buildRequest(RequestBody requestBody) {
        return builder.post(requestBody).build();
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    private void addParams(MultipartBody.Builder builder) {
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + key + "\""),
                        RequestBody.create(null, params.get(key)));
            }
        }
    }

    private void addParams(FormBody.Builder builder) {
        if (params != null) {
            for (String key : params.keySet()) {
                builder.add(key, params.get(key));
            }
        }
    }

}
