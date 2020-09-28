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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.database.DatabaseHandle;
import com.example.vanph.karaokemanage.model.Room;
import com.example.vanph.karaokemanage.model.RoomStyle;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by vanph on 25/10/2017.
 */

public class RoomAdapter extends ArrayAdapter<Room> {
    private Context context;
    private int resource;
    private List<Room> roomList;
    private ImageView delete;
    private  ImageView edite;
    public static final String RoomKey = "rom_key";
    private EditText edit_roomnamedialog;
    private Spinner spinner_roomstyle;
    private List<RoomStyle> roomStyleList;
    private Activity activity;
    private static AlertDialog dialog;
    private TextView tv_trangthai;
    public RoomAdapter(@NonNull Context context, int resource, @NonNull List<Room> objects, List<RoomStyle> roomStyleList,
                       Activity activity, TextView tv_trangthai) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.roomList = objects;
        this.roomStyleList = roomStyleList;
        this.activity = activity;
        this.tv_trangthai = tv_trangthai;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);
        TextView tvTitle = convertView.findViewById(R.id.tv_roomname);
        TextView tv_loaiphong = convertView.findViewById(R.id.tv_stylename);
        TextView tv_giatien = convertView.findViewById(R.id.tv_price);
        ImageView im_edit = convertView.findViewById(R.id.im_editroom);
        ImageView im_delete = convertView.findViewById(R.id.im_deleteroom);
        this.delete = im_delete;
        this.edite = im_edit;
        setClick(roomList.get(position));
        tvTitle.setText(roomList.get(position).getName_room());
        tv_loaiphong.setText(roomList.get(position).getRoomStyle().getName_roomstyle());
        tv_giatien.setText(tien(roomList.get(position).getRoomStyle().getPrice_roomstyle())+"đ/giờ");
        return convertView;

    }
    public  String tien(int giatien)
    {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(giatien);
    }
    public boolean Update(Room room)
    {
        String ten ="";
        ten =edit_roomnamedialog.getText().toString();
        int style = spinner_roomstyle.getSelectedItemPosition();

        if(ten.matches(""))
        {
            Toast.makeText(context.getApplicationContext(), "Không được để trống", Toast.LENGTH_LONG).show();
            return false;
        }
        if((DatabaseHandle.getDatabaseHandle(context).getRoomName(ten)==null||
                DatabaseHandle.getDatabaseHandle(context).getRoomName(ten).getId()==room.getId()))
        {
            room.setName_room(ten);
            room.setRoomStyle(roomStyleList.get(style));
            DatabaseHandle.getDatabaseHandle(context).UpdateRoom(room);
            return true;
        }
        else{
            Toast.makeText(context.getApplicationContext(), "Tên phòng đã có", Toast.LENGTH_LONG).show();
            return false;
        }

    }
    public int getPositionRoomStyle(RoomStyle roomStyle)
    {
        for(int i=0; i<roomStyleList.size(); i++)
        {
            if(roomStyle.getId_roomstyle()==roomStyleList.get(i).getId_roomstyle())
            {
                return i;
            }
        }
        return 0;
    }
    public void setSpinner()
    {
        if(roomStyleList.size()==0)
        {
            Toast.makeText(context.getApplicationContext(), "Loại phòng trống, cần thêm loại phòng.", Toast.LENGTH_LONG).show();
            return;
        }
        String[] arrayspin = new String[roomStyleList.size()];
        for(int i=0; i<arrayspin.length; i++)
        {
            arrayspin[i]=roomStyleList.get(i).getName_roomstyle();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, arrayspin);
        spinner_roomstyle.setAdapter(adapter);

    }
    public void setClick(final Room room)
    {
        edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View mview = activity.getLayoutInflater().inflate(R.layout.layout_dialogroom,null);
                edit_roomnamedialog=mview.findViewById(R.id.edit_roomnamedialog);
                spinner_roomstyle=mview.findViewById(R.id.sprin_romstyledialog);
                Button btn_add =mview.findViewById(R.id.btn_addroomdialog);
                Button btn_cancel =mview.findViewById(R.id.btn_canceroomldialog);
                btn_add.setText("Cập nhật");
                edit_roomnamedialog.setText(room.getName_room());
                setSpinner();
                spinner_roomstyle.setSelection(getPositionRoomStyle(room.getRoomStyle()));
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Update(room))
                        {
                            Toast.makeText(context.getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
                            RoomAdapter.dialog.dismiss();
                            notifyDataSetChanged();
                        }

                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RoomAdapter.dialog.dismiss();
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
                setClickDelete(room);

            }
        });
    }
    public void setClickDelete(final Room room)
    {
        if(room!=null)
        {
            AlertDialog.Builder altdial = new AlertDialog.Builder(context,R.style.MyDialogTheme);
            altdial.setMessage("Do you want to delete?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    boolean ok =  DatabaseHandle.getDatabaseHandle(context).DeleteRoom(room.getId());
                    if(ok)
                    {
                        Toast.makeText(context.getApplicationContext(), "Đã xoá.", Toast.LENGTH_LONG).show();
                        remove(room);
                        notifyDataSetChanged();
                        List<Room> rooms = DatabaseHandle.getDatabaseHandle(context).getListRoom();
                        if(rooms.size()==0)
                        {
                            tv_trangthai.setText("Danh sách phòng trống");
                        }

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


    }
}
