package com.example.vanph.karaokemanage.Acitivity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vanph.karaokemanage.R;
import com.example.vanph.karaokemanage.adapter.BillAdapter;
import com.example.vanph.karaokemanage.adapter.ListBillAdatper;
import com.example.vanph.karaokemanage.database.DatabaseHandle;
import com.example.vanph.karaokemanage.model.Bill;
import com.example.vanph.karaokemanage.model.ItemSelect;
import com.example.vanph.karaokemanage.model.Oder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListBillActivity extends AppCompatActivity {
    private   List<Bill> billList = new ArrayList<>();
    private ListView lv_View;
    public static   AlertDialog dialog;
    private ImageView im_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_bill);
        lv_View = findViewById(R.id.lv_dshoadon);
        im_back=findViewById(R.id.iv_backhoadon);
        LoadView();
        setaddListenner();
    }
    public  String tien(int giatien)
    {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(giatien);
    }
    public void showBill(Bill bill)
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
        Oder oder = DatabaseHandle.getDatabaseHandle(this).getOder(bill.getId_bill());
        List<ItemSelect> itemSelectList= new ArrayList<>();
        if(oder!=null)
        {
            itemSelectList=oder.getItemList();
        }
        BillAdapter billAdapter = new BillAdapter(this,R.layout.layout_itembill,itemSelectList);
        lv_bill.setAdapter(billAdapter);

        int total =0;

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
        tv_tenphong.setText("Tên phòng: "+bill.getRoom().getName_room());
        tv_loaiphong.setText("Loại phòng: "+bill.getRoom().getRoomStyle().getName_roomstyle()+" ("+tien(bill.getRoom().getRoomStyle().getPrice_roomstyle())+"đ/giờ)");
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
                ListBillActivity.dialog.dismiss();
            }
        });
        builder.setView(mview);
        dialog = builder.create();
        dialog.show();
    }
    public void setaddListenner()
    {
        im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public  void LoadView()
    {
        billList = DatabaseHandle.getDatabaseHandle(this).getBillList();
        ListBillAdatper listBillAdatper = new ListBillAdatper(this,R.layout.layout_itemdshoadon,billList);
        lv_View.setAdapter(listBillAdatper);
        billList = DatabaseHandle.getDatabaseHandle(this).getBillList();
        lv_View.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showBill(billList.get(position));
            }
        });
    }

}
