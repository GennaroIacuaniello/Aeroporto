package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

/**
 * Specialized button component for removing individual passengers from booking modification interfaces in the airport management system.
 * <p>
 * This class extends {@link JButton} to provide interactive passenger removal functionality within booking
 * modification workflows, particularly in the {@link BookingModifyPage} interface. Each RemovePassengerButton
 * is associated with a specific {@link PassengerPanel} and manages the complete removal process including
 * visual updates, collection management, layout restructuring, page navigation coordination, and resource cleanup
 * to maintain interface consistency throughout dynamic passenger management operations.
 * </p>
 * <p>
 * The RemovePassengerButton class provides comprehensive passenger removal functionality including:
 * </p>
 * <ul>
 *   <li><strong>Interactive Removal:</strong> Single-click passenger removal with immediate visual feedback and confirmation</li>
 *   <li><strong>Collection Management:</strong> Automatic removal from passenger panels and remove button collections with synchronized updates</li>
 *   <li><strong>Layout Restructuring:</strong> Dynamic layout updates and component repositioning after removal operations</li>
 *   <li><strong>Index Coordination:</strong> Automatic index adjustment for remaining passengers to maintain sequential ordering</li>
 *   <li><strong>Page Navigation:</strong> Intelligent page visibility management and navigation coordination during removal operations</li>
 *   <li><strong>Resource Cleanup:</strong> Comprehensive disposal of associated components including seat choosers and luggage views</li>
 * </ul>
 * <p>
 * The button is designed with booking workflow optimization, providing users with:
 * </p>
 * <ul>
 *   <li><strong>Intuitive Interaction:</strong> Clear "Remove Passenger" label for immediate action recognition and user guidance</li>
 *   <li><strong>Immediate Response:</strong> Real-time interface updates during passenger removal operations with visual feedback</li>
 *   <li><strong>Automatic Cleanup:</strong> Complete removal of associated components and data structures without manual intervention</li>
 *   <li><strong>Layout Preservation:</strong> Maintains organized interface layout after passenger removal with proper repositioning</li>
 *   <li><strong>Navigation Consistency:</strong> Ensures proper page navigation and visibility management throughout removal processes</li>
 * </ul>
 * <p>
 * Removal architecture utilizes a comprehensive multi-phase process that ensures complete cleanup
 * and interface consistency. The removal operation includes visibility management for immediate user
 * feedback, collection cleanup with synchronized state management, component removal from layout containers,
 * comprehensive resource disposal for associated components, layout restructuring for remaining passengers,
 * and intelligent page navigation coordination for optimal user experience.
 * </p>
 * <p>
 * Collection management handles removal from both passenger panel collections and remove button
 * collections while maintaining synchronized state across all data structures. The management ensures
 * that removed passengers are completely eliminated from all tracking mechanisms and user interface
 * components to prevent memory leaks, reference inconsistencies, and interface artifacts during
 * continued booking modification operations.
 * </p>
 * <p>
 * Layout restructuring provides intelligent repositioning of remaining passengers after removal
 * operations using a sophisticated coordinate system. The restructuring process includes removing
 * existing components from the layout, recalculating optimal positioning using {@link GridBagConstraints}
 * with modular arithmetic for page-based organization, and re-adding components with updated layout
 * parameters to maintain visual organization and consistent user experience.
 * </p>
 * <p>
 * Index coordination ensures that all remaining passengers maintain correct sequential indexing
 * after removal operations. The coordination process automatically decrements indices for passengers
 * positioned after the removed passenger, preserving logical ordering and supporting continued
 * dynamic operations on the remaining passenger collection while maintaining data integrity.
 * </p>
 * <p>
 * Page navigation management includes sophisticated visibility coordination that handles page-based
 * passenger display with intelligent transition logic. The navigation system calculates optimal
 * passenger visibility based on current page context, manages automatic page transitions when
 * necessary, and ensures that users maintain visual context throughout removal operations
 * with consistent interface behavior.
 * </p>
 * <p>
 * Resource cleanup includes comprehensive disposal of associated passenger components such as
 * {@link gui.SeatChooser} dialogs and {@link gui.LuggagesView} windows to prevent resource leaks
 * and ensure optimal system performance. The cleanup process handles null reference checking
 * and provides safe disposal operations for all passenger-related sub-components.
 * </p>
 * <p>
 * Button state management includes intelligent enabling and disabling logic that prevents users
 * from removing the last passenger from a booking while maintaining appropriate removal capabilities
 * for multi-passenger scenarios. The state management integrates with navigation controls to
 * provide consistent interface behavior throughout passenger removal workflows.
 * </p>
 * <p>
 * Event handling architecture utilizes {@link ActionListener} implementation to provide responsive
 * removal operations with comprehensive error prevention and user feedback mechanisms. The event
 * handling includes multi-phase removal processing, resource management coordination, and interface
 * state synchronization to ensure reliable passenger removal functionality across different
 * operational contexts and booking modification scenarios.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader booking modification ecosystem including
 * {@link BookingModifyPage} containers, {@link PassengerPanel} components, {@link BookingPage}
 * navigation systems, and {@link Constraints} layout utilities while maintaining clean separation
 * of concerns and reusable component design patterns throughout the airport management system.
 * </p>
 * <p>
 * Layout management utilizes {@link GridBagConstraints} for precise component positioning during
 * restructuring operations with a two-column layout system. The layout management includes proper
 * constraint configuration with modular arithmetic for page-based organization, alignment specification
 * for optimal visual presentation, and positioning coordination that maintains visual consistency
 * and optimal space utilization throughout removal operations.
 * </p>
 * <p>
 * Pagination integration provides essential coordination with page-based passenger display systems,
 * supporting dynamic page transitions and visibility management during removal operations. The
 * pagination system ensures that users maintain appropriate visual context while providing
 * efficient navigation capabilities throughout multi-passenger booking modification workflows.
 * </p>
 * <p>
 * Resource management follows standard Swing component lifecycle patterns with enhanced cleanup
 * procedures for passenger-specific components. The resource management ensures optimal system
 * performance and prevents resource leaks during extended booking modification sessions with
 * frequent passenger addition and removal operations while maintaining interface responsiveness.
 * </p>
 * <p>
 * The button serves as a critical component of the dynamic passenger management system within
 * booking modification workflows, providing essential removal functionality that enables flexible
 * passenger configuration while maintaining interface consistency, user experience quality, and
 * operational integrity throughout airport management booking modification operations.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JButton
 * @see BookingModifyPage
 * @see BookingPage
 * @see PassengerPanel
 * @see Constraints
 * @see GridBagConstraints
 * @see ActionListener
 */
public class RemovePassengerButton extends JButton {

    /**
     * Sequential index identifying the position of this removal button within the passenger collection.
     * <p>
     * This field maintains the current index position of the associated passenger within
     * the passenger collection, enabling proper identification and coordination during removal
     * operations. The index is automatically updated during layout restructuring to maintain
     * correct sequential ordering after removal operations and supports continued passenger
     * management throughout booking modification workflows.
     * </p>
     */
    int index;
    
    /**
     * Layout constraints utility for precise component positioning during restructuring operations.
     * <p>
     * This Constraints helper object provides standardized {@link GridBagConstraints}
     * configuration for optimal component layout management during removal and restructuring
     * operations. The constraints ensure consistent positioning with modular arithmetic for
     * page-based organization, proper alignment specification, and visual organization
     * throughout dynamic passenger management workflows.
     * </p>
     */
    Constraints constraints;

    /**
     * Constructs a new RemovePassengerButton with comprehensive removal functionality and booking integration.
     * <p>
     * This constructor initializes a complete passenger removal button with English localization,
     * proper event handling, and comprehensive collection management capabilities. The constructor
     * establishes all necessary components for immediate passenger removal operations including
     * layout constraint utilities, interactive event handling, and booking page integration for
     * seamless passenger management within booking modification workflows.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Button Configuration:</strong> "Remove Passenger" label setup with English localization for user recognition</li>
     *   <li><strong>Index Assignment:</strong> Position tracking within passenger collection for proper identification and coordination</li>
     *   <li><strong>Focus Management:</strong> Non-focusable configuration for clean interface behavior and consistent interaction patterns</li>
     *   <li><strong>Layout Preparation:</strong> Constraints utility initialization for restructuring operations and component positioning</li>
     *   <li><strong>Event Handler Setup:</strong> Comprehensive ActionListener implementation for complete removal workflow management</li>
     *   <li><strong>State Management:</strong> Initial button state configuration for immediate operational readiness</li>
     * </ul>
     * <p>
     * Button configuration establishes the removal button with clear English "Remove Passenger"
     * label text that provides immediate action recognition for users. The configuration follows
     * standard Swing JButton patterns while preparing for specialized passenger removal functionality
     * and integration with booking modification interface layouts and user interaction workflows.
     * </p>
     * <p>
     * Index assignment stores the provided position value for tracking the button's associated
     * passenger within collections and interface layouts. The index enables proper identification
     * during removal operations and supports automatic adjustment during layout restructuring to
     * maintain correct sequential ordering across remaining passengers throughout booking modifications.
     * </p>
     * <p>
     * Focus management configures the button as non-focusable to maintain clean interface behavior
     * and prevent focus-related interface artifacts during passenger removal operations. The focus
     * configuration ensures consistent user interaction patterns and maintains visual design
     * integrity throughout booking modification workflows.
     * </p>
     * <p>
     * Layout preparation creates the Constraints utility object that standardizes GridBagConstraints
     * configuration throughout removal and restructuring operations. The preparation ensures
     * consistent component positioning with modular arithmetic for page-based organization, proper
     * alignment coordination, and visual organization during dynamic interface updates and
     * passenger management operations.
     * </p>
     * <p>
     * Event handler setup establishes comprehensive ActionListener implementation that manages
     * the complete passenger removal workflow including:
     * </p>
     * <ul>
     *   <li><strong>Immediate Visibility Management:</strong> Instantaneous hiding of targeted passenger panel and removal button</li>
     *   <li><strong>Collection Synchronization:</strong> Coordinated removal from passenger panels and removal button collections</li>
     *   <li><strong>Layout Container Updates:</strong> Removal from booking page layout containers and component hierarchies</li>
     *   <li><strong>Resource Disposal:</strong> Comprehensive cleanup of associated components including seat choosers and luggage views</li>
     *   <li><strong>Layout Restructuring:</strong> Repositioning and index adjustment for remaining passengers with coordinate recalculation</li>
     *   <li><strong>Page Navigation Coordination:</strong> Intelligent page visibility management and navigation state updates</li>
     *   <li><strong>Interface State Management:</strong> Button state coordination and navigation control updates for consistent behavior</li>
     * </ul>
     * <p>
     * Immediate visibility management provides instantaneous user feedback by hiding both the
     * targeted passenger panel and its associated removal button before beginning collection
     * and container cleanup operations. The management ensures smooth visual transitions and
     * immediate user confirmation of removal initiation throughout passenger removal processes.
     * </p>
     * <p>
     * Collection synchronization removes the targeted components from both passenger panel and
     * removal button collections while maintaining synchronized state across all data structures.
     * The synchronization ensures complete elimination from tracking mechanisms and prevents
     * memory leaks, reference inconsistencies, and interface artifacts during continued booking
     * modification operations.
     * </p>
     * <p>
     * Layout container updates remove components from the booking page's passenger layout container,
     * ensuring that layout managers no longer track or position the removed components. The updates
     * prepare the container for restructuring operations, optimal space utilization, and continued
     * passenger management throughout booking modification workflows.
     * </p>
     * <p>
     * Resource disposal includes comprehensive cleanup of passenger-specific sub-components such
     * as seat chooser dialogs and luggage view windows with proper null reference checking. The
     * disposal prevents resource leaks and ensures optimal system performance during extended
     * booking modification sessions with frequent passenger management operations.
     * </p>
     * <p>
     * Layout restructuring repositions all remaining passengers and their associated removal
     * buttons with updated GridBagConstraints positioning using modular arithmetic for page-based
     * organization. The restructuring includes proper constraint configuration, component re-addition
     * with updated coordinates, and index adjustment to maintain visual consistency, logical
     * ordering, and optimal interface organization.
     * </p>
     * <p>
     * Page navigation coordination manages intelligent page visibility and navigation state updates
     * based on current passenger count and page context. The coordination includes automatic page
     * transitions when necessary, visibility management for optimal user experience, and navigation
     * control state updates to maintain consistent interface behavior throughout removal operations.
     * </p>
     * <p>
     * Interface state management includes navigation button state coordination and removal button
     * state management to prevent invalid removal operations. The state management ensures that
     * next page buttons are properly enabled or disabled based on current page context and that
     * the first removal button is disabled when only one passenger remains to prevent invalid
     * booking configurations.
     * </p>
     * <p>
     * State management includes initial button state configuration that enables the removal button
     * for immediate operational readiness. The state management integrates with booking modification
     * workflow requirements and provides appropriate button availability based on passenger count
     * and removal constraints throughout booking modification operations.
     * </p>
     * <p>
     * Collection integration maintains references to all necessary data structures and container
     * components required for comprehensive removal operations. The integration enables coordinated
     * updates across multiple interface components, ensures consistent state management throughout
     * passenger removal workflows, and provides seamless coordination with booking page navigation
     * and layout management systems.
     * </p>
     *
     * @param book the BookingPage instance providing access to layout containers and navigation controls for removal coordination
     * @param passengerPanels the list of PassengerPanel components for collection management and removal coordination
     * @param removePassengerButtons the list of RemovePassengerButton components for synchronized removal operations and state management
     * @param idx the initial index position within the passenger collection for identification and coordination throughout removal operations
     */
    public RemovePassengerButton(BookingPage book, List<PassengerPanel> passengerPanels,
                                 ArrayList<RemovePassengerButton> removePassengerButtons, int idx) {

        super("Remove Passenger");

        index = idx;

        this.setFocusable(false);

        constraints = new Constraints();

        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                //crea variabili temporanee
                PassengerPanel tmppassengerPanel = passengerPanels.get(index);
                RemovePassengerButton tmpremovePassengerButton = removePassengerButtons.get(index);
                tmppassengerPanel.setVisible(false);
                tmpremovePassengerButton.setVisible(false);

                //rimuovi dalla lista di passeggeri e bottoni
                passengerPanels.remove(index);
                removePassengerButtons.remove(index);

                //rimuovi dalla pagina
                book.getPassengerPage().remove(tmppassengerPanel);
                book.getPassengerPage().remove(tmpremovePassengerButton);

                //rimuovi eventuali panel aggiuntivi
                if (tmppassengerPanel.getLuggagesView() != null) tmppassengerPanel.getLuggagesView().dispose();
                if (tmppassengerPanel.getSeatChooser() != null) tmppassengerPanel.getSeatChooser().dispose();

                for (int i = index; i < passengerPanels.size(); i++) {

                    //shift passengerPanel
                    book.getPassengerPage().remove(passengerPanels.get(i));
                    constraints.setConstraints(0, i % 3, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
                    book.getPassengerPage().add(passengerPanels.get(i), constraints.getGridBagConstraints());

                    //shift removePassengerButton
                    book.getPassengerPage().remove(removePassengerButtons.get(i));
                    constraints.setConstraints(1, i % 3, 1, 1, GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
                    book.getPassengerPage().add(removePassengerButtons.get(i), constraints.getGridBagConstraints());

                    //shift indici
                    removePassengerButtons.get(i).index--;
                }

                //visibilità su pagina
                //calcolo quanti ne ho dopo
                int nNextPassengers = passengerPanels.size() - index;

                //calcolo quanti ne devo spostare
                int nToShiftPassengers = 3 - (index % 3);

                //li metto visibili
                for (int i = 0; i < Math.min(nNextPassengers, nToShiftPassengers); i++) {

                    passengerPanels.get(index + i).setVisible(true);
                    removePassengerButtons.get(index + i).setVisible(true);
                }

                //caso devo tornare a paginaa precedente
                if (nNextPassengers == 0 && index % 3 == 0) {

                    for (int i = 3; i > 0; i--) {

                        passengerPanels.get(index - i).setVisible(true);
                        removePassengerButtons.get(index - i).setVisible(true);
                    }

                    book.decreaseCurrPage();
                }

                //attivabilità nextPageButton
                if (book.getCurrPage() == ((passengerPanels.size() - 1) / 3)) {  //ora sono all'ultima pagina
                    book.getNextButton().setEnabled(false);
                }

                //attivabilità primo bottone
                if (removePassengerButtons.size() == 1) {
                    removePassengerButtons.getFirst().setEnabled(false);
                }
            }
        });

        this.setEnabled(true);
    }
}