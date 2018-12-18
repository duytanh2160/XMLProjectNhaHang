package com.example.anhduy.xmlproject_nhahang;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Receipt extends AppCompatActivity {

    TextView textTotalReceiptPrice;
    TextView textStatus;
    ListView listView;
    Button addMoreButton;

    RelativeLayout loadingLayout;
    TextView loadingText;

    Intent callerIntent;
    Bundle packageFromCaller;

    ArrayList<Integer> selectedFoodPosition;
    ArrayList<Integer> listTotalPrice;

    CustomAdapter customAdapter;

    AlertDialog.Builder builder;

    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        Init();
        listView.setAdapter(customAdapter);
        textTotalReceiptPrice.setText("VND: " + AddADotForPrice("" + GetTotalPrice()));


        addMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Receipt.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });




        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                      CheckOrderStatus task = new CheckOrderStatus(id,textStatus);
                                      task.execute(new String[]{""});
                                  }
                              },0,10000);
    }


    @Override
    public void onBackPressed() {
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Quay lại");
        builder.setMessage("Quay trở lại các món đã chọn sẽ hủy bỏ đơn hàng vừa đặt ?");
        builder.setPositiveButton("Đồng ý",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteOrder task = new DeleteOrder(id,Receipt.this,loadingLayout);
                        task.execute(new String[]{""});
                        loadingLayout.setVisibility(View.VISIBLE);
                    }
                });
        builder.setNegativeButton("Ở lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }




    private void Init(){
        loadingLayout = (RelativeLayout)findViewById(R.id.loadingLayout);
        loadingLayout.setVisibility(View.GONE);
        loadingText = (TextView)findViewById(R.id.loadingText);
        textStatus = (TextView)findViewById(R.id.textStatus);

        textTotalReceiptPrice = (TextView)findViewById(R.id.textTotalPrice);
        listView = (ListView)findViewById(R.id.OrderListView);
        addMoreButton = (Button)findViewById(R.id.AddMoreButton);

        callerIntent = getIntent();
        packageFromCaller = callerIntent.getBundleExtra("Package");
        selectedFoodPosition = packageFromCaller.getIntegerArrayList("SelectedFood");
        listTotalPrice = packageFromCaller.getIntegerArrayList("TotalPrice");
        id = packageFromCaller.getInt("LastedId");

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

