package com.example.anhduy.xmlproject_nhahang;
//Duy:alo test thấy gì ở đây ko
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.codecrafters.tableview.TableView;

public class DoneSelectingActivity extends Activity {

    TableView tableView;
    TextView textview;

    ArrayList<Menu> database;
    ArrayList<Integer> selectedFoodPosition;
    Intent callerIntent;
    Bundle packageFromCaller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doneselecting);


        Init();

        String count = "";
        for(int k = 0 ; k < selectedFoodPosition.size() ; k++){
            if(k == 0){
                count += database.get(selectedFoodPosition.get(k)).Name;
            }else {
                count = count + ";" + database.get(selectedFoodPosition.get(k)).Name;
            }
        }
        textview.setText(count);








        //Test chỉnh sữa
    }




    protected void Init(){
        callerIntent = getIntent();
        packageFromCaller = callerIntent.getBundleExtra("Package");

        database = new ArrayList<Menu>();
        database = MainActivity.database;
        selectedFoodPosition = packageFromCaller.getIntegerArrayList("SelectedFood");

        textview = (TextView)findViewById(R.id.textview);
        //tableView = (TableView)findViewById(R.id.tableView);
    }
}
