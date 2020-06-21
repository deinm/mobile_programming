package edu.skku.map.pp_everytime;

import android.content.Context;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class LectureAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private ArrayList<LectureItem> items;

    public LectureAdapter(Context context, ArrayList<LectureItem> memos) {
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = memos;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public LectureItem getItem(int i) {
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

        LectureItem item = items.get(i);

        TextView tv1 = (TextView)view.findViewById(R.id.subject);
        TextView tv2 = (TextView)view.findViewById(R.id.profname);
        RatingBar ratingBar = (RatingBar)view.findViewById(R.id.rating);

        tv1.setText(item.getCourseName());
        tv2.setText(item.getProfName());
        ratingBar.setRating(item.getTotalScore());

        return view;
    }
}
