package com.ananas.socialmediaapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ananas.socialmediaapp.databinding.RecyclerRowBinding;
import com.ananas.socialmediaapp.values.PostValues;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    public PostAdapter(ArrayList<PostValues> postValuesArrayList) {
        this.postValuesArrayList = postValuesArrayList;
    }

    ArrayList<PostValues> postValuesArrayList ;

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding= RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
return new PostHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
holder.recyclerRowBinding.recyclerViewUserEmailTextView.setText(postValuesArrayList.get(position).email);
holder.recyclerRowBinding.recyclerViewCommentTextView.setText(postValuesArrayList.get(position).comment);
        Picasso.get().load(postValuesArrayList.get(position).downloadUrl).into(holder.recyclerRowBinding.recyclerViewContentImageView);
    }

    @Override
    public int getItemCount() {
        return postValuesArrayList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder {
        RecyclerRowBinding recyclerRowBinding;

        public PostHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding=recyclerRowBinding;

        }
    }
}
