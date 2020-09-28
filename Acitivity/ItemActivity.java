package com.example.vanph.karaokemanage.Acitivity;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.adapter.ItemAdapter;
import com.example.vanph.karaokemanage.database.DatabaseHandle;
import com.example.vanph.karaokemanage.model.Group_Item;
import com.example.vanph.karaokemanage.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemActivity extends AppCompatActivity {
    private List<Group_Item> group_itemList;
    private TextView tv_trangthai;
    private ListView lv_item;
    private FloatingActionButton btn_additem;
    private List<Item> itemList;
    private Spinner spin_group;
    private EditText edit_itemname;
    private EditText edit_priceitem;
    private Button btn_add;
    private Button btn_cancel;
    private static AlertDialog dialog;
    private ItemAdapter itemAdapter;
    private Group_Item group_itemInten;
    private ImageView iv_back;
    private CheckBox checkBox;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        group_itemList = DatabaseHandle.getDatabaseHandle(this).getGroup_items();
        setupUI();
        LoadListView();
        addListenner();
        setTitle("Item Manage");
        setTitleColor(R.color.colorPrimary);

    }
    public void setupUI()
    {
        tv_trangthai = findViewById(R.id.tv_trangthaiitem);
        lv_item = findViewById(R.id.lv_itemroomoder);
        btn_additem = findViewById(R.id.btn_floatadditem);
        iv_back = findViewById(R.id.iv_backitem);
    }
    public void LoadData()
    {

        group_itemInten = (Group_Item) getIntent().getSerializableExtra(GroupItemActivity.GroupItemKey);
        if(group_itemInten!=null)
        {
            group_itemList = new ArrayList<>();
            group_itemList.add(group_itemInten);
            itemList  = DatabaseHandle.getDatabaseHandle(this).getItemListIDGroup(group_itemInten.getId_groupItem());
        }
        else{
            group_itemList = DatabaseHandle.getDatabaseHandle(this).getGroup_items();
            itemList= DatabaseHandle.getDatabaseHandle(this).getItems();
        }

    }
    public void LoadListView()
    {
        LoadData();
        itemAdapter = new ItemAdapter(this,R.layout.layout_itemthucdon,itemList,group_itemList,this,tv_trangthai);
        lv_item.setAdapter(itemAdapter);
        tv_trangthai.setText("");
        if(itemList.size()==0)
        {
            tv_trangthai.setText("Danh sách phòng trống");
        }
        itemAdapter.notifyDataSetChanged();
    }
    public boolean AddItem()
    {
        String ten ="";
        ten =edit_itemname.getText().toString();
        int giatien = 0;
        giatien = Integer.parseInt(edit_priceitem.getText().toString());
        int group = spin_group.getSelectedItemPosition();
        if(ten.matches(""))
        {
            Toast.makeText(getApplicationContext(), "Tên không được để trống", Toast.LENGTH_LONG).show();
            return false;
        }
        if(giatien<=0)
        {
            Toast.makeText(getApplicationContext(), "Giá tiền phải lớn lớn hơn 0", Toast.LENGTH_LONG).show();
            return false;
        }
        if(DatabaseHandle.getDatabaseHandle(this).getItemName(ten)!=null)
        {
            Toast.makeText(getApplicationContext(), "Tên đã có, không thể thêm", Toast.LENGTH_LONG).show();
            return false;
        }

        Item item = new Item(ten,giatien,group_itemList.get(group));
        boolean ok =
                DatabaseHandle.getDatabaseHandle(this).InsertItem(item);
        return true;
    }
    public boolean getSpinner()
    {

        if(group_itemList.size()==0)
        {
            Toast.makeText(getApplicationContext(), "Nhóm đồ trống, cần thêm nhóm đồ.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }
    public void setSpinner()
    {
        if(group_itemList.size()==0)
        {
            Toast.makeText(getApplicationContext(), "Nhóm đồ trống, cần thêm nhóm đồ.", Toast.LENGTH_LONG).show();
            return;
        }
        String[] arrayspin = new String[group_itemList.size()];
        for(int i=0; i<arrayspin.length; i++)
        {
            arrayspin[i]=group_itemList.get(i).getName_group();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayspin);
        spin_group.setAdapter(adapter);

    }
    public void addListenner()
    {
        btn_additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getSpinner())
                {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ItemActivity.this);
                    View mview = getLayoutInflater().inflate(R.layout.layout_dialogitem,null);
                    edit_itemname=mview.findViewById(R.id.edit_itemnamedialog);
                    edit_priceitem=mview.findViewById(R.id.edit_priceitem);
                    spin_group = mview.findViewById(R.id.sprin_groupdialog);
                    btn_add =mview.findViewById(R.id.btn_additemdialog);
                    btn_cancel =mview.findViewById(R.id.btn_cancelitemldialog);
                    setSpinner();
                    btn_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(AddItem())
                                Toast.makeText(getApplicationContext(), "Thêm thành công", Toast.LENGTH_LONG).show();
                            LoadListView();
                            ItemActivity.dialog.dismiss();
                        }

                    });
                    btn_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ItemActivity.dialog.dismiss();
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
