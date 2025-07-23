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
 *   <li><strong>Italian Localization:</strong> Complete Italian language support for user interface elements</li>
 *   <li><strong>Navigation Integration:</strong> Seamless coordination with application navigation hierarchy</li>
 * </ul>
 * <p>
 * The interface is designed with user experience optimization, providing system users with:
 * </p>
 * <ul>
 *   <li><strong>Clear User Identification:</strong> Prominent display of current user information with professional greeting</li>
 *   <li><strong>Intuitive Account Access:</strong> Single-click access to account management and session operations</li>
 *   <li><strong>Professional Presentation:</strong> HTML-formatted user greeting with appropriate typography and layout</li>
 *   <li><strong>Consistent Behavior:</strong> Uniform user panel functionality across administrative and customer contexts</li>
 *   <li><strong>Immediate Access:</strong> Quick access to logout and account modification functionality</li>
 *   <li><strong>Visual Integration:</strong> Seamless integration with parent interface design and layout systems</li>
 * </ul>
 * <p>
 * Panel architecture utilizes {@link GridBagLayout} for precise component positioning and optimal
 * space utilization. The layout ensures proper user button placement and supports responsive design
 * principles that maintain consistent presentation across different interface contexts and
 * window configurations throughout the airport management system.
 * </p>
 * <p>
 * User session management includes comprehensive user identification display through:
 * </p>
 * <ul>
 *   <li><strong>Dynamic User Greeting:</strong> Real-time username retrieval and display with Italian "Ciao" greeting</li>
 *   <li><strong>HTML Formatting:</strong> Professional multi-line button text with proper line breaks for optimal readability</li>
 *   <li><strong>Session Validation:</strong> Integration with controller systems for current user information access</li>
 *   <li><strong>Responsive Updates:</strong> Support for dynamic user information updates during session transitions</li>
 * </ul>
 * <p>
 * Account management integration provides sophisticated popup menu functionality with:
 * </p>
 * <ul>
 *   <li><strong>Logout Operations:</strong> Complete session termination with proper resource cleanup and navigation coordination</li>
 *   <li><strong>Account Modification:</strong> Direct access to account editing functionality through ModifyAccount dialog integration</li>
 *   <li><strong>Dialog Management:</strong> Proper parent window detection and modal dialog lifecycle coordination</li>
 *   <li><strong>Italian Localization:</strong> Complete menu item translation including "Logout" and "Modifica account" options</li>
 * </ul>
 * <p>
 * Interactive functionality includes click-based popup menu activation that positions the menu
 * directly below the user button for optimal accessibility and user experience. The interaction
 * system ensures immediate visual feedback and provides clear access to user account operations
 * throughout administrative and customer workflow scenarios.
 * </p>
 * <p>
 * Session operations integration includes comprehensive logout functionality that coordinates
 * with the system controller to ensure proper session termination, resource cleanup, and
 * navigation management. The logout process maintains application stability while ensuring
 * secure session management throughout user workflow transitions.
 * </p>
 * <p>
 * Account modification integration provides seamless access to the {@link ModifyAccount} dialog
 * component with proper parent window association and modal behavior. The integration ensures
 * that account changes are properly managed while maintaining user session context and
 * interface continuity throughout account management operations.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader airport management system through comprehensive
 * usage across multiple interface types including {@link HomePageAdmin}, {@link HomePageCustomer},
 * {@link BookingPage}, and {@link MyBookingsCustomerMainFrame}. The integration provides consistent
 * user session management while supporting interface-specific customization and context-appropriate
 * user functionality throughout administrative and customer workflows.
 * </p>
 * <p>
 * Navigation coordination includes sophisticated calling objects hierarchy management that
 * maintains proper resource management and interface state throughout user operations. The
 * coordination ensures that user actions are properly tracked and managed within the
 * application's navigation system for optimal user experience and system stability.
 * </p>
 * <p>
 * Visual design maintains consistency with the overall airport management system aesthetic
 * through standardized button styling, popup menu presentation, and layout integration that
 * supports both standalone operation and integration within larger interface contexts while
 * maintaining professional appearance and optimal user accessibility.
 * </p>
 * <p>
 * The UserPanel serves as a critical user interface component throughout the airport management
 * system ecosystem, providing essential user session management capabilities while maintaining
 * interface consistency, professional presentation, and optimal user experience throughout
 * administrative and customer interface workflows and account management operations.
 * </p>
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
     *   <li><strong>Layout Configuration:</strong> GridBagLayout establishment for precise component positioning</li>
     *   <li><strong>Popup Menu Setup:</strong> Comprehensive popup menu creation through setPopupMenu method delegation</li>
     *   <li><strong>User Button Creation:</strong> User identification button creation through setUserButton method delegation</li>
     * </ul>
     * <p>
     * Parent initialization establishes the foundational JPanel structure with default configuration,
     * providing the base component infrastructure for user session display and account management
     * functionality. The initialization ensures proper component hierarchy and prepares the panel
     * for enhanced user interface presentation and interactive functionality.
     * </p>
     * <p>
     * Layout configuration establishes {@link GridBagLayout} as the primary layout manager,
     * providing precise control over component positioning and alignment. The layout supports
     * optimal space utilization and maintains consistent presentation across different interface
     * contexts and user panel usage scenarios throughout the airport management system.
     * </p>
     * <p>
     * Popup menu setup delegates to the {@link #setPopupMenu(Controller, List)} method for
     * comprehensive popup menu creation including logout functionality and account modification
     * access. The setup ensures complete user account management capabilities with proper
     * event handling and Italian localization throughout user interaction workflows.
     * </p>
     * <p>
     * User button creation delegates to the {@link #setUserButton(Controller)} method for
     * professional user identification display with HTML-formatted greeting and interactive
     * popup menu activation. The creation ensures immediate user recognition and accessible
     * account management functionality throughout user session operations.
     * </p>
     * <p>
     * The constructor completes by establishing a fully functional user panel ready for
     * immediate integration within airport management system interfaces, providing comprehensive
     * user session management, professional user identification display, and seamless account
     * management capabilities throughout administrative and customer interface workflows.
     * </p>
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
     *   <li><strong>Italian Localization:</strong> Complete menu item translation for user interface accessibility</li>
     *   <li><strong>Event Handler Integration:</strong> Comprehensive action listener setup for all menu operations</li>
     *   <li><strong>Navigation Coordination:</strong> Proper calling objects hierarchy management for user workflow operations</li>
     * </ul>
     * <p>
     * Menu creation establishes the foundational JPopupMenu component that serves as the
     * container for all user account operations. The menu provides professional presentation
     * and maintains consistent visual styling that integrates seamlessly with the overall
     * airport management system design standards and user experience requirements.
     * </p>
     * <p>
     * Logout functionality includes comprehensive session termination capabilities through
     * direct controller integration via {@link Controller#logOut(List)}. The logout operation
     * ensures proper resource cleanup, session management, and navigation coordination while
     * maintaining application stability throughout user session termination workflows.
     * </p>
     * <p>
     * The logout menu item utilizes Italian localization ("Logout") and includes sophisticated
     * ActionListener implementation that coordinates with the system controller to ensure
     * complete session termination with proper calling objects hierarchy management for
     * optimal resource cleanup and navigation state management.
     * </p>
     * <p>
     * Account modification access provides seamless integration with the {@link ModifyAccount}
     * dialog component through proper parent window detection and modal dialog lifecycle
     * management. The integration ensures that account changes are properly managed while
     * maintaining user session context and interface continuity throughout account operations.
     * </p>
     * <p>
     * The account modification menu item includes Italian localization ("Modifica account")
     * and sophisticated ActionListener implementation that handles parent window detection
     * through {@link SwingUtilities#getWindowAncestor(java.awt.Component)}, creates the
     * ModifyAccount dialog with proper parameter passing, and activates the dialog for
     * immediate user account editing capabilities.
     * </p>
     * <p>
     * Event handler integration ensures that all popup menu operations maintain proper
     * integration with the application navigation hierarchy and controller systems. The
     * event handling provides immediate user feedback and ensures seamless user workflow
     * transitions throughout account management and session control operations.
     * </p>
     * <p>
     * Navigation coordination includes comprehensive calling objects hierarchy management
     * that ensures proper resource management and interface state maintenance throughout
     * user operations. The coordination supports both logout and account modification
     * workflows while maintaining application stability and optimal user experience.
     * </p>
     * <p>
     * The method ensures comprehensive popup menu functionality that provides essential
     * user account management capabilities while maintaining professional presentation,
     * Italian localization, and seamless integration with the broader airport management
     * system interface architecture and user workflow requirements.
     * </p>
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
     * The user button configuration includes:
     * </p>
     * <ul>
     *   <li><strong>User Information Retrieval:</strong> Dynamic username access through controller integration</li>
     *   <li><strong>Professional Greeting Display:</strong> HTML-formatted button text with Italian greeting and username presentation</li>
     *   <li><strong>Interactive Popup Activation:</strong> Click-based popup menu positioning and activation</li>
     *   <li><strong>Component Integration:</strong> Proper button addition to panel layout for immediate availability</li>
     *   <li><strong>Session Synchronization:</strong> Real-time user information display with controller coordination</li>
     * </ul>
     * <p>
     * User information retrieval utilizes comprehensive controller integration through
     * {@link Controller#getUserController()#getUsername()} to access current user session
     * information. The retrieval ensures real-time user identification display that remains
     * synchronized with the current system user session throughout interface operations.
     * </p>
     * <p>
     * The username information is stored in the {@link #userGreeted} field for consistent
     * access and potential future reference throughout the user panel lifecycle. The storage
     * enables dynamic user identification updates and supports user session management
     * operations throughout interface workflows and account management processes.
     * </p>
     * <p>
     * Professional greeting display utilizes sophisticated HTML formatting to create a
     * multi-line button presentation with Italian "Ciao" greeting followed by the username
     * on a separate line. The HTML formatting ensures optimal readability and professional
     * appearance while maintaining consistent visual presentation across different interface contexts.
     * </p>
     * <p>
     * The HTML button text format "<html>Ciao,<br>" + userGreeted + "</html>" provides
     * clear visual hierarchy with the greeting on the first line and the username on the
     * second line, creating an immediately recognizable user identification display that
     * enhances user experience and interface accessibility.
     * </p>
     * <p>
     * Interactive popup activation includes comprehensive ActionListener implementation
     * that positions and displays the popup menu directly below the user button for optimal
     * accessibility and user experience. The positioning calculation ensures consistent
     * menu placement and immediate visual association with the triggering button.
     * </p>
     * <p>
     * The popup menu positioning utilizes {@link JPopupMenu#show(java.awt.Component, int, int)}
     * with precise coordinate calculation (0, userButton.getHeight()) to display the menu
     * immediately below the button. The positioning ensures intuitive user interaction and
     * maintains visual context throughout popup menu operations.
     * </p>
     * <p>
     * Component integration includes proper button addition to the panel layout through
     * the established GridBagLayout system. The integration ensures immediate button
     * availability and optimal presentation within the parent interface context while
     * maintaining consistent visual styling and layout behavior.
     * </p>
     * <p>
     * The method ensures comprehensive user identification functionality that provides
     * professional user greeting display, seamless popup menu access, and consistent
     * integration with the broader airport management system interface architecture
     * and user experience requirements throughout administrative and customer workflows.
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
     * <p>
     * The user greeting access includes:
     * </p>
     * <ul>
     *   <li><strong>Session Identification:</strong> Current username information for user session validation and interface coordination</li>
     *   <li><strong>Interface Customization:</strong> Username access for parent interface customization and user-specific display elements</li>
     *   <li><strong>Session Validation:</strong> Username information for session consistency checking and user authentication verification</li>
     *   <li><strong>Workflow Coordination:</strong> User identification support for administrative and customer workflow management</li>
     * </ul>
     * <p>
     * Session identification enables parent interfaces to access current user information
     * for session validation operations, ensuring that displayed user information remains
     * consistent across different interface components and supports comprehensive user
     * session management throughout airport management system operations.
     * </p>
     * <p>
     * Interface customization support allows parent components to access username information
     * for user-specific interface elements, personalized messaging, and context-appropriate
     * functionality display based on current user session throughout administrative and
     * customer interface workflows and user experience optimization.
     * </p>
     * <p>
     * Session validation capabilities enable system components to verify user session
     * consistency and detect potential session changes that may require interface updates
     * or user information refresh throughout extended user sessions and interface transitions
     * within the airport management system.
     * </p>
     * <p>
     * The method supports comprehensive user session management by providing reliable access
     * to current user identification information while maintaining consistency with the
     * displayed user greeting and enabling proper integration with broader airport management
     * system user session coordination and interface management requirements.
     * </p>
     *
     * @return the current user greeting string containing the username for session identification and interface coordination
     */
    public String getUserGreeted() {
        return userGreeted;
    }

}