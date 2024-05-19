package com.example.vetid;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.myViewHolder> {
    Context context;
    ArrayList<AppointmentModel> allAppointments;



    public AppointmentAdapter(Context context, ArrayList<AppointmentModel> allAppointments) {
        this.context = context;
        this.allAppointments = allAppointments;
    }

    // function to reference the appointment_card.xml to place data
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.appointments_card,parent,false);
        return new AppointmentAdapter.myViewHolder(v);
    }

    //used to set the information grabbed from the appointment models into the appointment card
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        AppointmentModel appointments=allAppointments.get(position);

        holder.name.setText(appointments.doctor_username);
        holder.animal.setText(appointments.animal);
        holder.time.setText(appointments.time);
        holder.date.setText(appointments.date);
        holder.desc.setText(appointments.description);
        holder.address.setText(appointments.address);
    }

    //returns all of the items in the appointment list
    @Override
    public int getItemCount() {
        return allAppointments.size();
    }

    //Used to reference the id of texts in the appointment cards
    public static class myViewHolder extends RecyclerView.ViewHolder{

        TextView name,animal, time, date,desc,address;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.doctorAppointments);
            animal=itemView.findViewById(R.id.petAppointments);
            time=itemView.findViewById(R.id.timeAppointments);
            date=itemView.findViewById(R.id.dateAppointments);
            desc=itemView.findViewById(R.id.descriptionAppointments);
            address=itemView.findViewById(R.id.addressAppointments);
        }
    }







}
