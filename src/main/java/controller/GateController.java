package controller;

import gui.CheckinPassengers;
import gui.GateChooser;
import implementazioni_postgres_dao.FlightDAOImpl;

import javax.swing.*;

/**
 * Controller class for managing airport gate operations and assignments in the airport management system.
 * <p>
 * This class serves as the specialized controller for gate-related operations within the MVC
 * architecture of the airport management system. It handles gate assignments to flights,
 * gate availability checking, and provides methods for managing gate-related administrative
 * functions through integration with the database layer and user interface components.
 * </p>
 * <p>
 * The GateController is responsible for:
 * </p>
 * <ul>
 *   <li>Managing gate assignments for flights during check-in procedures</li>
 *   <li>Checking gate availability and existing assignments</li>
 *   <li>Coordinating with the {@link FlightDAOImpl} for database operations</li>
 *   <li>Integrating with GUI components for gate selection workflows</li>
 *   <li>Supporting administrative gate management operations</li>
 *   <li>Handling gate assignment workflows through the {@link GateChooser} interface</li>
 * </ul>
 * <p>
 * The class follows a stateless design pattern, delegating all persistence operations
 * to the database access layer while providing a clean interface for gate management
 * operations. It maintains tight integration with the check-in process and flight
 * management workflows.
 * </p>
 * <p>
 * Gate operations are typically initiated during flight check-in procedures when
 * administrators need to assign gates to flights for boarding operations. The
 * controller handles both scenarios where gates are already assigned and where
 * new gate assignments need to be made.
 * </p>
 * <p>
 * Integration with GUI components ensures that gate selection workflows are
 * user-friendly and provide appropriate feedback through button text updates
 * and interactive gate selection dialogs when manual assignment is required.
 * </p>
 * <p>
 * All gate operations are performed with immediate database persistence to
 * ensure that gate assignments are available across all system components
 * and operational interfaces in real-time.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Gate
 * @see Flight
 * @see GateChooser
 * @see CheckinPassengers
 * @see FlightDAOImpl
 * @see Controller
 */
public class GateController {

    /**
     * Initiates gate assignment workflow for a flight during check-in operations.
     * <p>
     * This method handles the gate assignment process for flights by first checking
     * if a gate is already assigned to the current flight. If a gate exists, it
     * updates the calling button to display the gate information. If no gate is
     * assigned, it launches the {@link GateChooser} interface to allow manual
     * gate selection by the administrator.
     * </p>
     * <p>
     * The method performs the following operations:
     * </p>
     * <ul>
     *   <li>Queries the database for existing gate assignments for the flight</li>
     *   <li>Updates the UI button text to show assigned gate information</li>
     *   <li>Launches gate selection dialog when no assignment exists</li>
     *   <li>Integrates with the check-in workflow for seamless operation</li>
     * </ul>
     * <p>
     * The gate assignment workflow is typically initiated during flight check-in
     * procedures when administrators need to ensure that flights have appropriate
     * gate assignments for passenger boarding operations. The method provides
     * immediate feedback through button text updates and handles both existing
     * assignments and new assignment scenarios.
     * </p>
     * <p>
     * Integration with the {@link CheckinPassengers} interface ensures that gate
     * selection workflows are properly managed within the check-in context,
     * maintaining reference to the gate chooser dialog for proper lifecycle
     * management and user interaction handling.
     * </p>
     * <p>
     * Database queries are performed through {@link FlightDAOImpl} to retrieve
     * current gate assignments, ensuring that the most up-to-date gate information
     * is used for operational decisions and display purposes.
     * </p>
     *
     * @param callingButton the UI button that triggered the gate assignment workflow,
     *                     used for displaying gate information and user feedback
     * @param controller the main {@link Controller} instance providing access to flight information
     * @param checkinPassengers the {@link CheckinPassengers} interface managing the check-in workflow
     */
    public void newGate(JButton callingButton, Controller controller, CheckinPassengers checkinPassengers) {

        FlightDAOImpl flightDAO = new FlightDAOImpl();

        int idGate = flightDAO.searchGate(controller.getFlightController().getFlight().getId());

        if (idGate != -1) callingButton.setText("GATE: " + idGate);
        else checkinPassengers.setGateChooser(new GateChooser(controller, callingButton));
    }

    /**
     * Assigns a specific gate to a flight with immediate database persistence.
     * <p>
     * This method performs the actual gate assignment operation by updating the
     * database to associate the specified gate ID with the current flight. The
     * assignment is persisted immediately to ensure that gate information is
     * available across all system components and operational interfaces.
     * </p>
     * <p>
     * The gate assignment process includes:
     * </p>
     * <ul>
     *   <li>Immediate database update linking the gate to the flight</li>
     *   <li>Real-time availability of gate information system-wide</li>
     *   <li>Integration with flight operational workflows</li>
     *   <li>Support for administrative gate management operations</li>
     * </ul>
     * <p>
     * This method is typically called as a result of user interaction with the
     * {@link GateChooser} interface, where administrators select an available
     * gate from the airport's gate inventory (gates 1-20). The assignment
     * becomes effective immediately upon successful database update.
     * </p>
     * <p>
     * Gate assignments are crucial for airport operations as they determine
     * where passengers will board flights and where ground services will
     * coordinate aircraft operations. The immediate persistence ensures
     * operational consistency across all airport systems.
     * </p>
     * <p>
     * The method uses {@link FlightDAOImpl} to perform the database update,
     * ensuring proper transaction handling and data integrity during the
     * gate assignment process. No validation is performed at this level
     * as gate selection constraints are enforced by the UI components.
     * </p>
     *
     * @param idGate the numeric identifier of the gate to assign (1-20)
     * @param controller the main {@link Controller} instance providing access to current flight information
     */
    public void setGate (int idGate, Controller controller) {

        FlightDAOImpl flightDAO = new FlightDAOImpl();

        flightDAO.setGate(idGate, controller.getFlightController().getFlight().getId());
    }
}