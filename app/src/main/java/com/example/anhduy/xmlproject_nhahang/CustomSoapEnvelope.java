package com.example.anhduy.xmlproject_nhahang;

import org.ksoap2.SoapEnvelope;

public class CustomSoapEnvelope extends SoapEnvelope {

    public CustomSoapEnvelope(int version) {
        super(version);
        this.version = version;
        if (version == 100) {
            this.xsi = "http://www.w3.org/1999/XMLSchema-instance";
            this.xsd = "http://www.w3.org/1999/XMLSchema";
        } else {
            this.xsi = "http://www.w3.org/2001/XMLSchema-instance";
            this.xsd = "http://www.w3.org/2001/XMLSchema";
        }

        if (version < 120) {
            this.enc = "http://schemas.xmlsoap.org/soap/encoding/";
            this.env = "http://schemas.xmlsoap.org/soap/envelope/";
        } else {
            this.enc = "http://www.w3.org/2001/12/soap-encoding";
            this.env = "http://www.w3.org/2001/12/soap-envelope";
        }

    }
}
