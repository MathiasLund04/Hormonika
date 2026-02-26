package Service;

import Model.Person;
import DAL.DBConfig;
import Repository.Customer.CustomerRepository;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private final DBConfig db = new DBConfig();
    private CustomerRepository cRepo;
    private List<Person> customers;


    public CustomerService(CustomerRepository cRepo) {
        this.cRepo = cRepo;
        try {
            customers = new ArrayList<>(cRepo.getCustomers());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public Person findCustomer(String name, String phoneNr) {
        for (Person c : customers) {
            if (c.getName().equals(name) && c.getPhoneNr().equals(phoneNr)) {
                return c;
            }
        }
        return null;
    }

    public Person createCustomerIfNotExist(String name, String phoneNr) {
        Person existingCustomer = findCustomer(name, phoneNr);
        if (existingCustomer != null) {
            return existingCustomer;
        }

        Person newCustomer = new Person(name, phoneNr);
        customers.add(newCustomer);
        return newCustomer;
    }

    public List<Person> getCustomers() {
        return new ArrayList<>(customers);
    }
}
