package edu.skku.map.week10_class;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost {
    public String token;
    public String userId;
    public String studentId;
    public String name;

    public FirebasePost() {}

    public FirebasePost(String token, String userId, String studentId, String name){
        this.token = token;
        this.userId = userId;
        this.studentId = studentId;
        this.name = name;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();

        result.put("Name", name);
        result.put("StudentID", studentId);
        result.put("Token", token);

        return result;
    }
}
