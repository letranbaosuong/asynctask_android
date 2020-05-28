package com.eztech.search;

class Rooms {
    private int id;
    private String name;
    private String address;
    private int imageResource;
    public Rooms(int id, String name, String address, int imageResource) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.imageResource = imageResource;
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

    public int getImageResource() {
        return imageResource;
    }
}
