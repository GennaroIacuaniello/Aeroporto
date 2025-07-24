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
 *   <li>Checking gate availability</li>
 *   <li>Coordinating with the {@link FlightDAOImpl} for database operations</li>
 *   <li>Integrating with GUI components for gate selection workflows</li>
 *   <li>Supporting administrative gate management operations</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see model.Gate
 * @see model.Flight
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
     * if exists a gate not already assigned to any flight. If a gate exists, it
     * updates the calling button to display the gate information. If no gate is
     * assigned, it creates the {@link GateChooser} interface to allow manual
     * gate selection by the administrator.
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
     *
     * @param idGate the numeric identifier of the gate to assign (1-20)
     * @param controller the main {@link Controller} instance providing access to current flight information
     */
    public void setGate (int idGate, Controller controller) {

        FlightDAOImpl flightDAO = new FlightDAOImpl();

        flightDAO.setGate(idGate, controller.getFlightController().getFlight().getId());
    }
}