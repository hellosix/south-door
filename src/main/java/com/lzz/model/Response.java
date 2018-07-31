package com.lzz.model;

import org.springframework.stereotype.Component;

/**
 * Created by lzz on 2018/2/4.
 */
@Component
public class Response {
    int code;
    String msg;
    Object result;

    public Response(){
        //ignore
    }

    public Response(int code, String msg, Object result){
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public static Response Success(){
        return new Response(0, "success", null);
    }
    public static Response Fail(){
        return new Response(1, "fail", null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
