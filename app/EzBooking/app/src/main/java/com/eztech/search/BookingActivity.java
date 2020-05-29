package com.eztech.search;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class BookingActivity extends AppCompatActivity {
    ImageView img;
    TextView txtName, txtAddress, txtPrice;
    String value = "";
    Rooms r;
    EditText editDatein, editeDateOut;
    @SuppressLint("SetTextI18n")
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
        txtPrice.setText("Giá: "+ Formatted.getFormatted(r.getPrice()) + "/đêm");

        editDatein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(editDatein);
            }
        });
    }

    private void showDateDialog(final EditText date) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String strDate = formatter.format(calendar.getTime());
                date.setText(strDate);
            }
        };
        new DatePickerDialog(BookingActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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
        txtPrice = findViewById(R.id.txtPrice);
        editDatein = findViewById(R.id.editDateIn);
        editeDateOut = findViewById( (R.id.editDateOut));
    }
}
