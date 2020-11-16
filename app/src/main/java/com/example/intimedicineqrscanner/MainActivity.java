package com.example.intimedicineqrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnQR, btnCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("QR Scanner for INTI");

        btnQR = findViewById(R.id.btnQR);
        btnCheck = findViewById(R.id.btnCheck);

        btnQR.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ScannerActivity.class);
                startActivity(intent);
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RecordActivity.class);
                startActivity(intent);
            }
        });
    }

}