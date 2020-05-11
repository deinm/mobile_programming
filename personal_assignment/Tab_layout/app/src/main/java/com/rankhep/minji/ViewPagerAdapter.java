package com.rankhep.minji;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {
    Context context;

    ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (View) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v;
        switch (position) {
            case 0:
                v = LayoutInflater.from(context).inflate(R.layout.fragment_personal, container,false);
                break;
            case 1:
                v = LayoutInflater.from(context).inflate(R.layout.fragment_public, container,false);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


}
