package com.example.vanph.karaokemanage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vanph.karaokemanage.Acitivity.ItemActivity;
import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.database.DatabaseHandle;
import com.example.vanph.karaokemanage.model.ItemSelect;
import com.example.vanph.karaokemanage.model.Oder;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by vanph on 02/11/2017.
 */

public class OderAdapter  extends ArrayAdapter<ItemSelect> {

    private Context context;
    private int resource;
    private List<ItemSelect> itemSelectList;
    private ImageView im_edit;
    private ImageView im_delete;
    private Oder oder;
    private Activity activity;
    private EditText edit;
    private Button btn_edit;
    private Button btn_cancel;
    private static AlertDialog dialog;
    private TextView tv_trangthai;
    private TextView tv_total;
    public OderAdapter(@NonNull Context context, int resource, @NonNull List<ItemSelect> objects, Oder oder, Activity activity,TextView tv_trangthai,TextView tv_total) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.itemSelectList = objects;
        this.oder = oder;
        this.activity = activity;
        this.tv_trangthai = tv_trangthai;
        this.tv_total = tv_total;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);
        TextView tv_ten = convertView.findViewById(R.id.tv_odernameitem);
        TextView tv_tien_sl = convertView.findViewById(R.id.tv_itemgiatienoder);
        TextView thanhtien  = convertView.findViewById(R.id.tv_itemtotaloder);
         im_edit = convertView.findViewById(R.id.im_edititemoder);
         im_delete = convertView.findViewById(R.id.im_deleteitemoder);

       // setClick(itemList.get(position));
        tv_ten.setText(itemSelectList.get(position).getItem().getName());
        tv_tien_sl.setText(tien(itemSelectList.get(position).getItem().getPrice_item())+"đ x "+itemSelectList.get(position).getSoluong());
        thanhtien.setText(tien(itemSelectList.get(position).getItem().getPrice_item()*itemSelectList.get(position).getSoluong())+"đ");
        setOnClick(itemSelectList.get(position));
        return convertView;
    }
    public  String tien(int giatien)
    {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(giatien);
    }
    public boolean EditSL(ItemSelect itemSelect)
    {
        int sl = Integer.parseInt(edit.getText().toString());
        itemSelect.setSoluong(sl);
        if(sl==0)
        {
            if(DatabaseHandle.getDatabaseHandle(context).DeleteItemOder(itemSelect, oder.getId_bill()))
                return true;
        }
        else{
            if (DatabaseHandle.getDatabaseHandle(context).UpdateItemOder(itemSelect, oder.getId_bill()))
                return true;
        }
        return false;
    }
    public void UpdateTotal()
    {
        Oder oder22 = DatabaseHandle.getDatabaseHandle(context).getOder(oder.getId_bill());
        if(oder22!=null)
        {
            int tongtien =0;
            for (int i=0; i<oder22.getItemList().size(); i++)
            {
                tongtien = tongtien+oder22.getItemList().get(i).getItem().getPrice_item()*oder22.getItemList().get(i).getSoluong();
            }
            tv_total.setText(tien(tongtien)+"đ");
        }
    }
    public void setOnClick(final ItemSelect itemSelect)
    {
        im_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                View mview = activity.getLayoutInflater().inflate(R.layout.layout_dialogeditoder,null);
                edit=mview.findViewById(R.id.edit_soluongoder);
                btn_edit=mview.findViewById(R.id.btn_edititemoder);
                btn_cancel = mview.findViewById(R.id.btn_cacelitemoder);

                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(EditSL(itemSelect))
                            Toast.makeText(context.getApplicationContext(), "Sửa thành công", Toast.LENGTH_LONG).show();
                        OderAdapter.dialog.dismiss();
                        notifyDataSetChanged();
                        UpdateTotal();
                    }

                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OderAdapter.dialog.dismiss();
                    }
                });
                builder.setView(mview);
                dialog = builder.create();
                dialog.show();
            }
        });

        im_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder altdial = new AlertDialog.Builder(context,R.style.MyDialogTheme);
                altdial.setMessage("Do you want to delete?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean ok =  DatabaseHandle.getDatabaseHandle(context).DeleteItemOder(itemSelect,oder.getId_bill());
                        if(ok)
                        {
                            Toast.makeText(context.getApplicationContext(), "Đã xoá.", Toast.LENGTH_LONG).show();
                            remove(itemSelect);
                            notifyDataSetChanged();
                            Oder tmp = DatabaseHandle.getDatabaseHandle(context).getOder(oder.getId_bill());
                            if(tmp==null)
                            {
                                tv_trangthai.setText("Danh sách đồ trống");
                            }
                            UpdateTotal();

                        }
                        else{
                            Toast.makeText(context.getApplicationContext(), "Không thành công.", Toast.LENGTH_LONG).show();
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

    }
}
