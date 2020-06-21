package com.example.map_pa;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    private DatabaseReference mPostReference;
    EditText signupUsername, signupPassword, signupFullname, signupBirthday, signupEmail;
    String username, userpw, fullname, birthday, email;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupUsername = (EditText)findViewById(R.id.signupUsername);
        signupPassword = (EditText)findViewById(R.id.signupPassword);
        signupFullname = (EditText)findViewById(R.id.signupFullname);
        signupBirthday = (EditText)findViewById(R.id.signupBirthday);
        signupEmail = (EditText)findViewById(R.id.signupEmail);

        mPostReference = FirebaseDatabase.getInstance().getReference();

        Button login = (Button)findViewById(R.id.signupButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = signupUsername.getText().toString();
                userpw = signupPassword.getText().toString();
                fullname = signupFullname.getText().toString();
                birthday = signupBirthday.getText().toString();
                email = signupEmail.getText().toString();

                int usernameLength = username.length();
                int userpwLength = userpw.length();
                int fullnameLength = fullname.length();
                int birthayLength = birthday.length();
                int emailLength = email.length();

                if(usernameLength == 0 || userpwLength == 0 || fullnameLength == 0
                        || birthayLength == 0 || emailLength == 0){
                    String msg = "Please fill all blanks";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("user_info").child(username);
                    ValueEventListener valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Log.d("TAG", "Users exists!");
                                String msg = "Please use another username";
                                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            } else {
                                //Create user
                                Log.d("TAG", "Users not exists!");
                                postFirebaseDatabase(true);

                                EditText username = (EditText)findViewById(R.id.signupUsername);
                                Intent signupIntent = new Intent(SignUp.this, MainActivity.class);
                                signupIntent.putExtra("Username", username.getText().toString());
                                startActivity(signupIntent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    };
                    userRef.addListenerForSingleValueEvent(valueEventListener);
                }
            }
        });

        getFirebaseDatabase();
    }

    public void postFirebaseDatabase(boolean add){
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        if(add){
            FirebasePost post = new FirebasePost(username, userpw, fullname, birthday, email);
            postValues = post.toMap();
        }
        childUpdates.put("user_info/"+username, postValues);
        mPostReference.updateChildren(childUpdates);
    }

    public void getFirebaseDatabase(){
        DatabaseReference userEmailRef = FirebaseDatabase.getInstance().getReference().child("user_info");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // dataSnapshot.exists()
                if (dataSnapshot.exists()) {
                } else {
                    //Create user
                    Log.d("TAG", "User not exists!");
                    postFirebaseDatabase(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        userEmailRef.addListenerForSingleValueEvent(valueEventListener);
    }
}
