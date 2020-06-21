package com.example.customlistviewexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<MemoItem> memos;

    MemoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

        memos = new ArrayList<MemoItem>();

        MemoItem i1 = new MemoItem("aaa", "Lee", "hello");
        memos.add(i1);

        MemoItem i2 = new MemoItem("bbb", "Kim", "world");
        memos.add(i2);

        MemoItem i3 = new MemoItem("ccc", "Kang", "moblie app");
        memos.add(i3);

        adapter = new MemoAdapter(this, memos);

        listView.setAdapter(adapter);
    }
}
