package com.tigercel.controller.support;

/**
 * Created by freedom on 2016/4/12.
 */
public enum AuthResult {
    AUTH_DENIED(0),             // User firewall users are deleted and the user removed.
    AUTH_VALIDATION_FAILED(6),  // User email validation timeout has occured and user/firewall is deleted
    AUTH_ALLOWED(1),            // User was valid, add firewall rules if not present
    AUTH_VALIDATION(5),         // Permit user access to email to get validation email under default rules
    AUTH_ERROR(-1);             // An error occurred during the validation process

    private int code;

    AuthResult(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setStatus(int code) {
        this.code = code;
    }

    public String getId(){
        return name();
    }

    @Override
    public String toString() {
        return String.valueOf(code);
    }
}
