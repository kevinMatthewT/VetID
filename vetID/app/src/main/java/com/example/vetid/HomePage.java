package com.example.vetid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class HomePage extends AppCompatActivity {

    TextView GroomingPage,HealthPage,ForumPage,SuppliesPage;

    Button logout,doctorButton,applyDoctor;

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //References all of the id of elements in the home page
        GroomingPage=findViewById(R.id.groomingMenu);
        ForumPage=findViewById(R.id.forumMenu);
        HealthPage=findViewById(R.id.healthMenu);
        SuppliesPage=findViewById(R.id.suppliesMenu);

        logout=findViewById(R.id.logOutButton);
        fAuth= FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        doctorButton=findViewById(R.id.doctorButton);
        applyDoctor=findViewById(R.id.applyDoctorButton);

        //Redirects with on click to different pages
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

        //logout button to logout a current user that is signed in
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(),LoginPage.class));
                finish();
            }
        });

        //allows to go to the doctor page
        doctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DoctorPage.class));
                finish();
            }
        });

        //a condition that a user is a doctor by checking a bool value in the is_doctor field
        DocumentReference documentReference=fstore.collection("users").document(fAuth.getCurrentUser().getUid());
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.getBoolean("is_doctor")==true){
                    doctorButton.setVisibility(View.VISIBLE);
                    applyDoctor.setVisibility(View.INVISIBLE);
                }
            }
        });

        //allows user to apply to become a doctor
        applyDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference documentReference=fstore.collection("users").document(fAuth.getCurrentUser().getUid());
                Map<String,Object> toggle= new HashMap<>();
                toggle.put("is_doctor",true);
                documentReference.update(toggle);
            }
        });
    }
}