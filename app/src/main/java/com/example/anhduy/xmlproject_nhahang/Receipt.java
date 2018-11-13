package com.example.anhduy.xmlproject_nhahang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Receipt extends AppCompatActivity {

    TextView textReceiptFoodName;
    TextView textReceiptFoodPrice;
    TextView textTotalReceiptPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        Init();

        //Logic:
        //Show tên món x số lượng = giá lấy từ màn hình DoneSelectingActivity
        //Show tổng hóa đơn
    }

    public void backButton(View view){
        Intent intent = new Intent(Receipt.this, MainActivity.class);

        startActivity(intent);

    }

    protected void Init(){
        textReceiptFoodName = (TextView) findViewById(R.id.textReceiptFoodName);
        textReceiptFoodPrice = (TextView) findViewById(R.id.textReceiptFoodPrice);
        textTotalReceiptPrice = (TextView) findViewById(R.id.textTotalReceiptPrice);
    }
}

