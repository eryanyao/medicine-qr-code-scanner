package com.example.intimedicineqrscanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DecodeActivity extends AppCompatActivity {
    private FirebaseFirestore firestore;

    private String uid;
    Button btnApprove, btnDenied;
    TextView txtId, txtRoles, txtStatus, txtName, txtCountry, txtPhone, txtEmail, txtCheckDate;
    ImageView imgUsr;
    ProgressDialog pd;

    private String m_Text = "";

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode);

        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Bundle bundle = getIntent().getExtras();
        uid = bundle.getString("uid");

        pd = new ProgressDialog(this);

        firestore = FirebaseFirestore.getInstance();

        imgUsr = findViewById(R.id.imgUsr);

        btnApprove = findViewById(R.id.btnApprove);
        btnDenied = findViewById(R.id.btnDenied);

        txtId = findViewById(R.id.txtId);
        txtRoles = findViewById(R.id.txtRoles);
        txtStatus = findViewById(R.id.txtStatus);
        txtName = findViewById(R.id.txtName);
        txtCheckDate = findViewById(R.id.txtDateCheck);
        txtPhone = findViewById(R.id.txtPhone);
        txtCountry = findViewById(R.id.txtCountry);
        txtEmail = findViewById(R.id.txt);

        update();

        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DecodeActivity.this);
                builder.setTitle("Key in temperature");
                final EditText input = new EditText(DecodeActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        if(m_Text.isEmpty()){
                            Toast.makeText(DecodeActivity.this,"Please key in temperature",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else{
                            final String recordId = UUID.randomUUID().toString();
                            Date d = new Date();
                            Map<String, Object> RecordData = new HashMap<>();

                            RecordData.put("recordId",recordId);
                            RecordData.put("studentId",txtId.getText());
                            RecordData.put("name",txtName.getText());
                            RecordData.put("checkIn",d);
                            RecordData.put("entry","Yes");
                            RecordData.put("status",txtStatus.getText());
                            RecordData.put("temperature",m_Text);

                            firestore.collection("record").document(recordId).set(RecordData).addOnCompleteListener(
                                    new OnCompleteListener<Void>() {
                                        @Override public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(DecodeActivity.this, "Record " +
                                                            "has " +
                                                            "been " +
                                                            "successfully saved",
                                                    Toast.LENGTH_LONG).show();
                                            record();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                @Override public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DecodeActivity.this, "Error occur. Please try again later.",
                                            Toast.LENGTH_LONG).show();
                                    scanner();
                                }
                            });
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            }
        });

        btnDenied.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DecodeActivity.this);

                builder.setTitle("Are you sure don't let this people in?");

                builder.setPositiveButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder.setNegativeButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final String recordId = UUID.randomUUID().toString();
                                Date d = new Date();
                                Map<String, Object> RecordData = new HashMap<>();

                                RecordData.put("recordId",recordId);
                                RecordData.put("studentId",txtId.getText());
                                RecordData.put("name",txtName.getText()) ;
                                RecordData.put("status",txtStatus.getText());
                                RecordData.put("checkIn",d);
                                RecordData.put("entry","No");
                                RecordData.put("temperature",null);

                                firestore.collection("record").document(recordId).set(RecordData).addOnCompleteListener(
                                        new OnCompleteListener<Void>() {
                                            @Override public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(DecodeActivity.this, "Record " +
                                                                "has " +
                                                                "been " +
                                                                "successfully saved",
                                                        Toast.LENGTH_LONG).show();
                                                scanner();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DecodeActivity.this, "Error occur. Please try again later.",
                                                Toast.LENGTH_LONG).show();
                                        scanner();
                                    }
                                });

                            }
                        });
                builder.create().show();
            }
        });


    }

    public void update(){
        pd.setTitle("Loading user data");
        pd.setMessage("Wait a moment...");
        pd.show();
        DocumentReference df = firestore.collection("user").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override public void onSuccess(DocumentSnapshot documentSnapshot) {
                String firstName = documentSnapshot.getString("userFirstName");
                String lastName = documentSnapshot.getString("userLastName");
                String ic = documentSnapshot.getString("userBarcode");
                String email = documentSnapshot.getString("userEmail");
                String img = documentSnapshot.getString("userProfileUri");
                String status = documentSnapshot.getString("userStatus");
                Timestamp date = documentSnapshot.getTimestamp("userLastCovidChecked");
                String country = documentSnapshot.getString("userCountry");
                String phone = documentSnapshot.getString("userPhoneNumber");
                String roles = documentSnapshot.getString("userAffiliation");

                if (!img.isEmpty()) {
                    Picasso.get().load(img).into(imgUsr);
                }

                if (status.equals("Safe")) {
                    txtStatus.setText("Safe");
                    txtStatus.setTextColor(Color.GREEN);
                } else if (status.equals("Danger")) {
                    txtStatus.setText("Danger");
                    txtStatus.setTextColor(Color.RED);
                }

                txtCheckDate.setText(format.format(date.toDate()));
                txtId.setText(ic);
                txtName.setText(firstName + " " + lastName);
                txtEmail.setText(email);
                txtCountry.setText(country);
                txtPhone.setText(phone);
                txtRoles.setText(roles);

                pd.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override public void onFailure(@NonNull Exception e) {
                Toast.makeText(DecodeActivity.this,"Record Not Found!",Toast.LENGTH_SHORT).show();

                pd.dismiss();

                AlertDialog.Builder builder = new AlertDialog.Builder(DecodeActivity.this);
                builder.setTitle("User Record Not Found!");
                builder.setPositiveButton(
                        "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                scanner();
                            }
                        });
                builder.create().show();
            }
        });
    }

    public void scanner(){
        Intent intent = new Intent(DecodeActivity.this,ScannerActivity.class);
        startActivity(intent);
    }

    public void record(){
        Intent intent = new Intent(DecodeActivity.this,RecordActivity.class);
        startActivity(intent);
    }
}