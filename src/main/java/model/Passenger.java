package model;
import java.sql.Date;

/**
 * The type Passenger.
 */
public class Passenger {

    private String firstName = null;
    private String lastName = null;
    private final String passengerSSN;
    private Date birthDate = null;

    /**
     * Instantiates a new Passenger.
     *
     * @param parFirstName the par first name
     * @param parLastName  the par last name
     * @param parSSN       the par ssn
     * @param parBirthDate the par birth date
     * @param parTicket    the par ticket
     * @throws InvalidTicket          the invalid ticket
     * @throws InvalidPassengerNumber the invalid passenger number
     */
    public Passenger(String parFirstName, String parLastName, String parSSN, Date parBirthDate, Ticket parTicket) throws InvalidTicket, InvalidPassengerNumber {

        if(parTicket != null){
            this.firstName = parFirstName;
            this.lastName = parLastName;
            if(parSSN != null){
                this.passengerSSN = parSSN;
            }else{
                throw new InvalidPassengerNumber("Un passeggero non può non avere SSN!");
            }

            this.birthDate = parBirthDate;
        }else{
            throw new InvalidTicket("un passeggero deve essere associato ad un biglietto!");
        }


    }

    /**
     * Instantiates a new Passenger.
     *
     * @param parSSN    the par ssn
     * @param parTicket the par ticket
     * @throws InvalidTicket          the invalid ticket
     * @throws InvalidPassengerNumber the invalid passenger number
     */
    public Passenger(String parSSN, Ticket parTicket) throws InvalidTicket, InvalidPassengerNumber {

        if(parTicket != null){
            if(parSSN != null){
                this.passengerSSN = parSSN;
            }else{
                throw new InvalidPassengerNumber("Un passeggero non può non avere SSN!");
            }
        }else{
            throw new InvalidTicket("un passeggero deve essere associato ad un biglietto!");
        }


    }

    /**
     * Instantiates a new Passenger.
     *
     * @param parFirstName the par first name
     * @param parLastName  the par last name
     * @param parSSN       the par ssn
     * @param parTicket    the par ticket
     * @throws InvalidTicket          the invalid ticket
     * @throws InvalidPassengerNumber the invalid passenger number
     */
    public Passenger(String parFirstName, String parLastName, String parSSN, Ticket parTicket) throws InvalidTicket, InvalidPassengerNumber {

        if(parTicket != null){
            if(parSSN != null){
                this.passengerSSN = parSSN;
            }else{
                throw new InvalidPassengerNumber("Un passeggero non può non avere SSN!");
            }
            this.firstName = parFirstName;
            this.lastName = parLastName;
        }else{
            throw new InvalidTicket("un passeggero deve essere associato ad un biglietto!");
        }

    }

    /**
     * Instantiates a new Passenger.
     *
     * @param parSSN       the par ssn
     * @param parBirthDate the par birth date
     * @param parTicket    the par ticket
     * @throws InvalidTicket          the invalid ticket
     * @throws InvalidPassengerNumber the invalid passenger number
     */
    public Passenger(String parSSN, Date parBirthDate, Ticket parTicket) throws InvalidTicket, InvalidPassengerNumber {

        if(parTicket != null){
            if(parSSN != null){
                this.passengerSSN = parSSN;
            }else{
                throw new InvalidPassengerNumber("Un passeggero non può non avere SSN!");
            }
            this.birthDate = parBirthDate;
        }else{
            throw new InvalidTicket("un passeggero deve essere associato ad un biglietto!");
        }


    }

    /**
     * Instantiates a new Passenger.
     *
     * @param parFirstName    the par first name
     * @param parLastName     the par last name
     * @param parSSN          the par ssn
     * @param parBirthDate    the par birth date
     * @param parTicketNumber the par ticket number
     * @param parSeat         the par seat
     * @param parCheckedIn    the par checked in
     * @param parStatus       the par status
     * @param parBookingDate  the par booking date
     * @param parBuyer        the par buyer
     * @param parBookedFlight the par booked flight
     * @throws InvalidTicket          the invalid ticket
     * @throws InvalidFlight          the invalid flight
     * @throws InvalidBooking         the invalid booking
     * @throws InvalidPassengerNumber the invalid passenger number
     */
    public Passenger(String parFirstName, String parLastName, String parSSN, Date parBirthDate,
                     String parTicketNumber, int parSeat, boolean parCheckedIn,
                     BookingStatus parStatus, Date parBookingDate, Customer parBuyer, Flight parBookedFlight) throws InvalidTicket, InvalidFlight, InvalidBooking, InvalidPassengerNumber {

        if(parSSN != null){
            this.passengerSSN = parSSN;
        }else{
            throw new InvalidPassengerNumber("Un passeggero non può non avere SSN!");
        }
        this.firstName = parFirstName;
        this.lastName = parLastName;
        this.birthDate = parBirthDate;
        new Booking(parStatus, parBookingDate, parBuyer, parBookedFlight, parTicketNumber, parSeat, parCheckedIn, this);


    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets passenger ssn.
     *
     * @return the passenger ssn
     */
    public String getPassengerSSN() {
        return passengerSSN;
    }

    /**
     * Gets birth date.
     *
     * @return the birth date
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets birth date.
     *
     * @param birthDate the birth date
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}
