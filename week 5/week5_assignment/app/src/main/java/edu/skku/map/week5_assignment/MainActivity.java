package edu.skku.map.week5_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Console;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.submit);

        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                // explicit Intent
                Intent intent = new Intent(MainActivity.this, anotherActivity.class);

                EditText edit = findViewById(R.id.editText);
                String name = edit.getText().toString();

                EditText edit2 = findViewById(R.id.editText2);
                String keyword = edit2.getText().toString();

                // (key, value)
                intent.putExtra("name", name);
                intent.putExtra("keyword", keyword);
                startActivity(intent);
            }
        });
    }
}
