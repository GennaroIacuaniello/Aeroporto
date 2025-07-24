package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * User information panel providing comprehensive user session management and account access functionality for the airport management system.
 * <p>
 * This class extends {@link JPanel} to provide a specialized user interface component that displays
 * current user session information, account management controls, and user-specific functionality
 * throughout the airport management system. The UserPanel serves as the primary user identification
 * and session management component across both administrative and customer interfaces, offering
 * professional user greeting display with integrated popup menu functionality for account operations.
 * </p>
 * <p>
 * The UserPanel class provides comprehensive user session functionality including:
 * </p>
 * <ul>
 *   <li><strong>User Session Display:</strong> Professional greeting display with HTML-formatted user identification</li>
 *   <li><strong>Account Management Integration:</strong> Popup menu access to account modification and session controls</li>
 *   <li><strong>Session Operations:</strong> Integrated logout functionality with proper resource cleanup</li>
 *   <li><strong>Interface Consistency:</strong> Standardized user panel design across all system interfaces</li>
 *   <li><strong>Interactive Functionality:</strong> Click-based popup menu activation with comprehensive user options</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JPanel
 * @see Controller
 * @see GridBagLayout
 * @see JPopupMenu
 * @see ModifyAccount
 * @see HomePageAdmin
 * @see HomePageCustomer
 * @see BookingPage
 * @see MyBookingsCustomerMainFrame
 */
public class UserPanel extends JPanel {

    /**
     * Current user greeting string storing the username for display and session identification.
     * <p>
     * This private String field maintains the current user's username retrieved from the
     * system controller for display in the user greeting button. The field supports dynamic
     * user identification and enables consistent user session display throughout the
     * user panel lifecycle and interface interactions.
     * </p>
     */
    private String userGreeted;
    
    /**
     * Popup menu component providing user account management and session control options.
     * <p>
     * This JPopupMenu provides comprehensive user functionality including logout operations
     * and account modification access. The menu integrates with the user button to provide
     * immediate access to user account operations through click-based activation and
     * professional menu presentation throughout user session management workflows.
     * </p>
     */
    JPopupMenu popupMenu;
    
    /**
     * Menu item component for dynamic popup menu construction and user operation access.
     * <p>
     * This JMenuItem serves as a reusable component for popup menu construction, enabling
     * dynamic menu item creation with appropriate action listeners and Italian localization.
     * The component supports flexible menu building and ensures consistent menu item
     * behavior throughout user account management and session control operations.
     * </p>
     */
    JMenuItem menuItem;

    /**
     * Constructs a new UserPanel with comprehensive user session management and account access functionality.
     * <p>
     * This constructor initializes the complete user panel interface by establishing layout management,
     * configuring the popup menu system, and creating the user identification button for comprehensive
     * user session display and account management operations. The constructor creates a fully functional
     * user panel ready for immediate integration within parent interfaces with professional user greeting
     * display and integrated account management capabilities throughout administrative and customer workflows.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Parent Initialization:</strong> Standard JPanel construction with default configuration</li>
     *   <li><strong>Popup Menu Setup:</strong> Comprehensive popup menu creation through setPopupMenu method delegation</li>
     *   <li><strong>User Button Creation:</strong> User identification button creation through setUserButton method delegation</li>
     * </ul>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and user workflow coordination
     * @param controller the system controller providing access to user information services and account management functionality
     */
    public UserPanel(List<DisposableObject> callingObjects, Controller controller) {

        super();

        this.setLayout(new GridBagLayout());

        setPopupMenu (controller, callingObjects);

        setUserButton(controller);
    }

    /**
     * Configures the comprehensive popup menu system with user account management and session control functionality.
     * <p>
     * This private method establishes the complete popup menu interface including logout operations
     * and account modification access with proper event handling and Italian localization. The method
     * creates a professional popup menu system that provides immediate access to essential user
     * account operations while maintaining consistent visual presentation and optimal user experience
     * throughout user session management and account administration workflows.
     * </p>
     * <p>
     * The popup menu configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Menu Creation:</strong> JPopupMenu instantiation for user account operations container</li>
     *   <li><strong>Logout Functionality:</strong> Logout menu item with integrated session termination and resource cleanup</li>
     *   <li><strong>Account Modification Access:</strong> Account editing menu item with ModifyAccount dialog integration</li>
     *   <li><strong>Event Handler Integration:</strong> Comprehensive action listener setup for all menu operations</li>
     *   <li><strong>Navigation Coordination:</strong> Proper calling objects hierarchy management for user workflow operations</li>
     * </ul>
     *
     * @param controller the system controller providing access to user session management and account functionality
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource coordination and user workflow management
     */
    private void setPopupMenu (Controller controller, List<DisposableObject> callingObjects) {

        popupMenu = new JPopupMenu();
        menuItem = new JMenuItem("Logout");

        menuItem.addActionListener(actionEvent ->

            controller.logOut(callingObjects)
        );

        popupMenu.add(menuItem);

        menuItem = new JMenuItem("Modifica account");

        menuItem.addActionListener(actionEvent -> {

            Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
            ModifyAccount dialog = new ModifyAccount(parent, callingObjects, controller);
            dialog.setVisible(true);
        });

        popupMenu.add(menuItem);
    }

    /**
     * Creates and configures the user identification button with professional greeting display and interactive popup menu functionality.
     * <p>
     * This private method establishes the user identification interface by retrieving current user information,
     * creating a professionally formatted greeting button, and integrating popup menu activation capabilities.
     * The method creates a comprehensive user identification display that provides immediate user recognition
     * with seamless access to account management functionality through click-based popup menu activation
     * throughout user session operations and interface interactions.
     * </p>
     * <p>
     * Interactive popup activation includes comprehensive ActionListener implementation
     * that positions and displays the popup menu directly below the user button for optimal
     * accessibility and user experience. The positioning calculation ensures consistent
     * menu placement and immediate visual association with the triggering button.
     * </p>
     * <p>
     * Component integration includes proper button addition to the panel layout through
     * the established GridBagLayout system. The integration ensures immediate button
     * availability and optimal presentation within the parent interface context while
     * maintaining consistent visual styling and layout behavior.
     * </p>
     *
     * @param controller the system controller providing access to current user session information and username retrieval
     */
    private void setUserButton (Controller controller) {
        JButton userButton;

        userGreeted = controller.getUserController().getUsername();
        userButton = new JButton("<html>Ciao,<br>" + userGreeted + "</html>");
        userButton.addActionListener(e ->

            popupMenu.show(userButton, 0, userButton.getHeight())
        );

        this.add(userButton);
    }

    /**
     * Returns the current user greeting string for session identification and interface coordination.
     * <p>
     * This method provides access to the stored username information that is displayed in the
     * user greeting button, enabling parent interfaces and system components to access current
     * user identification for session validation, interface customization, and user-specific
     * functionality coordination throughout administrative and customer workflow operations.
     * </p>
     *
     * @return the current user greeting string containing the username for session identification and interface coordination
     */
    public String getUserGreeted() {
        return userGreeted;
    }

}