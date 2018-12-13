package com.example.anhduy.xmlproject_nhahang;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class DeleteOrder extends AsyncTask<String,Integer,String> {

    public int ID;
    public Activity activity;
    private RelativeLayout loadingLayout;
    private TextView loadingText;

    public DeleteOrder(int id,Activity activity,RelativeLayout loadingLayout) {
        this.ID = id;
        this.activity = activity;
        this.loadingLayout = loadingLayout;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            ConnectionClass conn = new ConnectionClass();

            String Q0 = "select TinhTrang from PhieuDatMon " + "where IDPhieuDat = " + ID;
            String Q1 = "delete from ChiTietPhieuDat " + "where IDPhieuDat = " + ID;
            String Q2 = "delete from PhieuDatMon " + "where IDPhieuDat = " + ID + " and TinhTrang = 0";

            Statement st;
            int TinhTrang = 5555;
            st = conn.CONN().createStatement();

            ResultSet rs = st.executeQuery(Q0);
            while (rs.next()) {
                TinhTrang = rs.getInt("TinhTrang");
            }

            if(TinhTrang == 0){
                st.executeUpdate(Q1);
                st.executeUpdate(Q2);
            }else{
                return "False";
            }


            st.close();
        }catch (SQLException e) {
            return "Failed";
        } catch (ClassNotFoundException e) {
            return "Failed";
        }
        return "Success";
    }

    @Override
    protected void onPostExecute(String result) {
        if(result.compareTo("Success")==0) {
            activity.finish();
        }else{
            Toast.makeText(activity, "Phiếu đặt đã hoăc đang được xử lý, không thể hủy bỏ !", Toast.LENGTH_LONG).show();
        }
        loadingLayout.setVisibility(View.GONE);
    }
}
