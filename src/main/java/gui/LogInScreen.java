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
 * This class extends {@link DisposableObject}.
 * </p>
 * <p>
 * The LogInScreen class supports comprehensive authentication functionality, including:
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
     */
    private JPanel topPadding;
    
    /**
     * Bottom padding panel for dynamic interface spacing.
     */
    private JPanel bottomPadding;
    
    /**
     * Left padding panel for dynamic interface spacing.
     */
    private JPanel leftPadding;
    
    /**
     * Right padding panel for dynamic interface spacing.
     */
    private JPanel rightPadding;

    /**
     * Main application window frame for the login interface.
     */
    private static JFrame mainFrame;
    
    /**
     * Primary panel container for the entire login screen layout.
     */
    private JPanel loginScreenPanel;
    
    /**
     * Scroll container for the login menu providing overflow handling.
     * <p>
     * This scroll pane contains the login menu and provides scrolling capability
     * when needed, though the design typically maintains content within visible
     * bounds.
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
     */
    private JLabel pageTitle;

    /**
     * Text field for username or email input during authentication.
     */
    private JTextField usernameTextField;
    
    /**
     * Label for the username input field providing user guidance.
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
     */
    private JLabel passwordLabel;
    
    /**
     * Primary login button for authentication processing.
     */
    private JButton logInButton;

    /**
     * Registration navigation button for new user account creation.
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
     */
    private void toggleLoginButton(){
        logInButton.setEnabled(!usernameTextField.getText().isEmpty() && passwordField.getPassword().length != 0);
    }

    /**
     * Provides access to the main application window frame for navigation and resource management.
     *
     * @return the main JFrame instance containing the login interface
     */
    @Override
    public JFrame getFrame() {
        return mainFrame;
    }

}