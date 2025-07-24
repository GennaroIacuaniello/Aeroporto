package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Administrative menu panel providing specialized administrative functions and navigation for airport management operations.
 * <p>
 * This class extends {@link JPanel} to provide a comprehensive dropdown menu interface.
 * The panel offers centralized access to functions including system navigation, lost luggage management,
 * and flight operations through a dropdown menu interface.
 * </p>
 * <p>
 * The MenuPanelAdmin class supports comprehensive administrative functionality including:
 * </p>
 * <ul>
 *   <li><strong>System Navigation:</strong> Quick access to home interface and primary administrative areas</li>
 *   <li><strong>Lost Luggage Management:</strong> Direct access to comprehensive lost luggage oversight and management tools</li>
 *   <li><strong>Flight Operations:</strong> Administrative flight creation and management capabilities</li>
 *   <li><strong>Dropdown Interface:</strong> Intuitive popup menu design for efficient administrative navigation</li>
 *   <li><strong>Controller Integration:</strong> Seamless integration with system controller for administrative operations</li>
 *   <li><strong>Error Handling:</strong> Proper error state management and user feedback mechanisms</li>
 * </ul>
 * <p>
 * Navigation functionality includes duplicate prevention that checks the current interface
 * state before initiating navigation transitions. This prevents unnecessary interface reloads
 * and ensures smooth administrative workflow continuity throughout system operations.
 * </p>
 * <p>
 * Lost luggage integration provides direct access to the {@link LostLuggageDialog} component,
 * enabling administrators to quickly access comprehensive lost luggage management capabilities
 * including luggage tracking, recovery operations, and customer service coordination from
 * any administrative interface context.
 * </p>
 * <p>
 * Flight operations integration includes access to the {@link AddNewFlightDialog} component
 * for comprehensive flight creation and configuration capabilities. This integration enables
 * administrators to quickly add new flights to the system with complete operational parameters
 * and schedule coordination directly from the administrative menu.
 * </p>
 * <p>
 * Error handling integration includes proper error button management through controller
 * coordination, ensuring that administrative operations maintain proper error state tracking
 * and user feedback throughout dialog-based administrative workflows and system interactions.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JPanel
 * @see Controller
 * @see LostLuggageDialog
 * @see AddNewFlightDialog
 * @see DisposableObject
 * @see HomePageAdmin
 * @see JPopupMenu
 */
public class MenuPanelAdmin extends JPanel {

    /**
     * Constructs a new MenuPanelAdmin with comprehensive administrative menu functionality.
     * <p>
     * This constructor initializes the complete administrative menu interface by creating
     * the menu button, configuring available administrative options, and establishing
     * comprehensive event handling for all administrative functions. The constructor
     * creates a fully functional administrative menu ready for immediate use with
     * integrated navigation, dialog management, and system coordination capabilities.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Menu Button Creation:</strong> Primary menu button with Italian "Menù" label for administrative access</li>
     *   <li><strong>Option Configuration:</strong> Dynamic list of administrative functions including navigation and specialized tools</li>
     *   <li><strong>Event Handler Setup:</strong> Comprehensive popup menu creation and item selection handling</li>
     *   <li><strong>Navigation Integration:</strong> Intelligent navigation with duplicate prevention and state management</li>
     *   <li><strong>Dialog Coordination:</strong> Seamless integration with specialized administrative dialogs</li>
     *   <li><strong>Layout Integration:</strong> Proper panel integration and component visibility management</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper navigation state management and resource coordination
     * @param controller the system controller providing access to administrative functions, navigation logic, and specialized dialog management capabilities
     */
    public MenuPanelAdmin(List<DisposableObject> callingObjects, Controller controller) {

        super();

        JButton menuButton = new JButton("Menù");

        ArrayList<String> options = new ArrayList<>();
        options.add("Home");
        options.add("Bagagli smarriti");
        options.add("Aggiungi nuovo volo");


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