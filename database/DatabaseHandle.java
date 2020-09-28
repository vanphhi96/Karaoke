package com.example.vanph.karaokemanage.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.vanph.karaokemanage.adapter.AssetHelper;
import com.example.vanph.karaokemanage.model.Bill;
import com.example.vanph.karaokemanage.model.Group_Item;
import com.example.vanph.karaokemanage.model.Item;
import com.example.vanph.karaokemanage.model.ItemSelect;
import com.example.vanph.karaokemanage.model.Oder;
import com.example.vanph.karaokemanage.model.Room;
import com.example.vanph.karaokemanage.model.RoomStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanph on 07/10/2017.
 */

public class DatabaseHandle {
    private AssetHelper assetHelper;
    private SQLiteDatabase sqLiteDatabase;
    private String TAG = DatabaseHandle.class.toString();
    private DatabaseHandle(Context context)
    {
        assetHelper = new AssetHelper(context);
        sqLiteDatabase = assetHelper.getReadableDatabase();
    }
    private void closeSql()
    {
        DatabaseHandle.databaseHandle=null;
        sqLiteDatabase.notifyAll();
        sqLiteDatabase.close();
    }
    public  static  DatabaseHandle databaseHandle;
    public static DatabaseHandle getDatabaseHandle(Context context)
    {
        if(databaseHandle==null)
        {

            return  databaseHandle = new DatabaseHandle(context);
        }

        return databaseHandle;
    }
    public RoomStyle getRoomStyle(int ID)
    {

        RoomStyle roomStyle = new RoomStyle();
       // sqLiteDatabase = assetHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_RoomStyle WHERE Room_StyleID = "+ID,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {

            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            roomStyle.setId_roomstyle(ID);
            roomStyle.setName_roomstyle(name);
            roomStyle.setPrice_roomstyle(price);
            return roomStyle;

        }
        return  roomStyle;
    }
    public Room getRoom(int ID)
    {
        Room room = new Room();
        //sqLiteDatabase = assetHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_Room WHERE Room_ID = "+ID,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {

            String name = cursor.getString(1);
            int id_style =  cursor.getInt(2);
            int status = cursor.getInt(3);
            RoomStyle roomStyle = getRoomStyle(id_style);
            if(roomStyle==null)
            {
                return room;
            }
            room.setId(ID);
            room.setName_room(name);
            room.setRoomStyle(roomStyle);
            room.setStatus(status);
           return room;
        }
        return  room;
    }
    public List<RoomStyle> getListRoomStyle()
    {
        List<RoomStyle> roomStyleArrayList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_RoomStyle",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            RoomStyle roomStyle = new RoomStyle(id,name,price);
            roomStyleArrayList.add(roomStyle);

            Log.d(TAG,"getListStory: "+roomStyle.toString());

            //next
            cursor.moveToNext();
        }
        return roomStyleArrayList;
    }
    public List<Room> getListRoom()
    {
        List<Room> roomArrayList = new ArrayList<>();
        //sqLiteDatabase = assetHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_Room",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int id_style = cursor.getInt(2);
            int status = cursor.getInt(3);
            RoomStyle roomStyle = getRoomStyle(id_style);
            if(roomStyle==null)
            {
                return roomArrayList;
            }
            Room room = new Room(id,name,roomStyle,status);
            roomArrayList.add(room);

            Log.d(TAG,"getListStory: "+room.toString());

            //next
            cursor.moveToNext();
        }
        return roomArrayList;
    }
    public List<Room> getRoomListIDStyle(int idStyle)
    {
        List<Room> roomArrayList = new ArrayList<>();
        //sqLiteDatabase = assetHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_Room WHERE Room_Style = "+idStyle,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int id_style = cursor.getInt(2);
            int status = cursor.getInt(3);
            RoomStyle roomStyle = getRoomStyle(id_style);
            if(roomStyle==null)
            {
                return roomArrayList;
            }
            Room room = new Room(id,name,roomStyle,status);
            roomArrayList.add(room);

            Log.d(TAG,"getListStory: "+room.toString());

            //next
            cursor.moveToNext();
        }
        return roomArrayList;
    }
public RoomStyle getRoomStyleName(String name)
{
    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_RoomStyle WHERE Room_StyleName = '"+name+"'",null);
    cursor.moveToFirst();
    RoomStyle roomStyle=null;
    while(!cursor.isAfterLast())
    {
        roomStyle = new RoomStyle();
        roomStyle.setId_roomstyle(cursor.getInt(0));
        roomStyle.setName_roomstyle(cursor.getString(1));
        roomStyle.setPrice_roomstyle(cursor.getInt(2));
        return  roomStyle;
    }
    return  roomStyle;
}
public RoomStyle getRoomStylePrice(int price)
{
    RoomStyle roomStyle=null;
    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_RoomStyle WHERE Room_StylePrice = "+price,null);
    cursor.moveToFirst();
    while(!cursor.isAfterLast())
    {
        roomStyle = new RoomStyle();
        roomStyle.setId_roomstyle(cursor.getInt(0));
        roomStyle.setName_roomstyle(cursor.getString(1));
        roomStyle.setPrice_roomstyle(cursor.getInt(2));
        return  roomStyle;
    }
    return  roomStyle;
}
public Room getRoomIDStyle(int idStyle)
{
    Room room = null;
    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_Room WHERE Room_Style = "+idStyle,null);
    cursor.moveToFirst();
    while(!cursor.isAfterLast())
    {
        int id = cursor.getInt(0);
        String ten = cursor.getString(1);
        int status = cursor.getInt(3);
        RoomStyle roomStyle = getRoomStyle(cursor.getInt(2));

        room = new Room(id,ten,roomStyle,status);
        return room;
    }
    return  room;
}
public Room getRoomName(String name)
{
    Room room = null;
    Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_Room WHERE Room_Name = '"+name+"'",null);
    cursor.moveToFirst();
    while(!cursor.isAfterLast())
    {


        int id = cursor.getInt(0);
        String ten = cursor.getString(1);
        int status = cursor.getInt(3);
        RoomStyle roomStyle = getRoomStyle(cursor.getInt(2));
        room = new Room(id,ten,roomStyle,status);
      return room;
    }
    return  room;
}
    public boolean UpdateRoom(Room room){
        try{
            String sql = "UPDATE table_Room SET Room_Name = '"+room.getName_room()+"', Room_Style = "+room.getRoomStyle().getId_roomstyle()+ ", Room_Status = "+room.getStatus()+" WHERE Room_ID = "+room.getId();
            sqLiteDatabase.execSQL(sql);
            Log.d("Update room",sql);
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }


    }
    public boolean UpdateRoomStatus(Room room)
    {
        try{
            String sql = "UPDATE table_Room SET Room_Status = "+room.getStatus()+" WHERE Room_ID = "+room.getId();
            sqLiteDatabase.execSQL(sql);
            Log.d("Update room",sql);
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean UpdateRoomStyle(RoomStyle roomStyle){
        try{
            String sql = "UPDATE table_RoomStyle SET Room_StyleName = '"+roomStyle.getName_roomstyle()+"', Room_StylePrice = "+roomStyle.getPrice_roomstyle()+ " WHERE Room_StyleID = "+roomStyle.getId_roomstyle();
            sqLiteDatabase.execSQL(sql);
           Log.d("Update roomStyle",sql);
                return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean InsertRoom(Room room)
    {
        try{
            String sql = "INSERT INTO table_Room (Room_Name, Room_Style, Room_Status) VALUES ('"+room.getName_room()+"', '"+room.getRoomStyle().getId_roomstyle()+"'," +room.getStatus()+") ";
            sqLiteDatabase.execSQL(sql);
            Log.d("Insert room", sql);
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }

    }
    public boolean InsertRoomStyle(RoomStyle roomstyle)
    {
        try {
            String sql = "INSERT INTO table_RoomStyle ( Room_StyleName, Room_StylePrice) VALUES ('"+roomstyle.getName_roomstyle()+"', "+roomstyle.getPrice_roomstyle()+") ";
            sqLiteDatabase.execSQL(sql);
            Log.d("Insert roomStyle", sql);
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean DeleteRoomStyle(int id)
    {
        try {
            String sql = "DELETE FROM table_RoomStyle WHERE Room_StyleID = "+id;
            sqLiteDatabase.execSQL(sql);
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean DeleteRoom(int id)
    {
        try {
            String sql = "DELETE FROM table_Room  WHERE Room_ID = "+id;
            sqLiteDatabase.execSQL(sql);
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public List<Group_Item> getGroup_items()
    {
        List<Group_Item> group_items = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_ItemGroup",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            Group_Item group_item = new Group_Item(id,name);
            group_items.add(group_item);

            cursor.moveToNext();
        }
        return group_items;
    }
    public Group_Item getGroup_itemName(String tengroup)
    {
        Group_Item group_item=null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_ItemGroup WHERE Item_GroupName = '"+tengroup+"'",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            group_item = new Group_Item(id,name);
            return group_item;

        }
        return group_item;
    }
    public Group_Item getGroup_itemID(int idgroup)
    {
        Group_Item group_item=null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_ItemGroup WHERE Item_GroupID = "+idgroup,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            group_item = new Group_Item(id,name);
            return group_item;

        }
        return group_item;
    }
    public boolean UpdateGroupItem(Group_Item group_item)
    {
        try{
            String sql = "UPDATE table_ItemGroup SET Item_GroupName = '"+group_item.getName_group()+"' WHERE Item_GroupID = "+group_item.getId_groupItem();
            sqLiteDatabase.execSQL(sql);
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean InsertGroupItem(Group_Item group_item)
    {
        try {
            String sql = "INSERT INTO table_ItemGroup (Item_GroupName) VALUES ('"+group_item.getName_group()+"')";
            sqLiteDatabase.execSQL(sql);
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }

    }
    public boolean DeleteGroupItem(int id)
    {
        try {
            String sql = "DELETE FROM table_ItemGroup WHERE Item_GroupID = "+id;
            sqLiteDatabase.execSQL(sql);
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public List<Item> getItems()
    {
        List<Item> items = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_Item",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {

            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            int idgroup = cursor.getInt(3);
            Group_Item group_item = getGroup_itemID(idgroup);
            Item item = new Item(id,name,price,group_item);
            items.add(item);

            cursor.moveToNext();
        }
        return items;
    }
    public List<Item> getItemListIDGroup(int Idgroup)
    {
        List<Item> items = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_Item WHERE Item_GroupID = "+Idgroup,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            int idgroup = cursor.getInt(3);
            Group_Item group_item = getGroup_itemID(idgroup);
            Item item = new Item(id,name,price,group_item);
            items.add(item);

            cursor.moveToNext();
        }
        return items;
    }
    public Item getItemName(String tenItem)
    {
        Item item=null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_Item WHERE Item_Name = '"+tenItem+"'",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            int idgroup = cursor.getInt(3);
            Group_Item group_item = getGroup_itemID(idgroup);
             item = new Item(id,name,price,group_item);
            return item;

        }
        return item;
    }
    public Item getItemID(int idItem)
    {
        Item item=null;
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_Item WHERE Item_ID = "+idItem,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            int idgroup = cursor.getInt(3);
            Group_Item group_item = getGroup_itemID(idgroup);
            item = new Item(id,name,price,group_item);
            return item;

        }
        return item;
    }
    public boolean UpdateItem(Item item)
    {
        try{
            String sql = "UPDATE table_Item SET Item_Name = '"+item.getName()+"', Item_Price = "+item.getPrice_item()+" WHERE Item_ID = "+item.getId_item();
            sqLiteDatabase.execSQL(sql);
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean InsertItem(Item item)
    {
        try {
            String sql = "INSERT INTO table_Item (Item_Name, Item_Price, Item_GroupID) VALUES ('"+item.getName()+"',"+item.getPrice_item()+","+ item.getGroup_item().getId_groupItem()+")";
            sqLiteDatabase.execSQL(sql);
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }

    }
    public boolean DeleteItem(int id)
    {
        try {
            String sql = "DELETE FROM table_Item WHERE Item_ID = "+id;
            sqLiteDatabase.execSQL(sql);
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean InsertBill(Bill bill)
    {

            try{
                String sql = "INSERT INTO table_Bill (Bill_TimeStart,Bill_TimeStop ,Bill_RoomID) VALUES ('"+bill.getTime_start()+"', '"+bill.getTime_stop()+"', "+bill.getRoom().getId()+")";
                sqLiteDatabase.execSQL(sql);
                return true;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                return false;
            }


    }
    public Bill getBillNonStop(Room room)
    {
        Bill bill=null;
            try{
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_Bill WHERE Bill_RoomID = "+room.getId()+" AND Bill_TimeStop = '0'",null);
                cursor.moveToFirst();
                while(!cursor.isAfterLast())
                {
                    bill = new Bill(room,cursor.getString(1));
                    bill.setId_bill(cursor.getInt(0));
                    int id = cursor.getInt(0);
                    return bill;
                }
            }
            catch (SQLException e)
            {
                return bill;
            }


        return bill;
    }
    public List<Bill> getBillList()
    {

        List<Bill> billList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_Bill WHERE Bill_TimeStop <> '0'",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            Bill bill=null;
            int idroom = cursor.getInt(3);
            Room room = getRoom(idroom);
            bill = new Bill(room,cursor.getString(1));
            bill.setTime_stop(cursor.getString(2));
            bill.setId_bill(cursor.getInt(0));
            billList.add(bill);
            cursor.moveToNext();
        }
        return billList;
    }
    public boolean UpdateBillTimeStop(Bill bill)
    {
        try{
            String sql = "UPDATE table_Bill SET Bill_TimeStop = '"+bill.getTime_stop()+"' WHERE Bill_ID = "+bill.getId_bill();
            sqLiteDatabase.execSQL(sql);
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean DeleteBill(Bill bill)
    {   try {
                String sql = "DELETE FROM table_Bill WHERE Bill_ID = " +bill.getId_bill();
                sqLiteDatabase.execSQL(sql);
                return true;
            }
            catch(SQLException e)
            {
                return false;
            }

    }
    public boolean InsetListOder(Oder oder)
    {
        try {
            for(int i=0; i<oder.getItemList().size(); i++)
            {
                String sql = "INSERT INTO table_ListItemOder (Bill_ID, ItemID, ItemCount) VALUES ("+oder.getId_bill()+","+oder.getItemList().get(i).getItem().getId_item()+","+oder.getItemList().get(i).getSoluong()+")";
                sqLiteDatabase.execSQL(sql);

            }
            return true;

        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean UpdateSLOder(Oder oder)
    {
        try{
            for(int i=0; i<oder.getItemList().size(); i++)
            {
                String sql = "UPDATE table_ListItemOder SET ItemCount = '"+oder.getItemList().get(i).getSoluong()+" WHERE Bill_ID = "+oder.getId_bill()+" AND ItemID = "+oder.getItemList().get(i).getItem().getId_item();
                sqLiteDatabase.execSQL(sql);
            }
            return true;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    public boolean UpdateItemOder(ItemSelect itemSelect, int id_bill)
    {
        try{
            String sql = "UPDATE table_ListItemOder SET ItemCount = "+itemSelect.getSoluong()+" WHERE Bill_ID = "+id_bill+" AND ItemID = "+itemSelect.getItem().getId_item();
            sqLiteDatabase.execSQL(sql);
            return true;
        }
        catch (SQLException e)
        {
            return false;
        }
    }
    public boolean DeleteItemOder(ItemSelect itemSelect, int bill_id) {
        try {
            String sql = "DELETE FROM table_ListItemOder WHERE ItemID = " + itemSelect.getItem().getId_item() + " AND Bill_ID = " + bill_id;
            sqLiteDatabase.execSQL(sql);
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }


    }
    public boolean DeleteOder( int bill_id) {
        try {
            String sql = "DELETE FROM table_ListItemOder WHERE Bill_ID = " +bill_id;
            sqLiteDatabase.execSQL(sql);
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }


    }

    public Oder getOder(int bill_id)
    {
        Oder oder = null ;
        List<ItemSelect> itemSelectList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_ListItemOder WHERE Bill_ID = "+bill_id,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            int id = cursor.getInt(0);
           int itemID= cursor.getInt(1);
           int count = cursor.getInt(2);
           ItemSelect itemSelect = new ItemSelect(count,getItemID(itemID));
           itemSelectList.add(itemSelect);
           cursor.moveToNext();
        }
        if(itemSelectList.size()!=0)
        {
            oder = new Oder(bill_id,itemSelectList);
            return oder;
        }
        return oder;
    }
    public List<Item> SearchItem(String ten)
    {
        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM table_Item WHERE Item_Name LIKE '%"+ten+"%'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql,null);
        cursor.moveToNext();
        while (!cursor.isAfterLast())
        {
            int id= cursor.getInt(0);
            String tenitem= cursor.getString(1);
            int gia = cursor.getInt(2);
            int idgroup = cursor.getInt(3);
            Group_Item group_item = getGroup_itemID(idgroup);
            Item item = new Item(id,tenitem,gia,group_item);
            itemList.add(item);
            cursor.moveToNext();

        }
        return itemList;
    }


 }
