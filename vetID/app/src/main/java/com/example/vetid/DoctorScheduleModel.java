package com.example.vetid;

public class DoctorScheduleModel {
    String title,animal, time, date,patient_email,patient_name;

    DoctorScheduleModel(){

    }
    public DoctorScheduleModel(String title, String animal, String time, String date, String patient_email, String patient_name) {
        this.title = title;
        this.animal = animal;
        this.time = time;
        this.date = date;
        this.patient_email = patient_email;
        this.patient_name = patient_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatient_email() {
        return patient_email;
    }

    public void setPatient_email(String patient_email) {
        this.patient_email = patient_email;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }
}
