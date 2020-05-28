package com.eztech.search;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterRooms extends BaseAdapter {
    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    private final ArrayList<Rooms> listRooms;
    AdapterRooms(ArrayList<Rooms> listRooms) {
        this.listRooms = listRooms;
    }

    @Override
    public int getCount() {
        return listRooms.size();
    }

    @Override
    public Object getItem(int position) {
        //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
        //có chỉ số position trong listRooms
        return listRooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Trả về một ID của phần
        return listRooms.get(position).getId();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
        //Nếu null cần tạo mới
        View viewRooms;
        if (convertView == null) {
            viewRooms = View.inflate(parent.getContext(), R.layout.address_view, null);
        } else viewRooms = convertView;

        //Bind sữ liệu phần tử vào View
        Rooms rooms = (Rooms) getItem(position);
        ((TextView) viewRooms.findViewById(R.id.name)).setText(rooms.getName());
        ((TextView) viewRooms.findViewById(R.id.addess)).setText(rooms.getAddress());

        viewRooms.findViewById(R.id.imageView).setBackgroundResource(rooms.getImageResource());
        return viewRooms;
    }
}
