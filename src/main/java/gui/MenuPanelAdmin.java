package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static gui.FloatingMessage.SUCCESS_MESSAGE;

public class MenuPanelAdmin extends JPanel {

    public MenuPanelAdmin(ArrayList<DisposableObject> callingObjects, Controller controller) {

        super();

        JButton menuButton = new JButton("Menù");

        ArrayList<String> options = new ArrayList<>();
        options.add("Home");
        options.add("Cerca voli");
        options.add("Le mie prenotazioni");
        options.add("Bagagli smarriti");
        options.add("Aggiungi nuovo volo");


        menuButton.addActionListener(e -> {

            JPopupMenu popupMenu = new JPopupMenu();

            for (String option : options) {

                JMenuItem menuItem = new JMenuItem(option);

                menuItem.addActionListener(actionEvent -> {
                    switch (option) {
                        case "Home":
                            controller.goHome(callingObjects);
                            break;
                        case "Cerca voli":
                            if (!callingObjects.getLast().getFrame().getTitle().equals("Cerca voli")) {

                                new SearchFlightCustomerMainFrame(callingObjects, controller, callingObjects.getLast().getFrame().getSize(),
                                        callingObjects.getLast().getFrame().getLocation(), callingObjects.getLast().getFrame().getExtendedState());

                                callingObjects.get(callingObjects.size() - 2).getFrame().setVisible(false);
                            }
                            break;
                        case "Le mie prenotazioni":
                            if (!callingObjects.getLast().getFrame().getTitle().equals("Le mie prenotazioni")) {

                                //new MyBookingsCustomerMainFrame(callingObjects, controller, callingObjects.getLast().getFrame().getSize(),
                                  //      callingObjects.getLast().getFrame().getLocation(), callingObjects.getLast().getFrame().getExtendedState(), true);
                                new FloatingMessage("Non so cosa saranno le mie prenotazioni per gli admin", menuButton, SUCCESS_MESSAGE);
                                //callingObjects.get(callingObjects.size() - 2).getFrame().setVisible(false);
                            }
                            break;
                        case "Bagagli smarriti":
                            controller.setErrorButton(menuButton);
                            Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
                            LostLuggageDialog dialog = new LostLuggageDialog(parent, callingObjects, controller);
                            dialog.setVisible(true);
                            break;
                        case "Aggiungi nuovo volo":
                            Frame ownerFlight = (Frame) SwingUtilities.getWindowAncestor(this);
                            AddNewFlightDialog addDialog = new AddNewFlightDialog(ownerFlight, controller);
                            addDialog.setVisible(true);
                            break;
                    }
                });
                popupMenu.add(menuItem);
            }

            popupMenu.show(menuButton, 0, menuButton.getHeight());
        });

        this.add(menuButton);

    }
}