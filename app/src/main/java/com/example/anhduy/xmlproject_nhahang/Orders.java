package com.example.anhduy.xmlproject_nhahang;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

public class Orders  {
    public ArrayList<Integer> selectedFoodPosition;
    public ArrayList<Integer> listSoLuong;             //Lưu số lượng của các món đã chọn.
    public ArrayList<Integer> listFoodSizeIndex;
    public ArrayList<Integer> listTotalPrice;

}
