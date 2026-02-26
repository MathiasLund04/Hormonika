package Repository.Customer;

import DAL.DBConfig;
import Model.Customer;
import Model.Person;
import Service.CustomerService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLCustomerRepository implements CustomerRepository {
    public final DBConfig db;

    public MySQLCustomerRepository(DBConfig db) {
        this.db = db;
    }

    public void createCustomerIfNotExist(String name, int phoneNr) throws SQLException {
        String sql = "INSERT INTO customers (name, phoneNr) VALUES (?, ?)";
            try(Connection c = db.getConnection();
                PreparedStatement ps = c.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setInt(2, phoneNr);
            } catch (SQLException e){
                throw new SQLException("Fejl i tilføjelse af Ny Kunde");
            }
    }

    public String getCustomerNameByPhoneNr(String phoneNr) {
        String  sql = "SELECT name FROM customers WHERE phoneNr = ?";
        try(Connection c = db.getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ps.setString(1,phoneNr);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return rs.getString("name");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Customer> getCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();

        String sql = "SELECT * FROM customers";

        try (Connection con = db.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while (rs.next()) {
                String name = rs.getString("name");
                String phoneNr = rs.getString("PhoneNum");

                Customer customer = new Customer(name,phoneNr);
                customers.add(customer);
            }
            return customers;
        } catch (SQLException e) {
            throw new SQLException("Fejl i indlæsning af kunder");
        }

    }
}
