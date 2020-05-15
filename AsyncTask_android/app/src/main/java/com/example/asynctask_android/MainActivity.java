package com.example.asynctask_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnXyLy ,btnGoto;
    TextView txtThongTin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnXyLy= (Button) findViewById(R.id.btnXuLy);
        txtThongTin=(TextView) findViewById(R.id.txtThongTin);
        btnGoto=(Button) findViewById(R.id.btnGoto);
        btnXyLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 new CongViec().execute();
            }
        });
        btnGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doOpenDownloadActivity();
            }
        });

    }
    public void doOpenDownloadActivity(){
        Intent intent=new Intent(MainActivity.this,DownloadActivity.class);
        intent.putExtra("Key_1", "Truyền một String");  // Truyền một String
        intent.putExtra("Key_2", 5);                    // Truyền một Int
        intent.putExtra("Key_3", true);                 // Truyền một Boolean
        MainActivity.this.startActivity(intent);
    }
  //  asyncTask cơ bản
    private class CongViec extends AsyncTask<Void,String,String>{

        //hàm trước khi xử lý
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtThongTin.setText("bắt đầu nha!!. "+"\n");
        }

        //hàm chính bắt buộc phải có
        @Override
        protected String doInBackground(Void... voids) {

            for(int i =1 ; i<= 5 ;i ++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress("Xong việc  " +i+"\n");

              //  //methods must be call user interface Thread not here
            // txtThongTin.setText("xong việc "+i);
            }
            return "xong rồi nha!!";
        }


        //sau khi nó xử lý xong
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            txtThongTin.append(s);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            txtThongTin.append(values[0]);
        }
    }


}
