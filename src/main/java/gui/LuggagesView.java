package gui;

import javax.swing.*;
import java.awt.*;

public class LuggagesView extends JPanel {

    private JLabel label;
    private JScrollPane scrollPane;
    private JPanel luggagesPanel;
    private JPanel luggagePanel;

    private Constraints constraints;

    public LuggagesView() {

        this.setLayout(new GridBagLayout());
        constraints = new Constraints();

        label = new JLabel("Bagagli");

        setLuggagesPanel();
        setScrollPane();

    }

    private void setLuggagesPanel() {
        luggagesPanel = new JPanel();
        luggagesPanel.setLayout(new GridBagLayout());


    }

    private void setScrollPane() {
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(luggagesPanel);
    }
}
