package model;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Customer.
 */
public class Customer extends User{

    private ArrayList<Booking> bookings;

    /**
     * Instantiates a new Customer.
     *
     * @param parUsername       the par username
     * @param parEmail          the par email
     * @param parHashedPassword the par hashed password
     */
    public Customer(String parUsername, String parEmail, String parHashedPassword){
        super(parUsername, parEmail, parHashedPassword);
        this.bookings = new ArrayList<>(0);
    }

    /**
     * Instantiates a new Customer.
     *
     * @param parUsername       the par username
     * @param parEmail          the par email
     * @param parHashedPassword the par hashed password
     * @param parBookings       the par bookings
     */
    public Customer(String parUsername, String parEmail, String parHashedPassword, List<Booking> parBookings){
        super(parUsername, parEmail, parHashedPassword);
        this.bookings = (ArrayList<Booking>) parBookings;
    }

    /**
     * Gets bookings.
     *
     * @return the bookings
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Sets bookings.
     *
     * @param bookings the bookings
     */
    public void setBookings(List<Booking> bookings) {
        this.bookings = (ArrayList<Booking>) bookings;
    }

}
