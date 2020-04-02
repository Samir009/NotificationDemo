package com.samir.luniva.models;

/**
 * created by SAMIR SHRESTHA on 2/19/2020  at 5:56 PM
 */

public class Fcm_User_Device_Token {

    private String user_id;
    private String id;
    private String token;

    public Fcm_User_Device_Token() {
    }

    public Fcm_User_Device_Token(String user_id,String id, String token) {
        this.id = id;
        this.token = token;
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
