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
import com.example.vanph.karaokemanage.model.Room;
import com.example.vanph.karaokemanage.model.RoomStyle;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by vanph on 25/10/2017.
 */

public class GroupItemAdapter extends ArrayAdapter<Group_Item> {
    private Context context;
    private List<Group_Item> itemgrouplist;
    private ImageView delete;
    private  ImageView edite;
    public static final String RoomKey = "rom_key";
    private EditText edit_groupname;
    private List<RoomStyle> roomStyleList;
    private Activity activity;
    private static AlertDialog dialog;
    private TextView tv_trangthai;
    private int resource;

    public GroupItemAdapter(@NonNull Context context, int resource, @NonNull List<Group_Item> objects, Activity activity, TextView tv_trangthai) {
        super(context, resource, objects);
        this.activity = activity;
        this.tv_trangthai = tv_trangthai;
        this.itemgrouplist = objects;
        this.context = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resource,parent,false);
            TextView textView = convertView.findViewById(R.id.tv_groupname);
            ImageView im_edit = convertView.findViewById(R.id.im_editgroup);
            ImageView im_delete = convertView.findViewById(R.id.iv_deletegroup);
            this.delete = im_delete;
            this.edite = im_edit;
            setClick(itemgrouplist.get(position));
           textView.setText(itemgrouplist.get(position).getName_group());
            return convertView;

    }

    public boolean Update(Group_Item group_item)
    {
        String ten = edit_groupname.getText().toString();
        if(ten.matches(""))
        {
            Toast.makeText(context.getApplicationContext(), "Không được để trống", Toast.LENGTH_LONG).show();
            return false;

        }
        if(DatabaseHandle.getDatabaseHandle(context).getGroup_itemName(ten)==null||
                DatabaseHandle.getDatabaseHandle(context).getGroup_itemName(ten).getId_groupItem()==group_item.getId_groupItem())
        {
            group_item.setName_group(ten);
            return true;
        }
        else{
            Toast.makeText(context.getApplicationContext(), "Tên bị trùng ", Toast.LENGTH_LONG).show();
            return  false;
        }

    }

    public void setClick(final Group_Item group_item)
    {
        edite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View mview = activity.getLayoutInflater().inflate(R.layout.layout_dialoggroupitem,null);
                edit_groupname =mview.findViewById(R.id.edit_groupitemname);
                Button btn_add =mview.findViewById(R.id.btn_addgroupdialog);
                Button btn_cancel =mview.findViewById(R.id.btn_cancelgroupdialog);
                btn_add.setText("Cập nhật");
                edit_groupname.setText(group_item.getName_group());
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Update(group_item))
                        {
                            Toast.makeText(context.getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_LONG).show();
                            GroupItemAdapter.dialog.dismiss();
                            notifyDataSetChanged();
                        }
                    }
                });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GroupItemAdapter.dialog.dismiss();
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
                setClickDelete(group_item);

            }
        });
    }
    public void setClickDelete(final Group_Item group_item)
    {
        if(group_item!=null)
        {
            AlertDialog.Builder altdial = new AlertDialog.Builder(context,R.style.MyDialogTheme);
            altdial.setMessage("Do you want to delete?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(DatabaseHandle.getDatabaseHandle(context).getItemListIDGroup(group_item.getId_groupItem()).size()==0)
                    {
                        boolean ok =  DatabaseHandle.getDatabaseHandle(context).DeleteGroupItem(group_item.getId_groupItem());
                        if(ok)
                        {
                            Toast.makeText(context.getApplicationContext(), "Đã xoá.", Toast.LENGTH_LONG).show();
                            remove(group_item);
                            notifyDataSetChanged();
                            List<Group_Item> group_items = DatabaseHandle.getDatabaseHandle(context).getGroup_items();
                            if(group_items.size()==0)
                            {
                                tv_trangthai.setText("Danh sách trống");
                            }
                        }
                    }
                    else{
                        Toast.makeText(context.getApplicationContext(), "Nhóm đồ đang được chọn, không thể xoá.", Toast.LENGTH_LONG).show();
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
