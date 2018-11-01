package com.example.anhduy.xmlproject_nhahang;

import android.content.Intent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.media.AudioAttributesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
import com.squareup.picasso.Picasso;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    public static String response = "null";
    //Lưu vị trí của một món ăn cụ thể NẰM TRONG MẢNG DATABASE (nếu chưa hiểu nhắc tui nói lại cho)
    private ArrayList<Integer> itemPosition;

    //Lưu vị trí các món ăn đã được chọn
    private ArrayList<Integer> selectedFoodPosition;

    //Loại món ăn hiện tại đang lựa chọn
    private String TypeChose;

    private Toast forgetToSelectToast;




    Spinner spinner;
    SwipeRefreshLayout swipeRefreshLayout;
    ListView listView;
    CustomAdapter adapter;
    Button doneButton;
    RelativeLayout loadingLayout;
    TextView loadingText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Khởi tạo giá trị mặc định
        Init();

        //Countdown 20s, sau khi hết nếu vẫn chưa có database vào dữ liệu -> ko thể kết nối internet
        checkOnline();


        //Xử lý liên quan đến dropbox
        SetUpDropDownBox();


        //Load list view lần đầu
        LoadListView();


        //Refresh listview: load lại database khi vuốt màn hình xuống
        RefreshListView();


        //Xử lý sự kiện khi click vào item của listview
        OnClickListView();


        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedFoodPosition.size() == 0){
                    forgetToSelectToast.show();
                }else {
                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList("SelectedFood",selectedFoodPosition);

                    Intent intent = new Intent(MainActivity.this, DoneSelectingActivity.class);
                    intent.putExtra("Package",bundle);
                    startActivity(intent);
                }
            }
        });


    }


    private void Init(){
        listView = (ListView)findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        adapter = new CustomAdapter();
        spinner = (Spinner)findViewById(R.id.spinner);
        doneButton = (Button)findViewById(R.id.button_Done);
        loadingLayout = (RelativeLayout)findViewById(R.id.loadingLayout);
        loadingText = (TextView)findViewById(R.id.loadingText);

        forgetToSelectToast = Toast.makeText(MainActivity.this,"Bạn chưa chọn món nào cả",Toast.LENGTH_SHORT);

        itemPosition = new ArrayList<Integer>();
        selectedFoodPosition = new ArrayList<Integer>();
    }


    private void SetUpDropDownBox(){
        ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(MainActivity.this,
                R.layout.spinner_item,getResources().getStringArray(R.array.Type));
        stringAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stringAdapter);
        TypeChose = spinner.getSelectedItem().toString();


        //Xử lý khi chọn item cho dropbox
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //Chỉ update listview khi chọn item khác item đã chọn trước đó
                if(spinner.getSelectedItem().toString().compareTo(TypeChose) != 0) {
                    listView.setAdapter(null);


                    itemPosition.clear();
                    TypeChose = spinner.getSelectedItem().toString();


                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });
    }


    private void LoadDatabase(){
        MenuMonAn task = new MenuMonAn();
        task.execute(new String[]{""});
    }

    private void LoadListView(){
        listView.setAdapter(adapter);
    }


    private void OnClickListView(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckBox checkbox = (CheckBox)view.findViewById(R.id.checkBox);

                //Check nếu món vừa chọn đã chọn trước đó hay chưa
                if(selectedFoodPosition.contains(itemPosition.get(i))){
                    selectedFoodPosition.remove(itemPosition.get(i));
                    checkbox.setChecked(false);
                }else{
                    selectedFoodPosition.add(itemPosition.get(i));
                    checkbox.setChecked(true);
                }

                /* //Chỉ để debug
                String count = "";
                for(int k = 0 ; k < selectedFoodPosition.size() ; k++){
                    if(k == 0){
                        count += database.get(selectedFoodPosition.get(k)).Name;
                    }else {
                        count = count + ";" + database.get(selectedFoodPosition.get(k)).Name;
                    }
                }

                Toast.makeText(MainActivity.this,"You select: " + count ,Toast.LENGTH_SHORT).show();*/
            }
        });
    }

    private void RefreshListView(){
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                            listView.setAdapter(null);


                            itemPosition.clear();
                            TypeChose = spinner.getSelectedItem().toString();


                            LoadDatabase();
                            listView.setAdapter(adapter);


                            Toast.makeText(MainActivity.this, "Đã cập nhật database!", Toast.LENGTH_SHORT).show();
                    }
                },1000);
            }
        });
    }


    private void checkOnline(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(adapter.getCount() == 0){
                    Toast.makeText(MainActivity.this,"Kết nối thất bại",Toast.LENGTH_SHORT);
                    loadingText.setText("VUI LÒNG KIỂM TRA LẠI KẾT NỐI");
                }
            }
        }, 20000);
    }



    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            int count = 0;

            //Phân loại món để show lên listview
            for(int i = 0 ; i < SelectingTable.database.size() ; i++){
                if(SelectingTable.database.get(i).Type.compareTo(TypeChose) == 0){
                    count++;
                    itemPosition.add(i);
                }
            }

            if(count > 0){
                loadingLayout.setVisibility(View.GONE);
            }

            return count;
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
            view = getLayoutInflater().inflate(R.layout.customlayout,null);
                ImageView image = (ImageView) view.findViewById(R.id.foodImage);
                TextView text_TenMon = (TextView) view.findViewById(R.id.text_TenMon);
                TextView text_Gia = (TextView) view.findViewById(R.id.text_Gia);
                CheckBox checkbox = (CheckBox)view.findViewById(R.id.checkBox);

                Picasso.get().load(SelectingTable.database.get(itemPosition.get(i)).ImageUrl).into(image);

                text_TenMon.setText(SelectingTable.database.get(itemPosition.get(i)).Name);
                text_Gia.setText("N: " + SelectingTable.database.get(itemPosition.get(i)).PriceSmall + " VND\nL: " + SelectingTable.database.get(itemPosition.get(i)).PriceBig + " VND");

            if(selectedFoodPosition.contains(itemPosition.get(i))){
                checkbox.setChecked(true);
            }else{
                checkbox.setChecked(false);
            }
            return view;
        }
    }

//chưa xài, dùng để check nếu đt có đang kết nối mạng hay ko. EDIT: KO XÀI DC
    private boolean isNetworkAvailable() {
        if (isNetworkAvailable()) {
            try {
                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(1500);
                urlc.connect();
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
            }
        } else {
        }
        return false;
    }
}
