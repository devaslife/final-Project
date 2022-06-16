package com.example.ucasproject.listener;

import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.ucasproject.Models.User;

public interface OnRVItemClickListener {
    void onItemClicked(User users, int position);

    void onLongCLickItem(User users, LinearLayoutCompat cardView);

}
