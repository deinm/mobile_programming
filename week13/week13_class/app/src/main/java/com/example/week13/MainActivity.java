package com.example.week13;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    String MAIN_TAGS = "Week13Ex";
    private static String naverAPIUrl = "https://openapi.naver.com/v1/search/blog";
    private static String clientId = "lxRSxWFZOuofEHKbjog9";
    private static String clientSecret = "uWm2afuP_P";
    OkHttpClient client;

    Button button;
    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText2);
        textView = findViewById(R.id.textView);

        // Set OnClickListener to button
        button.setOnClickListener(btnClickListener);
    }

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String keyword = editText.getText().toString();
            if (keyword.length() != 0) {
                Log.d(MAIN_TAGS, keyword);

                // Request Query
                SearchRequest(keyword);
            } else {
                Toast.makeText(getApplicationContext(), "Please type any keyword....", Toast.LENGTH_SHORT);
            }
        }
    };

    private void SearchRequest (String keyword) {
        client = new OkHttpClient();
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        try{
            GET(naverAPIUrl, requestHeaders, keyword);
        } catch (IOException e) {
            Log.d(MAIN_TAGS, e.getMessage());
        }
    }

    private void GET (String url, Map<String, String> header, String keyword) throws IOException {
        // Build url with proper keyword...
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("query", keyword);
        urlBuilder.addQueryParameter("display", String.valueOf(3));
        String reqUrl = urlBuilder.build().toString();

        // In this case, header is needed...
        Headers headerBuild = Headers.of(header);
        Request request = new Request.Builder().url(reqUrl).headers(headerBuild).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(MAIN_TAGS, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String myResponse = response.body().string();

                // This thread will be run on Ui Thread...
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(myResponse);
                    }
                });
            }
        });
    }
}
