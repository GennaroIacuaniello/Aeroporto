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
 * This class extends {@link DisposableObject}.
 * </p>
 * <p>
 * The RegisterScreen class supports comprehensive registration functionality, including:
 * </p>
 * <ul>
 *   <li><strong>Multi-Field Registration:</strong> Email, username, and password collection with comprehensive validation</li>
 *   <li><strong>Advanced Password Security:</strong> Integration with PasswordHandler for secure password validation and hashing</li>
 *   <li><strong>Real-time Validation:</strong> Immediate input validation and user feedback during a registration process</li>
 *   <li><strong>Responsive Design:</strong> Dynamic sizing and layout adaptation across different screen configurations</li>
 *   <li><strong>Navigation Integration:</strong> Seamless transitions between registration and login interfaces</li>
 *   <li><strong>Modern UI Experience:</strong> FlatLaf integration with password reveal functionality and contemporary styling</li>
 * </ul>
 * <p>
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
     */
    private JPanel topPadding;
    
    /**
     * Bottom padding panel for dynamic interface spacing and responsive design.
     */
    private JPanel bottomPadding;
    
    /**
     * Left padding panel for dynamic interface spacing and responsive design.
     */
    private JPanel leftPadding;
    
    /**
     * Right padding panel for dynamic interface spacing and responsive design.
     */
    private JPanel rightPadding;

    /**
     * Main application window frame for the registration interface.
     */
    private static JFrame mainFrame;
    
    /**
     * Primary panel container for the entire registration screen layout.
     */
    private JPanel registerScreen;
    
    /**
     * Scroll container for the registration menu providing overflow handling.
     * <p>
     * This scroll pane contains the registration menu and provides scrolling capability
     * when needed, though the design typically maintains content within visible bounds.
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
     */
    private JLabel pageTitle;

    /**
     * Text field for email address input during user registration.
     */
    private JTextField mailTextField;
    
    /**
     * Label for the email input field providing user guidance.
     */
    private JLabel mailLabel;
    
    /**
     * Text field for username input during user registration.
     */
    private JTextField usernameTextField;
    
    /**
     * Label for the username input field providing user guidance.
     */
    private JLabel usernameLabel;
    
    /**
     * Specialized password field with advanced security and validation features.
     * <p>
     * This PasswordHandler component provides password management
     * including real-time validation, security requirements enforcement, and
     * secure hashing capabilities.
     * </p>
     */
    private PasswordHandler passwordField;
    
    /**
     * Label for the password input field providing user guidance.
     */
    private JLabel passwordLabel;
    
    /**
     * Primary registration button for account creation submission.
     */
    private JButton registerButton;

    /**
     * Navigation button for returning to the login interface.
     */
    private JButton loginButton;

    /**
     * Constructs a new RegisterScreen interface for user account creation and registration.
     * <p>
     * This constructor initializes the complete registration interface by establishing the main
     * application window, configuring registration components, and setting up event
     * handling for user interaction. The constructor creates a fully functional registration
     * system ready for immediate user account creation with advanced validation and security
     * features.
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
     * This private method implements responsive design algorithms that maintain consistent
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
     *
     */
    private void toggleRegisterButton(){
        registerButton.setEnabled(!usernameTextField.getText().isEmpty() && passwordField.getPassword().length != 0);
    }

    /**
     * Processes navigation transitions from registration to login interface with proper resource management.
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
     *
     * @return the main JFrame instance containing the registration interface
     */
    @Override
    public JFrame getFrame() {
        return mainFrame;
    }

}