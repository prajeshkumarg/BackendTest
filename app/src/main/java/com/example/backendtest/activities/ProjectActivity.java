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
import android.widget.Toast;

import com.example.backendtest.JsonPlaceholderApi;
import com.example.backendtest.R;
import com.example.backendtest.adapters.ProjectAdapter;
import com.example.backendtest.models.Projects;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjectActivity extends AppCompatActivity {

    Button buttonProject;
    RecyclerView recyclerViewProject;
    List<Projects> projectsList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        recyclerViewProject=findViewById(R.id.recyclerviewProject);
        recyclerViewProject.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://stc-app-backend.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        loadData(retrofit);
        buttonProject=findViewById(R.id.buttonProject);
        buttonProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GithubActivity.class));
            }
        });
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.projSwipeRefresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }

    private void loadData(Retrofit retrofit) {
        projectsList=new ArrayList<>();
        JsonPlaceholderApi jsonPlaceholderapi=retrofit.create(JsonPlaceholderApi.class);
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

                    String title = projects1.getTitle();
                    String desc = projects1.getDesc();
                    List <String> contri = projects1.getContributors();
                    String link= projects1.getLink();
                    projectsList.add(new Projects(title,contri,desc,link));

                }
                ProjectAdapter projectAdapter=new ProjectAdapter(getApplicationContext(),projectsList);
                recyclerViewProject.setAdapter(projectAdapter);
            }

            @Override
            public void onFailure(Call<List<Projects>> call, Throwable t) {

            }
        });
    }
}