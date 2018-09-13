package io.phyloyui.client.domain;

import java.io.Serializable;

/**
 * Created by yangyu-ds on 2018/5/16.
 */
public class ResponseEntity<T> implements Serializable {

    /**
     * 状态码
     */
    private int code = 200;

    /**
     * 消息
     */
    private String message;

    /**
     * 承载数据
     */
    private T object;


    public ResponseEntity() {
        //
    }

    public ResponseEntity(T object) {
        this.object = object;
    }

    public static <T> ResponseEntity<T> successResponse(T object) {
        return new ResponseEntity<T>(object);
    }

    public static <T> ResponseEntity<T> errorResponse(int code, String message) {
        ResponseEntity responseEntity = new ResponseEntity();
        responseEntity.setCode(code);
        responseEntity.setMessage(message);
        return responseEntity;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessResponse(){
        return code==200;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}

