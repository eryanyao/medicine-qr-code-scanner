package com.example.intimedicineqrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.intimedicineqrscanner.record.RecordCustomAdapter;
import com.example.intimedicineqrscanner.record.RecordModel;
import com.example.intimedicineqrscanner.record.RecordViewHolder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    FirebaseFirestore firestore;
    ProgressDialog pd;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    int numOfData = 1;
    List<RecordModel> RecordModelList = new ArrayList<>();
    RecordCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("INTI-IU Check-in Records");
        actionBar.setDisplayHomeAsUpEnabled(true);
        pd = new ProgressDialog(this);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        firestore = FirebaseFirestore.getInstance();
        showData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.record_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_record_all:
                showData();
                Toast.makeText(RecordActivity.this, "Show all records", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_record_yes:
                showDataQuery("entry", "Yes");
                Toast.makeText(RecordActivity.this, "Approved entries into campus", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_record_no:
                showDataQuery("entry", "No");
                Toast.makeText(RecordActivity.this, "Rejected entries into campus", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_record_safe:
                showDataQuery("status", "Safe");
                Toast.makeText(RecordActivity.this, "Status is SAFE", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_record_danger:
                showDataQuery("status", "Danger");
                Toast.makeText(RecordActivity.this, "Status is DANGER", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_record_softDateAscen:
                showDateAsc();
                Toast.makeText(RecordActivity.this, "Date in ascending order", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_record_softDateDesc:
                showDateDesc();
                Toast.makeText(RecordActivity.this, "Date in descending order", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showDataQuery(String data, String str) {
        pd.setTitle("Please wait");
        pd.setMessage("Loading data..");
        pd.show();
        firestore.collection("record").whereEqualTo(data, str).get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        RecordModelList.clear();
                        pd.dismiss();
                        for (DocumentSnapshot doc : task.getResult()) {
                            RecordModel model = new RecordModel(
                                    numOfData,
                                    doc.getString("entry"),
                                    doc.getString("studentId"),
                                    doc.getString("temperature"),
                                    doc.getString("recordId"),
                                    doc.getString("status"),
                                    doc.getString("name"),
                                    doc.getTimestamp("checkIn")
                            );
                            RecordModelList.add(model);
                            numOfData++;
                        }
                        adapter = new RecordCustomAdapter(RecordActivity.this, RecordModelList);
                        recyclerView.setAdapter(adapter);
                        numOfData = 1;
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RecordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDateAsc() {
        pd.setTitle("Please wait");
        pd.setMessage("Loading data..");
        pd.show();
        firestore.collection("record").orderBy("checkIn", Query.Direction.ASCENDING).get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        RecordModelList.clear();
                        pd.dismiss();
                        for (DocumentSnapshot doc : task.getResult()) {
                            RecordModel model = new RecordModel(
                                    numOfData,
                                    doc.getString("entry"),
                                    doc.getString("studentId"),
                                    doc.getString("temperature"),
                                    doc.getString("recordId"),
                                    doc.getString("status"),
                                    doc.getString("name"),
                                    doc.getTimestamp("checkIn")
                            );
                            RecordModelList.add(model);
                            numOfData++;
                        }
                        adapter = new RecordCustomAdapter(RecordActivity.this, RecordModelList);
                        recyclerView.setAdapter(adapter);
                        numOfData = 1;
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RecordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDateDesc() {
        pd.setTitle("Please wait");
        pd.setMessage("Loading data..");
        pd.show();
        firestore.collection("record").orderBy("checkIn",
                Query.Direction.DESCENDING).get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        RecordModelList.clear();
                        pd.dismiss();
                        for (DocumentSnapshot doc : task.getResult()) {
                            RecordModel model = new RecordModel(
                                    numOfData,
                                    doc.getString("entry"),
                                    doc.getString("studentId"),
                                    doc.getString("temperature"),
                                    doc.getString("recordId"),
                                    doc.getString("status"),
                                    doc.getString("name"),
                                    doc.getTimestamp("checkIn")
                            );
                            RecordModelList.add(model);
                            numOfData++;
                        }
                        adapter = new RecordCustomAdapter(RecordActivity.this, RecordModelList);
                        recyclerView.setAdapter(adapter);
                        numOfData = 1;
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RecordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showData() {
        pd.setTitle("Please wait");
        pd.setMessage("Loading data..");
        pd.show();
        firestore.collection("record").get().addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        RecordModelList.clear();
                        pd.dismiss();
                        for (DocumentSnapshot doc : task.getResult()) {
                            RecordModel model = new RecordModel(
                                    numOfData,
                                    doc.getString("entry"),
                                    doc.getString("studentId"),
                                    doc.getString("temperature"),
                                    doc.getString("recordId"),
                                    doc.getString("status"),
                                    doc.getString("name"),
                                    doc.getTimestamp("checkIn")
                            );
                            RecordModelList.add(model);
                            numOfData++;
                        }
                        adapter = new RecordCustomAdapter(RecordActivity.this, RecordModelList);
                        recyclerView.setAdapter(adapter);
                        numOfData = 1;
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RecordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed() {
        Intent intent = new Intent(RecordActivity.this, MainActivity.class);
        startActivity(intent);
    }
}