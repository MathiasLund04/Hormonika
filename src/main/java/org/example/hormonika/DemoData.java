package org.example.hormonika;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DemoData {

        public static List<Booking> getBookings(LocalDate date) {
            // Demo: samme bookinger hver dag
            return List.of(
                    new Booking("Mads", LocalTime.of(8, 0), LocalTime.of(9, 0)),
                    new Booking("Mads", LocalTime.of(11, 0), LocalTime.of(12, 0)),
                    new Booking("Alice", LocalTime.of(10, 30), LocalTime.of(11, 30)),
                    new Booking("Ida", LocalTime.of(13, 0), LocalTime.of(15, 0)),
                    new Booking("Fie", LocalTime.of(11, 0), LocalTime.of(14, 0)),
                    new Booking("Fie", LocalTime.of(16, 0), LocalTime.of(17, 0)),
                    new Booking("Monika", LocalTime.of(9, 0), LocalTime.of(12, 0)),
                    new Booking("Monika", LocalTime.of(13, 0), LocalTime.of(15, 0))
            );
        }
    }
