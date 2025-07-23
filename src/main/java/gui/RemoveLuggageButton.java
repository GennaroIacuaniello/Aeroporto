package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The type Remove luggage button.
 */
public class RemoveLuggageButton extends JButton {
    private int index;
    private final Constraints constraints;

    /**
     * Instantiates a new Remove luggage button.
     *
     * @param luggagePanels        the luggage panels
     * @param removeLuggageButtons the remove luggage buttons
     * @param luggagesPanel        the luggages panel
     * @param scrollPane           the scroll pane
     * @param i                    the
     */
    public RemoveLuggageButton(List<LuggagePanel> luggagePanels,
                               List<RemoveLuggageButton> removeLuggageButtons, JPanel luggagesPanel, JScrollPane scrollPane, int i) {
        super("Rimuovi ");
        index = i;
        constraints = new Constraints();

        this.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //creo variabili temporanee
                LuggagePanel tmpluggagePanel = luggagePanels.get(index);
                RemoveLuggageButton tmpremoveLuggageButton = removeLuggageButtons.get(index);
                tmpluggagePanel.setVisible(false);
                tmpremoveLuggageButton.setVisible(false);

                //rimuovo da liste
                luggagePanels.remove(index);
                removeLuggageButtons.remove(index);

                //rimuovo da pagina
                luggagesPanel.remove(tmpluggagePanel);
                luggagesPanel.remove(tmpremoveLuggageButton);

                //aggiusto la pagina
                for (int i = index; i < luggagePanels.size(); i++) {
                    luggagesPanel.remove(luggagePanels.get(i));
                    constraints.setConstraints(0, i, 1, 1,
                            GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_END);
                    luggagesPanel.add(luggagePanels.get(i), constraints.getGridBagConstraints());
                    luggagePanels.get(i).setVisible(true);

                    luggagesPanel.remove(removeLuggageButtons.get(i));
                    constraints.setConstraints(1, i, 1, 1,
                            GridBagConstraints.NONE, 0, 0, GridBagConstraints.LINE_START);
                    luggagesPanel.add(removeLuggageButtons.get(i), constraints.getGridBagConstraints());
                    removeLuggageButtons.get(i).setVisible(true);

                    removeLuggageButtons.get(i).index--;
                }

                //aggiusto view
                scrollPane.setViewportView(luggagesPanel);
            }
        });
    }

    /**
     * Gets index.
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }
}
