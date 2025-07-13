package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi

 */

public class Gate {

    private byte id;

    public Gate(byte parId) throws InvalidGate {

        if(parId < 0 || parId > 19){
            throw new InvalidGate("Gate non valido!");
        }else{
            this.id = parId;
        }

    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) throws InvalidGate {

        if(id < 0 || id > 19){
            throw new InvalidGate("Gate non valido!");
        }else{
            this.id = id;
        }

    }
}
