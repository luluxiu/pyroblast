package com.tigercel.model.support;

/**
 * Created by freedom on 2016/3/29.
 */
public enum UserType {
    ADMIN("admin"),
    GUEST("user");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId(){
        return name();
    }

    @Override
    public String toString() {
        return type;
    }
}
