package com.eztech.search;

class Rooms {
    private int id;
    private String name;
    private String address;
    private int imageResource;
    private int price;
    private int emptyRoom;
    public Rooms(int id, String name, String address, int imageResource, int price, int emptyRoom) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.imageResource = imageResource;
        this.price = price;
        this.emptyRoom = emptyRoom;
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

    public int getPrice() {
        return price;
    }

    public int getEmptyRoom() {
        return emptyRoom;
    }

    public void setEmptyRoom(int emptyRoom) {
        this.emptyRoom = emptyRoom;
    }
}
