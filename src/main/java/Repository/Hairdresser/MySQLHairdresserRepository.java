package Repository.Hairdresser;

import DAL.DBConfig;
import Model.Hairdresser;
import Model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLHairdresserRepository implements HairdresserRepository {

    private final DBConfig db;

    public MySQLHairdresserRepository(DBConfig db) {
        this.db = db;
    }

    public String getHairdresserNameById(int id) throws SQLException {
        String sql = "SELECT name FROM hairdresser WHERE id = ?";

        try(Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1,id);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }

        }
        return null;
    }

    public List<Hairdresser> getHairdressers() throws SQLException {
        List<Hairdresser> hairdressers = new ArrayList<>();

        String sql = "SELECT * FROM hairdresser";

        try (Connection con = db.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String username = rs.getString("PhoneNUM");
                String password = rs.getString("Password");

                Hairdresser hairdresser = new Hairdresser(name, id, username,password);
                hairdressers.add(hairdresser);
            }
            return hairdressers;

        } catch (SQLException e) {
            throw new SQLException("Fejl i indlæsning af kunder");
        }

    }
}
