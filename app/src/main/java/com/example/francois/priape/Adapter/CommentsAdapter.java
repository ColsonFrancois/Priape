package com.example.francois.priape.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.francois.priape.Model.Comment;
import com.example.francois.priape.databinding.CommentItemBinding;

import java.util.List;

/**
 * Created by Francois on 18/05/2016.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {

    public List<Comment> comments;
    public Context mContext;
    public CommentsAdapter(List<Comment> comments, Context mContext)
    {
        this.comments = comments;
        this.mContext = mContext;
    }



    @Override
    public CommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommentItemBinding binding = CommentItemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new CommentViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(CommentViewHolder holder, final int position) {
        holder.binding.setComment(comments.get(position));

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }


    public void add(Comment comment)
    {
        comments.add(0,comment);
        notifyItemInserted(0);
    }


    public static class CommentViewHolder extends RecyclerView.ViewHolder{
        public CommentItemBinding binding;
    public CommentViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}

}
