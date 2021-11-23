package com.example.inspaktdemoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inspaktdemoapp.R;
import com.example.inspaktdemoapp.models.RepoIssuesData;

import java.util.ArrayList;

public class RepoIssuesCardViewAdapter extends RecyclerView.Adapter<RepoIssuesCardViewAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<RepoIssuesData> repoIssuesDataArray;

    public RepoIssuesCardViewAdapter(@NonNull Context context, ArrayList<RepoIssuesData> repoDataArrayList) {
        inflater = LayoutInflater.from(context);
        repoIssuesDataArray = repoDataArrayList;
    }

    @NonNull
    @Override
    public RepoIssuesCardViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_item_repo_issues, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoIssuesCardViewAdapter.MyViewHolder holder, int position) {
        final RepoIssuesData repoIssuesData = repoIssuesDataArray.get(holder.getAdapterPosition());

        holder.txtViewIssueTitle.setText(repoIssuesData.getTitle());
        holder.txtViewIssueState.setText(repoIssuesData.getState());
    }

    @Override
    public int getItemCount() {
        return repoIssuesDataArray.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtViewIssueTitle, txtViewIssueState;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtViewIssueTitle = itemView.findViewById(R.id.txtViewIssueTitle);
            this.txtViewIssueState = itemView.findViewById(R.id.txtViewIssueState);

        }
    }
}
