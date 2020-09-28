package com.example.vanph.karaokemanage.Acitivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.adapter.SelectAdatper;
import com.example.vanph.karaokemanage.database.DatabaseHandle;
import com.example.vanph.karaokemanage.model.Bill;
import com.example.vanph.karaokemanage.model.Item;
import com.example.vanph.karaokemanage.model.ItemSelect;
import com.example.vanph.karaokemanage.model.Oder;

import java.util.ArrayList;
import java.util.List;

public class SelectItemActivity extends AppCompatActivity {
    private ImageView iv_back;
    private ImageView iv_done;
    private ListView lv_item;
    private SearchView searchView;
    public  List<Item> itemList = new ArrayList<>();
    private SelectAdatper selectAdatper;
    private Bill billIten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_item);
        itemList = DatabaseHandle.getDatabaseHandle(this).getItems();
        setuiUI();
        LoadData();
        LoadView();
        addListenner();
    }
    public void LoadData()
    {
        billIten = (Bill)getIntent().getSerializableExtra(OderActivity.OderKey);
    }

    private void addListenner() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectAdatper.itemSelectList = new ArrayList<>();
                finish();
            }
        });
        iv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Oder oder = new Oder();
                oder.setId_bill(billIten.getId_bill());
                oder.setItemList(SelectAdatper.itemSelectList);
                Oder oder1Old =  DatabaseHandle.getDatabaseHandle(SelectItemActivity.this).getOder(billIten.getId_bill());
                if(oder1Old!=null)
                {
                    for (int i=0; i<oder1Old.getItemList().size(); i++)
                    {
                        for (int j=0; j<SelectAdatper.itemSelectList.size(); j++)
                        {
                            if(oder1Old.getItemList().get(i).getItem().getId_item()==SelectAdatper.itemSelectList.get(j).getItem().getId_item())
                            {
                                int sl = oder1Old.getItemList().get(i).getSoluong()+SelectAdatper.itemSelectList.get(j).getSoluong();
                                DatabaseHandle.getDatabaseHandle(SelectItemActivity.this).UpdateItemOder(new ItemSelect(sl,SelectAdatper.itemSelectList.get(j).getItem()),billIten.getId_bill());
                                SelectAdatper.itemSelectList.remove(j);
                            }
                        }
                    }
                }

                DatabaseHandle.getDatabaseHandle(SelectItemActivity.this).InsetListOder(oder);
                SelectAdatper.itemSelectList = new ArrayList<>();
                finish();

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                itemList = DatabaseHandle.getDatabaseHandle(SelectItemActivity.this).SearchItem(query);
                LoadView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                itemList = DatabaseHandle.getDatabaseHandle(SelectItemActivity.this).SearchItem(newText);
                LoadView();
                return false;
            }
        });

    }
    public void setuiUI()
    {
        iv_back = findViewById(R.id.iv_backselect);
        iv_done= findViewById(R.id.iv_doneselect);
        lv_item = findViewById(R.id.lv_itemselect);
        searchView = findViewById(R.id.sv_searchitem);
    }
    public void LoadView()
    {
        selectAdatper = new SelectAdatper(this,R.layout.layout_item_select,itemList);
        lv_item.setAdapter(selectAdatper);
    }

}
