package edu.skku.map.week8_practice;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.textView);
        editText = (EditText)findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();

                String moviename = editText.getText().toString();

                // URL = /api/users?page=n
                HttpUrl.Builder urlBuilder = HttpUrl.parse("http://www.omdbapi.com/?").newBuilder();
                urlBuilder.addQueryParameter("t", moviename);
                urlBuilder.addQueryParameter("apikey", "b5e2e302");

                String url = urlBuilder.build().toString();

                Request req = new Request.Builder().url(url).build();

                client.newCall(req).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        // body에 data 존재
                        final String myResponse = response.body().string();

                        Gson gson = new GsonBuilder().create();
                        final DataModel data = gson.fromJson(myResponse, DataModel.class);

                        // xml을 변경하기 위함
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String infotext = "Title: " + data.getTitle();
                                infotext += "\nYear: " + data.getYear();
                                infotext += "\nReleased Date: " + data.getReleased();
                                infotext += "\nRuntime: " + data.getRuntime();
                                infotext += "\nGenre: " + data.getGenre();
                                infotext += "\nIMDB Rates: " + data.getImdbRating();
                                infotext += "\nMetascore: " + data.getMetascore();

                                textView.setText(infotext);
                            }
                        });
                    }
                });
            }
        });
    }
}
