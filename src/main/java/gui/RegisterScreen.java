package gui;

import com.formdev.flatlaf.FlatClientProperties;
import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static java.lang.Math.max;

/**
 * User registration interface for the airport management system providing account creation functionality.
 * <p>
 * This class extends {@link DisposableObject} to provide the primary user registration interface
 * for the airport management system. It serves as the account creation entry point for new users,
 * managing user credential collection, validation, and registration processes while maintaining
 * seamless integration with the authentication system and application navigation hierarchy.
 * </p>
 * <p>
 * The RegisterScreen class supports comprehensive registration functionality including:
 * </p>
 * <ul>
 *   <li><strong>Multi-Field Registration:</strong> Email, username, and password collection with comprehensive validation</li>
 *   <li><strong>Advanced Password Security:</strong> Integration with PasswordHandler for secure password validation and hashing</li>
 *   <li><strong>Real-time Validation:</strong> Immediate input validation and user feedback during registration process</li>
 *   <li><strong>Responsive Design:</strong> Dynamic sizing and layout adaptation across different screen configurations</li>
 *   <li><strong>Navigation Integration:</strong> Seamless transitions between registration and login interfaces</li>
 *   <li><strong>Modern UI Experience:</strong> FlatLaf integration with password reveal functionality and contemporary styling</li>
 * </ul>
 * <p>
 * The interface is designed with user experience optimization, providing:
 * </p>
 * <ul>
 *   <li><strong>Intuitive Form Layout:</strong> Logical field organization with clear labels and appropriate spacing</li>
 *   <li><strong>Real-time Feedback:</strong> Immediate button state management and validation error display</li>
 *   <li><strong>Security-First Design:</strong> Advanced password validation with user-friendly error messaging</li>
 *   <li><strong>Accessibility Support:</strong> Proper label associations and keyboard navigation support</li>
 *   <li><strong>Responsive Layout:</strong> Dynamic padding and sizing that adapts to different screen configurations</li>
 * </ul>
 * <p>
 * Layout architecture utilizes {@link BorderLayout} with dynamic padding panels to ensure
 * centered content presentation across different screen sizes. The interface maintains a
 * consistent registration menu size while adapting outer padding to accommodate various
 * display configurations and window states, following the same responsive design principles
 * as the companion {@link LogInScreen}.
 * </p>
 * <p>
 * Registration integration leverages the {@link Controller} system to process new user
 * accounts through the database, providing immediate feedback for registration attempts
 * and handling both successful and failed registration scenarios appropriately with
 * comprehensive error reporting and user guidance.
 * </p>
 * <p>
 * Security features include:
 * </p>
 * <ul>
 *   <li><strong>Advanced Password Validation:</strong> Multi-criteria password strength requirements with real-time feedback</li>
 *   <li><strong>Secure Hashing:</strong> Password hashing before transmission and storage for enhanced security</li>
 *   <li><strong>Input Validation:</strong> Comprehensive field validation to prevent invalid registrations</li>
 *   <li><strong>Password Reveal:</strong> Optional password visibility toggle for user convenience during entry</li>
 * </ul>
 * <p>
 * Form validation provides comprehensive input checking including email format validation,
 * username availability verification, and password strength requirements. The validation
 * system provides immediate user feedback and prevents submission of invalid registration
 * data while maintaining user-friendly error messaging and guidance.
 * </p>
 * <p>
 * Navigation integration provides seamless transitions between registration and authentication
 * interfaces through the login button, enabling users to switch between account creation
 * and existing account access without disrupting the application workflow or losing
 * interface state consistency.
 * </p>
 * <p>
 * Visual design incorporates modern UI principles with FlatLaf integration providing
 * contemporary appearance and improved usability. The interface maintains visual consistency
 * with the broader airport management system while providing specialized registration
 * functionality and user experience optimization.
 * </p>
 * <p>
 * Dynamic sizing algorithms ensure consistent appearance across different screen configurations
 * by calculating optimal window dimensions based on screen size and maintaining proper
 * aspect ratios. The padding system adapts to various window states including maximization
 * and custom sizing scenarios, providing consistent user experience.
 * </p>
 * <p>
 * Resource management follows the disposable object pattern with proper cleanup during
 * navigation operations. This ensures optimal memory usage and prevents resource leaks
 * during application lifecycle management and interface transitions.
 * </p>
 * <p>
 * Event handling includes comprehensive user interaction management with real-time button
 * state updates, keyboard navigation support, and dynamic form validation. The system
 * provides immediate feedback for user actions and maintains responsive interface behavior
 * throughout the registration process.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader airport management system through
 * comprehensive controller integration, ensuring proper user registration, database
 * coordination, and navigation management throughout the application ecosystem.
 * </p>
 * <p>
 * Interface responsiveness includes sophisticated window event handling that maintains
 * optimal layout and appearance during window resizing, moving, and state changes. The
 * responsive system ensures consistent user experience across different display scenarios
 * and user interaction patterns.
 * </p>
 * <p>
 * The registration interface serves as a critical component of the airport management
 * system's user management functionality, providing secure, user-friendly account creation
 * that integrates seamlessly with authentication and operational workflows throughout
 * the system.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see DisposableObject
 * @see Controller
 * @see LogInScreen
 * @see PasswordHandler
 * @see FlatClientProperties
 */
public class RegisterScreen extends DisposableObject {

    /**
     * Top padding panel for dynamic interface spacing and responsive design.
     * <p>
     * This panel provides vertical spacing at the top of the registration interface,
     * dynamically sized based on screen dimensions to ensure centered content
     * presentation across different display configurations. The padding adjusts
     * automatically during window resize operations to maintain optimal layout.
     * </p>
     */
    private JPanel topPadding;
    
    /**
     * Bottom padding panel for dynamic interface spacing and responsive design.
     * <p>
     * This panel provides vertical spacing at the bottom of the registration interface,
     * complementing the top padding to maintain centered vertical alignment of the
     * registration content across various screen sizes and window configurations.
     * </p>
     */
    private JPanel bottomPadding;
    
    /**
     * Left padding panel for dynamic interface spacing and responsive design.
     * <p>
     * This panel provides horizontal spacing on the left side of the registration interface,
     * working with right padding to ensure horizontal centering of registration components
     * across different window widths and display configurations for consistent presentation.
     * </p>
     */
    private JPanel leftPadding;
    
    /**
     * Right padding panel for dynamic interface spacing and responsive design.
     * <p>
     * This panel provides horizontal spacing on the right side of the registration interface,
     * balancing the left padding to maintain centered horizontal positioning of the
     * registration menu across various display scenarios and window states.
     * </p>
     */
    private JPanel rightPadding;

    /**
     * Main application window frame for the registration interface.
     * <p>
     * This static JFrame serves as the primary container for the registration interface,
     * configured with appropriate sizing, positioning, and lifecycle management for
     * optimal user registration experience. The static nature enables consistent
     * frame management across registration workflows and navigation operations.
     * </p>
     */
    private static JFrame mainFrame;
    
    /**
     * Primary panel container for the entire registration screen layout.
     * <p>
     * This panel serves as the root container using BorderLayout to organize
     * padding panels and central content area. It provides the foundation for
     * the responsive design system and houses all registration interface components
     * with proper visual hierarchy and organization.
     * </p>
     */
    private JPanel registerScreen;
    
    /**
     * Scroll container for the registration menu providing overflow handling.
     * <p>
     * This scroll pane contains the registration menu and provides scrolling capability
     * when needed, though the design typically maintains content within visible bounds.
     * The container includes optimized scroll policies and smooth scrolling configuration
     * for enhanced user experience during registration.
     * </p>
     */
    private JScrollPane registerMenuScrollContainer;
    
    /**
     * Central registration menu panel containing form components.
     * <p>
     * This panel houses all registration-related components including input fields,
     * labels, and action buttons. It uses GridBagLayout for precise component
     * positioning and maintains consistent dimensions for registration form
     * presentation across different display configurations.
     * </p>
     */
    private JPanel registerMenu;
    
    /**
     * Title label displaying the registration page heading.
     * <p>
     * This label provides clear identification of the registration interface with
     * appropriate typography and styling. It serves as the primary visual identifier
     * for the registration screen and maintains consistent branding with the overall
     * application design and airport management system aesthetic.
     * </p>
     */
    private JLabel pageTitle;

    /**
     * Text field for email address input during user registration.
     * <p>
     * This field accepts user email addresses for account creation, supporting
     * the primary identification method for new user accounts. The field includes
     * validation integration and proper accessibility support for comprehensive
     * email address collection and verification during registration.
     * </p>
     */
    private JTextField mailTextField;
    
    /**
     * Label for the email input field providing user guidance.
     * <p>
     * This label provides clear identification of the email field with Italian
     * localization ("Mail") and includes accessibility support through proper
     * label-field association. The label ensures users understand the email
     * requirement for account creation and registration completion.
     * </p>
     */
    private JLabel mailLabel;
    
    /**
     * Text field for username input during user registration.
     * <p>
     * This field accepts user-chosen usernames for account creation, providing
     * the secondary identification method for new user accounts. The field includes
     * real-time validation and integrates with username availability checking
     * to ensure unique account creation throughout the registration process.
     * </p>
     */
    private JTextField usernameTextField;
    
    /**
     * Label for the username input field providing user guidance.
     * <p>
     * This label provides clear identification of the username field with Italian
     * localization ("Nome Utente") and includes accessibility support through proper
     * label-field association. The label guides users in selecting appropriate
     * usernames for their airport management system accounts.
     * </p>
     */
    private JLabel usernameLabel;
    
    /**
     * Specialized password field with advanced security and validation features.
     * <p>
     * This PasswordHandler component provides comprehensive password management
     * including real-time validation, security requirements enforcement, and
     * secure hashing capabilities. The field integrates with FlatLaf reveal
     * functionality and maintains advanced security standards for user passwords.
     * </p>
     */
    private PasswordHandler passwordField;
    
    /**
     * Label for the password input field providing user guidance.
     * <p>
     * This label provides clear identification of the password field with Italian
     * localization ("Password") and includes accessibility support through proper
     * label-field association. The label indicates password requirements and
     * guides users in creating secure passwords for account protection.
     * </p>
     */
    private JLabel passwordLabel;
    
    /**
     * Primary registration button for account creation submission.
     * <p>
     * This button initiates the user registration process after form validation,
     * integrating with the controller system for account creation and database
     * coordination. The button includes dynamic state management and provides
     * visual feedback during registration operations and validation processes.
     * </p>
     */
    private JButton registerButton;

    /**
     * Navigation button for returning to the login interface.
     * <p>
     * This button provides seamless navigation back to the authentication interface
     * for users who already have accounts or need to access existing credentials.
     * The button maintains proper resource management during navigation and ensures
     * smooth transitions between registration and authentication workflows.
     * </p>
     */
    private JButton loginButton;

    /**
     * Constructs a new RegisterScreen interface for user account creation and registration.
     * <p>
     * This constructor initializes the complete registration interface by establishing the main
     * application window, configuring registration components, and setting up comprehensive event
     * handling for user interaction. The constructor creates a fully functional registration
     * system ready for immediate user account creation with advanced validation, security
     * features, and seamless navigation integration.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Window Configuration:</strong> Main frame setup with appropriate sizing and display properties</li>
     *   <li><strong>UI Enhancement:</strong> Modern look-and-feel configuration and scroll optimization</li>
     *   <li><strong>Responsive Layout:</strong> Dynamic sizing and padding configuration for optimal display</li>
     *   <li><strong>Event Handler Setup:</strong> Comprehensive input validation and registration event handling</li>
     *   <li><strong>Navigation Integration:</strong> Login screen navigation and interface state management</li>
     *   <li><strong>Real-time Validation:</strong> Dynamic form validation and button state management</li>
     * </ul>
     * <p>
     * Window configuration establishes the main application frame through the setMainFrame
     * method, configuring window properties including size based on provided starting dimensions,
     * navigation hierarchy integration, and proper lifecycle management for registration operations.
     * </p>
     * <p>
     * UI enhancement includes scroll container optimization with improved scroll unit increments
     * and password field configuration with FlatLaf reveal button integration for enhanced user
     * experience and modern interface standards that match contemporary application expectations.
     * </p>
     * <p>
     * Responsive layout configuration includes comprehensive window event handling for resizing,
     * moving, and state changes. The responsive system maintains optimal appearance through
     * dynamic padding calculations and layout adjustments across different display scenarios
     * and user interaction patterns.
     * </p>
     * <p>
     * Event handler setup establishes comprehensive registration processing including:
     * </p>
     * <ul>
     *   <li><strong>Registration Button:</strong> Complete registration workflow with validation and database integration</li>
     *   <li><strong>Login Navigation:</strong> Seamless transition to authentication interface</li>
     *   <li><strong>Real-time Input:</strong> Dynamic button state management based on field completion</li>
     *   <li><strong>Keyboard Support:</strong> Enhanced user experience with keyboard-driven form interaction</li>
     * </ul>
     * <p>
     * Registration processing includes advanced password validation through the PasswordHandler
     * system, user controller integration for account creation, and comprehensive error handling
     * with user-friendly feedback messages during registration attempts and validation failures.
     * </p>
     * <p>
     * Navigation integration provides smooth transitions between registration and authentication
     * interfaces while maintaining proper resource management, interface state consistency, and
     * application lifecycle coordination throughout user account creation workflows.
     * </p>
     * <p>
     * Real-time validation includes dynamic button state management that enables registration
     * submission only when required fields contain valid input, providing immediate user feedback
     * and preventing premature submission attempts during the registration process.
     * </p>
     * <p>
     * The constructor completes by establishing a fully functional registration interface that
     * provides secure, user-friendly account creation with comprehensive validation, modern UI
     * features, and seamless integration with the airport management system's authentication
     * and user management infrastructure.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management
     * @param controller the system controller providing access to user registration services and application functionality
     * @param startingSize the initial window dimensions for consistent sizing across navigation transitions
     */
    public RegisterScreen(List<DisposableObject> callingObjects, Controller controller, Dimension startingSize) {

        this.setMainFrame(callingObjects, startingSize);
        mainFrame.setVisible(true);

        registerMenuScrollContainer.getVerticalScrollBar().setUnitIncrement(4);
        passwordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true;");
        resizeFrame();

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                super.componentShown(e);
                resizeFrame();
            }
        });

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                resizeFrame();
            }
        });

        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                super.componentMoved(e);
                resizeFrame();
            }
        });

        mainFrame.addWindowStateListener(new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                super.windowStateChanged(e);
                resizeFrame();
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToLoginPage(callingObjects, controller);
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(passwordField.isValidPassword()){
                    if(controller.getUserController().registerUser(mailTextField.getText(), usernameTextField.getText(), passwordField.getHashedPassword(), registerButton)){
                        goToLoginPage(callingObjects, controller);
                    }
                } else {
                    passwordField.showInvalidPasswordMessage(registerButton);
                }
            }
        });

        usernameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                toggleRegisterButton();
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                toggleRegisterButton();
            }
        });
    }

    /**
     * Initializes and configures the main application window for registration interface display.
     * <p>
     * This private method establishes the main application frame with proper configuration for
     * registration operations, including window properties, navigation hierarchy integration,
     * and lifecycle management. The method ensures proper window setup with registration
     * content integration and immediate visibility for user interaction.
     * </p>
     * <p>
     * Main frame configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Frame Creation:</strong> New JFrame establishment with "RegisterScreen" title for clear identification</li>
     *   <li><strong>Size Configuration:</strong> Window sizing based on provided starting dimensions for consistency</li>
     *   <li><strong>Navigation Integration:</strong> Registration with calling objects hierarchy for proper resource management</li>
     *   <li><strong>Lifecycle Management:</strong> Default close operation configuration for application termination</li>
     *   <li><strong>Content Integration:</strong> Registration screen panel assignment as window content</li>
     * </ul>
     * <p>
     * Frame creation establishes a new JFrame instance with appropriate title identification,
     * providing clear window identification for registration operations and maintaining
     * consistency with application window management patterns throughout the system.
     * </p>
     * <p>
     * Size configuration applies the provided starting dimensions to maintain consistency
     * with previous interface sizing or navigation context. The sizing ensures optimal
     * display characteristics while preparing for responsive layout adjustments during
     * user interaction and window management operations.
     * </p>
     * <p>
     * Navigation integration includes adding the registration screen to the calling objects
     * list, enabling proper resource management and navigation state tracking throughout
     * registration workflows and application lifecycle management operations.
     * </p>
     * <p>
     * Lifecycle management configures the window with EXIT_ON_CLOSE behavior, ensuring
     * proper application termination when users close the registration window. This provides
     * expected application behavior and proper resource cleanup during closure operations.
     * </p>
     * <p>
     * Content integration adds the registration screen panel as the window content,
     * ensuring complete interface functionality with proper component hierarchy and
     * layout management for registration operations and user interaction.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management
     * @param startingSize the initial window dimensions for consistent sizing across navigation transitions
     */
    private void setMainFrame(List<DisposableObject> callingObjects, Dimension startingSize) {
        mainFrame = new JFrame("RegisterScreen");
        mainFrame.setSize(startingSize);
        callingObjects.addLast(this);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.add(registerScreen);

    }

    /**
     * Dynamically adjusts interface layout and padding to maintain optimal appearance across different screen configurations.
     * <p>
     * This private method implements sophisticated responsive design algorithms that maintain consistent
     * registration menu dimensions while adapting outer padding to accommodate various screen sizes,
     * window states, and display configurations. The method ensures optimal visual presentation and
     * usability across different hardware configurations and user preferences, following the same
     * responsive design principles as the companion LogInScreen interface.
     * </p>
     * <p>
     * The responsive design process includes:
     * </p>
     * <ul>
     *   <li><strong>Screen Analysis:</strong> Current screen dimensions detection and optimal size calculation</li>
     *   <li><strong>Dimension Calculation:</strong> Algorithmic determination of optimal window dimensions based on screen characteristics</li>
     *   <li><strong>Padding Computation:</strong> Dynamic padding calculation to ensure centered content presentation</li>
     *   <li><strong>Layout Updates:</strong> Comprehensive padding panel layout manager updates with calculated dimensions</li>
     *   <li><strong>Aspect Ratio Maintenance:</strong> Consistent visual proportions across different display scenarios</li>
     * </ul>
     * <p>
     * Screen analysis utilizes Toolkit.getDefaultToolkit().getScreenSize() to obtain current screen
     * dimensions and calculate optimal window sizing. The algorithm determines desired height as 2/3
     * of screen height and width as 7/10 of the calculated height to maintain consistent aspect ratios
     * and optimal visual presentation across different display configurations.
     * </p>
     * <p>
     * Dimension calculation ensures that the registration interface maintains appropriate sizing
     * relative to screen characteristics while preserving usability and visual appeal. The preferred
     * size is set on the main frame to guide layout calculations and window management operations
     * throughout the registration workflow.
     * </p>
     * <p>
     * Padding computation calculates horizontal and vertical padding values to center the registration
     * content within the available window space. The calculations include minimum padding constraints
     * (36 pixels vertical, 27 pixels horizontal) to ensure adequate spacing even in constrained
     * display scenarios and maintain professional appearance.
     * </p>
     * <p>
     * Layout updates apply calculated padding values to all four padding panels using IntelliJ
     * GridLayoutManager with computed insets. This ensures that the central registration menu
     * maintains consistent positioning and intended dimensions across various window configurations
     * and states while preserving visual design integrity.
     * </p>
     * <p>
     * Aspect ratio maintenance ensures that the registration interface presents consistently across
     * different screen resolutions, window states (normal, maximized, minimized), and user interactions
     * (resizing, moving) while preserving visual design integrity and usability standards throughout
     * the registration process.
     * </p>
     * <p>
     * The method is called automatically during various interface events including window showing,
     * resizing, moving, and state changes to maintain optimal appearance throughout user interaction
     * and system operation, ensuring consistent registration experience across all usage scenarios.
     * </p>
     */
    private void resizeFrame() {
        /*the formulas make it so that the loginMenu is always 480 by 320
         /Dimension - 2paddingDimension = desiredDimension -> paddingDimension = Dimension/2 - desiredDimension/2
         /there are some adjustments because of the border and the decorator
         /the actual dimension of the loginMenufluctuate a little (max 2 in either direction)
         /(not only because of parity, but i have no idea what is it other than that)
         */
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int desiredHeight = (int)screenSize.getHeight() * 2 / 3;
        int desiredWidth = desiredHeight * 7 / 10;
        mainFrame.setPreferredSize(new Dimension(desiredWidth, desiredHeight));
        int paddingHeight = max((mainFrame.getHeight() - desiredHeight) / 2, 36);
        int paddingWidth = max((mainFrame.getWidth() - desiredWidth) / 2, 27);

        topPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(paddingHeight, 0, 0, 0), -1, -1));
        bottomPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, paddingHeight, 0), -1, -1));
        leftPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, paddingWidth, 0, 0), -1, -1));
        rightPadding.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, paddingWidth), -1, -1));

    }

    /**
     * Manages registration button state based on input field completion to provide real-time user feedback.
     * <p>
     * This private method implements dynamic button state management that enables the registration button
     * only when both username and password fields contain input, providing immediate visual feedback about
     * registration readiness. The method enhances user experience by clearly indicating when registration
     * can be attempted and preventing premature registration attempts with incomplete information.
     * </p>
     * <p>
     * Button state management includes:
     * </p>
     * <ul>
     *   <li><strong>Input Validation:</strong> Real-time checking of both username and password field completion</li>
     *   <li><strong>State Synchronization:</strong> Button enabled state synchronized with input field content</li>
     *   <li><strong>User Feedback:</strong> Immediate visual indication of registration form completeness</li>
     *   <li><strong>Interaction Prevention:</strong> Prevention of registration attempts with incomplete information</li>
     * </ul>
     * <p>
     * Input validation examines both username and password fields to determine completion status,
     * ensuring that users have provided essential information before enabling registration submission.
     * The validation provides immediate feedback about form completeness and registration readiness.
     * </p>
     * <p>
     * State synchronization ensures that the registration button enabled state accurately reflects
     * current form completion status, providing users with clear visual feedback about when they
     * can proceed with account creation and registration submission.
     * </p>
     * <p>
     * User feedback enhancement provides immediate visual indication of registration form status,
     * helping users understand registration requirements and form completion progress throughout
     * the account creation process.
     * </p>
     * <p>
     * Interaction prevention ensures that users cannot attempt registration with incomplete
     * information, reducing error scenarios and providing clearer user experience during the
     * registration process while maintaining form validation integrity.
     * </p>
     * <p>
     * The method is called automatically during key release events in both username and password
     * fields, ensuring immediate response to user input changes and maintaining current button
     * state throughout user interaction with the registration interface.
     * </p>
     */
    private void toggleRegisterButton(){
        registerButton.setEnabled(!usernameTextField.getText().isEmpty() && passwordField.getPassword().length != 0);
    }

    /**
     * Processes navigation transition from registration to login interface with proper resource management.
     * <p>
     * This private method handles the complete transition from registration to authentication interface,
     * including proper window management, resource cleanup, and seamless navigation coordination. The
     * method ensures clean interface transitions while maintaining application state consistency and
     * proper resource management throughout navigation operations.
     * </p>
     * <p>
     * The transition process includes:
     * </p>
     * <ul>
     *   <li><strong>Window Management:</strong> Registration interface hiding in preparation for login interface display</li>
     *   <li><strong>Interface Creation:</strong> New LogInScreen instantiation with proper parameter passing</li>
     *   <li><strong>Resource Cleanup:</strong> Proper disposal of registration interface resources and components</li>
     *   <li><strong>Frame Management:</strong> Complete registration window disposal and memory cleanup</li>
     * </ul>
     * <p>
     * Window management hides the main registration frame in preparation for login interface display,
     * ensuring smooth visual transition without interface overlap or flickering during the navigation
     * process and maintaining professional user experience.
     * </p>
     * <p>
     * Interface creation establishes a new LogInScreen instance with proper parameter passing including
     * calling objects hierarchy and controller integration. The creation maintains interface continuity
     * and ensures proper system coordination throughout navigation operations.
     * </p>
     * <p>
     * Resource cleanup includes proper disposal of registration interface components through the
     * doOnDispose method, ensuring that all registration-related resources are properly released
     * and preventing memory leaks during navigation transitions.
     * </p>
     * <p>
     * Frame management includes complete disposal of the registration window frame, ensuring
     * proper resource cleanup and memory management during interface transitions while maintaining
     * application lifecycle integrity and performance optimization.
     * </p>
     * <p>
     * The method provides seamless, efficient navigation transition with proper resource management
     * and clean interface state handling, ensuring optimal user experience and system performance
     * during transitions between registration and authentication workflows.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow management
     * @param controller the system controller providing access to navigation services and application functionality
     */
    private void goToLoginPage(List<DisposableObject> callingObjects, Controller controller) {
        mainFrame.setVisible(false);
        new LogInScreen(callingObjects, controller, mainFrame.getSize());
        doOnDispose(callingObjects, controller);
        mainFrame.dispose();
    }

    /**
     * Provides access to the main application window frame for navigation and resource management.
     * <p>
     * This method returns the main JFrame instance that contains the registration interface,
     * enabling external components to perform window management operations such as sizing,
     * positioning, visibility control, and resource management. The method supports the
     * DisposableObject pattern by providing frame access for navigation operations.
     * </p>
     * <p>
     * Frame access capabilities include:
     * </p>
     * <ul>
     *   <li><strong>Window Management:</strong> External control of registration window properties and state</li>
     *   <li><strong>Resource Coordination:</strong> Integration with disposable object lifecycle management</li>
     *   <li><strong>Navigation Support:</strong> Frame reference for navigation hierarchy and transitions</li>
     *   <li><strong>State Management:</strong> Window state access for application coordination and management</li>
     * </ul>
     * <p>
     * Window management enables external components to control registration window properties
     * including size, position, visibility, and extended state for optimal integration with
     * application navigation workflows and user experience management.
     * </p>
     * <p>
     * Resource coordination provides integration with the DisposableObject pattern, enabling
     * proper resource management and cleanup during navigation operations and application
     * lifecycle management throughout registration workflows.
     * </p>
     * <p>
     * Navigation support includes frame reference provision for navigation hierarchy management,
     * enabling proper parent-child relationships and navigation state tracking throughout
     * application usage and interface transitions.
     * </p>
     * <p>
     * State management enables external access to window state information for application
     * coordination, ensuring proper integration with broader application management systems
     * and user experience coordination throughout registration operations.
     * </p>
     *
     * @return the main JFrame instance containing the registration interface
     */
    @Override
    public JFrame getFrame() {
        return mainFrame;
    }

}