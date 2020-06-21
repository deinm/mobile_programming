package edu.skku.map.week4_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.ListView);

        Button button1 = (Button)findViewById(R.id.first);
        Button button2 = (Button)findViewById(R.id.second);
        Button button3 = (Button)findViewById(R.id.third);

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ArrayList<Integer> data1 = new ArrayList<>();
                for(int i=0; i<=10; i++){
                    data1.add(i);
                }

                ArrayAdapter<Integer> adapter1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, data1);
                mListView.setAdapter(adapter1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ArrayList<Integer> data2 = new ArrayList<>();
                for(int i=1; i<=10; i++){
                    data2.add((int)Math.pow(2, i));
                }

                ArrayAdapter<Integer> adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, data2);
                mListView.setAdapter(adapter2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ArrayList<String> data3 = new ArrayList<>();
                data3.add("2018311658");
                data3.add("Minji Cha");
                data3.add("Institute for Convergence");
                data3.add("Data Science");
                data3.add("Sungkyunkwan University");

                ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, data3);
                mListView.setAdapter(adapter3);
            }
        });


    }
}
