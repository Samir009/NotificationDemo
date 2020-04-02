package com.samir.luniva.models;

/**
 * created by SAMIR SHRESTHA on 2/10/2020  at 3:52 PM
 */

public class AddToCardModel {
    private int id;
    private int clinic_id;
    private int test_id;
    private double price;
    private double total_price;
    private String display_name;
    private String created_on;

    public AddToCardModel(int id, int clinic_id, int test_id, double price, double total_price, String display_name, String created_on) {
        this.id = id;
        this.clinic_id = clinic_id;
        this.test_id = test_id;
        this.price = price;
        this.total_price = total_price;
        this.display_name = display_name;
        this.created_on = created_on;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClinic_id() {
        return clinic_id;
    }

    public void setClinic_id(int clinic_id) {
        this.clinic_id = clinic_id;
    }

    public int getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }
}
