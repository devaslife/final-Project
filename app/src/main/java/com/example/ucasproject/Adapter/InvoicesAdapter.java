package com.example.ucasproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ucasproject.Models.Issuing;
import com.example.ucasproject.Models.User;
import com.example.ucasproject.R;
import com.example.ucasproject.listener.OnInvoiceClickListener;

import java.util.ArrayList;
import java.util.Date;

public class InvoicesAdapter extends RecyclerView.Adapter<InvoicesAdapter.InvoicesViewHolder> {
    Context context;
    ArrayList<Issuing> list;
    OnInvoiceClickListener listener;

    public InvoicesAdapter(Context context, ArrayList<Issuing> list, OnInvoiceClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public InvoicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_all_invouce, parent, false);
        return new InvoicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoicesViewHolder holder, int position) {
        Issuing issuing = list.get(position);
        holder.bind(issuing);

        holder.allInvoiceRout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(issuing, holder.getAdapterPosition());
            }
        });
        holder.allInvoiceRout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(issuing, holder.allInvoiceRout);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class InvoicesViewHolder extends RecyclerView.ViewHolder {
        CardView allInvoiceRout;
        TextView invoiceUserName, invoiceUserNo, dateInvoice, invoiceBalance;

        public InvoicesViewHolder(@NonNull View itemView) {
            super(itemView);
            allInvoiceRout = itemView.findViewById(R.id.allInvoiceRout);
            invoiceUserName = itemView.findViewById(R.id.textView5);
            invoiceUserNo = itemView.findViewById(R.id.text_2);
            dateInvoice = itemView.findViewById(R.id.text_3);
            invoiceBalance = itemView.findViewById(R.id.text_4);
        }

        void bind(Issuing issuing) {
            invoiceUserName.setText(issuing.getUserName());
            invoiceUserNo.setText(issuing.getUserNo());
            dateInvoice.setText(issuing.getDate());
            invoiceBalance.setText(String.valueOf(issuing.getPrice()));
        }
    }
}
