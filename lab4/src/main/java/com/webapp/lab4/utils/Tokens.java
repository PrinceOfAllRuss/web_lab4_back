package com.webapp.lab4.utils;

import java.util.HashMap;
import java.util.LinkedList;
import org.springframework.stereotype.Component;

@Component
public class Tokens {
    private final HashMap<String, LinkedList<String>> users = new HashMap<>();
    private final HashMap<String, HashMap<String, String>> tokens = new HashMap<>();

    public String getTokenByName(String name) {
        String token = "";
        for (String key : tokens.keySet()) {
            String user_name = tokens.get(key).get("name");
            if (user_name.equals(name)) {
                token = key;
                break;
            }
        }
        return token;
    }
    
    public String createToken() {
        Long l = System.nanoTime();
        Integer i = l.hashCode();
        String token = i.toString();
        return token;
    }
    
    public void addUser(String name, String browser) {
        LinkedList browsers = new LinkedList();
        browsers.add(browser);
        this.users.put(name, browsers);
    }

    public void addToken(String name, String token) {
        HashMap<String, String> user = new HashMap<>();
        user.put("name", name);
        user.put("last_request", Long.toString(System.nanoTime()));
        this.tokens.put(token, user);
    }
    
    public void addBrowser(String name, String browser) {
        this.users.get(name).add(browser);
    }
    
    public void removeBrowser(String name, String browser) {
        this.users.get(name).remove(browser);
    }
    
    public void logoutUser(String token, String browser) {
        String user_name = tokens.get(token).get("name");
        removeBrowser(user_name, browser);
        if (!this.users.get(user_name).isEmpty()) {
            return;
        }
        this.users.remove(user_name);
        this.tokens.remove(token);
    }
    
    public boolean checkToken(String token) {
        return this.tokens.containsKey(token);
    }
    
    public boolean checkBrowser(String name, String browser) {
        LinkedList<String> browsers = this.users.get(name);
        for (String br : browsers) {
            if (br.equals(browser)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean checkUser(String name) {
        return this.users.containsKey(name);
    }
    
    public boolean checkTimeOfSession(String token, String browser) {
        if(System.nanoTime() - Long.parseLong(
                this.tokens.get(token).get("last_request")) >= 1800000000000L) {
            logoutUser(token, browser);
            return true;
        }
        return false;
    }
    
    public void updateTimeOfSession(String token) {
        this.tokens.get(token).put("last_request", Long.toString(System.nanoTime()));
    }
}
