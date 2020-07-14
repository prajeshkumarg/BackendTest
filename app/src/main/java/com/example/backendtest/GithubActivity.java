package com.example.backendtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubActivity extends AppCompatActivity {

    TextView textViewGithub;
    Button buttonGithub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewGithub=findViewById(R.id.textviewGithub);
        buttonGithub=findViewById(R.id.buttonGithub);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://stc-app-backend.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderGithub jsonPlaceholderGithub =retrofit.create(JsonPlaceholderGithub.class);
        Call <List<Github>> call = jsonPlaceholderGithub.getGithub();
        call.enqueue(new Callback<List<Github>>() {
            @Override
            public void onResponse(Call<List<Github>> call, Response<List<Github>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Code "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Github> githubs =response.body();
                for (Github github : githubs) {
                    String content = "";
                    content += "Title: " + github.getTitle() + "\n";
                    content += "Link: " + github.getLink() + "\n";
                    textViewGithub.append(content+"\n");

                }
            }

            @Override
            public void onFailure(Call<List<Github>> call, Throwable t) {
                textViewGithub.setText(t.getMessage());


            }
        });
        buttonGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ProjectActivity.class));
            }
        });
    }
}