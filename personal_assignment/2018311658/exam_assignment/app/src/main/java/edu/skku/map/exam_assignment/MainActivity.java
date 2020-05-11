package edu.skku.map.exam_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView signup;
    Button button;
    EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup = (TextView)findViewById(R.id.textView2);
        button = (Button)findViewById(R.id.button);
        username = (EditText)findViewById(R.id.editText);

        Intent intent = getIntent();
        String text = intent.getStringExtra("username");

        username.setText(text);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // explicit Intent
                Intent intent = new Intent(MainActivity.this, mainpageActivity.class);
                // (key, value)
                // intent.putExtra("myData", "Hi, 2nd Activity");
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // explicit Intent
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                // (key, value)
                // intent.putExtra("myData", "Hi, 2nd Activity");
                startActivity(intent);
            }
        });
    }
}
