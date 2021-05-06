package com.example.annuaire;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private int ID;
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "email")
    String email;
    @ColumnInfo(name = "job")
    String Job;
    @ColumnInfo(name = "phone")
    String phone;

    public Contact() {

    }
    public Contact(String name, String email, String job, String phone) {
        this.name = name;
        this.email = email;
        Job = job;
        this.phone = phone;
    }

    public Contact(int ID,String name, String email, String job, String phone) {
        this.ID=ID;
        this.name = name;
        this.email = email;
        Job = job;
        this.phone = phone;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", Job='" + Job + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
