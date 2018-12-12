package com.example.anhduy.xmlproject_nhahang;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    String ip = "den1.mssql2.gear.host";
    String db = "qldatmonan";
    String un = "qldatmonan";
    String password = "project.xml2018";

    public String error;

    @SuppressLint("NewApi")
    public Connection CONN() throws SQLException, ClassNotFoundException {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnURL = "jdbc:jtds:sqlserver://" + ip + "/" + db+ ";user=" + un + ";password=" + password + ";";

            conn = DriverManager.getConnection(ConnURL);

        }catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
            error = se.getMessage();
            throw se;
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
            error = e.getMessage();
            throw e;
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
            error = e.getMessage();
            throw e;
        }

        return conn;
    }
}
