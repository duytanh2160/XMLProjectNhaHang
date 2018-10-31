package com.example.anhduy.xmlproject_nhahang;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//Các nhiệm vụ có trong class này:
// - Load database
// - Lấy số bàn user nhập gán vào biến <SoBan>


public class SelectingTable extends Activity {

    public static int SoBan;
    public static ArrayList<Menu> database;


    EditText editText_STTBan;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tableseleting);

        Init();


        LoadDatabase();

        //Xử lý khi nhập vào ô trống
        editText_STTBan.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                //nếu ô chưa nhập gì, return false để không xủ lý gì cả.
                //ngược lại khi có chứa chữ số, lưu <SoBan> và chuyển sang MainActivity
                if(editText_STTBan.getText().toString().compareTo("") == 0){
                    return false;
                }else{
                    SoBan = Integer.parseInt(editText_STTBan.getText().toString());
                    Intent intent = new Intent(SelectingTable.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }
            }
        });
    }




    private void Init() {
        editText_STTBan = (EditText) findViewById(R.id.editText_STTBan);
        database = new ArrayList<Menu>();
    }


    private void LoadDatabase(){
        MenuMonAn task = new MenuMonAn();
        task.execute(new String[]{""});
    }


    public static void ReadData(String xmlString){
        if(database.isEmpty() == false) {
            database.clear();
        }
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xmlString));

            Document doc = builder.parse(is);
            Element root = doc.getDocumentElement();
            NodeList list = root.getElementsByTagName("Menu");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if(node instanceof Element) {
                    Element Item = (Element) node;
                    NodeList listChild = Item.getElementsByTagName("STT");
                    int STT = Integer.parseInt(listChild.item(0).getTextContent());

                    listChild = Item.getElementsByTagName("Name");
                    String Name = listChild.item(0).getTextContent();

                    listChild = Item.getElementsByTagName("PriceSmall");
                    int PriceSmall = Integer.parseInt(listChild.item(0).getTextContent());

                    listChild = Item.getElementsByTagName("PriceBig");
                    int PriceBig = Integer.parseInt(listChild.item(0).getTextContent());

                    listChild = Item.getElementsByTagName("Type");
                    String Type = listChild.item(0).getTextContent();

                    listChild = Item.getElementsByTagName("ImageUrl");
                    String ImageUrl = listChild.item(0).getTextContent();

                    Menu food = new Menu();
                    food.STT = STT;
                    food.Name = Name;
                    food.PriceSmall = PriceSmall;
                    food.PriceBig = PriceBig;
                    food.Type = Type;
                    food.ImageUrl = ImageUrl;
                    database.add(food);
                }
            }
        }catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
