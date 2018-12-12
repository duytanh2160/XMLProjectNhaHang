package com.example.anhduy.xmlproject_nhahang;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddOrdersViaSQL extends AsyncTask<String,Integer,String> {

    public Orders order;
    public int STTBan;
    ArrayList<Menu> database;
    Activity activity;
    RelativeLayout loadingLayout;
    TextView loadingText;

    public AddOrdersViaSQL(Orders passedOrder, int sttBan, ArrayList<Menu> db,Activity activity,RelativeLayout loadingLayout,TextView loadingText) {
        this.order = passedOrder;
        this.STTBan = sttBan;
        this.database = db;
        this.activity = activity;
        this.loadingLayout = loadingLayout;
        this.loadingText = loadingText;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            ConnectionClass conn = new ConnectionClass();
            Calendar currenttime = Calendar.getInstance();
            Date sqldate = new Date((currenttime.getTime()).getTime());

            java.util.Date dNow = new java.util.Date();
            //2018-12-12 00:00:00.000
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
            String dateTemp = ft.format(dNow);

            String Q1 = "insert into PhieuDatMon" + " values(?,?,?);";
            String Q2 = "select top 1 IDPhieuDat " + "from PhieuDatMon " + "Order by TGLap DESC";
            //String Q2 = "select MAX(IDPhieuDat) as IDPhieuDat " + "from PhieuDatMon";
            String Q3 = "insert into ChiTietPhieuDat " + "values(?,?,?,?,?);";
            PreparedStatement pre;
            Statement st;
            int id = 0;

            st = conn.CONN().createStatement();
            pre = conn.CONN().prepareStatement(Q1);

            //pre.setDate(1,sqlStartDate);
            //pre.setObject(1,ldt);
            pre.setString(1, dateTemp);

            pre.setInt(2, SelectingTable.SoBan);
            pre.setInt(3, 0);
            pre.executeUpdate();
            pre.close();

            ResultSet rs = st.executeQuery(Q2);
            while (rs.next()) {
                id = rs.getInt("IDPhieuDat");
            }
            rs.close();

            pre = conn.CONN().prepareStatement(Q3);
            for (int i = 0; i < order.selectedFoodPosition.size(); i++) {
                pre.setInt(1, id);
                pre.setInt(2, database.get(order.selectedFoodPosition.get(i)).STT);
                pre.setInt(4, order.listSoLuong.get(i));
                if (order.listFoodSizeIndex.get(i) == 0) {
                    pre.setString(3, "LỚN");
                } else if (order.listFoodSizeIndex.get(i) == 1) {
                    pre.setString(3, "NHỎ");
                }
                pre.setInt(5, order.listTotalPrice.get(i));
                pre.executeUpdate();
            }
            pre.close();
        } catch (SQLException e) {
            return "Failed";
        } catch (ClassNotFoundException e) {
            return "Failed";
        }
        return "Success";
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.compareTo("Success")==0) {
            loadingLayout.setVisibility(View.GONE);

            Bundle bundle = new Bundle();
            bundle.putIntegerArrayList("SelectedFood",order.selectedFoodPosition);
            bundle.putIntegerArrayList("TotalPrice",order.listTotalPrice);

            Intent intent = new Intent(activity, Receipt.class);
            intent.putExtra("Package",bundle);
            activity.startActivity(intent);
        }else{
            loadingText.setText("XỬ LÝ GẶP VẤN ĐỀ");
        }
    }
}
