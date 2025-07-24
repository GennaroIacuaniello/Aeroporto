package model;

/**
 * Represents an airport gate in the airport management system.
 * <p>
 * This class encapsulates the information about a physical gate at the airport.
 * Gates are identified by a unique ID number.
 * </p>
 * <p>
 * The airport supports gates numbered from 1 to 20, representing the physical
 * infrastructure limitations. Each gate can be assigned to flights for
 * departure and arrival operations. The class enforces validation to ensure
 * only valid gate numbers are used throughout the system.
 * </p>
 * <p>
 * Key features include:
 * </p>
 * <ul>
 *   <li>Input validation for gate numbers</li>
 *   <li>Integration with flight management</li>
 *   <li>Support for gate assignment operations</li>
 * </ul>
 * <p>
 * Gates are typically assigned to flights through the flight management system
 * and are used by various airport operations.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Flight
 * @see InvalidGate
 * @see controller.GateController
 */
public class Gate {

    /**
     * The unique identifier for this gate.
     * <p>
     * This field stores the gate number as a byte value, representing
     * the physical gate number at the airport. Valid gate numbers
     * range from 1 to 20, inclusive. The byte data type is used
     * for memory efficiency given the limited range of valid values.
     * </p>
     */
    private byte id;

    /**
     * Constructs a new Gate with the specified ID.
     * <p>
     * Creates a gate with the given identifier, ensuring that the ID
     * falls within the valid range of 1 to 20. This constructor is
     * used when creating gate objects from user input or database records.
     * </p>
     *
     * @param parId the gate identifier, must be between 1 and 20 inclusive
     * @throws InvalidGate if the gate ID is not within the valid range (1-20)
     */
    public Gate(byte parId) throws InvalidGate {

        if(parId < 1 || parId > 20){
            throw new InvalidGate("Gate non valido!");
        }else{
            this.id = parId;
        }

    }

    /**
     * Gets the unique identifier of this gate.
     * <p>
     * Returns the gate number that uniquely identifies this gate
     * within the airport. This ID is used for gate assignment
     * and user information displays.
     * </p>
     *
     * @return the gate identifier (1-20)
     */
    public byte getId() {
        return id;
    }

    /**
     * Sets the unique identifier for this gate.
     * <p>
     * Updates the gate number with a new valid identifier. This method
     * includes validation to ensure the new ID falls within the acceptable
     * range. It is typically used for gate reassignment or correction
     * operations.
     * </p>
     *
     * @param id the new gate identifier, must be between 1 and 20 inclusive
     * @throws InvalidGate if the new gate ID is not within the valid range (1-20)
     */
    public void setId(byte id) throws InvalidGate {

        if(id < 1 || id > 20){
            throw new InvalidGate("Gate non valido!");
        }else{
            this.id = id;
        }

    }
}