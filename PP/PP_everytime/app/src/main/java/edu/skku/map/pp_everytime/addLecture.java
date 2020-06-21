package edu.skku.map.pp_everytime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class addLecture extends AppCompatActivity {

    private DatabaseReference mPostReference;

    EditText courseNumber, courseName, profName;
    RadioGroup englishRadioGroup, hwRadioGroup, teamRadioGroup,
            attendanceRadioGroup, examRadioGroup, totalRateGroup;

    int englishIdx, hwIdx, teamIdx, attendanceIdx, examIdx, totalIdx;

    Button submitButton;
    String schoolID, schoolName, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lecture);

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

        // Firebase Database
        mPostReference = FirebaseDatabase.getInstance().getReference();

        // getIntent
        Intent lectureIntent = getIntent();
        schoolID = lectureIntent.getStringExtra("schoolID");
        schoolName = lectureIntent.getStringExtra("schoolName");

        courseNumber = (EditText)findViewById(R.id.courseNumber);
        courseName= (EditText)findViewById(R.id.courseName);
        profName = (EditText)findViewById(R.id.profName);

        englishRadioGroup = (RadioGroup)findViewById(R.id.englishRadioGroup);
        hwRadioGroup = (RadioGroup)findViewById(R.id.hwRadioGroup);
        teamRadioGroup = (RadioGroup)findViewById(R.id.teamRadioGroup);
        attendanceRadioGroup = (RadioGroup)findViewById(R.id.attendanceRadioGroup);
        examRadioGroup = (RadioGroup)findViewById(R.id.examRadioGroup);
        totalRateGroup = (RadioGroup)findViewById(R.id.totalRateGroup);

        submitButton = (Button)findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get editText text length
                int coursenumLength = courseNumber.getText().length();
                int coursenameLength = courseName.getText().length();
                int profnameLength = profName.getText().length();

                System.out.println(courseNumber.getText());

                // find RadioButtons
                RadioButton englishRadioButton = (RadioButton)findViewById(englishRadioGroup.getCheckedRadioButtonId());
                RadioButton hwRadioButton = (RadioButton)findViewById(hwRadioGroup.getCheckedRadioButtonId());
                RadioButton teamRadioButton = (RadioButton)findViewById(teamRadioGroup.getCheckedRadioButtonId());
                RadioButton attendanceRadioButton = (RadioButton)findViewById(attendanceRadioGroup.getCheckedRadioButtonId());
                RadioButton examRadioButton = (RadioButton)findViewById(examRadioGroup.getCheckedRadioButtonId());
                RadioButton totalRadioButton = (RadioButton)findViewById(totalRateGroup.getCheckedRadioButtonId());

                if(coursenumLength == 0 || coursenameLength == 0 || profnameLength == 0 ||
                        englishRadioButton == null || hwRadioButton == null ||
                        teamRadioButton == null || attendanceRadioButton == null ||
                        examRadioButton == null || totalRadioButton == null){
                    Toast myToast = Toast.makeText(getApplicationContext(),"Please fill in the blank.", Toast.LENGTH_SHORT);
                    myToast.show();
                    return;
                }

                // get English Radio Button value
                englishIdx = englishRadioGroup.indexOfChild(englishRadioButton);
                String englishText = englishRadioButton.getText().toString();
                System.out.println("ENGLISH idx"+englishIdx+", "+englishText);

                // get HW Radio Button value
                hwIdx = hwRadioGroup.indexOfChild(hwRadioButton);
                String hwText = hwRadioButton.getText().toString();
                System.out.println("HW idx"+hwIdx+", "+hwText);

                // get HW Radio Button value
                teamIdx = teamRadioGroup.indexOfChild(teamRadioButton);
                String teamText = teamRadioButton.getText().toString();
                System.out.println("TEAM idx"+teamIdx+", "+teamText);

                // get attendance Radio Button value
                attendanceIdx = attendanceRadioGroup.indexOfChild(attendanceRadioButton);
                String attendanceText = attendanceRadioButton.getText().toString();
                System.out.println("attendance idx"+attendanceIdx+", "+attendanceText);

                // get exam Radio Button value
                examIdx = examRadioGroup.indexOfChild(examRadioButton);
                String examText = examRadioButton.getText().toString();
                System.out.println("EXAM idx"+examIdx+", "+examText);

                // get totalRate Radio Button value
                totalIdx = totalRateGroup.indexOfChild(totalRadioButton);
                String totalText = totalRadioButton.getText().toString();
                System.out.println("TOTAL idx"+totalIdx+", "+totalText);

                // save in database
                postFirebaseDatabase(true);

                // intent
                Intent LectureIntent = new Intent(getApplicationContext(), LectureActivity.class);
                LectureIntent.putExtra("schoolName", schoolName);
                LectureIntent.putExtra("schoolID", schoolID);
                startActivity(LectureIntent);
            }
        });
    }

    public void postFirebaseDatabase(boolean add){
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        if(add){
            FirebasePost post = new FirebasePost(courseNumber.getText().toString(),
                    courseName.getText().toString(), profName.getText().toString(),
                    englishIdx, hwIdx, teamIdx, attendanceIdx, examIdx, totalIdx);
            postValues = post.toMap();
        }
        String uniqueId = UUID.randomUUID().toString();
        childUpdates.put("Lecture/"+courseNumber.getText().toString().substring(0,3)+
                        "/"+courseNumber.getText().toString()+"_"+userId, postValues);
        mPostReference.updateChildren(childUpdates);
    }
}
