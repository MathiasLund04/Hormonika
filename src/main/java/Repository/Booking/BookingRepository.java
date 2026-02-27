package Repository.Booking;

import Model.Booking;

import java.sql.SQLException;
import java.util.List;

public interface BookingRepository {
    List<Booking> getCalendar() throws SQLException;
    void insertBooking(Booking booking) throws SQLException;
    Booking getBookingById(int id);
    void cancelBooking(Booking booking) throws SQLException;

}
