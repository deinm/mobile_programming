package com.example.map_pa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MemoAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private ArrayList<MemoItem> items;

    public MemoAdapter (Context context, ArrayList<MemoItem> memos) {
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = memos;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public MemoItem getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if ( view == null ) {
            view = inflater.inflate(R.layout.item_layout, viewGroup, false);
        }

        MemoItem item = items.get(i);

        TextView tv1 = (TextView)view.findViewById(R.id.userName);
        TextView tv2 = (TextView)view.findViewById(R.id.postedContent);
        TextView tv3 = (TextView)view.findViewById(R.id.postedContent);

        tv1.setText(item.getUsername());
        tv2.setText(item.getContent());
        tv3.setText(item.getHashtag());

        return view;
    }

    public void addAll(ArrayList<MemoItem> memos) {
        this.items = memos;
    }
}
