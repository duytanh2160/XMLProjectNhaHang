package com.example.anhduy.xmlproject_nhahang;

import android.os.AsyncTask;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class AddMenu extends AsyncTask<String,Integer,String> {

    public final String OPERATION_NAME = "AddMenu";

    public final String WSDL_TARGET_NAMESPACE = "http://duygames.zapto.org/WebService1.asmx/";

    public final String SOAP_ACTION = WSDL_TARGET_NAMESPACE + OPERATION_NAME;

    public final String SOAP_ADDRESS = "http://duygames.zapto.org/WebService1.asmx";

    @Override
    protected String doInBackground(String... params) {
        String test;
        SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME);

        PropertyInfo pi = new PropertyInfo();
        pi.setName("name");
        pi.setValue(params[0]);
        pi.setType(String.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("smallprice");
        pi.setValue(Integer.parseInt(params[1]));
        pi.setType(Integer.class);
        request.addProperty(pi);

        pi = new PropertyInfo();
        pi.setName("bigprice");
        pi.setValue(Integer.parseInt(params[2]));
        pi.setType(Integer.class);
        request.addProperty(pi);

        SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        soapEnvelope.dotNet = true;
        soapEnvelope.setOutputSoapObject(request);
        // Needed to make the internet call

        // Allow for debugging - needed to output the request

        HttpTransportSE androidHttpTransport = new HttpTransportSE(SOAP_ADDRESS);
        androidHttpTransport.debug = true;
        // this is the actual part that will call the webservice
        //Object response=null;
        try {
            androidHttpTransport.call(SOAP_ACTION, soapEnvelope);
            test = soapEnvelope.getResponse().toString();
            //MainActivity.response = test;
            return test;
        } catch (Exception exception) {
            return exception.toString();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        MainActivity.response = result;
    }
}