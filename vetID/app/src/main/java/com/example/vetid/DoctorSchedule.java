package com.example.vetid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.TextUtils;
import android.widget.DatePicker;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.text.DateFormat;

public class DoctorSchedule extends AppCompatActivity  {

    TextView goBack,vetIdLogo, time, date,usernameDoc;

    EditText title, description, animal, address, price;
    Button submitFormButton;


    String UID;
    int hour,minute;

    FirebaseAuth fAuth;
    FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_schedule);


        goBack=findViewById(R.id.backToDoctor);
        vetIdLogo=findViewById(R.id.logo);


        //field input information
        title=findViewById(R.id.titleField);
        description=findViewById(R.id.descriptionField);
        time=findViewById(R.id.timeField);
        date=findViewById(R.id.dateField);
        animal=findViewById(R.id.animalField);
        address=findViewById(R.id.addressField);
        price=findViewById(R.id.priceField);

        usernameDoc=findViewById(R.id.usernameDoctor);

        fAuth=FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();

        submitFormButton=findViewById(R.id.submitForm);

        //logo to return to home page
        vetIdLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(),HomePage.class));
                finish();
            }
        });

        //Button to go back to see all of the appointments the current logged in doctor made
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),DoctorPage.class));
                finish();
            }
        });

        //Used to select the time for the field input
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popTimePicker();
            }
        });

        //Used to select the date for the field input
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });

        //get the UID of the current user/doctor
        UID=fAuth.getCurrentUser().getUid();


        //get the username of the doctor on the users collection based on the UID of current user
        DocumentReference documentReference=fstore.collection("users").document(UID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                usernameDoc.setText(value.getString("username"));
            }
        });

        //Submit details to firebase
        submitFormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting all values of the fields that exist in the form
                String formTitle=String.valueOf(title.getText());
                String formDescription=String.valueOf(description.getText());
                String formTime=String.valueOf(time.getText());
                String formDate=String.valueOf(date.getText());
                String formAnimal=String.valueOf(animal.getText());
                String formAddress=String.valueOf(address.getText());
                String formPrice=String.valueOf(price.getText());
                String formDoctor=String.valueOf(usernameDoc.getText());

                //error handing to make sure important details are not empty
                if(TextUtils.isEmpty(formTitle)  || TextUtils.isEmpty(formAnimal)||TextUtils.isEmpty(formAddress)){
                    Toast.makeText(getApplicationContext(),"Fill all of the details to create schedule",Toast.LENGTH_SHORT).show();
                    return;
                }

                //set an appointment to a random UUID
                UUID uuid = UUID.randomUUID();

                //Referencing to the appointment collection in the doctor collection
                DocumentReference documentReferenceDoctors=fstore.collection("doctors").document(UID).collection("appointments").document(uuid.toString());

                //putting all of the fields to place
                Map<String,Object> schedule= new HashMap<>();
                schedule.put("title",formTitle);
                schedule.put("description",formDescription);
                schedule.put("price",formPrice);
                schedule.put("time",formTime);
                schedule.put("date",formDate);
                schedule.put("animal",formAnimal);
                schedule.put("address",formAddress);
                schedule.put("patient_name",null);
                schedule.put("patient_email",null);
                schedule.put("patient_phone",null);
                schedule.put("doctor_username",formDoctor);


                //referencing to an appointment collection
                DocumentReference documentReferenceAppointments=fstore.collection("appointment").document(uuid.toString());

                //putting all of the fields to place
                Map<String,Object> appointment= new HashMap<>();
                appointment.put("title",formTitle);
                appointment.put("description",formDescription);
                appointment.put("price",formPrice);
                appointment.put("time",formTime);
                appointment.put("date",formDate);
                appointment.put("animal",formAnimal);
                appointment.put("address",formAddress);
                appointment.put("patient_name",null);
                appointment.put("patient_email",null);
                appointment.put("patient_phone",null);
                appointment.put("doctor_UID",UID);
                appointment.put("doctor_username",formDoctor);

                //placing all of the data in each hashmap in their respective collection
                documentReferenceDoctors.set(schedule).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                });

                documentReferenceAppointments.set(appointment);

                DocumentReference documentReference=fstore.collection("users").document(fAuth.getCurrentUser().getUid());



            }
        });





    }

    //function used to select the time
    public void popTimePicker() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfDay) {
                hour=hourOfDay;
                minute=minuteOfDay;
                time.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };
        TimePickerDialog timePickerDialog=new TimePickerDialog(this,onTimeSetListener,hour,minute,true);
        timePickerDialog.show();
    }

    //function used to select the date
    public void setDate() {
        Calendar calendar=Calendar.getInstance();
        int mYear,mMonth,mDay;
        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate= dayOfMonth+" "+(month+1)+" "+year;
                date.setText(selectedDate);
            }
        },mYear,mMonth,mDay);
        datePickerDialog.show();
    }


}