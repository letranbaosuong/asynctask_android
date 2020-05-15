package com.example.asynctask_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        data= new ArrayList<DownloadFile>();

        getMockData();

        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename =txtLink.getText().toString();
                double fileSize= new Random().nextDouble();
                DownloadFile downloadFile=new DownloadFile(filename,fileSize,1,0);
                data.add(downloadFile);
                apdater.notifyDataSetChanged();

                DownloadTask task= new DownloadTask();
                task.execute(downloadFile);

            }
        });
        apdater= new ArrayAdapter<DownloadFile>(
                DownloadActivity.this,
                R.drawable.list_item,
                R.id.lblFileName,
                data
        ){
            @Override
            public View getView(int position,View convertView,ViewGroup parent) {
                View itemView = super.getView(position,convertView,parent);

                DownloadFile downloadFile=data.get(position);

                TextView lblFileName = itemView.findViewById(R.id.lblFileName);
                TextView lblFileSize = itemView.findViewById(R.id.lblFileSize);
                TextView lblStatus = itemView.findViewById(R.id.lblStatus);
                ProgressBar progressBar=itemView.findViewById(R.id.progressBar);
                ImageView imageView=itemView.findViewById(R.id.imageView2);

                lblFileName.setText(downloadFile.getFilename());
                lblFileSize.setText(downloadFile.getSize()+" MB");
                imageView.setImageResource(downloadFile.getImageResourceId());

                if(downloadFile.getStatus()==1){
                    lblStatus.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.VISIBLE);

                    progressBar.setProgress(downloadFile.getProgress());
                }else if(downloadFile.getStatus()==2){
                    lblStatus.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                   lblStatus.setText("Fail");
                   lblStatus.setTextColor(Color.CYAN);

                }else {
                    lblStatus.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);

                    lblStatus.setText("Completed");
                    lblStatus.setTextColor(Color.GREEN);
                }

                return imageView;
            }
        };

        listView.setAdapter(apdater);
    }

    private void getMockData(){
        data.add(new DownloadFile("myDoc.docx",1.2,1,30));
        data.add(new DownloadFile("myMusic.mp3",5.2,2,40));
        data.add(new DownloadFile("avatar.png",2.5,3,100));
    }

    private class DownloadTask extends AsyncTask<DownloadFile ,DownloadFile,DownloadFile>{


        @Override
        protected DownloadFile doInBackground(DownloadFile... downloadFiles) {
          DownloadFile downloadFile=downloadFiles[0];
          double downloadedSize=0;
          while(downloadedSize <downloadFile.getSize()){
              try {
                  Thread.sleep(1000);
                  downloadedSize +=0.1;
                  int currentProgress= (int)(100 * downloadedSize/downloadFile.getSize());
                  downloadFile.setProgress(currentProgress);

                  publishProgress(downloadFile);
              } catch (InterruptedException e) {
                  downloadFile.setStatus(2);
                  e.printStackTrace();
              }
          }
          downloadFile.setStatus(3);
          return downloadFile;
        }

        @Override
        protected void onProgressUpdate(DownloadFile... values) {
            apdater.notifyDataSetChanged();
        }
    }

}
