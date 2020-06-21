package edu.skku.map.pp_everytime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailedLectureActivity extends AppCompatActivity {

    TextView detailed_courseName;
    String detailed_courseText;
    String courseKey, courseSchool;

    String schoolID, schoolName;

    ListView listView;
    ArrayList<detailedLectureItem> detailedItems;
    DetailedLectureAdapter adapter;
    FloatingActionButton addLecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_lecture);

        Intent secondIntent = getIntent();
        detailed_courseText = secondIntent.getStringExtra("coursename");
        schoolName = secondIntent.getStringExtra("schoolName");
        schoolID = secondIntent.getStringExtra("schoolID");

        int length = detailed_courseText.length();
        courseKey = detailed_courseText.substring(length-8,length-1);
        courseSchool = courseKey.substring(0,3);

        detailed_courseName = (TextView)findViewById(R.id.detailed_courseName);
        detailed_courseName.setText(detailed_courseText);


        listView = (ListView)findViewById(R.id.detailed_listView);
        detailedItems = new ArrayList<detailedLectureItem>();

        DatabaseReference lectureRef = FirebaseDatabase.getInstance().getReference().child("Lecture").child(courseSchool);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.toString());
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    if (key.contains(courseKey)) {
                        System.out.println("KEY " + key);
                        int totalidx = Integer.parseInt(ds.child("totalidx").getValue().toString());
                        int englishidx = Integer.parseInt(ds.child("englishidx").getValue().toString());
                        int hwidx = Integer.parseInt(ds.child("hwidx").getValue().toString());
                        int teamidx = Integer.parseInt(ds.child("teamidx").getValue().toString());
                        int attendanceidx = Integer.parseInt(ds.child("attendanceidx").getValue().toString());
                        int examidx = Integer.parseInt(ds.child("examidx").getValue().toString());

                        detailedLectureItem i1 = new detailedLectureItem(englishidx, hwidx, teamidx,
                                attendanceidx, examidx, totalidx);
                        detailedItems.add(i1);
                    }
                }

                adapter = new DetailedLectureAdapter(getApplicationContext(), detailedItems);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        lectureRef.addListenerForSingleValueEvent(valueEventListener);

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
    }
}
