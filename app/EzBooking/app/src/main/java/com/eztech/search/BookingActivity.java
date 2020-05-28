package com.eztech.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookingActivity extends AppCompatActivity {
    ImageView img;
    String value = "";
    Rooms r, test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        img = findViewById(R.id.imageViewBooking);
        value =  Objects.requireNonNull(getIntent().getExtras()).getString("key_id");
        //Toast.makeText(BookingActivity.this, "You Clicked at " + value, Toast.LENGTH_SHORT).show();
        r = findRoom(Integer.valueOf(value));
        img.setBackgroundResource(r.getImageResource());
    }

    private Rooms findRoom(int id) {
        boolean existed = false;
        int index = 0;
        for (int i = 0; i < MainActivity.listRooms.size(); i++) {
            if (MainActivity.listRooms.get(i).getId() == id) {
                existed = true;
                index = i;
                break;
            }
        }
        if(existed)
            return MainActivity.listRooms.get(index);
        else return null;
    }
}
