package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.ArrayList;

/*
 * HamburgerPanel is an alternative for MenuPanelCustomer
 * We have decided to use MenuPanelCustomer for practicality
 * and ease of use
 * Still, HamburgerPanel is a viable alternative
 */

public class HamburgerPanel extends JPanel {
    private JButton hamburgerButton;
    private JButton myFlightButton;
    private JButton searchFlightButton;
    private JPanel invisiblePanel;
    private Constraints constraints;
    private boolean isClicked = false;

    public HamburgerPanel(ArrayList<JFrame> callingFrames, Controller controller) {

        super();

        this.setLayout(new GridBagLayout());
        this.constraints = new Constraints();
        this.setBackground(Color.WHITE);

        this.setMyFlightButton();
        this.setSearchFlightButton();
        this.setHamburgerButton();
    }

    private void setSearchFlightButton() {

        searchFlightButton = new JButton("CERCA VOLO");
        searchFlightButton.setLayout(new GridBagLayout());
        searchFlightButton.setEnabled(false);
        searchFlightButton.setVisible(false);
        searchFlightButton.setFocusable(false);

        searchFlightButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                myFlightButton.setEnabled(true);
                myFlightButton.setVisible(true);

                searchFlightButton.setEnabled(true);
                searchFlightButton.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isClicked) {
                    myFlightButton.setEnabled(false);
                    myFlightButton.setVisible(false);

                    searchFlightButton.setEnabled(false);
                    searchFlightButton.setVisible(false);
                }
            }
        });

        searchFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //pagina cerca volo
            }
        });

        constraints.setConstraints(0, 1, 3, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.FIRST_LINE_START);

        this.add(searchFlightButton, constraints.getConstraints());
    }

    private void setMyFlightButton() {

        myFlightButton = new JButton("I MIEI VOLI");
        myFlightButton.setLayout(new GridBagLayout());
        myFlightButton.setEnabled(false);
        myFlightButton.setVisible(false);
        myFlightButton.setFocusable(false);

        myFlightButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                myFlightButton.setEnabled(true);
                myFlightButton.setVisible(true);

                searchFlightButton.setEnabled(true);
                searchFlightButton.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isClicked) {
                    myFlightButton.setEnabled(false);
                    myFlightButton.setVisible(false);

                    searchFlightButton.setEnabled(false);
                    searchFlightButton.setVisible(false);
                }
            }
        });

        myFlightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //pagina i miei voli
            }
        });

        constraints.setConstraints(0, 2, 3, 1, GridBagConstraints.BOTH,
                0, 0, GridBagConstraints.FIRST_LINE_START);

        this.add(myFlightButton, constraints.getConstraints());
    }

    private void setHamburgerButton() {

        hamburgerButton = new JButton("≡");
        hamburgerButton.setLayout(new GridBagLayout());
        hamburgerButton.setFocusable(false);

        hamburgerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                isClicked = !isClicked;
            }
        });

        hamburgerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                myFlightButton.setEnabled(true);
                myFlightButton.setVisible(true);

                searchFlightButton.setEnabled(true);
                searchFlightButton.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!isClicked) {
                    myFlightButton.setEnabled(false);
                    myFlightButton.setVisible(false);

                    searchFlightButton.setEnabled(false);
                    searchFlightButton.setVisible(false);
                }
            }
        });

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.VERTICAL,
                0, 0, GridBagConstraints.FIRST_LINE_START);

        this.add(hamburgerButton, constraints.getConstraints());

        invisiblePanel = new JPanel();
        invisiblePanel.setBackground(Color.WHITE);
        invisiblePanel.setLayout(new GridBagLayout());

        invisiblePanel.setVisible(true);

        constraints.setConstraints(0, 1, 3, 2, GridBagConstraints.BOTH,
                0, 50, GridBagConstraints.FIRST_LINE_START);

        this.add(invisiblePanel, constraints.getConstraints());
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
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    }
}
