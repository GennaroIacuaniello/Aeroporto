package model;

/*TO DO:
    - rivedere la visibilità di metodi e attributi
    - rivedere tipi di ritorno metodi

 */

public class Gate {

    private byte id;

    public Gate(byte par_id) {
        this.id = par_id;
    }

    public byte get_Id() {
        return this.id;
    }

    public int set_Id(byte par_id) {
        this.id = par_id;
        return 0;
    }
}
