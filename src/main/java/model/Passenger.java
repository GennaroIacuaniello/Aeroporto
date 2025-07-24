package model;
import java.sql.Date;

/**
 * Represents a passenger in the airport management system.
 * <p>
 * This class encapsulates all information related to an individual passenger
 * who travels on flights managed by the airport system. A passenger is
 * associated with tickets and represents the person who will actually travel,
 * containing personal identification and travel-related information.
 * </p>
 * <p>
 * The passenger maintains essential personal information including first name,
 * last name, social security number (SSN), and birthdate. The SSN is required
 * and immutable as it serves as the primary identification for the passenger
 * throughout the system.
 * </p>
 * <p>
 * Key features include:
 * </p>
 * <ul>
 *   <li>Unique passenger identification via SSN</li>
 *   <li>Mandatory ticket association for travel validity</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Ticket
 * @see Booking
 * @see Flight
 * @see Customer
 * @see InvalidPassengerNumber
 * @see InvalidTicket
 */
public class Passenger {

    /**
     * The first name of the passenger.
     * <p>
     * This field stores the passenger's given name and can be null if
     * not provided during passenger creation. It is mutable to allow
     * for updates and corrections to passenger information (only before flight's
     * opening of check-ins).
     * </p>
     */
    private String firstName = null;
    
    /**
     * The last name of the passenger.
     * <p>
     * This field stores the passenger's family name and can be null if
     * not provided during passenger creation. It is mutable to allow
     * for updates and corrections to passenger information (only before flight's
     * opening of check-ins).
     * </p>
     */
    private String lastName = null;
    
    /**
     * The social security number of the passenger.
     * <p>
     * This field stores the passenger's unique identification number
     * and is final as it cannot be changed after passenger creation.
     * The SSN is mandatory for all passengers and serves as the
     * primary identification throughout the system.
     * </p>
     */
    private final String passengerSSN;
    
    /**
     * The birthdate of the passenger.
     * <p>
     * This field stores the passenger's date of birth and can be null
     * if not provided during passenger creation. It is mutable to allow
     * for updates when additional information becomes available(only before flight's
     * opening of check-ins).
     * </p>
     */
    private Date birthDate = null;

    /**
     * Constructs a new passenger with complete personal information.
     * <p>
     * Creates a passenger with all personal details including name,
     * SSN, and birthdate. This constructor is used when complete
     * passenger information is available and must be associated
     * with a valid ticket.
     * </p>
     *
     * @param parFirstName the passenger's first name
     * @param parLastName the passenger's last name
     * @param parSSN the passenger's social security number, must not be null
     * @param parBirthDate the passenger's birthdate
     * @param parTicket the ticket associated with this passenger
     * @throws InvalidTicket if the ticket is null
     * @throws InvalidPassengerNumber if the SSN is null
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
     * Constructs a new passenger with minimal identification information.
     * <p>
     * Creates a passenger with only SSN identification, used when
     * minimal passenger information is available. Additional details
     * can be added later through setter methods.
     * </p>
     *
     * @param parSSN the passenger's social security number, must not be null
     * @param parTicket the ticket associated with this passenger
     * @throws InvalidTicket if the ticket is null
     * @throws InvalidPassengerNumber if the SSN is null
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
     * Constructs a new passenger with name and identification information.
     * <p>
     * Creates a passenger with name details and SSN identification.
     * </p>
     *
     * @param parFirstName the passenger's first name
     * @param parLastName the passenger's last name
     * @param parSSN the passenger's social security number, must not be null
     * @param parTicket the ticket associated with this passenger
     * @throws InvalidTicket if the ticket is null
     * @throws InvalidPassengerNumber if the SSN is null
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
     * Constructs a new passenger with identification and birthdate information.
     * <p>
     * Creates a passenger with SSN and birthdate.
     * </p>
     *
     * @param parSSN the passenger's social security number, must not be null
     * @param parBirthDate the passenger's birthdate
     * @param parTicket the ticket associated with this passenger
     * @throws InvalidTicket if the ticket is null
     * @throws InvalidPassengerNumber if the SSN is null
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
     * Constructs a new passenger with integrated booking creation.
     * <p>
     * Creates a passenger with complete personal information and
     * simultaneously creates an associated booking with the provided
     * booking details.
     * </p>
     *
     * @param parFirstName the passenger's first name
     * @param parLastName the passenger's last name
     * @param parSSN the passenger's social security number, must not be null
     * @param parBirthDate the passenger's birthdate
     * @param parTicketNumber the ticket number for the booking
     * @param parSeat the seat assignment
     * @param parCheckedIn the check-in status
     * @param parStatus the booking status
     * @param parBookingDate the booking creation date
     * @param parBuyer the customer making the booking
     * @param parBookedFlight the flight being booked
     * @throws InvalidTicket if ticket creation fails
     * @throws InvalidFlight if the flight is invalid
     * @throws InvalidBooking if booking creation fails
     * @throws InvalidPassengerNumber if the SSN is null
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
     * Gets the first name of this passenger.
     * <p>
     * Returns the passenger's given name. This value can be null
     * if the first name was not provided during passenger creation
     * or has not been set after.
     * </p>
     *
     * @return the passenger's first name, can be null
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of this passenger.
     * <p>
     * Updates the passenger's given name. This method allows for
     * corrections or updates to passenger information when new
     * data becomes available.
     * </p>
     *
     * @param firstName the new first name for the passenger
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of this passenger.
     * <p>
     * Returns the passenger's family name. This value can be null
     * if the last name was not provided during passenger creation
     * or has not been set after.
     * </p>
     *
     * @return the passenger's last name, can be null
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of this passenger.
     * <p>
     * Updates the passenger's family name. This method allows for
     * corrections or updates to passenger information when new
     * data becomes available.
     * </p>
     *
     * @param lastName the new last name for the passenger
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the social security number of this passenger.
     * <p>
     * Returns the passenger's unique identification number.
     * This value is never null as it is required for all passengers
     * and is immutable once set during passenger creation.
     * </p>
     *
     * @return the passenger's SSN, never null
     */
    public String getPassengerSSN() {
        return passengerSSN;
    }

    /**
     * Gets the birthdate of this passenger.
     * <p>
     * Returns the passenger's date of birth. This value can be null
     * if the birthdate was not provided during passenger creation
     * or has not been set after.
     * </p>
     *
     * @return the passenger's birth date, can be null
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the birthdate of this passenger.
     * <p>
     * Updates the passenger's date of birth. This method allows for
     * corrections or updates to passenger information when new
     * data becomes available or when initially missing information
     * is provided.
     * </p>
     *
     * @param birthDate the new birthdate for the passenger
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}