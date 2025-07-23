package model;

/**
 * The type Gate.
 */
public class Gate {

    private byte id;

    /**
     * Instantiates a new Gate.
     *
     * @param parId the par id
     * @throws InvalidGate the invalid gate
     */
    public Gate(byte parId) throws InvalidGate {

        if(parId < 1 || parId > 20){
            throw new InvalidGate("Gate non valido!");
        }else{
            this.id = parId;
        }

    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public byte getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     * @throws InvalidGate the invalid gate
     */
    public void setId(byte id) throws InvalidGate {

        if(id < 1 || id > 20){
            throw new InvalidGate("Gate non valido!");
        }else{
            this.id = id;
        }

    }
}
