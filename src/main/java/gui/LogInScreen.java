package gui;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.util.List;
import java.util.ArrayList;

import static java.lang.Math.max;

/**
 * User authentication interface for the airport management system providing login functionality.
 * <p>
 * This class extends {@link DisposableObject} to provide the primary authentication interface
 * for the airport management system. It serves as the entry point for both administrative and
 * customer users, managing user credentials, authentication processes, and navigation to
 * appropriate home interfaces based on user roles and permissions.
 * </p>
 * <p>
 * The LogInScreen class supports comprehensive authentication functionality including:
 * </p>
 * <ul>
 *   <li><strong>Dual User Authentication:</strong> Support for both administrative and customer user authentication</li>
 *   <li><strong>Secure Password Handling:</strong> Advanced password validation and secure hashing for authentication</li>
 *   <li><strong>Modern UI Integration:</strong> FlatLaf look-and-feel with responsive design and accessibility features</li>
 *   <li><strong>Dynamic Interface Sizing:</strong> Adaptive layout that maintains optimal appearance across different screen configurations</li>
 *   <li><strong>Role-Based Navigation:</strong> Automatic redirection to appropriate home interfaces based on authenticated user type</li>
 *   <li><strong>Registration Integration:</strong> Seamless transition to user registration interface for new users</li>
 *   <li><strong>Input Validation:</strong> Real-time validation of user credentials with immediate feedback</li>
 *   <li><strong>Session Management:</strong> Proper cleanup and initialization of user sessions during authentication</li>
 * </ul>
 * <p>
 * The interface is designed with user experience optimization, providing:
 * </p>
 * <ul>
 *   <li><strong>Intuitive Layout:</strong> Clean, centered design with logical component organization</li>
 *   <li><strong>Keyboard Navigation:</strong> Full keyboard support including Enter key authentication</li>
 *   <li><strong>Visual Feedback:</strong> Real-time button state management and input validation indicators</li>
 *   <li><strong>Password Security:</strong> Integrated password reveal functionality and secure handling</li>
 *   <li><strong>Responsive Design:</strong> Dynamic padding and sizing that adapts to different screen sizes</li>
 *   <li><strong>Accessibility Support:</strong> Proper focus management and assistive technology compatibility</li>
 * </ul>
 * <p>
 * Layout architecture utilizes {@link BorderLayout} with dynamic padding panels to ensure
 * centered content presentation across different screen sizes. The interface maintains a
 * consistent login menu size (480x320) while adapting outer padding to accommodate various
 * display configurations and window states.
 * </p>
 * <p>
 * Authentication integration leverages the {@link Controller} system to verify user credentials
 * against the database, supporting both username and email-based authentication. The system
 * provides immediate feedback for authentication attempts and handles both successful and
 * failed login scenarios appropriately.
 * </p>
 * <p>
 * Password security features include:
 * </p>
 * <ul>
 *   <li><strong>Secure Hashing:</strong> Password hashing before transmission for enhanced security</li>
 *   <li><strong>Validation Integration:</strong> Comprehensive password validation with user feedback</li>
 *   <li><strong>Reveal Functionality:</strong> Optional password visibility toggle for user convenience</li>
 *   <li><strong>Input Masking:</strong> Standard password field masking for privacy protection</li>
 * </ul>
 * <p>
 * Role-based navigation automatically directs authenticated users to appropriate interfaces:
 * administrative users are redirected to {@link HomePageAdmin} while customer users access
 * {@link HomePageCustomer}. This ensures proper access control and user experience tailoring
 * based on account privileges and system roles.
 * </p>
 * <p>
 * The interface supports seamless integration with the registration system through the
 * registration button, enabling new users to create accounts without disrupting the
 * authentication workflow. Navigation between login and registration maintains proper
 * resource management and interface state consistency.
 * </p>
 * <p>
 * Visual design incorporates modern UI principles with FlatLaf integration providing
 * contemporary appearance and improved usability. The system gracefully handles FlatLaf
 * unavailability by falling back to system default look-and-feel with user notification.
 * </p>
 * <p>
 * Dynamic sizing algorithms ensure consistent appearance across different screen configurations
 * by calculating optimal window dimensions based on screen size and maintaining proper
 * aspect ratios. The padding system adapts to various window states including maximization
 * and custom sizing scenarios.
 * </p>
 * <p>
 * Resource management follows the disposable object pattern with proper cleanup of previous
 * interface instances during navigation operations. This ensures optimal memory usage and
 * prevents resource leaks during application lifecycle management.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader airport management system through
 * comprehensive controller integration, ensuring proper authentication, session management,
 * and navigation coordination throughout the application ecosystem.
 * </p>
 * <p>
 * Application entry point functionality includes complete system initialization with
 * look-and-feel configuration, controller setup, and primary window establishment for
 * standalone application execution scenarios.
 * </p>
 * <p>
 * The login interface serves as the security gateway for the entire airport management
 * system, ensuring that only authenticated users gain access to system functionality
 * while providing an intuitive and secure authentication experience.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see DisposableObject
 * @see Controller
 * @see HomePageAdmin
 * @see HomePageCustomer
 * @see RegisterScreen
 * @see PasswordHandler
 */
public class LogInScreen extends DisposableObject {

    /**
     * Top padding panel for dynamic interface spacing.
     * <p>
     * This panel provides vertical spacing at the top of the interface,
     * dynamically sized based on screen dimensions to ensure centered
     * content presentation across different display configurations.
     * </p>
     */
    private JPanel topPadding;
    
    /**
     * Bottom padding panel for dynamic interface spacing.
     * <p>
     * This panel provides vertical spacing at the bottom of the interface,
     * complementing the top padding to maintain centered vertical alignment
     * of the login content across various screen sizes.
     * </p>
     */
    private JPanel bottomPadding;
    
    /**
     * Left padding panel for dynamic interface spacing.
     * <p>
     * This panel provides horizontal spacing on the left side of the interface,
     * working with right padding to ensure horizontal centering of login
     * components across different window widths and display configurations.
     * </p>
     */
    private JPanel leftPadding;
    
    /**
     * Right padding panel for dynamic interface spacing.
     * <p>
     * This panel provides horizontal spacing on the right side of the interface,
     * balancing the left padding to maintain centered horizontal positioning
     * of the login menu across various display scenarios.
     * </p>
     */
    private JPanel rightPadding;

    /**
     * Main application window frame for the login interface.
     * <p>
     * This static JFrame serves as the primary container for the login interface,
     * configured with appropriate sizing, positioning, and lifecycle management
     * for optimal user authentication experience. The static nature enables
     * application-level access for main method initialization.
     * </p>
     */
    private static JFrame mainFrame;
    
    /**
     * Primary panel container for the entire login screen layout.
     * <p>
     * This panel serves as the root container using BorderLayout to organize
     * padding panels and central content area. It provides the foundation
     * for the responsive design system and houses all login interface components.
     * </p>
     */
    private JPanel loginScreenPanel;
    
    /**
     * Scroll container for the login menu providing overflow handling.
     * <p>
     * This scroll pane contains the login menu and provides scrolling capability
     * when needed, though the design typically maintains content within visible
     * bounds. The container includes optimized scroll policies and smooth
     * scrolling configuration for enhanced user experience.
     * </p>
     */
    private JScrollPane loginMenuScrollContainer;
    
    /**
     * Central login menu panel containing authentication components.
     * <p>
     * This panel houses all login-related components including input fields,
     * labels, and action buttons. It uses GridBagLayout for precise component
     * positioning and maintains the core 480x320 dimensions for consistency
     * across different display configurations.
     * </p>
     */
    private JPanel loginMenu;
    
    /**
     * Title label displaying the login page heading.
     * <p>
     * This label provides clear identification of the login interface with
     * appropriate typography and styling. It serves as the primary visual
     * identifier for the authentication screen and maintains consistent
     * branding with the overall application design.
     * </p>
     */
    private JLabel pageTitle;

    /**
     * Text field for username or email input during authentication.
     * <p>
     * This field accepts both username and email address for login flexibility,
     * supporting the dual authentication modes provided by the system. The field
     * includes real-time validation and integrates with keyboard navigation
     * for enhanced user experience.
     * </p>
     */
    private JTextField usernameTextField;
    
    /**
     * Label for the username input field providing user guidance.
     * <p>
     * This label provides clear identification of the username field and
     * includes accessibility support through proper label-field association.
     * The text indicates support for both username and email authentication
     * methods for user convenience.
     * </p>
     */
    private JLabel usernameLabel;
    
    /**
     * Specialized password field with advanced security and validation features.
     * <p>
     * This custom password handler provides secure password input with
     * validation capabilities, hashing functionality, and optional reveal
     * features. It integrates with the authentication system to ensure
     * secure credential handling throughout the login process.
     * </p>
     */
    private PasswordHandler passwordField;
    
    /**
     * Label for the password input field providing user guidance.
     * <p>
     * This label provides clear identification of the password field and
     * maintains proper accessibility relationships with the password input.
     * The label styling matches the overall interface design for visual
     * consistency and professional appearance.
     * </p>
     */
    private JLabel passwordLabel;
    
    /**
     * Primary login button for authentication processing.
     * <p>
     * This button initiates the authentication process when activated, integrating
     * with the controller system to verify user credentials. The button includes
     * dynamic state management, keyboard navigation support, and provides
     * immediate feedback during authentication attempts.
     * </p>
     */
    private JButton logInButton;

    /**
     * Registration navigation button for new user account creation.
     * <p>
     * This button provides seamless navigation to the registration interface
     * for users who need to create new accounts. The button maintains proper
     * resource management during navigation and ensures smooth transition
     * between authentication and registration workflows.
     * </p>
     */
    private JButton registerButton;

    /**
     * Constructs a new LogInScreen interface for user authentication and system access.
     * <p>
     * This constructor initializes the complete login interface by establishing the main
     * application window, configuring authentication components, and setting up event
     * handling for user interaction. The constructor creates a fully functional login
     * system ready for immediate user authentication with comprehensive input validation
     * and secure credential handling.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Session Cleanup:</strong> Proper disposal of any existing interface instances</li>
     *   <li><strong>Window Configuration:</strong> Main frame setup with appropriate sizing and properties</li>
     *   <li><strong>UI Enhancement:</strong> Modern look-and-feel configuration and scroll optimization</li>
     *   <li><strong>Event Handler Setup:</strong> Comprehensive input validation and authentication event handling</li>
     *   <li><strong>Navigation Integration:</strong> Registration screen navigation and window state management</li>
     *   <li><strong>Responsive Layout:</strong> Dynamic sizing and padding configuration for optimal display</li>
     * </ul>
     * <p>
     * Session cleanup ensures that any existing disposable objects in the calling hierarchy
     * are properly disposed before establishing the new login interface. This prevents
     * resource leaks and ensures clean state initialization for the authentication process.
     * </p>
     * <p>
     * Window configuration establishes the main application frame through the setMainFrame
     * method when transitioning from other interfaces, or utilizes existing static frame
     * configuration for application entry scenarios. The setup includes proper sizing,
     * content pane assignment, and lifecycle management.
     * </p>
     * <p>
     * UI enhancement includes scroll container optimization with improved scroll unit
     * increments and password field configuration with FlatLaf reveal button integration
     * for enhanced user experience and modern interface standards.
     * </p>
     * <p>
     * Event handler setup establishes comprehensive input validation and authentication
     * processing including login button action handling, keyboard navigation support
     * with Enter key authentication, real-time input validation, and registration
     * navigation functionality.
     * </p>
     * <p>
     * Navigation integration includes registration button configuration for seamless
     * transition to user registration, proper resource cleanup during navigation,
     * and maintenance of interface state consistency throughout navigation operations.
     * </p>
     * <p>
     * Responsive layout configuration includes dynamic window resizing handlers that
     * maintain optimal interface appearance across different screen configurations,
     * window states, and user interaction scenarios through comprehensive component
     * and window state listeners.
     * </p>
     * <p>
     * The constructor completes by adding the login screen instance to the calling
     * objects hierarchy for proper resource management and navigation state tracking
     * throughout the application lifecycle.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management
     * @param controller the system controller providing access to authentication services and user management functionality
     * @param startingSize the initial window dimensions for the login interface display
     */
    public LogInScreen(List<DisposableObject> callingObjects, Controller controller, Dimension startingSize) {

        if (!callingObjects.isEmpty()) {
            int size = callingObjects.size();

            for (int i = size - 1; i >= 0; i--) {
                callingObjects.get(i).doOnDispose(callingObjects, controller);
            }
            callingObjects.clear();

            this.setMainFrame(callingObjects, controller, startingSize);
        }
        callingObjects.addLast(this);
        //some additional manual setup
        loginMenuScrollContainer.getVerticalScrollBar().setUnitIncrement(4);
        passwordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true;");
        resizeFrame();


        //todo: ELIMINA QUESTE DUE RIGHE
        usernameTextField.setText("customer_user1");
        passwordField.setText("Customer_User1");
        //usernameTextField.setText("admin_user1");
        //passwordField.setText("Admin_User1");

        //Logging in logic
        logInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!passwordField.isValidPassword()) {
                    passwordField.showInvalidPasswordMessage(logInButton);
                } else if(controller.verifyUser(usernameTextField.getText(), passwordField.getHashedPassword(), logInButton)) {
                    login(callingObjects, controller);
                }
            }
        });

        usernameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                toggleLoginButton();
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!passwordField.isValidPassword()) {
                        passwordField.showInvalidPasswordMessage(logInButton);
                    } else if(controller.verifyUser(usernameTextField.getText(), passwordField.getHashedPassword(), logInButton)) {
                        login(callingObjects, controller);
                    }
                }
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                toggleLoginButton();
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!passwordField.isValidPassword()) {
                        passwordField.showInvalidPasswordMessage(logInButton);
                    } else if(controller.verifyUser(usernameTextField.getText(), passwordField.getHashedPassword(), logInButton)) {
                        login(callingObjects, controller);
                    }
                }
            }
        });

        //Go to Register Screen
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.setVisible(false);
                new RegisterScreen(callingObjects, controller, mainFrame.getSize());
                doOnDispose(callingObjects, controller);
                mainFrame.dispose();
            }
        });


        //resizing logic
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
    }

    /**
     * Application entry point providing complete system initialization and login interface startup.
     * <p>
     * This static method serves as the main entry point for the airport management system,
     * providing comprehensive application initialization including look-and-feel configuration,
     * controller system setup, and primary login interface establishment. The method handles
     * all aspects of application startup from UI framework initialization to main window display.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Controller Initialization:</strong> Primary system controller creation for business logic coordination</li>
     *   <li><strong>Look-and-Feel Configuration:</strong> Modern UI framework setup with fallback handling</li>
     *   <li><strong>Error Handling:</strong> Graceful degradation for unsupported UI frameworks with user notification</li>
     *   <li><strong>Window Establishment:</strong> Main application window creation with proper configuration</li>
     *   <li><strong>Interface Integration:</strong> Login screen integration with window content and lifecycle management</li>
     *   <li><strong>Display Activation:</strong> Final window sizing and visibility activation for user interaction</li>
     * </ul>
     * <p>
     * Controller initialization creates the primary {@link Controller} instance that coordinates
     * all business logic operations throughout the application lifecycle. This controller provides
     * access to authentication services, user management, and all system functionality required
     * by the login interface and subsequent application components.
     * </p>
     * <p>
     * Look-and-feel configuration attempts to establish FlatLightLaf for modern, professional
     * appearance with enhanced usability features. The system provides comprehensive error
     * handling for scenarios where FlatLaf is not supported by the target platform.
     * </p>
     * <p>
     * Error handling includes user-friendly dialog presentation when FlatLaf is unavailable,
     * offering options to continue with system default look-and-feel or exit the application.
     * This ensures graceful degradation while maintaining application functionality across
     * different system configurations and Java runtime environments.
     * </p>
     * <p>
     * Window establishment creates the main application frame with "LogIn" title and configures
     * it with the login screen content pane. The window includes proper sizing through pack()
     * method to ensure optimal layout based on content requirements and screen characteristics.
     * </p>
     * <p>
     * Interface integration establishes the complete login interface as the window content,
     * ensuring proper event handling, component initialization, and navigation hierarchy
     * establishment for subsequent application operations and user interactions.
     * </p>
     * <p>
     * Display activation includes final window configuration with default close operation,
     * optimal sizing through pack() method, and visibility activation to present the
     * login interface to users for immediate authentication access.
     * </p>
     * <p>
     * The main method provides complete standalone application capability, enabling direct
     * execution of the airport management system with full functionality and proper
     * initialization of all required system components and services.
     * </p>
     *
     * @param args command-line arguments passed to the application (currently unused but reserved for future configuration options)
     */
    public static void main(String[] args) {
        Controller controller = new Controller();
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            String[] options = {"Continua", "Chiudi"};
            int action = JOptionPane.showOptionDialog(null, "<html><center>Il tuo device non supporta FlatLaf,<br>" +
                            "utilizzerai un'altra versione dell'app,<br>" +
                            "tutte le funzioni rimarranno invariate.</center></html>",
                    "Errore nel caricamento grafica", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null);
            if (action == 1 || action == JOptionPane.CLOSED_OPTION) {
                return;
            }
        }

        mainFrame = new JFrame("LogIn");
        mainFrame.setContentPane(new LogInScreen(new ArrayList<>(), controller, mainFrame.getPreferredSize()).loginScreenPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    /**
     * Initializes and configures a new main application window for login interface display.
     * <p>
     * This method establishes a fresh main application window during navigation transitions,
     * typically when accessing the login screen from other application interfaces such as
     * registration screens or session timeout scenarios. The method ensures proper window
     * configuration with login content integration and immediate visibility activation.
     * </p>
     * <p>
     * Window configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Frame Creation:</strong> New JFrame establishment with "LogIn" title for clear identification</li>
     *   <li><strong>Size Configuration:</strong> Window sizing based on provided starting dimensions</li>
     *   <li><strong>Content Integration:</strong> Login screen content pane assignment with proper component hierarchy</li>
     *   <li><strong>Lifecycle Management:</strong> Default close operation configuration for application termination</li>
     *   <li><strong>Visibility Activation:</strong> Immediate window display for user interaction</li>
     * </ul>
     * <p>
     * Frame creation establishes a new JFrame instance with appropriate title identification,
     * replacing any existing static frame reference to ensure clean window state during
     * navigation transitions and interface reinitializations.
     * </p>
     * <p>
     * Size configuration applies the provided starting dimensions to maintain consistency
     * with previous interface sizing or user preferences. The sizing ensures optimal
     * display characteristics while preparing for responsive layout adjustments.
     * </p>
     * <p>
     * Content integration creates a new LoginScreen instance with proper parameter passing
     * and assigns its loginScreenPanel as the window content pane. This ensures complete
     * interface functionality with proper event handling and component initialization.
     * </p>
     * <p>
     * Lifecycle management configures the window with EXIT_ON_CLOSE behavior, ensuring
     * proper application termination when users close the login window. This provides
     * expected application behavior and proper resource cleanup during closure.
     * </p>
     * <p>
     * Visibility activation immediately displays the configured window, enabling users
     * to begin authentication processes without additional interaction or delay. The
     * activation ensures seamless transition from previous interfaces.
     * </p>
     * <p>
     * The method supports navigation workflows where login interface access is required
     * from other application areas, maintaining proper resource management and interface
     * state consistency throughout navigation operations.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper resource management
     * @param controller the system controller providing access to authentication services and application functionality
     * @param startingSize the initial window dimensions for consistent sizing across navigation transitions
     */
    private void setMainFrame(List<DisposableObject> callingObjects, Controller controller, Dimension startingSize) {
        mainFrame = new JFrame("LogIn");
        mainFrame.setSize(startingSize);
        mainFrame.setContentPane(new LogInScreen(callingObjects, controller, startingSize).loginScreenPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainFrame.setVisible(true);
    }

    /**
     * Processes successful authentication and navigates to appropriate home interface based on user role.
     * <p>
     * This method handles the complete authentication success workflow including credential cleanup,
     * interface transition preparation, and role-based navigation to appropriate home interfaces.
     * The method ensures secure session handling and provides seamless transition to operational
     * interfaces based on authenticated user privileges and system roles.
     * </p>
     * <p>
     * The authentication success process includes:
     * </p>
     * <ul>
     *   <li><strong>Credential Cleanup:</strong> Secure clearing of input fields to prevent credential exposure</li>
     *   <li><strong>Interface State Reset:</strong> Login button disabling and visual state preparation for transition</li>
     *   <li><strong>Window Management:</strong> Login interface hiding in preparation for home interface display</li>
     *   <li><strong>Role-Based Navigation:</strong> Automatic redirection to appropriate interface based on user privileges</li>
     *   <li><strong>Session Initialization:</strong> Proper session establishment for continued application use</li>
     * </ul>
     * <p>
     * Credential cleanup includes immediate clearing of both username and password fields
     * to prevent credential exposure during interface transitions. This security measure
     * ensures that sensitive authentication information is not left visible in the
     * interface after successful authentication completion.
     * </p>
     * <p>
     * Interface state reset disables the login button to provide visual feedback about
     * authentication completion and prevent duplicate authentication attempts during
     * the transition process. This ensures clean interface state during navigation.
     * </p>
     * <p>
     * Window management hides the main login frame in preparation for home interface
     * display, ensuring smooth visual transition without interface overlap or
     * flickering during the navigation process.
     * </p>
     * <p>
     * Role-based navigation leverages the controller's user role detection to automatically
     * redirect users to appropriate interfaces: administrators access {@link HomePageAdmin}
     * for system management capabilities, while customers access {@link HomePageCustomer}
     * for booking and flight information services.
     * </p>
     * <p>
     * Session initialization ensures that authenticated user sessions are properly
     * established and available to subsequent interfaces for continued application
     * functionality and user-specific feature access throughout system usage.
     * </p>
     * <p>
     * The method provides secure, efficient authentication completion with proper
     * resource management and seamless transition to operational interfaces based
     * on user roles and system permissions.
     * </p>
     *
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper session management
     * @param controller the system controller providing user role detection and session management functionality
     */
    private void login(List<DisposableObject> callingObjects, Controller controller) {
        usernameTextField.setText("");
        passwordField.setText("");
        logInButton.setEnabled(false);
        mainFrame.setVisible(false);
        if(controller.isLoggedAdmin()){
            new HomePageAdmin(callingObjects, controller);
        }else{
            new HomePageCustomer(callingObjects, controller);
        }

    }

    /**
     * Dynamically adjusts interface layout and padding to maintain optimal appearance across different screen configurations.
     * <p>
     * This method implements sophisticated responsive design algorithms that maintain consistent
     * login menu dimensions while adapting outer padding to accommodate various screen sizes,
     * window states, and display configurations. The method ensures optimal visual presentation
     * and usability across different hardware configurations and user preferences.
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
     * Screen analysis utilizes Toolkit.getDefaultToolkit().getScreenSize() to obtain current
     * screen dimensions and calculate optimal window sizing. The algorithm determines desired
     * height as 2/3 of screen height and width as 7/10 of the calculated height to maintain
     * consistent aspect ratios and optimal visual presentation.
     * </p>
     * <p>
     * Dimension calculation ensures that the login interface maintains appropriate sizing
     * relative to screen characteristics while preserving usability and visual appeal.
     * The preferred size is set on the main frame to guide layout calculations and
     * window management operations.
     * </p>
     * <p>
     * Padding computation calculates horizontal and vertical padding values to center
     * the login content within the available window space. The calculations include
     * minimum padding constraints (36 pixels vertical, 27 pixels horizontal) to
     * ensure adequate spacing even in constrained display scenarios.
     * </p>
     * <p>
     * Layout updates apply calculated padding values to all four padding panels using
     * IntelliJ GridLayoutManager with computed insets. This ensures that the central
     * login menu maintains consistent positioning and the intended 480x320 dimensions
     * across various window configurations and states.
     * </p>
     * <p>
     * Aspect ratio maintenance ensures that the login interface presents consistently
     * across different screen resolutions, window states (normal, maximized, minimized),
     * and user interactions (resizing, moving) while preserving visual design integrity
     * and usability standards.
     * </p>
     * <p>
     * The method is called automatically during various interface events including
     * window showing, resizing, moving, and state changes to maintain optimal
     * appearance throughout user interaction and system operation.
     * </p>
     */
    private void resizeFrame() {
        /*the formulas make it so that the loginMenu is always 480 by 320
         /Dimension - 2paddingDimension = desiredDimension -> paddingDimension = Dimension/2 - desiredDimension/2
         /there are some adjustments because of the border and the decorator
         /the actual dimension of the loginMenufluctuate a little (max 2 in either direction)
         /(not only because of parity, but I have no idea what is it other than that)
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
     * Manages login button state based on input field completion to provide real-time user feedback.
     * <p>
     * This method implements dynamic button state management that enables the login button only
     * when both username and password fields contain input, providing immediate visual feedback
     * about authentication readiness. The method enhances user experience by clearly indicating
     * when authentication can be attempted and preventing premature authentication attempts.
     * </p>
     * <p>
     * Button state management includes:
     * </p>
     * <ul>
     *   <li><strong>Input Validation:</strong> Real-time checking of both username and password field completion</li>
     *   <li><strong>State Synchronization:</strong> Button enabled state synchronized with input field content</li>
     *   <li><strong>User Feedback:</strong> Visual indication of authentication readiness through button appearance</li>
     *   <li><strong>Interaction Prevention:</strong> Prevention of authentication attempts with incomplete credentials</li>
     * </ul>
     * <p>
     * Input validation checks both the username text field for non-empty content and the
     * password field for non-zero length input. The validation ensures that both required
     * authentication fields contain user input before enabling authentication functionality.
     * </p>
     * <p>
     * State synchronization immediately updates the login button enabled state based on
     * input field validation results. The button is enabled only when both fields contain
     * input, providing clear visual feedback about authentication readiness and system state.
     * </p>
     * <p>
     * User feedback is provided through standard button visual states where disabled buttons
     * appear grayed out and enabled buttons show normal interactive appearance. This provides
     * intuitive feedback about system readiness for authentication attempts.
     * </p>
     * <p>
     * Interaction prevention ensures that users cannot attempt authentication with incomplete
     * credentials, reducing error scenarios and providing clearer user experience during
     * the authentication process. This prevents unnecessary authentication attempts and
     * associated error handling scenarios.
     * </p>
     * <p>
     * The method is called automatically during key release events in both input fields,
     * ensuring immediate response to user input changes and maintaining current button
     * state throughout user interaction with the authentication interface.
     * </p>
     */
    private void toggleLoginButton(){
        logInButton.setEnabled(!usernameTextField.getText().isEmpty() && passwordField.getPassword().length != 0);
    }

    /**
     * Provides access to the main application window frame for navigation and resource management.
     * <p>
     * This method returns the main JFrame instance that contains the login interface,
     * enabling external components to perform window management operations such as sizing,
     * positioning, visibility control, and resource management. The method supports the
     * DisposableObject pattern by providing frame access for navigation operations.
     * </p>
     * <p>
     * Frame access enables:
     * </p>
     * <ul>
     *   <li><strong>Navigation Operations:</strong> Window management during interface transitions</li>
     *   <li><strong>Resource Management:</strong> Frame access for proper disposal and cleanup operations</li>
     *   <li><strong>State Management:</strong> Window state preservation and restoration during navigation</li>
     *   <li><strong>Display Control:</strong> Visibility and positioning management for optimal user experience</li>
     * </ul>
     * <p>
     * The returned frame reference provides access to standard JFrame operations including
     * visibility control, sizing, positioning, and state management that are essential
     * for proper navigation system integration and authentication workflow support.
     * </p>
     *
     * @return the main JFrame instance containing the login interface
     */
    @Override
    public JFrame getFrame() {
        return mainFrame;
    }

}