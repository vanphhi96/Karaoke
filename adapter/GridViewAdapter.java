package com.example.vanph.karaokemanage.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.vanph.karaokemanage.Acitivity.OderActivity;
import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.model.Room;

import java.util.List;

import static com.example.vanph.karaokemanage.R.color.RoomNoActive;

/**
 * Created by vanph on 31/10/2017.
 */

public class GridViewAdapter  extends BaseAdapter{
    private List<Room> roomNameList;
    private Context context;
    private LayoutInflater layoutInflater;
    private Button btnItem;
    public static final String GribAdapterKey = "groupitem_key";
    public GridViewAdapter(List<Room> roomNameList, Context context) {
        this.roomNameList = roomNameList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return roomNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View gridView = convertView;
       if(convertView==null)
       {
           layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           gridView = layoutInflater.inflate(R.layout.layout_itemgrid,null);

       }
        btnItem = gridView.findViewById(R.id.btn_itemgrid);
        btnItem.setText(roomNameList.get(position).getName_room()+"\n"+roomNameList.get(position).getRoomStyle().getName_roomstyle());
        if(roomNameList.get(position).getStatus()==0)
        {
           btnItem.setBackgroundColor(ContextCompat.getColor(context,R.color.RoomNoActive));

        }
        else{
            btnItem.setBackgroundColor(ContextCompat.getColor(context,R.color.RoomActive));
        }
        setOnClick(roomNameList.get(position));
        return gridView;
    }
    public void setOnClick(final Room room)
    {
        btnItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OderActivity.class);
                intent.putExtra(GribAdapterKey,room);
                context.startActivity(intent);
            }
        });

    }
}
