package com.example.vetid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GroomingPage extends AppCompatActivity {

    TextView vetIdLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grooming_page);

        vetIdLogo=findViewById(R.id.logo);
        vetIdLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(GroomingPage.this,HomePage.class));

            }
        });
    }
}