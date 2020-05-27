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
        listAddress.add(new Address(1, "My house 1", "Landmark"));
        listAddress.add(new Address(2, "My house 2", "Landmark"));
        listAddress.add(new Address(3, "My house 3", "Landmark"));
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
                    if (a.getName().toLowerCase().contains(strings[0].toLowerCase()))
                        listSearch.add(a);
                }
            }
            adapterAddress = new AdapterAddress(listSearch);
            listAdd.setAdapter(adapterAddress);
        }
    }
}
