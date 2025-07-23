package model;

/**
 * The type Luggage.
 */
public class Luggage {

    private String id = null;
    private LuggageType type = null;
    private LuggageStatus status = LuggageStatus.BOOKED;
    /**
     * The Ticket.
     */
    Ticket ticket;

    /**
     * Instantiates a new Luggage.
     *
     * @param parTicket the par ticket
     * @throws InvalidTicket the invalid ticket
     */
    public Luggage( Ticket parTicket) throws InvalidTicket {

        if(parTicket != null){
            this.ticket = parTicket;
        }else{
            throw new InvalidTicket("Il bagaglio deve essere associato ad un biglietto!");
        }

    }

    /**
     * Instantiates a new Luggage.
     *
     * @param parType   the par type
     * @param parTicket the par ticket
     * @throws InvalidTicket the invalid ticket
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
     * Instantiates a new Luggage.
     *
     * @param parStatus the par status
     * @param parTicket the par ticket
     * @throws InvalidTicket the invalid ticket
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
     * Instantiates a new Luggage.
     *
     * @param parId     the par id
     * @param parType   the par type
     * @param parTicket the par ticket
     * @throws InvalidTicket the invalid ticket
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
     * Instantiates a new Luggage.
     *
     * @param parId     the par id
     * @param parStatus the par status
     * @param parTicket the par ticket
     * @throws InvalidTicket the invalid ticket
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
     * Instantiates a new Luggage.
     *
     * @param parId     the par id
     * @param parType   the par type
     * @param parStatus the par status
     * @param parTicket the par ticket
     * @throws InvalidTicket the invalid ticket
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
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public LuggageType getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(LuggageType type) {
        this.type = type;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public LuggageStatus getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(LuggageStatus status) {
        this.status = status;
    }

    /**
     * Gets ticket.
     *
     * @return the ticket
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Sets ticket.
     *
     * @param ticket the ticket
     */
    void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
