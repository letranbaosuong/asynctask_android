package com.eztech.search;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    ArrayList<Address> listAddress, listSearch;
    AdapterAddress adapterAddress;
    ListView listAdd;
    EditText txtNhap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listAddress = new ArrayList<>();
        AddToList();
        listAdd = findViewById(R.id.listViewSearch);
        txtNhap= findViewById(R.id.txtNhap);
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

    }

    public void btnSearch_Clicked(View view) {
        new SearchAddress().execute(txtNhap.getText().toString());
    }

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
                for (int i = 0; i < listAddress.size(); i++) {
                    Address a = listAddress.get(i);
                    String name = removeAccent(a.getName().toLowerCase());
                    String ads = removeAccent(a.getAddress().toLowerCase());
                    String string = removeAccent(strings[0].toLowerCase());
                    if (name.contains(string) || ads.contains(string))
                        listSearch.add(a);
                }
            }
            adapterAddress = new AdapterAddress(listSearch);
            listAdd.setAdapter(adapterAddress);
        }
    }

    private static final char[] SOURCE_CHARACTERS = {'À', 'Á', 'Â', 'Ã', 'È', 'É',
            'Ê', 'Ì', 'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â',
            'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý',
            'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
            'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ',
            'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ',
            'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ',
            'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ',
            'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ',
            'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ',
            'ữ', 'Ự', 'ự',};

    private static final char[] DESTINATION_CHARACTERS = {'A', 'A', 'A', 'A', 'E',
            'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a',
            'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u',
            'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u',
            'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
            'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e',
            'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
            'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
            'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
            'o', 'O', 'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
            'U', 'u', 'U', 'u',};

    public static char removeAccent(char ch) {
        int index = Arrays.binarySearch(SOURCE_CHARACTERS, ch);
        if (index >= 0) {
            ch = DESTINATION_CHARACTERS[index];
        }
        return ch;
    }

    public static String removeAccent(String str) {
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < sb.length(); i++) {
            sb.setCharAt(i, removeAccent(sb.charAt(i)));
        }
        return sb.toString();
    }

    private void AddToList() {
        String uri = "@drawable/room_1";
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listAddress.add(new Address(1, "Gulf Stream Cottages", "4101 Mayfair Street, Myrtle Beach, SC 29577, Mỹ", imageResource));

        uri = "@drawable/room_2";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listAddress.add(new Address(2, "ARory Hotel", "167 Đường 3/2 ,Phường 4 ,Đà Lạt, Việt Nam", imageResource));

        uri = "@drawable/room_3";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listAddress.add(new Address(3, "The Art", "A31, Khu Quy Hoạch, Phan Đình Phùng, phường 2, Đà Lạt, Việt Nam", imageResource));

        uri = "@drawable/room_4";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listAddress.add(new Address(4, "Silent Night Dem Lanh Hotel", "05 Phạm Ngũ Lão, Quận 1, Đà Lạt, Việt Nam", imageResource));

        uri = "@drawable/room_5";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listAddress.add(new Address(5, "Prince II Hotel", "42B Hàng Giấy, Quận Hoàn Kiếm, Hà Nội, Việt Nam", imageResource));

        uri = "@drawable/room_6";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listAddress.add(new Address(6, "Hanoi Amber Hotel", "8/50 Đào Duy Từ, Quận Hoàn Kiếm, Hà Nội, Việt Nam", imageResource));

        uri = "@drawable/room_7";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listAddress.add(new Address(7, "Libra Hotel Residence", "44 Phố Dịch Vọng Hậu, Cầu Giấy, Hà Nội, Việt Nam", imageResource));

        uri = "@drawable/room_8";
        imageResource = getResources().getIdentifier(uri, null, getPackageName());
        listAddress.add(new Address(8, "Marie Line Hotel", "283/43 Phạm Ngũ Lão, Quận 1, TP. Hồ Chí Minh, Việt Nam", imageResource));
    }
}
