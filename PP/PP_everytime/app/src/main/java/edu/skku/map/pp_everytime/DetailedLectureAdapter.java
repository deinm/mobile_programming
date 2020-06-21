package edu.skku.map.pp_everytime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailedLectureAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private ArrayList<detailedLectureItem> items;

    public DetailedLectureAdapter(Context context, ArrayList<detailedLectureItem> memos) {
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.items = memos;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public detailedLectureItem getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if ( view == null ) {
            view = inflater.inflate(R.layout.detailed_item_layout, viewGroup, false);
        }

        detailedLectureItem item = items.get(i);

        RatingBar detailedRatingBar = (RatingBar)view.findViewById(R.id.detailed_rating);
        TextView english = (TextView)view.findViewById(R.id.detailed_english);
        TextView hw = (TextView)view.findViewById(R.id.detailed_hw);
        TextView team = (TextView)view.findViewById(R.id.detailed_team);
        TextView attend = (TextView)view.findViewById(R.id.detailed_attend);
        TextView exam = (TextView)view.findViewById(R.id.detailed_exam);

        detailedRatingBar.setRating(item.getTotalIdx()+1);
        english.setText(item.getDetailedEnglish());
        hw.setText(item.getDetailedHW());
        team.setText(item.getDetailedTeam());
        attend.setText(item.getDetailedAttendance());
        exam.setText(item.getDetailedExam());

        return view;
    }
}
