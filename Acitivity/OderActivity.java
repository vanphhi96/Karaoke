package com.example.vanph.karaokemanage.Acitivity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.adapter.BillAdapter;
import com.example.vanph.karaokemanage.adapter.GridViewAdapter;
import com.example.vanph.karaokemanage.adapter.OderAdapter;
import com.example.vanph.karaokemanage.database.DatabaseHandle;
import com.example.vanph.karaokemanage.model.Bill;
import com.example.vanph.karaokemanage.model.Item;
import com.example.vanph.karaokemanage.model.ItemSelect;
import com.example.vanph.karaokemanage.model.Oder;
import com.example.vanph.karaokemanage.model.Room;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OderActivity extends AppCompatActivity {
    private Room roomInten;
    private List<Item> itemList;
    private TextView tv_title;
    private ListView lv_oder;
    private Button btn_startStop;
    private ImageView iv_back;
    private ImageView iv_addoder;
    private View viewheader;
    private TextView tv_trangthai;
    private TextView timestart;
    private TextView totalpriceitem;
    private List<ItemSelect> itemSelectList = new ArrayList<>();
    private Oder oder;
    private int total;
    private boolean dangchay =false;// tinh trang = true la dang duoc su dung=1 trong database, 0 = false la dang trong
    public static final String OderKey = "oder_key";
    private  Bill bill = null;
    public static AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);
        setupUI();
        LoadData();
        addListenner();
        setBill();
        if(dangchay)
        {
            timestart.setText(bill.getTime_start());

        }
        else{
            timestart.setText("Chưa bắt đầu");
            tv_trangthai.setText("Phòng không hoạt động");
        }


        LoadView();
        setButtonStart();
    }

    @Override
    protected void onStart() {
        LoadView();
        super.onStart();
    }

    public void setBill()
    {
        if(dangchay)
        {
            bill = DatabaseHandle.getDatabaseHandle(this).getBillNonStop(roomInten);
            oder = DatabaseHandle.getDatabaseHandle(this).getOder(bill.getId_bill());
            if(oder==null||oder.getItemList().size()==0)
            {
                tv_trangthai.setText("Danh sách order trống.");
            }
        }

    }
    public void LoadView2()
    {

            total =0;
            List<ItemSelect> itemlsttmp = new ArrayList<>();

            if(itemSelectList.size()==0)
            {
                tv_trangthai.setText("Danh sách order trống.");
            }
            else{
                tv_trangthai.setText("");
            }
            Oder abc = new Oder();
            totalpriceitem.setText(tien(total)+"đ");
            OderAdapter oderAdapter = new OderAdapter(this,R.layout.layout_itemoder,itemlsttmp,abc,this,tv_trangthai,totalpriceitem);
            lv_oder.setAdapter(oderAdapter);



    }

    public void LoadView()
    {
        if(dangchay)
        {
            oder = DatabaseHandle.getDatabaseHandle(this).getOder(bill.getId_bill());
            total =0;

            if(oder!=null)
            {
                itemSelectList = oder.getItemList();
                for (int i=0; i<itemSelectList.size(); i++)
                {
                    total = total+itemSelectList.get(i).getItem().getPrice_item()*itemSelectList.get(i).getSoluong();
                }
                if(itemSelectList.size()==0)
                {
                    tv_trangthai.setText("Danh sách order trống.");
                }
                else{
                    tv_trangthai.setText("");
                }
                totalpriceitem.setText(tien(total)+"đ");
                OderAdapter oderAdapter = new OderAdapter(this,R.layout.layout_itemoder,itemSelectList,oder,this,tv_trangthai,totalpriceitem);
                lv_oder.setAdapter(oderAdapter);
            }

        }
    }
    public  String tien(int giatien)
    {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(giatien);
    }
    public void showBill()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mview = getLayoutInflater().inflate(R.layout.layout_dialogbill,null);
        Button btn_ok=mview.findViewById(R.id.btn_okbill);
        TextView tv_trangthaibill = mview.findViewById(R.id.tv_trangthaiitembill);
        TextView tv_tenphong = mview.findViewById(R.id.tv_tenphongbilll);
        TextView tv_loaiphong = mview.findViewById(R.id.tv_loaigiaphongbill);
        TextView tv_timestart = mview.findViewById(R.id.tv_timstartbill);
        TextView tv_timestop = mview.findViewById(R.id.tv_timestopbill);
        TextView tv_timesudung = mview.findViewById(R.id.tv_timesudungbill);
        ListView lv_bill = mview.findViewById(R.id.lv_itembill);
        TextView tv_tongtien= mview.findViewById(R.id.tv_tongtienbill);
        TextView tv_tienhat = mview.findViewById(R.id.tv_tienhatbill);
        TextView tv_tienorder = mview.findViewById(R.id.tv_tienorderbill);
        BillAdapter billAdapter=null;
        if(oder!=null)
        {
            billAdapter  = new BillAdapter(this,R.layout.layout_itembill,oder.getItemList());
        }
        else {
            List<ItemSelect> abc = new ArrayList<>();
            billAdapter  = new BillAdapter(this,R.layout.layout_itembill,abc);
        }

        lv_bill.setAdapter(billAdapter);
        if(oder==null||oder.getItemList().size()==0)
        {
            tv_trangthaibill.setText("Danh sách order trống");
        }
        else {
            total=0;
            tv_trangthaibill.setText("");
            itemSelectList = oder.getItemList();
            for (int i=0; i<itemSelectList.size(); i++)
            {
                total = total+itemSelectList.get(i).getItem().getPrice_item()*itemSelectList.get(i).getSoluong();
            }
        }
        tv_tenphong.setText("Tên phòng: "+roomInten.getName_room());
        tv_loaiphong.setText("Loại phòng: "+roomInten.getRoomStyle().getName_roomstyle()+" ("+tien(roomInten.getRoomStyle().getPrice_roomstyle())+"đ/giờ)");
        tv_timestart.setText("Thời gian bắt đầu: "+bill.getTime_start());
        tv_timestop.setText("Thời gian kết thúc: "+bill.getTime_stop());

        Date timebatdau = ConvertDate.StringToDate(bill.getTime_start());
        Date timeketthuc = ConvertDate.StringToDate(bill.getTime_stop());
        float phutbd =(float)(timebatdau.getMinutes());
        float pgbd = phutbd/60;
        float giobt =(float)(timebatdau.getHours());
        giobt= giobt+pgbd;

        float phukt =(float)(timeketthuc.getMinutes());
        float pgkt = phukt/60;
        float giokt = (float)(timeketthuc.getHours());
        giokt = giokt+pgkt;
        float giosd = giokt-giobt;
        int giosd2 = (int)giosd;
        int phutsd=(int)((giosd-giosd2)*60);
        float tienh = giosd*bill.getRoom().getRoomStyle().getPrice_roomstyle();
        int tienhat =(int) (tienh);

        tv_timesudung.setText("Thời gian sử dụng: "+giosd2+"giờ "+phutsd+"phút ");

        tv_tienhat.setText("Tiền phòng: "+tien(tienhat)+"đ");
        tv_tienorder.setText("Tiền order: "+tien(total)+"đ");
        tv_tongtien.setText("Tổng tiền: "+tien(total+tienhat)+"đ");
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OderActivity.dialog.dismiss();
            }
        });
        builder.setView(mview);
        dialog = builder.create();
        dialog.show();
    }
    private void addListenner() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OderActivity.super.onBackPressed();
            }
        });
        iv_addoder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dangchay)
                {
                    Intent intent = new Intent(OderActivity.this, SelectItemActivity.class);
                    intent.putExtra(OderKey,bill);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Không thể thêm khi phòng không hoạt động", Toast.LENGTH_LONG).show();
                }

            }
        });
        btn_startStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dangchay)
                {
                    dangchay = false;
                    setButtonStart();
                    roomInten.setStatus(0);
                    DatabaseHandle.getDatabaseHandle(OderActivity.this).UpdateRoomStatus(roomInten);
                    Date date = new Date();
                    String timestop = ConvertDate.DateToString(date);
                    bill.setTime_stop(timestop);
                     boolean acb= DatabaseHandle.getDatabaseHandle(OderActivity.this).UpdateBillTimeStop(bill);
                    oder = DatabaseHandle.getDatabaseHandle(OderActivity.this).getOder(bill.getId_bill());
                    tv_trangthai.setText("Phòng không hoạt động");
                    timestart.setText("Chưa bắt đầu");
                    showBill();
                    oder=null;
                    LoadView2();

                }
                else {
                    dangchay = true;
                    setButtonStart();
                    roomInten.setStatus(1);
                    DatabaseHandle.getDatabaseHandle(OderActivity.this).UpdateRoomStatus(roomInten);
                    Date date = new Date();
                    String timestart11 = ConvertDate.DateToString(date);
                    Bill bill2 = new Bill(roomInten,timestart11);
                    bill2.setTime_stop("0");
                    boolean insertbill = DatabaseHandle.getDatabaseHandle(OderActivity.this).InsertBill(bill2);
                    List<Bill> lst = DatabaseHandle.getDatabaseHandle(OderActivity.this).getBillList();
                    bill = DatabaseHandle.getDatabaseHandle(OderActivity.this).getBillNonStop(roomInten);
                    timestart.setText(timestart11);
                    tv_trangthai.setText("Danh sách oder trống.");

                }
            }
        });
    }
    public  void setButtonStart()
    {
        if(dangchay)
        {
            btn_startStop.setText("TRẢ PHÒNG");
            btn_startStop.setBackgroundColor(ContextCompat.getColor(this,R.color.RoomActive));
            viewheader.setBackgroundColor(ContextCompat.getColor(this,R.color.RoomActive));

        }else{
            btn_startStop.setText("SỬ DỤNG");
            btn_startStop.setBackgroundColor(ContextCompat.getColor(this,R.color.RoomNoActive));
            viewheader.setBackgroundColor(ContextCompat.getColor(this,R.color.RoomNoActive));
        }
    }
    public  void setupUI()
    {
        tv_title= findViewById(R.id.tv_roomtitle);
        iv_back = findViewById(R.id.im_backroomoder);
        iv_addoder = findViewById(R.id.im_additemoder);
        btn_startStop = findViewById(R.id.btn_startroom);
        tv_trangthai=findViewById(R.id.tv_trangthaiitemoder);
        lv_oder = findViewById(R.id.lv_itemroomoder);
        timestart = findViewById(R.id.tv_timeline);
        totalpriceitem = findViewById(R.id.tv_totalpriceitem);
        viewheader = findViewById(R.id.view_headeroder);
    }

    public void LoadData()
    {
        roomInten = (Room) getIntent().getSerializableExtra(GridViewAdapter.GribAdapterKey);
        tv_title.setText(roomInten.getName_room());
        if(roomInten.getStatus()==0)
        {
            dangchay = false;
            setButtonStart();
        }
        else{

            dangchay = true;
            setButtonStart();
        }
    }

}
