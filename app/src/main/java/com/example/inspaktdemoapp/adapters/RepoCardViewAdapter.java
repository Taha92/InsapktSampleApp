package com.example.inspaktdemoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inspaktdemoapp.R;
import com.example.inspaktdemoapp.models.RepoData;

import java.util.ArrayList;

public class RepoCardViewAdapter extends RecyclerView.Adapter<RepoCardViewAdapter.MyViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<RepoData> repoDataArray;
    private final OnItemClickListener onItemClickListener;


    public RepoCardViewAdapter(@NonNull Context context, ArrayList<RepoData> repoDataArrayList, OnItemClickListener onItemClickListener1) {
        inflater = LayoutInflater.from(context);
        repoDataArray = repoDataArrayList;
        onItemClickListener = onItemClickListener1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_item_repo, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final RepoData repoData = repoDataArray.get(holder.getAdapterPosition());

        holder.txtViewRepoName.setText(repoData.getName());
        holder.txtViewRepoFullName.setText(repoData.getFull_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.OnItemClickListener(repoData, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return repoDataArray.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView txtViewRepoName, txtViewRepoFullName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.txtViewRepoName = itemView.findViewById(R.id.txtViewRepoName);
            this.txtViewRepoFullName = itemView.findViewById(R.id.txtViewRepoFullName);

        }
    }

    public interface OnItemClickListener {
        void OnItemClickListener(RepoData repoData, int position);
    }
}
