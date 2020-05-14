package edu.skku.map.week9_class;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mPostReference;
    String name="", gender="";
    long ID = 0, age = 0;
    EditText ageET, nameET, genderET, idET;
    Button btn;
    ListView listView;

    ArrayList<String> data;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = new ArrayList<String>();

        ageET = (EditText)findViewById(R.id.ageEdit);
        nameET = (EditText)findViewById(R.id.nameEdit);
        genderET = (EditText)findViewById(R.id.genderEdit);
        idET = (EditText)findViewById(R.id.idEdit);

        btn = (Button)findViewById(R.id.button);
        listView = (ListView) findViewById(R.id.datalist);

        //access DB
        mPostReference = FirebaseDatabase.getInstance().getReference();

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                name = nameET.getText().toString();
                gender = genderET.getText().toString();
                String aget = ageET.getText().toString();
                String idet = idET.getText().toString();

                if((idet.length() * name.length() * gender.length() * aget.length()) == 0){
                    Toast.makeText(MainActivity.this, "Data is missing", Toast.LENGTH_SHORT).show();
                } else{
                    age = Long.parseLong(aget);
                    ID = Long.parseLong(idet);
                    // send information to firebase
                    postFirebaseDatabase(true);
                }
            }
        });
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);
        // get data from firebase
        getFirebaseDatabase();
    }

    public void getFirebaseDatabase(){
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("onDataChange", "Data is Updated");
                data.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    String key = postSnapshot.getKey();
                    FirebasePost get = postSnapshot.getValue(FirebasePost.class);
                    String[] info = {String.valueOf(get.id), get.name, String.valueOf(get.age), get.gender};
                    String result = info[0] + " : " + info[1] + "(" + info[3] + ", " + info[2] + ")";

                    data.add(result);
                    Log.d("getFirebaseDatabase", "key: " + key);
                    Log.d("getFirebaseDatabase", "info: "+ info[0] + info[1] + info[2] + info[3]);
                }
                arrayAdapter.clear();
                arrayAdapter.addAll(data);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        // add listener
        mPostReference.child("id_list").addValueEventListener(postListener);
    }

    public void postFirebaseDatabase(boolean add){
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        if(add){
            FirebasePost post = new FirebasePost(ID, name, age, gender);
            postValues = post.toMap();
        }

        childUpdates.put("/id_list/"+ID, postValues);
        mPostReference.updateChildren(childUpdates);
        clearET();
    }

    public void clearET(){
        genderET.setText("");
        ageET.setText("");
        nameET.setText("");
        idET.setText("");
        gender = "";
        age = 0;
        name = "";
        ID = 0;
    }
}
