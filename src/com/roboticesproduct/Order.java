package com.roboticesproduct;

import java.io.*;

public class Order {
    private String username;
    private String productName;

    public Order(String username, String productName) {
        this.username = username;
        this.productName = productName;
    }

    public String getUsername() {
        return username;
    }

    public String getProductName() {
        return productName;
    }
}
