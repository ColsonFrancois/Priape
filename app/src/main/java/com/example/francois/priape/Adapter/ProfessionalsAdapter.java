package com.example.francois.priape.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.francois.priape.Model.User;
import com.example.francois.priape.databinding.ProfessionalItemBinding;

import java.util.List;

/**
 * Created by Francois on 02/05/2016.
 *
 */
public class ProfessionalsAdapter extends RecyclerView.Adapter<ProfessionalsAdapter.ProfessionalViewHolder> {

    public List<User> users;
    public Context mContext;
    public ProfessionalsAdapter(List<User> users, Context mContext) {
        this.users = users;
        this.mContext = mContext;
    }

    @Override
    public ProfessionalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProfessionalItemBinding binding = ProfessionalItemBinding.inflate(LayoutInflater.from(mContext),parent,false);
        return new ProfessionalViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ProfessionalViewHolder holder, int position) {
        holder.binding.setUser(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ProfessionalViewHolder extends RecyclerView.ViewHolder{

        public ProfessionalItemBinding binding;

        public ProfessionalViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
        }
    }

}
