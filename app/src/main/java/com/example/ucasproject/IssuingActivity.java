package com.example.ucasproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ucasproject.Models.Issuing;
import com.example.ucasproject.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.type.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IssuingActivity extends AppCompatActivity {
    TextView textName, textSubNo;
    EditText editLastRead, editNewReading, editIssuingKeloPrice;
    Button btnIssuingSave;
    ProgressBar pbIssuing;

    FirebaseFirestore db;
    DocumentReference documentReference;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issuing);

        db = FirebaseFirestore.getInstance();
        documentReference = db.collection("users").document();

        user = new User();
        user = (User) getIntent().getSerializableExtra("data");

        textName = findViewById(R.id.textIssuingUserName);
        textSubNo = findViewById(R.id.textIssuingUserSubNo);

        textName.setText(user.getUserFirstName());
        textSubNo.setText(user.getUserSubNumber());

        editLastRead = findViewById(R.id.editLastReading);
        editNewReading = findViewById(R.id.editNewReading);
        editIssuingKeloPrice = findViewById(R.id.editIssuingKeloPrice);

        btnIssuingSave = findViewById(R.id.btnIssuingSave);
        pbIssuing = findViewById(R.id.pbAddPrice);

        user = new User();
        user.setUserSubNumber(textSubNo.getText().toString());

        btnIssuingSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editLastRead.getText().toString()) || TextUtils.isEmpty(editNewReading.getText().toString())
                        || TextUtils.isEmpty(editIssuingKeloPrice.getText().toString())) {
                    Toast.makeText(IssuingActivity.this, R.string.pleaseCompleteAllVales, Toast.LENGTH_SHORT).show();
                    return;
                }

                pbIssuing.setVisibility(View.VISIBLE);
                btnIssuingSave.setVisibility(View.GONE);

                String user_name = textName.getText().toString();
                String subscription_Number = textSubNo.getText().toString();

                // to get the day into user add
                Date date_invoice = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                String date_time = formatter.format(date_invoice);


                double total_price = Double.parseDouble(editIssuingKeloPrice.getText().toString());



                double price = (Integer.valueOf(editLastRead.getText().toString()) - Integer.valueOf(editNewReading.getText().toString())) * total_price;

                Issuing issuing = new Issuing(user_name, subscription_Number, date_time, price);

                db.collection("bills").add(issuing).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        if (documentReference.getId() != null) {

                            pbIssuing.setVisibility(View.GONE);
                            btnIssuingSave.setVisibility(View.VISIBLE);
                            Toast.makeText(IssuingActivity.this, R.string.finshSuccessfull, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(IssuingActivity.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}