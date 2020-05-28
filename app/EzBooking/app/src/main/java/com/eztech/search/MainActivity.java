package com.eztech.search;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Rooms> listRooms, listSearch;
    AdapterRooms adapterRooms;
    AdapterGridViewRooms adapterGridViewRooms;
    ListView listAdd;
    EditText txtNhap;
    GridView gridViewRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listRooms = new ArrayList<>();
        AddToList();
        listAdd = findViewById(R.id.listViewSearch);
        txtNhap= findViewById(R.id.txtNhap);
        gridViewRoom = findViewById(R.id.gridViewRoom);
        adapterGridViewRooms = new AdapterGridViewRooms(listRooms);;
        gridViewRoom.setAdapter(adapterGridViewRooms);

        txtNhap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                new SearchAddress().execute(txtNhap.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        gridViewRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "You Clicked at " + id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, BookingActivity.class);
                intent.putExtra("key_id", "" + id);
                startActivity(intent);
            }
        });
    }

    public void btnSearch_Clicked(View view) {
        new SearchRooms().execute(txtNhap.getText().toString());
    }

    //SEARCH ADDRESS
    @SuppressLint("StaticFieldLeak")
    private class SearchAddress extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            publishProgress(strings[0]);
            return strings[0];
        }

        @Override
        protected void onProgressUpdate(String... strings) {
            listSearch = new ArrayList<>();
            if(txtNhap.getText().toString().matches("^(\\s|\\S)*(\\S)+(\\s|\\S)*$")) {
                for (int i = 0; i < listRooms.size(); i++) {
                    Rooms r = listRooms.get(i);
                    String name = removeAccent(r.getName().toLowerCase());
                    String address = removeAccent(r.getAddress().toLowerCase());
                    String string = removeAccent(strings[0].toLowerCase());
//                    Toast.makeText(MainActivity.this,
//                            name+"  //  "+ address + "  //  "+string, Toast.LENGTH_SHORT).show();
                    if (name.contains(string) || address.contains(string))
                        listSearch.add(r);
                }
            }
            adapterRooms = new AdapterRooms(listSearch);
            listAdd.setAdapter(adapterRooms);
        }
    }

    //SEARCH ROOMS
    @SuppressLint("StaticFieldLeak")
    private class SearchRooms extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            publishProgress(strings[0]);
            return strings[0];
        }

        @Override
        protected void onProgressUpdate(String... strings) {
            if(txtNhap.getText().toString().equals("")) {
                adapterGridViewRooms = new AdapterGridViewRooms(listRooms);
                gridViewRoom.setAdapter(adapterGridViewRooms);
            } else {
                listSearch = new ArrayList<>();
                if(txtNhap.getText().toString().matches("^(\\s|\\S)*(\\S)+(\\s|\\S)*$")) {
                    for (int i = 0; i < listRooms.size(); i++) {
                        Rooms r = listRooms.get(i);
                        String name = removeAccent(r.getName().toLowerCase());
                        String address = removeAccent(r.getAddress().toLowerCase());
                        String string = removeAccent(strings[0].toLowerCase());
//                    Toast.makeText(MainActivity.this,
//                            name+"  //  "+ address + "  //  "+string, Toast.LENGTH_SHORT).show();
                        if (name.contains(string) || address.contains(string))
                            listSearch.add(r);
                    }
                }
                adapterGridViewRooms = new AdapterGridViewRooms(listSearch);
                gridViewRoom.setAdapter(adapterGridViewRooms);
                txtNhap.setText("");
            }
        }
    }

    public static String removeAccent(String s) {

        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D');
    }

    private void AddToList() {
        String uri = "@drawable/room_1";
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(1, "Gulf Stream Cottages", "4101 Mayfair Street, Myrtle Beach, SC 29577, Mỹ", imageResource));
        uri = "@drawable/room_2";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(2, "ARory Hotel", "167 Đường 3/2 ,Phường 4 ,Đà Lạt, Việt Nam", imageResource));

        uri = "@drawable/room_3";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(3, "The Art", "A31, Khu Quy Hoạch, Phan Đình Phùng, phường 2, Đà Lạt, Việt Nam", imageResource));

        uri = "@drawable/room_4";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(4, "Silent Night Dem Lanh Hotel", "05 Phạm Ngũ Lão, Quận 1, Đà Lạt, Việt Nam", imageResource));

        uri = "@drawable/room_5";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(5, "Prince II Hotel", "42B Hàng Giấy, Quận Hoàn Kiếm, Hà Nội, Việt Nam", imageResource));

        uri = "@drawable/room_6";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(6, "Hanoi Amber Hotel", "8/50 Đào Duy Từ, Quận Hoàn Kiếm, Hà Nội, Việt Nam", imageResource));

        uri = "@drawable/room_7";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(7, "Libra Hotel Residence", "44 Phố Dịch Vọng Hậu, Cầu Giấy, Hà Nội, Việt Nam", imageResource));

        uri = "@drawable/room_8";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(8, "Marie Line Hotel", "283/43 Phạm Ngũ Lão, Quận 1, TP. Hồ Chí Minh, Việt Nam", imageResource));

        uri = "@drawable/room_9";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(9, "Wyndham Dubai Marina", "P.O. Box: 215373, Al Seba Street,, Bến Du Thuyền Dubai, Dubai, United Arab Emirates (Các Tiểu Vương Quốc Ả Rập Thống Nhất)", imageResource));

        uri = "@drawable/room_10";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(10, "City Seasons Towers ", "Khalifa Bin Zayed Road, Mankhool, Dubai Next to Burjuman Mall , P.O.Box- 5847, Bur Dubai, Dubai, United Arab Emirates (Các Tiểu Vương Quốc Ả Rập Thống Nhất)", imageResource));

        uri = "@drawable/room_11";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(11, "Vista Hotel", "Abu Baker Al Siddique Road Al Muraqqabat, Deira, Dubai, United Arab Emirates (Các Tiểu Vương Quốc Ả Rập Thống Nhất)", imageResource));

        uri = "@drawable/room_12";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(12, "Aloft Palm Jumeirah", "East Crescent , Palm Jumeirah, Palm Jumeirah, Dubai, United Arab Emirates (Các Tiểu Vương Quốc Ả Rập Thống Nhất)", imageResource));
    }
}
