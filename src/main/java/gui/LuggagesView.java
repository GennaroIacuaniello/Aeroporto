package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

import controller.Controller;

/**
 * Comprehensive luggage management interface providing interactive luggage configuration and status management in the airport management system.
 * <p>
 * This class extends {@link JFrame} to provide a dedicated window for managing multiple luggage items
 * associated with passenger bookings. The interface supports dynamic luggage addition, removal, type
 * configuration, and status tracking through an intuitive, scrollable interface that adapts to different
 * operational contexts including booking creation, administrative management, and customer service operations.
 * </p>
 * <p>
 * The LuggagesView class provides comprehensive luggage management functionality including:
 * </p>
 * <ul>
 *   <li><strong>Dynamic Luggage Management:</strong> Interactive addition and removal of luggage items with real-time interface updates</li>
 *   <li><strong>Contextual Interface Adaptation:</strong> Flight status-based interface modifications for different operational phases</li>
 *   <li><strong>Lost Luggage Integration:</strong> Comprehensive lost luggage reporting and recovery functionality</li>
 *   <li><strong>Scrollable Container Support:</strong> Efficient handling of large numbers of luggage items through scrollable interface</li>
 *   <li><strong>Role-Based Functionality:</strong> Different interface behaviors for customers and administrative users</li>
 *   <li><strong>Real-Time Status Updates:</strong> Integration with luggage controller for immediate status synchronization</li>
 * </ul>
 * <p>
 * The interface is designed with operational workflow optimization, providing users with:
 * </p>
 * <ul>
 *   <li><strong>Intuitive Luggage Addition:</strong> Single-click luggage addition with automatic indexing and layout management</li>
 *   <li><strong>Interactive Removal System:</strong> Individual luggage removal buttons with dynamic interface restructuring</li>
 *   <li><strong>Comprehensive Status Management:</strong> Context-sensitive buttons for lost luggage reporting and recovery</li>
 *   <li><strong>Professional Presentation:</strong> Clean, organized display optimized for both customer and administrative use</li>
 *   <li><strong>Responsive Layout:</strong> Adaptive sizing and positioning relative to calling components</li>
 * </ul>
 * <p>
 * Layout architecture utilizes {@link GridBagLayout} for precise component positioning and optimal
 * space utilization. The interface maintains a three-row structure with control buttons at the top,
 * scrollable luggage content in the center, and dynamic positioning capabilities for optimal user
 * experience across different screen configurations and operational contexts.
 * </p>
 * <p>
 * Dynamic luggage management enables real-time addition and removal of {@link LuggagePanel} components
 * with automatic index management, layout restructuring, and scroll container updates. The system
 * maintains consistent luggage identification and supports both sequential numbering and ticket-based
 * identification for comprehensive luggage tracking throughout operational workflows.
 * </p>
 * <p>
 * Contextual interface adaptation provides different functionality based on flight status and user roles:
 * </p>
 * <ul>
 *   <li><strong>PROGRAMMED Flights:</strong> Full luggage management with addition/removal capabilities for customer users</li>
 *   <li><strong>LANDED Flights:</strong> Lost luggage reporting and recovery functionality with status-specific button presentation</li>
 *   <li><strong>Administrative Context:</strong> Enhanced functionality for luggage recovery and status management operations</li>
 *   <li><strong>Customer Context:</strong> Streamlined interface focused on personal luggage management and reporting</li>
 * </ul>
 * <p>
 * Lost luggage integration provides comprehensive functionality for reporting missing luggage and
 * managing recovery operations. The system supports both customer-initiated lost luggage reports
 * and administrative recovery operations with appropriate status transitions and user feedback
 * through integrated {@link FloatingMessage} components.
 * </p>
 * <p>
 * The interface integrates seamlessly with the broader luggage management ecosystem including
 * {@link LuggagePanel} components for individual luggage configuration, {@link RemoveLuggageButton}
 * functionality for luggage removal operations, and {@link LuggageController} integration for
 * real-time status updates and database synchronization.
 * </p>
 * <p>
 * Positioning and display management includes intelligent window positioning relative to calling
 * buttons, always-on-top behavior for focused user attention, and proper visibility management
 * during different operational phases. The interface supports both standalone operation and
 * integration within broader booking and administrative workflows.
 * </p>
 * <p>
 * Resource management follows standard Swing component lifecycle patterns with proper component
 * initialization, dynamic collection management, and efficient memory utilization during extended
 * usage sessions with multiple luggage items and frequent interface updates.
 * </p>
 * <p>
 * The class serves as the primary interface for all luggage-related operations within the airport
 * management system, providing comprehensive functionality for luggage configuration, status management,
 * and operational coordination while maintaining clean separation of concerns and intuitive user experience.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JFrame
 * @see LuggagePanel
 * @see RemoveLuggageButton  
 * @see LuggageController
 * @see Controller
 * @see FloatingMessage
 * @see GridBagLayout
 */
public class LuggagesView extends JFrame {

    /**
     * Self-reference to the frame instance for internal event handling and lifecycle management.
     * <p>
     * This final reference enables event handlers and internal methods to access the frame
     * instance for visibility control, positioning operations, and lifecycle management
     * during dynamic luggage management operations and interface state transitions.
     * </p>
     */
    private final JFrame thisFrame;
    
    /**
     * Scrollable container providing overflow handling for large numbers of luggage items.
     * <p>
     * This JScrollPane component contains the luggage panel and provides vertical scrolling
     * capability when the number of luggage items exceeds the visible area. The container
     * includes mouse wheel scrolling support and automatic viewport updates during dynamic
     * luggage addition and removal operations.
     * </p>
     */
    private JScrollPane scrollPane;
    
    /**
     * Main container panel for organizing luggage panel components with GridBagLayout.
     * <p>
     * This JPanel serves as the primary container for all luggage panel components,
     * utilizing GridBagLayout for precise positioning and automatic layout management
     * during dynamic luggage addition and removal operations. The panel maintains
     * consistent organization and visual hierarchy for optimal user experience.
     * </p>
     */
    private JPanel luggagesPanel;
    
    /**
     * Dynamic collection of luggage panel components for individual luggage item management.
     * <p>
     * This ArrayList maintains all {@link LuggagePanel} instances representing individual
     * luggage items within the interface. The collection supports dynamic addition and
     * removal operations with automatic index management and layout coordination for
     * comprehensive luggage management functionality.
     * </p>
     */
    private ArrayList<LuggagePanel> luggagesPanels;
    
    /**
     * Collection of removal button components for interactive luggage deletion functionality.
     * <p>
     * This ArrayList maintains {@link RemoveLuggageButton} instances corresponding to each
     * luggage panel, enabling users to remove individual luggage items with automatic
     * interface restructuring and index management for consistent user experience
     * throughout luggage management operations.
     * </p>
     */
    private ArrayList<RemoveLuggageButton> removeLuggageButtons;
    
    /**
     * Collection of lost luggage management buttons for reporting and recovery operations.
     * <p>
     * This ArrayList maintains JButton instances for lost luggage functionality, providing
     * context-sensitive buttons for reporting missing luggage or marking luggage as found
     * based on user roles and flight status. The buttons integrate with the luggage
     * controller for real-time status updates and operational coordination.
     * </p>
     */
    private ArrayList<JButton> lostLuggageButtons;
    
    /**
     * Primary action button for adding new luggage items to the interface.
     * <p>
     * This JButton provides users with the ability to dynamically add new luggage
     * panels to the interface, automatically managing indexing, layout constraints,
     * and removal button associations for seamless luggage addition operations
     * throughout booking and administrative workflows.
     * </p>
     */
    private JButton addLuggageButton;
    
    /**
     * Confirmation button for completing luggage configuration operations.
     * <p>
     * This JButton enables users to confirm and close the luggage management interface
     * after completing luggage configuration operations. The button provides immediate
     * interface closure with proper state preservation for continued application
     * workflows and user experience optimization.
     * </p>
     */
    private JButton confirmButton;

    /**
     * Layout constraints utility for precise component positioning throughout the interface.
     * <p>
     * This Constraints helper object provides standardized GridBagConstraints configuration
     * for optimal component layout and positioning. The constraints ensure consistent
     * spacing, alignment, and visual organization across all interface components
     * and support dynamic layout updates during luggage management operations.
     * </p>
     */
    private final Constraints constraints;

    /**
     * Constructs a new LuggagesView interface for comprehensive luggage management and configuration.
     * <p>
     * This constructor initializes the complete luggage management interface by establishing the
     * window framework, configuring component collections, and setting up interactive elements
     * for dynamic luggage operations. The constructor creates a fully functional luggage management
     * system ready for immediate user interaction with comprehensive functionality for luggage
     * addition, removal, configuration, and status management.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Window Configuration:</strong> Frame setup with appropriate title, visibility, and display properties</li>
     *   <li><strong>Layout Establishment:</strong> GridBagLayout configuration with constraints utility for precise positioning</li>
     *   <li><strong>Component Collections:</strong> ArrayList initialization for dynamic luggage panel and button management</li>
     *   <li><strong>Interface Structure:</strong> Luggage panel, button controls, and scroll container configuration</li>
     *   <li><strong>Event Integration:</strong> Interactive button functionality with comprehensive event handling</li>
     *   <li><strong>Display Optimization:</strong> Window sizing and positioning for optimal user experience</li>
     * </ul>
     * <p>
     * Window configuration establishes the JFrame with "Luggages" title and configures it with
     * initially hidden visibility, always-on-top behavior for focused user attention, and
     * appropriate sizing (300x400) for optimal luggage management interface presentation
     * across different screen configurations and operational contexts.
     * </p>
     * <p>
     * Layout establishment includes GridBagLayout configuration as the primary layout manager
     * and Constraints utility initialization for standardized component positioning throughout
     * the interface. The layout supports dynamic component addition and removal with consistent
     * visual organization and optimal space utilization.
     * </p>
     * <p>
     * Component collections initialization creates ArrayList instances for managing luggage panels,
     * removal buttons, and lost luggage buttons with dynamic addition and removal capabilities.
     * The collections support efficient component management and automatic index coordination
     * throughout luggage management operations.
     * </p>
     * <p>
     * Interface structure configuration includes the main luggage panel setup with GridBagLayout,
     * interactive button controls for luggage addition and confirmation, and scroll container
     * integration for handling large numbers of luggage items with smooth scrolling and
     * viewport management capabilities.
     * </p>
     * <p>
     * Event integration establishes comprehensive action listeners for all interactive components
     * including luggage addition functionality, interface confirmation and closure, and
     * removal button coordination for complete luggage management workflow support.
     * </p>
     * <p>
     * Display optimization includes final window configuration with proper layout application,
     * component visibility activation, and scroll container integration for immediate user
     * interaction and optimal interface presentation across different usage scenarios.
     * </p>
     */
    public LuggagesView() {

        super("Luggages");

        this.setVisible(false);
        this.setAlwaysOnTop(true);

        thisFrame = this;
        this.setLayout(new GridBagLayout());
        constraints = new Constraints();
        this.setSize(300, 400);

        setLuggagesPanel();
        setButtons();
        setScrollPane();
    }

    /**
     * Initializes the main luggage container panel and associated component collections for dynamic luggage management.
     * <p>
     * This private method establishes the core luggage management infrastructure by creating and
     * configuring the main luggage panel with GridBagLayout and initializing all component
     * collections required for dynamic luggage panel management, removal operations, and lost
     * luggage functionality. The method provides the foundation for all subsequent luggage
     * management operations and interface interactions.
     * </p>
     * <p>
     * Panel initialization includes:
     * </p>
     * <ul>
     *   <li><strong>Container Creation:</strong> Main JPanel instantiation with GridBagLayout for precise component organization</li>
     *   <li><strong>Layout Configuration:</strong> GridBagLayout setup enabling dynamic component addition with consistent positioning</li>
     *   <li><strong>Collection Initialization:</strong> ArrayList creation for luggage panels, removal buttons, and lost luggage buttons</li>
     *   <li><strong>Foundation Establishment:</strong> Core infrastructure preparation for dynamic luggage management operations</li>
     * </ul>
     * <p>
     * Container creation establishes the main JPanel that will house all luggage-related components
     * including individual luggage panels, removal buttons, and contextual lost luggage controls.
     * The panel provides the structural foundation for dynamic component management and visual
     * organization throughout luggage management workflows.
     * </p>
     * <p>
     * Layout configuration applies GridBagLayout to the luggage panel, enabling precise control
     * over component positioning, spacing, and alignment during dynamic luggage addition and
     * removal operations. The layout supports responsive design and maintains visual consistency
     * across different numbers of luggage items and interface states.
     * </p>
     * <p>
     * Collection initialization creates three essential ArrayList instances for managing different
     * types of components: luggagesPanels for individual luggage configuration panels,
     * removeLuggageButtons for interactive removal functionality, and lostLuggageButtons for
     * lost luggage reporting and recovery operations. The collections enable efficient component
     * lifecycle management and dynamic interface updates.
     * </p>
     * <p>
     * Foundation establishment completes the core infrastructure setup, preparing the interface
     * for subsequent button configuration, scroll container integration, and dynamic luggage
     * management operations while ensuring optimal performance and user experience throughout
     * all luggage management workflows and operational contexts.
     * </p>
     */
    private void setLuggagesPanel() {
        luggagesPanel = new JPanel();
        luggagesPanel.setLayout(new GridBagLayout());

        luggagesPanels = new ArrayList<>();
        removeLuggageButtons = new ArrayList<>();
        lostLuggageButtons = new ArrayList<>();
    }

    /**
     * Configures interactive control buttons with comprehensive event handling for luggage management operations.
     * <p>
     * This private method establishes the primary user interaction controls including luggage addition
     * and interface confirmation functionality. The method creates and configures buttons with
     * appropriate event listeners, layout positioning, and visual properties to provide intuitive
     * luggage management operations with real-time interface updates and comprehensive user feedback.
     * </p>
     * <p>
     * Button configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Component Creation:</strong> Add luggage and confirm button instantiation with appropriate labels</li>
     *   <li><strong>Event Handler Setup:</strong> Comprehensive action listeners for dynamic luggage management operations</li>
     *   <li><strong>Layout Integration:</strong> Precise button positioning using GridBagConstraints for optimal organization</li>
     *   <li><strong>Visual Configuration:</strong> Button appearance settings including visibility and focus management</li>
     *   <li><strong>Dynamic Operations:</strong> Real-time luggage addition with automatic indexing and removal button coordination</li>
     * </ul>
     * <p>
     * Component creation establishes the add luggage button with "+" label for intuitive user
     * recognition and the confirm button with "Confirm" label for clear action identification.
     * The buttons provide the primary user interaction points for luggage management operations
     * and interface lifecycle control throughout all operational contexts.
     * </p>
     * <p>
     * Event handler setup includes comprehensive ActionListener implementations for both buttons:
     * the add luggage button manages dynamic luggage panel creation with automatic indexing,
     * layout constraint application, removal button coordination, and scroll container updates,
     * while the confirm button provides immediate interface closure for workflow completion.
     * </p>
     * <p>
     * The add luggage functionality includes intelligent index management that determines the
     * next available index based on existing luggage panels, creates new {@link LuggagePanel}
     * instances with appropriate indexing, applies proper layout constraints for optimal
     * positioning, and coordinates removal button creation and layout for complete functionality.
     * </p>
     * <p>
     * Layout integration utilizes the Constraints utility to apply standardized GridBagConstraints
     * positioning both buttons in the top row of the interface with appropriate alignment:
     * the add button positioned at LINE_END and the confirm button at LINE_START for intuitive
     * user interaction patterns and visual balance throughout the interface organization.
     * </p>
     * <p>
     * Visual configuration includes immediate visibility activation for both buttons and focus
     * disablement to prevent keyboard navigation conflicts during luggage management operations.
     * The configuration ensures optimal user experience and interface behavior across different
     * interaction methods and operational scenarios.
     * </p>
     * <p>
     * Dynamic operations support includes automatic scroll container viewport updates following
     * luggage addition, ensuring that newly added luggage items are immediately visible and
     * accessible to users regardless of the current scroll position or interface state.
     * </p>
     */
    private void setButtons() {
        addLuggageButton = new JButton("+");
        confirmButton = new JButton("Confirm");

        addLuggageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!luggagesPanels.isEmpty()) {
                    luggagesPanels.add(new LuggagePanel(luggagesPanels.getLast().getIndex() + 1));
                } else {
                    luggagesPanels.add(new LuggagePanel(0));
                }


                constraints.setConstraints(0, luggagesPanels.size() - 1, 1, 1,
                        GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
                luggagesPanel.add(luggagesPanels.getLast(), constraints.getGridBagConstraints());
                luggagesPanels.getLast().setVisible(true);

                removeLuggageButtons.add(new RemoveLuggageButton(luggagesPanels, removeLuggageButtons, luggagesPanel,
                        scrollPane, removeLuggageButtons.size()));

                constraints.setConstraints(1, luggagesPanels.size() - 1, 1, 1,
                        GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
                luggagesPanel.add(removeLuggageButtons.getLast(), constraints.getGridBagConstraints());
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
        this.add(addLuggageButton, constraints.getGridBagConstraints());

        constraints.setConstraints(1, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
        this.add(confirmButton, constraints.getGridBagConstraints());

        addLuggageButton.setVisible(true);
        addLuggageButton.setFocusable(false);
        confirmButton.setVisible(true);
        confirmButton.setFocusable(false);
    }

    /**
     * Establishes the scrollable container infrastructure for efficient luggage panel management and display.
     * <p>
     * This private method creates and configures the scroll container that houses the main luggage
     * panel, providing comprehensive scrolling functionality for handling large numbers of luggage
     * items with optimal user experience. The method establishes viewport management, mouse wheel
     * scrolling support, and proper layout integration for seamless luggage management operations.
     * </p>
     * <p>
     * Scroll container configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Container Creation:</strong> JScrollPane instantiation with luggage panel as viewport content</li>
     *   <li><strong>Scrolling Configuration:</strong> Mouse wheel scrolling enablement for intuitive navigation</li>
     *   <li><strong>Layout Integration:</strong> Precise positioning and sizing using GridBagConstraints</li>
     *   <li><strong>Viewport Management:</strong> Automatic content updates and visibility optimization</li>
     *   <li><strong>Space Utilization:</strong> Optimal space allocation with flexible width and height expansion</li>
     * </ul>
     * <p>
     * Container creation establishes the JScrollPane with the main luggage panel as its viewport
     * view, providing the foundation for scrollable luggage management when the number of luggage
     * items exceeds the visible area. The container maintains proper content organization and
     * automatic viewport updates during dynamic luggage addition and removal operations.
     * </p>
     * <p>
     * Scrolling configuration enables mouse wheel scrolling functionality, allowing users to
     * navigate through large numbers of luggage items using intuitive mouse wheel interactions.
     * The configuration enhances user experience and provides efficient navigation capabilities
     * for comprehensive luggage management across different operational scenarios.
     * </p>
     * <p>
     * Layout integration utilizes GridBagConstraints to position the scroll container in the
     * center portion of the interface (row 1) spanning both columns with BOTH fill behavior
     * for optimal space utilization. The layout includes weight values (0.5f, 0.5f) for
     * flexible expansion and responsive design across different window sizes and configurations.
     * </p>
     * <p>
     * Viewport management ensures that the scroll container properly displays the luggage panel
     * content and automatically updates the viewport when luggage items are dynamically added
     * or removed. The management includes proper content sizing and scroll position optimization
     * for consistent user experience throughout luggage management operations.
     * </p>
     * <p>
     * Space utilization optimization includes both horizontal and vertical expansion capabilities
     * with center alignment, ensuring that the scroll container efficiently uses available
     * interface space while maintaining proper content organization and visual hierarchy
     * throughout different luggage management scenarios and interface states.
     * </p>
     */
    private void setScrollPane() {
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(luggagesPanel);
        scrollPane.setWheelScrollingEnabled(true);

        constraints.setConstraints(0, 1, 2, 1,
                GridBagConstraints.BOTH, 0, 0, GridBagConstraints.CENTER, 0.5f, 0.5f);
        this.add(scrollPane, constraints.getGridBagConstraints());
        scrollPane.setVisible(true);
    }

    /**
     * Provides access to the collection of luggage panel components for external management and coordination.
     * <p>
     * This method returns the ArrayList containing all {@link LuggagePanel} instances currently
     * managed by the interface, enabling external components to access luggage configuration
     * information, perform validation operations, and coordinate luggage management workflows
     * with parent components and controller systems throughout the application.
     * </p>
     * <p>
     * Collection access enables:
     * </p>
     * <ul>
     *   <li><strong>Configuration Retrieval:</strong> Access to individual luggage panel configurations for processing</li>
     *   <li><strong>Validation Operations:</strong> External validation of luggage configurations and completeness</li>
     *   <li><strong>Workflow Coordination:</strong> Integration with booking and administrative management systems</li>
     *   <li><strong>Status Management:</strong> Luggage status updates and synchronization with controller systems</li>
     * </ul>
     * <p>
     * The returned collection maintains direct references to all active luggage panels,
     * enabling external components to iterate through luggage items, extract configuration
     * information, perform validation checks, and coordinate luggage management operations
     * with the broader airport management system infrastructure.
     * </p>
     *
     * @return the ArrayList of LuggagePanel instances representing all currently configured luggage items
     */
    public List<LuggagePanel> getLuggagesPanels() {
        return luggagesPanels;
    }

    /**
     * Configures existing luggage items with comprehensive information and contextual functionality based on operational status.
     * <p>
     * This method establishes luggage panels for existing luggage items with complete configuration
     * including type settings, ticket associations, and status-dependent functionality. The method
     * provides contextual interface adaptation based on flight status and user roles, enabling
     * appropriate luggage management operations including removal, lost luggage reporting, and
     * recovery functionality throughout different operational phases.
     * </p>
     * <p>
     * Configuration process includes:
     * </p>
     * <ul>
     *   <li><strong>Panel Creation:</strong> Dynamic luggage panel generation with proper indexing and ticket association</li>
     *   <li><strong>Layout Management:</strong> Precise positioning and visual organization of luggage components</li>
     *   <li><strong>Contextual Functionality:</strong> Flight status and role-based feature activation and button configuration</li>
     *   <li><strong>Status Integration:</strong> Lost luggage functionality with appropriate user interface adaptations</li>
     *   <li><strong>Type Configuration:</strong> Luggage type assignment and dropdown configuration for each item</li>
     *   <li><strong>Interface Updates:</strong> Continuous scroll container viewport updates for optimal user experience</li>
     * </ul>
     * <p>
     * Panel creation includes intelligent index management for sequential luggage numbering,
     * automatic ticket label assignment when ticket information is available, and proper
     * layout constraint application for optimal visual organization. Each luggage panel
     * receives appropriate positioning and visibility configuration for immediate user interaction.
     * </p>
     * <p>
     * Layout management utilizes GridBagConstraints for precise component positioning with
     * center alignment for luggage panels and coordinated placement of associated buttons
     * including removal buttons and contextual lost luggage functionality buttons based
     * on operational requirements and user permissions.
     * </p>
     * <p>
     * Contextual functionality adaptation provides different interface behaviors based on
     * flight status and user roles:
     * </p>
     * <ul>
     *   <li><strong>PROGRAMMED Flights:</strong> Full luggage management with removal buttons for customers</li>
     *   <li><strong>LANDED Flights:</strong> Lost luggage reporting ("SMARRITO?") for customers and recovery ("RITROVATO?") for administrators</li>
     *   <li><strong>Status-Based Enablement:</strong> Button activation based on current luggage status and operational context</li>
     * </ul>
     * <p>
     * Lost luggage integration includes comprehensive button creation with appropriate labels
     * based on user roles, status-dependent enablement logic, and complete event handling
     * for luggage status updates through controller integration. The functionality includes
     * real-time status updates and user feedback through {@link FloatingMessage} components.
     * </p>
     * <p>
     * Type configuration ensures that each luggage panel receives appropriate type settings
     * based on the provided luggage types array, enabling proper luggage classification
     * and configuration persistence throughout luggage management workflows and operational
     * transitions across different system components.
     * </p>
     * <p>
     * Interface updates include continuous scroll container viewport refreshing to ensure
     * that all luggage items remain visible and accessible during the configuration process,
     * providing optimal user experience and immediate access to all configured luggage
     * items regardless of the total number of items or current scroll position.
     * </p>
     *
     * @param luggagesTypes list of Integer values representing luggage type classifications for each luggage item
     * @param luggagesTickets list of String values containing ticket identifiers for luggage association and tracking
     * @param luggagesStatus list of String values indicating current status of each luggage item for contextual functionality
     * @param controller the system controller providing access to flight status, user roles, and luggage management functionality
     */
    public void setLuggages(List<Integer> luggagesTypes, List<String> luggagesTickets, List<String> luggagesStatus,Controller controller) {

        int i = 0;

        for (Integer luggageType : luggagesTypes) {

            if (!luggagesPanels.isEmpty()) {
                luggagesPanels.add(new LuggagePanel(luggagesPanels.getLast().getIndex() + 1));
            } else {
                luggagesPanels.add(new LuggagePanel(0));
            }
            if (luggagesTickets.get(i) != null) luggagesPanels.getLast().setLabel("Bagaglio: " + luggagesTickets.get(i++));


            constraints.setConstraints(0, luggagesPanels.size() - 1, 1, 1,
                    GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
            luggagesPanel.add(luggagesPanels.getLast(), constraints.getGridBagConstraints());
            luggagesPanels.getLast().setVisible(true);

            removeLuggageButtons.add(new RemoveLuggageButton(luggagesPanels, removeLuggageButtons, luggagesPanel,
                    scrollPane, removeLuggageButtons.size()));

            if (controller.getFlightController().getFlightStatus().toString().equalsIgnoreCase("PROGRAMMED") && controller.getCustomerController().getLoggedCustomer() != null) {

                constraints.setConstraints(1, luggagesPanels.size() - 1, 1, 1,
                        GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
                luggagesPanel.add(removeLuggageButtons.getLast(), constraints.getGridBagConstraints());
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
                luggagesPanel.add(lostLuggageButtons.getLast(), constraints.getGridBagConstraints());
                luggagesPanels.getLast().setVisible(true);
            }



            luggagesPanels.getLast().setType(luggageType);

            scrollPane.setViewportView(luggagesPanel);
        }
    }

    /**
     * Positions the luggage management interface optimally relative to a calling button for intuitive user experience.
     * <p>
     * This method calculates and applies precise window positioning to center the luggage interface
     * relative to the provided calling button, ensuring optimal visual relationship and user
     * experience during luggage management operations. The positioning algorithm accounts for
     * button location, size, and window dimensions to provide consistent and intuitive interface
     * placement across different screen configurations and operational contexts.
     * </p>
     * <p>
     * Positioning calculation includes:
     * </p>
     * <ul>
     *   <li><strong>Button Location Analysis:</strong> Screen coordinate determination for the calling button</li>
     *   <li><strong>Center Point Calculation:</strong> Mathematical center point determination of the calling button</li>
     *   <li><strong>Window Offset Computation:</strong> Interface window positioning relative to button center</li>
     *   <li><strong>Screen Coordinate Application:</strong> Final window location setting with calculated coordinates</li>
     * </ul>
     * <p>
     * Button location analysis retrieves the absolute screen coordinates of the calling button's
     * top-left corner using getLocationOnScreen(), providing the foundation for all subsequent
     * positioning calculations. These coordinates account for the button's position within its
     * parent container and the container's position within the overall screen space.
     * </p>
     * <p>
     * Center point calculation determines the mathematical center of the calling button by
     * adding half of the button's width and height to the top-left coordinates. This center
     * point serves as the reference position for optimal luggage interface placement relative
     * to the user's interaction point.
     * </p>
     * <p>
     * Window offset computation calculates the final window position by subtracting half of
     * the luggage interface's width and height from the button's center coordinates. This
     * calculation ensures that the luggage interface appears centered on the calling button,
     * providing intuitive visual relationship and optimal user experience.
     * </p>
     * <p>
     * Screen coordinate application converts the calculated floating-point coordinates to
     * integer values and applies them to the window using setLocation(), ensuring precise
     * window positioning while maintaining integer pixel alignment for optimal visual
     * presentation across different display configurations.
     * </p>
     * <p>
     * The positioning algorithm ensures consistent luggage interface placement regardless of
     * the calling button's location, size, or the screen configuration, providing reliable
     * and intuitive interface positioning for enhanced user experience throughout luggage
     * management workflows and operational contexts.
     * </p>
     *
     * @param callingButton the JButton component that triggered the luggage interface display, used for relative positioning calculations
     */
    public void setLocation(JButton callingButton) {
        //coordinate punto in alto a sx del bottone
        double x = callingButton.getLocationOnScreen().getX();
        double y = callingButton.getLocationOnScreen().getY();

        //coordinate centro
        x += callingButton.getSize().getWidth() / 2;
        y += callingButton.getSize().getHeight() / 2;

        //coordinate punto in alto a sx frame
        x -= (double) this.getWidth() / 2;
        y -= (double) this.getHeight() / 2;

        this.setLocation((int) x, (int) y);
    }

    /**
     * Provides access to the luggage addition button for external control and integration.
     * <p>
     * This method returns the add luggage button component, enabling external components
     * to control button state, attach additional event listeners, or integrate the button
     * with broader application workflows and user interface coordination. The access
     * supports advanced luggage management integration and customized user experience.
     * </p>
     *
     * @return the JButton component for adding new luggage items to the interface
     */
    public JButton getAddLuggageButton () {
        return addLuggageButton;
    }

    /**
     * Provides access to the confirmation button for external control and workflow integration.
     * <p>
     * This method returns the confirm button component, enabling external components to
     * control button state, monitor confirmation events, or integrate the button with
     * broader luggage management workflows and application coordination. The access
     * supports comprehensive luggage management integration and user experience optimization.
     * </p>
     *
     * @return the JButton component for confirming and closing the luggage management interface
     */
    public JButton getConfirmButton () {
        return confirmButton;
    }

    /**
     * Updates luggage identification labels with specific luggage ID information for tracking and administrative purposes.
     * <p>
     * This method modifies the display labels of existing luggage panels to show specific
     * luggage identification information, typically used when luggage IDs are assigned
     * during check-in procedures or when displaying existing luggage with assigned tracking
     * identifiers. The method ensures that luggage panels display current identification
     * information for optimal user experience and administrative tracking capabilities.
     * </p>
     * <p>
     * Label update process includes:
     * </p>
     * <ul>
     *   <li><strong>Collection Iteration:</strong> Sequential processing of all provided luggage IDs</li>
     *   <li><strong>Panel Correlation:</strong> Direct mapping between luggage IDs and corresponding panels</li>
     *   <li><strong>Label Formatting:</strong> Standardized "Bagaglio: ID" format for consistent identification display</li>
     *   <li><strong>Real-time Updates:</strong> Immediate visual updates for current luggage identification status</li>
     * </ul>
     * <p>
     * Collection iteration processes each provided luggage ID in sequence, ensuring that
     * luggage identification updates are applied to the correct luggage panels based on
     * index correlation and maintaining consistent luggage identification throughout
     * the interface and associated administrative operations.
     * </p>
     * <p>
     * Panel correlation ensures that each luggage ID is applied to the corresponding
     * luggage panel based on index matching, maintaining proper luggage identification
     * relationships and supporting accurate luggage tracking and management operations
     * throughout the application lifecycle and user interactions.
     * </p>
     * <p>
     * Label formatting applies the standardized "Bagaglio: [ID]" format to ensure
     * consistent luggage identification display across all luggage panels and
     * operational contexts, supporting both user recognition and administrative
     * tracking requirements throughout luggage management workflows.
     * </p>
     * <p>
     * Real-time updates provide immediate visual feedback showing current luggage
     * identification status, enabling users and administrators to immediately
     * recognize luggage items and their assigned identifiers for optimal tracking
     * and management capabilities throughout operational processes.
     * </p>
     *
     * @param luggagesIds list of String values containing luggage identification codes for display and tracking purposes
     */
    public void setLuggagesIds (List<String> luggagesIds) {

        for (int i = 0; i < luggagesIds.size(); i++) {

            luggagesPanels.get(i).setLabel("Bagaglio: " + luggagesIds.get(i));
        }
    }
}