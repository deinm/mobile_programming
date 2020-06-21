package edu.skku.map.week5_practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, intent.getAction(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // explicit Intent
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                // (key, value)
                intent.putExtra("myData", "Hi, 2nd Activity");
                startActivity(intent);
            }
        });


        Button button2 = findViewById(R.id.implicit);

        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                EditText edit = findViewById(R.id.editText);
                Uri url = Uri.parse(edit.getText().toString());

                // implicit Intent
                Intent intent = new Intent(Intent.ACTION_VIEW, url);
                // (key, value)
                if(intent.resolveActivity(getPackageManager())!=null) startActivity(intent);
            }
        });


        Button button3 = findViewById(R.id.broadcast);

        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Broadcast
                Intent intent = new Intent("com.example.week5_practice.helloAction");
                sendBroadcast(intent);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.example.week5_practice.helloAction");
        registerReceiver(br, filter);
    }

    @Override
    protected void onPause(){
        super.onPause();
        unregisterReceiver(br);
    }
}
