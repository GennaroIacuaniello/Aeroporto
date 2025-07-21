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

    public void setLabel(String string) {
        label.setText(string);
    }

    private void setLabel() {
        label = new JLabel("Bagaglio:" + (index + 1));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add(label, constraints.getConstraints());

        label.setVisible(true);
    }

    private void setComboBox() {

        comboBox = new JComboBox<String>();

        comboBox.addItem("TYPE");
        comboBox.addItem("CARRY_ON");
        comboBox.addItem("CHECKED");

        constraints.setConstraints(0, 1, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
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

    public JComboBox getComboBox() {
        return comboBox;
    }

    public String getTicket () {
        return getSubstringAfterColon(label.getText());
    }

    public static String getSubstringAfterColon(String inputString) {
        // Controlla se la stringa è valida (non null e non vuota)
        if (inputString == null || inputString.isEmpty()) {
            return null;
        }

        // Trova l'indice del primo carattere ':'
        int colonIndex = inputString.indexOf(":");

        // Se il carattere ':' non è stato trovato, restituisce null
        if (colonIndex == -1) {
            return null;
        }

        // Se il carattere ':' è l'ultimo carattere della stringa, restituisce una stringa vuota
        if (colonIndex == inputString.length() - 1) {
            return "";
        }

        // Estrae e restituisce la sottostringa che segue il carattere ':'
        return inputString.substring(colonIndex + 1);
    }
}
