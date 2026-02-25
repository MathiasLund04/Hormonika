package People;

public class Person {
    private String name;
    private int phoneNr;

    public Person(String name, int phoneNr) {
        this.name = name;
        this.phoneNr = phoneNr;
    }

    public String getName() {return name;}
    public int getPhoneNr() {return phoneNr;}

    public void setName(String name) {this.name = name;}
    public void setPhoneNr(int phoneNr) {this.phoneNr = phoneNr;}
}
