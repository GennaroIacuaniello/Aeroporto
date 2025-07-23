package model;

/**
 * Rappresenta un bagaglio associato ad un biglietto aereo.
 * 
 * <p>La classe Luggage gestisce le informazioni relative ai bagagli dei passeggeri,
 * inclusi ID univoco, tipo di bagaglio, stato corrente e biglietto associato.
 * Ogni bagaglio deve essere obbligatoriamente associato ad un biglietto valido.</p>
 * 
 * <p>Gli stati possibili del bagaglio sono definiti nell'enum {@link LuggageStatus}
 * e i tipi di bagaglio nell'enum {@link LuggageType}.</p>
 * 
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see LuggageStatus
 * @see LuggageType
 * @see Ticket
 */
public class Luggage {

    /**
     * Identificatore univoco del bagaglio.
     * Può essere null se non ancora assegnato.
     */
    private String id = null;
    
    /**
     * Tipo di bagaglio (es. bagaglio a mano, bagaglio da stiva).
     * Può essere null se non specificato.
     */
    private LuggageType type = null;
    
    /**
     * Stato corrente del bagaglio.
     * Inizializzato di default a {@link LuggageStatus#BOOKED}.
     */
    private LuggageStatus status = LuggageStatus.BOOKED;
    
    /**
     * Biglietto a cui è associato questo bagaglio.
     * Non può essere null - ogni bagaglio deve essere associato ad un biglietto valido.
     */
    Ticket ticket;

    /**
     * Crea un nuovo bagaglio associato al biglietto specificato.
     * Il bagaglio viene inizializzato con stato BOOKED e senza tipo specifico.
     *
     * @param parTicket il biglietto a cui associare il bagaglio
     * @throws InvalidTicket se il biglietto fornito è null
     * @see LuggageStatus#BOOKED
     */
    public Luggage(Ticket parTicket) throws InvalidTicket {
        if(parTicket != null){
            this.ticket = parTicket;
        }else{
            throw new InvalidTicket("Il bagaglio deve essere associato ad un biglietto!");
        }
    }

    /**
     * Crea un nuovo bagaglio con tipo specificato associato al biglietto fornito.
     * Il bagaglio viene inizializzato con stato BOOKED.
     *
     * @param parType   il tipo di bagaglio
     * @param parTicket il biglietto a cui associare il bagaglio
     * @throws InvalidTicket se il biglietto fornito è null
     * @see LuggageType
     * @see LuggageStatus#BOOKED
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
     * Crea un nuovo bagaglio con stato specificato associato al biglietto fornito.
     * Il tipo di bagaglio rimane non specificato.
     *
     * @param parStatus lo stato iniziale del bagaglio
     * @param parTicket il biglietto a cui associare il bagaglio
     * @throws InvalidTicket se il biglietto fornito è null
     * @see LuggageStatus
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
     * Crea un nuovo bagaglio con ID e tipo specificati associato al biglietto fornito.
     * Il bagaglio viene inizializzato con stato BOOKED.
     *
     * @param parId     l'identificatore univoco del bagaglio
     * @param parType   il tipo di bagaglio
     * @param parTicket il biglietto a cui associare il bagaglio
     * @throws InvalidTicket se il biglietto fornito è null
     * @see LuggageType
     * @see LuggageStatus#BOOKED
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
     * Crea un nuovo bagaglio con ID e stato specificati associato al biglietto fornito.
     * Il tipo di bagaglio rimane non specificato.
     *
     * @param parId     l'identificatore univoco del bagaglio
     * @param parStatus lo stato iniziale del bagaglio
     * @param parTicket il biglietto a cui associare il bagaglio
     * @throws InvalidTicket se il biglietto fornito è null
     * @see LuggageStatus
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
     * Crea un nuovo bagaglio completamente specificato.
     * Costruttore principale che inizializza tutti i campi del bagaglio.
     *
     * @param parId     l'identificatore univoco del bagaglio
     * @param parType   il tipo di bagaglio
     * @param parStatus lo stato iniziale del bagaglio
     * @param parTicket il biglietto a cui associare il bagaglio
     * @throws InvalidTicket se il biglietto fornito è null
     * @see LuggageType
     * @see LuggageStatus
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
     * Restituisce l'identificatore univoco del bagaglio.
     *
     * @return l'ID del bagaglio, può essere null se non ancora assegnato
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta l'identificatore univoco del bagaglio.
     *
     * @param id il nuovo ID del bagaglio
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Restituisce il tipo di bagaglio.
     *
     * @return il tipo di bagaglio, può essere null se non specificato
     * @see LuggageType
     */
    public LuggageType getType() {
        return type;
    }

    /**
     * Imposta il tipo di bagaglio.
     *
     * @param type il nuovo tipo di bagaglio
     * @see LuggageType
     */
    public void setType(LuggageType type) {
        this.type = type;
    }

    /**
     * Restituisce lo stato corrente del bagaglio.
     *
     * @return lo stato del bagaglio
     * @see LuggageStatus
     */
    public LuggageStatus getStatus() {
        return status;
    }

    /**
     * Imposta lo stato del bagaglio.
     * Utilizzato per tracciare il progresso del bagaglio attraverso
     * i vari stadi del processo aeroportuale.
     *
     * @param status il nuovo stato del bagaglio
     * @see LuggageStatus
     */
    public void setStatus(LuggageStatus status) {
        this.status = status;
    }

    /**
     * Restituisce il biglietto associato a questo bagaglio.
     *
     * @return il biglietto associato, non può essere null
     * @see Ticket
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * Imposta il biglietto associato a questo bagaglio.
     * Metodo package-private utilizzato internamente per modificare
     * l'associazione del bagaglio.
     *
     * @param ticket il nuovo biglietto da associare
     * @see Ticket
     */
    void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}