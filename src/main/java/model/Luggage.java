package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi

 */

/*POSSIBLE
    inserire attributi e relative gestioni per il peso del bagaglio
    private int grammi;     //e poi stampiamo grammi/1000 per stampare il peso in kg
    eventuale nuovo possibile status bagaglio ex. "preso in carico",
    se si modificare lo status assengato nel costruttore
 */

public class Luggage {

    private Integer id = null;
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

    public Luggage(int parId, LuggageType parType, Ticket parTicket) throws InvalidTicket {

        if(parTicket != null){
            this.id = parId;
            this.type = parType;
            this.status = LuggageStatus.BOOKED;
            this.ticket = parTicket;
        }else{
            throw new InvalidTicket("Il bagaglio deve essere associato ad un biglietto!");
        }


    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
