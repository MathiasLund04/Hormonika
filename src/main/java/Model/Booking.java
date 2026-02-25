package Model;

import Enums.HairStyles;
import Enums.Status;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private int id;
    private String name;
    private int phoneNr;
    private LocalDate date;
    private LocalTime time;
    private HairStyles haircutType;
    private String hairdresser;
    private String description;
    private Status status;
    private LocalTime endTime;

    //Konstruktør
    public Booking(int id, String name, int phoneNr, LocalDate date, LocalTime time, HairStyles haircutType, String hairdresser, String description, LocalTime endTime) {
        this.id = id;
        this.name = name;
        this.phoneNr = phoneNr;
        this.date = date;
        this.time = time;
        this.haircutType = haircutType;
        this.hairdresser = hairdresser;
        this.description = description;
        this.status = Status.ACTIVE;
        this.endTime = endTime;
    }

    @FXML
    private ComboBox<HairStyles> haircutBox;

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

    public String getHairdresser(){
        return hairdresser;
    }
    public void setHairdresser(String hairdresser){
        this.hairdresser = hairdresser;
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
           Telefon: %d
           Dato: %s
           Tid: %s
           Behandling: %s
           Frisør: %s
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
                hairdresser,
                status.getDescription(),
                description == null || description.isBlank() ? "-" : description
        );
    }
}
