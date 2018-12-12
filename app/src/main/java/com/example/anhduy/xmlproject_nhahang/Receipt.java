package com.example.anhduy.xmlproject_nhahang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Receipt extends AppCompatActivity {

    TextView textTotalReceiptPrice;
    ListView listView;

    Intent callerIntent;
    Bundle packageFromCaller;

    ArrayList<Integer> selectedFoodPosition;
    ArrayList<Integer> listTotalPrice;

    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        Init();
        listView.setAdapter(customAdapter);
        textTotalReceiptPrice.setText("VND: " + AddADotForPrice("" + GetTotalPrice()));
    }

    private void Init(){
        textTotalReceiptPrice = (TextView)findViewById(R.id.textTotalPrice);
        listView = (ListView)findViewById(R.id.OrderListView);

        callerIntent = getIntent();
        packageFromCaller = callerIntent.getBundleExtra("Package");
        selectedFoodPosition = packageFromCaller.getIntegerArrayList("SelectedFood");
        listTotalPrice = packageFromCaller.getIntegerArrayList("TotalPrice");

        customAdapter = new CustomAdapter();
    }

    private int GetTotalPrice(){
        int result = 0;
        for (int i : listTotalPrice){
            result += i;
        }
        return result;
    }





    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return selectedFoodPosition.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.order_item,null);

            TextView textFoodName = (TextView)view.findViewById(R.id.textTenMon);
            TextView textPrice = (TextView)view.findViewById(R.id.textGia);

            textFoodName.setText(SelectingTable.database.get(selectedFoodPosition.get(i)).Name);
            textPrice.setText(AddADotForPrice("" +listTotalPrice.get(i)) + " VND");

            return view;
        }
    }

    private String AddADotForPrice(String price){
        char[] temp = price.toCharArray();
        String result = "";
        int a = 1;
        for(int j = temp.length -1  ; j >= 0 ; j--) {
            if (a <= 3) {
                result += temp[j];
                a++;
            } else {
                result += ".";
                result += temp[j];
                a=2;
            }
        }
        result = new StringBuilder(new String(result)).reverse().toString();
        return result;
    }


    }

