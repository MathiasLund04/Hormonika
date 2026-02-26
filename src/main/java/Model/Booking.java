package Model;

import Enums.HairStyles;
import Enums.Status;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Booking {
    private int id;
    private String name;
    private String phoneNr;
    private LocalDate date;
    private LocalTime time;
    private HairStyles haircutType;
    private int hairdresserId;
    private String hairdresserName;
    private String description;
    private Status status;
    private LocalTime endTime;

    //Konstruktør
    public Booking(int id, String name, String phoneNr, LocalDate date, LocalTime time, HairStyles haircutType, int hairdresserId, String description) {
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

    @FXML
    private ComboBox<HairStyles> haircutBox;

    public Booking(String name, String phoneNr, LocalDate date, LocalTime time, HairStyles haircutType, int hairdresserId, String description, Status status) {
        this.name = name;
        this.phoneNr = phoneNr;
        this.date = date;
        this.time = time;
        this.haircutType = haircutType;
        this.hairdresserId = hairdresserId;
        this.description = description;
        this.status = status;
    }

    public Booking(int id, String name, String phoneNr, LocalDate date, LocalTime time, HairStyles haircutType, String hairdresser, String description, Status status) {
        this.id = id;
        this.name = name;
        this.phoneNr = phoneNr;
        this.date = date;
        this.time = time;
        this.haircutType = haircutType;
        this.hairdresserName = hairdresser;
        this.description = description;
        this.status = status;
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

    public String getPhoneNr(){
        return phoneNr;
    }
    public void setPhoneNr(String phoneNr){
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

    public String getHairdresserName(){
        return hairdresserName;
    }
    public void setHairdresserName(String hairdresserName){
        this.hairdresserName = hairdresserName;
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

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime){ this.endTime = endTime; }


    @Override
    public String toString(){
        return """
           -------------------------------
           Booking ID: %d
           Kunde: %s
           Telefon: %s
           Dato: %s
           Tid: %s
           Behandling: %s
           Frisør: %d
           Status: %s
           Note: %s
           -------------------------------
           """.formatted(
                id,
                name,
                phoneNr,
                date,
                time,
                haircutType.getDescription(),
                hairdresserId,
                status.getDescription(),
                description == null || description.isBlank() ? "-" : description
        );
    }
}
