package com.example.ucasproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.ucasproject.Adapter.InvoicesAdapter;
import com.example.ucasproject.Models.Issuing;
import com.example.ucasproject.Models.User;
import com.example.ucasproject.listener.OnInvoiceClickListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.remote.WatchChange;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShowAllInvoice extends AppCompatActivity implements OnInvoiceClickListener {
    Toolbar toolbar;
    RecyclerView recyclerView;
    InvoicesAdapter adapter;
    FirebaseFirestore db;
    ArrayList<Issuing> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_invoice);

        db = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.allInvoiceUser);
        recyclerView = findViewById(R.id.recyclerViewInvoices);

        arrayList = new ArrayList<>();

        adapter = new InvoicesAdapter(this, arrayList, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        db.collection("bills").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot ds : list) {
                        Issuing issuing = ds.toObject(Issuing.class);
                        issuing.setId(ds.getId());
                        arrayList.add(issuing);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onClick(Issuing issuing, int position) {
        Toast.makeText(this, issuing.getUserName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongClick(Issuing issuing, CardView layoutCompat) {
        Toast.makeText(this, issuing.getUserName(), Toast.LENGTH_SHORT).show();
    }


}