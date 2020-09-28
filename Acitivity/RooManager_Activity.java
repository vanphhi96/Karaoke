package com.example.vanph.karaokemanage.Acitivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.adapter.RoomAdapter;
import com.example.vanph.karaokemanage.database.DatabaseHandle;
import com.example.vanph.karaokemanage.model.Room;
import com.example.vanph.karaokemanage.model.RoomStyle;

import java.util.ArrayList;
import java.util.List;

import static com.example.vanph.karaokemanage.R.color.colorRoom;

public class RooManager_Activity extends AppCompatActivity {
    private FloatingActionButton btn_addroom;
    private List<RoomStyle> roomStyleList = new ArrayList<>();
    private List<Room> roomList = new ArrayList<>();
    private ListView lvRoom;
    private RoomAdapter roomAdapter;
    private EditText edit_roomdialog;
    private Spinner spin_styledialog;
    private Button btn_add;
    private Button btn_cancel;
    private static AlertDialog dialog;
    private TextView tv_rong;
    public static final String RoomKey = "rom_key";
    private RoomStyle roomStyleInten;
    private ImageView iv_back;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roo_manager_);
        roomStyleList = DatabaseHandle.getDatabaseHandle(this).getListRoomStyle();
        setupUI();
        LoadListView();
        addListenner();
        setTitle("Room Manage");
        setTitleColor(R.color.colorPrimary);


    }
    @Override
    protected void onStart() {
        super.onStart();
        LoadListView();

    }
    public void setupUI()
    {
        btn_addroom = (FloatingActionButton) findViewById(R.id.btn_floataddroom);
        lvRoom = (ListView) findViewById(R.id.lv_room);
        tv_rong = findViewById(R.id.tv_trong);
        iv_back = findViewById(R.id.iv_backroom);
    }
    public void LoadData()
    {

        roomStyleInten  = (RoomStyle) getIntent().getSerializableExtra(RoomStyleActivity.RoomStyleKey);
        if(roomStyleInten!=null)
        {
            roomList = new ArrayList<>();
            roomStyleList = new ArrayList<>();
            roomStyleList.add(roomStyleInten);
            roomList  = DatabaseHandle.getDatabaseHandle(this).getRoomListIDStyle(roomStyleInten.getId_roomstyle());
        }
        else{
            roomList  = DatabaseHandle.getDatabaseHandle(this).getListRoom();
            roomStyleList= DatabaseHandle.getDatabaseHandle(this).getListRoomStyle();
        }

    }
    public void setSpinner()
    {
        if(roomStyleList.size()==0)
        {
            Toast.makeText(getApplicationContext(), "Loại phòng trống, cần thêm loại phòng.", Toast.LENGTH_LONG).show();
            return;
        }
        String[] arrayspin = new String[roomStyleList.size()];
        for(int i=0; i<arrayspin.length; i++)
        {
            arrayspin[i]=roomStyleList.get(i).getName_roomstyle();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayspin);
        spin_styledialog.setAdapter(adapter);

    }
    public boolean getSpinner()
    {
       // roomStyleList = DatabaseHandle.getDatabaseHandle(this).getListRoomStyle();
        if(roomStyleList.size()==0)
        {
            Toast.makeText(getApplicationContext(), "Loại phòng trống, cần thêm loại phòng.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }

    public void LoadListView()
    {
        LoadData();
         roomAdapter = new RoomAdapter(this,R.layout.layout_itemroom,roomList,roomStyleList,this,tv_rong);
        lvRoom.setAdapter(roomAdapter);
        tv_rong.setText("");
        if(roomList.size()==0)
        {
            tv_rong.setText("Danh sách phòng trống");
        }
    }
    public boolean AddRoom()
    {
        String ten ="";
        ten =edit_roomdialog.getText().toString();
        int style = spin_styledialog.getSelectedItemPosition();
        if(ten.matches(""))
        {
            Toast.makeText(getApplicationContext(), "Tên không được để trống", Toast.LENGTH_LONG).show();
            return false;
        }
        if(DatabaseHandle.getDatabaseHandle(this).getRoomName(ten)!=null)
        {
            Toast.makeText(getApplicationContext(), "Tên đã có, không thể thêm", Toast.LENGTH_LONG).show();
            return false;
        }

        Room room = new Room(ten,roomStyleList.get(style),0);
        boolean ok =
                DatabaseHandle.getDatabaseHandle(RooManager_Activity.this).InsertRoom(room);
        return true;
    }
    public void addListenner()
    {
        btn_addroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSpinner())
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(RooManager_Activity.this);
                    View mview = getLayoutInflater().inflate(R.layout.layout_dialogroom,null);
                    edit_roomdialog=mview.findViewById(R.id.edit_roomnamedialog);
                    spin_styledialog=mview.findViewById(R.id.sprin_romstyledialog);
                    btn_add =mview.findViewById(R.id.btn_addroomdialog);
                    btn_cancel =mview.findViewById(R.id.btn_canceroomldialog);
                    setSpinner();
                    btn_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(AddRoom())
                                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_LONG).show();
                            LoadListView();
                            RooManager_Activity.dialog.dismiss();
                        }

                    });
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RooManager_Activity.dialog.dismiss();
                        }
                    });
                    builder.setView(mview);
                    dialog = builder.create();
                    dialog.show();

                }
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
