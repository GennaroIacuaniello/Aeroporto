package controller;

import model.FlightStatus;

public class FlightStatusController {

    public FlightStatus programmed = FlightStatus.PROGRAMMED;
    public FlightStatus cancelled = FlightStatus.CANCELLED;
    public FlightStatus departed = FlightStatus.DEPARTED;
    public FlightStatus delayed = FlightStatus.DELAYED;
    public FlightStatus landed = FlightStatus.LANDED;
    public FlightStatus aboutToDepart = FlightStatus.ABOUT_TO_DEPART;
}
