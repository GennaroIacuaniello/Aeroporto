package controller;

import gui.CheckinPassengers;
import gui.GateChooser;
import implementazioni_postgres_dao.FlightDAOImpl;

import javax.swing.*;

public class GateController {

    public void newGate(JButton callingButton, Controller controller, CheckinPassengers checkinPassengers) {

        FlightDAOImpl flightDAO = new FlightDAOImpl();

        int idGate = flightDAO.searchGate(controller.getFlightController().getFlight().getId());

        if (idGate != -1) callingButton.setText("GATE: " + idGate);
        else checkinPassengers.setGateChooser(new GateChooser(controller, callingButton));
    }

    public void setGate (int idGate, Controller controller) {

        FlightDAOImpl flightDAO = new FlightDAOImpl();

        flightDAO.setGate(idGate, controller.getFlightController().getFlight().getId());
    }
}
