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
import org.w3c.dom.Text;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CheckOrderStatus extends AsyncTask<String,Integer,String> {

    public int ID;
    TextView textStatus;

    public CheckOrderStatus(int id, TextView TextStatus) {
        this.ID = id;
        this.textStatus = TextStatus;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            ConnectionClass conn = new ConnectionClass();

            String Q0 = "select TinhTrang from PhieuDatMon " + "where IDPhieuDat = " + ID;

            Statement st;
            int TinhTrang = 5555;
            st = conn.CONN().createStatement();


            ResultSet rs = st.executeQuery(Q0);
            while (rs.next()) {
                TinhTrang = rs.getInt("TinhTrang");
            }
            st.close();
            if(TinhTrang == 0 || TinhTrang == 1 || TinhTrang == 2){
                return "" + TinhTrang;
            }else{
                return "False";
            }
        }catch (SQLException e) {
            return "Failed";
        } catch (ClassNotFoundException e) {
            return "Failed";
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.compareTo("Failed") != 0){


            if(result.compareTo("0") == 0){
                textStatus.setText("Đang chờ");
            }else if(result.compareTo("1") == 0){
                textStatus.setText("Đang xử lý");
            }else if(result.compareTo("2") == 0){
                textStatus.setText("Đã hoàn thành");
            }




        }
    }
}
