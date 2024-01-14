package com.webapp.lab4.home;

public class ClientPoint {
    private String x;
    private String y;
    private String r;
    private String method;
    private String token;

    public ClientPoint(String x, String y, String r, String method, String token) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.method = method;
        this.token = token;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
}
