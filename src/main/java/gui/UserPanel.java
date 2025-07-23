package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The UserPanel class provides a user greeting and account management interface.
 * This panel displays a greeting with the current user's username and provides a popup menu
 * with options for logging out and modifying account details. It's typically displayed
 * in the corner of the application to show the current user's session information.
 */
public class UserPanel extends JPanel {


    /**
     * The username of the currently logged-in user.
     * This is displayed in the greeting message.
     */
    private String userGreeted;

    /**
     * The popup menu that appears when the user button is clicked.
     * Contains options for account management and logout.
     */
    JPopupMenu popupMenu;

    /**
     * The menu item currently being configured.
     * Used as a temporary reference during menu construction.
     */
    JMenuItem menuItem;

    /**
     * Instantiates a new User panel with the specified context.
     * This constructor sets up the panel with a GridBagLayout, creates the popup menu
     * for user account management, and configures the user greeting button.
     *
     * @param callingObjects the list of disposable objects in the application hierarchy,
     *                       used for navigation when logging out or modifying account
     * @param controller     the main controller that manages application state and business logic
     */
    public UserPanel(List<DisposableObject> callingObjects, Controller controller) {

        super();

        this.setLayout(new GridBagLayout());

        setPopupMenu (controller, callingObjects);

        setUserButton(controller);
    }

    /**
     * Creates and configures the popup menu for user account management.
     * This method adds menu items for logging out and modifying account details,
     * and sets up their respective action listeners.
     *
     * @param controller     the main controller that handles application logic and navigation
     * @param callingObjects the list of disposable objects in the application hierarchy,
     *                       used for navigation when logging out or modifying account
     */
    private void setPopupMenu (Controller controller, List<DisposableObject> callingObjects) {

        popupMenu = new JPopupMenu();
        menuItem = new JMenuItem("Logout");

        menuItem.addActionListener(actionEvent ->

            controller.logOut(callingObjects)
        );

        popupMenu.add(menuItem);

        menuItem = new JMenuItem("Modifica account");

        menuItem.addActionListener(actionEvent -> {

            Frame parent = (Frame) SwingUtilities.getWindowAncestor(this);
            ModifyAccount dialog = new ModifyAccount(parent, callingObjects, controller);
            dialog.setVisible(true);
        });

        popupMenu.add(menuItem);
    }

    /**
     * Creates and configures the user greeting button.
     * This method retrieves the current user's username from the controller,
     * creates a button with a personalized greeting, and configures it to show
     * the popup menu when clicked.
     *
     * @param controller the main controller that provides access to the current user's information
     */
    private void setUserButton (Controller controller) {
        JButton userButton;

        userGreeted = controller.getUserController().getUsername();
        userButton = new JButton("<html>Ciao,<br>" + userGreeted + "</html>");
        userButton.addActionListener(e ->

            popupMenu.show(userButton, 0, userButton.getHeight())
        );

        this.add(userButton);
    }

    /**
     * Gets the username of the currently logged-in user.
     * This method returns the username that is displayed in the greeting message.
     * It can be used by other components to access the current user's display name.
     *
     * @return the username of the currently logged-in user
     */
    public String getUserGreeted() {
        return userGreeted;
    }

}
