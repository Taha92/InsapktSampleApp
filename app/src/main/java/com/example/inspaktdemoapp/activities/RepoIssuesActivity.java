package com.example.inspaktdemoapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inspaktdemoapp.R;
import com.example.inspaktdemoapp.adapters.RepoIssuesCardViewAdapter;
import com.example.inspaktdemoapp.http.RestService;
import com.example.inspaktdemoapp.models.RepoData;
import com.example.inspaktdemoapp.models.RepoIssuesData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoIssuesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String repoName;
    private String issuesCount;
    RecyclerView recyclerView;
    RepoIssuesCardViewAdapter repoIssuesCardViewAdapter;
    TextView txtViewIssuesCount;
    TextView txtViewNoData;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_issues);


        try {
            // Initializing REST services
            RestService.initializeSyncHttpService();
            RestService.initializeHttpService();

            initialize();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.text_loading));
            txtViewIssuesCount = findViewById(R.id.txtViewIssuesCount);
            txtViewNoData = findViewById(R.id.txtViewNoData);
            toolbar = findViewById(R.id.toolBar);
            setSupportActionBar(toolbar);
            recyclerView = findViewById(R.id.recycler_view);

            RepoData repoData = (RepoData) getIntent().getSerializableExtra("repoDataObject");
            repoName = repoData.getName();

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(repoName);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            getRepoIssues(repoName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // API call for repository issues
    private void getRepoIssues(String repo) {

        Call<ArrayList<RepoIssuesData>> getRepoIssuesResponseCall = RestService.getHttpService().getRepoIssues(repo);
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        getRepoIssuesResponseCall.enqueue(new Callback<ArrayList<RepoIssuesData>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<RepoIssuesData>> call, @NonNull Response<ArrayList<RepoIssuesData>> response) {
                try {
                    if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();

                    if (response.body() != null) {
                        issuesCount = String.valueOf(response.body().size());
                        txtViewIssuesCount.setText("Total Count: " + issuesCount);

                        ArrayList<RepoIssuesData> repoIssuesData = response.body();

                        repoIssuesCardViewAdapter = new RepoIssuesCardViewAdapter(getApplicationContext(), repoIssuesData);

                        LinearLayoutManager manager = new LinearLayoutManager(RepoIssuesActivity.this);
                        recyclerView.setLayoutManager(manager);

                        recyclerView.setAdapter(repoIssuesCardViewAdapter);

                    } else {
                        txtViewNoData.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<RepoIssuesData>> call, @NonNull Throwable t) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
