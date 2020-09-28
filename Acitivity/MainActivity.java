package com.example.vanph.karaokemanage.Acitivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.adapter.GridViewAdapter;
import com.example.vanph.karaokemanage.database.DatabaseHandle;
import com.example.vanph.karaokemanage.model.Room;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   private GridViewAdapter gridViewAdapter;
    private List<Room> roomList;
    private GridView gridView;
    public static final String MainKey = "groupitem_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.layout_actionbar_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this,R.color.colorMain)));

        setupUI();
        LoadView();
        addListenner();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_roomstyle:
                Intent intent = new Intent(this, RoomStyleActivity.class);
                startActivity(intent);
                break;
            case R.id.action_room:
                Intent intent2 = new Intent(this, RooManager_Activity.class);
                startActivity(intent2);
                break;
            case R.id.action_groupitem:
                Intent intent3 = new Intent(this, GroupItemActivity.class);
                startActivity(intent3);
                break;
            case R.id.action_item:
                Intent intent4 = new Intent(this, ItemActivity.class);
                startActivity(intent4);
                break;
            case R.id.action_hoadon:
                Intent intent5 = new Intent(this, ListBillActivity.class);
                startActivity(intent5);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        LoadView();
        super.onStart();
    }

    public void setupUI()
    {
        gridView = findViewById(R.id.gridView);
    }
    public void LoadView()
    {
        roomList = DatabaseHandle.getDatabaseHandle(this).getListRoom();
        gridViewAdapter = new GridViewAdapter(roomList,this);
        gridView.setAdapter(gridViewAdapter);
    }
    public void addListenner(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
