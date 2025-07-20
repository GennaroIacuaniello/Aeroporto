package model;
import java.sql.Date;
import java.util.ArrayList;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi
    - controllare associazione con Booking
    - aggiungere una funzione update_flight() dopo l'aggiunta o rimozione di un bagaglio
 */

public class Passenger {

    private String firstName = null;
    private String lastName = null;
    private final String SSN;
    private Date birthDate = null;

    public Passenger(String parFirstName, String parLastName, String parSSN, Date parBirthDate, Ticket parTicket) throws InvalidTicket, InvalidPassengerNumber {

        if(parTicket != null){
            this.firstName = parFirstName;
            this.lastName = parLastName;
            if(parSSN != null){
                this.SSN = parSSN;
            }else{
                throw new InvalidPassengerNumber("Un passeggero non può non avere SSN!");
            }

            this.birthDate = parBirthDate;
        }else{
            throw new InvalidTicket("un passeggero deve essere associato ad un biglietto!");
        }


    }

    public Passenger(String parSSN, Ticket parTicket) throws InvalidTicket, InvalidPassengerNumber {

        if(parTicket != null){
            if(parSSN != null){
                this.SSN = parSSN;
            }else{
                throw new InvalidPassengerNumber("Un passeggero non può non avere SSN!");
            }
        }else{
            throw new InvalidTicket("un passeggero deve essere associato ad un biglietto!");
        }


    }

    public Passenger(String parFirstName, String parLastName, String parSSN, Ticket parTicket) throws InvalidTicket, InvalidPassengerNumber {

        if(parTicket != null){
            if(parSSN != null){
                this.SSN = parSSN;
            }else{
                throw new InvalidPassengerNumber("Un passeggero non può non avere SSN!");
            }
            this.firstName = parFirstName;
            this.lastName = parLastName;
        }else{
            throw new InvalidTicket("un passeggero deve essere associato ad un biglietto!");
        }

    }

    public Passenger(String parSSN, Date parBirthDate, Ticket parTicket) throws InvalidTicket, InvalidPassengerNumber {

        if(parTicket != null){
            if(parSSN != null){
                this.SSN = parSSN;
            }else{
                throw new InvalidPassengerNumber("Un passeggero non può non avere SSN!");
            }
            this.birthDate = parBirthDate;
        }else{
            throw new InvalidTicket("un passeggero deve essere associato ad un biglietto!");
        }


    }

    public Passenger(String parFirstName, String parLastName, String parSSN, Date parBirthDate,
                     String parTicketNumber, int parSeat, boolean parCheckedIn,
                     BookingStatus parStatus, Date parBookingDate, Customer parBuyer, Flight parBookedFlight) throws InvalidTicket, InvalidFlight, InvalidBooking, InvalidPassengerNumber {

        if(parSSN != null){
            this.SSN = parSSN;
        }else{
            throw new InvalidPassengerNumber("Un passeggero non può non avere SSN!");
        }
        this.firstName = parFirstName;
        this.lastName = parLastName;
        this.birthDate = parBirthDate;
        new Booking(parStatus, parBookingDate, parBuyer, parBookedFlight, parTicketNumber, parSeat, parCheckedIn, this);


    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSSN() {
        return SSN;
    }

    //non si può modificare l'SSN
    /*
    public void setSSN(String SSN) {
        this.SSN = SSN;
    }*/

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

}
