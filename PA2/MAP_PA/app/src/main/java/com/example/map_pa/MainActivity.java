package com.example.map_pa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    String username_text, password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.userid);
        password = (EditText)findViewById(R.id.password);

        if(getIntent().getExtras() != null){
            EditText username = (EditText)findViewById(R.id.userid);
            Intent signupIntent = getIntent();
            username.setText(signupIntent.getStringExtra("Username"));
        }

        Button login = (Button)findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_text = username.getText().toString();
                password_text = password.getText().toString();

                if(username_text.length()==0) {
                    String msg = "Wrong Username";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    if(password_text == null) password_text = "";
                    // id check
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    Query query = rootRef.child("user_info").orderByChild("userName");

                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String data = dataSnapshot.toString();
                            // check if id exists
                            if(data.contains(username_text+"={")){
                                // pw check
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("user_info").child(username_text).child("password");
                                ValueEventListener valueEventListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String database_password = dataSnapshot.getValue().toString();

                                        if (!database_password.equals(password_text)) {
                                            String msg = "Wrong Password";
                                            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                            return;
                                        } else {
                                            // Correct password
                                            Intent loginIntent = new Intent(MainActivity.this, postPage.class);
                                            loginIntent.putExtra("Username", username_text);
                                            startActivity(loginIntent);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                };
                                userRef.addListenerForSingleValueEvent(valueEventListener);
                            }
                            else{
                                Log.d("no", dataSnapshot.toString());
                                String msg = "There is no username you input.";
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                                return;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

        TextView signup = (TextView)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(MainActivity.this, SignUp.class);
                startActivity(signupIntent);
            }
        });


    }
}
