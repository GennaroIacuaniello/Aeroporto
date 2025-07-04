package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NavigatorBarPanel extends JPanel {
    JButton homeButton;
    JButton backButton;
    JLabel pathLabel;
    Constraints constraints;

    public NavigatorBarPanel(ArrayList<DisposableObject> callingObjects, Controller controller) {
        super();

        constraints = new Constraints();
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBackground(Color.white);

        this.setHomeButton(callingObjects, controller);
        this.setBackButton(callingObjects, controller);
        this.setPath(callingObjects);
    }

    private void setHomeButton(ArrayList<DisposableObject> callingObjects, Controller controller) {
        homeButton = new JButton("Home");
        //homeButton.setLayout (new FlowLayout ());
        homeButton.setFocusable(false);

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                controller.goHome(callingObjects);
            }
        });

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.VERTICAL,
                0, 0, GridBagConstraints.LINE_START);
        this.add(homeButton, constraints.getConstraints());
        homeButton.setVisible(true);
    }

    private void setBackButton(ArrayList<DisposableObject> callingObjects, Controller controller) {
        backButton = new JButton("Back");
        //backButton.setLayout (new GridBagLayout ());
        backButton.setFocusable(false);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                controller.goBack(callingObjects);
            }
        });

        constraints.setConstraints(1, 0, 1, 1, GridBagConstraints.VERTICAL,
                0, 0, GridBagConstraints.LINE_START);
        this.add(backButton, constraints.getConstraints());
        //constraints.setConstraints (1, 0, 1, 1, GridBagConstraints.VERTICAL, 0, 0, GridBagConstraints.LINE_START);
        this.add(backButton);
        backButton.setVisible(true);
    }

    private void setPath(ArrayList<DisposableObject> callingObjects) {
        String path = "Posizione:\"";
        for (int i = 1; i < callingObjects.size(); i++) {
            path += callingObjects.get(i).getFrame().getTitle() + "/";
        }
        path += "\"";
        pathLabel = new JLabel(path);

        //pathLabel.setLayout (new GridBagLayout ());

        //constraints.setConstraints (2, 0, 17, 1, GridBagConstraints.BOTH, 0, 0, GridBagConstraints.LINE_START);
        this.add(pathLabel);
        pathLabel.setVisible(true);
    }

}
