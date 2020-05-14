package edu.skku.map.week9_practice;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost {
    public String content;
    public String owner;
    public String title;

    public FirebasePost(){

    }

    public FirebasePost(String content, String owner, String title){
        this.content = content;
        this.owner = owner;
        this.title = title;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("content", content);
        result.put("owner", owner);
        result.put("title", title);

        return result;
    }
}
