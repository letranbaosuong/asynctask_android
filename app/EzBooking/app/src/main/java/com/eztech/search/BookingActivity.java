package com.eztech.search;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class BookingActivity extends AppCompatActivity {
    ImageView img;
    TextView txtName, txtAddress;
    String value = "";
    Rooms r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        refactor();
        value =  Objects.requireNonNull(getIntent().getExtras()).getString("key_id");
        //Toast.makeText(BookingActivity.this, "You Clicked at " + value, Toast.LENGTH_SHORT).show();
        assert value != null;
        r = findRoom(Integer.parseInt(value));
        assert r != null;
        img.setBackgroundResource(r.getImageResource());
        txtName.setText(r.getName());
        txtAddress.setText("Địa chỉ: " + r.getAddress());
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

    private void refactor () {
        img = findViewById(R.id.imageViewBooking);
        txtName = findViewById(R.id.name);
        txtAddress = findViewById(R.id.address);
    }
}
