package com.example.backendtest;

import com.example.backendtest.models.Github;
import com.example.backendtest.models.Projects;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderApi {

    @GET("github")
    Call<List<Github>> getGithub();

    @GET("projects")
    Call<List<Projects>> getProjects();
}
