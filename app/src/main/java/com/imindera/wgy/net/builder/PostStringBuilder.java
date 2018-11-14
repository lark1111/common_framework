package com.imindera.wgy.net.builder;

import com.imindera.wgy.net.request.RequestCall;
import com.imindera.wgy.net.request.PostStringRequest;

import okhttp3.MediaType;

/**
 * Created by zhouyu on 2018/9/4.
 */
public class PostStringBuilder extends OkHttpRequestBuilder<PostStringBuilder>
{
    private String content;
    private MediaType mediaType;


    public PostStringBuilder content(String content)
    {
        this.content = content;
        return this;
    }

    public PostStringBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostStringRequest(url, tag, params, headers, content, mediaType,id).build();
    }


}
