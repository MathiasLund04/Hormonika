package Service;

import People.Person;
import org.example.hormonika.DBConfig;
import org.example.hormonika.DBRepo;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private DBConfig db;
    private DBRepo dbRepo = new DBRepo(db);

    public CustomerService(DBRepo dbRepo) {
        this.dbRepo = dbRepo;
    }


    private List<Person> customers = new ArrayList<>();

    public Person findCustomer(String name, int phoneNr) {
        for (Person c : customers) {
            if (c.getName().equals(name) && c.getPhoneNr() == phoneNr) {
                return c;
            }
        }
        return null;
    }

    public Person createCustomerIfNotExist(String name, int phoneNr) {
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
