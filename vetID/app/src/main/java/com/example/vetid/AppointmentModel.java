package com.example.vetid;

public class AppointmentModel {
    //file containing the getter, setters and constructors for variable to change in the view and grabbing data from firebase

    String doctor_username,animal, time, date,description,address;

    AppointmentModel(){

    }
    public AppointmentModel(String doctor_username, String animal, String time, String date, String description, String address) {
        this.doctor_username = doctor_username;
        this.animal = animal;
        this.time = time;
        this.date = date;
        this.description = description;
        this.address = address;
    }

    public void changeName(String doctors){
        this.doctor_username=doctors;
    }

    public String getDoctor_username() {
        return doctor_username;
    }

    public void setDoctor_username(String doctor_username) {
        this.doctor_username = doctor_username;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
