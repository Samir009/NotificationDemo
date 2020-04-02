package com.samir.luniva.models;

/**
 * created by SAMIR SHRESTHA on 2/23/2020  at 3:54 PM
 */

public class NotificationModel {

    String title;
    String body;

    public NotificationModel(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
