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

import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.database.DatabaseHandle;
import com.example.vanph.karaokemanage.model.RoomStyle;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by vanph on 27/10/2017.
 */

public class RoomStyleAdapter  extends ArrayAdapter<RoomStyle>{
    private Context context;
    private int resource;
    private List<RoomStyle> roomStyleList;
    private ImageView delete;
    private  ImageView edite;
    public static final String RoomStyleKey = "romstyle_key";
    private Activity activity;
    private EditText edit_styledialog;
    private EditText edit_giatiendialog;
    private static AlertDialog dialog;
    private TextView tv_trangthai;
    public RoomStyleAdapter(@NonNull Context context, int resource, @NonNull List<RoomStyle> objects, Activity activity, TextView tv_trangthai) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.roomStyleList = objects;
        this.activity= activity;
        this.tv_trangthai = tv_trangthai;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);
        TextView tvTitle = convertView.findViewById(R.id.tv_roomstylename);
        TextView tv_giatien = convertView.findViewById(R.id.tv_giatien);
        ImageView im_edit = convertView.findViewById(R.id.im_editstyle);
        ImageView im_delete = convertView.findViewById(R.id.iv_deletylegroup);
        this.delete = im_delete;
        this.edite = im_edit;
        setClick(roomStyleList.get(position));
        tvTitle.setText(roomStyleList.get(position).getName_roomstyle());
        tv_giatien.setText(tien(roomStyleList.get(position).getPrice_roomstyle())+"đ/giờ");
        return convertView;
    }
    public  String tien(int giatien)
    {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(giatien);
    }
    public boolean Update(RoomStyle roomStyle)
    {
        String ten ="";
        ten =edit_styledialog.getText().toString();
        int giatien = 0;
        giatien=Integer.parseInt(edit_giatiendialog.getText().toString());
        if(ten.matches("")||giatien==0)
        {
            Toast.makeText(context.getApplicationContext(), "Không được để trống", Toast.LENGTH_LONG).show();
            return false;
        }
        if((DatabaseHandle.getDatabaseHandle(context).getRoomStyleName(ten)==null||
                DatabaseHandle.getDatabaseHandle(context).getRoomStyleName(ten).getId_roomstyle()==roomStyle.getId_roomstyle())
                &&DatabaseHandle.getDatabaseHandle(context).getRoomStylePrice(giatien)==null)
        {
            roomStyle.setName_roomstyle(ten);
            roomStyle.setPrice_roomstyle(giatien);
            DatabaseHandle.getDatabaseHandle(context).UpdateRoomStyle(roomStyle);
            return true;
        }
        else{
            Toast.makeText(context.getApplicationContext(), "Giá tiền đã có", Toast.LENGTH_LONG).show();
            return false;
        }

    }
    public void setClick( final RoomStyle roomStyle)
    {

        edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View mview = activity.getLayoutInflater().inflate(R.layout.layout_dialogromstyle,null);
                 edit_giatiendialog=mview.findViewById(R.id.edit_giaphongdialog);
                 edit_styledialog=mview.findViewById(R.id.edit_roomstylenamedialog);
                Button btn_add =mview.findViewById(R.id.btn_addstyledialog);
                Button btn_cancel =mview.findViewById(R.id.btn_canceldialogstyle);
                btn_add.setText("Cập nhật");
                edit_styledialog.setText(roomStyle.getName_roomstyle());
                edit_giatiendialog.setText(Integer.toString(roomStyle.getPrice_roomstyle()));
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    if(Update(roomStyle))
                        Toast.makeText(context.getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
                        RoomStyleAdapter.dialog.dismiss();
                        notifyDataSetChanged();
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RoomStyleAdapter.dialog.dismiss();
                    }
                });
                builder.setView(mview);
                dialog = builder.create();
                dialog.show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClickDelete(roomStyle);

            }
        });
    }
    public void setClickDelete(final RoomStyle roomStyle)
    {

        if(roomStyle!=null)
        {
            AlertDialog.Builder altdial = new AlertDialog.Builder(context,R.style.MyDialogTheme);
            altdial.setMessage("Do you want to delete?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if(DatabaseHandle.getDatabaseHandle(context).getRoomIDStyle(roomStyle.getId_roomstyle())==null)
                    {
                        boolean ok =  DatabaseHandle.getDatabaseHandle(context).DeleteRoomStyle(roomStyle.getId_roomstyle());
                        if(ok)
                        {
                            Toast.makeText(context.getApplicationContext(), "Đã xoá.", Toast.LENGTH_LONG).show();
                            remove(roomStyle);
                            notifyDataSetChanged();
                            List<RoomStyle> roomStyles = DatabaseHandle.getDatabaseHandle(context).getListRoomStyle();
                            if(roomStyles.size()==0)
                            {
                                tv_trangthai.setText("Danh sách loại phòng trống");
                            }
                        }
                        else{
                            Toast.makeText(context.getApplicationContext(), "Không thành công.", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(context.getApplicationContext(), "Không thể xoá, có phòng đang sử dụng.", Toast.LENGTH_LONG).show();
                    }


                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            }).show();

        }


    }
}
