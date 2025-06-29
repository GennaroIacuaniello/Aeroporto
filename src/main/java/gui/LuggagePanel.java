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
        label = new JLabel("Bagaglio " + (index + 1));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        constraints.setConstraints(0, 0, 1, 1,
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

    public boolean checkLuggage() {
        return comboBox.getSelectedIndex() == 0;
    }

    public void setType(Integer type) {
        comboBox.setSelectedIndex(type + 1);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
    }
}
