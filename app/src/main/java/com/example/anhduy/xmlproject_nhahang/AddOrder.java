package com.example.anhduy.xmlproject_nhahang;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

public class AddOrder extends AsyncTask<String,Integer,String> {

    public final String OPERATION_NAME = "AddOrder";

    public final String WSDL_TARGET_NAMESPACE = "http://duygames.zapto.org/WebService1.asmx/";

    public final String SOAP_ACTION = WSDL_TARGET_NAMESPACE + OPERATION_NAME;

    public final String SOAP_ADDRESS = "http://duygames.zapto.org/WebService1.asmx/";
    public Orders order;
    public int STTBan;

    public AddOrder(Orders passedOrder,int sttBan){
        order = passedOrder;
        STTBan = sttBan;
    }

    @Override
    protected String doInBackground(String... params) {
        String test;
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        IntegerArraySerialize vector = new IntegerArraySerialize();
        for(int temp : order.selectedFoodPosition){
            vector.add(temp);
        }
        PropertyInfo v = new PropertyInfo();
        v.setName("selectedFoodPosition");
        v.setValue(vector);
        v.setType(vector.getClass());

        request.addProperty(v);

        vector = new IntegerArraySerialize();
        for(int temp : order.listSoLuong){
            vector.add(temp);
        }
        v = new PropertyInfo();
        v.setName("listSoLuong");
        v.setValue(vector);
        v.setType(vector.getClass());

        request.addProperty(v);


        vector = new IntegerArraySerialize();
        for(int temp : order.listTotalPrice){
            vector.add(temp);
        }
        v = new PropertyInfo();
        v.setName("listTotalPrice");
        v.setValue(vector);
        v.setType(vector.getClass());

        request.addProperty(v);


        vector = new IntegerArraySerialize();
        for(int temp : order.listFoodSizeIndex){
            vector.add(temp);
        }
        v = new PropertyInfo();
        v.setName("listFoodSizeIndex");
        v.setValue(vector);
        v.setType(vector.getClass());

        request.addProperty(v);

        PropertyInfo pi = new PropertyInfo();
        pi = new PropertyInfo();
        pi.setName("STTBan");
        pi.setValue(STTBan);
        pi.setType(Integer.class);
        request.addProperty(pi);



        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        soapEnvelope.env = "http://www.w3.org/2003/05/soap-envelope";
        soapEnvelope.implicitTypes = true;
        soapEnvelope.setAddAdornments(false);

        soapEnvelope.dotNet = true;
        soapEnvelope.setOutputSoapObject(request);
        soapEnvelope.addMapping(WSDL_TARGET_NAMESPACE, "IntegerArraySerialize", new IntegerArraySerialize().getClass());
        // Needed to make the internet call

        // Allow for debugging - needed to output the request

        HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
        androidHttpTransport.debug = true;
        // this is the actual part that will call the webservice
        //Object response=null;
        try {
            androidHttpTransport.call("", soapEnvelope);
            test = soapEnvelope.getResponse().toString();
            //MainActivity.response = test;
            return test;
        } catch (Exception exception) {
            Log.d("",androidHttpTransport.requestDump);
            return exception.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        MainActivity.response = result;
    }
}