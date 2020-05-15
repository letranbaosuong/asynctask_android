package com.example.asynctask_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DownloadActivity extends AppCompatActivity {
    List<DownloadFile> data;
    ArrayAdapter<DownloadFile> apdater;
    ListView listView;
    EditText txtLink;
    Button btnDownload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        listView = findViewById(R.id.listView);
        txtLink=findViewById(R.id.txtLink);
        btnDownload =findViewById(R.id.btnDownload);

        data= new ArrayList<>();
    }
}
