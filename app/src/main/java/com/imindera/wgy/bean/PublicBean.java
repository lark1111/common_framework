package com.imindera.wgy.bean;

/**
 * Created by zhouyu on 2018/9/3.
 */

public class PublicBean {
    private String message;
    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        if (status == null) {
            return "数据格式错误";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
