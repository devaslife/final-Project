package com.example.ucasproject.listener;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import com.example.ucasproject.Models.Issuing;

public interface OnInvoiceClickListener {
    void onClick(Issuing issuing , int position);

    void onLongClick(Issuing issuing , CardView layoutCompat);
}
