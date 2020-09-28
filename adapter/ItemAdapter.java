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
import com.example.vanph.karaokemanage.model.Group_Item;
import com.example.vanph.karaokemanage.model.Item;
import com.example.vanph.karaokemanage.model.Room;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by vanph on 30/10/2017.
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    private Context context;
    private int resource;
    private List<Item> itemList;
    private ImageView delete;
    private ImageView edite;
    public static final String ItemAdapterKey = "ItemAdapter_key";
    private EditText edit_itemname;
    private Spinner spin_group;
    private EditText edit_giatien;
    private List<Group_Item> group_itemList;
    private Activity activity;
    private static AlertDialog dialog;
    private TextView tv_trangthai;


    public ItemAdapter(@NonNull Context context, int resource, @NonNull List<Item> objects,List<Group_Item> group_itemList,
                       Activity activity, TextView tv_trangthai) {
        super(context, resource, objects);
        this.activity = activity;
        this.context= context;
        this.resource = resource;
        this.group_itemList = group_itemList;
        this.tv_trangthai = tv_trangthai;
        this.itemList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource,parent,false);
        TextView tvTitle = convertView.findViewById(R.id.tv_itemname);
        TextView tv_group = convertView.findViewById(R.id.tv_groupitemname);
        TextView giatiem = convertView.findViewById(R.id.tv_priceitem);
        ImageView im_edit = convertView.findViewById(R.id.im_edititem);
        im_edit.setColorFilter(R.color.colorItem);
        ImageView im_delete = convertView.findViewById(R.id.im_deleteitem);
        this.delete = im_delete;
        this.edite = im_edit;
        setClick(itemList.get(position));
        tvTitle.setText(itemList.get(position).getName());
        tv_group.setText(itemList.get(position).getGroup_item().getName_group());
        giatiem.setText(tien(itemList.get(position).getPrice_item())+"đ");
        return convertView;
    }
    public  String tien(int giatien)
    {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(giatien);
    }
    public void setSpinner()
    {
        if(group_itemList.size()==0)
        {
            Toast.makeText(context.getApplicationContext(), "Loại phòng trống, cần thêm loại phòng.", Toast.LENGTH_LONG).show();
            return;
        }
        String[] arrayspin = new String[group_itemList.size()];
        for(int i=0; i<arrayspin.length; i++)
        {
            arrayspin[i]=group_itemList.get(i).getName_group();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                android.R.layout.simple_spinner_item, arrayspin);
        spin_group.setAdapter(adapter);

    }
    public boolean Update(Item item)
    {
        String ten ="";
        ten =edit_itemname.getText().toString();
        int giatien = Integer.parseInt(edit_giatien.getText().toString());
        int style = spin_group.getSelectedItemPosition();

        if(ten.matches(""))
        {
            Toast.makeText(context.getApplicationContext(), "Không được để trống", Toast.LENGTH_LONG).show();
            return false;
        }
        if(giatien==0)
        {
            Toast.makeText(context.getApplicationContext(), "Giá tiền phải lớn hơn 0", Toast.LENGTH_LONG).show();
            return false;
        }
        if((DatabaseHandle.getDatabaseHandle(context).getItemName(ten)==null||
                DatabaseHandle.getDatabaseHandle(context).getItemName(ten).getId_item()==item.getId_item()))
        {
            item.setName(ten);
            item.setPrice_item(giatien);
            int group = spin_group.getSelectedItemPosition();
            item.setGroup_item(group_itemList.get(group));
            DatabaseHandle.getDatabaseHandle(context).UpdateItem(item);
            return true;
        }
        else{
            Toast.makeText(context.getApplicationContext(), "Tên phòng đã có", Toast.LENGTH_LONG).show();
            return false;
        }

    }
    public int getPositionGroup(Group_Item group_item)
    {
        for(int i=0; i<group_itemList.size(); i++)
        {
            if(group_item.getId_groupItem()==group_itemList.get(i).getId_groupItem())
            {
                return i;
            }
        }
        return 0;
    }
    public void setClick(final Item item)
    {
        edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View mview = activity.getLayoutInflater().inflate(R.layout.layout_dialogitem,null);
                edit_itemname=mview.findViewById(R.id.edit_itemnamedialog);
                edit_giatien=mview.findViewById(R.id.edit_priceitem);
                spin_group = mview.findViewById(R.id.sprin_groupdialog);
                Button btn_add =mview.findViewById(R.id.btn_additemdialog);
                Button btn_cancel =mview.findViewById(R.id.btn_cancelitemldialog);
                btn_add.setText("Cập nhật");
                edit_itemname.setText(item.getName());
                edit_giatien.setText(Integer.toString(item.getPrice_item()));
                setSpinner();
                spin_group.setSelection(getPositionGroup(item.getGroup_item()));
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Update(item))
                        {
                            Toast.makeText(context.getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
                            ItemAdapter.dialog.dismiss();
                            notifyDataSetChanged();
                        }

                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ItemAdapter.dialog.dismiss();
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
                setClickDelete(item);

            }
        });
    }
    public void setClickDelete(final Item item)
    {
        if(item!=null)
        {
                AlertDialog.Builder altdial = new AlertDialog.Builder(context,R.style.MyDialogTheme);
                altdial.setMessage("Do you want to delete?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean ok =  DatabaseHandle.getDatabaseHandle(context).DeleteItem(item.getId_item());
                        if(ok)
                        {
                            Toast.makeText(context.getApplicationContext(), "Đã xoá.", Toast.LENGTH_LONG).show();
                            remove(item);
                            notifyDataSetChanged();
                            List<Item> items = DatabaseHandle.getDatabaseHandle(context).getItems();
                            if(items.size()==0)
                            {
                                tv_trangthai.setText("Danh sách đồ trống");
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
