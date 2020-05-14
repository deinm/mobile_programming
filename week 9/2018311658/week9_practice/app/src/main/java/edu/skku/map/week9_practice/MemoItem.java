package edu.skku.map.week9_practice;

public class MemoItem {
    private String title;
    private String owner;
    private String content;

    public MemoItem () { }

    public MemoItem(String title, String owner, String content) {
        this.title = title;
        this.owner = owner;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getOwner() {
        return owner;
    }

    public String getContent() {
        return content;
    }
}
