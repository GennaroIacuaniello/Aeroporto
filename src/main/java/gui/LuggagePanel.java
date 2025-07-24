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
 * Type selection integration provides a standardized dropdown interface with three options:
 * a "TYPE" placeholder indicating unselected state, "CARRY_ON" for cabin baggage, and "CHECKED"
 * for cargo hold luggage. This selection directly maps to the {@link model.LuggageType} enumeration
 * used throughout the luggage management system.
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
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JPanel
 * @see LuggagesView
 * @see RemoveLuggageButton
 * @see PassengerPanel
 * @see model.LuggageType
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