package gui;

import controller.Controller;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HamburgerPanel extends JPanel
{
    private JButton hamburgerButton;
    private JButton myFlightButton;
    private JButton searchFlightButton;
    private JPanel invisiblePanel;
    private Constraints constraints;

    public HamburgerPanel(JFrame callingFrame, Controller controller){

        super();

        this.setLayout(new GridBagLayout());
        this.constraints = new Constraints();

        this.setMyFlightButton();
        this.setSearchFlightButton();
        this.setHamburgerButton(callingFrame, controller);

        this.setVisible(true);
    }

    private void setSearchFlightButton(){

        searchFlightButton = new JButton("CERCA VOLO");
        searchFlightButton.setLayout(new GridBagLayout());
        searchFlightButton.setEnabled(false);
        searchFlightButton.setVisible(false);
        searchFlightButton.setFocusable(false);

        searchFlightButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                //pagina cerca volo
            }
        });

        constraints.setConstraints(0, 1, 3, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.FIRST_LINE_START);

        this.add(searchFlightButton, constraints.getConstraints());
    }

    private void setMyFlightButton(){

        myFlightButton = new JButton("I MIEI VOLI");
        myFlightButton.setLayout(new GridBagLayout());
        myFlightButton.setEnabled(false);
        myFlightButton.setVisible(false);
        myFlightButton.setFocusable(false);

        myFlightButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){

                //pagina i miei voli
            }
        });

        constraints.setConstraints(0, 2, 3, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.FIRST_LINE_START);

        this.add (myFlightButton, constraints.getConstraints());
    }

    private void setHamburgerButton(JFrame callingFrame, Controller controller){

        hamburgerButton = new JButton("test");
        hamburgerButton.setLayout(new GridBagLayout());
        hamburgerButton.setFocusable(false);

        hamburgerButton.addActionListener (new ActionListener (){
            @Override
            public void actionPerformed(ActionEvent e){

                myFlightButton.setEnabled(true);
                myFlightButton.setVisible(true);

                searchFlightButton.setEnabled(true);
                searchFlightButton.setVisible(true);
            }
        });

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.VERTICAL,
                0, 0, GridBagConstraints.FIRST_LINE_START);

        this.add(hamburgerButton, constraints.getConstraints());

        invisiblePanel = new JPanel();
        //invisiblePanel.setBackground(Color.GREEN);
        invisiblePanel.setLayout (new GridBagLayout());
        invisiblePanel.setVisible(true);

        constraints.setConstraints (0, 1, 3, 2, GridBagConstraints.BOTH,
                0, 50, GridBagConstraints.FIRST_LINE_START);

        this.add(invisiblePanel, constraints.getConstraints());
    }
}
