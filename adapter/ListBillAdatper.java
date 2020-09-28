package com.example.vanph.karaokemanage.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vanph.karaokemanage.Acitivity.ConvertDate;
import com.example.vanph.karaokemanage.Acitivity.ListBillActivity;
import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.database.DatabaseHandle;
import com.example.vanph.karaokemanage.model.Bill;
import com.example.vanph.karaokemanage.model.Oder;
import com.example.vanph.karaokemanage.model.Room;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by vanph on 04/11/2017.
 */

public class ListBillAdatper extends ArrayAdapter<Bill> {
    private Context context;
    private int resource;
    private List<Bill> billList;
    private String TAG = ListBillAdatper.class.toString();
    public ListBillAdatper(@NonNull Context context, int resource, @NonNull List<Bill> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.billList = objects;
        Log.d(TAG,"contrustor adper");
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);
        TextView ten = convertView.findViewById(R.id.tv_tenphonghoadon);
        TextView timestart = convertView.findViewById(R.id.tv_timebatdauhoadon);
        TextView sogio = convertView.findViewById(R.id.tv_sogiohathoaddon);
        TextView total = convertView.findViewById(R.id.tv_tongtienhoadon);
        ImageView im_delete = convertView.findViewById(R.id.iv_deletehoadon);
        ten.setText(billList.get(position).getRoom().getName_room());
        timestart.setText(billList.get(position).getTime_start());
        sogio.setText(sogiohat(billList.get(position)));
        Date timebatdau = ConvertDate.StringToDate(billList.get(position).getTime_start());
        Date timeketthuc = ConvertDate.StringToDate(billList.get(position).getTime_stop());
        float phutbd =(float)(timebatdau.getMinutes());
        float pgbd = phutbd/60;
        float giobt =(float)(timebatdau.getHours());
        giobt= giobt+pgbd;

        float phukt =(float)(timeketthuc.getMinutes());
        float pgkt = phukt/60;
        float giokt = (float)(timeketthuc.getHours());
        giokt = giokt+pgkt;
        float giosd = giokt-giobt;
        int giosd2 = (int)giosd;
        int phutsd=(int)((giosd-giosd2)*60);
        float tienh = giosd*billList.get(position).getRoom().getRoomStyle().getPrice_roomstyle();
        int tienhat =(int) (tienh);

        total.setText(tien(tongtien(billList.get(position).getId_bill())+tienhat)+"đ");
        final Oder oder = DatabaseHandle.getDatabaseHandle(context).getOder(billList.get(position).getId_bill());
        im_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder altdial = new AlertDialog.Builder(context,R.style.MyDialogTheme);
                altdial.setMessage("Do you want to delete?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(oder!=null)
                        {
                            boolean xoa = DatabaseHandle.getDatabaseHandle(context).DeleteBill(billList.get(position));
                            if(xoa)
                            {
                                boolean xoa2 = DatabaseHandle.getDatabaseHandle(context).DeleteOder(billList.get(position).getId_bill());
                                Toast.makeText(context.getApplicationContext(), "Đã xoá", Toast.LENGTH_LONG).show();
                                remove(billList.get(position));
                                notifyDataSetChanged();

                            }
                            else {
                                Toast.makeText(context.getApplicationContext(), "Không xoá được", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            boolean xoa = DatabaseHandle.getDatabaseHandle(context).DeleteBill(billList.get(position));
                            if(xoa)
                            {
                                Toast.makeText(context.getApplicationContext(), "Đã xoá", Toast.LENGTH_LONG).show();
                                remove(billList.get(position));
                                notifyDataSetChanged();

                            }
                        }
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).show();



            }
        });
        return  convertView;
    }
    public  String tien(int giatien)
    {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(giatien);
    }
    public int tongtien(int id)
    {
        Oder oder = DatabaseHandle.getDatabaseHandle(context).getOder(id);
        int tongtien =0;
        if(oder!=null)
        {
            for (int i=0; i<oder.getItemList().size(); i++)
            {
                tongtien = tongtien+oder.getItemList().get(i).getSoluong()*oder.getItemList().get(i).getItem().getPrice_item();
            }
        }
        return tongtien;
    }
    public String sogiohat(Bill bill)
    {
        Date timebatdau = ConvertDate.StringToDate(bill.getTime_start());
        Date timeketthuc = ConvertDate.StringToDate(bill.getTime_stop());
        float phutbd =(float)(timebatdau.getMinutes());
        float pgbd = phutbd/60;
        float giobt =(float)(timebatdau.getHours());
        giobt= giobt+pgbd;

        float phukt =(float)(timeketthuc.getMinutes());
        float pgkt = phukt/60;
        float giokt = (float)(timeketthuc.getHours());
        giokt = giokt+pgkt;
        float giosd = giokt-giobt;
        int giosd2 = (int)giosd;
        int phutsd=(int)((giosd-giosd2)*60);

        return giosd2+"giờ "+phutsd+"phút";
    }

}
