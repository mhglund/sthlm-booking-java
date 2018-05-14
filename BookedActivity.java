package com.example.project;
import java.time.LocalDate;
import java.time.LocalTime;


public class BookedActivity {
    public final LocalDate date;
    public final LocalTime startTime, endTime;
    public final String owner, place, description, activity;

    public BookedActivity(LocalDate date, LocalTime startTime, LocalTime endTime,
                          String owner, String place, String description,
                          String activity) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.owner = owner;
        this.place = place; 
        this.activity = activity;
        this.description = description;
        
    }

    @Override
    public String toString() {
        String bookedActivityInfo = "\n---------------------------------------" +
            "\n Aktivitet: " + activity +
            "\n Datum: " + date +
            "\n Starttid: " + startTime + "| Sluttid: " + endTime +
            "\n Förening: " + owner +
            "\n Hemsida: " + description +
            "\n Plats: " + place;
        return bookedActivityInfo; 
    }

}
