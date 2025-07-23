package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The type User panel.
 */
public class UserPanel extends JPanel {


    private String userGreeted;
    /**
     * The Popup menu.
     */
    JPopupMenu popupMenu;
    /**
     * The Menu item.
     */
    JMenuItem menuItem;

    /**
     * Instantiates a new User panel.
     *
     * @param callingObjects the calling objects
     * @param controller     the controller
     */
    public UserPanel(List<DisposableObject> callingObjects, Controller controller) {

        super();

        this.setLayout(new GridBagLayout());

        setPopupMenu (controller, callingObjects);

        setUserButton(controller);
    }

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
     * Gets user greeted.
     *
     * @return the user greeted
     */
    public String getUserGreeted() {
        return userGreeted;
    }

}
