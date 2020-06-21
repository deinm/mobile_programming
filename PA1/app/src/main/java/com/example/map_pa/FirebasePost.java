package com.example.map_pa;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost {
    String userName;
    String password;
    String fullName;
    String birthday;
    String email;

    public FirebasePost(){}

    public FirebasePost(String userName, String password, String fullName, String birthday, String email){
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.birthday = birthday;
        this.email = email;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", userName);
        result.put("password", password);
        result.put("fullname", fullName);
        result.put("birthday", birthday);
        result.put("email", email);
        return result;
    }
}
