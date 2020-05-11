package edu.skku.map.exam_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;

public class addpostActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);

        this.InitializeLayout();


        Button post = (Button)findViewById(R.id.button);
        post.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // explicit Intent
                Intent intent = new Intent(addpostActivity.this, mainpageActivity.class);
                // (key, value)
                // intent.putExtra("myData", "Hi, 2nd Activity");
                startActivity(intent);
            }
        });
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
