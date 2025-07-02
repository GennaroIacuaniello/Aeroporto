package gui;

import controller.Controller;

import java.util.ArrayList;

public class PassengerPanelAdmin extends PassengerPanel {

    private String ticketNumber;

    public PassengerPanelAdmin(Controller controller, ArrayList<PassengerPanel> passengerPanelsAdmin) {

        super (controller, passengerPanelsAdmin);
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }
}
