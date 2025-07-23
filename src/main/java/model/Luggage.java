package model;

public class Luggage {

    private String id = null;
    private LuggageType type = null;
    private LuggageStatus status = LuggageStatus.BOOKED;
    Ticket ticket;

    public Luggage( Ticket parTicket) throws InvalidTicket {

        if(parTicket != null){
            this.ticket = parTicket;
        }else{
            throw new InvalidTicket("Il bagaglio deve essere associato ad un biglietto!");
        }

    }

    public Luggage(LuggageType parType, Ticket parTicket) throws InvalidTicket {

        if(parTicket != null){
            this.type = parType;
            this.ticket = parTicket;
        }else{
            throw new InvalidTicket("Il bagaglio deve essere associato ad un biglietto!");
        }


    }

    public Luggage(LuggageStatus parStatus, Ticket parTicket) throws InvalidTicket {

        if(parTicket != null){
            this.status = parStatus;
            this.ticket = parTicket;
        }else{
            throw new InvalidTicket("Il bagaglio deve essere associato ad un biglietto!");
        }


    }

    public Luggage(String parId, LuggageType parType, Ticket parTicket) throws InvalidTicket {

        if(parTicket != null){
            this.id = parId;
            this.type = parType;
            this.ticket = parTicket;
        }else{
            throw new InvalidTicket("Il bagaglio deve essere associato ad un biglietto!");
        }


    }

    public Luggage(String parId, LuggageStatus parStatus, Ticket parTicket) throws InvalidTicket {

        if(parTicket != null){
            this.id = parId;
            this.status = parStatus;
            this.ticket = parTicket;
        }else{
            throw new InvalidTicket("Il bagaglio deve essere associato ad un biglietto!");
        }


    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LuggageType getType() {
        return type;
    }

    public void setType(LuggageType type) {
        this.type = type;
    }

    public LuggageStatus getStatus() {
        return status;
    }

    public void setStatus(LuggageStatus status) {
        this.status = status;
    }

    public Ticket getTicket() {
        return ticket;
    }

    void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
