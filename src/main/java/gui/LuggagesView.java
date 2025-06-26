package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import controller.Controller;

public class LuggagesView extends JFrame {

    private JFrame thisFrame;
    private JScrollPane scrollPane;
    private JPanel luggagesPanel;
    private ArrayList<LuggagePanel> luggagesPanels;
    private ArrayList<RemoveLuggageButton> removeLuggageButtons;
    private JButton addLuggageButton;
    private JButton confirmButton;

    private Constraints constraints;

    public LuggagesView(Controller controller) {

        super ("Luggages");

        this.setVisible (false);
        this.setAlwaysOnTop (true);

        thisFrame = this;
        this.setLayout(new GridBagLayout());
        constraints = new Constraints();
        this.setSize(200, 300);

        setLuggagesPanel(controller);
        setButtons(controller);
        setScrollPane(controller);
    }

    private void setLuggagesPanel(Controller controller) {
        luggagesPanel = new JPanel();
        luggagesPanel.setLayout(new GridBagLayout());

        luggagesPanels = new ArrayList<LuggagePanel>();
        removeLuggageButtons = new ArrayList<RemoveLuggageButton>();
    }

    private void setButtons(Controller controller) {
        addLuggageButton = new JButton("+");
        confirmButton = new JButton("Confirm");

        addLuggageButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if (!luggagesPanels.isEmpty()) {
                    luggagesPanels.add(new LuggagePanel(controller, luggagesPanels.getLast().getIndex() + 1));
                } else {
                    luggagesPanels.add(new LuggagePanel(controller, 0));
                }


                constraints.setConstraints(0, luggagesPanels.size() - 1, 1, 1,
                        GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
                luggagesPanel.add(luggagesPanels.getLast(), constraints.getConstraints());
                luggagesPanels.getLast().setVisible(true);

                removeLuggageButtons.add(new RemoveLuggageButton(controller, luggagesPanels, removeLuggageButtons, luggagesPanel,
                        scrollPane, removeLuggageButtons.size()));

                constraints.setConstraints(1, luggagesPanels.size() - 1, 1, 1,
                        GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
                luggagesPanel.add(removeLuggageButtons.getLast(), constraints.getConstraints());
                luggagesPanels.getLast().setVisible(true);

                scrollPane.setViewportView(luggagesPanel);
            }
        });

        confirmButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                thisFrame.setVisible(false);
            }
        });

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
        this.add(addLuggageButton, constraints.getConstraints());

        constraints.setConstraints(1, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
        this.add(confirmButton, constraints.getConstraints());

        addLuggageButton.setVisible(true);
        addLuggageButton.setFocusable(false);
        confirmButton.setVisible(true);
        confirmButton.setFocusable(false);
    }

    private void setScrollPane(Controller controller) {
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(luggagesPanel);
        scrollPane.setWheelScrollingEnabled(true);

        constraints.setConstraints(0, 1, 2, 1,
                GridBagConstraints.BOTH, 0, 0, GridBagConstraints.CENTER, 0.5f, 0.5f);
        this.add(scrollPane, constraints.getConstraints());
        scrollPane.setVisible(true);
    }

    public ArrayList<LuggagePanel> getLuggagesPanels() {
        return luggagesPanels;
    }
}
