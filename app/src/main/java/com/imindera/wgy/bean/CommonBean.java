package com.imindera.wgy.bean;

/**
 * Created by zhouyu on 2018/9/3.
 */

public class CommonBean {
    private String msg;
    private String code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        if (code == null) {
            return "数据格式错误";
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
