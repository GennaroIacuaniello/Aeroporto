package gui;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;

import static gui.FloatingMessage.ERROR_MESSAGE;
import static gui.FloatingMessage.SUCCESS_MESSAGE;

/**
 * Modal dialog for adding new flights to the airport management system.
 * <p>
 * This class provides a comprehensive user interface for administrators to add new flights
 * to the system database. It supports both arriving and departing flight types with
 * complete flight information including schedules, seating capacity, and route details.
 * The dialog implements extensive validation to ensure data integrity and consistency
 * within the airport management system.
 * </p>
 * <p>
 * The dialog features include:
 * </p>
 * <ul>
 *   <li><strong>Flight Information Entry:</strong> Complete flight details including ID, company, and capacity</li>
 *   <li><strong>Schedule Management:</strong> Date and time selection with validation</li>
 *   <li><strong>Route Configuration:</strong> Origin/destination city specification</li>
 *   <li><strong>Flight Type Selection:</strong> Radio button selection for arriving/departing flights</li>
 *   <li><strong>Input Validation:</strong> Comprehensive validation with user-friendly error messages</li>
 *   <li><strong>Database Integration:</strong> Direct integration with flight management controllers</li>
 * </ul>
 * <p>
 * The user interface is organized into a structured form layout with clearly labeled
 * fields and intuitive controls. The dialog adapts its interface based on flight type
 * selection, updating field labels and context appropriately for arriving or departing
 * flights.
 * </p>
 * <p>
 * Validation includes business rule enforcement such as preventing flights with
 * "Napoli" as both origin and destination, ensuring positive seating capacity,
 * and requiring future dates for flight scheduling. All validation errors are
 * displayed through {@link FloatingMessage} components for consistent user feedback.
 * </p>
 * <p>
 * The dialog integrates with the {@link Controller} and {@link FlightController}
 * to persist flight information to the database. Successful flight creation is
 * confirmed with user feedback before closing the dialog.
 * </p>
 * <p>
 * Layout management uses {@link GridBagLayout} with the {@link Constraints} utility
 * for precise component positioning and responsive design. The dialog maintains
 * consistent visual styling with the rest of the application through standardized
 * fonts, colors, and spacing.
 * </p>
 * <p>
 * The dialog is modal, ensuring users complete or cancel the flight creation
 * process before returning to the main application interface. This prevents
 * data inconsistencies and ensures focused user interaction during flight
 * creation workflows.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Controller
 * @see FlightController
 * @see FloatingMessage
 * @see Constraints
 * @see DatePicker
 * @see TimePicker
 */
public class AddNewFlightDialog extends JDialog {

    /**
     * Layout constraints utility for managing component positioning and sizing.
     * <p>
     * This final field provides consistent layout management throughout the dialog
     * using GridBagLayout constraints. It ensures proper component alignment,
     * spacing, and responsive behavior across different screen sizes and
     * system configurations.
     * </p>
     */
    private final Constraints constraints; // Oggetto per la gestione dei vincoli

    /**
     * Dynamic label that changes based on flight type selection.
     * <p>
     * This label displays either "Città di partenza:" (departure city) for
     * arriving flights or "Destinazione:" (destination) for departing flights.
     * The text updates automatically when users change the flight type
     * radio button selection to provide appropriate context.
     * </p>
     */
    private JLabel cityLabel;
    
    /**
     * Text field for entering the unique flight identifier.
     * <p>
     * This field accepts the flight ID which serves as the primary identifier
     * for the flight in the database. The ID must be unique and is validated
     * during the flight creation process to prevent duplicates.
     * </p>
     */
    private JTextField flightIdField;
    
    /**
     * Text field for entering the airline company name.
     * <p>
     * This field captures the name of the airline company operating the flight.
     * The company name is displayed in flight listings and booking information
     * throughout the system for passenger identification and operational purposes.
     * </p>
     */
    private JTextField companyField;
    
    /**
     * Text field for entering the origin or destination city.
     * <p>
     * This field captures the city name for the flight route. For arriving flights,
     * it represents the departure city, while for departing flights, it represents
     * the destination city. The field is validated to prevent "Napoli" as input
     * since Naples is the airport's base location.
     * </p>
     */
    private JTextField cityField;
    
    /**
     * Text field for entering the maximum seating capacity.
     * <p>
     * This numeric field specifies the total number of seats available on the
     * flight. The value must be a positive integer and is validated during
     * form submission. This information is used for booking management and
     * capacity planning throughout the system.
     * </p>
     */
    private JTextField maxSeatsField;
    
    /**
     * Date picker component for selecting the flight date.
     * <p>
     * This component provides an intuitive calendar interface for selecting
     * the flight date. The selected date must be in the future and is validated
     * against the current system date to ensure operational validity.
     * </p>
     */
    private DatePicker flightDatePicker;
    
    /**
     * Time picker component for selecting the departure time.
     * <p>
     * This component allows precise time selection for flight departure.
     * The time is used in conjunction with the flight date to create complete
     * departure timestamp information for scheduling and operational purposes.
     * </p>
     */
    private TimePicker departureTimePicker;
    
    /**
     * Time picker component for selecting the arrival time.
     * <p>
     * This component allows precise time selection for flight arrival.
     * The arrival time is used for passenger information, ground services
     * coordination, and operational planning within the airport management system.
     * </p>
     */
    private TimePicker arrivalTimePicker;
    
    /**
     * Radio button for selecting arriving flight type.
     * <p>
     * When selected, this radio button indicates that the flight is arriving
     * at Naples airport from another city. This affects how route information
     * is interpreted and stored in the database, with the city field representing
     * the departure location.
     * </p>
     */
    private JRadioButton arrivingRadio;
    
    /**
     * Radio button for selecting departing flight type.
     * <p>
     * When selected, this radio button indicates that the flight is departing
     * from Naples airport to another city. This affects how route information
     * is interpreted and stored in the database, with the city field representing
     * the destination location.
     * </p>
     */
    private JRadioButton departingRadio;

    /**
     * Confirmation button for submitting the flight creation form.
     * <p>
     * This final button triggers form validation and flight creation processes
     * when clicked. It is styled consistently with the application theme and
     * provides visual feedback during form submission operations.
     * </p>
     */
    private final JButton confirmButton;

    /**
     * Constructs a new AddNewFlightDialog with comprehensive flight creation interface.
     * <p>
     * This constructor initializes the complete dialog interface including form fields,
     * validation logic, and integration with the flight management system. The dialog
     * is configured as modal to ensure focused user interaction and data consistency.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li>Setting up the modal dialog with appropriate title and properties</li>
     *   <li>Creating and configuring the main panel with consistent styling</li>
     *   <li>Initializing the constraints utility for layout management</li>
     *   <li>Adding the title label with application-consistent typography</li>
     *   <li>Creating and integrating the comprehensive form panel</li>
     *   <li>Configuring the confirmation button with styling and event handling</li>
     *   <li>Finalizing dialog properties including size, position, and modality</li>
     * </ul>
     * <p>
     * The dialog uses a structured layout with the title at the top, the form
     * panel in the center, and the confirmation button at the bottom. This
     * provides an intuitive user flow from information entry to submission.
     * </p>
     * <p>
     * Visual styling includes consistent color schemes, typography, and spacing
     * that match the overall application design. The dialog is centered relative
     * to its parent window and configured as non-resizable to maintain layout
     * integrity.
     * </p>
     * <p>
     * Event handling is established for the confirmation button, connecting
     * user actions to the flight creation workflow through the provided controller.
     * This ensures proper integration with the overall system architecture.
     * </p>
     *
     * @param owner the parent frame that owns this modal dialog
     * @param controller the system controller for handling flight creation operations
     */
    public AddNewFlightDialog(Frame owner, Controller controller) {

        super(owner, "Aggiungi Nuovo Volo", true);
        constraints = new Constraints();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(240, 242, 245));


        JLabel titleLabel = new JLabel("Inserisci le informazioni del volo da aggiungere");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(0, 0, 20, 0));
        mainPanel.add(titleLabel, constraints.getGridBagConstraints());


        JPanel formPanel = createFormPanel();
        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.CENTER, 1.0f, 1.0f, new Insets(20, 0, 20, 0));
        mainPanel.add(formPanel, constraints.getGridBagConstraints());


        confirmButton = new JButton("Conferma");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        confirmButton.setBackground(new Color(0, 120, 215));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);

        confirmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmButton.setPreferredSize(new Dimension(150, 40));

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmAction(controller);
            }
        });

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.PAGE_END, 1.0f, 0.0f, new Insets(10, 0, 0, 0));
        mainPanel.add(confirmButton, constraints.getGridBagConstraints());

        this.setContentPane(mainPanel);
        this.pack();
        this.setLocationRelativeTo(owner);
        this.setResizable(false);
    }

    /**
     * Creates and configures the comprehensive form panel for flight information entry.
     * <p>
     * This method constructs the main form interface with all necessary input fields
     * for flight creation. The form is organized in a grid layout with logical
     * grouping of related fields and consistent spacing for optimal usability.
     * </p>
     * <p>
     * The form includes the following sections:
     * </p>
     * <ul>
     *   <li><strong>Flight Identification:</strong> Flight ID and airline company fields</li>
     *   <li><strong>Schedule Information:</strong> Flight date with date picker component</li>
     *   <li><strong>Route Details:</strong> Dynamic city field that adapts to flight type</li>
     *   <li><strong>Timing Information:</strong> Departure and arrival time pickers</li>
     *   <li><strong>Capacity Management:</strong> Maximum seats numeric input field</li>
     *   <li><strong>Flight Type Selection:</strong> Radio buttons for arriving/departing flights</li>
     * </ul>
     * <p>
     * The layout uses a 4-column grid structure with labels in even columns and
     * input fields in odd columns. This provides balanced visual weight and
     * efficient use of dialog space while maintaining readability.
     * </p>
     * <p>
     * Dynamic behavior is implemented for the flight type selection, where radio
     * button changes automatically update the city label text to reflect the
     * appropriate context (departure city for arriving flights, destination for
     * departing flights).
     * </p>
     * <p>
     * All input components are configured with consistent styling including fonts,
     * sizes, and appearance settings that match the overall application design.
     * Field validation is handled separately during form submission.
     * </p>
     * <p>
     * The radio button panel uses a grouped layout to ensure mutually exclusive
     * selection and proper visual organization. Event listeners are attached to
     * handle dynamic label updates based on flight type selection.
     * </p>
     *
     * @return the configured form panel containing all flight information input fields
     */
    private JPanel createFormPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        Insets labelInsets = new Insets(5, 5, 5, 10);
        Insets fieldInsets = new Insets(5, 0, 5, 25);


        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, labelInsets);
        panel.add(createLabel("ID volo:"), constraints.getGridBagConstraints());

        flightIdField = new JTextField(15);
        setFieldAppearance(flightIdField);

        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, fieldInsets);
        panel.add(flightIdField, constraints.getGridBagConstraints());


        constraints.setConstraints(2, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, labelInsets);
        panel.add(createLabel("Compagnia aerea:"), constraints.getGridBagConstraints());

        companyField = new JTextField(15);
        setFieldAppearance(companyField);

        constraints.setConstraints(3, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, fieldInsets);
        panel.add(companyField, constraints.getGridBagConstraints());


        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, labelInsets);
        panel.add(createLabel("Data del volo:"), constraints.getGridBagConstraints());

        flightDatePicker = new DatePicker();
        flightDatePicker.getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, fieldInsets);
        panel.add(flightDatePicker, constraints.getGridBagConstraints());


        cityLabel = createLabel("Città di partenza:");
        constraints.setConstraints(2, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, labelInsets);
        panel.add(cityLabel, constraints.getGridBagConstraints());

        cityField = new JTextField(15);
        setFieldAppearance(cityField);

        constraints.setConstraints(3, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, fieldInsets);
        panel.add(cityField, constraints.getGridBagConstraints());


        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, labelInsets);
        panel.add(createLabel("Orario di partenza:"), constraints.getGridBagConstraints());

        departureTimePicker = new TimePicker();
        departureTimePicker.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, fieldInsets);
        panel.add(departureTimePicker, constraints.getGridBagConstraints());


        constraints.setConstraints(2, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, labelInsets);
        panel.add(createLabel("Orario di arrivo:"), constraints.getGridBagConstraints());

        arrivalTimePicker = new TimePicker();
        arrivalTimePicker.getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        constraints.setConstraints(3, 2, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, fieldInsets);
        panel.add(arrivalTimePicker, constraints.getGridBagConstraints());


        constraints.setConstraints(0, 3, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.LINE_END, 0.0f, 0.0f, labelInsets);
        panel.add(createLabel("Posti massimi:"), constraints.getGridBagConstraints());

        maxSeatsField = new JTextField(15);
        setFieldAppearance(maxSeatsField);

        constraints.setConstraints(1, 3, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.LINE_START, 1.0f, 0.0f, fieldInsets);
        panel.add(maxSeatsField, constraints.getGridBagConstraints());


        arrivingRadio = new JRadioButton("Volo per Napoli");
        arrivingRadio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        arrivingRadio.setOpaque(false);

        departingRadio = new JRadioButton("Volo in partenza da Napoli");
        departingRadio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        departingRadio.setOpaque(false);

        ButtonGroup flightTypeGroup = new ButtonGroup();

        flightTypeGroup.add(arrivingRadio);
        flightTypeGroup.add(departingRadio);

        arrivingRadio.setSelected(true);

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        radioPanel.setOpaque(false);

        radioPanel.add(arrivingRadio);
        radioPanel.add(departingRadio);

        constraints.setConstraints(2, 3, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.CENTER, 1.0f, 0.0f, new Insets(5, 5, 5, 5));
        panel.add(radioPanel, constraints.getGridBagConstraints());

        arrivingRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arrivingRadio.isSelected()) {
                    cityLabel.setText("Città di partenza:");
                } else {
                    cityLabel.setText("Destinazione:");
                }
            }
        });

        departingRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (arrivingRadio.isSelected()) {
                    cityLabel.setText("Città di partenza:");
                } else {
                    cityLabel.setText("Destinazione:");
                }
            }
        });

        return panel;
    }

    /**
     * Creates a standardized label with consistent styling for form fields.
     * <p>
     * This utility method generates labels with uniform appearance throughout
     * the dialog, ensuring visual consistency and professional presentation.
     * All labels use the same font family, style, and size for cohesive design.
     * </p>
     * <p>
     * The method applies Segoe UI font with bold styling and 14-point size,
     * matching the overall application typography standards. This provides
     * clear readability and appropriate visual hierarchy within the form.
     * </p>
     *
     * @param text the text content to display in the label
     * @return a configured JLabel with standardized appearance
     */
    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;

    }

    /**
     * Applies consistent styling to text input fields throughout the form.
     * <p>
     * This utility method ensures all text fields have uniform appearance
     * and behavior, creating a cohesive user interface experience. The styling
     * includes font configuration for optimal readability and user interaction.
     * </p>
     * <p>
     * The method applies Segoe UI font with plain styling and 16-point size,
     * providing clear text entry visibility while maintaining consistency with
     * the overall application design language.
     * </p>
     *
     * @param field the JTextField to style with standard appearance settings
     */
    private void setFieldAppearance(JTextField field) {

        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));

    }

    /**
     * Processes form submission with comprehensive validation and flight creation.
     * <p>
     * This method handles the complete flight creation workflow including data
     * extraction from form fields, comprehensive validation of all input values,
     * business rule enforcement, and integration with the flight management system
     * for database persistence.
     * </p>
     * <p>
     * The validation process includes multiple stages:
     * </p>
     * <ul>
     *   <li><strong>Completeness Check:</strong> Ensures all required fields contain data</li>
     *   <li><strong>Data Type Validation:</strong> Verifies numeric fields contain valid integers</li>
     *   <li><strong>Business Rule Enforcement:</strong> Applies airport-specific constraints</li>
     *   <li><strong>Logical Validation:</strong> Ensures data consistency and operational validity</li>
     * </ul>
     * <p>
     * Business rules enforced include:
     * </p>
     * <ul>
     *   <li>Maximum seats must be a positive integer greater than zero</li>
     *   <li>Flight date must be in the future (after current system date)</li>
     *   <li>City name cannot be "Napoli" (prevents internal routing conflicts)</li>
     *   <li>All time fields must contain valid time values</li>
     * </ul>
     * <p>
     * Error handling provides specific, user-friendly feedback for each validation
     * failure through {@link FloatingMessage} components. This ensures users
     * understand exactly what needs to be corrected for successful submission.
     * </p>
     * <p>
     * Successful validation triggers flight creation through the {@link FlightController}
     * with appropriate flight type determination based on radio button selection.
     * The method handles both arriving flights (flightType = false) and departing
     * flights (flightType = true) with proper data interpretation.
     * </p>
     * <p>
     * Upon successful flight creation, the user receives confirmation feedback
     * and the dialog is automatically closed. Database errors are handled gracefully
     * with appropriate error messages displayed to the user.
     * </p>
     *
     * @param controller the system controller for handling flight creation operations
     */
    private void confirmAction(Controller controller) {

        String flightId = flightIdField.getText();
        String companyName = companyField.getText();
        LocalDate flightDate = flightDatePicker.getDate();
        String otherCity = cityField.getText();
        LocalTime departureTime = departureTimePicker.getTime();
        LocalTime arrivalTime = arrivalTimePicker.getTime();
        String maxSeatsStr = maxSeatsField.getText();

        boolean flightType = departingRadio.isSelected();

        if (flightId.isEmpty() || companyName.isEmpty() || flightDate == null || otherCity.isEmpty() ||
                departureTime == null || arrivalTime == null || maxSeatsStr.isEmpty()) {

            new FloatingMessage("Tutti i campi sono obbligatori!", confirmButton, ERROR_MESSAGE);

            return;
        }

        try {
            int maxSeats = Integer.parseInt(maxSeatsStr);

            if (maxSeats <= 0) {

                new FloatingMessage("Il numero di posti massimi deve essere maggiore di zero!", confirmButton, ERROR_MESSAGE);
                return;

            }else if(flightDate.isBefore(LocalDate.now())){

                new FloatingMessage("La data deve essere successiva alla data odierna!", confirmButton, ERROR_MESSAGE);
                return;

            }else if(otherCity.equalsIgnoreCase("Napoli")){

                new FloatingMessage("La città non può essere Napoli!", confirmButton, ERROR_MESSAGE);
                return;

            }


            if(controller.getFlightController().addFlight(flightId, companyName, flightDate, departureTime, arrivalTime, maxSeats, otherCity, flightType, confirmButton)){

                new FloatingMessage("Volo aggiunto con successo!", confirmButton, SUCCESS_MESSAGE);
                dispose();

            }



        } catch (NumberFormatException e) {

            new FloatingMessage("Il numero di posti massimi non è un numero valido.", confirmButton, ERROR_MESSAGE);

        }
    }
}