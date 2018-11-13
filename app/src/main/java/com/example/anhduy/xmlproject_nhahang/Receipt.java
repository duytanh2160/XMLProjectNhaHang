package com.example.anhduy.xmlproject_nhahang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class Receipt extends AppCompatActivity {

    TextView textReceiptFoodsName;
    TextView textReceiptFoodPrice;
    TextView textTotalReceiptPrice;

    Intent callerIntent;
    Bundle packageFromCaller;
    ArrayList<Menu> database;
    ArrayList<Integer> selectedFoodPosition;
    ArrayList<Integer> listSoLuong;             //Lưu số lượng của các món đã chọn.
    ArrayList<Integer> listTotalPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        Init();

        //Logic:
        //Show tên các món lấy từ màn hình DoneSelectingActivity
        //Show giá từng món lấy từ màn hình DoneSelectingActivity
        //Show tổng hóa đơn = soLuong * price
    }

    public void backButton(View view){
        Intent intent = new Intent(Receipt.this, MainActivity.class);

        startActivity(intent);

    }

    protected void Init(){
        textReceiptFoodsName = (TextView) findViewById(R.id.textReceiptFoodName);
        textReceiptFoodPrice = (TextView) findViewById(R.id.textReceiptFoodPrice);
        textTotalReceiptPrice = (TextView) findViewById(R.id.textTotalReceiptPrice);

        callerIntent = getIntent();
        packageFromCaller = callerIntent.getBundleExtra("Package");


        database = new ArrayList<Menu>();
        database = SelectingTable.database;
        selectedFoodPosition = packageFromCaller.getIntegerArrayList("SelectedFood");


        }
    }

