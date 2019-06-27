package org.hellosix.south.door.model;


/**
 * @author lzz
 * @date 2018/2/5.
 */
public class Response {

    private int code;

    private String msg;

    private Object data;

    public Response() {
    }

    public Response(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Response success() {
        return new Response(0, "success", null);
    }

    public static Response fail() {
        return new Response(1, "fail", null);
    }

    public static Response success(Object data) {
        return new Response(0, "success", data);
    }

    public static Response fail(Object data) {
        return new Response(1, "fail", data);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
