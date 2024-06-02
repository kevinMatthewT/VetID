package com.example.vettest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class HomeFragment extends Fragment {
    TextView GroomingPage, HealthPage, ForumPage, SuppliesPage, DoctorPage;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        GroomingPage = view.findViewById(R.id.groomingMenu);
        ForumPage = view.findViewById(R.id.forumMenu);
        HealthPage = view.findViewById(R.id.healthMenu);
        SuppliesPage = view.findViewById(R.id.suppliesMenu);
        DoctorPage = view.findViewById(R.id.doctorView);

        GroomingPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GroomingPage.class));
                getActivity().finish();
            }
        });

        HealthPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HealthPage.class));
                getActivity().finish();
            }
        });

        ForumPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ForumPage.class));
                getActivity().finish();
            }
        });

        SuppliesPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SuppliesPage.class));
                getActivity().finish();
            }
        });

        // Set up Firestore snapshot listener for the current user
        DocumentReference documentReference = fstore.collection("users")
                .document(fAuth.getCurrentUser().getUid());

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    // Handle error
                    return;
                }

                if (value != null && value.exists()) {
                    Boolean isDoctor = value.getBoolean("is_doctor");
                    if (Boolean.TRUE.equals(isDoctor)) {
                        DoctorPage.setVisibility(View.VISIBLE);
                    } else {
                        DoctorPage.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        return view;
    }
}