package org.example.hormonika;

import DAL.DBConfig;
import DAL.DBRepo;
import Enums.HairStyles;
import Model.Booking;
import Repository.Booking.MySQLBookingRepository;
import Repository.Customer.MySQLCustomerRepository;
import Service.BookingService;


import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {
    private static DBConfig db = new DBConfig();
    private static DBRepo dbRepo = new DBRepo(db);
    private static MySQLBookingRepository bRepo = new MySQLBookingRepository(db);
    private static Scanner input = new Scanner(System.in);
    private static BookingService bs = new BookingService(bRepo);

    public static void main(String[] args) {
    boolean running = true;
    int choice = 0;
        while(running){
            System.out.println("""
    Menu
    1. ny tidsbestilling
    2. se tidsbestilling
    3. test connection""");
            choice = input.nextInt();
            switch (choice){
                case 1 -> {
                    try {
                        newTicket();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 2 -> showBooking();
                case 3 -> db.testConnection();
                case 4 -> System.exit(0);
                default -> {
                    System.out.println("Invalid choice. Try again.");
                }
            }
        }
    }
    public static void showBooking(){
        for (Booking b : bs.getCalendar()){
            System.out.println(b.toString());
        }
    }
    public static void newTicket() throws SQLException {
        String name;
        String phoneNr;
        String dateInput;
        String timeInput;
        String endTimeInput;
        HairStyles haircutType;
        int hairdresserId;
        String beskrivelse;

        input.nextLine();
        System.out.println("Navn: ");
        name = input.nextLine();

        System.out.println("Tlf nr: ");
        phoneNr = input.nextLine();

        input.nextLine();
        System.out.println("Dato (yyyy-MM-dd): ");
        dateInput = input.nextLine();
        LocalDate date = LocalDate.parse(dateInput);

        System.out.println("Tid (HH:mm): ");
        timeInput = input.nextLine();
        LocalTime time = LocalTime.parse(timeInput);

        System.out.println("Slut tid (HH:mm): ");
        endTimeInput = input.nextLine();
        LocalTime endTime = LocalTime.parse(endTimeInput);

        System.out.println("""
                klipnings type:
                MANCUT
                WOMANCUT
                CHILDCUT
                COLOUR
                PERM
                BEARD
                OTHER
                """);
        haircutType = HairStyles.valueOf(input.nextLine());

        System.out.println("""
                frisør:
                1. Mads
                2. Ida
                3. Fie
                4. Mie""");
        hairdresserId = input.nextInt();
                //= dbRepo.getHairdresserNamebyID(input.nextInt());

        input.nextLine();
        System.out.println("Beskrivelse: ");
        beskrivelse = input.nextLine();

        bs.createBooking(name,phoneNr,date,time,haircutType,hairdresserId,beskrivelse);


    }


}
