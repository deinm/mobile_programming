package edu.skku.map.pp_everytime;

import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost {
    String courseNumber;
    String courseName;
    String profName;
    int englishIdx;
    int hwIdx;
    int teamIdx;
    int attendanceIdx;
    int examIdx;
    int totalIdx;

    public FirebasePost(){}

    public FirebasePost(String courseNumber, String courseName, String profName, int englishIdx,
                        int hwIdx, int teamIdx, int attendanceIdx, int examIdx, int totalIdx){
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.profName = profName;
        this.englishIdx = englishIdx;
        this.hwIdx = hwIdx;
        this.teamIdx = teamIdx;
        this.attendanceIdx = attendanceIdx;
        this.examIdx = examIdx;
        this.totalIdx = totalIdx;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("coursenumber", courseNumber);
        result.put("coursename", courseName);
        result.put("profname", profName);
        result.put("englishidx", englishIdx);
        result.put("hwidx", hwIdx);
        result.put("teamidx", teamIdx);
        result.put("attendanceidx", attendanceIdx);
        result.put("examidx", examIdx);
        result.put("totalidx", totalIdx);
        return result;
    }
}
