package com.example.ucasproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ucasproject.Adapter.UsersAdapter;
import com.example.ucasproject.Models.User;
import com.example.ucasproject.listener.OnRVItemClickListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements OnRVItemClickListener {
    RecyclerView recyclerView;
    UsersAdapter adapter;
    FirebaseFirestore db;
    List<User> arrayList;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recyclerInvoice);
        searchView = findViewById(R.id.searchUsers);

        arrayList = new ArrayList<>();
        adapter = new UsersAdapter(this, arrayList, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchUsers(newText);
                return false;
            }
        });

        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot ds : list) {
                        User users = ds.toObject(User.class);
                        users.setUserId(ds.getId());
                        arrayList.add(users);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void searchUsers(String newText) {
        List<User> users = new ArrayList<>();
        for (User user : arrayList) {
            if (user.getUserFirstName().toLowerCase().contains(newText.toLowerCase()) ||
                    user.getUserSubNumber().toLowerCase().contains(newText.toLowerCase())) {
                    users.add(user);
            }
        }
        adapter.filterList(users);
    }

    @Override
    public void onItemClicked(User users, int position) {
        Intent intent = new Intent(getApplicationContext(), IssuingActivity.class);
        intent.putExtra("data", users);
        startActivity(intent);

//        Toast.makeText(this, users.getUserFirstName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLongCLickItem(User users, LinearLayoutCompat cardView) {
        Intent intent = new Intent(getApplicationContext(),ShowAllInvoice.class);
        startActivity(intent);
    }
}
