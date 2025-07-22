package gui;

import javax.swing.*;
import java.awt.*;

public class Constraints {
    private final GridBagConstraints constraints;

    public Constraints() {
        constraints = new GridBagConstraints();
        constraints.weightx = 0.01;
        constraints.weighty = 0.01;
    }

    public void setConstraints(int gridx, int gridy, int gridwidth, int gridheight, int fill,
                               int ipadx, int ipady, int anchor, float weightx, float weighty, Insets insets) {

        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.fill = fill;
        constraints.ipadx = ipadx;
        constraints.ipady = ipady;
        constraints.anchor = anchor;
        constraints.weightx = weightx;
        constraints.weighty = weighty;
        constraints.insets = insets;
    }

    public void setConstraints(int gridx, int gridy, int gridwidth, int gridheight, int fill,
                               int ipadx, int ipady, int anchor, float weightx, float weighty) {

        this.setConstraints(gridx, gridy, gridwidth, gridheight, fill,
                ipadx, ipady, anchor, weightx, weighty, new Insets(0, 0, 0, 0));
    }

    public void setConstraints(int gridx, int gridy, int gridwidth, int gridheight, int fill,
                               int ipadx, int ipady, int anchor, Insets insets) {

        this.setConstraints(gridx, gridy, gridwidth, gridheight, fill,
                ipadx, ipady, anchor, 0.01f, 0.01f, insets);

    }

    public void setConstraints(int gridx, int gridy, int gridwidth, int gridheight, int fill,
                               int ipadx, int ipady, int anchor) {

        this.setConstraints(gridx, gridy, gridwidth, gridheight, fill,
                ipadx, ipady, anchor, 0.01f, 0.01f, new Insets(0, 0, 0, 0));
    }

    public GridBagConstraints getConstraints() {
        return constraints;
    }
}
