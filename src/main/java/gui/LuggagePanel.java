package gui;

import javax.swing.*;
import java.awt.*;

/**
 * The type Luggage panel.
 */
public class LuggagePanel extends JPanel {

    private JLabel label;
    private final int index;
    private JComboBox<String> comboBox;

    private final Constraints constraints;

    /**
     * Instantiates a new Luggage panel.
     *
     * @param i the
     */
    public LuggagePanel(int i) {

        this.setLayout(new GridBagLayout());
        constraints = new Constraints();
        index = i;

        setLabel();
        setComboBox();
    }

    /**
     * Sets label.
     *
     * @param string the string
     */
    public void setLabel(String string) {
        label.setText(string);
    }

    private void setLabel() {
        label = new JLabel("Bagaglio:" + (index + 1));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        constraints.setConstraints(0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add(label, constraints.getGridBagConstraints());

        label.setVisible(true);
    }

    private void setComboBox() {

        comboBox = new JComboBox<>();

        comboBox.addItem("TYPE");
        comboBox.addItem("CARRY_ON");
        comboBox.addItem("CHECKED");

        constraints.setConstraints(0, 1, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);
        this.add(comboBox, constraints.getGridBagConstraints());
        comboBox.setVisible(true);
    }

    /**
     * Gets index.
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Check luggage boolean.
     *
     * @return the boolean
     */
    public boolean checkLuggage() {
        return comboBox.getSelectedIndex() == 0;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(Integer type) {
        comboBox.setSelectedIndex(type + 1);
    }

    /**
     * Gets combo box.
     *
     * @return the combo box
     */
    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    /**
     * Gets ticket.
     *
     * @return the ticket
     */
    public String getTicket () {
        return getSubstringAfterColon(label.getText());
    }

    /**
     * Gets substring after colon.
     *
     * @param inputString the input string
     * @return the substring after colon
     */
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
        return inputString.substring(colonIndex + 2);
    }
}
