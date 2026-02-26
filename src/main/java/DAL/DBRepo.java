package DAL;

import Model.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBRepo {
    private final DBConfig db;
    public DBRepo(DBConfig db) {
        this.db = db;
    }

    public String getHairdresserNamebyID(int id) throws SQLException {
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



}
