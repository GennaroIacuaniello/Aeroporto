package gui;

import javax.swing.*;
import java.awt.*;

public class HamburgerPanel extends JButton
{
    public HamburgerPanel (String title)
    {
        super (title);

        this.setLayout (new GridBagLayout());

        this.setVisible (true);
    }
}
