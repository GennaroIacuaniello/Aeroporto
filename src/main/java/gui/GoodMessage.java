package gui;

import javax.swing.*;
import java.awt.*;

public class GoodMessage {

    private JWindow goodWindow;
    private RoundedPanel goodPanel;
    private JLabel goodLabel;

    public GoodMessage (String msg, JButton callingButton){

        setWindow(callingButton);
        setPanel(msg);

        new DisposeTimers(goodWindow);

        goodWindow.setVisible(true);
    }

    private void setWindow (JButton callingButton) {

        goodWindow = new JWindow();

        goodWindow.setAlwaysOnTop(true);
        goodWindow.setOpacity(0.75f);
        goodWindow.setBackground(new Color(0, 0, 0, 0));
        JPanel contentPanel = (JPanel) goodWindow.getContentPane();
        contentPanel.setOpaque(false);
        goodWindow.setSize(300, 100);
        Point point = new Point(callingButton.getLocationOnScreen());
        goodWindow.setLocation((int)point.getX() + (callingButton.getWidth() - goodWindow.getWidth()) / 2, (int)point.getY() - goodWindow.getHeight() - 10);
    }

    private void setPanel (String msg) {

        goodPanel = new RoundedPanel(new BorderLayout());
        goodPanel.setBackground(new Color(139, 255, 104, 187));
        goodPanel.setRoundBorderColor (new Color(55, 142, 5));

        goodLabel = new JLabel("<html><center>" + msg + "</center></html>", SwingConstants.CENTER);
        goodLabel.setForeground(Color.BLACK);

        goodPanel.add(goodLabel, BorderLayout.CENTER);

        goodWindow.add(goodPanel);
    }
}
