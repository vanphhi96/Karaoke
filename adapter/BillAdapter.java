package com.example.vanph.karaokemanage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.model.ItemSelect;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by vanph on 03/11/2017.
 */

public class BillAdapter extends ArrayAdapter<ItemSelect> {
    private Context context;
    private int resource;
    private List<ItemSelect> itemSelectList;
    public BillAdapter(@NonNull Context context, int resource, @NonNull List<ItemSelect> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.itemSelectList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);
        TextView ten = convertView.findViewById(R.id.tv_billnameitem);
        TextView gia = convertView.findViewById(R.id.tv_itemgiatienbill);
        TextView soluong = convertView.findViewById(R.id.tv_soluongbill);
        TextView thanhtien = convertView.findViewById(R.id.tv_thanhtienbill);
        ten.setText(itemSelectList.get(position).getItem().getName());
        gia.setText("Giá "+tien(itemSelectList.get(position).getItem().getPrice_item())+"đ");
        soluong.setText(Integer.toString(itemSelectList.get(position).getSoluong()));
        thanhtien.setText(tien(itemSelectList.get(position).getItem().getPrice_item()*itemSelectList.get(position).getSoluong())+"đ");
        return  convertView;
    }

    public  String tien(int giatien)
    {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(giatien);
    }
}
