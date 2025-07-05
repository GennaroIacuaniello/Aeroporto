package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PassengerPanelAdmin extends PassengerPanel {

    private String ticketNumber;
    private JCheckBox checkinCheckBox;

    public PassengerPanelAdmin(Controller controller, ArrayList<PassengerPanel> passengerPanelsAdmin) {

        super (controller, passengerPanelsAdmin);
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setPanelEnabled (boolean flag) {

        super.passengerNameField.setEnabled(flag);
        super.passengerSurnameField.setEnabled(flag);
        super.passengerCFField.setEnabled(flag);
        super.seatButton.setEnabled(flag);

        super.luggagesView.getAddLuggageButton().setVisible(flag);
        super.luggagesView.getConfirmButton().setVisible(flag);
        for (int i = 0; i < super.luggagesView.getLuggagesPanels().size(); i++) {

            super.luggagesView.getLuggagesPanels().get(i).getComboBox().setEnabled(flag);
            super.luggagesView.getRemoveLuggageButtons().get(i).setVisible(flag);
        }
    }

    public void addCheckinCheckBox (boolean flag) {
        checkinCheckBox = new JCheckBox("Checkin", flag);

        constraints.setConstraints(0, 3, 3, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add(checkinCheckBox, constraints.getConstraints());
        checkinCheckBox.setVisible(true);
    }

    public JCheckBox getCheckinCheckBox () {
        return checkinCheckBox;
    }

    public void setTicketNumber (String ticketNumber) {

        this.ticketNumber = ticketNumber;
        super.passengerLabel.setText("Passenger: " + ticketNumber);
    }
}
