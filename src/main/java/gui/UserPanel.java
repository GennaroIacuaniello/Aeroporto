package gui;

import controller.Controller;
import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class UserPanel extends JPanel {

    private JButton userButton;
    private JButton logoutButton;
    private Constraints constraints;
    private JPanel invisiblePanel;

    public UserPanel(ArrayList<JFrame> callingFrames, Controller controller) {

        super();

        constraints = new Constraints();
        this.setLayout(new GridBagLayout());

        this.setLogoutButton(callingFrames, controller);
        this.setUserButton(controller.getUserController());
    }

    private void setLogoutButton(ArrayList<JFrame> callingFrames, Controller controller) {

        logoutButton = new JButton("Logout");
        logoutButton.setLayout(new GridBagLayout());
        logoutButton.setEnabled(false);
        logoutButton.setVisible(false);
        logoutButton.setFocusable(false);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new LogInScreen(callingFrames, controller);
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

}
