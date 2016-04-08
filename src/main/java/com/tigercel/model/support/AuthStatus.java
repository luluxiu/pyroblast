package com.tigercel.model.support;


/**
 * Created by freedom on 2016/3/29.
 */
public enum AuthStatus {
    VALIDATING("WAITING_TOKEN_VALIDATION"),
    VALIDATED("TOKEN_VALIDATED"),
    LOGOUT("LOGOUT"),
    EXPIRED("EXPIRED");

    private String status;

    AuthStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId(){
        return name();
    }

    @Override
    public String toString() {
        return status;
    }
}
