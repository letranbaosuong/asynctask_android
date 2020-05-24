package com.eztech.noasynctaskdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnXyLy ,btnGoto;
    TextView txtThongTin;
    ProgressBar prb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnXyLy= (Button) findViewById(R.id.btnXuLy);
        txtThongTin=(TextView) findViewById(R.id.txtThongTin);
        btnGoto=(Button) findViewById(R.id.btnGoto);
        prb=(ProgressBar) findViewById(R.id.pb);
        btnXyLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CongViec();
            }
        });
        btnGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doOpenDownloadActivity();
            }
        });
        prb.setVisibility(View.VISIBLE);
    }
    private void CongViec() {
        txtThongTin.setText("Bắt đầu nha! \n");
        for(int i =1 ; i<= 5 ;i++){
            try {
                Thread.sleep(1000);
                int currentProgress= (int)(100 * i/5);
                txtThongTin.append("Xong việc "+i+"\n");
                prb.setProgress(currentProgress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        txtThongTin.append("Xong rồi nha!\n");
    }

    private void doOpenDownloadActivity(){
        Intent intent=new Intent(MainActivity.this,DownloadActivity.class);
        intent.putExtra("Key_1", "Truyền một String");  // Truyền một String
        intent.putExtra("Key_2", 5);                    // Truyền một Int
        intent.putExtra("Key_3", true);                 // Truyền một Boolean
        MainActivity.this.startActivity(intent);
    }
}
