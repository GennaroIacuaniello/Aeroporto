package model;

import java.sql.Date;

/**
 * The Passenger class represents an individual traveler in the airport management system.
 * 
 * <p>This class contains all personal information required for air travel including
 * identification details, contact information, and travel-specific data. Each passenger
 * is associated with a specific ticket and must provide valid identification information
 * for security and regulatory compliance.</p>
 * 
 * <p>Key features of the passenger system:</p>
 * <ul>
 *   <li>Personal identification information (name, SSN, birth date)</li>
 *   <li>Travel document validation</li>
 *   <li>Age calculation for fare and service purposes</li>
 *   <li>Association with ticket for travel context</li>
 *   <li>Compliance with aviation security requirements</li>
 * </ul>
 * 
 * <p>The passenger information is used throughout the system for:</p>
 * <ul>
 *   <li>Ticket issuance and validation</li>
 *   <li>Security screening and check-in procedures</li>
 *   <li>Age-based service provision (child, adult, senior)</li>
 *   <li>Emergency contact and notification services</li>
 *   <li>Loyalty program and customer service</li>
 * </ul>
 * 
 * <p>All personal information is handled in compliance with privacy regulations
 * and aviation security requirements. The SSN field can accommodate various
 * types of identification numbers including social security numbers, passport
 * numbers, or national identification numbers depending on the jurisdiction.</p>
 * 
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Ticket
 * @see Booking
 */
public class Passenger {

    /**
     * The passenger's first name.
     * Used for identification purposes and must match travel documents.
     * This field is immutable once set during passenger creation.
     */
    private final String firstName;
    
    /**
     * The passenger's last name (surname).
     * Used for identification purposes and must match travel documents.
     * This field is immutable once set during passenger creation.
     */
    private final String lastName;
    
    /**
     * The passenger's social security number or identification number.
     * This can be a social security number, passport number, national ID,
     * or other government-issued identification number depending on jurisdiction.
     * This field is immutable once set during passenger creation.
     */
    private final String passengerSSN;
    
    /**
     * The passenger's birth date.
     * Used for age verification, fare calculation, and service provision.
     * This field is immutable once set during passenger creation.
     */
    private final Date birthDate;

    /**
     * Constructs a new Passenger with complete information.
     * 
     * <p>Creates a passenger with all required personal information and
     * associates them with a specific ticket.</p>
     *
     * @param parFirstName the passenger's first name (must not be null or empty)
     * @param parLastName the passenger's last name (must not be null or empty)
     * @param parSSN the passenger's identification number (must not be null or empty)
     * @param parBirthDate the passenger's birth date (must not be null or future date)
     * @param parTicket the ticket associated with this passenger (must not be null)
     * @throws InvalidPassengerNumber if passenger information validation fails
     */
    public Passenger(String parFirstName, String parLastName, String parSSN, Date parBirthDate, Ticket parTicket)
            throws InvalidPassengerNumber {
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
     * Constructs a new Passenger with some information.
     * 
     * <p>Creates a passenger only SSN as personal information and
     * associates it with a specific ticket.</p>
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
     * Constructs a new Passenger with some information.
     * 
     * <p>Creates a passenger only first name, last name and SSN as personal information and
     * associates them with a specific ticket.</p>
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
     * Constructs a new Passenger with some information.
     * 
     * <p>Creates a passenger only SSN and birth date as personal information and
     * associates them with a specific ticket.</p>
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
     * Constructs a new Passenger with complete information.
     * 
     * <p>Creates a passenger with all required personal information and
     * associates them with a specific booking and ticket.</p>
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
     * Gets the passenger's first name.
     * 
     * <p>Returns the passenger's first name as provided during creation.
     * This name should match the name on travel documents.</p>
     *
     * @return the passenger's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the passenger's last name.
     * 
     * <p>Returns the passenger's last name (surname) as provided during creation.
     * This name should match the name on travel documents.</p>
     *
     * @return the passenger's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the passenger's identification number.
     * 
     * <p>Returns the passenger's SSN or other identification number as provided
     * during creation. This information is used for security verification and
     * regulatory compliance.</p>
     *
     * @return the passenger's identification number
     */
    public String getPassengerSSN() {
        return passengerSSN;
    }

    /**
     * Gets the passenger's birth date.
     * 
     * <p>Returns the passenger's birth date as provided during creation.
     * This information is used for age verification and service provision.</p>
     *
     * @return the passenger's birth date
     */
    public Date getBirthDate() {
        return birthDate;
    }

}