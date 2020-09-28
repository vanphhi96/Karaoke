package com.example.vanph.karaokemanage.Acitivity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.adapter.GroupItemAdapter;
import com.example.vanph.karaokemanage.database.DatabaseHandle;
import com.example.vanph.karaokemanage.model.Group_Item;

import java.util.ArrayList;
import java.util.List;

public class GroupItemActivity extends AppCompatActivity {

    private List<Group_Item> group_itemList = new ArrayList<>();
    private ListView lv_group;
    private FloatingActionButton btn_add;
    private TextView tv_trangthai;
    private GroupItemAdapter groupItemAdapter;
    private EditText editText;
    private static AlertDialog dialog;
    private ImageView iv_back;
    public static final String GroupItemKey = "groupitem_key";
    private  View viewActionbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_item);
        setupUI();
        group_itemList = DatabaseHandle.getDatabaseHandle(this).getGroup_items();
        LoadListView();
        addListenner();
        viewActionbar.setBackgroundColor(ContextCompat.getColor(this,R.color.colorItemGroup));
    }
    public void setupUI()
    {
        lv_group = findViewById(R.id.lv_itemgroup);
        btn_add = findViewById(R.id.btn_floataddgroup);
        tv_trangthai = findViewById(R.id.tv_trangthaigroup);
        iv_back = findViewById(R.id.im_backgroup);
        viewActionbar = findViewById(R.id.view);

    }

    public void LoadListView()
    {
        group_itemList  = DatabaseHandle.getDatabaseHandle(this).getGroup_items();
        groupItemAdapter = new GroupItemAdapter(this,R.layout.layout_itemgroup,group_itemList,this,tv_trangthai);
        lv_group.setAdapter(groupItemAdapter);
        tv_trangthai.setText("");
        if(group_itemList.size()==0)
        {
            tv_trangthai.setText("Danh sách phòng trống");
        }
    }
    public boolean AddGroup()
    {
        String ten ="";
        ten =editText.getText().toString();
        if(ten.matches(""))
        {
            Toast.makeText(getApplicationContext(), "Tên không được để trống", Toast.LENGTH_LONG).show();
            return false;
        }
        if(DatabaseHandle.getDatabaseHandle(this).getGroup_itemName(ten)!=null)
        {
            Toast.makeText(getApplicationContext(), "Tên đã có, không thể thêm", Toast.LENGTH_LONG).show();
            return false;
        }

        Group_Item group_item = new Group_Item(ten);
        boolean ok =
                DatabaseHandle.getDatabaseHandle(this).InsertGroupItem(group_item);
        return true;
    }
    public void addListenner()
    {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(GroupItemActivity.this);
                View mview = getLayoutInflater().inflate(R.layout.layout_dialoggroupitem,null);
                editText=mview.findViewById(R.id.edit_groupitemname);
                Button btn_addgroup =mview.findViewById(R.id.btn_addgroupdialog);
               Button btn_cancel =mview.findViewById(R.id.btn_cancelgroupdialog);
                btn_addgroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(AddGroup())
                            Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_LONG).show();
                        LoadListView();
                        GroupItemActivity.dialog.dismiss();
                    }

                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GroupItemActivity.dialog.dismiss();
                    }
                });
                builder.setView(mview);
                dialog = builder.create();
                dialog.show();
            }
        });
        lv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GroupItemActivity.this, ItemActivity.class);
                intent.putExtra(GroupItemKey,group_itemList.get(position));
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
