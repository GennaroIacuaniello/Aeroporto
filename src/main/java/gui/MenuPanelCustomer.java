package gui;

import controller.Controller;
import model.Customer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuPanelCustomer extends JPanel {

    private JPanel invisiblePanel;
    private Constraints constraints;
    private JComboBox menu;

    public MenuPanelCustomer(ArrayList<JFrame> callingFrames, Controller controller, Customer customer){

        super();

        this.setLayout(new GridBagLayout());
        this.constraints = new Constraints();

        menu = new JComboBox<String>();

        this.menu.addItem("Menù");
        this.menu.addItem("Home");
        this.menu.addItem("Cerca voli");
        this.menu.addItem("I miei voli");
        //this.menu.addItem("Colora");


        this.setVisible(true);
        this.menu.setVisible(true);
        this.add(menu, constraints.getConstraints());

        invisiblePanel = new JPanel();
        //invisiblePanel.setBackground(Color.GREEN);
        invisiblePanel.setLayout (new GridBagLayout());
        invisiblePanel.setVisible(true);

        constraints.setConstraints (0, 1, 3, 2, GridBagConstraints.BOTH,
                0, 50, GridBagConstraints.FIRST_LINE_START);

        this.add(invisiblePanel, constraints.getConstraints());

        this.menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selected_option = (String) menu.getSelectedItem();

                switch (selected_option) {
                    case "Menu":
                        //Selezionato menù, chiudo solamente la JComboBox
                        break;
                    case "Home":
                        callingFrames.get(callingFrames.size() - 1).setVisible(false);
                        callingFrames.getLast().dispose();
                        callingFrames.removeLast();
                        new MainCustomerScreen(callingFrames, controller, customer);
                        break;
                    case "Cerca voli":
                        callingFrames.get(callingFrames.size() - 1).setVisible(false);
                        callingFrames.getLast().dispose();
                        callingFrames.removeLast();
                        new SearchFlightCustomerMainFrame(callingFrames, controller, customer);
                        break;
                    case "I miei voli":
                        JOptionPane.showMessageDialog(invisiblePanel, "Apertura pagina I miei voli");
                        break;
                    case "Colora":
                        controller.developerMode = !controller.developerMode;
                    default:
                        break;
                }
            }

        });
    }



}
