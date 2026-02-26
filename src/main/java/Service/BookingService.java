package Service;

import Enums.HairStyles;
import Enums.Status;
import Model.Booking;
import DAL.DBConfig;
import DAL.DBRepo;
import Repository.Booking.BookingRepository;
import Repository.Booking.MySQLBookingRepository;
import Repository.Customer.CustomerRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private DBConfig db = new DBConfig();
    private DBRepo dbRepo = new DBRepo(db);
    private final BookingRepository bRepo;
    private CustomerRepository cRepo;
    private final CustomerService customerService = new CustomerService(cRepo);

    private List<Booking> calendar;
    private int nextBookingId = 1;

    public BookingService(BookingRepository bRepo) {
        this.bRepo = bRepo;
        try {
            calendar = new ArrayList<>(bRepo.getCalendar());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Booking createBooking(String name, String phoneNr, LocalDate date, LocalTime time,
                                 HairStyles haircutType, int hairdresserId,
                                 String description) {

        // Validering
        if (!validateBookingTime(date, time, hairdresserId)) {
            return null;
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Navn skal udfyldes");
        }
        if (phoneNr == null || phoneNr.trim().isEmpty()) {
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
        if (hairdresserId == 0) {
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
                hairdresserId,
                description
        );

        calendar.add(newBooking);
        addBookingDB(newBooking);
        return newBooking;
    }


    //Validering til at sikre at en medarbejder ikke kan dobbeltbookes
    private boolean validateBookingTime(LocalDate date, LocalTime time, int hairdresserId){
        for (Booking b : calendar) {

            boolean sameHairdresser = b.getHairdresserId() == hairdresserId;
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

    public void addBookingDB(Booking booking){
        try {
            bRepo.insertBooking(booking);
        } catch (SQLException e){
            //Returner en besked i konsollen WIP
        }
    }


}
