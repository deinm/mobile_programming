package edu.skku.map.pp_everytime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.usermgmt.response.model.Profile;
import com.kakao.usermgmt.response.model.UserAccount;
import com.kakao.util.OptionalBoolean;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LectureActivity extends AppCompatActivity {

    TextView schoolInfo;
    ListView listView;
    FloatingActionButton addLecture;
    String schoolID, schoolName, userId;

    Button back;

    // Custom Adapter
    ArrayList<LectureItem> lectureItems;
    LectureAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);

        // Kakao

        UserManagement.getInstance().me(new MeV2ResponseCallback() {
            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("KAKAO API", "세션이 닫혀 있음: "+ errorResult);
            }

            @Override
            public void onSuccess(MeV2Response result) {
                Log.i("KAKAO API", "사용자 아이디: " + result.getId());
                userId = String.valueOf(result.getId());
            }
        });

        Intent secondIntent = getIntent();
        schoolID = secondIntent.getStringExtra("schoolID");
        schoolName = secondIntent.getStringExtra("schoolName");

        schoolInfo = (TextView)findViewById(R.id.schoolInfo);
//        schoolInfo.setText("This is the page of " + schoolName + "(" + schoolID + ") major!");
        schoolInfo.setText(schoolName + "(" + schoolID + ")");

        addLecture = (FloatingActionButton)findViewById(R.id.addLecture);
        addLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addLectureIntent = new Intent(getApplicationContext(), addLecture.class);
                addLectureIntent.putExtra("schoolName", schoolName);
                addLectureIntent.putExtra("schoolID", schoolID);
                startActivity(addLectureIntent);
            }
        });

        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent majorIntent = new Intent(getApplicationContext(), MajorActivity.class);
                startActivity(majorIntent);
            }
        });

        listView = (ListView)findViewById(R.id.listView);
        lectureItems = new ArrayList<LectureItem>();

        final HashMap<String, ArrayList<Integer>> lectureScoreMap = new HashMap<String, ArrayList<Integer>>();
        DatabaseReference lectureRef = FirebaseDatabase.getInstance().getReference().child("Lecture").child(schoolID);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.toString());
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    final String value = ds.child("totalidx").getValue().toString();
                    final String professor = ds.child("profname").getValue().toString();
                    final String coursename = ds.child("coursename").getValue().toString();

                    System.out.println("value1 : "+value);
                    System.out.println("professor : "+professor);

                    String lectureID = key.split("_")[0];
//                    String posterName = key.split("_public_")[0];
                    String searchKey = coursename+"("+lectureID+")_"+professor;
                    if(lectureScoreMap.containsKey(searchKey)){
                        ArrayList<Integer> scoreArray = lectureScoreMap.get(searchKey);
                        final Integer score = scoreArray.get(0);
                        final Integer cnt = scoreArray.get(1);
                        lectureScoreMap.put(coursename+"("+lectureID+")_"+professor, new ArrayList<Integer>() {{ add(Integer.parseInt(value) + 1 + score); add(cnt+1); }} );
                        System.out.println("after stars"+(Integer.parseInt(value) + 1 + score));
                    }
                    else{
                        lectureScoreMap.put(coursename+"("+lectureID+")_"+professor, new ArrayList<Integer>() {{ add(Integer.parseInt(value) + 1); add(1); }} );
                        System.out.println("stars"+(Integer.parseInt(value) + 1));
                    }
                }

                // Calculate total score of the subject
//                final ArrayList<Integer> finalScoreList = new ArrayList<>();
                for( String key : lectureScoreMap.keySet() ){
                    String lec = key.split("_")[0];
                    String prof = key.split("_")[1];
                    System.out.println("score"+ lectureScoreMap.get(key).get(0) + lectureScoreMap.get(key).get(1));
                    LectureItem i1 = new LectureItem(lec, prof,
                            lectureScoreMap.get(key).get(0) / lectureScoreMap.get(key).get(1));
                    lectureItems.add(i1);
                }

                adapter = new LectureAdapter(getApplicationContext(), lectureItems);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Object listItem = listView.getItemAtPosition(position);
                        TextView textView = (TextView) view.findViewById(R.id.subject);
                        String coursename = textView.getText().toString();

                        Intent detailedLectureIntent = new Intent(getApplicationContext(), DetailedLectureActivity.class);
                        detailedLectureIntent.putExtra("coursename", coursename);
                        detailedLectureIntent.putExtra("schoolName", schoolName);
                        detailedLectureIntent.putExtra("schoolID", schoolID);
                        startActivity(detailedLectureIntent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        lectureRef.addListenerForSingleValueEvent(valueEventListener);
    }
}
