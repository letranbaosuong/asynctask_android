package com.eztech.search;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
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
    ImageButton imageButtonDat, imageButtonDateIn,imageButtonDateOut;
    TextView txtName, txtAddress, txtPrice, txtGia, txtTongGia;
    String value;
    Rooms r;
    EditText editDateIn, editDateOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        refactor();
        initialization();

        //events
        imageButtonDateIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(editDateIn);
            }
        });
        imageButtonDateOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(editDateOut);
            }
        });
        imageButtonDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BookingActivity.this, "Cảm ơn bạn đã đặt phòng", Toast.LENGTH_SHORT).show();
                r.setEmptyRoom(r.getEmptyRoom() - 1);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", 1);
                setResult(BookingActivity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    //show date dialog and set text edit text
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

    // compare two dates
    @SuppressLint("SetTextI18n")
    private void compareDate () throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date strDateIn = sdf.parse(editDateIn.getText().toString());
        Date strDateOut = sdf.parse(editDateOut.getText().toString());
        Date strDateNow = sdf.parse(sdf.format(new Date()));
        assert strDateOut != null;
        assert strDateIn != null;
        if(strDateOut.after(strDateIn) && ((strDateIn.after(strDateNow) || strDateIn.equals(strDateNow)))) {
            imageButtonDat.setEnabled(true);
            imageButtonDat.setImageResource(R.drawable.dn);
            long diff = strDateOut.getTime() - strDateIn.getTime();
            txtGia.setText("Giá "+ TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + " đêm" );
            int tong = r.getPrice() * (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            txtTongGia.setText(Formatted.getFormatted(tong)+"đ");
        } else {
            imageButtonDat.setEnabled(false);
            imageButtonDat.setImageResource(R.drawable.dnd);
            txtGia.setText("Giá 0 đêm" );
            txtTongGia.setText("0đ");
        }
    }

    //find rooms
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

    //refactor
    private void refactor () {
        img = findViewById(R.id.imageViewBooking);
        txtName = findViewById(R.id.name);
        txtAddress = findViewById(R.id.address);
        txtPrice = findViewById(R.id.txtPrice);
        editDateIn = findViewById(R.id.editDateIn);
        editDateOut = findViewById( (R.id.editDateOut));
        txtGia = findViewById(R.id.gia);
        txtTongGia = findViewById(R.id.tongGia);
        imageButtonDat = findViewById(R.id.imageButtonDat);
        imageButtonDateIn = findViewById(R.id.imageButtonDateIn);
        imageButtonDateOut = findViewById(R.id.imageButtonDateOut);
    }

    //initialization
    @SuppressLint("SetTextI18n")
    private void initialization() {
        value =  Objects.requireNonNull(getIntent().getExtras()).getString("key_id");
        assert value != null;
        r = findRoom(Integer.parseInt(value));
        assert r != null;
        img.setBackgroundResource(r.getImageResource());
        txtName.setText(r.getName());
        txtAddress.setText("Địa chỉ: " + r.getAddress());
        txtPrice.setText("Giá: "+ Formatted.getFormatted(r.getPrice()) + "/đêm");
        imageButtonDat.setEnabled(false);

        if(r.getEmptyRoom() == 0) {
            imageButtonDateIn.setEnabled(false);
            imageButtonDateOut.setEnabled(false);
            imageButtonDat.setEnabled(false);
            imageButtonDat.setImageResource(R.drawable.hp);
        }
    }
}
