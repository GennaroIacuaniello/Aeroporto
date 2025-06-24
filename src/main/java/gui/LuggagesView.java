package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import controller.Controller;

public class LuggagesView extends JPanel {

    private JLabel label;
    private JScrollPane scrollPane;
    private JPanel luggagesPanel;
    private ArrayList<JPanel> luggagesPanels;
    private JButton addLuggageButton;

    private Constraints constraints;

    public LuggagesView(Controller controller) {

        this.setLayout(new GridBagLayout());
        constraints = new Constraints();
        luggagesPanels = new ArrayList<JPanel>();

        label = new JLabel("Bagagli");
        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.HORIZONTAL, 0, 0, GridBagConstraints.CENTER);
        this.add(label, constraints.getConstraints());

        setLuggagesPanel(controller);
        setAddLuggageButton(controller);
        setScrollPane(controller);
    }

    private void setLuggagesPanel(Controller controller) {
        luggagesPanel = new JPanel();
        luggagesPanel.setLayout(new GridBagLayout());

        luggagesPanels.add(new LuggagePanel());

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        luggagesPanel.add(luggagesPanels.getFirst(), constraints.getConstraints());
    }

    private void setScrollPane(Controller controller) {
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(luggagesPanel);

        constraints.setConstraints(0, 1, 2, 1,
                GridBagConstraints.BOTH, 0, 0, GridBagConstraints.CENTER, 0.5f, 0.5f);
        this.add(scrollPane, constraints.getConstraints());
    }

    private void setAddLuggageButton(Controller controller) {
        addLuggageButton = new JButton("+");

        constraints.setConstraints(1, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
        this.add(addLuggageButton, constraints.getConstraints());

        addLuggageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                luggagesPanels.add(new LuggagePanel());

                constraints.setConstraints(0, luggagesPanels.size() - 1, 1, 1,
                        GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
                luggagesPanel.add(luggagesPanels.getLast(), constraints.getConstraints());
                luggagesPanels.getLast().setVisible(true);
                scrollPane.setViewportView(luggagesPanel);
            }
        });
    }
}
