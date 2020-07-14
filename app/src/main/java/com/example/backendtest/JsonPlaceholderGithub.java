package com.example.backendtest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceholderGithub {
    @GET("github")
    Call<List<Github>> getGithub();
}
