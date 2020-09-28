package com.example.vanph.karaokemanage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vanph.karaokemanage.Acitivity.SelectItemActivity;
import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.model.Item;
import com.example.vanph.karaokemanage.model.ItemSelect;
import com.example.vanph.karaokemanage.model.Oder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLDisplay;

/**
 * Created by vanph on 31/10/2017.
 */

public class SelectAdatper extends ArrayAdapter<Item> {
    private static final String TAG = "SelectAdatper";
    private Context context;
    private int resource;
    private List<Item> itemList;
    public static List<ItemSelect> itemSelectList= new ArrayList<>();
    private Activity activity;
    private List<Item> itemListTrangThai = new ArrayList<>();
    public SelectAdatper(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.itemList = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.e(TAG, "getView: ");
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);
        TextView  tv_itemname = convertView.findViewById(R.id.tv_itemselect);
        TextView tv_groupitem = convertView.findViewById(R.id.tv_groupselect);
        TextView  tv_priceitem = convertView.findViewById(R.id.tv_priceselect);
        final EditText edit_soluong = convertView.findViewById(R.id.edit_soluongselect);
        final CheckBox checkBox = convertView.findViewById(R.id.cb_checkitemselect);
        tv_itemname.setText(itemList.get(position).getName());
        tv_groupitem.setText(itemList.get(position).getGroup_item().getName_group());
        tv_priceitem.setText(tien(itemList.get(position).getPrice_item())+"đ");
        edit_soluong.setVisibility(View.INVISIBLE);
        for(ItemSelect item1: SelectAdatper.itemSelectList)
        {
            if (itemList.get(position).getId_item()==item1.getItem().getId_item())
            {
                checkBox.setChecked(true);
                edit_soluong.setVisibility(View.VISIBLE);
                edit_soluong.setText(Integer.toString(item1.getSoluong()));
            }
        }
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    edit_soluong.setVisibility(View.VISIBLE);
                } else {

                    edit_soluong.setVisibility(View.INVISIBLE);
                    for(int i=0; i<SelectAdatper.itemSelectList.size(); i++)
                    {
                        if(itemList.get(position).getId_item()==SelectAdatper.itemSelectList.get(i).getItem().getId_item())
                        {
                            SelectAdatper.itemSelectList.remove(i);
                            break;
                        }
                    }

                }
            }
        });

        edit_soluong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                Item item = itemList.get(position);
                try{
                    int soluong = Integer.parseInt(edit_soluong.getText().toString());
                    if (soluong>0)
                    {

                        ItemSelect itemSelect = new ItemSelect(soluong,item);
                        for(int i=0; i<SelectAdatper.itemSelectList.size(); i++)
                        {
                            if(itemList.get(position).getId_item()==SelectAdatper.itemSelectList.get(i).getItem().getId_item())
                            {
                                SelectAdatper.itemSelectList.remove(i);
                                break;
                            }
                        }
                        SelectAdatper.itemSelectList.add(itemSelect);

                    }
                    else{
                        Toast.makeText(context.getApplicationContext(), "Số lượng không thoả mãn.", Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
        return convertView;
    }
    public  String tien(int giatien)
    {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(giatien);
    }
}
