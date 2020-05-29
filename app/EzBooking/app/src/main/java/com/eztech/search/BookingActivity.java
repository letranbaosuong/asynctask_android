package com.eztech.search;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class BookingActivity extends AppCompatActivity {
    ImageView img;
    ImageButton imageButtonDat;
    TextView txtName, txtAddress, txtPrice, txtGia, txtTongGia;
    String value = "";
    Rooms r;
    EditText editeDateIn, editeDateOut;
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
        if(r.getEmptyRoom() == 0) {
            editeDateOut.setEnabled(false);
            editeDateIn.setEnabled(false);
            imageButtonDat.setEnabled(false);
            imageButtonDat.setImageResource(R.drawable.hp);
        }
        editeDateIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(editeDateIn);
            }
        });
        editeDateOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(editeDateOut);
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
                try {
                    compareDate();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        new DatePickerDialog(BookingActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @SuppressLint("SetTextI18n")
    private void compareDate () throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date strDateIn = sdf.parse(editeDateIn.getText().toString());
        Date strDateOut = sdf.parse(editeDateOut.getText().toString());
        assert strDateOut != null;
        assert strDateIn != null;
        if(strDateOut.after(strDateIn)) {
            imageButtonDat.setEnabled(true);
            long diff = strDateOut.getTime() - strDateIn.getTime();
            txtGia.setText("Giá "+ TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + " đêm" );
            int tong = (int) r.getPrice() * (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            txtTongGia.setText(Formatted.getFormatted(tong)+"đ");
        } else
        {
            imageButtonDat.setEnabled(false);
            txtGia.setText("Giá 0 đêm" );
            txtTongGia.setText("0đ");
        }
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
        editeDateIn = findViewById(R.id.editDateIn);
        editeDateOut = findViewById( (R.id.editDateOut));
        txtGia = findViewById(R.id.gia);
        txtTongGia = findViewById(R.id.tongGia);
        imageButtonDat = findViewById(R.id.imageButtonDat);
    }
}
