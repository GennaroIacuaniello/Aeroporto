package model;

/**
 * Represents a piece of luggage in the airport management system.
 * <p>
 * This class encapsulates all information related to luggage items associated with
 * passenger tickets. Luggage is a fundamental component of the baggage handling
 * system, tracking items from booking through to final retrieval or reporting
 * as lost. Each luggage item must be associated with a valid ticket to maintain
 * system integrity and enable proper baggage tracking.
 * </p>
 * <p>
 * The luggage maintains essential information including unique identification,
 * type classification (carry-on or checked), current status throughout the
 * handling process, and the associated ticket reference. The status tracking
 * enables comprehensive baggage management from initial booking to final delivery.
 * </p>
 * <p>
 * Key features include:
 * </p>
 * <ul>
 *   <li>Unique luggage identification for tracking purposes</li>
 *   <li>Type classification (CARRY_ON, CHECKED)</li>
 *   <li>Status tracking throughout handling lifecycle</li>
 *   <li>Mandatory ticket association for passenger linkage</li>
 *   <li>Integration with baggage handling operations</li>
 *   <li>Support for lost luggage reporting and recovery</li>
 * </ul>
 * <p>
 * The class provides multiple constructors to accommodate different scenarios
 * of luggage creation, from basic ticket association to comprehensive luggage
 * details with identification, type, and status information. All luggage items
 * require a valid ticket association to ensure proper passenger linkage and
 * maintain data integrity within the system.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see LuggageType
 * @see LuggageStatus
 * @see Ticket
 * @see Passenger
 * @see InvalidTicket
 * @see controller.LuggageController
 */
public class Luggage {

    /**
     * The unique identifier for this luggage item.
     * <p>
     * This field stores the luggage identification string used for tracking
     * and management purposes. It can be null if no specific ID has been
     * assigned yet, but is typically set after check-in procedures when
     * physical baggage tags are created.
     * </p>
     */
    private String id = null;
    
    /**
     * The type classification of this luggage item.
     * <p>
     * This field indicates whether the luggage is carry-on baggage that
     * remains with the passenger or checked baggage that goes into the
     * aircraft cargo hold. It can be null if the type has not been
     * determined yet during the booking process.
     * </p>
     *
     * @see LuggageType
     */
    private LuggageType type = null;
    
    /**
     * The current status of this luggage item in the handling process.
     * <p>
     * This field tracks the luggage throughout its lifecycle from initial
     * booking to final delivery or loss reporting. It defaults to BOOKED
     * status when luggage is first created, progressing through various
     * states as it moves through the baggage handling system.
     * </p>
     *
     * @see LuggageStatus
     */
    private LuggageStatus status = LuggageStatus.BOOKED;
    
    /**
     * The ticket associated with this luggage item.
     * <p>
     * This field maintains the mandatory association between luggage and
     * the passenger's ticket. This relationship is essential for passenger
     * identification, baggage claim processes, and maintaining system
     * integrity. The field has package visibility to allow controlled
     * access from related classes.
     * </p>
     *
     * @see Ticket
     */
    Ticket ticket;

    /**
     * Constructs a new luggage item with minimal ticket association.
     * <p>
     * Creates a luggage item with only the mandatory ticket association.
     * The luggage will have default status (BOOKED) and no specific type
     * or identification assigned. This constructor is used when luggage
     * details are to be set later in the process.
     * </p>
     *
     * @param parTicket the ticket this luggage is associated with
     * @throws InvalidTicket if the ticket is null
     */
    public Luggage( Ticket parTicket) throws InvalidTicket {

        if(parTicket != null){
            this.ticket = parTicket;
        }else{
            throw new InvalidTicket("Il bagaglio deve essere associato ad un biglietto!");
        }

    }

    /**
     * Constructs a new luggage item with type specification.
     * <p>
     * Creates a luggage item with a specified type (carry-on or checked)
     * and the mandatory ticket association. The luggage will have default
     * status (BOOKED) and no specific identification assigned yet.
     * </p>
     *
     * @param parType the type of luggage (CARRY_ON or CHECKED)
     * @param parTicket the ticket this luggage is associated with
     * @throws InvalidTicket if the ticket is null
     */
    public Luggage(LuggageType parType, Ticket parTicket) throws InvalidTicket {

        if(parTicket != null){
            this.type = parType;
            this.ticket = parTicket;
        }else{
            throw new InvalidTicket("Il bagaglio deve essere associato ad un biglietto!");
        }


    }

    /**
     * Constructs a new luggage item with status specification.
     * <p>
     * Creates a luggage item with a specified status and the mandatory
     * ticket association. This constructor is useful when creating luggage
     * objects from existing data where the status is already known,
     * such as during system imports or status updates.
     * </p>
     *
     * @param parStatus the initial status of the luggage
     * @param parTicket the ticket this luggage is associated with
     * @throws InvalidTicket if the ticket is null
     */
    public Luggage(LuggageStatus parStatus, Ticket parTicket) throws InvalidTicket {

        if(parTicket != null){
            this.status = parStatus;
            this.ticket = parTicket;
        }else{
            throw new InvalidTicket("Il bagaglio deve essere associato ad un biglietto!");
        }


    }

    /**
     * Constructs a new luggage item with identification and type.
     * <p>
     * Creates a luggage item with specific identification and type
     * information along with the mandatory ticket association. This
     * constructor is used when luggage is being tagged and typed
     * during check-in procedures.
     * </p>
     *
     * @param parId the unique identifier for the luggage
     * @param parType the type of luggage (CARRY_ON or CHECKED)
     * @param parTicket the ticket this luggage is associated with
     * @throws InvalidTicket if the ticket is null
     */
    public Luggage(String parId, LuggageType parType, Ticket parTicket) throws InvalidTicket {

        if(parTicket != null){
            this.id = parId;
            this.type = parType;
            this.ticket = parTicket;
        }else{
            throw new InvalidTicket("Il bagaglio deve essere associato ad un biglietto!");
        }


    }

    /**
     * Constructs a new luggage item with identification and status.
     * <p>
     * Creates a luggage item with specific identification and status
     * information along with the mandatory ticket association. This
     * constructor is useful for importing existing luggage data or
     * creating luggage objects with known status states.
     * </p>
     *
     * @param parId the unique identifier for the luggage
     * @param parStatus the current status of the luggage
     * @param parTicket the ticket this luggage is associated with
     * @throws InvalidTicket if the ticket is null
     */
    public Luggage(String parId, LuggageStatus parStatus, Ticket parTicket) throws InvalidTicket {

        if(parTicket != null){
            this.id = parId;
            this.status = parStatus;
            this.ticket = parTicket;
        }else{
            throw new InvalidTicket("Il bagaglio deve essere associato ad un biglietto!");
        }


    }

    /**
     * Constructs a new luggage item with complete information.
     * <p>
     * Creates a comprehensive luggage item with all available information
     * including identification, type, status, and ticket association.
     * This constructor is used when creating complete luggage objects
     * from database records or when all information is available
     * during luggage creation.
     * </p>
     *
     * @param parId the unique identifier for the luggage
     * @param parType the type of luggage (CARRY_ON or CHECKED)
     * @param parStatus the current status of the luggage
     * @param parTicket the ticket this luggage is associated with
     * @throws InvalidTicket if the ticket is null
     */
    public Luggage(String parId, LuggageType parType, LuggageStatus parStatus, Ticket parTicket) throws InvalidTicket {

        if(parTicket != null){
            this.id = parId;
            this.type = parType;
            this.status = parStatus;
            this.ticket = parTicket;
        }else{
            throw new InvalidTicket("Il bagaglio deve essere associato ad un biglietto!");
        }


    }

    /**
     * Gets the unique identifier of this luggage item.
     * <p>
     * Returns the luggage identification string used for tracking
     * and management purposes. This ID is typically assigned during
     * check-in procedures when physical baggage tags are created.
     * </p>
     *
     * @return the luggage identifier, can be null if not assigned
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this luggage item.
     * <p>
     * Updates the luggage identification string, typically used
     * during check-in procedures when baggage tags are assigned
     * or when updating luggage records with new identification.
     * </p>
     *
     * @param id the new luggage identifier
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the type classification of this luggage item.
     * <p>
     * Returns the luggage type indicating whether it is carry-on
     * baggage or checked baggage. This information is essential
     * for proper baggage handling and security procedures.
     * </p>
     *
     * @return the luggage type, can be null if not specified
     * @see LuggageType
     */
    public LuggageType getType() {
        return type;
    }

    /**
     * Sets the type classification for this luggage item.
     * <p>
     * Updates the luggage type to specify whether it is carry-on
     * or checked baggage. This information affects handling
     * procedures and security requirements throughout the
     * baggage processing system.
     * </p>
     *
     * @param type the new luggage type
     * @see LuggageType
     */
    public void setType(LuggageType type) {
        this.type = type;
    }

    /**
     * Gets the current status of this luggage item.
     * <p>
     * Returns the current status indicating where the luggage
     * is in the handling process. This status is used for
     * tracking, passenger notifications, and operational
     * decision making throughout the baggage system.
     * </p>
     *
     * @return the current luggage status, never null
     * @see LuggageStatus
     */
    public LuggageStatus getStatus() {
        return status;
    }

    /**
     * Sets the current status of this luggage item.
     * <p>
     * Updates the luggage status to reflect its current state
     * in the handling process. Status updates are critical for
     * tracking luggage movement, passenger notifications, and
     * operational efficiency in the baggage handling system.
     * </p>
     *
     * @param status the new luggage status
     * @see LuggageStatus
     */
    public void setStatus(LuggageStatus status) {
        this.status = status;
    }

    /**
     * Gets the ticket associated with this luggage item.
     * <p>
     * Returns the ticket that this luggage is associated with.
     * This association is mandatory and provides the link between
     * luggage and the passenger, enabling proper baggage claim
     * and passenger notification processes.
     * </p>
     *
     * @return the associated ticket, never null
     * @see Ticket
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Sets the ticket associated with this luggage item.
     * <p>
     * Updates the ticket association for this luggage. This method
     * has package visibility to allow controlled updates from related
     * classes while maintaining the integrity of the luggage-ticket
     * relationship. This method is typically used internally by the
     * system to establish bidirectional relationships.
     * </p>
     *
     * @param ticket the new associated ticket
     * @see Ticket
     */
    void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}