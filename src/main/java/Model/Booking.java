package Model;

import Enums.HairStyles;
import Enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private int id;
    private String name;
    private int phoneNr;
    private LocalDate date;
    private LocalTime time;
    private HairStyles haircutType;
    private int hairdresserId;
    private String description;
    private Status status;

    //Konstruktør
    public Booking(int id, String name, int phoneNr, LocalDate date, LocalTime time, HairStyles haircutType, int hairdresserId, String description) {
        this.id = id;
        this.name = name;
        this.phoneNr = phoneNr;
        this.date = date;
        this.time = time;
        this.haircutType = haircutType;
        this.hairdresserId = hairdresserId;
        this.description = description;
        this.status = Status.ACTIVE;
    }

    //Gettere og settere
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getPhoneNr(){
        return phoneNr;
    }
    public void setPhoneNr(int phoneNr){
        this.phoneNr = phoneNr;
    }

    public LocalDate getDate(){
        return date;
    }
    public void setDate(LocalDate date){
        this.date = date;
    }

    public LocalTime getTime(){
        return time;
    }
    public void setTime(LocalTime time){
        this.time = time;
    }

    public HairStyles getHaircutType(){
        return haircutType;
    }
    public void setHaircutType(HairStyles haircutType){
        this.haircutType = haircutType;
    }

    public int getHairdresserId(){
        return hairdresserId;
    }
    public void setHairdresserId(int hairdresserId){
        this.hairdresserId = hairdresserId;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public Status getStatus(){
        return status;
    }
    public void setStatus(Status status){
        this.status = status;
    }

}
