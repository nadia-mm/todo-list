package com.nadiamm.todo;

import java.util.Collections;
import java.util.List;

public class Response<T> {

    private boolean success;
    private List<T> data;

    private String message;

    public Response(boolean success, List<T> data, String message) {
        this.success = success;
        this.data = data != null ? data : Collections.emptyList();
        this.message = message;
    }

    public Response(boolean success, String message) {
        this(success, Collections.emptyList(), message);
    }


    public Response(boolean success, T data, String message) {
        this(success, Collections.singletonList(data), message);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
