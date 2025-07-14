package dao;

import model.*;

import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface FlightDAO {

    public void getImminentArrivingFlights (ArrayList<String> parId, ArrayList<String> parCompanyName, ArrayList<Date> parDate,
                                            ArrayList<Time> parDepartureTime, ArrayList<Time> parArrivalTime, ArrayList<String> parStatus,
                                            ArrayList<Integer> parMaxSeats, ArrayList<Integer> parFreeSeats, ArrayList<Integer> parGate);
}
