package gui;

import java.awt.*;

/**
 * The type Constraints.
 */
public class Constraints {
    private final GridBagConstraints gridBagConstraints;

    /**
     * Instantiates a new Constraints.
     */
    public Constraints() {
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 0.01;
        gridBagConstraints.weighty = 0.01;
    }

    /**
     * Sets constraints.
     *
     * @param gridx      the gridx
     * @param gridy      the gridy
     * @param gridwidth  the gridwidth
     * @param gridheight the gridheight
     * @param fill       the fill
     * @param ipadx      the ipadx
     * @param ipady      the ipady
     * @param anchor     the anchor
     * @param weightx    the weightx
     * @param weighty    the weighty
     * @param insets     the insets
     */
    public void setConstraints(int gridx, int gridy, int gridwidth, int gridheight, int fill,
                               int ipadx, int ipady, int anchor, float weightx, float weighty, Insets insets) {

        gridBagConstraints.gridx = gridx;
        gridBagConstraints.gridy = gridy;
        gridBagConstraints.gridwidth = gridwidth;
        gridBagConstraints.gridheight = gridheight;
        gridBagConstraints.fill = fill;
        gridBagConstraints.ipadx = ipadx;
        gridBagConstraints.ipady = ipady;
        gridBagConstraints.anchor = anchor;
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
        gridBagConstraints.insets = insets;
    }

    /**
     * Sets constraints.
     *
     * @param gridx      the gridx
     * @param gridy      the gridy
     * @param gridwidth  the gridwidth
     * @param gridheight the gridheight
     * @param fill       the fill
     * @param ipadx      the ipadx
     * @param ipady      the ipady
     * @param anchor     the anchor
     * @param weightx    the weightx
     * @param weighty    the weighty
     */
    public void setConstraints(int gridx, int gridy, int gridwidth, int gridheight, int fill,
                               int ipadx, int ipady, int anchor, float weightx, float weighty) {

        this.setConstraints(gridx, gridy, gridwidth, gridheight, fill,
                ipadx, ipady, anchor, weightx, weighty, new Insets(0, 0, 0, 0));
    }

    /**
     * Sets constraints.
     *
     * @param gridx      the gridx
     * @param gridy      the gridy
     * @param gridwidth  the gridwidth
     * @param gridheight the gridheight
     * @param fill       the fill
     * @param ipadx      the ipadx
     * @param ipady      the ipady
     * @param anchor     the anchor
     * @param insets     the insets
     */
    public void setConstraints(int gridx, int gridy, int gridwidth, int gridheight, int fill,
                               int ipadx, int ipady, int anchor, Insets insets) {

        this.setConstraints(gridx, gridy, gridwidth, gridheight, fill,
                ipadx, ipady, anchor, 0.01f, 0.01f, insets);

    }

    /**
     * Sets constraints.
     *
     * @param gridx      the gridx
     * @param gridy      the gridy
     * @param gridwidth  the gridwidth
     * @param gridheight the gridheight
     * @param fill       the fill
     * @param ipadx      the ipadx
     * @param ipady      the ipady
     * @param anchor     the anchor
     */
    public void setConstraints(int gridx, int gridy, int gridwidth, int gridheight, int fill,
                               int ipadx, int ipady, int anchor) {

        this.setConstraints(gridx, gridy, gridwidth, gridheight, fill,
                ipadx, ipady, anchor, 0.01f, 0.01f, new Insets(0, 0, 0, 0));
    }

    /**
     * Gets grid bag constraints.
     *
     * @return the grid bag constraints
     */
    public GridBagConstraints getGridBagConstraints() {
        return gridBagConstraints;
    }
}
