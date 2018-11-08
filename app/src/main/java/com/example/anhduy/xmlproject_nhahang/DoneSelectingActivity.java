package com.example.anhduy.xmlproject_nhahang;
//http://codepad.org/sqO2o5nf
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.jar.Attributes;

import at.markushi.ui.CircleButton;
import de.codecrafters.tableview.TableView;

public class DoneSelectingActivity extends Activity {

    TextView text_TotalPrice;
    CircleButton orderButton;
    CustomAdapter customAdapter;
    ListView listView;
    Intent callerIntent;
    Bundle packageFromCaller;

    ArrayList<Menu> database;
    ArrayList<Integer> selectedFoodPosition;
    ArrayList<Integer> listSoLuong;             //Lưu số lượng của các món đã chọn.
    ArrayList<Integer> listTotalPrice;
    int TotalPrice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmorder);

        Init();

        LoadListView();

    }

    private void Init(){
        callerIntent = getIntent();
        packageFromCaller = callerIntent.getBundleExtra("Package");


        database = new ArrayList<Menu>();
        database = SelectingTable.database;
        selectedFoodPosition = packageFromCaller.getIntegerArrayList("SelectedFood");

        listSoLuong = new ArrayList<Integer>();
        for (int i = 0; i < selectedFoodPosition.size(); i++) {
            listSoLuong.add(1);
        }// Khởi tạo giá trị.
        listTotalPrice = new ArrayList<Integer>();
        for (int i = 0; i < selectedFoodPosition.size(); i++) {
            listTotalPrice.add(0);
        }


        customAdapter = new CustomAdapter();

        //Khai báo "findViewById" bỏ vào đây
        listView = (ListView)findViewById(R.id.listView);
        orderButton = (CircleButton) findViewById(R.id.buttonOrderConfirm);
        text_TotalPrice = (TextView)findViewById(R.id.text_TotalPrice);
    }


    public class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {

            return selectedFoodPosition.size();
        }

        @Override
        public Object getItem(int itemPosition) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            // với " i " là vị trí của item đang được chọn  trên màn hình

            view = getLayoutInflater().inflate(R.layout.activity_doneselecting,null);

            //Khai báo giá trị int,string,...
            final int itemPosition = i; // 1. Java bắt buộc phải có "final" khi xài biến bên ngoài vào các hàm nhỏ ở dưới   2. Vì ko xài đc " i " trong hàm nhỏ nên buộc phải tạo như này



            //Khai báo "findViewById"
            ImageView imgView = (ImageView) view.findViewById(R.id.foodImage);
            TextView textTenMon = (TextView) view.findViewById(R.id.text_TenMon);
            TextView textGia  = (TextView) view.findViewById(R.id.text_Gia);
            final TextView text_ItemTotalPrice = (TextView)view.findViewById(R.id.text_ItemTotalPrice);

            final Spinner spinner = (Spinner)view.findViewById(R.id.spinnerFoodSize);
            ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(DoneSelectingActivity.this,
                    R.layout.spinner_item_foodsize,getResources().getStringArray(R.array.FoodSize));
            stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(stringAdapter);
            final EditText editText_SoLuong = (EditText)view.findViewById(R.id.editText_SoLuong);



            //Gán giá trị khi mới load activity
            editText_SoLuong.setText(listSoLuong.get(i).toString());
            String name = "";
            String priceSmall = "";
            String priceBig = "";


            name += database.get(selectedFoodPosition.get(i)).Name;
            priceSmall += database.get(selectedFoodPosition.get(i)).PriceSmall;
            priceBig +=  database.get(selectedFoodPosition.get(i)).PriceBig;
            Picasso.get().load(database.get(selectedFoodPosition.get(i)).ImageUrl).into(imgView);
            textTenMon.setText(name);
            textGia.setText("N: " + priceSmall + " VND\nL: " + priceBig + " VND");
            text_ItemTotalPrice.setText(AddADotForPrice("" +priceBig));



            //XỬ LÝ KHI CÓ THAY ĐỔI GIÁ TRỊ:


            // + Xử lý editText Số lượng
            editText_SoLuong.addTextChangedListener(new TextWatcher() {
                int old;
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(editText_SoLuong.getText().toString().compareTo("")!= 0) {
                        old = listTotalPrice.get(itemPosition);
                    }
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (editText_SoLuong.getText().toString().compareTo("") != 0) {
                        int soLuong = Integer.parseInt(editText_SoLuong.getText().toString());
                        listSoLuong.set(itemPosition,soLuong);
                        if (soLuong > 0) {
                            int price = GetPrice(spinner,itemPosition);
                            text_ItemTotalPrice.setText(AddADotForPrice("" + soLuong * price));
                            if(old != soLuong * price && old != 0) {
                                TotalPrice -= old;
                                TotalPrice += soLuong * price;
                                listTotalPrice.set(itemPosition,soLuong * price);
                            }else if(old == 0){
                                TotalPrice += soLuong * price;
                                listTotalPrice.set(itemPosition,soLuong * price);
                            }
                            text_TotalPrice.setText(AddADotForPrice("" + TotalPrice));
                        } else {
                            text_ItemTotalPrice.setText("0");
                        }
                    }else{
                        text_ItemTotalPrice.setText("0");
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            // + Xử lý spinner chọn food size
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int old = listTotalPrice.get(itemPosition);

                    if(editText_SoLuong.getText().toString().compareTo("") == 0){
                        editText_SoLuong.setText("1");
                    }


                    int soLuong = Integer.parseInt(editText_SoLuong.getText().toString());
                    int price = GetPrice(spinner,itemPosition);
                    listSoLuong.set(itemPosition,soLuong);

                    text_ItemTotalPrice.setText(AddADotForPrice("" + soLuong * price));
                    if(old != soLuong * price && old != 0) {
                        TotalPrice -= old;
                        TotalPrice += soLuong * price;
                        listTotalPrice.set(itemPosition,soLuong * price);
                    }else if(old == 0){
                        TotalPrice += soLuong * price;
                        listTotalPrice.set(itemPosition,soLuong * price);
                    }
                    text_TotalPrice.setText(AddADotForPrice("" + TotalPrice));
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            return view;
        }
    }


    //Lấy ra giá của món ăn tương ứng với size đã chọn
    private int GetPrice(Spinner spinner,int i){
        if (spinner.getSelectedItem().toString().compareTo("Lớn") == 0) {
            return database.get(selectedFoodPosition.get(i)).PriceBig;
        } else {
            return database.get(selectedFoodPosition.get(i)).PriceSmall;
        }
    }

    //Xác nhận khi chỉnh sửa các món trong Order
    public void orderConfirmFunction(View view){

    }


    private void LoadListView(){
        listView.setAdapter(customAdapter);
        UIUtils.setListViewHeightBasedOnItems(listView);
    }



    //Thêm dấu " . " để tách số ra hàng trăm, hàng triệu,...
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
