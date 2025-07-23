package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Temporary notification display system for user feedback and operational status communication.
 * <p>
 * This class provides a sophisticated floating message system for displaying temporary notifications
 * positioned relative to triggering UI components. It implements automatic positioning, visual
 * styling based on message type, and automated disposal with fade-out animation effects for
 * optimal user experience and interface feedback.
 * </p>
 * <p>
 * The FloatingMessage class supports comprehensive notification functionality including:
 * </p>
 * <ul>
 *   <li><strong>Message Type Classification:</strong> Three distinct message types with appropriate visual styling</li>
 *   <li><strong>Intelligent Positioning:</strong> Automatic positioning relative to calling buttons with screen boundary detection</li>
 *   <li><strong>Visual Consistency:</strong> Rounded panel design with type-specific color schemes and transparency effects</li>
 *   <li><strong>Automatic Disposal:</strong> Timed disposal with fade-out animation through integrated DisposeTimers</li>
 *   <li><strong>Screen Boundary Handling:</strong> Automatic adjustment to prevent messages from appearing off-screen</li>
 *   <li><strong>HTML Content Support:</strong> Rich text formatting capabilities for complex message display</li>
 * </ul>
 * <p>
 * The message type system provides three distinct categories for different operational scenarios:
 * </p>
 * <ul>
 *   <li><strong>Error Messages:</strong> Red-themed notifications for error conditions and validation failures</li>
 *   <li><strong>Warning Messages:</strong> Yellow-themed notifications for cautionary information and alerts</li>
 *   <li><strong>Success Messages:</strong> Green-themed notifications for successful operations and confirmations</li>
 * </ul>
 * <p>
 * Positioning intelligence ensures optimal message placement by calculating the ideal location
 * relative to the triggering button while implementing screen boundary detection to prevent
 * messages from appearing outside the visible screen area. The system automatically adjusts
 * horizontal positioning when messages would extend beyond screen boundaries.
 * </p>
 * <p>
 * Visual design incorporates semi-transparent overlays (75% opacity) with rounded panel styling
 * that maintains readability while providing subtle visual integration with the underlying
 * interface. Each message type features distinct color schemes including background colors
 * and border colors that provide immediate visual classification.
 * </p>
 * <p>
 * Automatic disposal functionality integrates seamlessly with the {@link DisposeTimers} system
 * to provide sophisticated fade-out animation sequences. Messages remain visible for an initial
 * period before gradually fading to transparency and complete disposal, ensuring user awareness
 * without requiring manual dismissal.
 * </p>
 * <p>
 * HTML content support enables rich text formatting including line breaks, text styling, and
 * centered alignment for complex message presentation. The system automatically centers content
 * both horizontally and vertically within the message window for optimal readability.
 * </p>
 * <p>
 * Integration with the airport management system enables comprehensive user feedback for:
 * </p>
 * <ul>
 *   <li><strong>Validation Results:</strong> Form field validation feedback and data entry errors</li>
 *   <li><strong>Operation Confirmation:</strong> Successful completion notifications for user actions</li>
 *   <li><strong>System Status:</strong> Operational status updates and system state changes</li>
 *   <li><strong>Error Reporting:</strong> User-friendly error messages and recovery guidance</li>
 * </ul>
 * <p>
 * The floating message system is designed for immediate instantiation and automatic lifecycle
 * management. Each FloatingMessage instance handles its complete lifecycle from creation through
 * automatic disposal, requiring no manual cleanup or resource management by calling code.
 * </p>
 * <p>
 * Thread safety is provided through Swing's Event Dispatch Thread execution model, as all
 * GUI operations execute on the EDT. The class should only be instantiated from the Event
 * Dispatch Thread to maintain proper Swing threading compliance.
 * </p>
 * <p>
 * Usage examples throughout the application include booking validation feedback, flight operation
 * confirmations, administrative action results, and user authentication status notifications.
 * The system provides consistent user experience across all application interfaces.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see DisposeTimers
 * @see RoundedPanel
 * @see FloatingMessageException
 * @see JWindow
 * @see JButton
 */
public class FloatingMessage {

    /**
     * Message type constant for error notifications and validation failures.
     * <p>
     * This constant identifies error messages that should be displayed with red-themed
     * styling to indicate problems, failures, or conditions requiring user attention.
     * Error messages typically appear for validation failures, operational errors,
     * and system exceptions that prevent successful task completion.
     * </p>
     * <p>
     * Visual styling includes:
     * </p>
     * <ul>
     *   <li>Background color: Red (200, 60, 60)</li>
     *   <li>Border color: Dark red (120, 0, 10)</li>
     *   <li>Semantic meaning: Error, failure, attention required</li>
     * </ul>
     */
    public static final int ERROR_MESSAGE = 1;
    
    /**
     * Message type constant for warning notifications and cautionary information.
     * <p>
     * This constant identifies warning messages that should be displayed with yellow-themed
     * styling to indicate cautionary conditions, alerts, or information requiring user
     * awareness. Warning messages typically appear for non-critical issues, operational
     * alerts, and conditions that may require user consideration.
     * </p>
     * <p>
     * Visual styling includes:
     * </p>
     * <ul>
     *   <li>Background color: Yellow (240, 220, 50)</li>
     *   <li>Border color: Dark yellow (160, 140, 10)</li>
     *   <li>Semantic meaning: Warning, caution, informational alert</li>
     * </ul>
     */
    public static final int WARNING_MESSAGE = 2;
    
    /**
     * Message type constant for success notifications and confirmation messages.
     * <p>
     * This constant identifies success messages that should be displayed with green-themed
     * styling to indicate successful operations, confirmations, and positive outcomes.
     * Success messages typically appear for completed operations, successful validations,
     * and confirmations of user actions.
     * </p>
     * <p>
     * Visual styling includes:
     * </p>
     * <ul>
     *   <li>Background color: Green (139, 255, 104)</li>
     *   <li>Border color: Dark green (55, 142, 5)</li>
     *   <li>Semantic meaning: Success, completion, positive confirmation</li>
     * </ul>
     */
    public static final int SUCCESS_MESSAGE = 3;
    
    /**
     * Main window component for displaying the floating message notification.
     * <p>
     * This JWindow serves as the container for the message display, providing
     * always-on-top behavior, transparency effects, and precise positioning
     * relative to the triggering UI component. The window is configured with
     * semi-transparent background and appropriate sizing for message content.
     * </p>
     */
    private JWindow messageWindow;
    
    /**
     * Rounded panel component containing the message content and visual styling.
     * <p>
     * This RoundedPanel provides the visual container for message text with
     * rounded corners, type-specific background colors, and border styling.
     * The panel integrates with the message window to provide cohesive visual
     * presentation and proper content layout management.
     * </p>
     */
    private RoundedPanel messagePanel;

    /**
     * Constructs and displays a new floating message notification with automatic positioning and disposal.
     * <p>
     * This constructor creates a complete floating message system by initializing the message window,
     * configuring visual styling based on message type, positioning the message relative to the
     * calling button, and activating automatic disposal through the DisposeTimers system. The
     * message becomes immediately visible upon construction completion.
     * </p>
     * <p>
     * The construction process includes:
     * </p>
     * <ul>
     *   <li><strong>Window Initialization:</strong> Creating and configuring the JWindow with transparency and positioning</li>
     *   <li><strong>Panel Setup:</strong> Creating the RoundedPanel with message content and type-specific styling</li>
     *   <li><strong>Positioning Logic:</strong> Calculating optimal position relative to calling button with boundary detection</li>
     *   <li><strong>Disposal Integration:</strong> Activating DisposeTimers for automatic fade-out and cleanup</li>
     *   <li><strong>Visibility Activation:</strong> Making the message immediately visible to the user</li>
     * </ul>
     * <p>
     * Window initialization creates a JWindow with always-on-top behavior, semi-transparent
     * background (75% opacity), and transparent content panel. The window is sized to
     * accommodate message content while maintaining consistent dimensions across message types.
     * </p>
     * <p>
     * Panel setup creates a RoundedPanel with BorderLayout containing the message text
     * in HTML format for centered display. The panel receives type-specific styling
     * including background colors and border colors appropriate for the message type.
     * </p>
     * <p>
     * Positioning logic calculates the optimal message location based on the calling button's
     * screen position, implementing intelligent boundary detection to ensure messages remain
     * visible within screen boundaries. Horizontal positioning is automatically adjusted
     * when messages would extend beyond screen edges.
     * </p>
     * <p>
     * Disposal integration immediately activates the DisposeTimers system to manage automatic
     * message lifecycle including visibility duration, fade-out animation, and complete
     * resource cleanup without requiring manual intervention by calling code.
     * </p>
     * <p>
     * The constructor completes by making the message immediately visible, ensuring users
     * receive immediate feedback for the triggering action while beginning the automatic
     * disposal sequence for proper resource management.
     * </p>
     *
     * @param msg the message text to display, supporting HTML formatting for rich text presentation
     * @param callingButton the JButton that triggered the message display, used for positioning calculations and screen coordinate determination
     * @param messageType the type of message determining visual styling, must be one of ERROR_MESSAGE, WARNING_MESSAGE, or SUCCESS_MESSAGE
     */
    public FloatingMessage (String msg, JButton callingButton, int messageType){

        setWindow(callingButton);
        setPanel(msg, messageType);

        
        new DisposeTimers(messageWindow);

        messageWindow.setVisible(true);
    }

    /**
     * Initializes and configures the message window with positioning and transparency settings.
     * <p>
     * This method creates and configures the JWindow that serves as the container for the
     * floating message, establishing window properties including transparency, size, positioning,
     * and always-on-top behavior. The method integrates intelligent positioning logic to
     * ensure optimal message placement relative to the triggering button.
     * </p>
     * <p>
     * Window configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Always-on-Top Behavior:</strong> Ensures message visibility above other application windows</li>
     *   <li><strong>Transparency Settings:</strong> 75% opacity for subtle visual integration</li>
     *   <li><strong>Background Configuration:</strong> Transparent background for rounded panel integration</li>
     *   <li><strong>Content Panel Setup:</strong> Transparent content panel for proper visual composition</li>
     *   <li><strong>Size Configuration:</strong> Standard 300x100 pixel dimensions for consistent message sizing</li>
     *   <li><strong>Position Calculation:</strong> Intelligent positioning relative to calling button with boundary detection</li>
     * </ul>
     * <p>
     * Always-on-top behavior ensures that floating messages remain visible above other
     * application windows and system dialogs, providing immediate user feedback regardless
     * of the current focus or window stacking order.
     * </p>
     * <p>
     * Transparency configuration creates a semi-transparent overlay effect (75% opacity)
     * that maintains message readability while providing subtle visual integration with
     * the underlying interface, reducing visual disruption while ensuring message visibility.
     * </p>
     * <p>
     * Background and content panel configuration creates a transparent foundation that
     * allows the RoundedPanel component to provide complete visual styling including
     * rounded corners, type-specific colors, and proper border rendering.
     * </p>
     * <p>
     * Size configuration establishes consistent message dimensions (300x100 pixels) that
     * accommodate typical message content while maintaining visual proportions across
     * different screen sizes and resolutions.
     * </p>
     * <p>
     * Position calculation leverages intelligent positioning logic through the getPoint
     * method to determine optimal message placement that considers button location,
     * screen boundaries, and visual alignment for optimal user experience.
     * </p>
     *
     * @param callingButton the JButton component that triggered the message display, used for position calculations and screen coordinate determination
     */
    private void setWindow (JButton callingButton) {

        messageWindow = new JWindow();

        messageWindow.setAlwaysOnTop(true);
        messageWindow.setOpacity(0.75f);
        messageWindow.setBackground(new Color(0, 0, 0, 0));

        JPanel contentPanel = (JPanel) messageWindow.getContentPane();
        contentPanel.setOpaque(false);
        messageWindow.setSize(300, 100);

        Point callingButtonLocation = new Point(callingButton.getLocationOnScreen());
        Point messageLocation = getPoint(callingButton, callingButtonLocation);

        messageWindow.setLocation(messageLocation);
    }

    /**
     * Calculates optimal message position with intelligent screen boundary detection and adjustment.
     * <p>
     * This method implements sophisticated positioning logic to determine the ideal location
     * for displaying floating messages relative to the triggering button while ensuring
     * complete visibility within screen boundaries. The method provides automatic adjustment
     * for horizontal positioning when messages would extend beyond screen edges.
     * </p>
     * <p>
     * The positioning algorithm includes:
     * </p>
     * <ul>
     *   <li><strong>Centered Horizontal Alignment:</strong> Centers message horizontally relative to triggering button</li>
     *   <li><strong>Above-Button Vertical Positioning:</strong> Positions message above button with appropriate spacing</li>
     *   <li><strong>Left Boundary Detection:</strong> Prevents messages from extending beyond left screen edge</li>
     *   <li><strong>Right Boundary Detection:</strong> Prevents messages from extending beyond right screen edge</li>
     *   <li><strong>Automatic Adjustment:</strong> Repositions messages when boundary violations are detected</li>
     * </ul>
     * <p>
     * Centered horizontal alignment calculates the message position to center the message
     * window horizontally relative to the triggering button, providing visually balanced
     * positioning that clearly associates the message with its triggering action.
     * </p>
     * <p>
     * Above-button vertical positioning places the message above the triggering button
     * with a 10-pixel spacing buffer, ensuring clear visual separation while maintaining
     * obvious association between the message and its trigger.
     * </p>
     * <p>
     * Left boundary detection identifies scenarios where centered positioning would place
     * the message partially or completely off the left edge of the screen. When detected,
     * the message is repositioned with a 5-pixel margin from the left screen edge.
     * </p>
     * <p>
     * Right boundary detection identifies scenarios where the message would extend beyond
     * the right edge of the screen based on the message width (300 pixels). When detected,
     * the message is repositioned to maintain a 5-pixel margin from the right screen edge.
     * </p>
     * <p>
     * Automatic adjustment ensures that all floating messages remain completely visible
     * within screen boundaries regardless of the triggering button's position, providing
     * consistent user experience across different screen sizes and button placements.
     * </p>
     *
     * @param callingButton the JButton component used for width calculations and positioning reference
     * @param callingButtonLocation the screen coordinates of the triggering button for position calculations
     * @return the calculated Point representing the optimal screen position for the message window
     */
    private Point getPoint(JButton callingButton, Point callingButtonLocation) {
        Point messageLocation = new Point((int) callingButtonLocation.getX() + (callingButton.getWidth() - messageWindow.getWidth()) / 2,
                (int) callingButtonLocation.getY() - messageWindow.getHeight() - 10);

        if (messageLocation.getX() < 0)
            messageLocation.setLocation(5, (int) messageLocation.getY());
        else if (messageLocation.getX() + 300 > Toolkit.getDefaultToolkit().getScreenSize().getWidth())
            messageLocation.setLocation((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() - 305),
                    (int) messageWindow.getLocationOnScreen().getY());
        return messageLocation;
    }

    /**
     * Creates and configures the message content panel with HTML text formatting and type-specific styling.
     * <p>
     * This method establishes the visual content container for the floating message by creating
     * a RoundedPanel with BorderLayout, configuring message text with HTML formatting for
     * centered display, and applying type-specific visual styling through color configuration.
     * The method integrates text presentation with visual design for optimal user readability.
     * </p>
     * <p>
     * Panel configuration includes:
     * </p>
     * <ul>
     *   <li><strong>RoundedPanel Creation:</strong> Creates styled panel container with BorderLayout for content organization</li>
     *   <li><strong>Color Styling:</strong> Applies message type-specific background and border colors</li>
     *   <li><strong>HTML Text Formatting:</strong> Formats message text with HTML for centered alignment and rich formatting</li>
     *   <li><strong>Label Configuration:</strong> Creates centered JLabel with black text for optimal readability</li>
     *   <li><strong>Layout Integration:</strong> Integrates content label with panel layout and window structure</li>
     * </ul>
     * <p>
     * RoundedPanel creation establishes the visual foundation with BorderLayout management
     * that provides flexible content organization while maintaining the rounded corner
     * styling and type-specific visual appearance that distinguishes different message types.
     * </p>
     * <p>
     * Color styling delegation to the setColor method ensures that message panels receive
     * appropriate visual treatment based on message type, including background colors
     * and border colors that provide immediate visual classification for users.
     * </p>
     * <p>
     * HTML text formatting wraps the message content in HTML tags with center alignment,
     * enabling rich text presentation including line breaks, text styling, and proper
     * centering within the message container for optimal visual presentation.
     * </p>
     * <p>
     * Label configuration creates a JLabel with centered alignment both horizontally and
     * vertically, using black text color for optimal readability against the various
     * background colors used for different message types.
     * </p>
     * <p>
     * Layout integration combines the configured message label with the RoundedPanel
     * using BorderLayout.CENTER placement, and integrates the complete panel with
     * the message window for final display presentation.
     * </p>
     *
     * @param msg the message text content to display, which will be formatted with HTML for centered presentation
     * @param messageType the message type constant determining visual styling and color scheme application
     */
    private void setPanel (String msg, int messageType) {

        messagePanel = new RoundedPanel(new BorderLayout());
        setColor(messageType);

        JLabel messageLabel = new JLabel("<html><center>" + msg + "</center></html>", SwingConstants.CENTER);
        messageLabel.setForeground(Color.BLACK);

        messagePanel.add(messageLabel, BorderLayout.CENTER);

        messageWindow.add(messagePanel);
    }
    
    /**
     * Applies message type-specific color schemes to the message panel for visual classification.
     * <p>
     * This method implements the visual styling system by applying appropriate background and
     * border colors based on the message type parameter. Each message type receives distinct
     * color treatment that provides immediate visual classification and semantic meaning for
     * users, enhancing the communication effectiveness of the notification system.
     * </p>
     * <p>
     * Color scheme implementation includes:
     * </p>
     * <ul>
     *   <li><strong>Error Messages:</strong> Red background (200, 60, 60) with dark red border (120, 0, 10)</li>
     *   <li><strong>Warning Messages:</strong> Yellow background (240, 220, 50) with dark yellow border (160, 140, 10)</li>
     *   <li><strong>Success Messages:</strong> Green background (139, 255, 104) with dark green border (55, 142, 5)</li>
     *   <li><strong>Type Validation:</strong> Comprehensive validation with exception throwing for invalid message types</li>
     * </ul>
     * <p>
     * Error message styling uses red color schemes that immediately communicate failure,
     * problems, or conditions requiring user attention. The combination of bright red
     * background with darker red border creates visual emphasis while maintaining
     * readability of the contained text content.
     * </p>
     * <p>
     * Warning message styling employs yellow color schemes that indicate caution, alerts,
     * or informational conditions that require user awareness. The yellow background
     * with darker yellow border provides clear visual distinction while avoiding the
     * urgency associated with error coloring.
     * </p>
     * <p>
     * Success message styling utilizes green color schemes that communicate positive
     * outcomes, successful operations, and confirmations. The bright green background
     * with darker green border provides clear positive reinforcement for user actions
     * and system operations.
     * </p>
     * <p>
     * Type validation ensures system integrity by verifying that only valid message
     * type constants are processed. Invalid message types trigger FloatingMessageException
     * with descriptive error messages that aid in debugging and system maintenance.
     * </p>
     * <p>
     * The color application process configures both the RoundedPanel background color
     * and the specialized round border color, ensuring consistent visual treatment
     * across the entire message display including corners and edges.
     * </p>
     *
     * @param messageType the message type constant that determines color scheme application, must be ERROR_MESSAGE, WARNING_MESSAGE, or SUCCESS_MESSAGE
     */
    private void setColor(int messageType){

        switch (messageType) {
            case ERROR_MESSAGE -> {
                messagePanel.setBackground(new Color(200, 60, 60));
                messagePanel.setRoundBorderColor(new Color(120, 0, 10));
            }
            case WARNING_MESSAGE -> {
                messagePanel.setBackground(new Color(240, 220, 50));
                messagePanel.setRoundBorderColor(new Color(160, 140, 10));
            }
            case SUCCESS_MESSAGE -> {
                messagePanel.setBackground(new Color(139, 255, 104));
                messagePanel.setRoundBorderColor(new Color(55, 142, 5));
            }
            default ->
                throw new FloatingMessageException("FloatingMessage: messageType must be one of" +
                        "FloatingMessage.ERROR_MESSAGE, FloatingMessage.WARNING_MESSAGE, FloatingMessage.SUCCESS_MESSAGE");

        }
    }
}