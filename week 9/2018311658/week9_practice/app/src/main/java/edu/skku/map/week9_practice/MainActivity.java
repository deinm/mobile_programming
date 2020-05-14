package edu.skku.map.week9_practice;

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
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mPostReference;
    String content="", owner="", title="";
    EditText contentET, ownerET, titleET;
    Button btn;

    ListView listView;
    ArrayList<MemoItem> memos;
    MemoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memos = new ArrayList<MemoItem>();

        contentET = (EditText)findViewById(R.id.contentET);
        ownerET = (EditText)findViewById(R.id.ownerET);
        titleET = (EditText)findViewById(R.id.titleET);

        btn = (Button)findViewById(R.id.button);
        listView = (ListView)findViewById(R.id.datalist);

        //access DB
        mPostReference = FirebaseDatabase.getInstance().getReference();

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                content = contentET.getText().toString();
                owner = ownerET.getText().toString();
                title = titleET.getText().toString();

                if((content.length() * owner.length() * title.length()) == 0){
                    Toast.makeText(MainActivity.this, "Data is missing", Toast.LENGTH_SHORT).show();
                } else{
                    // send information to firebase
                    postFirebaseDatabase(true);
                }
            }
        });

        adapter = new MemoAdapter(this, memos);
        listView.setAdapter(adapter);

        // get data from firebase
        getFirebaseDatabase();
    }

    public void getFirebaseDatabase(){
        final ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("onDataChange", "Data is Updated");
                memos.clear();

                Log.d("LEGTH", String.valueOf(dataSnapshot.getChildrenCount()));

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    String key = postSnapshot.getKey();
                    FirebasePost get = postSnapshot.getValue(FirebasePost.class);
                    MemoItem memo = new MemoItem(get.title, get.owner, get.content);

                    memos.add(memo);

                    Log.d("getFirebaseDatabase", "key: " + key);
                }
//                adapter.clear();
                adapter.addAll(memos);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        // add listener
        mPostReference.child("memo_list").addValueEventListener(postListener);
    }

    public void postFirebaseDatabase(boolean add){
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        if(add){
            FirebasePost post = new FirebasePost(content, owner, title);
            postValues = post.toMap();
        }

        String uuid = UUID.randomUUID().toString();
        childUpdates.put("/memo_list/"+uuid, postValues);
        mPostReference.updateChildren(childUpdates);
        clearET();
    }

    public void clearET(){
        contentET.setText("");
        ownerET.setText("");
        titleET.setText("");
        content="";
        owner="";
        title="";
    }
}
