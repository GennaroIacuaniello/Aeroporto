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
 * The ModifyAccount class provides comprehensive account management functionality, including:
 * </p>
 * <ul>
 *   <li><strong>Profile Updates:</strong> Username and email address modification with uniqueness validation</li>
 *   <li><strong>Password Management:</strong> Secure password changes with current password verification</li>
 *   <li><strong>Account Deletion:</strong> Secure account removal with confirmation dialogs and authentication</li>
 *   <li><strong>Role-Based Interface:</strong> Different functionality and restrictions based on user type</li>
 *   <li><strong>Real-Time Validation:</strong> Comprehensive input validation with immediate user feedback</li>
 *   <li><strong>Security Integration:</strong> Password verification and hashing for all sensitive operations</li>
 * </ul>
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
     * The main container panel for all dialog components with structured layout management.
     * <p>
     * This panel serves as the primary container for all dialog elements, including
     * the title, form fields, and action buttons. The panel uses GridBagLayout
     * for precise component positioning and includes proper padding and background
     * styling for professional appearance.
     * </p>
     */
    private final JPanel mainPanel;
    
    /**
     * Text field for username input and modification.
     */
    private JTextField usernameTextField;
    
    /**
     * Text field for email address input and modification.
     */
    private JTextField mailTextField;
    
    /**
     * Password input field for a new password entry with security features.
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
     */
    private JButton confirmButton;
    
    /**
     * Destructive action button for account deletion operations.
     */
    private JButton deleteButton;

    /**
     * Layout constraints utility for precise component positioning throughout the dialog.
     */
    private final Constraints constraints = new Constraints();

    /**
     * Original username value for change detection and validation purposes.
     */
    String oldUsername = null;
    
    /**
     * Original email address value for change detection and validation purposes.
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
     *  scenarios, including username changes, email updates, and password modifications.
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
     *
     * @param field the JTextField instance to which standardized appearance styling should be applied
     */
    private void setFieldAppearance(JTextField field) {

        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));

    }
}