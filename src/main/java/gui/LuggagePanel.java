package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Specialized panel component for individual luggage item display and management in the airport management system.
 * <p>
 * This class extends {@link JPanel} to provide a dedicated interface component for managing individual
 * luggage items within the broader luggage management system. Each LuggagePanel represents a single
 * piece of luggage with its associated properties, type selection capabilities, and identification
 * information, supporting both customer booking workflows and administrative luggage management operations.
 * </p>
 * <p>
 * The LuggagePanel class provides comprehensive luggage item functionality including:
 * </p>
 * <ul>
 *   <li><strong>Interactive Type Selection:</strong> Dropdown interface for choosing luggage type (CARRY_ON or CHECKED)</li>
 *   <li><strong>Dynamic Labeling:</strong> Configurable label display supporting both indexed and ticket-based identification</li>
 *   <li><strong>Validation Support:</strong> Built-in validation methods for ensuring proper luggage configuration</li>
 *   <li><strong>Layout Integration:</strong> GridBagLayout-based design optimized for container integration</li>
 *   <li><strong>Data Extraction:</strong> Methods for retrieving luggage information for processing and storage</li>
 *   <li><strong>Index Management:</strong> Sequential indexing system for multi-luggage scenarios</li>
 * </ul>
 * <p>
 * The panel is designed with user experience optimization, providing customers and administrators with:
 * </p>
 * <ul>
 *   <li><strong>Intuitive Interface:</strong> Clear visual hierarchy with centered label and dropdown selection</li>
 *   <li><strong>Type Selection:</strong> Comprehensive dropdown with TYPE placeholder and standard luggage options</li>
 *   <li><strong>Visual Consistency:</strong> Standardized appearance matching application design principles</li>
 *   <li><strong>Flexible Identification:</strong> Support for both sequential numbering and ticket-based labeling</li>
 *   <li><strong>Immediate Visibility:</strong> All components visible upon creation for immediate user interaction</li>
 * </ul>
 * <p>
 * Layout architecture utilizes {@link GridBagLayout} for precise component positioning and optimal
 * space utilization. The panel maintains a two-row structure with the identification label in the
 * top row and the type selection dropdown in the bottom row, ensuring consistent visual organization
 * across different luggage management contexts.
 * </p>
 * <p>
 * Type selection integration provides a standardized dropdown interface with three options:
 * a "TYPE" placeholder indicating unselected state, "CARRY_ON" for cabin baggage, and "CHECKED"
 * for cargo hold luggage. This selection directly maps to the {@link LuggageType} enumeration
 * used throughout the luggage management system.
 * </p>
 * <p>
 * Validation capabilities include the {@link #checkLuggage()} method that determines whether
 * a luggage item has been properly configured by checking if a valid type has been selected.
 * This validation integrates with broader luggage collection validation workflows in parent
 * components and booking processes.
 * </p>
 * <p>
 * Data extraction functionality enables parent components to retrieve luggage configuration
 * information including selected type indices and associated ticket numbers. The panel supports
 * both indexed access for programmatic processing and ticket-based identification for
 * administrative tracking and customer service operations.
 * </p>
 * <p>
 * Index management provides sequential numbering capabilities for scenarios involving multiple
 * luggage items. The index system supports dynamic luggage addition and removal while maintaining
 * consistent identification across luggage collections in booking and administrative interfaces.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader luggage management ecosystem including
 * {@link LuggagesView} containers, {@link RemoveLuggageButton} removal functionality, and
 * {@link PassengerPanel} passenger-luggage associations while maintaining clean separation
 * of concerns and reusable component design.
 * </p>
 * <p>
 * Label management supports both static sequential numbering ("Bagaglio:1", "Bagaglio:2", etc.)
 * and dynamic ticket-based identification ("Bagaglio: ABC123") enabling the panel to adapt
 * to different usage contexts from initial booking creation to post-booking luggage management
 * and administrative oversight operations.
 * </p>
 * <p>
 * The panel maintains visual consistency through standardized component styling, proper spacing,
 * and adherence to application design principles while providing the flexibility required for
 * integration across different luggage management workflows and user interface contexts.
 * </p>
 * <p>
 * Resource management follows standard Swing component lifecycle patterns with proper component
 * initialization, visibility management, and integration with parent container layouts for
 * optimal performance and memory utilization during extended application usage.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JPanel
 * @see LuggagesView
 * @see RemoveLuggageButton
 * @see PassengerPanel
 * @see LuggageType
 * @see GridBagLayout
 */
public class LuggagePanel extends JPanel {

    /**
     * Display label for luggage identification and information.
     * <p>
     * This JLabel provides visual identification for the luggage item, supporting
     * both sequential numbering for new luggage items and ticket-based identification
     * for existing luggage. The label uses center alignment for optimal visual
     * presentation and maintains consistent styling across different luggage contexts.
     * </p>
     */
    private JLabel label;
    
    /**
     * Sequential index for luggage item identification and ordering.
     * <p>
     * This immutable integer maintains the sequential position of the luggage item
     * within collections of luggage panels. The index supports dynamic luggage
     * management including addition, removal, and reordering operations while
     * providing consistent identification for programmatic access and user display.
     * </p>
     */
    private final int index;
    
    /**
     * Interactive dropdown component for luggage type selection.
     * <p>
     * This JComboBox provides user interface for selecting luggage type classification
     * including carry-on and checked baggage options. The dropdown includes a placeholder
     * option and maps directly to system luggage type enumeration for consistent
     * data management and validation throughout the application.
     * </p>
     */
    private JComboBox<String> comboBox;

    /**
     * Layout constraints utility for precise component positioning.
     * <p>
     * This Constraints helper object provides standardized GridBagConstraints
     * configuration for optimal component layout and positioning. The constraints
     * ensure consistent spacing, alignment, and visual organization across all
     * luggage panel instances and integration contexts.
     * </p>
     */
    private final Constraints constraints;

    /**
     * Constructs a new LuggagePanel with specified index for luggage item management and display.
     * <p>
     * This constructor initializes a complete luggage management panel with sequential indexing,
     * interactive type selection, and proper layout configuration. The constructor establishes
     * all necessary components for immediate luggage item configuration including identification
     * labeling and type selection dropdown with standardized styling and behavior.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Layout Configuration:</strong> GridBagLayout setup for precise component positioning</li>
     *   <li><strong>Constraints Initialization:</strong> Layout constraints utility for consistent component arrangement</li>
     *   <li><strong>Index Assignment:</strong> Sequential numbering for luggage identification and management</li>
     *   <li><strong>Label Creation:</strong> Identification label with centered alignment and default numbering</li>
     *   <li><strong>Dropdown Setup:</strong> Type selection interface with placeholder and standard options</li>
     *   <li><strong>Component Integration:</strong> Proper component addition with optimized layout constraints</li>
     * </ul>
     * <p>
     * Layout configuration establishes GridBagLayout as the primary layout manager, providing
     * precise control over component positioning and sizing. The layout supports the two-row
     * structure required for optimal luggage panel organization with label and dropdown
     * arrangement for intuitive user interaction.
     * </p>
     * <p>
     * Constraints initialization creates the Constraints utility object that standardizes
     * GridBagConstraints configuration across all component additions. This ensures consistent
     * spacing, alignment, and visual presentation regardless of the specific layout requirements
     * or integration context within parent containers.
     * </p>
     * <p>
     * Index assignment stores the provided sequential number as an immutable reference,
     * enabling consistent luggage identification and supporting dynamic luggage collection
     * management including addition, removal, and reordering operations in parent interfaces.
     * </p>
     * <p>
     * Label creation establishes the identification display through the setLabel method,
     * configuring center alignment and default sequential numbering format. The label
     * provides immediate visual identification and supports later customization for
     * ticket-based identification in administrative and tracking contexts.
     * </p>
     * <p>
     * Dropdown setup creates the type selection interface through the setComboBox method,
     * populating the dropdown with placeholder and standard luggage type options. The
     * dropdown provides immediate user interaction capability and integrates with system
     * validation and data processing workflows.
     * </p>
     * <p>
     * Component integration includes proper addition of both label and dropdown to the
     * panel with optimized GridBagConstraints configuration. The integration ensures
     * immediate visibility and proper layout behavior within parent container contexts
     * including scrollable views and dynamic luggage collections.
     * </p>
     * <p>
     * The constructor completes by ensuring all components are visible and properly
     * configured for immediate user interaction, supporting both customer booking
     * workflows and administrative luggage management operations.
     * </p>
     *
     * @param i the sequential index for luggage identification and collection management
     */
    public LuggagePanel(int i) {

        this.setLayout(new GridBagLayout());
        constraints = new Constraints();
        index = i;

        setLabel();
        setComboBox();
    }

    /**
     * Updates the luggage identification label with specified text content.
     * <p>
     * This method provides dynamic label customization capability, enabling the panel
     * to display different identification formats including sequential numbering for
     * new luggage items and ticket-based identification for existing luggage. The
     * method supports real-time label updates during luggage management operations
     * and administrative tracking workflows.
     * </p>
     * <p>
     * Label updates support various identification formats including:
     * </p>
     * <ul>
     *   <li><strong>Sequential Format:</strong> Default "Bagaglio:N" numbering for new luggage items</li>
     *   <li><strong>Ticket-Based Format:</strong> "Bagaglio: TICKET_ID" for existing luggage tracking</li>
     *   <li><strong>Custom Identification:</strong> Administrative labels for special luggage categories</li>
     *   <li><strong>Status Indicators:</strong> Dynamic labels indicating luggage processing states</li>
     * </ul>
     * <p>
     * The method maintains existing label styling and positioning while updating only
     * the text content, ensuring visual consistency across different identification
     * contexts and luggage management scenarios.
     * </p>
     *
     * @param string the new text content for the luggage identification label
     */
    public void setLabel(String string) {
        label.setText(string);
    }

    /**
     * Initializes the luggage identification label with default sequential numbering and optimal visual styling.
     * <p>
     * This private method establishes the luggage identification display component with standardized
     * formatting, center alignment, and proper layout integration. The method creates a JLabel
     * with default sequential numbering format and configures it for optimal visual presentation
     * within the luggage panel's two-row layout structure.
     * </p>
     * <p>
     * Label initialization includes:
     * </p>
     * <ul>
     *   <li><strong>Component Creation:</strong> JLabel instantiation with default "Bagaglio:N" format</li>
     *   <li><strong>Alignment Configuration:</strong> Center horizontal alignment for optimal visual balance</li>
     *   <li><strong>Layout Integration:</strong> GridBagConstraints configuration for top-row positioning</li>
     *   <li><strong>Container Addition:</strong> Proper component addition to panel with layout constraints</li>
     *   <li><strong>Visibility Activation:</strong> Immediate component visibility for user interaction</li>
     * </ul>
     * <p>
     * Component creation establishes the JLabel with default text format combining the Italian
     * "Bagaglio" (Luggage) term with sequential numbering based on the panel's index value.
     * The format provides immediate identification while supporting later customization for
     * different luggage tracking and management contexts.
     * </p>
     * <p>
     * Alignment configuration applies SwingConstants.CENTER horizontal alignment to ensure
     * optimal visual presentation within the panel's layout structure. The center alignment
     * provides balanced appearance regardless of text length variations in different
     * identification formats and luggage tracking scenarios.
     * </p>
     * <p>
     * Layout integration utilizes the Constraints utility to apply standardized GridBagConstraints
     * for top-row positioning (0,0) with NONE fill behavior and CENTER anchor point. The
     * constraints ensure consistent positioning and spacing across different luggage panel
     * instances and parent container contexts.
     * </p>
     * <p>
     * Container addition includes proper component integration with the panel using configured
     * layout constraints, ensuring optimal positioning and visual organization within the
     * overall luggage panel structure and supporting responsive layout behavior.
     * </p>
     * <p>
     * Visibility activation immediately enables component display for user interaction,
     * ensuring that the label is available for viewing and interaction as soon as the
     * luggage panel is created and integrated within parent interface components.
     * </p>
     */
    private void setLabel() {
        label = new JLabel("Bagaglio:" + (index + 1));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add(label, constraints.getGridBagConstraints());

        label.setVisible(true);
    }

    /**
     * Initializes the luggage type selection dropdown with standard options and optimal layout integration.
     * <p>
     * This private method establishes the interactive type selection component with standardized
     * luggage type options, proper validation placeholder, and optimal layout positioning. The
     * method creates a JComboBox with three essential options supporting both user selection
     * validation and direct integration with system luggage type enumeration.
     * </p>
     * <p>
     * Dropdown initialization includes:
     * </p>
     * <ul>
     *   <li><strong>Component Creation:</strong> JComboBox instantiation with String generic type for text options</li>
     *   <li><strong>Option Population:</strong> Standard luggage type options with validation placeholder</li>
     *   <li><strong>Layout Integration:</strong> GridBagConstraints configuration for bottom-row positioning</li>
     *   <li><strong>Container Addition:</strong> Proper component addition to panel with layout constraints</li>
     *   <li><strong>Visibility Activation:</strong> Immediate component visibility for user interaction</li>
     * </ul>
     * <p>
     * Component creation establishes the JComboBox with String generic typing to support
     * text-based option display and selection. The component provides standard dropdown
     * interface behavior with click-to-expand functionality and keyboard navigation
     * support for optimal user experience across different interaction methods.
     * </p>
     * <p>
     * Option population includes three essential items: "TYPE" as validation placeholder
     * indicating unselected state, "CARRY_ON" for cabin baggage selection, and "CHECKED"
     * for cargo hold luggage selection. The options directly correspond to system luggage
     * type enumeration and support comprehensive luggage classification workflows.
     * </p>
     * <p>
     * The "TYPE" placeholder option serves dual purposes: providing clear visual indication
     * of unselected state for validation purposes and offering intuitive user guidance
     * about the dropdown's purpose. This placeholder integrates with validation methods
     * to ensure proper luggage configuration before processing.
     * </p>
     * <p>
     * Layout integration utilizes the Constraints utility to apply standardized GridBagConstraints
     * for bottom-row positioning (0,1) with NONE fill behavior and CENTER anchor point. The
     * constraints ensure consistent positioning below the identification label and maintain
     * visual balance within the panel's two-row structure.
     * </p>
     * <p>
     * Container addition includes proper component integration with the panel using configured
     * layout constraints, ensuring optimal positioning relative to the identification label
     * and supporting responsive layout behavior within parent container contexts.
     * </p>
     * <p>
     * Visibility activation immediately enables component display and interaction, ensuring
     * that users can begin type selection as soon as the luggage panel is created and
     * integrated within booking interfaces and luggage management workflows.
     * </p>
     */
    private void setComboBox() {

        comboBox = new JComboBox<>();

        comboBox.addItem("TYPE");
        comboBox.addItem("CARRY_ON");
        comboBox.addItem("CHECKED");

        constraints.setConstraints(0, 1, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add(comboBox, constraints.getGridBagConstraints());
        comboBox.setVisible(true);
    }

    /**
     * Returns the sequential index assigned to this luggage panel for identification and collection management.
     * <p>
     * This method provides access to the immutable index value that identifies this luggage
     * panel's position within collections of luggage items. The index supports dynamic luggage
     * management operations including addition, removal, and reordering while maintaining
     * consistent identification for both programmatic access and user interface display.
     * </p>
     * <p>
     * Index usage includes:
     * </p>
     * <ul>
     *   <li><strong>Collection Management:</strong> Position tracking within luggage panel arrays and lists</li>
     *   <li><strong>Dynamic Operations:</strong> Support for luggage addition, removal, and reordering</li>
     *   <li><strong>User Display:</strong> Sequential numbering for intuitive luggage identification</li>
     *   <li><strong>Validation Workflows:</strong> Index-based validation and processing coordination</li>
     * </ul>
     * <p>
     * The index remains constant throughout the panel's lifecycle, providing stable identification
     * even during dynamic luggage collection modifications and parent container operations.
     * </p>
     *
     * @return the sequential index assigned to this luggage panel
     */
    public int getIndex() {
        return index;
    }

    /**
     * Validates whether the luggage item has been properly configured with a valid type selection.
     * <p>
     * This method performs essential validation by checking if the user has selected a valid
     * luggage type from the dropdown interface. The validation ensures that luggage items
     * are properly configured before processing in booking workflows, administrative operations,
     * and luggage management procedures throughout the airport management system.
     * </p>
     * <p>
     * Validation logic checks if the dropdown's selected index equals 0, which corresponds
     * to the "TYPE" placeholder option indicating an unselected state. The method returns
     * true when validation fails (indicating incomplete configuration) and false when a
     * valid luggage type has been selected.
     * </p>
     * <p>
     * Validation integration supports:
     * </p>
     * <ul>
     *   <li><strong>Booking Workflows:</strong> Ensuring complete luggage configuration before booking confirmation</li>
     *   <li><strong>Administrative Processing:</strong> Validating luggage data completeness during check-in operations</li>
     *   <li><strong>User Interface Feedback:</strong> Enabling parent components to provide validation error messages</li>
     *   <li><strong>Data Processing:</strong> Preventing incomplete luggage data from entering system workflows</li>
     * </ul>
     * <p>
     * The validation method integrates with broader luggage collection validation workflows
     * in parent components, supporting comprehensive validation of multiple luggage items
     * and providing clear feedback about configuration completeness to users and administrators.
     * </p>
     *
     * @return true if luggage type selection is incomplete (validation fails), false if a valid type is selected
     */
    public boolean checkLuggage() {
        return comboBox.getSelectedIndex() == 0;
    }

    /**
     * Configures the luggage type selection based on provided type integer value.
     * <p>
     * This method enables programmatic configuration of the luggage type dropdown by setting
     * the selected index based on luggage type enumeration values. The method supports both
     * administrative luggage configuration and data restoration workflows where existing
     * luggage type information needs to be displayed in the user interface.
     * </p>
     * <p>
     * Type mapping converts integer enumeration values to appropriate dropdown indices:
     * type value + 1 accounts for the "TYPE" placeholder at index 0, ensuring correct
     * mapping between system luggage type enumeration and user interface selection options.
     * </p>
     * <p>
     * Configuration scenarios include:
     * </p>
     * <ul>
     *   <li><strong>Data Restoration:</strong> Loading existing luggage type selections from database records</li>
     *   <li><strong>Administrative Override:</strong> Programmatic type assignment during check-in operations</li>
     *   <li><strong>Booking Modification:</strong> Updating luggage types during booking change workflows</li>
     *   <li><strong>System Integration:</strong> Synchronizing luggage types across different system components</li>
     * </ul>
     * <p>
     * The method maintains consistency with manual user selection behavior while providing
     * programmatic access for automated workflows and administrative operations throughout
     * the luggage management system.
     * </p>
     *
     * @param type the luggage type integer value corresponding to system enumeration (0 for CARRY_ON, 1 for CHECKED)
     */
    public void setType(Integer type) {
        comboBox.setSelectedIndex(type + 1);
    }

    /**
     * Provides access to the luggage type selection dropdown component for advanced configuration and integration.
     * <p>
     * This method returns the JComboBox component that manages luggage type selection, enabling
     * parent components and administrative interfaces to perform advanced configuration, state
     * management, and integration operations. The access supports both read-only inspection
     * and dynamic configuration of dropdown behavior and appearance.
     * </p>
     * <p>
     * Component access enables:
     * </p>
     * <ul>
     *   <li><strong>State Management:</strong> Enabling/disabling dropdown based on operational context</li>
     *   <li><strong>Event Handling:</strong> Attaching custom listeners for selection change events</li>
     *   <li><strong>Visual Customization:</strong> Modifying appearance for different user interface contexts</li>
     *   <li><strong>Validation Integration:</strong> Direct access for complex validation and processing workflows</li>
     * </ul>
     * <p>
     * The method supports integration with parent container management workflows including
     * enabling/disabling luggage configuration during different booking phases and providing
     * administrative interfaces with direct component access for specialized operations.
     * </p>
     *
     * @return the JComboBox component managing luggage type selection
     */
    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    /**
     * Extracts the ticket identifier from the luggage identification label for tracking and administrative purposes.
     * <p>
     * This method retrieves ticket-based identification information from the luggage label text,
     * supporting administrative tracking, luggage-passenger association, and customer service
     * operations. The method utilizes string parsing to extract ticket numbers from formatted
     * label text, enabling integration with ticket-based luggage management workflows.
     * </p>
     * <p>
     * Ticket extraction supports:
     * </p>
     * <ul>
     *   <li><strong>Administrative Tracking:</strong> Associating luggage items with specific passenger tickets</li>
     *   <li><strong>Customer Service:</strong> Enabling ticket-based luggage lookup and management</li>
     *   <li><strong>System Integration:</strong> Connecting luggage panels with broader ticket management systems</li>
     *   <li><strong>Operational Workflows:</strong> Supporting check-in, boarding, and luggage handling procedures</li>
     * </ul>
     * <p>
     * The method leverages the {@link #getSubstringAfterColon(String)} utility method to parse
     * formatted label text and extract ticket identification following the colon separator.
     * The extraction handles various label formats while maintaining consistent ticket
     * identification retrieval across different luggage management contexts.
     * </p>
     *
     * @return the ticket identifier extracted from the luggage label, or null if no ticket information is present
     */
    public String getTicket () {
        return getSubstringAfterColon(label.getText());
    }

    /**
     * Utility method for extracting text content following a colon separator with robust null and boundary handling.
     * <p>
     * This static utility method provides safe string parsing functionality for extracting content
     * that follows a colon separator in formatted text strings. The method includes comprehensive
     * error handling for null inputs, empty strings, missing separators, and boundary conditions
     * to ensure reliable text extraction across various input scenarios.
     * </p>
     * <p>
     * Parsing functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Null Safety:</strong> Proper handling of null input strings without exceptions</li>
     *   <li><strong>Empty String Handling:</strong> Appropriate response to empty input strings</li>
     *   <li><strong>Separator Detection:</strong> Robust colon character location and validation</li>
     *   <li><strong>Boundary Conditions:</strong> Proper handling of edge cases including colon at string end</li>
     *   <li><strong>Content Extraction:</strong> Accurate substring extraction with proper offset handling</li>
     * </ul>
     * <p>
     * The method performs comprehensive input validation by checking for null and empty strings
     * before attempting any parsing operations. This prevents NullPointerException and ensures
     * predictable behavior across different calling contexts and input conditions.
     * </p>
     * <p>
     * Separator detection uses the String.indexOf method to locate the first colon character,
     * providing reliable separator identification even in strings containing multiple colons.
     * The method returns null when no colon separator is found, indicating invalid format.
     * </p>
     * <p>
     * Boundary condition handling includes special logic for cases where the colon appears
     * at the end of the input string, returning an empty string to distinguish this case
     * from complete parsing failures that return null.
     * </p>
     * <p>
     * Content extraction utilizes String.substring with proper offset calculation (colonIndex + 2)
     * to account for both the colon character and the expected space character that follows
     * in formatted luggage label text, ensuring clean extraction of ticket identifiers and
     * other colon-separated content.
     * </p>
     * <p>
     * The utility method supports various parsing scenarios throughout the luggage management
     * system including ticket extraction from luggage labels, administrative data parsing,
     * and general text processing workflows requiring colon-separated content extraction.
     * </p>
     *
     * @param inputString the input string to parse for colon-separated content
     * @return the substring following the first colon and space, empty string if colon is at end, or null for invalid input/format
     */
    public static String getSubstringAfterColon(String inputString) {
        // Controlla se la stringa è valida (non null e non vuota)
        if (inputString == null || inputString.isEmpty()) {
            return null;
        }

        // Trova l'indice del primo carattere ':'
        int colonIndex = inputString.indexOf(":");

        // Se il carattere ':' non è stato trovato, restituisce null
        if (colonIndex == -1) {
            return null;
        }

        // Se il carattere ':' è l'ultimo carattere della stringa, restituisce una stringa vuota
        if (colonIndex == inputString.length() - 1) {
            return "";
        }

        // Estrae e restituisce la sottostringa che segue il carattere ':'
        return inputString.substring(colonIndex + 2);
    }
}