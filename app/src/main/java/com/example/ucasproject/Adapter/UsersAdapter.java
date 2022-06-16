package com.example.ucasproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ucasproject.Models.User;
import com.example.ucasproject.R;
import com.example.ucasproject.listener.OnRVItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.InvoiceViewHolder> {

    Context context;
    List<User> invoiceArrayList;
    OnRVItemClickListener listener;

    public UsersAdapter(Context context, List<User> invoiceArrayList, OnRVItemClickListener listener) {
        this.context = context;
        this.invoiceArrayList = invoiceArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invouce, parent, false);
        return new InvoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceViewHolder holder, int position) {
        User users = invoiceArrayList.get(position);
        holder.bind(users);
        holder.invoiceRout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(users, holder.getAdapterPosition());
            }
        });
        holder.invoiceRout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongCLickItem(users, holder.invoiceRout);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return invoiceArrayList.size();
    }

    public void filterList(List<User> users) {
        invoiceArrayList = users;
        notifyDataSetChanged();
    }

    class InvoiceViewHolder extends RecyclerView.ViewHolder {
        LinearLayoutCompat invoiceRout;
        ImageView image_user;
        TextView userName, userSubNumber, userAddress;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            invoiceRout = itemView.findViewById(R.id.invoiceRout);
            image_user = itemView.findViewById(R.id.image_user);
            userName = itemView.findViewById(R.id.userName);
            userSubNumber = itemView.findViewById(R.id.userSubNumber);
            userAddress = itemView.findViewById(R.id.userAddress);

        }

        void bind(User users) {
            Glide.with(context).load(users.getUserImgUrl()).placeholder(R.drawable.mask_group24).circleCrop().into(image_user);
            userName.setText(users.getUserFirstName());
            userSubNumber.setText(users.getUserSubNumber());
            userAddress.setText(users.getUserAddress());
        }
    }
}
