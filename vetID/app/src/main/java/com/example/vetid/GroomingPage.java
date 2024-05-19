package com.example.vetid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class GroomingPage extends AppCompatActivity {

    TextView vetIdLogo;

    RecyclerView recyclerView;

    ArrayList<AppointmentModel> appointmentModels;
    AppointmentAdapter appointmentAdapter;
    FirebaseFirestore fstore;
    FirebaseAuth auth;

    TextView doctorID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grooming_page);

        vetIdLogo=findViewById(R.id.logo);


        recyclerView=findViewById(R.id.allAppointments);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initialize the available appointments
        fstore= FirebaseFirestore.getInstance();
        auth= FirebaseAuth.getInstance();
        appointmentModels=new ArrayList<AppointmentModel>();
        appointmentAdapter=new AppointmentAdapter(GroomingPage.this,appointmentModels);
        recyclerView.setAdapter(appointmentAdapter);

        vetIdLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(GroomingPage.this,HomePage.class));


            }


        });

        //calls the event to put all of the information of the card
        eventChageListener();


    }

    //grabs all of the available appointments that exist in the appointment collection
    private void eventChageListener() {
        fstore.collection("appointment")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                //gets the information based on the Appointment model
                                appointmentModels.add(dc.getDocument().toObject(AppointmentModel.class));

                            }
                            appointmentAdapter.notifyDataSetChanged();
                        }

                        return;
                    }


                });
    }
}