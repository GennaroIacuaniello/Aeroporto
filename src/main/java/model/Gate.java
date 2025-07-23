package model;

public class Gate {

    private byte id;

    public Gate(byte parId) throws InvalidGate {

        if(parId < 1 || parId > 20){
            throw new InvalidGate("Gate non valido!");
        }else{
            this.id = parId;
        }

    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) throws InvalidGate {

        if(id < 1 || id > 20){
            throw new InvalidGate("Gate non valido!");
        }else{
            this.id = id;
        }

    }
}
