package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Menu_panel extends JPanel {

    private JPanel invisiblePanel;
    private Constraints constraints;
    private JComboBox menu;

    public Menu_panel(ArrayList<JFrame> callingFrames, Controller controller) {

        super();

        this.setLayout(new GridBagLayout());
        this.constraints = new Constraints();

        menu = new JComboBox<String>();
        this.menu.addItem("Home");
        this.menu.addItem("Pagina iniziale");
        this.menu.addItem("I miei voli");

        this.setVisible(true);
        this.menu.setVisible(true);
        this.add(menu, constraints.getConstraints());

        invisiblePanel = new JPanel();
        //invisiblePanel.setBackground(Color.GREEN);
        invisiblePanel.setLayout(new GridBagLayout());
        invisiblePanel.setVisible(true);

        constraints.setConstraints(0, 1, 3, 2, GridBagConstraints.BOTH,
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
                    case "Pagina iniziale":
                        JOptionPane.showMessageDialog(invisiblePanel, "Apertura Pagina Iniziale");
                        break;
                    case "I miei voli":
                        JOptionPane.showMessageDialog(invisiblePanel, "Apertura pagina I miei voli");
                        break;
                    default:
                        break;
                }
            }

        });
    }


}
