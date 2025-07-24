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
     * Index assignment stores the provided position value for tracking the button's associated
     * passenger within collections and interface layouts. The index enables proper identification
     * during removal operations and supports automatic adjustment during layout restructuring to
     * maintain correct sequential ordering across remaining passengers throughout booking modifications.
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