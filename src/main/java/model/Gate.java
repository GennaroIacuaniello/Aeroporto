package model;

/**
 * Rappresenta un gate aeroportuale per l'imbarco dei passeggeri.
 * 
 * <p>La classe Gate gestisce un gate aeroportuale identificato da un numero univoco.
 * Ogni gate ha un ID numerico compreso tra 1 e 20, che rappresenta i gate disponibili
 * nell'aeroporto. La classe garantisce che l'ID del gate sia sempre valido attraverso
 * la validazione nei costruttori e nei metodi setter.</p>
 * 
 * <p>I gate sono utilizzati per l'imbarco dei passeggeri sui voli e rappresentano
 * le porte di accesso agli aeromobili all'interno della struttura aeroportuale.</p>
 * 
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see InvalidGate
 */
public class Gate {

    /**
     * Identificatore numerico del gate.
     * Deve essere compreso tra 1 e 20 (inclusi).
     * Rappresenta il numero del gate nell'aeroporto.
     */
    private byte id;

    /**
     * Crea un nuovo gate con l'identificatore specificato.
     * 
     * <p>Il gate viene inizializzato con un ID che deve essere compreso
     * tra 1 e 20. Se l'ID non è valido, viene lanciata un'eccezione.</p>
     *
     * @param parId l'identificatore del gate, deve essere compreso tra 1 e 20
     * @throws InvalidGate se l'ID del gate non è compreso tra 1 e 20
     * @see InvalidGate
     */
    public Gate(byte parId) throws InvalidGate {

        if(parId < 1 || parId > 20){
            throw new InvalidGate("Gate non valido!");
        }else{
            this.id = parId;
        }

    }

    /**
     * Restituisce l'identificatore del gate.
     * 
     * <p>L'ID restituito è sempre compreso tra 1 e 20, essendo validato
     * durante la creazione dell'oggetto e nelle modifiche successive.</p>
     *
     * @return l'identificatore numerico del gate (1-20)
     */
    public byte getId() {
        return id;
    }

    /**
     * Imposta l'identificatore del gate.
     * 
     * <p>Modifica l'ID del gate con un nuovo valore. Il nuovo ID deve essere
     * compreso tra 1 e 20, altrimenti viene lanciata un'eccezione. Questo
     * metodo garantisce che l'integrità del gate sia sempre mantenuta.</p>
     *
     * @param id il nuovo identificatore del gate, deve essere compreso tra 1 e 20
     * @throws InvalidGate se il nuovo ID non è compreso tra 1 e 20
     * @see InvalidGate
     */
    public void setId(byte id) throws InvalidGate {

        if(id < 1 || id > 20){
            throw new InvalidGate("Gate non valido!");
        }else{
            this.id = id;
        }

    }
}