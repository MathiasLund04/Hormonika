package Model;

public class Person {
    private String name;
    private String phoneNr;

    public Person(String name, String phoneNr) {
        this.name = name;
        this.phoneNr = phoneNr;
    }

    public String getName() {return name;}
    public String getPhoneNr() {return phoneNr;}

    public void setName(String name) {this.name = name;}
    public void setPhoneNr(String phoneNr) {this.phoneNr = phoneNr;}
}
