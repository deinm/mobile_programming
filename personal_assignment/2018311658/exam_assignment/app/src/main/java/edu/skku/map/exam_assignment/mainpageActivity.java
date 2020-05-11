package edu.skku.map.exam_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class mainpageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    private ViewPager pager;
    private TabLayout tab;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        this.InitializeLayout();

        ImageView addpost = (ImageView)findViewById(R.id.addpost);
        addpost.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // explicit Intent
                Intent intent = new Intent(mainpageActivity.this, addpostActivity.class);
                startActivity(intent);
            }
        });

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

    public void InitializeLayout()
    {
        //toolBar를 통해 App Bar 생성
        Toolbar tb = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer);

        navigationView.setNavigationItemSelectedListener(this);

        drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, tb, R.string.app_name, R.string.app_name);

        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ // 왼쪽 상단 버튼 눌렀을 때
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:{
                break;
            }
            case R.id.item2:
                break;
            case R.id.item3:
                break;
        }
        return false;
    }
}
