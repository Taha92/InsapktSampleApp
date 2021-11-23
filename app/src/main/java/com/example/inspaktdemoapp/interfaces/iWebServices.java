package com.example.inspaktdemoapp.interfaces;

import com.example.inspaktdemoapp.models.RepoData;
import com.example.inspaktdemoapp.models.RepoIssuesData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface iWebServices {

    @GET("/orgs/hashicorp/repos")
    Call<ArrayList<RepoData>> getRepos();

    @GET("repos/hashicorp/{repo}/issues")
    Call<ArrayList<RepoIssuesData>> getRepoIssues(@Path(value = "repo", encoded = true) String repo);

}
