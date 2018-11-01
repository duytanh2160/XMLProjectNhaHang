package com.example.anhduy.xmlproject_nhahang;
//http://codepad.org/sqO2o5nf
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.jar.Attributes;

import at.markushi.ui.CircleButton;
import de.codecrafters.tableview.TableView;

public class DoneSelectingActivity extends Activity {

    //đưa các món ăn đã chọn vào 1 listView
    //Hiển thị các món ăn đó
    //Xác nhận order bằng button

    //Lưu vị trí của một món ăn cụ thể NẰM TRONG MẢNG DATABASE (nếu chưa hiểu nhắc tui nói lại cho)
    //private ArrayList<Integer> itemPosition;

    //Loại món ăn hiện tại đang lựa chọn
    //private String TypeChose;


    //TableView tableView;
    TextView labelTenMon;
    TextView labelGia;
    TextView labelMieuTa;
    TextView textTenMon;
    TextView textGia;
    TextView textMieuTa;
    TextView textFoodNumber;

    ImageView imgView;

    TextView textView;

    CircleButton orderButton;
    ImageButton addFoodButton;
    ImageButton removeFoodButton;

    CustomAdapter customAdapter;
    ListView listView;

    ArrayList<Menu> database;
    ArrayList<Integer> selectedFoodPosition;
    Intent callerIntent;
    Bundle packageFromCaller;


//    String name = "";
//    String priceSmall = "";
//    String priceBig = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);

        Init();

        listView.setAdapter(customAdapter);
        LoadListView();


        //Test
   /*   String count = "";
        for(int k = 0 ; k < selectedFoodPosition.size() ; k++){
            if(k == 0){
                count += database.get(selectedFoodPosition.get(k)).Name;
            }else {
                count = count + ";" + database.get(selectedFoodPosition.get(k)).Name;
            }
        }
        textView.setText(count);*/
    }


        ///PHẦN NÀY CHỈ HIỆN ĐƯỢC 1 MÓN
       /* String img = "";
        String name = "";
        String priceSmall = "";
        String priceBig = "";

        //Nếu không sử dụng listView thì cả phần này chỉ hiển thị 1 món đã chọn
        //Nếu chọn 2 món -> bị đè
        for (int i = 0; i < selectedFoodPosition.size(); i++) {
            if (i == 0) {
                img += database.get(selectedFoodPosition.get(i)).ImageUrl;
                name += database.get(selectedFoodPosition.get(i)).Name;
                priceSmall += database.get(selectedFoodPosition.get(i)).PriceSmall;
                priceBig += database.get(selectedFoodPosition.get(i)).PriceBig;

            } else {
                img = img + ";" + database.get(selectedFoodPosition.get(i)).ImageUrl;
                name = name + ";" + database.get(selectedFoodPosition.get(i)).Name;
                priceSmall = priceSmall + ";" + database.get(selectedFoodPosition.get(i)).PriceSmall;
                priceBig = priceBig + ";" + database.get(selectedFoodPosition.get(i)).PriceBig;

            }

            Picasso.get().load(database.get(selectedFoodPosition.get(i)).ImageUrl).into(imgView);
            //textGia.setText("L: " + priceBig);
        }
    }*/

    public class CustomAdapter extends BaseAdapter{


        @Override
        public int getCount() {

            return selectedFoodPosition.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.activity_doneselecting,null);
            String name = "";
            String priceSmall = "";
            String priceBig = "";

            ImageView imgView = (ImageView) view.findViewById(R.id.foodImage);
            TextView textTenMon = (TextView) view.findViewById(R.id.text_TenMon);
            TextView textGia  = (TextView) view.findViewById(R.id.text_Gia);

                /*if(i == 0){
                    name += database.get(selectedFoodPosition.get(i)).Name;
                    priceSmall += database.get(selectedFoodPosition.get(i)).PriceSmall;
                    priceBig += database.get(selectedFoodPosition.get(i)).PriceBig;
                }else {*/
                    name += database.get(selectedFoodPosition.get(i)).Name;
                    priceSmall += database.get(selectedFoodPosition.get(i)).PriceSmall;
                    priceBig +=  database.get(selectedFoodPosition.get(i)).PriceBig;



            Picasso.get().load(database.get(selectedFoodPosition.get(i)).ImageUrl).into(imgView);
            textTenMon.setText(name);
            textGia.setText("N: " + priceSmall + " VND\nL: " + priceBig + " VND");
            return view;
        }
    }

    //Xác nhận khi chỉnh sửa các món trong Order
    public void orderConfirmFunction(View view){

    }

    //Thêm số lượng món ăn đã chọn
    public void addFoodNumber (View view){
        //Khi bấm button
        //text view tăng lên 1 đơn vị
        Log.i("addFoodNumber", "nút xài được");
    }

    //Giảm số lượng món ăn đã chọn
    public void removeFoodNumber (View view){
        //Khi bấm button
        //text view giảm 1 đơn vị
        Log.i("removeFoodNumber", "nút xài được");
    }

    private void LoadListView(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                listView.setAdapter(customAdapter);
            }
        }, 2000);   //chờ 2 second cho database load xong rồi mới hiện wiew
        //response = task.response;
    }



        protected void Init(){
            callerIntent = getIntent();
            packageFromCaller = callerIntent.getBundleExtra("Package");

            database = new ArrayList<Menu>();
            database = SelectingTable.database;
            selectedFoodPosition = packageFromCaller.getIntegerArrayList("SelectedFood");

            customAdapter = new CustomAdapter();
            listView = (ListView)findViewById(R.id.listView);

         // imgView = (ImageView) findViewById(R.id.foodImage);
            labelTenMon = (TextView) findViewById(R.id.labelTen);
            labelGia = (TextView) findViewById(R.id.labelGia);
            labelMieuTa = (TextView) findViewById(R.id.labelMieuTa);

            orderButton = (CircleButton) findViewById(R.id.buttonOrderConfirm);
            addFoodButton = (ImageButton) findViewById(R.id.buttonAdd);
            removeFoodButton = (ImageButton) findViewById(R.id.buttonRemove);

            int foodNumber = 0;
            textFoodNumber = (TextView) findViewById(R.id.textFoodNumber);

         //   textMieuTa = (TextView) findViewById(R.id.text_MieuTa);
         //   textTenMon = (TextView) findViewById(R.id.text_TenMon);
         //   textGia = (TextView) findViewById(R.id.text_Gia);

            //textView = (TextView)findViewById(R.id.textview);
            //tableView = (TableView)findViewById(R.id.tableView);
        }
    }
