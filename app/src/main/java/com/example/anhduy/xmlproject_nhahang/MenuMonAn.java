package com.example.anhduy.xmlproject_nhahang;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MenuMonAn extends AsyncTask<String,Integer,String> {
    public final String OPERATION_NAME = "menuMonAn";

    public final String WSDL_TARGET_NAMESPACE = "http://duygames.zapto.org/WebService1.asmx/";

    public final String SOAP_ACTION = WSDL_TARGET_NAMESPACE + OPERATION_NAME;

    public final String SOAP_ADDRESS = "http://duygames.zapto.org/WebService1.asmx";

    public MainActivity.CustomAdapter adapter;
    public RelativeLayout loadingLayout;
    public TextView loadingText;

    public MenuMonAn(MainActivity.CustomAdapter adapter,RelativeLayout loadingLayout,TextView loadingText){
        this.adapter = adapter;
        this.loadingLayout = loadingLayout;
        this.loadingText = loadingText;
    }


    @Override
    protected String doInBackground(String... params) {
        String test;
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        soapEnvelope.dotNet = true;
        soapEnvelope.setOutputSoapObject(request);
        // Needed to make the internet call

        // Allow for debugging - needed to output the request

        HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS,7000);
        androidHttpTransport.debug = true;
        // this is the actual part that will call the webservice
        //Object response=null;
        try {
            androidHttpTransport.call(SOAP_ACTION, soapEnvelope);
            test = androidHttpTransport.responseDump;
            SelectingTable.ReadData(test);
            return "Success";
        } catch (Exception exception) {
            return "Failed";
        }
    }


    protected void onPostExecute(String result) {
        if(result.compareTo("Success")==0) {
            loadingLayout.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
        }else{
            loadingText.setText("XẢY RA SỰ CỐ KẾT NỐI");
        }
    }
}