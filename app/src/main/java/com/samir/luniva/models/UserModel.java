package com.samir.luniva.models;

import android.graphics.Bitmap;

/**
 * created by SAMIR SHRESTHA on 1/31/2020  at 11:55 AM
 */

public class UserModel {
    String name, address;
    Bitmap img;

    public UserModel(String name, String address, Bitmap img) {
        this.name = name;
        this.address = address;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
