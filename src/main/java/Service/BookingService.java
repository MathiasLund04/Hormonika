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
    private DBConfig db;
    private DBRepo dbRepo = new DBRepo(db);
    private final CustomerService customerService = new CustomerService(dbRepo);

    private List<Booking> calendar = new ArrayList<>();
    private int nextBookingId = 0;

    public BookingService(DBRepo dbRepo){
        this.dbRepo = dbRepo;
    }

    public Booking createBooking(String name, int phoneNr, LocalDate date, LocalTime time, HairStyles haircutType, int hairdresserId, String description){
        //lige nu... hvis ikke valideringen går igennem vil den returnere null
        if (!validateBooking(date, time, hairdresserId)){
            return null;
        }

        //Lille tjek for at se om kunden allerede findes og tilføjer dem hvis det er en ny kunde
        customerService.createCustomerIfNotExist(name, phoneNr);

        //Hvis valideringen går igennem vil den lave en ny tidsbestilling
        int id = nextBookingId++;
        Booking newBooking = new Booking(id, name, phoneNr, date, time, haircutType, hairdresserId, description);
        calendar.add(newBooking);
        return newBooking;
    }

    private boolean validateBooking(LocalDate date, LocalTime time, int hairdresserId){
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

    public boolean addBookingDB(Booking booking){


        return true;
    }


}
