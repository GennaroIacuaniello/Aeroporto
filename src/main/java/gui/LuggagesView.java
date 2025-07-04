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
    private RoundedButton addLuggageButton;
    private RoundedButton confirmButton;

    private Constraints constraints;

    public LuggagesView(Controller controller) {

        super("Luggages");

        this.setVisible(false);
        this.setAlwaysOnTop(true);

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
        addLuggageButton = new RoundedButton("+");
        confirmButton = new RoundedButton("Confirm");

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

    public void setLuggagesTypes(ArrayList<Integer> luggagesTypes, Controller controller) {
        for (Integer luggageType : luggagesTypes) {
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

            luggagesPanels.getLast().setType(luggageType);

            scrollPane.setViewportView(luggagesPanel);
        }
    }

    public void setLocation(RoundedButton callingButtonutton) {
        //coordinate punto in alto a sx del bottone
        double x = callingButtonutton.getLocationOnScreen().getX();
        double y = callingButtonutton.getLocationOnScreen().getY();

        //coordinate centro
        x += callingButtonutton.getSize().getWidth() / 2;
        y += callingButtonutton.getSize().getHeight() / 2;

        //coordinate punto in alto a sx frame
        x -= (double) this.getWidth() / 2;
        y -= (double) this.getHeight() / 2;

        this.setLocation((int) x, (int) y);
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
