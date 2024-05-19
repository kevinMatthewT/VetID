package com.example.vetid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SuppliesPage extends AppCompatActivity {

    TextView vetIdLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplies_page);

        //empty page coming soon

        vetIdLogo=findViewById(R.id.logo);

        vetIdLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        startActivity(new Intent(SuppliesPage.this,HomePage.class));

            }
        });
    }
}