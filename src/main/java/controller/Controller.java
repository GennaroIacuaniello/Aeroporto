package controller;

import model.Arriving;
import model.Departing;
import model.Flight;

import java.util.ArrayList;
import java.util.Date;

public class Controller {

    public ArrayList<Arriving> getImminentArrivingFlights(){

        ArrayList<Arriving> arrivingFlights = new ArrayList<Arriving>();

        arrivingFlights.add(new Arriving("01", "Compagnia", new Date(),
                "00:00", "00:00", 100, "Dubai"));
        arrivingFlights.add(new Arriving("02", "Compagnia", new Date(),
                "00:00", "00:01", 100, "Dubai"));
        arrivingFlights.add(new Arriving("03", "Compagnia", new Date(),
                "00:00", "00:02", 100, "Dubai"));

        return arrivingFlights;
    }

    public ArrayList<Departing> getImminentDepartingFlights(){

        ArrayList<Departing> departingFlights = new ArrayList<Departing>();

        departingFlights.add(new Departing("01", "Compagnia", new Date(),
                "00:00", "00:00", 100, "Dubai"));
        departingFlights.add(new Departing("02", "Compagnia", new Date(),
                "00:00", "00:01", 100, "Dubai"));
        departingFlights.add(new Departing("03", "Compagnia", new Date(),
                "00:00", "00:02", 100, "Dubai"));

        return departingFlights;
    }

    public ArrayList<Flight> search_flight_customer(String departing_city, String arriving_city, String date, String initial_time, String final_time){

        ArrayList<Flight> res = new ArrayList<Flight>(0);

        res.add(new Arriving("01", "Compagnia", new Date(),
                "00:00", "00:00", 100, "Dubai"));
        res.add(new Arriving("02", "Compagnia", new Date(),
                "00:00", "00:01", 100, "Dubai"));
        res.add(new Arriving("03", "Compagnia", new Date(),
                "00:00", "00:02", 100, "Dubai"));

        res.add(new Departing("01", "Compagnia", new Date(),
                "00:00", "00:00", 100, "Dubai"));
        res.add(new Departing("02", "Compagnia", new Date(),
                "00:00", "00:01", 100, "Dubai"));
        res.add(new Departing("03", "Compagnia", new Date(),
                "00:00", "00:02", 100, "Dubai"));

        return res;
    }

}
