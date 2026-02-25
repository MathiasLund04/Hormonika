package Service;

import Enums.HairStyles;
import Enums.Status;
import Model.Booking;
import org.example.hormonika.DBConfig;
import org.example.hormonika.DBRepo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private DBConfig db = new DBConfig();
    private DBRepo dbRepo = new DBRepo(db);
    private final CustomerService customerService = new CustomerService(dbRepo);

    private List<Booking> calendar = new ArrayList<>();
    private int nextBookingId = 1;

    public BookingService(DBRepo dbRepo){
        this.dbRepo = dbRepo;
    }

    public Booking createBooking(String name, int phoneNr, LocalDate date, LocalTime time,
                                 HairStyles haircutType, String hairdresser,
                                 String description, LocalTime endTime) {

        // Validering
        if (!validateBookingTime(date, time, hairdresser)) {
            return null;
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Navn skal udfyldes");
        }
        if (phoneNr == 0) {
            throw new IllegalArgumentException("Telefonnummer skal udfyldes");
        }
        if (date == null) {
            throw new IllegalArgumentException("Dato skal udfyldes");
        }
        if (time == null) {
            throw new IllegalArgumentException("Tid skal udfyldes");
        }
        if (haircutType == null) {
            throw new IllegalArgumentException("Klipning skal udfyldes");
        }
        if (hairdresser == null || hairdresser.isBlank()) {
            throw new IllegalArgumentException("Frisør skal vælges");
        }

        // Opret kunde hvis ny
        customerService.createCustomerIfNotExist(name, phoneNr);

        // Opret booking
        int id = nextBookingId++;
        Booking newBooking = new Booking(
                id,
                name,
                phoneNr,
                date,
                time,
                haircutType,
                hairdresser,      // ← STRING, ikke ID
                description,
                endTime
        );

        calendar.add(newBooking);
        return newBooking;
    }


    //Validering til at sikre at en medarbejder ikke kan dobbeltbookes
    private boolean validateBookingTime(LocalDate date, LocalTime time, String hairdresser){
        for (Booking b : calendar) {

            boolean sameHairdresser = b.getHairdresser().equals(hairdresser);
            boolean sameDate = b.getDate().equals(date);
            boolean sameTime = b.getTime().equals(time);
            //Ekstra for lige at tjekke at tidsbestillingen stadig er aktiv
            boolean stillActive = b.getStatus().equals(Status.ACTIVE);

            if (sameHairdresser && sameDate && sameTime && stillActive){
                //Dette fortæller at frisøren allerede er booket på denne tid (til en der stadig er aktiv)
                return false;
            }
        }
        return true;
    }

    public List<Booking> getCalendar(){
        return new ArrayList<>(calendar);
    }

    public boolean addBookingDB(Booking booking){


        return true;
    }


}
