package edu.skku.map.week9_practice;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
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

        TextView tv1 = (TextView)view.findViewById(R.id.title_tv);
        TextView tv2 = (TextView)view.findViewById(R.id.owner_tv);
        TextView tv3 = (TextView)view.findViewById(R.id.contents_tv);

        tv1.setText(item.getTitle());
        tv2.setText(item.getOwner());
        tv3.setText(item.getContent());

        return view;
    }

    public void clear() {
        items.clear();
        this.notifyDataSetChanged();
    }

    public void addAll(ArrayList<MemoItem> memos) {
        this.items = memos;
    }

}
