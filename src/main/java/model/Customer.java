package model;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User{

    private ArrayList<Booking> bookings;

    public Customer(String parUsername, String parEmail, String parHashedPassword){
        super(parUsername, parEmail, parHashedPassword);
        this.bookings = new ArrayList<>(0);
    }

    public Customer(String parUsername, String parEmail, String parHashedPassword, List<Booking> parBookings){
        super(parUsername, parEmail, parHashedPassword);
        this.bookings = (ArrayList<Booking>) parBookings;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = (ArrayList<Booking>) bookings;
    }

}
