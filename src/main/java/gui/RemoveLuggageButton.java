package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Specialized button component for removing individual luggage items from the luggage management interface in the airport management system.
 * <p>
 * This class extends {@link JButton} to provide interactive luggage removal functionality within the
 * {@link LuggagesView} interface. Each RemoveLuggageButton is associated with a specific {@link LuggagePanel}
 * and manages the complete removal process including visual updates, collection management, layout
 * restructuring, and index coordination to maintain interface consistency throughout dynamic luggage operations.
 * </p>
 * <p>
 * The RemoveLuggageButton class provides comprehensive luggage removal functionality including:
 * </p>
 * <ul>
 *   <li><strong>Interactive Removal:</strong> Single-click luggage item removal with immediate visual feedback</li>
 *   <li><strong>Collection Management:</strong> Automatic removal from luggage panels and button collections</li>
 *   <li><strong>Layout Restructuring:</strong> Dynamic layout updates and component repositioning after removal</li>
 *   <li><strong>Index Coordination:</strong> Automatic index adjustment for remaining luggage items</li>
 *   <li><strong>Container Integration:</strong> Seamless integration with scrollable luggage container interfaces</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JButton
 * @see LuggagesView
 * @see LuggagePanel
 * @see Constraints
 * @see GridBagConstraints
 * @see ActionListener
 */
public class RemoveLuggageButton extends JButton {
    
    /**
     * Sequential index identifying the position of this removal button within the luggage collection.
     * <p>
     * This field maintains the current index position of the associated luggage item within
     * the luggage collection, enabling proper identification and coordination during removal
     * operations. The index is automatically updated during layout restructuring to maintain
     * correct sequential ordering after removal operations.
     * </p>
     */
    private int index;
    
    /**
     * Layout constraints utility for precise component positioning during restructuring operations.
     * <p>
     * This final Constraints helper object provides standardized {@link GridBagConstraints}
     * configuration for optimal component layout management during removal and restructuring
     * operations. The constraints ensure consistent positioning and visual organization
     * throughout dynamic luggage management workflows.
     * </p>
     */
    private final Constraints constraints;

    /**
     * Constructs a new RemoveLuggageButton with comprehensive removal functionality and layout management integration.
     * <p>
     * This constructor initializes a complete luggage removal button with Italian localization,
     * proper event handling, and comprehensive collection management capabilities. The constructor
     * establishes all necessary components for immediate luggage removal operations including
     * layout constraint utilities and interactive event handling for seamless integration
     * within luggage management interfaces.
     * </p>
     * <p>
     * Index assignment stores the provided position value for tracking the button's associated
     * luggage item within collections. The index enables proper identification during removal
     * operations and supports automatic adjustment during layout restructuring to maintain
     * correct sequential ordering across remaining items.
     * </p>
     * <p>
     * Collection cleanup removes the targeted components from both luggage panel and removal
     * button collections, ensuring complete elimination from data structures and preventing
     * memory leaks or reference inconsistencies during continued interface operations.
     * </p>
     * <p>
     * Container updates remove components from the parent JPanel container, ensuring that
     * layout managers no longer track or position the removed components. The updates prepare
     * the container for restructuring operations and optimal space utilization.
     * </p>
     * <p>
     * Layout restructuring repositions all remaining luggage items and their associated removal
     * buttons with updated GridBagConstraints positioning. The restructuring includes proper
     * constraint configuration, component re-addition, visibility restoration, and index
     * adjustment to maintain visual consistency and logical ordering.
     * </p>
     *
     * @param luggagePanels the list of LuggagePanel components for collection management and removal coordination
     * @param removeLuggageButtons the list of RemoveLuggageButton components for synchronized removal operations
     * @param luggagesPanel the parent JPanel container for component removal and layout restructuring
     * @param scrollPane the JScrollPane container for viewport updates and scroll management
     * @param i the initial index position within the luggage collection for identification and coordination
     */
    public RemoveLuggageButton(List<LuggagePanel> luggagePanels,
                               List<RemoveLuggageButton> removeLuggageButtons, JPanel luggagesPanel, JScrollPane scrollPane, int i) {
        super("Rimuovi ");
        index = i;
        constraints = new Constraints();

        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //creo variabili temporanee
                LuggagePanel tmpluggagePanel = luggagePanels.get(index);
                RemoveLuggageButton tmpremoveLuggageButton = removeLuggageButtons.get(index);
                tmpluggagePanel.setVisible(false);
                tmpremoveLuggageButton.setVisible(false);

                //rimuovo da liste
                luggagePanels.remove(index);
                removeLuggageButtons.remove(index);

                //rimuovo da pagina
                luggagesPanel.remove(tmpluggagePanel);
                luggagesPanel.remove(tmpremoveLuggageButton);

                //aggiusto la pagina
                for (int i = index; i < luggagePanels.size(); i++) {
                    luggagesPanel.remove(luggagePanels.get(i));
                    constraints.setConstraints(0, i, 1, 1,
                            GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
                    luggagesPanel.add(luggagePanels.get(i), constraints.getGridBagConstraints());
                    luggagePanels.get(i).setVisible(true);

                    luggagesPanel.remove(removeLuggageButtons.get(i));
                    constraints.setConstraints(1, i, 1, 1,
                            GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
                    luggagesPanel.add(removeLuggageButtons.get(i), constraints.getGridBagConstraints());
                    removeLuggageButtons.get(i).setVisible(true);

                    removeLuggageButtons.get(i).index--;
                }

                //aggiusto view
                scrollPane.setViewportView(luggagesPanel);
            }
        });
    }

    /**
     * Provides access to the current index position of this removal button within the luggage collection.
     * <p>
     * This method returns the current sequential index that identifies the position of this
     * removal button's associated luggage item within the luggage collection. The index supports
     * proper identification during removal operations and is automatically maintained during
     * layout restructuring to ensure correct sequential ordering and collection management.
     * </p>
     *
     * @return the current sequential index position of this removal button within the luggage collection
     */
    public int getIndex() {
        return index;
    }
}