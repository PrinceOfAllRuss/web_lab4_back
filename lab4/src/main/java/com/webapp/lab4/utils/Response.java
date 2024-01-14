package com.webapp.lab4.utils;

public class Response {
    String msg = "";
    boolean condition = false;
    String token = "";
    
    public Response() {}
    
    public Response(String msg, boolean condition, String token) {
        this.msg = msg;
        this.condition = condition;
        this.token = token;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    
}
