package com.eztech.search;

class Rooms {
    private int id;
    private String name;
    private String address;
    private int imageResource;
    private int imageCircleResource;
    public Rooms(int id, String name, String address, int imageResource,  int imageCircleResource) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.imageResource = imageResource;
        this.imageCircleResource = imageCircleResource;
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

    public int getImageCircleResource() {
        return imageCircleResource;
    }
}
