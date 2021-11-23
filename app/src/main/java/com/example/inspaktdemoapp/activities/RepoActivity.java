package com.example.inspaktdemoapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inspaktdemoapp.R;
import com.example.inspaktdemoapp.adapters.RepoCardViewAdapter;
import com.example.inspaktdemoapp.http.RestService;
import com.example.inspaktdemoapp.models.RepoData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RepoCardViewAdapter repoCardViewAdapter;
    TextView txtViewNoData;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Initializing REST services
            RestService.initializeSyncHttpService();
            RestService.initializeHttpService();

            initialize();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initialize(){
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.text_loading));
            txtViewNoData = findViewById(R.id.txtViewNoData);
            recyclerView = findViewById(R.id.recycler_view);

            ViewCompat.setNestedScrollingEnabled(recyclerView, false);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                    DividerItemDecoration.HORIZONTAL);
            recyclerView.addItemDecoration(dividerItemDecoration);

            getRepos();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // API call for repositories
    private void getRepos() {

        Call<ArrayList<RepoData>> getRepoResponseCall = RestService.getHttpService().getRepos();
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        getRepoResponseCall.enqueue(new Callback<ArrayList<RepoData>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<RepoData>> call, @NonNull Response<ArrayList<RepoData>> response) {

                try {
                    if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();

                    if (response.body() != null) {
                        ArrayList<RepoData> repoData = response.body();

                        repoCardViewAdapter = new RepoCardViewAdapter(getApplicationContext(), repoData, new RepoCardViewAdapter.OnItemClickListener() {
                            @Override
                            public void OnItemClickListener(RepoData repoData, int position) {
                                Intent intent = new Intent(RepoActivity.this, RepoIssuesActivity.class);
                                intent.putExtra("repoDataObject", repoData);
                                RepoActivity.this.startActivity(intent);
                            }
                        });

                        LinearLayoutManager manager = new LinearLayoutManager(RepoActivity.this);
                        recyclerView.setLayoutManager(manager);

                        recyclerView.setAdapter(repoCardViewAdapter);

                    } else {
                        txtViewNoData.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<RepoData>> call, @NonNull Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
