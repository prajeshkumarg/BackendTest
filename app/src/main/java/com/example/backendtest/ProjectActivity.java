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

public class ProjectActivity extends AppCompatActivity {
    TextView textViewProject;
    Button buttonProject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        textViewProject= findViewById(R.id.textviewProject);
        buttonProject=findViewById(R.id.buttonProject);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://stc-app-backend.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderProjects jsonPlaceholderapi=retrofit.create(JsonPlaceHolderProjects.class);
        Call<List<Projects>> call =jsonPlaceholderapi.getProjects();
        call.enqueue(new Callback<List<Projects>>() {
            @Override
            public void onResponse(Call<List<Projects>> call, Response<List<Projects>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Code "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Projects> projects=response.body();
                for(Projects projects1:projects){

                    String content = "";
                    content += "Title: " + projects1.getTitle()+"\n";
                    content += "Contributors: " + projects1.getContributors()+"\n";
                    content += "Description: " + projects1.getDesc()+"\n";
                    content += "Link: " + projects1.getLink()+"\n";
                    textViewProject.append(content+ "\n");

                }
            }

            @Override
            public void onFailure(Call<List<Projects>> call, Throwable t) {
                textViewProject.setText(t.getMessage());
            }
        });
        buttonProject=findViewById(R.id.buttonProject);
        buttonProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GithubActivity.class));
            }
        });
    }
}