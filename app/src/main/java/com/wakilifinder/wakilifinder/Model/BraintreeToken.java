package com.wakilifinder.wakilifinder.Model;

public class BraintreeToken {

    private String clientToken;
    private boolean success;

    public BraintreeToken() {
    }

    public String getClientToken() {
        return clientToken;
    }

    public void setClientToken(String clientToken) {
        this.clientToken = clientToken;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
