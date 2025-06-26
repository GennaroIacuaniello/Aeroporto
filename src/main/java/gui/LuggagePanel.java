package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LuggagePanel extends JPanel {

    private JLabel label;
    private int index;
    private JComboBox comboBox;

    private Constraints constraints;

    public LuggagePanel(Controller controller, int i) {

        this.setLayout(new GridBagLayout());
        constraints = new Constraints();
        index = i;

        setLabel();
        setComboBox();
    }

    private void setLabel() {
        label = new JLabel("Bagaglio " + (index+1));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        constraints.setConstraints (0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add(label, constraints.getConstraints());

        label.setVisible(true);
    }

    private void setComboBox() {

        comboBox = new JComboBox<String>();

        comboBox.addItem("Type");
        comboBox.addItem("Carry-on");
        comboBox.addItem("Checked");

        constraints.setConstraints(0, 1, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
        this.add(comboBox, constraints.getConstraints());
        comboBox.setVisible(true);
    }

    public int getIndex() {
        return index;
    }

    public boolean checkLuggage () {
        return comboBox.getSelectedIndex() == 0;
    }
}
