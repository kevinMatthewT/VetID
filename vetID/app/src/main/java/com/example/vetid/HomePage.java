package com.example.vetid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    TextView GroomingPage,HealthPage,ForumPage,SuppliesPage;

    Button logout;

    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        GroomingPage=findViewById(R.id.groomingMenu);
        ForumPage=findViewById(R.id.forumMenu);
        HealthPage=findViewById(R.id.healthMenu);
        SuppliesPage=findViewById(R.id.suppliesMenu);

        logout=findViewById(R.id.logOutButton);
        fAuth= FirebaseAuth.getInstance();

        GroomingPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,GroomingPage.class));
                finish();
            }
        });

        HealthPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,HealthPage.class));
                finish();
            }
        });

        ForumPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,ForumPage.class));
                finish();
            }
        });

        SuppliesPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,SuppliesPage.class));
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(),LoginPage.class));
                finish();
            }
        });

    }
}