package gui;

import controller.Controller;
import controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserPanel extends JPanel {

    private JButton userButton;
    private String userGreeted;
    JPopupMenu popupMenu;
    JMenuItem menuItem;

    private Constraints constraints;

    public UserPanel(List<DisposableObject> callingObjects, Controller controller) {

        super();

        constraints = new Constraints();

        this.setLayout(new GridBagLayout());

        setPopupMenu (controller, callingObjects);

        setUserButton(controller);
    }

    private void setPopupMenu (Controller controller, List<DisposableObject> callingObjects) {

        popupMenu = new JPopupMenu();
        menuItem = new JMenuItem("Logout");

        menuItem.addActionListener(actionEvent -> {

            controller.logOut(callingObjects);
        });

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

        userGreeted = controller.getUserController().getUsername();
        userButton = new JButton("<html>Ciao,<br>" + userGreeted + "</html>");
        System.out.println(userButton.getSize());
        userButton.addActionListener(e -> {

            popupMenu.show(userButton, 0, userButton.getHeight());
        });

        this.add(userButton);
    }

    public String getUserGreeted() {
        return userGreeted;
    }

    /*
    private void setLogoutButton(ArrayList<DisposableObject> callingObjects, Controller controller) {

        logoutButton = new JButton("Logout");
        logoutButton.setLayout(new GridBagLayout());
        logoutButton.setEnabled(false);
        logoutButton.setVisible(false);
        logoutButton.setFocusable(false);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                controller.logOut(callingObjects);
            }
        });

        constraints.setConstraints(0, 1, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_END);

        this.add(logoutButton, constraints.getConstraints());
    }

    private void setUserButton(UserController userController) {

        userButton = new JButton("<html>Ciao,<br>" + userController.getUsername() + "</html>");
        userButton.setLayout(new GridBagLayout());
        userButton.setFocusable(false);

        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!logoutButton.isVisible()) {
                    logoutButton.setEnabled(true);
                    logoutButton.setVisible(true);
                } else {
                    logoutButton.setEnabled(false);
                    logoutButton.setVisible(false);
                }
            }
        });

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.FIRST_LINE_END);

        this.add(userButton, constraints.getConstraints());

        invisiblePanel = new JPanel();
        //invisiblePanel.setBackground(Color.GREEN);
        invisiblePanel.setLayout(new GridBagLayout());
        invisiblePanel.setVisible(true);

        constraints.setConstraints(1, 1, 2, 1, GridBagConstraints.NONE,
                0, 0, GridBagConstraints.PAGE_END);

        this.add(invisiblePanel, constraints.getConstraints());
    }
    */
}
