package com.rankhep.minji;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ViewPager pager;
    private TabLayout tab;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new ViewPagerAdapter(this);
        pager = findViewById(R.id.view_pager);
        tab = findViewById(R.id.tab_layout);
        tab.addTab(tab.newTab().setText("Personal"));
        tab.addTab(tab.newTab().setText("Public"));

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tab.setScrollPosition(position, positionOffset, false);
            }

            @Override
            public void onPageSelected(int position) {
                tab.selectTab(tab.getTabAt(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pager.setAdapter(adapter);
    }
}
