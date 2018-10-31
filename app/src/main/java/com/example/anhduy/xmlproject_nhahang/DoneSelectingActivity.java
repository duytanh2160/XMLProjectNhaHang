package com.example.anhduy.xmlproject_nhahang;
//http://codepad.org/sqO2o5nf
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
    ImageView imgView;

    CircleButton orderButton;

    CustomAdapter customAdapter;
    ListView listView;

    ArrayList<Menu> database;
    ArrayList<Integer> selectedFoodPosition;
    Intent callerIntent;
    Bundle packageFromCaller;

    String name = "";
    String priceSmall = "";
    String priceBig = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);

        Init();

        listView.setAdapter(customAdapter);
       // LoadListView();


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
        }*/
    }

    public class CustomAdapter extends BaseAdapter{


        @Override
        public int getCount() {

            return 0;
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
            textTenMon = (TextView) findViewById(R.id.text_TenMon);
            textGia = (TextView) findViewById(R.id.text_Gia);


            if (i == 0) {
             //   img += database.get(selectedFoodPosition.get(i)).ImageUrl;
                name += database.get(selectedFoodPosition.get(i)).Name;
                priceSmall += database.get(selectedFoodPosition.get(i)).PriceSmall;
                priceBig += database.get(selectedFoodPosition.get(i)).PriceBig;

            } else {
             //   img = img + ";" + database.get(selectedFoodPosition.get(i)).ImageUrl;
                name = name + ";" + database.get(selectedFoodPosition.get(i)).Name;
                priceSmall = priceSmall + ";" + database.get(selectedFoodPosition.get(i)).PriceSmall;
                priceBig = priceBig + ";" + database.get(selectedFoodPosition.get(i)).PriceBig;

            }
            Picasso.get().load(database.get(selectedFoodPosition.get(i)).ImageUrl).into(imgView);
            textTenMon.setText(name);
            textGia.setText("N: " + priceSmall +"VND\nL: " + priceBig + "VND");

            return view;
        }
    }

    public void orderConfirmFunction(View view){


    }

/*    private void LoadListView(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                listView.setAdapter(customAdapter);
            }
        }, 2000);   //chờ 2 second cho database load xong rồi mới hiện wiew
        //response = task.response;
    }*/



        protected void Init(){
            callerIntent = getIntent();
            packageFromCaller = callerIntent.getBundleExtra("Package");

            database = new ArrayList<Menu>();
            database = SelectingTable.database;
            selectedFoodPosition = packageFromCaller.getIntegerArrayList("SelectedFood");

            //itemPosition = new ArrayList<Integer>();

            orderButton = (CircleButton) findViewById(R.id.buttonOrderConfirm);

            customAdapter = new CustomAdapter();
            listView = (ListView)findViewById(R.id.listView);

            imgView = (ImageView) findViewById(R.id.foodImage);
            labelTenMon = (TextView) findViewById(R.id.labelTen);
            labelGia = (TextView) findViewById(R.id.labelGia);
            labelMieuTa = (TextView) findViewById(R.id.labelMieuTa);


            //textMieuTa = (TextView) findViewById(R.id.text_MieuTa);
            // textTenMon = (TextView) findViewById(R.id.text_TenMon);
            // textGia = (TextView) findViewById(R.id.text_Gia);

            //textview = (TextView)findViewById(R.id.textview);
            //tableView = (TableView)findViewById(R.id.tableView);
        }
    }
