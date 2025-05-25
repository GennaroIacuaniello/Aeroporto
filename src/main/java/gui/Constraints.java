package gui;

import javax.swing.*;
import java.awt.*;

public class Constraints {
    private GridBagConstraints constraints;

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
                               int ipadx, int ipady, int anchor, float weightx, float weighty){

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
        constraints.insets = new Insets(0, 0, 0, 0);
    }

    public void setConstraints (int gridx, int gridy, int gridwidth, int gridheight, int fill,
                                int ipadx, int ipady, int anchor){

        constraints.gridx = gridx;
        constraints.gridy = gridy;
        constraints.gridwidth = gridwidth;
        constraints.gridheight = gridheight;
        constraints.fill = fill;
        constraints.ipadx = ipadx;
        constraints.ipady = ipady;
        constraints.anchor = anchor;
        constraints.weightx = 0.01;
        constraints.weighty = 0.01;
        constraints.insets = new Insets(0, 0, 0, 0);
    }

    public void resetWeight ()
    {
        constraints.weightx = 0.01;
        constraints.weighty = 0.01;
    }

    public GridBagConstraints getConstraints(){return constraints;}

}
