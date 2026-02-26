package Repository.Customer;

import Model.Person;

import java.sql.SQLException;
import java.util.List;

public interface CustomerRepository {
    void createCustomerIfNotExist(String name, int phoneNr) throws SQLException;
    String getCustomerNameByPhoneNr(String phoneNr);
    List<Person> getCustomers() throws SQLException;
}
