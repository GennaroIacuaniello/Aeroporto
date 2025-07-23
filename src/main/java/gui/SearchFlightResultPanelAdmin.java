package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Administrative flight search results display panel providing comprehensive flight management capabilities and operational oversight for the airport management system.
 * <p>
 * This class extends {@link JPanel} to provide a specialized administrative interface for displaying flight
 * search results with integrated flight management functionality and professional table presentation. The
 * SearchFlightResultPanelAdmin serves as the primary flight results display component for administrative workflows,
 * offering comprehensive flight information presentation, operational management capabilities, and direct access
 * to detailed flight administration through an intuitive table-based interface optimized for airport management operations.
 * </p>
 * <p>
 * The SearchFlightResultPanelAdmin class provides comprehensive administrative flight result capabilities including:
 * </p>
 * <ul>
 *   <li><strong>Flight Information Display:</strong> Complete flight details with company, route, schedule, delay, and capacity information</li>
 *   <li><strong>Management Integration:</strong> Direct flight administration access with comprehensive operational control capabilities</li>
 *   <li><strong>Professional Table Presentation:</strong> Alternating row colors, center alignment, and optimized typography for administrative readability</li>
 *   <li><strong>Interactive Management Buttons:</strong> Universal button rendering for all flights regardless of status or availability</li>
 *   <li><strong>Empty State Management:</strong> Professional messaging for no-results scenarios with centered administrative guidance</li>
 *   <li><strong>Status Localization:</strong> Italian language translation for all flight operational status information</li>
 *   <li><strong>Navigation Integration:</strong> Seamless integration with administrative management workflows and interface hierarchy</li>
 * </ul>
 * <p>
 * The interface is designed with administrative efficiency optimization, providing airport administrators with:
 * </p>
 * <ul>
 *   <li><strong>Complete Flight Information:</strong> Comprehensive flight details with operational indicators and management context</li>
 *   <li><strong>Operational Oversight:</strong> Real-time flight status display with detailed capacity and operational information</li>
 *   <li><strong>Management Access:</strong> One-click flight administration initiation with comprehensive operational control</li>
 *   <li><strong>Professional Presentation:</strong> Clean administrative table design with optimal spacing and information density</li>
 *   <li><strong>Status Clarity:</strong> Italian-localized flight status information for administrative understanding and decision-making</li>
 *   <li><strong>Responsive Design:</strong> Adaptive administrative interface that maintains functionality across different window configurations</li>
 * </ul>
 * <p>
 * Table architecture utilizes {@link BorderLayout} for optimal component organization with header
 * positioning in the NORTH region and flight results table in the CENTER region. The layout
 * supports dynamic content sizing and maintains professional presentation throughout administrative
 * flight management and operational oversight workflows.
 * </p>
 * <p>
 * Flight information display includes comprehensive administrative details for operational management:
 * </p>
 * <ul>
 *   <li><strong>Company Information:</strong> Airline company names for carrier identification and operational coordination</li>
 *   <li><strong>Route Display:</strong> Directional route information with Naples integration and arrow indicators for operational clarity</li>
 *   <li><strong>Schedule Details:</strong> Departure and arrival times with professional HH:MM formatting for schedule management</li>
 *   <li><strong>Delay Information:</strong> Current delay status with minute display or "--" for no delays for operational awareness</li>
 *   <li><strong>Flight Status:</strong> Italian-localized status information for all flight operational states and administrative oversight</li>
 *   <li><strong>Capacity Information:</strong> Free/total seat display for operational planning and resource management</li>
 * </ul>
 * <p>
 * Management integration provides sophisticated administrative workflow coordination through mouse event
 * handling that enables direct access to comprehensive flight management operations. The integration
 * includes seamless transitions to flight administration interfaces while maintaining administrative
 * session context and operational information throughout comprehensive management workflows.
 * </p>
 * <p>
 * Professional table presentation includes alternating row colors (248,248,248 and white) for
 * enhanced administrative readability, center-aligned content, and consistent typography throughout flight
 * information display. The presentation optimizes administrative scanning and information comprehension
 * during flight management and operational oversight processes.
 * </p>
 * <p>
 * Interactive management functionality includes universal button rendering that ensures management
 * buttons are enabled for all flights regardless of availability or operational status. The
 * functionality provides comprehensive administrative access while maintaining clear visual feedback
 * about management capabilities throughout administrative interaction workflows.
 * </p>
 * <p>
 * Empty state management provides professional no-results messaging through the specialized
 * {@link JTableWithEmptyMessage} component that displays centered Italian messages when search
 * criteria return no matching flights. The messaging maintains professional administrative appearance
 * and provides clear administrative guidance during unsuccessful search scenarios.
 * </p>
 * <p>
 * Status localization includes comprehensive Italian translation for all flight operational
 * states including PROGRAMMED (In programma), CANCELLED (Cancellato), DELAYED (In ritardo),
 * ABOUT_TO_DEPART (In partenza), DEPARTED (Partito), ABOUT_TO_ARRIVE (In arrivo), and
 * LANDED (Atterrato) for optimal administrative understanding and operational clarity.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader airport management system through comprehensive
 * {@link Controller} integration, enabling access to flight information services, administrative
 * management capabilities, and operational workflow coordination. The integration supports administrative
 * flight management workflows while maintaining proper separation of concerns and interface consistency.
 * </p>
 * <p>
 * Data management utilizes extensive ArrayList collections for flight information including
 * flight IDs, company names, dates, times, delays, status, seat capacity, and destination
 * cities. The data management ensures efficient administrative result presentation and supports
 * dynamic content updates for optimal administrative experience throughout flight management operations.
 * </p>
 * <p>
 * Navigation integration includes sophisticated calling objects hierarchy management that
 * maintains administrative workflow context during management transitions and enables proper resource
 * management throughout administrative interactions with comprehensive flight management capabilities.
 * </p>
 * <p>
 * The SearchFlightResultPanelAdmin serves as a critical component of the administrative flight
 * management ecosystem, providing essential flight information presentation and management initiation
 * capabilities while maintaining interface consistency, administrative experience quality, and
 * operational effectiveness throughout administrative flight oversight and management workflows.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JPanel
 * @see Controller
 * @see AbstractTableModel
 * @see MouseAdapter
 * @see BorderLayout
 * @see BookingPageAdmin
 */
public class SearchFlightResultPanelAdmin extends JPanel {

    /**
     * Primary administrative results table for displaying flight search results with integrated management functionality.
     * <p>
     * This final JTable component serves as the main display interface for administrative flight search results,
     * configured with custom table model, professional styling, and interactive management capabilities.
     * The table includes alternating row colors, center-aligned content, and universal button rendering
     * for optimal administrative experience throughout flight management and operational oversight workflows.
     * </p>
     */
    private final JTable resultsTable;

    /**
     * Custom table model providing flight data management and presentation logic for administrative interfaces.
     * <p>
     * This final FlightTableModel instance manages comprehensive flight information including
     * company names, routes, schedules, delays, status, and capacity data. The model provides
     * Italian-localized content presentation, administrative formatting, and integration with
     * management workflow logic for optimal administrative flight information display and interaction workflows.
     * </p>
     */
    private final FlightTableModel tableModel;

    /**
     * Constructs a new SearchFlightResultPanelAdmin with comprehensive flight results display and integrated management capabilities for administrative workflows.
     * <p>
     * This constructor initializes the complete administrative flight results interface by establishing table
     * configuration, data model setup, event handling integration, and visual styling for optimal
     * administrative experience during flight result review and management operations. The constructor creates
     * a fully functional administrative results panel ready for immediate integration within administrative flight
     * management workflows with support for both populated results and empty state scenarios.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Layout Configuration:</strong> BorderLayout establishment with proper administrative component organization</li>
     *   <li><strong>Visual Styling:</strong> White background configuration for professional administrative appearance and optimal readability</li>
     *   <li><strong>Table Model Setup:</strong> FlightTableModel initialization with controller integration and comprehensive data binding</li>
     *   <li><strong>Empty State Handling:</strong> Conditional table creation based on result availability with specialized administrative messaging</li>
     *   <li><strong>Event Handler Integration:</strong> MouseAdapter setup for interactive management access and administrative navigation</li>
     *   <li><strong>Appearance Configuration:</strong> Professional table styling through setTableApperance method delegation for administrative presentation</li>
     *   <li><strong>Header Management:</strong> Table header configuration and visibility setup for administrative column identification</li>
     *   <li><strong>Component Assembly:</strong> BorderLayout component placement for optimal administrative interface organization</li>
     * </ul>
     * <p>
     * Layout configuration establishes {@link BorderLayout} as the primary layout manager,
     * providing optimal organization for administrative table header and content areas. The layout ensures
     * proper component positioning and supports responsive design principles for different
     * window sizes and administrative usage scenarios throughout flight management result presentation.
     * </p>
     * <p>
     * Visual styling includes white background configuration that provides optimal content
     * readability and professional administrative appearance. The styling integrates seamlessly with the
     * airport management system's visual design standards and ensures consistent administrative branding
     * throughout flight management and operational oversight interfaces.
     * </p>
     * <p>
     * Table model setup creates the FlightTableModel with comprehensive flight data including
     * company names, routes, schedules, delays, status information, and seat capacity.
     * The model provides Italian-localized content presentation and integrates with controller
     * systems for dynamic flight information access throughout administrative result display.
     * </p>
     * <p>
     * Empty state handling includes conditional table creation that provides specialized
     * no-results messaging through JTableWithEmptyMessage when search criteria return no
     * matching flights. The handling ensures professional administrative presentation and clear
     * administrative guidance during unsuccessful search scenarios with appropriate Italian messaging.
     * </p>
     * <p>
     * Event handler integration establishes comprehensive MouseAdapter functionality for
     * interactive management access. The handler includes click detection, flight selection,
     * management workflow initiation, and navigation coordination for seamless administrative management
     * transitions throughout flight administration and operational oversight workflows.
     * </p>
     * <p>
     * Management coordination within the mouse event handler ensures that management operations
     * are accessible for all flights through universal button functionality. The coordination
     * provides comprehensive administrative access while enabling appropriate management workflow routing
     * through getAllForAFlight integration and BookingPageAdmin navigation for complete operational oversight.
     * </p>
     * <p>
     * Navigation coordination includes proper calling objects hierarchy management during
     * management transitions, ensuring that previous administrative interface frames are properly hidden
     * and administrative workflow context is maintained throughout management operations and
     * interface transitions within the comprehensive airport management system.
     * </p>
     * <p>
     * Appearance configuration delegates to the setTableApperance method for comprehensive
     * administrative table styling including typography, colors, alignment, and renderer configuration.
     * The appearance setup ensures professional administrative presentation and optimal readability
     * throughout administrative flight result display and management interaction operations.
     * </p>
     * <p>
     * Header management includes proper JTableHeader configuration and visibility setup
     * based on the ifSearched parameter, ensuring that administrative column headers are only displayed
     * when actual search results are present. The management supports clean administrative interface
     * presentation and appropriate administrative guidance throughout flight management workflows.
     * </p>
     * <p>
     * Component assembly utilizes BorderLayout positioning to place the table header in
     * the NORTH region and the results table in the CENTER region. The assembly ensures
     * proper administrative component hierarchy and supports responsive layout behavior throughout
     * different administrative interface scenarios and management usage patterns.
     * </p>
     * <p>
     * The constructor completes by establishing a fully functional administrative flight results
     * display ready for immediate administrative interaction, providing comprehensive flight
     * information presentation, management capabilities, and professional visual design
     * throughout administrative flight oversight and operational management workflows.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management and administrative workflow coordination
     * @param controller the system controller providing access to flight information services and comprehensive administrative management functionality
     * @param ids the list of unique flight identifiers for result correlation and detailed administrative flight access
     * @param companyNames the list of airline company names for carrier identification and administrative display
     * @param dates the list of flight dates for scheduling information and administrative temporal organization
     * @param departureTimes the list of flight departure times for schedule display and administrative planning coordination
     * @param arrivalTimes the list of flight arrival times for complete schedule information and administrative travel coordination
     * @param delays the list of flight delay information in minutes for current status presentation and administrative awareness
     * @param status the list of flight operational status information for administrative awareness and management validation
     * @param maxSeats the list of maximum seat capacity for each flight for administrative availability context and resource planning
     * @param freeSeats the list of available seat counts for administrative decision support and capacity management
     * @param cities the list of destination cities for route information display and administrative travel planning coordination
     * @param ifSearched the boolean flag indicating whether a search has been performed for proper administrative header visibility and interface state management
     */
    public SearchFlightResultPanelAdmin(List<DisposableObject> callingObjects, Controller controller, List<String> ids, List<String> companyNames, List<Date> dates, List<Time> departureTimes, List<Time> arrivalTimes,
                                   List<Integer> delays, List<String> status, List<Integer> maxSeats, List<Integer> freeSeats, List<String> cities, boolean ifSearched) {

        super(new BorderLayout());
        this.setBackground(Color.WHITE);

        //se searchedFlights fosse null, darebbe nullPointerException, quindi gli passo una lista vuota
        tableModel = new FlightTableModel( controller, ids, companyNames, dates, departureTimes, arrivalTimes,
                delays, status, maxSeats, freeSeats, cities);

        boolean hasResults = (ids != null && !ids.isEmpty());

        if (ifSearched && !hasResults)
            resultsTable = new JTableWithEmptyMessage(tableModel, "Nessun risultato per i parametri di ricerca impostati");
        else
            resultsTable = new JTable(tableModel);

        resultsTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent mouseEvent) {

                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                int col = table.columnAtPoint(point);
                if (table.getSelectedRow() != -1 && row != -1 && col == tableModel.getColumnCount() - 1) {


                    int index = table.rowAtPoint(point);   //index of the selectedFlight

                    controller.getAllForAFlight(index);

                    new BookingPageAdmin(callingObjects, controller, callingObjects.getLast().getFrame().getSize(),
                            callingObjects.getLast().getFrame().getLocation(), callingObjects.getLast().getFrame().getExtendedState());

                    callingObjects.get(callingObjects.size() - 2).getFrame().setVisible(false);


                }
            }
        });

        setTableApperance();

        JTableHeader header = resultsTable.getTableHeader();

        //Per mostrare l'intestazione solo dopo avere effettivamente premuto "Cerca" almeno una volta
        header.setVisible(ifSearched);

        this.add(header, BorderLayout.NORTH);
        this.add(resultsTable, BorderLayout.CENTER);

    }

    /**
     * Configures comprehensive administrative table appearance with professional styling and optimal administrative readability for flight results display.
     * <p>
     * This private method establishes complete administrative table visual configuration including typography,
     * colors, alignment, and renderer setup to ensure professional administrative appearance and optimal
     * readability throughout administrative flight result display operations. The method implements
     * consistent administrative styling standards that align with the airport management system's visual design
     * principles and enhance administrative experience during flight management and operational oversight workflows.
     * </p>
     * <p>
     * The administrative table appearance configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Header Styling:</strong> Bold typography, background colors, and reordering prevention for professional administrative presentation</li>
     *   <li><strong>Content Typography:</strong> Segoe UI font configuration with appropriate sizing for optimal administrative readability</li>
     *   <li><strong>Row Configuration:</strong> Height optimization and selection behavior setup for enhanced administrative user interaction</li>
     *   <li><strong>Visual Enhancement:</strong> Grid colors, viewport filling, and professional administrative color scheme application</li>
     *   <li><strong>Cell Rendering:</strong> Custom renderer setup with alternating row colors for improved administrative content scanning</li>
     *   <li><strong>Button Integration:</strong> Specialized button renderer configuration for management column functionality and administrative access</li>
     * </ul>
     * <p>
     * Header styling includes bold Segoe UI font (14pt) for clear administrative column identification and
     * light gray background (230, 230, 230) that provides subtle visual separation while
     * maintaining professional administrative appearance. Column reordering is disabled to maintain
     * consistent administrative layout and prevent administrative interface confusion during flight result browsing.
     * </p>
     * <p>
     * Content typography utilizes plain Segoe UI font (14pt) for optimal administrative readability and
     * consistent visual presentation across all administrative table content. The typography ensures
     * clear administrative information display while maintaining professional appearance standards
     * throughout the airport management system administrative interface.
     * </p>
     * <p>
     * Row configuration includes 35-pixel height optimization that provides comfortable
     * vertical spacing for administrative content readability while maintaining efficient space utilization.
     * The row selection is disabled (setRowSelectionAllowed(false)) to focus administrative
     * interaction on the management button column for precise administrative control and workflow initiation.
     * </p>
     * <p>
     * Visual enhancement includes viewport filling (setFillsViewportHeight(true)) that
     * ensures optimal administrative space utilization and professional presentation. The grid color
     * configuration (220, 220, 220) provides subtle visual organization that enhances
     * administrative readability without overwhelming the administrative content presentation.
     * </p>
     * <p>
     * Cell rendering utilizes a custom DefaultTableCellRenderer that implements alternating
     * row colors for enhanced administrative content scanning. The renderer applies light gray (248, 248, 248)
     * for even rows and white for odd rows, creating visual rhythm that improves administrative
     * ability to scan flight information and make management decisions throughout operational oversight.
     * </p>
     * <p>
     * Center alignment is applied to all content columns (excluding the management button column)
     * to ensure consistent administrative presentation and optimal readability of flight information including
     * times, dates, status, and capacity data throughout administrative result display and management workflows.
     * </p>
     * <p>
     * Button integration includes specialized ButtonRenderer configuration for the management
     * column that provides universal button rendering for all flights regardless of availability
     * or operational status. The button renderer ensures that management buttons are accessible
     * for comprehensive administrative oversight while maintaining consistent visual presentation throughout
     * the administrative flight results table and management workflow integration.
     * </p>
     * <p>
     * The method ensures comprehensive administrative visual consistency that enhances administrative experience
     * through professional presentation, clear administrative information hierarchy, and optimal readability
     * throughout flight result display and management interaction workflows within the airport
     * management system administrative interface architecture.
     * </p>
     */
    private void setTableApperance() {

        resultsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        resultsTable.getTableHeader().setBackground(new Color(230, 230, 230));
        resultsTable.getTableHeader().setReorderingAllowed(false);

        resultsTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultsTable.setRowHeight(35);
        resultsTable.setRowSelectionAllowed(false);
        resultsTable.setFillsViewportHeight(true);
        resultsTable.setGridColor(new Color(220, 220, 220));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    //alternanza di colori nel risultato della ricerca
                    c.setBackground(row % 2 == 0 ? new Color(248, 248, 248) : Color.WHITE);
                }
                return c;
            }
        };
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < resultsTable.getColumnCount() - 1; i++) {
            resultsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        int bookColumnIndex = tableModel.getColumnCount() - 1;
        resultsTable.getColumnModel().getColumn(bookColumnIndex).setCellRenderer(new ButtonRenderer());

    }

    /**
     * Custom table model providing comprehensive flight data management and Italian-localized presentation for administrative flight results display.
     * <p>
     * This private static class extends {@link AbstractTableModel} to provide sophisticated flight data
     * management and presentation capabilities for administrative flight results. The FlightTableModel includes
     * comprehensive Italian localization for all status fields, professional temporal formatting, and
     * integrated capacity information for optimal administrative flight information display throughout
     * operational oversight and management workflows within the airport management system.
     * </p>
     * <p>
     * The administrative table model functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Data Management:</strong> Comprehensive flight information storage and retrieval with administrative controller integration</li>
     *   <li><strong>Italian Localization:</strong> Complete status translation for all flight operational states and administrative clarity</li>
     *   <li><strong>Temporal Formatting:</strong> Professional time presentation with zero-padding and HH:MM format consistency for operational scheduling</li>
     *   <li><strong>Route Information:</strong> Directional route display with Naples integration and arrow indicators for operational clarity</li>
     *   <li><strong>Capacity Display:</strong> Clear seat capacity presentation with free/total seat formatting for administrative resource management</li>
     *   <li><strong>Status Management:</strong> Comprehensive flight status handling with delay information presentation for operational oversight</li>
     *   <li><strong>Management Integration:</strong> Administrative access methods for comprehensive flight management workflow coordination</li>
     * </ul>
     * <p>
     * Data management utilizes comprehensive ArrayList collections for all flight information
     * including identifiers, company names, schedules, delays, status, and capacity data.
     * The data management ensures efficient administrative information access and supports dynamic content
     * presentation throughout administrative flight result display and management workflows.
     * </p>
     * <p>
     * Italian localization provides complete translation of flight operational states including
     * PROGRAMMED (In programma), CANCELLED (Cancellato), DELAYED (In ritardo), ABOUT_TO_DEPART
     * (In partenza), DEPARTED (Partito), ABOUT_TO_ARRIVE (In arrivo), and LANDED (Atterrato)
     * for optimal administrative understanding and operational clarity throughout management workflows.
     * </p>
     * <p>
     * Temporal formatting includes sophisticated time presentation with automatic zero-padding
     * for hours and minutes less than 10, ensuring consistent HH:MM format throughout all
     * administrative time displays. The formatting supports both departure and arrival time presentation
     * with professional appearance standards for administrative schedule management and operational planning.
     * </p>
     * <p>
     * Route information utilizes intelligent directional display based on flight type detection
     * through controller integration. The presentation shows either "Napoli → Destination" for
     * departing flights or "Origin → Napoli" for arriving flights, providing immediate visual
     * clarity for administrative travel direction understanding and operational coordination.
     * </p>
     * <p>
     * Capacity display presents seat information in clear "free/total" format that enables
     * immediate administrative assessment of capacity utilization and resource allocation opportunities.
     * The display integrates with administrative planning logic to support informed administrative
     * decision-making throughout flight management and operational resource coordination workflows.
     * </p>
     * <p>
     * Status management includes comprehensive handling of flight operational states with
     * delay information presentation that shows delay minutes for delayed flights or "--"
     * for flights without delays. The management provides clear administrative awareness of
     * current flight conditions for operational planning and management decision-making purposes.
     * </p>
     * <p>
     * The model serves as the foundation for administrative flight result presentation, ensuring
     * comprehensive information display, professional administrative formatting, and seamless integration
     * with management workflows throughout the airport management system's administrative interface
     * and operational oversight capabilities.
     * </p>
     */
    private static class FlightTableModel extends AbstractTableModel {

        /**
         * System controller providing access to flight information services and administrative business logic coordination.
         * <p>
         * This final Controller reference enables access to flight management functionality,
         * route type determination, and administrative business logic coordination throughout flight result
         * presentation and administrative management workflows. The controller integration ensures
         * proper administrative data access and maintains consistency with system-wide flight management
         * operations and administrative service coordination throughout operational oversight.
         * </p>
         */
        private final Controller controller;

        /**
         * Collection of unique flight identifiers for administrative result correlation and detailed flight access.
         * <p>
         * This final ArrayList stores flight IDs that enable proper administrative result correlation with
         * detailed flight information and support management workflow integration. The identifiers
         * provide essential linkage between administrative display presentation and underlying flight data
         * management throughout administrative flight management and operational oversight operations.
         * </p>
         */
        private final ArrayList<String> ids;

        /**
         * Collection of airline company names for carrier identification and administrative information display.
         * <p>
         * This final ArrayList stores airline company names that provide essential carrier
         * identification for administrative operational planning and management decisions. The company
         * information supports administrative coordination management and enables informed flight
         * oversight throughout operational planning and administrative management workflows.
         * </p>
         */
        private final ArrayList<String> companyNames;

        /**
         * Collection of flight dates for scheduling information and administrative temporal organization throughout operational planning.
         * <p>
         * This final ArrayList stores flight dates that provide essential temporal information
         * for administrative operational planning and schedule coordination. The date information supports
         * administrative operational planning and enables proper scheduling decisions throughout flight
         * management and operational oversight workflows within the airport management system.
         * </p>
         */
        private final ArrayList<Date> dates;

        /**
         * Collection of flight departure times for schedule display and administrative operational planning coordination.
         * <p>
         * This final ArrayList stores departure time information that enables administrative schedule
         * planning and coordination with operational itineraries. The departure time data supports
         * professional time formatting and provides essential scheduling information for
         * administrative decision-making throughout flight management and operational oversight workflows.
         * </p>
         */
        private final ArrayList<Time> departureTimes;

        /**
         * Collection of flight arrival times for complete schedule information and administrative operational coordination.
         * <p>
         * This final ArrayList stores arrival time information that provides complete flight
         * scheduling details for administrative operational planning and coordination. The arrival time
         * data supports comprehensive schedule presentation and enables administrative planning
         * for operational activities throughout flight management and operational oversight workflows.
         * </p>
         */
        private final ArrayList<Time> arrivalTimes;

        /**
         * Collection of flight delay information in minutes for current status presentation and administrative operational awareness.
         * <p>
         * This final ArrayList stores delay information that provides real-time flight status
         * updates for administrative awareness and operational planning adjustments. The delay data
         * supports dynamic status presentation with appropriate formatting (minutes or "--")
         * throughout administrative flight result display and management decision workflows.
         * </p>
         */
        private final ArrayList<Integer> delays;

        /**
         * Collection of flight operational status information for administrative awareness and management validation throughout operational workflows.
         * <p>
         * This final ArrayList stores current flight operational status including states such as
         * PROGRAMMED, CANCELLED, DELAYED, ABOUT_TO_DEPART, DEPARTED, ABOUT_TO_ARRIVE, and LANDED.
         * The status information supports Italian localization for administrative understanding and
         * integrates with management workflow logic throughout flight oversight and operational management workflows.
         * </p>
         */
        private final ArrayList<String> status;

        /**
         * Collection of maximum seat capacity information for flight availability context and administrative resource planning support.
         * <p>
         * This final ArrayList stores maximum seat capacity for each flight that provides
         * essential context for administrative capacity assessment and operational resource decisions.
         * The capacity information supports availability ratio display and enables administrative
         * understanding of flight demand throughout flight management and operational resource coordination workflows.
         * </p>
         */
        private final ArrayList<Integer> maxSeats;

        /**
         * Collection of available seat counts for administrative capacity management and operational decision support.
         * <p>
         * This final ArrayList stores current available seat counts that provide administrative
         * capacity information for operational decision-making. The availability
         * data supports administrative resource management and enables immediate administrative assessment
         * of capacity utilization opportunities throughout flight management and operational coordination workflows.
         * </p>
         */
        private final ArrayList<Integer> freeSeats;

        /**
         * Collection of destination city names for route information display and administrative operational planning coordination.
         * <p>
         * This final ArrayList stores destination cities that provide essential route
         * information for administrative operational planning and destination coordination. The city
         * information supports directional route display formatting and enables administrative
         * understanding of operational destinations throughout flight management and operational oversight workflows.
         * </p>
         */
        private final ArrayList<String> cities;

        /**
         * Array of Italian column names providing localized administrative table headers for administrative interface accessibility and understanding.
         * <p>
         * This final String array defines the administrative table column headers in Italian language including
         * "Compagnia" (Company), "Tratta" (Route), "Data" (Date), "Partenza" (Departure),
         * "Ritardo" (Delay), "Arrivo" (Arrival), "Stato" (Status), "Posti" (Seats), and
         * "Gestione" (Management) for complete administrative interface localization and operational accessibility.
         * </p>
         */
        private final String[] colNames = {"Compagnia", "Tratta", "Data", "Partenza", "Ritardo", "Arrivo", "Stato", "Posti", "Gestione"};

        /**
         * Constructs a new FlightTableModel with comprehensive flight data integration and Italian localization for administrative result presentation.
         * <p>
         * This constructor initializes the complete administrative flight table model by establishing data binding
         * with provided flight information collections and controller integration for administrative business logic
         * access. The constructor creates a fully functional administrative table model ready for immediate
         * integration within administrative flight result display with support for Italian localization,
         * professional formatting, and management workflow coordination throughout administrative operational planning operations.
         * </p>
         * <p>
         * The initialization process includes:
         * </p>
         * <ul>
         *   <li><strong>Controller Integration:</strong> System controller reference establishment for administrative business logic access</li>
         *   <li><strong>Data Binding:</strong> Comprehensive flight information collection assignment with administrative type casting</li>
         *   <li><strong>Collection Management:</strong> ArrayList references establishment for all administrative flight data categories</li>
         *   <li><strong>Business Logic Access:</strong> Controller integration for flight type determination and administrative route formatting</li>
         * </ul>
         * <p>
         * Controller integration establishes the system controller reference that provides access
         * to flight management functionality, route type determination, and administrative business logic
         * coordination throughout flight result presentation and administrative management workflows.
         * </p>
         * <p>
         * Data binding includes comprehensive assignment of all provided flight information
         * collections with appropriate type casting to ArrayList instances. The binding ensures
         * proper administrative data access and maintains type safety throughout flight information presentation
         * and administrative interaction workflows.
         * </p>
         * <p>
         * Collection management establishes references to all flight information categories
         * including identifiers, company names, schedules, delays, status, capacity, and
         * destination data. The management ensures efficient administrative data access and supports dynamic
         * content presentation throughout administrative flight result display operations.
         * </p>
         * <p>
         * The constructor completes by establishing a fully functional administrative flight table model ready
         * for immediate administrative flight result presentation with comprehensive data access,
         * Italian localization support, and professional formatting capabilities throughout
         * administrative operational planning and management workflows.
         * </p>
         *
         * @param controller the system controller providing access to flight information services and administrative business logic coordination
         * @param parIds the list of unique flight identifiers for administrative result correlation and detailed flight access
         * @param parCompanyNames the list of airline company names for carrier identification and administrative information display
         * @param parDates the list of flight dates for scheduling information and administrative temporal organization
         * @param parDepartureTimes the list of flight departure times for schedule display and administrative operational planning
         * @param parArrivalTimes the list of flight arrival times for complete schedule information and administrative coordination
         * @param parDelays the list of flight delay information in minutes for current status presentation and administrative awareness
         * @param parStatus the list of flight operational status information for administrative awareness and management validation
         * @param parMaxSeats the list of maximum seat capacity for administrative availability context and resource planning support
         * @param parFreeSeats the list of available seat counts for administrative capacity management and operational decision support
         * @param parCities the list of destination cities for route information display and administrative operational planning coordination
         */
        public FlightTableModel( Controller controller, List<String> parIds, List<String> parCompanyNames, List<Date> parDates, List<Time> parDepartureTimes, List<Time> parArrivalTimes,
                                 List<Integer> parDelays, List<String> parStatus, List<Integer> parMaxSeats, List<Integer> parFreeSeats, List<String> parCities) {

            this.controller = controller;
            this.ids = (ArrayList<String>) parIds;
            this.companyNames= (ArrayList<String>) parCompanyNames;
            this.dates = (ArrayList<Date>) parDates;
            this.departureTimes = (ArrayList<Time>) parDepartureTimes;
            this.arrivalTimes = (ArrayList<Time>) parArrivalTimes;
            this.delays = (ArrayList<Integer>) parDelays;
            this.status = (ArrayList<String>) parStatus;
            this.maxSeats = (ArrayList<Integer>) parMaxSeats;
            this.freeSeats = (ArrayList<Integer>) parFreeSeats;
            this.cities = (ArrayList<String>) parCities;

        }

        /**
         * Returns the total number of flight results for administrative table row management and display coordination.
         * <p>
         * This overridden method from AbstractTableModel provides the row count for administrative table display
         * management by returning the size of the flight IDs collection. The row count enables
         * proper administrative table sizing, scrolling behavior, and empty state detection throughout administrative
         * flight result presentation and interface management within the airport management system.
         * </p>
         *
         * @return the total number of flight results for administrative table row management and display coordination
         */
        @Override
        public int getRowCount() {
            return ids.size();
        }

        /**
         * Returns the total number of administrative table columns for display management and header coordination.
         * <p>
         * This overridden method from AbstractTableModel provides the column count for administrative table display
         * management by returning the length of the column names array. The column count enables
         * proper administrative table structure, header display, and column renderer assignment throughout administrative
         * flight result presentation and interface coordination within the airport management system.
         * </p>
         *
         * @return the total number of administrative table columns for display management and header coordination
         */
        @Override
        public int getColumnCount() {
            return colNames.length;
        }

        /**
         * Returns the Italian-localized column name for administrative table header display and administrative interface accessibility.
         * <p>
         * This overridden method from AbstractTableModel provides localized column names for administrative table
         * header display by returning the appropriate Italian column name from the colNames array.
         * The method enables proper administrative header presentation with Italian localization for administrative
         * accessibility and interface understanding throughout flight result display within the airport management system.
         * </p>
         *
         * @param column the column index for administrative header name retrieval and display coordination
         * @return the Italian-localized column name for administrative table header display and administrative interface accessibility
         */
        @Override
        public String getColumnName(int column) {
            return colNames[column];
        }

        /**
         * Returns the formatted cell value for comprehensive administrative flight information display with Italian localization and professional formatting.
         * <p>
         * This overridden method from AbstractTableModel provides comprehensive cell content generation
         * for administrative flight information display including company names, routes, schedules, delays, status,
         * and capacity information. The method includes Italian localization for status information,
         * professional time formatting with zero-padding, and directional route display for optimal
         * administrative understanding throughout flight result presentation and management workflows.
         * </p>
         * <p>
         * The administrative content generation includes:
         * </p>
         * <ul>
         *   <li><strong>Column 0:</strong> Airline company name for carrier identification and administrative information</li>
         *   <li><strong>Column 1:</strong> Directional route display with Naples integration and arrow indicators for operational clarity</li>
         *   <li><strong>Column 2:</strong> Flight date with standard toString formatting for administrative schedule reference</li>
         *   <li><strong>Column 3:</strong> Departure time with professional HH:MM formatting and zero-padding for administrative consistency</li>
         *   <li><strong>Column 4:</strong> Delay information with minute display or "--" for no delays for administrative awareness</li>
         *   <li><strong>Column 5:</strong> Arrival time with professional HH:MM formatting and zero-padding for administrative schedule completion</li>
         *   <li><strong>Column 6:</strong> Italian-localized flight status for administrative understanding and management validation</li>
         *   <li><strong>Column 7:</strong> Seat capacity with free/total format for administrative resource management and capacity planning</li>
         *   <li><strong>Column 8:</strong> Management button text ("Gestisci") for administrative action identification and workflow access</li>
         * </ul>
         * <p>
         * Route display utilizes controller integration to determine flight type and provide
         * appropriate directional formatting. Departing flights show "Napoli → Destination"
         * while arriving flights show "Origin → Napoli" for immediate administrative operational
         * direction understanding and coordination clarity.
         * </p>
         * <p>
         * Time formatting includes sophisticated zero-padding logic for both hours and minutes
         * less than 10, ensuring consistent HH:MM presentation throughout all administrative time displays.
         * The formatting maintains professional administrative appearance and supports administrative schedule
         * planning throughout flight management and operational oversight workflows.
         * </p>
         * <p>
         * Status localization provides comprehensive Italian translation for all flight
         * operational states including PROGRAMMED (In programma), CANCELLED (Cancellato),
         * DELAYED (In ritardo), ABOUT_TO_DEPART (In partenza), DEPARTED (Partito),
         * ABOUT_TO_ARRIVE (In arrivo), and LANDED (Atterrato) for administrative accessibility
         * and operational clarity throughout management workflows.
         * </p>
         * <p>
         * Delay presentation shows delay minutes for delayed flights or "--" for flights
         * without delays, providing clear administrative awareness of current flight conditions
         * for operational planning and administrative decision-making throughout administrative workflows.
         * </p>
         * <p>
         * Capacity display presents seat information in "free/total" format that enables
         * immediate administrative assessment of capacity utilization and resource allocation opportunities.
         * The display supports informed administrative decision-making throughout flight management
         * and operational resource coordination workflows.
         * </p>
         *
         * @param row the row index for flight data retrieval and administrative content generation
         * @param col the column index for specific information type determination and administrative formatting
         * @return the formatted cell value for comprehensive administrative flight information display with Italian localization and professional formatting
         */
        @Override
        public Object getValueAt(int row, int col) {

            int hours;
            int minutes;

            switch (col) {
                case 0:
                    return companyNames.get(row);
                case 1:
                    if (controller.getFlightController().getFlightType(row))
                        return "Napoli → " + cities.get(row);
                    else
                        return cities.get(row) + " → Napoli";

                case 2:
                    return dates.get(row).toString();
                case 3:
                    hours = departureTimes.get(row).getHours();
                    minutes = departureTimes.get(row).getMinutes();

                    if(hours < 10){
                        if(minutes < 10)
                            return  "0" + hours +  ":" + "0" + minutes;
                        else
                            return  "0" + hours +  ":" + minutes;
                    }else{
                        if(minutes < 10)
                            return  hours +  ":" + "0" + minutes;
                        else
                            return  hours +  ":" + minutes;
                    }

                case 4:
                    int delay = delays.get(row);
                    return delay > 0 ? delay + " min" : "--";
                case 5:
                    hours = arrivalTimes.get(row).getHours();
                    minutes = arrivalTimes.get(row).getMinutes();

                    if(hours < 10){
                        if(minutes < 10)
                            return  "0" + hours +  ":" + "0" + minutes;
                        else
                            return  "0" + hours +  ":" + minutes;
                    }else{
                        if(minutes < 10)
                            return  hours +  ":" + "0" + minutes;
                        else
                            return  hours +  ":" + minutes;
                    }
                case 6:
                    switch (status.get(row).toUpperCase()){
                        case "PROGRAMMED":
                            return "In programma";
                        case "CANCELLED":
                            return "Cancellato";
                        case "DELAYED":
                            return "In ritardo";
                        case "ABOUT_TO_DEPART":
                            return "In partenza";
                        case "DEPARTED":
                            return "Partito";
                        case "ABOUT_TO_ARRIVE":
                            return "In arrivo";
                        case "LANDED":
                            return "Atterrato";
                        default:
                            return null;
                    }
                case 7:
                    return freeSeats.get(row) + "/" + maxSeats.get(row);
                case 8:
                    return "Gestisci";
                default:
                    return null;
            }
        }
    }


    /**
     * Custom button renderer providing universal management button display for comprehensive administrative flight management access.
     * <p>
     * This private static class extends {@link JButton} and implements {@link TableCellRenderer} to provide
     * sophisticated management button rendering with universal enablement for all flights regardless of
     * availability or operational status. The ButtonRenderer ensures that management buttons are accessible
     * for comprehensive administrative oversight while maintaining consistent visual presentation
     * throughout administrative flight result display and management workflows.
     * </p>
     * <p>
     * The administrative button renderer functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Universal Enablement:</strong> Button state management ensuring all flights have accessible management functionality</li>
     *   <li><strong>Professional Styling:</strong> Consistent typography and appearance for administrative management column presentation</li>
     *   <li><strong>Selection Integration:</strong> Proper color coordination with administrative table selection states</li>
     *   <li><strong>Management Access:</strong> Universal management button availability for comprehensive administrative oversight</li>
     *   <li><strong>Administrative Consistency:</strong> Standardized button presentation throughout administrative management workflows</li>
     * </ul>
     * <p>
     * Universal enablement ensures that management buttons are active for all flights regardless of
     * availability requirements or operational status. The enablement provides comprehensive administrative
     * access while maintaining clear visual feedback about management capabilities throughout
     * administrative interaction and operational oversight workflows.
     * </p>
     * <p>
     * Professional styling includes bold Segoe UI font (12pt) for clear button text presentation
     * and opaque rendering for consistent visual appearance. The styling maintains professional
     * administrative presentation standards throughout the management column while ensuring optimal readability
     * and administrative interaction clarity throughout operational management workflows.
     * </p>
     * <p>
     * Selection integration provides proper color coordination with administrative table selection states by
     * applying appropriate foreground and background colors based on selection status. The
     * integration ensures consistent visual behavior throughout administrative table interaction while
     * maintaining button functionality and administrative presentation standards.
     * </p>
     * <p>
     * The renderer serves as a critical component of the administrative management workflow, providing visual
     * feedback about management availability and enabling comprehensive access to management functionality
     * throughout administrative flight oversight and operational management operations within the airport management system.
     * </p>
     */
    private static class ButtonRenderer extends JButton implements TableCellRenderer {

        /**
         * Constructs a new ButtonRenderer with professional styling and optimal configuration for administrative management button display.
         * <p>
         * This constructor initializes the button renderer with professional visual styling including
         * opaque rendering and bold typography for optimal administrative management button presentation throughout
         * administrative flight result display. The constructor establishes consistent administrative appearance standards
         * that align with airport management system design principles and enhance administrative experience
         * during flight management and operational oversight workflows.
         * </p>
         * <p>
         * The initialization includes:
         * </p>
         * <ul>
         *   <li><strong>Opaque Rendering:</strong> Ensures proper administrative background color display and visual consistency</li>
         *   <li><strong>Typography Configuration:</strong> Bold Segoe UI font (12pt) for clear administrative button text presentation</li>
         * </ul>
         * <p>
         * Opaque rendering configuration ensures that administrative background colors are properly displayed
         * and maintains visual consistency throughout administrative table rendering operations. The opaque
         * setting supports proper color coordination with administrative table selection states and provides
         * consistent visual presentation throughout administrative management button display.
         * </p>
         * <p>
         * Typography configuration utilizes bold Segoe UI font (12pt) for clear administrative button text
         * presentation and professional appearance. The font configuration maintains consistency
         * with overall administrative table typography while providing appropriate visual weight for management
         * action identification throughout administrative flight result presentation and operational oversight.
         * </p>
         */
        public ButtonRenderer() {
            setOpaque(true);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
        }

        /**
         * Returns the configured button component with universal enablement and professional styling for administrative management column display.
         * <p>
         * This overridden method from TableCellRenderer provides comprehensive button configuration
         * including text assignment, universal enablement for all flights, and color coordination with
         * administrative table selection states. The method ensures that management buttons are accessible
         * for comprehensive administrative oversight while maintaining consistent visual presentation
         * throughout administrative flight result display and management workflows.
         * </p>
         * <p>
         * The administrative button configuration includes:
         * </p>
         * <ul>
         *   <li><strong>Text Assignment:</strong> Button text setting from cell value with administrative null handling</li>
         *   <li><strong>Universal Enablement:</strong> All management buttons enabled for comprehensive administrative access</li>
         *   <li><strong>Color Coordination:</strong> Proper foreground and background color assignment based on administrative selection state</li>
         *   <li><strong>Visual Consistency:</strong> Consistent administrative appearance throughout different interaction states</li>
         * </ul>
         * <p>
         * Text assignment includes proper handling of cell values with null checking to ensure
         * consistent administrative button text presentation. The assignment supports dynamic content updates
         * and maintains professional appearance throughout administrative management button display operations.
         * </p>
         * <p>
         * Universal enablement ensures that all management buttons are enabled (setEnabled(true))
         * regardless of flight status or availability, providing comprehensive administrative access
         * to flight management functionality. The enablement ensures administrative oversight capabilities
         * are available for all operational scenarios throughout flight management workflows.
         * </p>
         * <p>
         * Color coordination includes proper foreground and background color assignment based
         * on administrative table selection state. Selected rows receive table selection colors while
         * unselected rows use standard table colors and default button background for
         * consistent visual presentation throughout administrative interaction and management workflows.
         * </p>
         * <p>
         * The method returns a fully configured administrative button component ready for management column
         * display with appropriate enablement, styling, and color coordination for optimal
         * administrative experience throughout flight management and operational oversight workflows.
         * </p>
         *
         * @param table the JTable component for administrative context and color coordination
         * @param value the cell value for administrative button text assignment and display
         * @param isSelected the selection state for administrative color coordination and visual consistency
         * @param hasFocus the focus state for administrative visual presentation management
         * @param row the row index for flight data access and administrative management coordination
         * @param column the column index for administrative renderer coordination and display management
         * @return the configured button component with universal enablement and professional styling for administrative management column display
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {


            setText((value == null) ? "" : value.toString());

            setEnabled(true);

            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }

            return this;
        }
    }


    /**
     * Specialized table component providing professional empty state messaging for no-results scenarios in administrative flight search displays.
     * <p>
     * This private static class extends {@link JTable} to provide sophisticated empty state handling
     * with centered Italian messaging when flight search operations return no matching results.
     * The JTableWithEmptyMessage ensures professional administrative presentation and clear administrative guidance
     * during unsuccessful search scenarios while maintaining consistent visual design throughout
     * the airport management system's administrative interface and flight search workflows.
     * </p>
     * <p>
     * The specialized administrative table functionality includes:
     * </p>
     * <ul>
     *   <li><strong>Empty State Detection:</strong> Automatic detection of zero-row conditions for administrative message display activation</li>
     *   <li><strong>Professional Messaging:</strong> Centered Italian message display with appropriate typography and administrative styling</li>
     *   <li><strong>Visual Integration:</strong> Consistent appearance with standard administrative table styling and airport system branding</li>
     *   <li><strong>Custom Painting:</strong> Sophisticated graphics rendering for optimal administrative message presentation and readability</li>
     *   <li><strong>Anti-aliasing Support:</strong> Smooth text rendering for professional administrative appearance and optimal readability</li>
     * </ul>
     * <p>
     * Empty state detection automatically identifies when the administrative table contains no rows and activates
     * the custom message display functionality. The detection ensures that empty state messaging
     * is only displayed when appropriate while maintaining standard administrative table functionality for
     * populated result scenarios throughout administrative flight search workflows.
     * </p>
     * <p>
     * Professional messaging provides centered Italian language messages with appropriate typography
     * (italic Segoe UI, 16pt) and gray color for subtle yet clear communication with administrators.
     * The messaging maintains professional administrative appearance standards while providing helpful guidance
     * during no-results scenarios throughout administrative flight search operations.
     * </p>
     * <p>
     * Visual integration ensures consistent appearance with standard JTable styling while adding
     * the specialized empty state functionality. The integration maintains airport management
     * system visual design standards and provides seamless administrative user experience throughout different
     * result scenarios and administrative interaction workflows.
     * </p>
     * <p>
     * The class serves as an essential component of administrative experience management, providing
     * clear communication during unsuccessful search scenarios while maintaining professional
     * presentation standards throughout the airport management system's administrative flight search interface.
     * </p>
     */
    private static class JTableWithEmptyMessage extends JTable {

        /**
         * Italian message text for administrative empty state display and administrative guidance during no-results scenarios.
         * <p>
         * This final String stores the Italian message text that is displayed when the administrative table
         * contains no flight results. The message provides clear administrative guidance and maintains
         * professional communication standards during unsuccessful search scenarios throughout
         * administrative flight search workflows within the airport management system interface.
         * </p>
         */
        private final String emptyMessage;

        /**
         * Constructs a new JTableWithEmptyMessage with specialized empty state messaging for administrative no-results scenarios.
         * <p>
         * This constructor initializes the specialized administrative table component with standard JTable functionality
         * and additional empty state messaging capabilities. The constructor establishes the administrative table
         * model integration and message text storage for professional no-results presentation throughout
         * administrative flight search workflows when search criteria return no matching flights.
         * </p>
         * <p>
         * The initialization includes:
         * </p>
         * <ul>
         *   <li><strong>Table Model Integration:</strong> Standard AbstractTableModel assignment for administrative data management</li>
         *   <li><strong>Message Storage:</strong> Empty state message text storage for administrative display functionality</li>
         * </ul>
         * <p>
         * Table model integration provides standard JTable functionality with the provided
         * AbstractTableModel for administrative data management and display coordination. The integration
         * ensures proper administrative table behavior while adding specialized empty state capabilities
         * for enhanced administrative experience during no-results scenarios.
         * </p>
         * <p>
         * Message storage establishes the Italian message text that will be displayed when
         * the administrative table contains no rows. The storage enables custom painting functionality
         * to provide professional administrative guidance during unsuccessful flight search
         * operations throughout airport management system workflows.
         * </p>
         *
         * @param model the AbstractTableModel providing administrative data management and table functionality
         * @param emptyMessage the Italian message text for administrative empty state display and administrative guidance
         */
        public JTableWithEmptyMessage(AbstractTableModel model, String emptyMessage) {

            super(model);
            this.emptyMessage = emptyMessage;

        }

        /**
         * Renders custom administrative empty state messaging with professional typography and centered alignment for optimal administrative communication.
         * <p>
         * This overridden method from JTable provides sophisticated custom painting functionality
         * that displays centered Italian messages when the administrative table contains no flight results. The
         * method includes standard table painting followed by conditional empty state message
         * rendering with anti-aliasing, professional typography, and precise center alignment
         * for optimal administrative guidance during no-results scenarios throughout flight search workflows.
         * </p>
         * <p>
         * The custom administrative painting process includes:
         * </p>
         * <ul>
         *   <li><strong>Standard Painting:</strong> Parent paintComponent invocation for normal administrative table rendering</li>
         *   <li><strong>Empty State Detection:</strong> Row count verification for administrative message display activation</li>
         *   <li><strong>Graphics Enhancement:</strong> Graphics2D casting and anti-aliasing activation for smooth administrative text rendering</li>
         *   <li><strong>Typography Configuration:</strong> Italic Segoe UI font setup with appropriate sizing and color for administrative presentation</li>
         *   <li><strong>Center Alignment:</strong> Precise message positioning calculations for optimal administrative visual presentation</li>
         *   <li><strong>Message Rendering:</strong> Professional text drawing with proper coordinate calculation for administrative guidance</li>
         * </ul>
         * <p>
         * Standard painting includes calling the parent paintComponent method to ensure proper
         * administrative table rendering for normal scenarios while preparing the graphics context for additional
         * custom painting operations during administrative empty state scenarios.
         * </p>
         * <p>
         * Empty state detection verifies that the administrative table row count is zero before proceeding
         * with empty state message rendering. The detection ensures that custom administrative messaging
         * is only displayed when appropriate while maintaining standard functionality for
         * populated administrative table scenarios throughout administrative flight result presentation.
         * </p>
         * <p>
         * Graphics enhancement includes Graphics2D casting for advanced rendering capabilities
         * and anti-aliasing activation for smooth administrative text presentation. The enhancement ensures
         * professional text rendering quality and optimal readability throughout administrative empty state
         * message display operations within the airport management system interface.
         * </p>
         * <p>
         * Typography configuration establishes italic Segoe UI font (16pt) with gray color
         * for subtle yet clear administrative message presentation. The typography maintains consistency
         * with airport management system design standards while providing appropriate
         * visual weight for administrative guidance messaging.
         * </p>
         * <p>
         * Center alignment includes sophisticated coordinate calculations using FontMetrics
         * to determine precise message positioning for optimal administrative visual presentation. The
         * calculations ensure that administrative empty state messages are properly centered both horizontally
         * and vertically within the available administrative table space.
         * </p>
         * <p>
         * Message rendering utilizes precise coordinate calculation with string width and height
         * determination for optimal positioning. The rendering includes proper baseline adjustment
         * and ensures clear administrative message presentation throughout empty result scenarios and administrative
         * guidance operations within the airport management system interface.
         * </p>
         *
         * @param g the Graphics context for administrative painting operations and custom message rendering
         */
        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            if (getRowCount() == 0) {

                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.GRAY);

                g2d.setFont(new Font("Segoe UI", Font.ITALIC, 16));

                FontMetrics fm = g2d.getFontMetrics();

                int stringWidth = fm.stringWidth(emptyMessage);
                int stringHeight = fm.getAscent();
                int x = (getWidth() - stringWidth) / 2;
                int y = (getHeight() - stringHeight) / 2 + fm.getAscent();

                g2d.drawString(emptyMessage, x, y);
            }
        }
    }


}