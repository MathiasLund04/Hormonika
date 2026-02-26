package Repository.Booking;

import DAL.DBConfig;
import Enums.Haircuts;
import Enums.Status;
import Exceptions.DataAccessException;
import Model.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MySQLBookingRepository implements BookingRepository {
    public final DBConfig db;

    public MySQLBookingRepository(DBConfig db){
        this.db = db;
    }

    public List<Booking> getCalendar() throws SQLException {
        List<Booking> bookings = new ArrayList<>();

        String sql = """
                    
                SELECT
                       b.id,
                       b.name AS booking_name,
                       b.PhoneNum,
                       b.Date,
                       b.Time,
                       b.HaircutType,
                       h.name AS hairdresser_name,
                       b.Description,
                       b.status
                   FROM Booking b
                   JOIN Hairdresser h ON b.Hairdresser = h.id;
                    """;

        try (Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("booking_name");
                String phoneNr = rs.getString("PhoneNum");
                LocalDate date = rs.getDate("Date").toLocalDate();
                LocalTime time = rs.getTime("Time").toLocalTime();
                Haircuts hairStyle = Haircuts.valueOf(rs.getString("HaircutType"));
                String hairdresser =  rs.getString("hairdresser_name");
                String description = rs.getString("Description");
                Status status = Status.valueOf(rs.getString("status"));

                Booking adding = new Booking(id, name, phoneNr, date, time, hairStyle, hairdresser, description, status);
                bookings.add(adding);
            }
            return bookings;

        } catch (SQLException ex) {
            throw new SQLException("Fejl i indlæsning af kalender");
        }
    }

    public void insertBooking(Booking booking) throws SQLException {
        String sql = """
            INSERT INTO Booking
            (name, PhoneNum, Date, Time, HaircutType, Hairdresser, Description, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, booking.getName());
            ps.setString(2, booking.getPhoneNr());
            ps.setObject(3, booking.getDate());
            ps.setObject(4, booking.getTime());
            ps.setString(5, booking.getHaircutType().name());
            ps.setInt(6, booking.getHairdresserId());
            ps.setString(7,booking.getDescription());
            ps.setString(8,booking.getStatus().name());
            ps.executeUpdate();

        } catch (SQLException e){
            throw new SQLException("Fejl i tilføjelse af tidsbestilling"); //WIP
        }
    }

    public Booking getBookingById(int id) {
        String sql = """
                select booking.id, booking.name, booking.PhoneNum, booking.Date, booking.Time, booking.HaircutType, booking.Hairdresser, booking.Description, booking.status 
                from booking 
                inner join customer on customer.PhoneNUM = booking.PhoneNum
                inner join hairdresser on hairdresser.ID = booking.Hairdresser
                where booking.id = ?;
                """;

        try (Connection conn = db.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    String phoneNum = rs.getString("phoneNum");
                    LocalDate date = rs.getDate("date").toLocalDate();
                    LocalTime time = rs.getTime("time").toLocalTime();
                    Haircuts haircutType = Haircuts.valueOf(rs.getString("HaircutType"));
                    int hairdresser = rs.getInt("Hairdresser");
                    String description = rs.getString("Description");
                    Status status = Status.valueOf(rs.getString("status"));

                    Booking resultat = new Booking(name, phoneNum, date, time, haircutType, hairdresser, description, status);
                    return resultat;
                }
            } catch (SQLException e){
                throw new SQLException("Kunne ikke indlæse data");
            }

            } catch (SQLException e){
            throw new DataAccessException("Kunne ikke finde bookingen!" + e);
        }
        return null; //Returner anden besked?
    }
}
