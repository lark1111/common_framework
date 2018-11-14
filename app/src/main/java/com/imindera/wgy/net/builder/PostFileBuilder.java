package com.imindera.wgy.net.builder;

import com.imindera.wgy.net.request.RequestCall;
import com.imindera.wgy.net.request.PostFileRequest;

import java.io.File;

import okhttp3.MediaType;

/**
 * Created by zhouyu on 2018/9/4.
 */
public class PostFileBuilder extends OkHttpRequestBuilder<PostFileBuilder>
{
    private File file;
    private MediaType mediaType;


    public OkHttpRequestBuilder file(File file)
    {
        this.file = file;
        return this;
    }

    public OkHttpRequestBuilder mediaType(MediaType mediaType)
    {
        this.mediaType = mediaType;
        return this;
    }

    @Override
    public RequestCall build()
    {
        return new PostFileRequest(url, tag, params, headers, file, mediaType,id).build();
    }


}
