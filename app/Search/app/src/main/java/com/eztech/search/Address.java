package com.eztech.search;

import java.util.ArrayList;

class Address {
     private int id;
     private String name;
     private String address;
    public Address(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

}
