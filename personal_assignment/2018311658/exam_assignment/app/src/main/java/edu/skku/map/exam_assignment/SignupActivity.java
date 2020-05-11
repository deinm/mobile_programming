package edu.skku.map.exam_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {

    Button button;
    EditText usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        button = (Button)findViewById(R.id.button);
        usernameText = (EditText)findViewById(R.id.username);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String username = usernameText.getText().toString();
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                // (key, value)
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}
