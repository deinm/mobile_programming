package com.example.map_pa;

public class MemoItem {
    private String username;
    private String content;
    private String hashtag;
    private String imageID;

    public MemoItem () { }

    public MemoItem(String username, String content, String hashtag, String imageID) {
        this.username = username;
        this.content = content;
        this.hashtag = hashtag;
        this.imageID = imageID;
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public String getHashtag() {
        return hashtag;
    }

    public String getImageID(){ return imageID; }
}
