package Model;

public class Customer extends Person {
    private String phoneNum;

    public Customer(String name,String phoneNum) {
        super(name);
        this.phoneNum = phoneNum;
    }

    public String getPhoneNum() {
        return phoneNum;
    }
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }



}
