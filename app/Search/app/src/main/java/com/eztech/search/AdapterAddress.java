package com.eztech.search;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterAddress extends BaseAdapter {
    //Dữ liệu liên kết bởi Adapter là một mảng các sản phẩm
    private final ArrayList<Address> listAddress;

    AdapterAddress(ArrayList<Address> listAddress) {
        this.listAddress = listAddress;
    }

    @Override
    public int getCount() {
        return listAddress.size();
    }

    @Override
    public Object getItem(int position) {
        //Trả về dữ liệu ở vị trí position của Adapter, tương ứng là phần tử
        //có chỉ số position trong listProduct
        return listAddress.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Trả về một ID của phần
        return listAddress.get(position).getId();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //convertView là View của phần tử ListView, nếu convertView != null nghĩa là
        //View này được sử dụng lại, chỉ việc cập nhật nội dung mới
        //Nếu null cần tạo mới
        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.address_view, null);
        } else viewProduct = convertView;

        //Bind sữ liệu phần tử vào View
        Address address = (Address) getItem(position);
        ((TextView) viewProduct.findViewById(R.id.name)).setText(address.getName());
        ((TextView) viewProduct.findViewById(R.id.addess)).setText("Địa chỉ: " + address.getAddress());
        return viewProduct;
    }
}
