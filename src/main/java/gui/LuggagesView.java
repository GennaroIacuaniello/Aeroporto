package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

import controller.Controller;

public class LuggagesView extends JFrame {

    private JFrame thisFrame;
    private JScrollPane scrollPane;
    private JPanel luggagesPanel;
    private ArrayList<LuggagePanel> luggagesPanels;
    private ArrayList<RemoveLuggageButton> removeLuggageButtons;
    private ArrayList<JButton> lostLuggageButtons;
    private JButton addLuggageButton;
    private JButton confirmButton;

    private Constraints constraints;

    public LuggagesView(Controller controller) {

        super("Luggages");

        this.setVisible(false);
        this.setAlwaysOnTop(true);

        thisFrame = this;
        this.setLayout(new GridBagLayout());
        constraints = new Constraints();
        this.setSize(300, 400);

        setLuggagesPanel(controller);
        setButtons(controller);
        setScrollPane(controller);
    }

    private void setLuggagesPanel(Controller controller) {
        luggagesPanel = new JPanel();
        luggagesPanel.setLayout(new GridBagLayout());

        luggagesPanels = new ArrayList<LuggagePanel>();
        removeLuggageButtons = new ArrayList<RemoveLuggageButton>();
        lostLuggageButtons = new ArrayList<JButton>();
    }

    private void setButtons(Controller controller) {
        addLuggageButton = new JButton("+");
        confirmButton = new JButton("Confirm");

        addLuggageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!luggagesPanels.isEmpty()) {
                    luggagesPanels.add(new LuggagePanel(controller, luggagesPanels.getLast().getIndex() + 1));
                } else {
                    luggagesPanels.add(new LuggagePanel(controller, 0));
                }


                constraints.setConstraints(0, luggagesPanels.size() - 1, 1, 1,
                        GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
                luggagesPanel.add(luggagesPanels.getLast(), constraints.getConstraints());
                luggagesPanels.getLast().setVisible(true);

                removeLuggageButtons.add(new RemoveLuggageButton(controller, luggagesPanels, removeLuggageButtons, luggagesPanel,
                        scrollPane, removeLuggageButtons.size()));

                constraints.setConstraints(1, luggagesPanels.size() - 1, 1, 1,
                        GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
                luggagesPanel.add(removeLuggageButtons.getLast(), constraints.getConstraints());
                luggagesPanels.getLast().setVisible(true);

                scrollPane.setViewportView(luggagesPanel);
            }
        });

        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
            }
        });

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
        this.add(addLuggageButton, constraints.getConstraints());

        constraints.setConstraints(1, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
        this.add(confirmButton, constraints.getConstraints());

        addLuggageButton.setVisible(true);
        addLuggageButton.setFocusable(false);
        confirmButton.setVisible(true);
        confirmButton.setFocusable(false);
    }

    private void setScrollPane(Controller controller) {
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(luggagesPanel);
        scrollPane.setWheelScrollingEnabled(true);

        constraints.setConstraints(0, 1, 2, 1,
                GridBagConstraints.BOTH, 0, 0, GridBagConstraints.CENTER, 0.5f, 0.5f);
        this.add(scrollPane, constraints.getConstraints());
        scrollPane.setVisible(true);
    }

    public ArrayList<LuggagePanel> getLuggagesPanels() {
        return luggagesPanels;
    }

    public void setLuggages(List<Integer> luggagesTypes, List<String> luggagesTickets, List<String> luggagesStatus,Controller controller) {

        int i = 0;

        for (Integer luggageType : luggagesTypes) {

            if (!luggagesPanels.isEmpty()) {
                luggagesPanels.add(new LuggagePanel(controller, luggagesPanels.getLast().getIndex() + 1));
                if (luggagesTickets.get(i) != null) luggagesPanels.getLast().setLabel("Bagaglio: " + luggagesTickets.get(i++));
            } else {
                luggagesPanels.add(new LuggagePanel(controller, 0));
                if (luggagesTickets.get(i) != null) luggagesPanels.getLast().setLabel("Bagaglio: " + luggagesTickets.get(i++));
            }


            constraints.setConstraints(0, luggagesPanels.size() - 1, 1, 1,
                    GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
            luggagesPanel.add(luggagesPanels.getLast(), constraints.getConstraints());
            luggagesPanels.getLast().setVisible(true);

            removeLuggageButtons.add(new RemoveLuggageButton(controller, luggagesPanels, removeLuggageButtons, luggagesPanel,
                    scrollPane, removeLuggageButtons.size()));

            if (controller.getFlightController().getFlightStatus().toString().equalsIgnoreCase("PROGRAMMED") && controller.getCustomerController().getLoggedCustomer() != null) {

                constraints.setConstraints(1, luggagesPanels.size() - 1, 1, 1,
                        GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
                luggagesPanel.add(removeLuggageButtons.getLast(), constraints.getConstraints());
                luggagesPanels.getLast().setVisible(true);
            } else if (controller.getFlightController().getFlightStatus().toString().equalsIgnoreCase("LANDED")) {

                String name;
                boolean flag = false;

                if (controller.getCustomerController().getLoggedCustomer() != null) {
                    name = "SMARRITO?";
                    if (luggagesStatus.get(luggagesPanels.size() - 1).equals("WITHDRAWABLE")) flag = true;
                } else {
                    name = "RITROVATO?";
                    if (luggagesStatus.get(luggagesPanels.size() - 1).equals("LOST")) flag = true;
                }

                JButton lostLuggageButton = new JButton(name);

                int finalI = i - 1;

                lostLuggageButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {

                        lostLuggageButton.setEnabled(false);

                        String luggageStatus;

                        if (controller.getCustomerController().getLoggedCustomer() != null) luggageStatus = "LOST";
                        else luggageStatus = "WITHDRAWABLE";

                        controller.getLuggageController().lostLuggage(luggagesPanels.get(finalI).getTicket(), luggageStatus);

                        new FloatingMessage("Segnalazione avvenuta con successo", lostLuggageButton, FloatingMessage.SUCCESS_MESSAGE);
                    }
                });

                lostLuggageButton.setFocusPainted(false);
                lostLuggageButton.setEnabled(flag);

                lostLuggageButtons.add(lostLuggageButton);

                constraints.setConstraints(1, luggagesPanels.size() - 1, 1, 1,
                        GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
                luggagesPanel.add(lostLuggageButtons.getLast(), constraints.getConstraints());
                luggagesPanels.getLast().setVisible(true);
            }



            luggagesPanels.getLast().setType(luggageType);

            scrollPane.setViewportView(luggagesPanel);
        }
    }

    public void setLocation(JButton callingButtonutton) {
        //coordinate punto in alto a sx del bottone
        double x = callingButtonutton.getLocationOnScreen().getX();
        double y = callingButtonutton.getLocationOnScreen().getY();

        //coordinate centro
        x += callingButtonutton.getSize().getWidth() / 2;
        y += callingButtonutton.getSize().getHeight() / 2;

        //coordinate punto in alto a sx frame
        x -= (double) this.getWidth() / 2;
        y -= (double) this.getHeight() / 2;

        this.setLocation((int) x, (int) y);
    }

    public JButton getAddLuggageButton () {
        return addLuggageButton;
    }

    public JButton getConfirmButton () {
        return confirmButton;
    }

    public ArrayList<RemoveLuggageButton> getRemoveLuggageButtons () {
        return removeLuggageButtons;
    }

    public void setLuggagesIds (ArrayList<String> luggagesIds) {

        for (int i = 0; i < luggagesIds.size(); i++) {

            luggagesPanels.get(i).setLabel("Bagaglio: " + luggagesIds.get(i));
        }
    }
}
