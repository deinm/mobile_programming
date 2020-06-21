package com.example.map_pa;

import java.util.HashMap;
import java.util.Map;

public class FirebasePost2 {
    String contents;
    String tags;
    String imagename;
    boolean ispublic;

    public FirebasePost2(){}

    public FirebasePost2(String contents, String tags, String imagename, boolean ispublic){
        this.contents = contents;
        this.tags = tags;
        this.imagename = imagename;
        this.ispublic = ispublic;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("contents", contents);
        result.put("tags", tags);
        result.put("imagename", imagename);
        result.put("ispublic", ispublic);
        return result;
    }
}
