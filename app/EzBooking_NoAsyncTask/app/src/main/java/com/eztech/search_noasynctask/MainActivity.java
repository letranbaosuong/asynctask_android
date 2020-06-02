package com.eztech.search_noasynctask;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;

//NO ASYNCTASK
public class MainActivity extends AppCompatActivity {
    public static ArrayList<Rooms> listRooms, listSearch;
    AdapterRooms adapterRooms;
    AdapterGridViewRooms adapterGridViewRooms;
    ListView listAdd;
    EditText txtNhap;
    GridView gridViewRoom;
    int LAUNCH_SECOND_ACTIVITY = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refactor();
        initialization();

        //events
        txtNhap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SearchAddress(txtNhap.getText().toString());
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
                startActivityForResult(intent, LAUNCH_SECOND_ACTIVITY);
            }
        });

        listAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, "You Clicked at " + id, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, BookingActivity.class);
                intent.putExtra("key_id", "" + id);
                startActivity(intent);
            }
        });
    }

    // get result back from booking activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {
            if(resultCode == MainActivity.RESULT_OK){
                SearchRooms("");
            }
            if (resultCode == MainActivity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    //search room
    public void btnSearch_Clicked(View view) {
        SearchRooms(txtNhap.getText().toString());
    }

    //search rooms, address on search bar
    private void SearchAddress(String s) {
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        listSearch = new ArrayList<>();
        if(txtNhap.getText().toString().matches("^(\\s|\\S)*(\\S)+(\\s|\\S)*$")) {
            for (int i = 0; i < listRooms.size(); i++) {
                Rooms r = listRooms.get(i);
                String name = removeAccent(r.getName().toLowerCase());
                String address = removeAccent(r.getAddress().toLowerCase());
                String string = removeAccent(s.toLowerCase());
                String price = "" + r.getPrice();
//                    Toast.makeText(MainActivity.this,
//                            name+"  //  "+ address + "  //  "+string, Toast.LENGTH_SHORT).show();
                if (name.contains(string) || address.contains(string) || price.contains(string))
                    listSearch.add(r);
            }
        }
        adapterRooms = new AdapterRooms(listSearch);
        listAdd.setAdapter(adapterRooms);
    }


    //search rooms
    private void SearchRooms(String s) {
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        if(txtNhap.getText().toString().equals("")) {
            adapterGridViewRooms = new AdapterGridViewRooms(listRooms);
        } else {
            listSearch = new ArrayList<>();
            if(txtNhap.getText().toString().matches("^(\\s|\\S)*(\\S)+(\\s|\\S)*$")) {
                for (int i = 0; i < listRooms.size(); i++) {
                    Rooms r = listRooms.get(i);
                    String name = removeAccent(r.getName().toLowerCase());
                    String address = removeAccent(r.getAddress().toLowerCase());
                    String string = removeAccent(s.toLowerCase());
                    String price = "" + r.getPrice();
//                    Toast.makeText(MainActivity.this,
//                            name+"  //  "+ address + "  //  "+string, Toast.LENGTH_SHORT).show();
                    if (name.contains(string) || address.contains(string) || price.contains(string))
                        listSearch.add(r);
                }
            }
            adapterGridViewRooms = new AdapterGridViewRooms(listSearch);
        }
        gridViewRoom.setAdapter(adapterGridViewRooms);
    }

    //remove accent
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("").replace('đ','d').replace('Đ','D');
    }

    //refactor
    private void refactor () {
        listAdd = findViewById(R.id.listViewSearch);
        txtNhap= findViewById(R.id.txtNhap);
        gridViewRoom = findViewById(R.id.gridViewRoom);
    }

    //initialization
    @SuppressLint("SetTextI18n")
    private void initialization() {
        listRooms = new ArrayList<>();
        AddToList();
        adapterGridViewRooms = new AdapterGridViewRooms(listRooms);;
        gridViewRoom.setAdapter(adapterGridViewRooms);
    }

    //add items to list
    public void AddToList() {
        String uri = "@drawable/room_1";
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(1, "Gulf Stream Cottages", "4101 Mayfair Street, Myrtle Beach, SC 29577, Mỹ", imageResource, 4800000, 1 ));

        uri = "@drawable/room_2";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(2, "ARory", "167 Đường 3/2 ,Phường 4 ,Đà Lạt, Việt Nam", imageResource, 800000, 2));

        uri = "@drawable/room_3";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(3, "The Art", "A31, Khu Quy Hoạch, Phan Đình Phùng, phường 2, Đà Lạt, Việt Nam", imageResource, 1200000, 3));

        uri = "@drawable/room_4";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(4, "Silent Night", "05 Phạm Ngũ Lão, Quận 1, Đà Lạt, Việt Nam", imageResource, 1300000, 10));

        uri = "@drawable/room_5";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(5, "Prince II", "42B Hàng Giấy, Quận Hoàn Kiếm, Hà Nội, Việt Nam", imageResource, 1500000, 10));

        uri = "@drawable/room_6";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(6, "Hanoi Amber", "8/50 Đào Duy Từ, Quận Hoàn Kiếm, Hà Nội, Việt Nam", imageResource, 900000, 10));

        uri = "@drawable/room_7";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(7, "Libra Hotel Residence", "44 Phố Dịch Vọng Hậu, Cầu Giấy, Hà Nội, Việt Nam", imageResource, 900000, 10));

        uri = "@drawable/room_8";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(8, "Marie Line", "283/43 Phạm Ngũ Lão, Quận 1, TP. Hồ Chí Minh, Việt Nam", imageResource, 750000, 10));

        uri = "@drawable/room_9";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(9, "Wyndham Dubai Marina", "P.O. Box: 215373, Al Seba Street,, Bến Du Thuyền Dubai, Dubai, United Arab Emirates (Các Tiểu)",  imageResource, 1200000, 5));

        uri = "@drawable/room_10";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(10, "City Seasons Towers ", "Khalifa Bin Zayed Road, Mankhool, Dubai Next to Burjuman Mall , P.O.Box- 5847, Bur Dubai, Dubai, United Arab Emirates (Các Tiểu Vương Quốc)", imageResource, 7200000, 5));

        uri = "@drawable/room_11";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(11, "Vista", "Abu Baker Al Siddique Road Al Muraqqabat, Deira, Dubai, United Arab Emirates (Các Tiểu Vương Quốc Ả Rập)", imageResource, 2000000, 5));

        uri = "@drawable/room_12";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listRooms.add(new Rooms(12, "Aloft Palm Jumeirah", "East Crescent , Palm Jumeirah, Palm Jumeirah, Dubai, United Arab Emirates (Các Tiểu Vương Quốc Ả Rập Thống Nhất)", imageResource, 4500000, 5));
    }
}
