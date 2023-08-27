package com.ccompanion.orderservice.response;

public class CCompanionResponseData<T> {
    private boolean success;
    private T body;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
