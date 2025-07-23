package gui;

import controller.Controller;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

/**
 * The type Menu panel customer.
 */
public class MenuPanelCustomer extends JPanel {

    /**
     * Instantiates a new Menu panel customer.
     *
     * @param callingObjects the calling objects
     * @param controller     the controller
     */
    public MenuPanelCustomer(List<DisposableObject> callingObjects, Controller controller) {

        super();

        JButton menuButton = new JButton("Menù");

        ArrayList<String> options = new ArrayList<>();
        options.add("Home");
        options.add("Cerca voli");
        options.add("Le mie prenotazioni");

        menuButton.addActionListener(e -> {

            JPopupMenu popupMenu = new JPopupMenu();

            for (String option : options) {

                JMenuItem menuItem = new JMenuItem(option);

                menuItem.addActionListener(actionEvent -> {
                    switch (option) {
                        case "Home":
                            if (!callingObjects.getLast().getFrame().getTitle().equals("Home")){
                                controller.goHome(callingObjects);
                            }
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

                                new MyBookingsCustomerMainFrame(callingObjects, controller, callingObjects.getLast().getFrame().getSize(),
                                        callingObjects.getLast().getFrame().getLocation(), callingObjects.getLast().getFrame().getExtendedState(), true);

                                callingObjects.get(callingObjects.size() - 2).getFrame().setVisible(false);
                            }
                            break;
                        default:
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