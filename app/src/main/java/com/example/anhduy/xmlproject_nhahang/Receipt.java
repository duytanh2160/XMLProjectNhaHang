package com.example.anhduy.xmlproject_nhahang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Receipt extends AppCompatActivity {

    TextView textReceiptFoodsName;
    TextView textReceiptFoodPrice;
    TextView textTotalReceiptPrice;

    Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        Init();

    }
    
    public void backButton(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }


    protected void Init(){
        textReceiptFoodsName = (TextView) findViewById(R.id.textReceiptFoodName);
        textReceiptFoodPrice = (TextView) findViewById(R.id.textReceiptFoodPrice);
        textTotalReceiptPrice = (TextView) findViewById(R.id.textTotalReceiptPrice);
        backButton = (Button)findViewById(R.id.backButton);

        }
    }

