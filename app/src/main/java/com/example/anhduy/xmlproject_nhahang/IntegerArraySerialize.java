package com.example.anhduy.xmlproject_nhahang;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;
import java.util.Vector;



public class IntegerArraySerialize extends Vector<Integer> implements KvmSerializable{

    public final String WSDL_TARGET_NAMESPACE = "http://duygames.zapto.org/WebService1.asmx/";

    @Override
    public Object getProperty(int i) {
        return this.get(i);
    }

    @Override
    public int getPropertyCount() {
        return this.size();
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        propertyInfo.name = "int";
        propertyInfo.type = PropertyInfo.INTEGER_CLASS;
    }

    @Override
    public void setProperty(int i, Object o) {
        this.add((Integer)o);
    }
}
