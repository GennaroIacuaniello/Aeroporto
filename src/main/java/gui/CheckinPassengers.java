package gui;

import controller.BookingStatusController;
import controller.Controller;
import controller.FlightController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CheckinPassengers extends Book{

    private RoundedButton startCheckinButton;
    private RoundedButton confirmButton;
    private RoundedButton cancelButton;

    public CheckinPassengers (ArrayList<DisposableObject> callingObjects, Controller controller, Dimension dimension, Point point) {

        super();

        constraints = new Constraints();

        //makes this the operating frame
        super.setMainFrame(callingObjects, controller, dimension, point, "CheckinPassengers");

        //setting top panels
        this.addTopPanel (callingObjects, controller);

        //setting mainPanel
        this.addMainPanel (callingObjects, controller);

        mainFrame.setVisible(true);
    }

    @Override
    protected void addTopPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        topPanel = new JPanel();
        topPanel.setLayout(new GridBagLayout());

        super.addTitlePanel("AEROPORTO DI NAPOLI", controller);
        super.addNavigatorBarPanel (callingObjects, controller);
        super.addUserPanel(callingObjects, controller);

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START);
        mainFrame.add(topPanel, constraints.getConstraints());
        topPanel.setVisible (true);
    }

    private void addMainPanel (ArrayList<DisposableObject> callingObjects, Controller controller)
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        super.addFlightInfoPanel (controller);

        passengerPanels = new ArrayList<PassengerPanel> ();
        passengerPage = new JPanel();
        passengerPage.setLayout(new GridBagLayout());

        prevPageButton = new RoundedButton("←");
        nextPageButton = new RoundedButton("→");

        currentPageLabel = new JLabel(Integer.valueOf(currPage + 1).toString());

        constraints.setConstraints (0, 0, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        mainPanel.add (passengerPage, constraints.getConstraints ());

        this.addModifyPanel (callingObjects , controller);
        this.addConfirmPanel (callingObjects, controller);

        insertPassengers(controller);

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_START, 1, 1);
        mainFrame.add (mainPanel, constraints.getConstraints());
        constraints.resetWeight ();
        mainFrame.setVisible (true);
    }

    private void addModifyPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        modifyPanel = new JPanel();
        modifyPanel.setLayout(new GridBagLayout());

        JPanel flowPanel = new JPanel();
        flowPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        constraints.setConstraints (1, 0, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
        modifyPanel.add (flowPanel, constraints.getConstraints());

        super.addPageChangeButtons (flowPanel);

        flowPanel.setVisible (true);

        constraints.setConstraints (0, 2, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_END);
        mainFrame.add (modifyPanel, constraints.getConstraints());
        modifyPanel.setVisible (true);
    }

    @Override
    protected void addConfirmPanel (ArrayList<DisposableObject> callingObjects, Controller controller) {

        cancelButton = new RoundedButton("ANNULLA");
        startCheckinButton = new RoundedButton("INIZIA CHECK-IN");
        confirmButton = new RoundedButton("CONFERMA");

        cancelButton.setFocusable(false);
        startCheckinButton.setFocusable(false);
        confirmButton.setFocusable(false);

        cancelButton.addActionListener (new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                controller.goBack(callingObjects);
            }
        });

        startCheckinButton.addActionListener (new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                //il controller farà qualcosa
            }
        });

        confirmButton.addActionListener (new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e) {
                //il controller farà qualcosa
            }
        });

        super.confirmPanel = new JPanel();
        super.confirmPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        //aggiungi pulsanti a confirmPanel
        super.confirmPanel.add (cancelButton);
        super.confirmPanel.add (startCheckinButton);
        super.confirmPanel.add (confirmButton);

        constraints.setConstraints (0, 3, 3, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER);
        mainFrame.add (confirmPanel, constraints.getConstraints());
        confirmPanel.setVisible (true);
    }

    @Override
    protected void insertPassengers (Controller controller) {

        int i;

        for (i = 0; i < controller.getFlightController().getPassengersSize(); i++) {

            PassengerPanelAdmin passengerPanelAdmin = new PassengerPanelAdmin(controller, passengerPanels);

            passengerPanelAdmin.setPassengerName(controller.getFlightController().getPassengerName(i));
            passengerPanelAdmin.setPassengerSurname(controller.getFlightController().getPassengerSurname(i));
            passengerPanelAdmin.setPassengerCF(controller.getFlightController().getPassengerCF(i));
            passengerPanelAdmin.setSeat(controller.getFlightController().getPassengerSeat(i));
            passengerPanelAdmin.setTicketNumber(controller.getFlightController().getPassengerTicketNumber(i));
            passengerPanelAdmin.setLuggagesTypes(controller.getFlightController().getPassengerLuggagesTypes(i), controller);

            insertPassengerPanel(controller, this, passengerPanelAdmin);
        }

        if (passengerPanels.size() > 3) super.nextPageButton.setEnabled(true);

        super.passengerPage.setVisible(false);
        super.passengerPage.setVisible(true);
    }

    protected void insertPassengerPanel (Controller controller, CheckinPassengers checkinPassengers, PassengerPanelAdmin newPassengerPanelAdmin) {

        constraints.setConstraints(0, (passengerPanels.size() % 3), 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
        passengerPage.add(newPassengerPanelAdmin, constraints.getConstraints());

        passengerPanels.addLast(newPassengerPanelAdmin);

        newPassengerPanelAdmin.setVisible(passengerPanels.size() < 4);
    }
}
