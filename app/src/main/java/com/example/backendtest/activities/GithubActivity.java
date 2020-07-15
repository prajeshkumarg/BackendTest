package com.example.backendtest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.backendtest.JsonPlaceholderApi;
import com.example.backendtest.R;
import com.example.backendtest.activities.ProjectActivity;
import com.example.backendtest.adapters.GithubAdapter;
import com.example.backendtest.models.Github;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubActivity extends AppCompatActivity {

    TextView textViewGithub;
    Button buttonGithub;
    List<Github> githubList=new ArrayList<>();
    RecyclerView recyclerViewGithub ;
    private int backButtonCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGithub=findViewById(R.id.buttonGithub);
        recyclerViewGithub=findViewById(R.id.recyclerviewGithub);
        recyclerViewGithub.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://stc-app-backend.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loadData(retrofit);
        buttonGithub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProjectActivity.class));
            }
        });
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.githubSwipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        loadData(retrofit);
                    }
                }, 2000);
            }
        });
    }

    private void loadData(Retrofit retrofit) {
        githubList=new ArrayList<>();
        JsonPlaceholderApi jsonPlaceholderApi =retrofit.create(JsonPlaceholderApi.class);
        Call <List<Github>> call = jsonPlaceholderApi.getGithub();
        call.enqueue(new Callback<List<Github>>() {
            @Override
            public void onResponse(Call<List<Github>> call, Response<List<Github>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Code "+response.code(),Toast.LENGTH_SHORT).show();
                    return;
                }
                List<Github> githubs =response.body();
                for (Github github : githubs) {
                    String title=github.getTitle();
                    String content = github.getLink();
                    githubList.add(new Github(title,content));
                }
                GithubAdapter githubAdapter=new GithubAdapter(getApplicationContext(), githubList);
                recyclerViewGithub.setAdapter(githubAdapter);

            }

            @Override
            public void onFailure(Call<List<Github>> call, Throwable t) {
                textViewGithub.setText(t.getMessage());


            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backButtonCount >= 1) {
            backButtonCount = 0;
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Snackbar.make(findViewById(android.R.id.content),"Press back again to exit.",Snackbar.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}