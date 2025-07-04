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

    public CheckinPassengers (ArrayList<DisposableObject> callingObjects, Controller controller, Dimension dimension, Point point, int fullScreen) {

        super();

        constraints = new Constraints();

        //makes this the operating frame
        super.setMainFrame(callingObjects, controller, dimension, point, fullScreen, "CheckinPassengers");

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

        this.addPageChangeButtons (flowPanel);

        flowPanel.setVisible (true);

        constraints.setConstraints (0, 2, 2, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.PAGE_END);
        mainFrame.add (modifyPanel, constraints.getConstraints());
        modifyPanel.setVisible (true);
    }

    @Override
    protected void addPageChangeButtons (JPanel flowPanel) {

        prevPageButton.setFocusable(false);
        nextPageButton.setFocusable(false);

        prevPageButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed (ActionEvent e)
            {
                if (currPage != (passengerPanels.size() - 1) / 3) //non sto all'ultima pagina quindi sono 3
                {
                    for (int i = 0; i < 3; i++)
                        passengerPanels.get ((currPage * 3) + i).setVisible (false);

                } else //sto all'ultima pagina quindi non so quanti sono
                {
                    for (int i = 0; i <= (passengerPanels.size() - 1) % 3; i++)
                        passengerPanels.get ((passengerPanels.size() - i - 1)).setVisible (false);
                }

                currPage--;
                currentPageLabel.setText (Integer.valueOf(currPage + 1).toString());

                for (int i = 0; i < 3; i++) //vado indietro quindi sono 3
                    passengerPanels.get ((currPage * 3) + i).setVisible (true);

                nextPageButton.setEnabled (true);

                if (currPage == 0) prevPageButton.setEnabled (false);
            }
        });

        prevPageButton.setEnabled (false);

        flowPanel.add (prevPageButton);

        flowPanel.add (currentPageLabel);

        nextPageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed (ActionEvent e)
            {
                for (int i = 0; i < 3; i++) //metto a false la pagina corrente
                    passengerPanels.get ((currPage * 3) + i).setVisible (false);

                if (currPage + 1 == (passengerPanels.size() - 1) / 3) //sto andando all'ultima pagina quindi non so quanti sono
                {
                    for (int i = 0; i <= (passengerPanels.size() - 1) % 3; i++)
                        passengerPanels.get(passengerPanels.size() - i - 1).setVisible(true);

                    nextPageButton.setEnabled (false);
                } else //la prossima pagina ne ha 3
                {
                    for (int i = 0; i < 3; i++)
                        passengerPanels.get (((currPage + 1) * 3) + i).setVisible (true);
                }

                currPage++;
                currentPageLabel.setText (Integer.valueOf(currPage + 1).toString());
                prevPageButton.setEnabled (true);
            }
        });

        nextPageButton.setEnabled (false);
        flowPanel.add (nextPageButton);
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

        for (int j = 0; j < controller.getFlightController().getBookingsSize(); j++) {

            if (controller.getFlightController().checkBookingConfirm(j)) {

                for (int i = 0; i < controller.getFlightController().getBookingSize(j); i++) {
                    PassengerPanelAdmin passengerPanelAdmin = new PassengerPanelAdmin(controller, passengerPanels);

                    passengerPanelAdmin.setPassengerName(controller.getFlightController().getPassengerNameFromBooking(j, i));
                    passengerPanelAdmin.setPassengerSurname(controller.getFlightController().getPassengerSurnameFromBooking(j, i));
                    passengerPanelAdmin.setPassengerCF(controller.getFlightController().getPassengerCFFromBooking(j, i));
                    passengerPanelAdmin.setSeat(controller.getFlightController().getPassengerSeatFromBooking(j, i));
                    passengerPanelAdmin.setTicketNumber(controller.getFlightController().getPassengerTicketNumberFromBooking(j, i));
                    passengerPanelAdmin.setLuggagesTypes(controller.getFlightController().getPassengerLuggagesTypesFromBooking(j, i), controller);

                    insertPassengerPanel(controller, this, passengerPanelAdmin);
                }
            }
        }

        if (passengerPanels.size() > 3) super.nextPageButton.setEnabled(true);

        super.passengerPage.setVisible(false);
        super.passengerPage.setVisible(true);
    }

    protected void insertPassengerPanel (Controller controller, CheckinPassengers checkinPassengers, PassengerPanelAdmin newPassengerPanelAdmin) {

        constraints.setConstraints(0, (passengerPanels.size() % 3), 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        passengerPage.add(newPassengerPanelAdmin, constraints.getConstraints());

        passengerPanels.addLast(newPassengerPanelAdmin);

        newPassengerPanelAdmin.setVisible(passengerPanels.size() < 4);
    }
}
