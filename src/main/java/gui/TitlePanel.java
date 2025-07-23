package gui;

import javax.swing.*;
import java.awt.*;

/**
 * The TitlePanel class provides a standardized header panel with a title.
 * This panel is used throughout the application to display section titles with consistent styling.
 * It creates a centered title with HTML formatting for enhanced visual appearance.
 */
public class TitlePanel extends JPanel {

    /**
     * The layout constraints manager for this panel.
     * Used to position components within the GridBagLayout.
     */
    Constraints constraints;


    /**
     * Instantiates a new Title panel with the specified title text.
     * This constructor sets up the panel with a GridBagLayout and adds a centered title label.
     * The title is formatted as an HTML h1 heading for enhanced visual appearance.
     *
     * @param title the text to display as the panel's title
     */
    public TitlePanel(String title) {

        super();

        constraints = new Constraints();

        this.setLayout(new GridBagLayout());

        setTitleLabel(title);

        this.setVisible(true);
    }

    /**
     * Creates and configures the title label with the specified text.
     * This method formats the title as an HTML h1 heading and centers it within the panel.
     * It uses GridBagConstraints to position the label appropriately.
     *
     * @param title the text to display in the title label
     */
    private void setTitleLabel(String title) {
        JLabel titleLabel;

        titleLabel = new JLabel("<html><h1>" + title + "</h1></html>");

        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);

        constraints.setConstraints(0, 0, 1, 1, GridBagConstraints.BOTH, 0, 50, GridBagConstraints.CENTER);

        this.add(titleLabel, constraints.getGridBagConstraints());
        titleLabel.setVisible(true);
    }
}
