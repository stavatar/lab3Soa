package com.utils;

import java.io.Serializable;

public  class Message  {
    private int code;
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object text) {
        this.data = text;
    }


}