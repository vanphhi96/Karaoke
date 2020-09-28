package com.example.vanph.karaokemanage.Acitivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.adapter.RoomStyleAdapter;
import com.example.vanph.karaokemanage.database.DatabaseHandle;
import com.example.vanph.karaokemanage.model.RoomStyle;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RoomStyleActivity extends AppCompatActivity {

    private ListView lvStyle;
    private static final String TAG = RoomStyleActivity.class.toString();
    private List<RoomStyle> roomStyleList = new ArrayList<>();
    private FloatingActionButton addNew;
    private EditText edit_styledialog;
    private EditText edit_giatiendialog;
    private Button btn_add;
    private Button btn_cancel;
    private static AlertDialog dialog;
    private TextView tv_trangthai;
    private  ImageView iv_back;
    public static final String RoomStyleKey = "romstyle_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_style);
        setupUI();
        LoadListView();
        addListtenners();
        setTitle("Room Style Manage");
    }

    public  String tien(int giatien)
    {DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(giatien);
    }
    public boolean AddRoomStyle()
    {
        String ten ="";
        ten =edit_styledialog.getText().toString();
        int giatien = 0;
        giatien=Integer.parseInt(edit_giatiendialog.getText().toString());
        if(ten.matches("")||giatien==0)
        {
            Toast.makeText(getApplicationContext(), "Không được để trống", Toast.LENGTH_LONG).show();
            return false;
        }
        if(DatabaseHandle.getDatabaseHandle(this).getRoomStyleName(ten)!=null)
        {
            Toast.makeText(getApplicationContext(), "Tên đã có, không thể thêm", Toast.LENGTH_LONG).show();
            return false;
        }
        if(DatabaseHandle.getDatabaseHandle(this).getRoomStylePrice(giatien)!=null)
        {
            Toast.makeText(getApplicationContext(), "Đã có loại phòng có giá "+tien(giatien), Toast.LENGTH_LONG).show();
            return false;
        }
        RoomStyle abc = new RoomStyle(ten,giatien);
        DatabaseHandle.getDatabaseHandle(this).InsertRoomStyle(abc);
        return true;
    }

    public void setupUI()
    {
        lvStyle = (ListView) findViewById(R.id.lv_roomstyle);
        addNew = (FloatingActionButton) findViewById(R.id.btn_floataddstyleroom);
        tv_trangthai = findViewById(R.id.tv_trongstyle);
        iv_back = findViewById(R.id.iv_backstyle);

    }

    @Override
    protected void onStart() {
        super.onStart();
        LoadListView();

    }

    public void LoadListView()
    {

            roomStyleList = DatabaseHandle.getDatabaseHandle(this).getListRoomStyle();
            RoomStyleAdapter roomStyleAdapter = new RoomStyleAdapter(this,R.layout.layout_itemroomstyle,roomStyleList,this,tv_trangthai);
            lvStyle.setAdapter(roomStyleAdapter);
            roomStyleAdapter.notifyDataSetChanged();
    }
    public void addListtenners()
    {
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(RoomStyleActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.layout_dialogromstyle,null);
                edit_giatiendialog=mview.findViewById(R.id.edit_giaphongdialog);
                edit_styledialog=mview.findViewById(R.id.edit_roomstylenamedialog);
                btn_add =mview.findViewById(R.id.btn_addstyledialog);
                btn_cancel =mview.findViewById(R.id.btn_canceldialogstyle);

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(AddRoomStyle())
                            Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_LONG).show();
                        LoadListView();
                        RoomStyleActivity.dialog.dismiss();
                    }

                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RoomStyleActivity.dialog.dismiss();
                    }
                });
                builder.setView(mview);
                dialog = builder.create();
                dialog.show();
            }
        });
        lvStyle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RoomStyleActivity.this, RooManager_Activity.class);
                intent.putExtra(RoomStyleKey,roomStyleList.get(position));
                startActivity(intent);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
