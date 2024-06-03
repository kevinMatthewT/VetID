package com.example.vetid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DoctorPage extends AppCompatActivity {

    TextView makeScheduleRedirect,vetIdLogo;

    // variable for the uid of user
    String UID;

    //for the display of all of the schedule/appointments created
    RecyclerView recyclerView;

    ArrayList<DoctorScheduleModel> doctorSchedule;
    DoctorScheduleAdapter doctorAdapter;

    //for connection to firebase
    FirebaseFirestore fstore;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_page);

        //references the id or variables in the activity_doctor_page.xml
        makeScheduleRedirect=findViewById(R.id.createSchedule);
        vetIdLogo=findViewById(R.id.logo);

        recyclerView=findViewById(R.id.doctorSchedules);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialize the conntetion to the database, the current user and a list for
        //displaying all appointments
        fstore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        doctorSchedule=new ArrayList<DoctorScheduleModel>();
        doctorAdapter=new DoctorScheduleAdapter(DoctorPage.this,doctorSchedule);
        recyclerView.setAdapter(doctorAdapter);

        //logo to return to home page
        vetIdLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        //go to a doctor exclusive activity to create schedules
        makeScheduleRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DoctorSchedule.class));
                finish();
            }
        });

        //calling a function to get and display the collection in a recycler view display
        eventChageListener();


    }

    private void eventChageListener() {
        //grabs all of the appointments that a doctor creates based on the current UID that is logged in
        UID=auth.getCurrentUser().getUid();
        fstore.collection("doctors").document(UID).collection("appointments")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error!=null){
                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc: value.getDocumentChanges()){

                            if(dc.getType()==DocumentChange.Type.ADDED) {
                                doctorSchedule.add(dc.getDocument().toObject(DoctorScheduleModel.class));
                            }
                            doctorAdapter.notifyDataSetChanged();
                        }

                        return;
                    }


                });
    }
}