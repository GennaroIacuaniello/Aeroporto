package gui;

import controller.Controller;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Customer menu panel providing specialized customer functions and navigation for airport management operations.
 * <p>
 * This class extends {@link JPanel} to provide a comprehensive dropdown menu interface specifically
 * designed for customer users of the airport management system. The panel offers
 * access to customer-specific functions including system navigation, flight search capabilities,
 * and booking management through a dropdown menu interface optimized for customer workflows.
 * </p>
 * <p>
 * The MenuPanelCustomer class supports comprehensive customer functionality including:
 * </p>
 * <ul>
 *   <li><strong>System Navigation:</strong> Quick access to home interface and primary customer areas</li>
 *   <li><strong>Flight Search Access:</strong> Direct access to comprehensive flight search and booking capabilities</li>
 *   <li><strong>Booking Management:</strong> Customer booking overview and management functionality</li>
 *   <li><strong>Dropdown Interface:</strong> Intuitive popup menu design for efficient customer navigation</li>
 *   <li><strong>Controller Integration:</strong> Seamless integration with system controller for customer operations</li>
 *   <li><strong>Navigation Optimization:</strong> Intelligent navigation that prevents redundant interface transitions</li>
 * </ul>
 * <p>
 * Navigation functionality includes intelligent duplicate prevention that checks current interface
 * state before initiating navigation transitions. This prevents unnecessary interface reloads
 * and ensures smooth customer workflow continuity throughout system operations.
 * </p>
 * <p>
 * Flight search integration provides direct access to the {@link SearchFlightCustomerMainFrame} component,
 * enabling customers to quickly access comprehensive flight search capabilities including route
 * planning, schedule checking, and booking initiation from any customer interface context.
 * </p>
 * <p>
 * Booking management integration includes access to the {@link MyBookingsCustomerMainFrame} component
 * for comprehensive booking overview and management capabilities. This integration enables
 * customers to quickly access their booking history, modify existing reservations, and manage
 * travel plans directly from the customer menu.
 * </p>
 * <p>
 * The panel integrates seamlessly with the broader customer interface ecosystem including
 * {@link HomePageCustomer}, {@link NavigatorBarPanel}, and other customer components while
 * maintaining clean separation of concerns and reusable design patterns.
* </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JPanel
 * @see Controller
 * @see SearchFlightCustomerMainFrame
 * @see MyBookingsCustomerMainFrame
 * @see DisposableObject
 * @see HomePageCustomer
 * @see JPopupMenu
 */
public class MenuPanelCustomer extends JPanel {

    /**
     * Constructs a new MenuPanelCustomer with comprehensive customer menu functionality.
     * <p>
     * This constructor initializes the complete customer menu interface by creating
     * the menu button, configuring available customer options, and establishing
     * comprehensive event handling for all customer functions. The constructor
     * creates a fully functional customer menu ready for immediate use with
     * integrated navigation, interface management, and system coordination capabilities.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Menu Button Creation:</strong> Primary menu button with Italian "Menù" label for customer access</li>
     *   <li><strong>Option Configuration:</strong> Dynamic list of customer functions including navigation and specialized tools</li>
     *   <li><strong>Event Handler Setup:</strong> Comprehensive popup menu creation and item selection handling</li>
     *   <li><strong>Navigation Integration:</strong> Intelligent navigation with duplicate prevention and state management</li>
     *   <li><strong>Interface Coordination:</strong> Seamless integration with specialized customer interfaces</li>
     *   <li><strong>Layout Integration:</strong> Proper panel integration and component visibility management</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper navigation state management and resource coordination
     * @param controller the system controller providing access to customer functions, navigation logic, and specialized interface management capabilities
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