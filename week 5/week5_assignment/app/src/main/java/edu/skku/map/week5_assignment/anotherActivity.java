package edu.skku.map.week5_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class anotherActivity extends AppCompatActivity {
    public String keyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        Intent intent = getIntent();
        String text = intent.getStringExtra("name");
        keyword = intent.getStringExtra("keyword");

        TextView textView = findViewById(R.id.textView);
        textView.setText("Hi, "+text);


        Button search = findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchWeb(keyword);

                // searching method 2
//                Uri url = Uri.parse("https://www.google.com/search?q="+keyword);
//
//                // implicit Intent
//                Intent intent2 = new Intent(Intent.ACTION_VIEW, url);
//                if(intent2.resolveActivity(getPackageManager())!=null) startActivity(intent2);
            }
        });
    }

    public void searchWeb(String query){
        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY, query);
        if(intent.resolveActivity(getPackageManager())!=null) startActivity(intent);
    }
}
