package com.example.backendtest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderProjects {
    @GET("projects")
    Call<List<Projects>> getProjects();
}
