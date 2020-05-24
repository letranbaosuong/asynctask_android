package com.example.asynctask_android;

import android.widget.Switch;

public class DownloadFile {

    private String filename;
    private double size;
    private int status;
    private int progress;
    DownloadFile(String filename, double size, int status, int progress){
        this.filename=filename;
        this.size=size;
        this.status=status;
        this.progress=progress;
    }

    String getFilename() {return filename; }

    public void setFilename(String filename) {this.filename = filename;}

    double getSize() { return size; }

    public void setSize(double size) { this.size = size; }

    int getStatus() {  return status;}

    void setStatus(int status) { this.status = status;}

    int getProgress() {  return progress;}

    void setProgress(int progress) { this.progress = progress;}

    int getImageResourceId(){
        int resId= R.drawable.icon_other;
        String ext=filename.substring(filename.lastIndexOf(".")+1);
        switch(ext){
            case "docx":{
                resId=R.drawable.icon_docs;
                break;
            }
            case "mp3":{
                resId=R.drawable.icon_mp3;
                break;
            }
            case "pdf":{
                resId=R.drawable.icon_pdf;
                break;
            }
            case "png":{
                resId=R.drawable.icon_png;
                break;
            }
            case "txt":{
                resId=R.drawable.icon_txt;
                break;
            }
            case "zip":{
                resId=R.drawable.icon_zip;
                break;
            }
        }
        return resId;
    }
}
