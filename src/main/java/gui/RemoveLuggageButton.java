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
 *   <li><strong>Visual Consistency:</strong> Maintains proper visual organization throughout removal operations</li>
 *   <li><strong>Container Integration:</strong> Seamless integration with scrollable luggage container interfaces</li>
 * </ul>
 * <p>
 * The button is designed with operational workflow optimization, providing users with:
 * </p>
 * <ul>
 *   <li><strong>Intuitive Interaction:</strong> Clear "Rimuovi" (Remove) label for immediate action recognition</li>
 *   <li><strong>Immediate Response:</strong> Real-time interface updates during luggage removal operations</li>
 *   <li><strong>Automatic Cleanup:</strong> Complete removal of associated components and data structures</li>
 *   <li><strong>Layout Preservation:</strong> Maintains organized interface layout after item removal</li>
 *   <li><strong>Index Integrity:</strong> Ensures consistent indexing across remaining luggage items</li>
 * </ul>
 * <p>
 * Removal architecture utilizes a comprehensive multi-phase process that ensures complete cleanup
 * and interface consistency. The removal operation includes visibility management, collection cleanup,
 * component removal from containers, layout restructuring for remaining items, and scroll container
 * viewport updates for optimal user experience.
 * </p>
 * <p>
 * Collection management handles removal from both luggage panel collections and removal button
 * collections, maintaining synchronized state across all data structures. The management ensures
 * that removed items are completely eliminated from all tracking mechanisms and user interface
 * components to prevent memory leaks and interface inconsistencies.
 * </p>
 * <p>
 * Layout restructuring provides intelligent repositioning of remaining luggage items after removal
 * operations. The restructuring process includes removing existing components from the layout,
 * recalculating optimal positioning using {@link GridBagConstraints}, and re-adding components
 * with updated layout parameters to maintain visual organization.
 * </p>
 * <p>
 * Index coordination ensures that all remaining luggage items maintain correct sequential indexing
 * after removal operations. The coordination process automatically decrements indices for items
 * positioned after the removed item, preserving logical ordering and supporting continued
 * dynamic operations on the remaining luggage collection.
 * </p>
 * <p>
 * Visual consistency management includes proper visibility controls, component removal animations,
 * and layout updates that maintain professional appearance throughout removal operations. The
 * consistency management ensures that users experience smooth, predictable interface behavior
 * during luggage management workflows.
 * </p>
 * <p>
 * Container integration provides seamless coordination with parent {@link JScrollPane} containers,
 * ensuring that viewport updates reflect current luggage collection state. The integration includes
 * automatic scroll position optimization and content area updates for optimal user experience
 * during dynamic luggage management operations.
 * </p>
 * <p>
 * Event handling architecture utilizes {@link ActionListener} implementation to provide responsive
 * removal operations. The event handling includes comprehensive state management, error prevention,
 * and user feedback mechanisms that ensure reliable luggage removal functionality across
 * different operational contexts and usage scenarios.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader luggage management ecosystem including
 * {@link LuggagesView} containers, {@link LuggagePanel} components, and {@link Constraints}
 * layout utilities while maintaining clean separation of concerns and reusable component design
 * patterns throughout the airport management system.
 * </p>
 * <p>
 * Layout management utilizes {@link GridBagConstraints} for precise component positioning during
 * restructuring operations. The layout management includes proper constraint configuration,
 * alignment specification, and positioning coordination that maintains visual consistency
 * and optimal space utilization throughout removal operations.
 * </p>
 * <p>
 * Index management provides essential tracking capabilities for maintaining correct sequential
 * ordering during dynamic luggage operations. The index system supports both removal operations
 * and subsequent luggage management activities while ensuring consistent identification and
 * positioning across the luggage collection interface.
 * </p>
 * <p>
 * Resource management follows standard Swing component lifecycle patterns with proper component
 * cleanup, memory management, and container coordination during removal operations. The resource
 * management ensures optimal system performance and prevents resource leaks during extended
 * luggage management sessions with frequent addition and removal operations.
 * </p>
 * <p>
 * The button serves as a critical component of the dynamic luggage management system, providing
 * essential removal functionality that enables flexible luggage configuration while maintaining
 * interface consistency and user experience quality throughout airport management operations.
 * </p>
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
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Button Configuration:</strong> "Rimuovi" label setup with Italian localization for user recognition</li>
     *   <li><strong>Index Assignment:</strong> Position tracking within luggage collection for proper identification</li>
     *   <li><strong>Layout Preparation:</strong> Constraints utility initialization for restructuring operations</li>
     *   <li><strong>Event Handler Setup:</strong> Comprehensive ActionListener implementation for removal operations</li>
     *   <li><strong>Collection Integration:</strong> References to all necessary collections and containers for management</li>
     * </ul>
     * <p>
     * Button configuration establishes the removal button with clear Italian "Rimuovi" (Remove)
     * label text that provides immediate action recognition for users. The configuration follows
     * standard Swing JButton patterns while preparing for specialized luggage removal functionality
     * and integration with parent container layouts.
     * </p>
     * <p>
     * Index assignment stores the provided position value for tracking the button's associated
     * luggage item within collections. The index enables proper identification during removal
     * operations and supports automatic adjustment during layout restructuring to maintain
     * correct sequential ordering across remaining items.
     * </p>
     * <p>
     * Layout preparation creates the Constraints utility object that standardizes GridBagConstraints
     * configuration throughout removal and restructuring operations. The preparation ensures
     * consistent component positioning and visual organization during dynamic interface updates
     * and layout modifications.
     * </p>
     * <p>
     * Event handler setup establishes comprehensive ActionListener implementation that manages
     * the complete luggage removal workflow including:
     * </p>
     * <ul>
     *   <li><strong>Visibility Management:</strong> Immediate hiding of targeted luggage panel and removal button</li>
     *   <li><strong>Collection Cleanup:</strong> Removal from luggage panels and removal button collections</li>
     *   <li><strong>Container Updates:</strong> Removal from parent container components and layout managers</li>
     *   <li><strong>Layout Restructuring:</strong> Repositioning and index adjustment for remaining items</li>
     *   <li><strong>Viewport Updates:</strong> Scroll container refresh for optimal user interface presentation</li>
     * </ul>
     * <p>
     * Visibility management provides immediate user feedback by hiding both the targeted luggage
     * panel and its associated removal button before beginning collection and container cleanup
     * operations. The management ensures smooth visual transitions during removal processes.
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
     * <p>
     * Viewport updates refresh the scroll container's viewport to reflect current luggage
     * collection state, ensuring optimal scrolling behavior and content presentation after
     * removal operations. The updates maintain consistent user experience throughout dynamic
     * luggage management workflows.
     * </p>
     * <p>
     * Collection integration maintains references to all necessary data structures and container
     * components required for comprehensive removal operations. The integration enables coordinated
     * updates across multiple interface components and ensures consistent state management
     * throughout luggage removal workflows.
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
     * <p>
     * Index usage includes:
     * </p>
     * <ul>
     *   <li><strong>Collection Management:</strong> Identifying the correct luggage item for removal operations</li>
     *   <li><strong>Layout Coordination:</strong> Supporting proper positioning during restructuring operations</li>
     *   <li><strong>Sequential Ordering:</strong> Maintaining logical order within luggage collections</li>
     *   <li><strong>Dynamic Operations:</strong> Enabling continued luggage management after removal operations</li>
     * </ul>
     * <p>
     * The index value is automatically updated during removal operations to maintain accurate
     * positioning information for remaining luggage items, ensuring consistent interface behavior
     * and proper component identification throughout dynamic luggage management workflows.
     * </p>
     *
     * @return the current sequential index position of this removal button within the luggage collection
     */
    public int getIndex() {
        return index;
    }
}