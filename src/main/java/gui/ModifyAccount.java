package gui;

import com.formdev.flatlaf.FlatClientProperties;
import controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Modal dialog interface for comprehensive user account modification and management in the airport management system.
 * <p>
 * This class extends {@link JDialog} to provide a dedicated modal interface for user account management
 * operations including profile updates, password changes, and account deletion. The dialog supports both
 * customer and administrative user accounts with role-specific functionality and validation, ensuring secure
 * and comprehensive account management capabilities throughout the airport management system.
 * </p>
 * <p>
 * The ModifyAccount class provides comprehensive account management functionality including:
 * </p>
 * <ul>
 *   <li><strong>Profile Updates:</strong> Username and email address modification with uniqueness validation</li>
 *   <li><strong>Password Management:</strong> Secure password changes with current password verification</li>
 *   <li><strong>Account Deletion:</strong> Secure account removal with confirmation dialogs and authentication</li>
 *   <li><strong>Role-Based Interface:</strong> Different functionality and restrictions based on user type</li>
 *   <li><strong>Real-Time Validation:</strong> Comprehensive input validation with immediate user feedback</li>
 *   <li><strong>Security Integration:</strong> Password verification and hashing for all sensitive operations</li>
 * </ul>
 * <p>
 * The interface is designed with security and user experience optimization, providing users with:
 * </p>
 * <ul>
 *   <li><strong>Intuitive Layout:</strong> Clean, professional form layout with clear field organization</li>
 *   <li><strong>Progressive Disclosure:</strong> Optional new password field for users who want to keep existing passwords</li>
 *   <li><strong>Immediate Feedback:</strong> Real-time validation and error messaging through floating messages</li>
 *   <li><strong>Security Verification:</strong> Current password requirement for all modification operations</li>
 *   <li><strong>Confirmation Dialogs:</strong> Additional confirmation for destructive operations like account deletion</li>
 * </ul>
 * <p>
 * Layout architecture utilizes {@link GridBagLayout} for precise component positioning and optimal
 * space utilization. The dialog maintains a structured three-section layout with title area,
 * main form containing user input fields, and action button area for operation confirmation.
 * </p>
 * <p>
 * Role-based functionality provides different interface behaviors based on user type:
 * </p>
 * <ul>
 *   <li><strong>Customer Users:</strong> Full profile management including username, email, and password changes</li>
 *   <li><strong>Administrative Users:</strong> Profile management with email field restrictions (read-only email)</li>
 *   <li><strong>Security Enforcement:</strong> Current password verification required for all users regardless of role</li>
 * </ul>
 * <p>
 * Validation integration provides comprehensive input validation including username uniqueness
 * checking, email format validation, password strength requirements, and current password
 * verification. All validation operations provide immediate user feedback through {@link FloatingMessage}
 * components with clear error descriptions and guidance.
 * </p>
 * <p>
 * Security features include mandatory current password verification for all operations, secure
 * password hashing, and confirmation dialogs for destructive operations. The interface ensures
 * that unauthorized account modifications are prevented while providing legitimate users with
 * comprehensive account management capabilities.
 * </p>
 * <p>
 * The dialog integrates seamlessly with the broader airport management system through the
 * {@link Controller} interface, ensuring proper data persistence, business logic execution,
 * and navigation management throughout account modification workflows.
 * </p>
 * <p>
 * Visual design maintains consistency with the overall airport management system aesthetic
 * through standardized colors, fonts, and component styling. The interface uses professional
 * blue tones for confirmation actions and warning red tones for destructive operations.
 * </p>
 * <p>
 * Resource management follows standard Swing dialog patterns with proper modal behavior,
 * parent window association, and automatic dialog disposal after successful operations
 * or user cancellation.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JDialog
 * @see Controller
 * @see PasswordHandler
 * @see FloatingMessage
 * @see DisposableObject
 * @see GridBagLayout
 */
public class ModifyAccount extends JDialog {

    /**
     * Main container panel for all dialog components with structured layout management.
     * <p>
     * This panel serves as the primary container for all dialog elements including
     * the title, form fields, and action buttons. The panel uses GridBagLayout
     * for precise component positioning and includes proper padding and background
     * styling for professional appearance.
     * </p>
     */
    private final JPanel mainPanel;
    
    /**
     * Text field for username input and modification.
     * <p>
     * This field allows users to view and modify their current username.
     * The field is pre-populated with the current username and includes
     * validation to ensure uniqueness across the user base during modification.
     * </p>
     */
    private JTextField usernameTextField;
    
    /**
     * Text field for email address input and modification.
     * <p>
     * This field enables email address updates for customer accounts.
     * For administrative users, this field is set to read-only mode
     * as email modifications are restricted for administrative accounts
     * for security and operational consistency.
     * </p>
     */
    private JTextField mailTextField;
    
    /**
     * Password input field for new password entry with security features.
     * <p>
     * This specialized password field allows users to enter a new password
     * when they wish to change their current password. The field includes
     * show/hide functionality and comprehensive password strength validation
     * to ensure secure password selection.
     * </p>
     */
    private PasswordHandler newPasswordField;
    
    /**
     * Password input field for current password verification.
     * <p>
     * This field requires users to enter their current password for
     * authentication before any account modifications can be processed.
     * The field includes show/hide functionality and is mandatory for
     * all modification operations to ensure account security.
     * </p>
     */
    private PasswordHandler oldPasswordField;

    /**
     * Primary action button for confirming and processing account modifications.
     * <p>
     * This button triggers the account update process after validating
     * all input fields and verifying the current password. The button
     * uses professional blue styling to indicate a primary action and
     * includes comprehensive validation before processing modifications.
     * </p>
     */
    private JButton confirmButton;
    
    /**
     * Destructive action button for account deletion operations.
     * <p>
     * This button initiates the account deletion process with additional
     * confirmation dialogs and security verification. The button uses
     * warning red styling to indicate a destructive action and requires
     * current password verification before processing deletion.
     * </p>
     */
    private JButton deleteButton;

    /**
     * Layout constraints utility for precise component positioning throughout the dialog.
     * <p>
     * This helper object provides standardized GridBagConstraints configuration
     * for optimal component layout and positioning. The constraints ensure
     * consistent spacing, alignment, and visual organization across all
     * dialog components and form elements.
     * </p>
     */
    private final Constraints constraints = new Constraints();

    /**
     * Original username value for change detection and validation purposes.
     * <p>
     * This field stores the user's original username to enable change detection
     * and provide fallback functionality. The value is used during validation
     * to determine if username modifications are being attempted and to allow
     * users to keep their existing username during other profile updates.
     * </p>
     */
    String oldUsername = null;
    
    /**
     * Original email address value for change detection and validation purposes.
     * <p>
     * This field maintains the user's original email address for change detection
     * and validation operations. The value enables the system to determine if
     * email modifications are being attempted and supports validation logic
     * that allows users to retain their existing email address.
     * </p>
     */
    String oldMail = null;

    /**
     * Constructs a new ModifyAccount dialog for comprehensive user account management.
     * <p>
     * This constructor initializes the complete account modification interface by creating
     * the dialog window, configuring all form components, and establishing comprehensive
     * event handling for account management operations. The constructor creates a fully
     * functional modal dialog ready for immediate user interaction with integrated
     * validation, security verification, and navigation management capabilities.
     * </p>
     * <p>
     * The initialization process includes:
     * </p>
     * <ul>
     *   <li><strong>Dialog Configuration:</strong> Modal dialog setup with proper title, parent association, and window properties</li>
     *   <li><strong>Container Setup:</strong> Main panel creation with GridBagLayout and professional styling</li>
     *   <li><strong>Title Integration:</strong> Centered dialog title with appropriate typography and positioning</li>
     *   <li><strong>Form Creation:</strong> Complete form setup with username, email, and password fields</li>
     *   <li><strong>Button Configuration:</strong> Action button setup with comprehensive event handling</li>
     *   <li><strong>Window Management:</strong> Final dialog properties including size, position, and focus management</li>
     * </ul>
     * <p>
     * Dialog configuration establishes the modal dialog with "Modifica Account" title
     * and proper parent window association for correct window management and focus behavior.
     * The modal nature ensures that users must complete or cancel the account modification
     * before returning to the parent interface.
     * </p>
     * <p>
     * Container setup creates the main panel with GridBagLayout for precise component
     * positioning and applies professional styling including background color (240, 242, 245)
     * and appropriate padding (20, 30, 20, 30) for optimal visual presentation and
     * component spacing throughout the dialog.
     * </p>
     * <p>
     * Title integration includes creating and positioning the dialog title with "Modifica Account"
     * text using large, bold typography (Segoe UI, 22pt) and center alignment for clear
     * dialog identification and professional appearance consistent with application design.
     * </p>
     * <p>
     * Form creation leverages the addMainForm method to establish all user input fields
     * including username, email, and password fields with proper labeling, validation,
     * and role-based restrictions. The form includes current user information pre-population
     * for convenient modification workflows.
     * </p>
     * <p>
     * Button configuration includes both confirmation and deletion buttons through dedicated
     * setup methods that establish comprehensive event handling, security validation, and
     * user feedback systems for all account modification operations.
     * </p>
     * <p>
     * Window management includes final dialog properties configuration with content pane
     * assignment, automatic sizing (pack), center positioning relative to parent, non-resizable
     * behavior, and initial focus management for optimal user experience and interaction.
     * </p>
     * <p>
     * The constructor completes by establishing a fully functional account modification
     * dialog that provides secure, comprehensive account management capabilities while
     * maintaining integration with the broader airport management system navigation
     * and user management infrastructure.
     * </p>
     *
     * @param owner the parent Frame window for modal dialog behavior and proper window management
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow management
     * @param controller the system controller providing access to user management, validation, and navigation functionality
     */
    public ModifyAccount(Frame owner, List<DisposableObject> callingObjects, Controller controller) {

        super(owner, "Modifica Account", true);

        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(new Color(240, 242, 245));

        //Setup for Title
        JLabel titleLabel = new JLabel("Modifica Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        constraints.setConstraints(0, 0, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(0, 0, 16, 0));
        mainPanel.add(titleLabel, constraints.getGridBagConstraints());

        this.addMainForm(controller);

        setConfirmButton(controller, callingObjects);

        setDeleteButton(controller, callingObjects);

        this.setContentPane(mainPanel);
        this.pack();
        this.setLocationRelativeTo(owner);
        this.setResizable(false);
        this.getContentPane().requestFocusInWindow();


    }

    /**
     * Creates and configures the account deletion button with comprehensive security verification.
     * <p>
     * This private method establishes the destructive action button for account deletion operations
     * with comprehensive security measures, confirmation dialogs, and proper event handling. The
     * method creates a visually distinct button with warning styling and implements multi-level
     * security verification to ensure safe account deletion operations.
     * </p>
     * <p>
     * Deletion button configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Visual Design:</strong> Warning red styling with appropriate typography and cursor behavior</li>
     *   <li><strong>Security Verification:</strong> Current password requirement and validation</li>
     *   <li><strong>Confirmation Dialog:</strong> Additional user confirmation before irreversible deletion</li>
     *   <li><strong>Error Handling:</strong> Comprehensive validation with clear user feedback</li>
     *   <li><strong>Navigation Management:</strong> Automatic redirect to login after successful deletion</li>
     * </ul>
     * <p>
     * Visual design utilizes warning red background (220, 0, 0) with white text to clearly
     * indicate the destructive nature of the operation. The button includes appropriate sizing,
     * typography, and hand cursor for professional interaction design.
     * </p>
     * <p>
     * Security verification includes mandatory current password entry validation and comparison
     * with the user's stored password hash. The verification prevents unauthorized account
     * deletions and ensures that only authenticated users can delete their accounts.
     * </p>
     * <p>
     * Confirmation dialog implementation provides an additional safety layer with clear warning
     * message about the irreversible nature of account deletion. The dialog uses Italian
     * localization with "Annulla" (Cancel) and "Elimina account" (Delete Account) options.
     * </p>
     * <p>
     * Error handling provides specific feedback for validation failures including empty password
     * fields and incorrect password entries through {@link FloatingMessage} components with
     * clear error descriptions and user guidance.
     * </p>
     * <p>
     * Navigation management ensures that successful account deletions trigger proper cleanup
     * and redirect users to the login interface through the controller's navigation system,
     * preventing orphaned sessions and maintaining application state consistency.
     * </p>
     *
     * @param controller the system controller providing access to user management and navigation functionality
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow management
     */
    private void setDeleteButton(Controller controller, List<DisposableObject> callingObjects){
        deleteButton = new JButton("Elimina Account");
        deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 16));


        deleteButton.setBackground(new Color(220, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);

        deleteButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteButton.setPreferredSize(new Dimension(200, 40));

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(oldPasswordField.isEmpty()){
                    new FloatingMessage("<html>Bisogna inserire la password</html>", confirmButton, FloatingMessage.ERROR_MESSAGE);
                    return;
                }
                if (!oldPasswordField.getHashedPassword().equals(controller.getUserController().getHashedPassword())) {
                    new FloatingMessage("<html>Password errata</html>", confirmButton, FloatingMessage.ERROR_MESSAGE);
                }
                else{
                    String[] options = {"Annulla", "Elimina account"};
                    int action = JOptionPane.showOptionDialog(mainPanel, "<html>Sei sicuro di voler eliminare il tuo account?<br>" +
                                    "Questa azione non può è reversibile </html>", "Elimina account", JOptionPane.DEFAULT_OPTION,
                            JOptionPane.WARNING_MESSAGE, null, options, null);
                    if(action == 1){
                        controller.getUserController().deleteAccount(deleteButton);
                        controller.goToLogin(callingObjects);
                    }
                }
            }
        });

        constraints.setConstraints(0, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.PAGE_END, 1.0f, 0.0f, new Insets(16, 0, 0, 0));
        mainPanel.add(deleteButton, constraints.getGridBagConstraints());
    }

    /**
     * Creates and configures the confirmation action button with comprehensive validation and update processing.
     * <p>
     * This private method establishes the primary action button for account modification confirmation
     * with comprehensive input validation, security verification, and update processing. The method
     * creates a professionally styled button with complete event handling for all account modification
     * scenarios including username changes, email updates, and password modifications.
     * </p>
     * <p>
     * Confirmation button configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Professional Styling:</strong> Primary blue color scheme with appropriate typography and interaction design</li>
     *   <li><strong>Security Verification:</strong> Mandatory current password validation for all modification operations</li>
     *   <li><strong>Flexible Processing:</strong> Support for password-only updates and comprehensive profile modifications</li>
     *   <li><strong>Validation Integration:</strong> Password strength validation and uniqueness checking</li>
     *   <li><strong>Navigation Management:</strong> Automatic return to home interface after successful updates</li>
     * </ul>
     * <p>
     * Professional styling utilizes primary blue background (0, 120, 215) with white text to
     * indicate the primary action nature of the button. The styling includes appropriate sizing,
     * typography (Segoe UI, 16pt bold), and hand cursor for optimal user interaction experience.
     * </p>
     * <p>
     * Security verification requires current password entry and validation against the stored
     * password hash for all modification operations. This security measure prevents unauthorized
     * account changes and ensures that only authenticated users can modify account information.
     * </p>
     * <p>
     * Flexible processing accommodates two primary modification scenarios: users who want to
     * update profile information without changing passwords (empty new password field) and
     * users who want to change passwords along with other profile updates. Both scenarios
     * require current password verification.
     * </p>
     * <p>
     * Validation integration includes password strength validation through the PasswordHandler's
     * isValidPassword method and comprehensive uniqueness checking through the controller's
     * updateUser method. Invalid passwords trigger appropriate error messages with user guidance.
     * </p>
     * <p>
     * Navigation management ensures that successful account updates trigger automatic navigation
     * to the home interface and proper dialog disposal, maintaining application workflow continuity
     * and preventing interface confusion after successful operations.
     * </p>
     * <p>
     * Error handling provides specific feedback for various validation failures including empty
     * password fields, incorrect current passwords, and invalid new passwords through
     * {@link FloatingMessage} components with clear descriptions and user guidance.
     * </p>
     *
     * @param controller the system controller providing access to user management, validation, and navigation functionality
     * @param callingObjects the list of parent objects in the application navigation hierarchy for proper workflow management
     */
    private void setConfirmButton(Controller controller, List<DisposableObject> callingObjects){
        confirmButton = new JButton("Conferma");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        confirmButton.setBackground(new Color(0, 120, 215));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);

        confirmButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmButton.setPreferredSize(new Dimension(200, 40));


        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(oldPasswordField.isEmpty()){
                    new FloatingMessage("<html>Bisogna inserire la password</html>", confirmButton, FloatingMessage.ERROR_MESSAGE);
                    return;
                }
                if (!oldPasswordField.getHashedPassword().equals(controller.getUserController().getHashedPassword())) {
                    new FloatingMessage("<html>Password errata</html>", confirmButton, FloatingMessage.ERROR_MESSAGE);
                    return;
                }
                if(newPasswordField.isEmpty() ) {
                    if (controller.updateUser(mailTextField.getText(), usernameTextField.getText(),
                            oldPasswordField.getHashedPassword(), confirmButton)) {
                        controller.goHome(callingObjects);
                        dispose();
                    }
                }else if(newPasswordField.isValidPassword()){
                    if(controller.updateUser(mailTextField.getText(), usernameTextField.getText(),
                            newPasswordField.getHashedPassword(), confirmButton)){
                        controller.goHome(callingObjects);
                        dispose();
                    }
                } else{
                    newPasswordField.showInvalidPasswordMessage(confirmButton);
                }
            }});

        constraints.setConstraints(1, 2, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 0.0f, new Insets(16, 0, 0, 0));
        mainPanel.add(confirmButton, constraints.getGridBagConstraints());
    }

    /**
     * Creates and configures the main form containing all user input fields with role-based functionality.
     * <p>
     * This private method establishes the comprehensive form interface for user account modification
     * including username, email, and password fields with appropriate labeling, validation, and
     * role-based restrictions. The method creates a structured form layout with professional
     * styling and immediate integration with current user information.
     * </p>
     * <p>
     * Main form configuration includes:
     * </p>
     * <ul>
     *   <li><strong>Structured Layout:</strong> GridBagLayout-based form with precise field positioning and spacing</li>
     *   <li><strong>Current Data Population:</strong> Pre-population of fields with current user information</li>
     *   <li><strong>Role-Based Restrictions:</strong> Email field restrictions for administrative users</li>
     *   <li><strong>Security Features:</strong> Password reveal buttons and secure password handling</li>
     *   <li><strong>Accessibility Support:</strong> Proper label associations and field descriptions</li>
     * </ul>
     * <p>
     * Structured layout utilizes GridBagLayout for precise two-column form organization with
     * username and new password fields in the left column and email and current password
     * fields in the right column. The layout ensures optimal space utilization and visual balance.
     * </p>
     * <p>
     * Current data population includes retrieving and displaying current username and email
     * information from the user controller, enabling users to see their current information
     * and make informed modifications. The pre-population improves user experience and
     * reduces input requirements for unchanged fields.
     * </p>
     * <p>
     * Role-based restrictions implement different functionality based on user type: customer
     * users have full email modification capabilities while administrative users have read-only
     * email fields due to operational and security requirements for administrative accounts.
     * </p>
     * <p>
     * Security features include specialized {@link PasswordHandler} components with show/hide
     * functionality through FlatLaf reveal buttons, enabling users to verify password input
     * while maintaining security through masked display by default.
     * </p>
     * <p>
     * Accessibility support includes proper label-to-field associations through setLabelFor
     * methods and clear field labeling in Italian: "Nome utente" (Username), "Mail" (Email),
     * "Nuova password" (New Password), and "Vecchia password" (Current Password).
     * </p>
     * <p>
     * Field appearance standardization ensures consistent typography and styling across all
     * input fields through the setFieldAppearance method, maintaining visual consistency
     * and professional appearance throughout the form interface.
     * </p>
     *
     * @param controller the system controller providing access to current user information and role detection
     */
    private void addMainForm(Controller controller) {

        JPanel mainForm = new JPanel(new GridBagLayout());
        constraints.setConstraints(0, 1, 2, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f);
        mainPanel.add(mainForm, constraints.getGridBagConstraints());

        //username Label and field
        JLabel usernameLabel = new JLabel("Nome utente");
        usernameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        usernameLabel.setLabelFor(usernameTextField);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(16, 8, 0, 0));
        mainForm.add(usernameLabel, constraints.getGridBagConstraints());

        oldUsername = controller.getUserController().getUsername();
        usernameTextField = new JTextField(oldUsername, 20);
        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(0, 8, 0, 8));
        mainForm.add(usernameTextField, constraints.getGridBagConstraints());
        setFieldAppearance(usernameTextField);

        //mail Label and Field
        JLabel mailLabel = new JLabel("Mail");
        mailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mailLabel.setLabelFor(mailTextField);

        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(16, 8, 0, 0));
        mainForm.add(mailLabel, constraints.getGridBagConstraints());

        oldMail = controller.getUserController().getMail();
        mailTextField = new JTextField(oldMail, 20);
        constraints.setConstraints(1, 1, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(0, 8, 0, 8));
        mainForm.add(mailTextField, constraints.getGridBagConstraints());
        setFieldAppearance(mailTextField);

        if (controller.isLoggedAdmin()) {
            mailTextField.setEditable(false);
            mailTextField.setFocusable(false);
            mailTextField.setForeground(new Color(80, 80, 80));
        }

        //newPassword Label and Field
        JLabel newPasswordLabel = new JLabel("Nuova password");
        newPasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        newPasswordLabel.setLabelFor(newPasswordField);

        constraints.setConstraints(0, 4, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(16, 8, 0, 0));
        mainForm.add(newPasswordLabel, constraints.getGridBagConstraints());

        newPasswordField = new PasswordHandler(20);
        newPasswordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true;");
        constraints.setConstraints(0, 5, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(0, 8, 0, 8));
        mainForm.add(newPasswordField, constraints.getGridBagConstraints());
        setFieldAppearance(newPasswordField);

        //oldPassword Label and Field
        JLabel oldPasswordLabel = new JLabel("Vecchia password");
        oldPasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        oldPasswordLabel.setLabelFor(oldPasswordField);

        constraints.setConstraints(1, 4, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(16, 8, 0, 0));
        mainForm.add(oldPasswordLabel, constraints.getGridBagConstraints());

        oldPasswordField = new PasswordHandler(20);
        oldPasswordField.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true;");
        constraints.setConstraints(1, 5, 1, 1, GridBagConstraints.HORIZONTAL,
                0, 0, GridBagConstraints.PAGE_START, 1.0f, 1.0f, new Insets(0, 8, 0, 8));
        mainForm.add(oldPasswordField, constraints.getGridBagConstraints());
        setFieldAppearance(oldPasswordField);

    }

    /**
     * Applies standardized visual styling to text input fields for consistent interface appearance.
     * <p>
     * This private utility method establishes consistent typography and styling across all
     * text input fields within the account modification dialog. The method ensures that
     * all form fields maintain professional appearance and visual consistency throughout
     * the user interface.
     * </p>
     * <p>
     * Field appearance standardization includes:
     * </p>
     * <ul>
     *   <li><strong>Typography:</strong> Consistent font family and size (Segoe UI, 16pt plain)</li>
     *   <li><strong>Visual Consistency:</strong> Uniform appearance across all input field types</li>
     *   <li><strong>Professional Styling:</strong> Clean, readable typography optimized for user input</li>
     *   <li><strong>Cross-Platform Compatibility:</strong> Font selection appropriate for Windows environments</li>
     * </ul>
     * <p>
     * Typography configuration applies the Segoe UI font family at 16-point size with plain
     * (non-bold, non-italic) styling to ensure optimal readability and professional appearance
     * across all form input fields including text fields and password fields.
     * </p>
     * <p>
     * The standardization ensures that users experience consistent visual design throughout
     * the account modification process, contributing to overall interface coherence and
     * professional application appearance.
     * </p>
     *
     * @param field the JTextField instance to which standardized appearance styling should be applied
     */
    private void setFieldAppearance(JTextField field) {

        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));

    }
}